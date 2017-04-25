package com.kayako.sdk.android.k5.kre.base;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.utils.NetworkUtils;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.kre.base.credentials.KreCredentials;
import com.kayako.sdk.android.k5.kre.base.credentials.KreFingerprintCredentials;
import com.kayako.sdk.android.k5.kre.base.credentials.KreSessionCredentials;
import com.kayako.sdk.android.k5.kre.helpers.KreLogHelper;

import org.phoenixframework.channels.Channel;
import org.phoenixframework.channels.IErrorCallback;
import org.phoenixframework.channels.ISocketCloseCallback;
import org.phoenixframework.channels.ISocketOpenCallback;
import org.phoenixframework.channels.Socket;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

class KreConnection {

    private static final String TAG = "KreConnection";

    private static final String KRE_SOCKET_URL = "wss://kre.kayako.net/socket/websocket";
    private static final String PARAM_SESSION_ID = "session_id";
    private static final String PARAM_FINGERPRINT_ID = "fingerprint_id";
    private static final String PARAM_INSTANCE = "instance";
    private static final String PARAM_USER_AGENT = "user_agent";
    private static final String PARAM_VERSION = "vsn";
    private static final String VERSION_NO = "1.0.0";

    private Socket mSocket;
    private AtomicBoolean mIsConnected = new AtomicBoolean();

    /**
     * Used to connect and get a kre channel. The kre channel can be subscribed to and listened to for events.
     *
     * @param kreCredentials
     * @param channelName
     * @param listener
     */
    protected synchronized void connect(@NonNull KreCredentials kreCredentials, final @NonNull String channelName, @NonNull final OnOpenConnectionListener listener) {
        if (!NetworkUtils.isConnectedToNetwork(Kayako.getApplicationContext())) {
            KreLogHelper.e(TAG, "No Internet Connection! Not going forward with connect()");
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
                    KreLogHelper.e(TAG, "Non-Fatal: Crash has been prevented but intended behaviour no longer guaranteed.");
                    KreLogHelper.logException(TAG, e);
                }
            });

            mSocket
                    .onOpen(new ISocketOpenCallback() {
                        @Override
                        public void onOpen() {
                            Channel channel = mSocket.chan(channelName, null);
                            listener.onOpen(channel);
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
            KreLogHelper.printStackTrace(TAG, e); // Don't track IOExceptions on Crashlytics
        } catch (Throwable e) {
            KreLogHelper.logException(TAG, e);
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

                mIsConnected.set(false);
            } catch (IOException e) {
                KreLogHelper.printStackTrace(TAG, e); // Don't track IOExceptions on Crashlytics
            } catch (Throwable e) {
                KreLogHelper.logException(TAG, e);
            }
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
                return generateUrl(kreFingerprintCredentials.getInstanceUrl(),
                        kreFingerprintCredentials.getFingerprintId());

            case SESSION:
                KreSessionCredentials kreSessionCredentials = (KreSessionCredentials) kreCredentials;
                return generateUrl(kreSessionCredentials.getInstanceUrl(),
                        kreSessionCredentials.getSessionId(),
                        kreSessionCredentials.getUserAgent());

            default:
                throw new IllegalArgumentException("Invalid KRE Credentials passed!");
        }
    }

    /**
     * Generate a valid url from sessionId, instance and userAgent
     *
     * @param instanceUrl
     * @param sessionId
     * @param userAgent
     * @return
     */
    private String generateUrl(String instanceUrl, String sessionId, String userAgent) {
        Uri uri = Uri.parse(KRE_SOCKET_URL)
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
     * @param instanceUrl
     * @param fingerprintId
     * @return
     */
    private String generateUrl(String instanceUrl, String fingerprintId) {
        Uri uri = Uri.parse(KRE_SOCKET_URL)
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

    public interface OnOpenConnectionListener {
        void onOpen(Channel channel);

        void onError(String message);
    }

    public interface OnCloseConnectionListener {
        void onClose();

        void onError(String message);
    }

}
