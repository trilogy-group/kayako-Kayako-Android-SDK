package com.kayako.sdk.android.k5.kre.base;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fasterxml.jackson.databind.JsonNode;
import com.kayako.sdk.android.k5.common.utils.NetworkUtils;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.core.KayakoLogHelper;
import com.kayako.sdk.android.k5.kre.base.credentials.KreCredentials;
import com.kayako.sdk.android.k5.kre.base.credentials.KreFingerprintCredentials;
import com.kayako.sdk.android.k5.kre.base.credentials.KreSessionCredentials;

import org.phoenixframework.channels.Channel;
import org.phoenixframework.channels.IErrorCallback;
import org.phoenixframework.channels.ISocketCloseCallback;
import org.phoenixframework.channels.ISocketOpenCallback;
import org.phoenixframework.channels.Socket;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created on 17/03/18.
 */

public class SocketConnection {

    private static final String TAG = "SocketConnection";

    // private static final String KRE_SOCKET_URL = "wss://kre.kayako.net/socket/websocket"; // No longer used, value received via API
    private static final String KRE_SOCKET_URL_SUFFIX = "/websocket";
    private static final String PARAM_SESSION_ID = "session_id";
    private static final String PARAM_FINGERPRINT_ID = "fingerprint_id";
    private static final String PARAM_INSTANCE = "instance";
    private static final String PARAM_USER_AGENT = "user_agent";
    private static final String PARAM_VERSION = "vsn";
    private static final String VERSION_NO = "1.0.0";

    protected Socket mSocket;
    private AtomicBoolean mIsConnected = new AtomicBoolean();

    /**
     * Used to connect
     *
     * @param kreCredentials
     * @param listener
     */
    protected synchronized void connect(@NonNull KreCredentials kreCredentials, @NonNull final OnOpenSocketConnectionListener listener) {
        connect(kreCredentials, listener, null);
    }

    /**
     * Used to connect
     *
     * @param kreCredentials
     * @param listener
     * @param jsonPayload
     */
    protected synchronized void connect(@NonNull final KreCredentials kreCredentials, @NonNull final OnOpenSocketConnectionListener listener, @Nullable final JsonNode jsonPayload) {
        if (!NetworkUtils.isConnectedToNetwork(Kayako.getApplicationContext())) {
            KayakoLogHelper.e(TAG, "No Internet Connection! Not going forward with connect()");
            listener.onError("No Network Connection. Please connect to the Internet.");
            return;
        }

        String url = generateUrlFromKreCredentials(kreCredentials);

        try {
            mIsConnected.set(false);

            mSocket = new Socket(url);

            // When the KRE Client Library throws an exception or fails for any reason, the app does not crash but instead calls this
            mSocket.setOnSocketThrowExceptionListener(new Socket.OnSocketThrowExceptionListener() {
                @Override
                public void onThrowException(String method, Throwable e) {
                    KayakoLogHelper.e(TAG, "Non-Fatal: Crash has been prevented but intended behaviour no longer guaranteed.");
                    KayakoLogHelper.logException(TAG, e);
                }
            });

            mSocket
                    .onOpen(new ISocketOpenCallback() {
                        @Override
                        public void onOpen() {
                            listener.onOpen();
                            mIsConnected.set(true);
                        }
                    })
                    .onError(new IErrorCallback() {
                        @Override
                        public void onError(String s) {
                            listener.onError(s);
                            mIsConnected.set(false);
                        }
                    })
                    .connect();

        } catch (IOException e) {
            KayakoLogHelper.printStackTrace(TAG, e); // Don't track IOExceptions on Crashlytics
        } catch (Throwable e) {
            KayakoLogHelper.logException(TAG, e);
        }
    }

    /**
     * Disconnect the socket
     */
    protected synchronized void disconnect(@Nullable final OnCloseConnectionListener listener) {
        if (mSocket != null) {
            try {
                mSocket
                        .onClose(new ISocketCloseCallback() {
                            @Override
                            public void onClose() {
                                if (listener != null) {
                                    listener.onClose();
                                }
                                mIsConnected.set(false);
                            }
                        })
                        .onError(new IErrorCallback() {
                            @Override
                            public void onError(String s) {
                                if (listener != null) {
                                    listener.onError(s);
                                }
                            }
                        })
                        .disconnect();

                mSocket = null;
                mIsConnected.set(false);
            } catch (IOException e) {
                KayakoLogHelper.printStackTrace(TAG, e); // Don't track IOExceptions on Crashlytics
            } catch (Throwable e) {
                KayakoLogHelper.logException(TAG, e);
            }
        }
    }

