package com.kayako.sdk.android.k5.messenger.data;

import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.messenger.conversationstarter.ConversationStarter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ConversationStarterRepositoryManyListeners implements IConversationStarterRepository, IConversationStarterRepository.OnLoadConversationStarterListener {

    private ConversationStarterRepository conversationStarterRepository;
    private List<WeakReference<OnLoadConversationStarterListener>> listeners = new ArrayList<>();

    public ConversationStarterRepositoryManyListeners() {
        conversationStarterRepository = new ConversationStarterRepository();
    }

    @Override
    public synchronized void getConversationStarter(OnLoadConversationStarterListener listener) {
        listeners.add(new WeakReference<>(listener));
        conversationStarterRepository.getConversationStarter(this);
    }

    @Override
    public synchronized void onLoadConversationMetrics(ConversationStarter conversationStarter) {
        for (WeakReference<OnLoadConversationStarterListener> weakReference : listeners) {
            if (weakReference.get() == null) {
                listeners.remove(weakReference);
            } else {
                weakReference.get().onLoadConversationMetrics(conversationStarter);
            }
        }
    }

    @Override
    public synchronized void onFailure(KayakoException exception) {
        for (WeakReference<OnLoadConversationStarterListener> weakReference : listeners) {
            if (weakReference.get() == null) {
                listeners.remove(weakReference);
            } else {
                weakReference.get().onFailure(exception);
            }
        }
    }
}
