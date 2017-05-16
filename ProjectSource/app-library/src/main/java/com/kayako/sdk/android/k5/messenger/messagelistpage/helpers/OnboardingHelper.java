package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.BotMessageListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputEmailListItem;
import com.kayako.sdk.android.k5.core.Kayako;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * All logic involving onboarding items that ask for email
 */
public class OnboardingHelper {

    private boolean wasEmailAskedInThisConversation;

    public OnboardingHelper() {
    }

    public enum OnboardingState {
        ASK_FOR_EMAIL, PREFILLED_EMAIL, NONE
    }

    public OnboardingState getOnboardingState(boolean hasEmail, boolean wasNewConversation) {
        if (wasNewConversation && !hasEmail) { // New conversation (first time) where email is unknown
            return OnboardingState.ASK_FOR_EMAIL;
        } else if (wasNewConversation && wasEmailAskedInThisConversation) { // New conversation (first time) where email is now known (but was asked in this conversation)
            return OnboardingState.PREFILLED_EMAIL;
        } else { // New conversation (not the first time)
            return OnboardingState.NONE;
        }
    }

    public List<BaseListItem> generateOnboardingMessagesAskingForEmail(final InputEmailListItem.OnClickSubmitListener onClickSubmitListener) {
        if (onClickSubmitListener == null) {
            throw new IllegalArgumentException("Can not be null!");
        }

        List<BaseListItem> baseListItems = new ArrayList<>();
        baseListItems.add(new InputEmailListItem(onClickSubmitListener));

        // If onboarding messages asking for email is generated, safely assuming that email was asked in this conversation
        wasEmailAskedInThisConversation = true;

        return baseListItems;
    }

    public List<BaseListItem> generateOnboardingMessagesWithPrefilledEmail(String email) {
        if (email == null || email.length() == 0) {
            throw new IllegalArgumentException("Email can not be empty!");
        }
        List<BaseListItem> baseListItems = new ArrayList<>();
        baseListItems.add(new InputEmailListItem(email));
        baseListItems.add(new BotMessageListItem(Kayako.getApplicationContext().getString(R.string.ko__email_input_field_msg_you_will_be_notified), 0));
        return baseListItems;
    }

    public List<BaseListItem> generateOnboardingMessagesWithoutEmail() {
        return Collections.EMPTY_LIST; // If email is known, there's no need to show any bot message asking user to type something
    }

}