    protected void configureReconnectOnFailure(boolean reconnect) {
        try {
            if (mSocket != null) {
                mSocket.reconectOnFailure(reconnect);
                KayakoLogHelper.e(TAG, String.format("configureReconnectOnFailure(%s)", reconnect));
            }
        } catch (Exception e) {
            KayakoLogHelper.logException(TAG, e);
        }
    }

    /**
     * Check if the connection was successful. If true, then we can listen for events and call unsubscribe
     *
     * @return
     */
    public boolean isConnected() {
        return mIsConnected.get();
    }

    /**
     * Generate a url based on type of KreCredentials
     *
     * @param kreCredentials
     * @return
     */
    private String generateUrlFromKreCredentials(KreCredentials kreCredentials) {
        switch (kreCredentials.getType()) {
            case FINGERPRINT:
                KreFingerprintCredentials kreFingerprintCredentials = (KreFingerprintCredentials) kreCredentials;
                return generateUrl(
                        kreFingerprintCredentials.getRealtimeUrl(),
                        kreFingerprintCredentials.getInstanceUrl(),
                        kreFingerprintCredentials.getFingerprintId());

            case SESSION:
                KreSessionCredentials kreSessionCredentials = (KreSessionCredentials) kreCredentials;
                return generateUrl(kreSessionCredentials.getRealtimeUrl(),
                        kreSessionCredentials.getInstanceUrl(),
                        kreSessionCredentials.getSessionId(),
                        kreSessionCredentials.getUserAgent());

            default:
                throw new IllegalArgumentException("Invalid KRE Credentials passed!");
        }
    }

    /**
     * Generate a valid url from sessionId, instance and userAgent
     *
     * @param socketUrl
     * @param instanceUrl
     * @param sessionId
     * @param userAgent
     * @return
     */
    private String generateUrl(String socketUrl, String instanceUrl, String sessionId, String userAgent) {
        Uri uri = Uri.parse(String.format("%s%s", socketUrl, KRE_SOCKET_URL_SUFFIX))
                .buildUpon()
                .appendQueryParameter(PARAM_SESSION_ID, sessionId)
                .appendQueryParameter(PARAM_USER_AGENT, userAgent)
                .appendQueryParameter(PARAM_INSTANCE, extractInstanceFromInstanceUrl(instanceUrl)) // needs instance, not instanceUrl
                .appendQueryParameter(PARAM_VERSION, VERSION_NO)
                .build();
        return uri.toString();
    }

    /**
     * Generate a valid url from instanceUrl and fingerprintId
     *
     * @param socketUrl
     * @param instanceUrl
     * @param fingerprintId
     * @return
     */
    private String generateUrl(String socketUrl, String instanceUrl, String fingerprintId) {
        Uri uri = Uri.parse(String.format("%s%s", socketUrl, KRE_SOCKET_URL_SUFFIX))
                .buildUpon()
                .appendQueryParameter(PARAM_FINGERPRINT_ID, fingerprintId)
                .appendQueryParameter(PARAM_INSTANCE, extractInstanceFromInstanceUrl(instanceUrl)) // needs instance, not instanceUrl
                .appendQueryParameter(PARAM_VERSION, VERSION_NO)
                .build();

        return uri.toString();
    }

    /**
     * Extract the instance from the instance url. For eg, extract support.kayako.com from https://support.kayako.com
     *
     * @param instanceUrl
     * @return
     */
    private String extractInstanceFromInstanceUrl(String instanceUrl) {
        Uri uri = Uri.parse(instanceUrl);
        return uri.getHost();
    }

    public interface OnOpenSocketConnectionListener {
        void onOpen();

        void onError(String message);
    }

    public interface OnOpenConnectionListener {
        void onOpen(Channel channel);

        void onError(String message);
    }


    public interface OnCloseConnectionListener {
        void onClose();

        void onError(String message);
    }

}
