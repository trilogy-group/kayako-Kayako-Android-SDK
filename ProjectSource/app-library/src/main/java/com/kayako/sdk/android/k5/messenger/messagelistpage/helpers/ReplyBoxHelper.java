package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.common.fragments.ListPageState;

/**
 * All logic for showing and hiding reply box should be in this class
 */
public class ReplyBoxHelper {

    public ReplyBoxHelper() {
    }

    public enum ReplyBoxViewState {
        DISABLED, HIDDEN, VISIBLE
    }

    /**
     * Anywhere on this class, by calling one method, configureReplyBoxVisibility(), the reply box will make the necessary checks to alter it's visibility
     */
    public ReplyBoxViewState getReplyBoxVisibility(boolean isNewConversation, boolean hasEmail, @Nullable ListPageState listPageState) {
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
                        return ReplyBoxViewState.VISIBLE;
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
}