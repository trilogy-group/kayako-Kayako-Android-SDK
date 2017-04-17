package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.TypingListItem;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Note: This helper class assumes that there is only only agent typing at a time.
 * The views and logic have been designed accordingly.
 */
public class TypingViewHelper {

    private UserViewModel lastAgentTyping;
    private AtomicBoolean isAgentTyping = new AtomicBoolean(false);

    public TypingViewHelper() {
    }

    public boolean setTypingStatus(UserViewModel lastAgentTyping, boolean isAgentTyping) {
        if (this.isAgentTyping.get() == isAgentTyping) {
            return false; // To prevent multiple UI revisions for the same info
        }

        this.isAgentTyping.set(isAgentTyping);
        this.lastAgentTyping = lastAgentTyping;
        return true;
    }

    public List<BaseListItem> getTypingViews() {
        if (isAgentTyping.get() && lastAgentTyping != null) {
            List<BaseListItem> viewItems = new ArrayList<>();
            viewItems.add(new TypingListItem(null, lastAgentTyping.getAvatar(), null));
            return viewItems;
        } else {
            return Collections.EMPTY_LIST;
        }
    }

}
