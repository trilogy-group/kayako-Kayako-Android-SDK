package com.kayako.sdk.android.k5.kre.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fasterxml.jackson.databind.JsonNode;
import com.kayako.sdk.android.k5.core.KayakoLogHelper;
import com.kayako.sdk.android.k5.kre.base.credentials.KreCredentials;

import org.phoenixframework.channels.Channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class KreConnection extends SocketConnection {

    private static final String TAG = "KreConnection";

    private OnOpenSocketConnectionListener mMainListener;

    private Set<OnOpenConnectionListener> mListeners = new HashSet<>();
    private Map<OnOpenConnectionListener, String> mChannelMap = new HashMap<>();

    private AtomicBoolean mIsConnecting = new AtomicBoolean();

    /**
     * Used to connect and get a kre channel. The kre channel can be subscribed to and listened to for events.
     *
     * @param kreCredentials
     * @param listener
     */
    public synchronized void connect(@NonNull KreCredentials kreCredentials, final @NonNull String channelName, @NonNull final KreConnection.OnOpenConnectionListener listener) {
        connect(kreCredentials, channelName, listener, null);
    }

    /**
     * Used to connect and get a kre channel. The kre channel can be subscribed to and listened to for events.
     * <p>
     * Also, supports parameter jsonPayload when subscribing to a channel.
     *
     * @param kreCredentials
     * @param channelName
     * @param openConnectionListener
     * @param jsonPayload
     */
    public synchronized void connect(@NonNull final KreCredentials kreCredentials, @NonNull String channelName, @NonNull final KreConnection.OnOpenConnectionListener openConnectionListener, @Nullable final JsonNode jsonPayload) {
        if (channelName == null || openConnectionListener == null) {
            throw new IllegalArgumentException();
        }

        if (mListeners.contains(openConnectionListener)) {
            return;
        }

        mListeners.add(openConnectionListener);
        mChannelMap.put(openConnectionListener, channelName);

        KayakoLogHelper.d(TAG, String.format("connect(), No. of Listeners %s", mListeners.size()));
        if (isConnected()) {
            Channel channel = mSocket.chan(channelName, jsonPayload);
            openConnectionListener.onOpen(channel);
        } else if (!mIsConnecting.get()) {
            mIsConnecting.set(true);
            KayakoLogHelper.d(TAG, "connect(), MAIN");
            super.connect(kreCredentials, mMainListener = new OnOpenSocketConnectionListener() {
                @Override
                public synchronized void onOpen() {
                    mIsConnecting.set(false);
                    KayakoLogHelper.d(TAG, String.format("onOpen(), No. of Listeners %s", mListeners.size()));
                    for (OnOpenConnectionListener listener : mListeners) {
                        String channelName = mChannelMap.get(listener);
                        Channel channel = mSocket.chan(channelName, jsonPayload);
                        listener.onOpen(channel);
                    }
                }

                @Override
                public synchronized void onError(String message) {
                    mIsConnecting.set(false);
                    KayakoLogHelper.d(TAG, String.format("onError(), No. of Listeners %s", mListeners.size()));
                    for (OnOpenConnectionListener listener : mListeners) {
                        listener.onError(message);
                    }
                }
            }, jsonPayload);
        } else { // not connected but connecting
            // wait for the connection, after which all added listeners will be executed
        }
    }

    public synchronized void disconnect(@NonNull final KreConnection.OnOpenConnectionListener openConnectionListener) {
        if (openConnectionListener == null) {
            throw new IllegalArgumentException();
        }

        mListeners.remove(openConnectionListener);
        mChannelMap.remove(openConnectionListener);

        KayakoLogHelper.d(TAG, String.format("disconnect(), No. of Listeners %s", mListeners.size()));
        if (mListeners.size() == 0) {
            super.disconnect(null);
            mListeners = new HashSet<>();
            mChannelMap = new HashMap<>();
            mMainListener = null;
            KayakoLogHelper.d(TAG, String.format("disconnect(), FINAL", mListeners.size()));
        }
    }

    public void configureReconnectOnFailure(boolean reconnect) {
        super.configureReconnectOnFailure(reconnect);
    }

    public boolean isConnected() {
        return super.isConnected();
    }
}
