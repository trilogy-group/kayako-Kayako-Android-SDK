package com.kayako.sdk.android.k5.kre.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fasterxml.jackson.databind.JsonNode;
import com.kayako.sdk.android.k5.kre.base.credentials.KreCredentials;

import org.phoenixframework.channels.Channel;

import java.util.ArrayList;
import java.util.List;

public class KreConnection extends SocketConnection {

    private OnOpenSocketConnectionListener mMainListener;
    private List<OnOpenConnectionListener> mListeners = new ArrayList<>();

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
    public synchronized void connect(@NonNull final KreCredentials kreCredentials, final @NonNull String channelName, @NonNull final KreConnection.OnOpenConnectionListener openConnectionListener, @Nullable final JsonNode jsonPayload) {
        if (channelName == null || openConnectionListener == null) {
            throw new IllegalArgumentException();
        }

        mListeners.add(openConnectionListener);
        if (isConnected() && mSocket != null) {
            Channel channel = mSocket.chan(channelName, jsonPayload);
            openConnectionListener.onOpen(channel);
        } else {
            if (mMainListener == null) {
                super.connect(kreCredentials, mMainListener = new OnOpenSocketConnectionListener() {
                    @Override
                    public synchronized void onOpen() {
                        for (OnOpenConnectionListener listener : mListeners) {
                            Channel channel = mSocket.chan(channelName, jsonPayload);
                            listener.onOpen(channel);
                        }
                    }

                    @Override
                    public synchronized void onError(String message) {
                        for (OnOpenConnectionListener listener : mListeners) {
                            listener.onError(message);
                        }
                    }
                }, jsonPayload);
            }
        }
    }

    public synchronized void disconnect(@NonNull final KreConnection.OnOpenConnectionListener openConnectionListener) {
        if (openConnectionListener == null) {
            throw new IllegalArgumentException();
        }

        mListeners.remove(openConnectionListener);
        if (mListeners.size() == 0) {
            super.disconnect(null);
            mListeners = new ArrayList<>();
            mMainListener = null;
        }
    }

    public void configureReconnectOnFailure(boolean reconnect) {
        super.configureReconnectOnFailure(reconnect);
    }

    public boolean isConnected() {
        return super.isConnected();
    }
}
