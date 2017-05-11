package com.kayako.sdk.android.k5.messenger.data.conversationstarter;

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
    public synchronized void getConversationStarter(final OnLoadConversationStarterListener listener) {
        if (listener != null) {
            listeners.add(new WeakReference<>(listener));
        }
        conversationStarterRepository.getConversationStarter(this);
    }

    @Override
    public synchronized void clear() {
        if (conversationStarterRepository != null) {
            conversationStarterRepository.clear();
        }

        if (listeners != null) {
            listeners.clear();
        }
    }

    @Override
    public synchronized void onLoadConversationMetrics(final ConversationStarter conversationStarter) {
        List<WeakReference<OnLoadConversationStarterListener>> listenersToRemove = new ArrayList<>();
        for (WeakReference<OnLoadConversationStarterListener> weakReference : listeners) {
            if (weakReference.get() == null) {
                listenersToRemove.add(weakReference);
            } else {
                weakReference.get().onLoadConversationMetrics(conversationStarter);
            }
        }

        listeners.removeAll(listenersToRemove);
    }

    @Override
    public synchronized void onFailure(final KayakoException exception) {
        List<WeakReference<OnLoadConversationStarterListener>> listenersToRemove = new ArrayList<>();
        for (WeakReference<OnLoadConversationStarterListener> weakReference : listeners) {
            if (weakReference.get() == null) {
                listenersToRemove.add(weakReference);
            } else {
                weakReference.get().onFailure(exception);
            }
        }

        listeners.removeAll(listenersToRemove);
    }
}
