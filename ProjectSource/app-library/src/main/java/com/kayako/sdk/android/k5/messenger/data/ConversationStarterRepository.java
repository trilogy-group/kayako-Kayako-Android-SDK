package com.kayako.sdk.android.k5.messenger.data;

import android.os.Handler;

import com.kayako.sdk.android.k5.core.HelpCenterPref;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.auth.FingerprintAuth;
import com.kayako.sdk.base.callback.ItemCallback;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.messenger.Messenger;
import com.kayako.sdk.messenger.conversationstarter.ConversationStarter;

public class ConversationStarterRepository implements IConversationStarterRepository {

    private Messenger mMessenger;
    private ConversationStarter mConversationStarter; // cached in memory since it's used throughout app

    public ConversationStarterRepository() {
        String fingerprintId = MessengerPref.getInstance().getFingerprintId();
        if (fingerprintId != null && fingerprintId.length() == 0) {
            FingerprintAuth fingerprintAuth = new FingerprintAuth(fingerprintId);
            mMessenger = new Messenger(HelpCenterPref.getInstance().getHelpCenterUrl(), fingerprintAuth);
        } else {
            mMessenger = new Messenger(HelpCenterPref.getInstance().getHelpCenterUrl());
        }
    }

    @Override
    public void getConversationStarter(final IConversationStarterRepository.OnLoadConversationStarterListener listener) {
        final Handler handler = new Handler();
        // If cached available, load it!
        if (mConversationStarter != null && listener != null) {
            listener.onLoadConversationMetrics(mConversationStarter);
        }

        mMessenger.getConversationStarter(new ItemCallback<ConversationStarter>() {
            @Override
            public void onSuccess(final ConversationStarter item) {
                mConversationStarter = item;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (listener != null) {
                            listener.onLoadConversationMetrics(item);
                        }
                    }
                });
            }

            @Override
            public void onFailure(final KayakoException exception) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (listener != null) {
                            listener.onFailure(exception);
                        }
                    }
                });
            }
        });
    }
}
