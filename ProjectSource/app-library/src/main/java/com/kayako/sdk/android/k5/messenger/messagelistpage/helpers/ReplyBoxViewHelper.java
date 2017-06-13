package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.fragments.ListPageState;
import com.kayako.sdk.android.k5.core.Kayako;

/**
 * All logic for showing and hiding reply box should be in this class
 */
public class ReplyBoxViewHelper {

    public ReplyBoxViewHelper() {
    }


    private ReplyBoxViewState lastSetReplyBoxViewState;

    public void setLastSetReplyBoxViewState(ReplyBoxViewState lastSetReplyBoxViewState) {
        this.lastSetReplyBoxViewState = lastSetReplyBoxViewState;
    }

    public ReplyBoxViewState getLastSetReplyBoxViewState() {
        return lastSetReplyBoxViewState;
    }

    /**
     * Anywhere on this class, by calling one method, configureReplyBoxVisibility(), the reply box will make the necessary checks to alter it's visibility
     */
    public ReplyBoxViewState getReplyBoxVisibility(boolean isNewConversation,
                                                   boolean hasEmail,
                                                   boolean isConversationClosed,
                                                   @Nullable ListPageState listPageState) {
        if (listPageState != null) {
            switch (listPageState) {
                case LIST:
                    if (isNewConversation) { // Starting a new Conversation
                        if (hasEmail) { // Email step is yet to be entered (Reply box shouldn't be shown until the email is known
                            return ReplyBoxViewState.VISIBLE;
                        } else {
                            return ReplyBoxViewState.HIDDEN;
                        }
                    } else { // Existing Conversation
                        if (isConversationClosed) {
                            return ReplyBoxViewState.HIDDEN;
                        } else {
                            return ReplyBoxViewState.VISIBLE;
                        }
                    }

                default:
                case EMPTY:
                    return ReplyBoxViewState.VISIBLE;

                case ERROR:
                case LOADING:
                case NONE:
                    return ReplyBoxViewState.HIDDEN;
            }
        } else {
            return ReplyBoxViewState.HIDDEN;
        }
    }

    public String getReplyBoxHintMessage(boolean isNewConversation, @NonNull String brandName, @Nullable String agentName) {
        if (brandName == null || brandName.length() == 0) {
            throw new IllegalArgumentException("Invalid State - can't be null");
        }

        if (isNewConversation) {
            return Kayako.getApplicationContext().getString(R.string.ko__messenger_reply_box_hint_new_conversation);

        } else if (agentName != null) {
            return String.format(
                    Kayako.getApplicationContext().getString(R.string.ko__messenger_reply_box_hint_to_agent),
                    getFirstName(agentName));
            
        } else {
            return String.format(Kayako.getApplicationContext().getString(R.string.ko__messenger_reply_box_hint_to_brand), brandName);
        }
    }

    private String getFirstName(String agentName) {
        if (agentName == null || agentName.length() == 0) {
            throw new IllegalArgumentException("Invalid State - can't be null");
        }

        String[] words = agentName.split(" ");
        return words[0];
    }

    public enum ReplyBoxViewState {
        DISABLED, HIDDEN, VISIBLE
    }
}
