package com.kayako.sdk.android.k5.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.kayako.sdk.android.k5.common.activities.BaseMessengerActivity;
import com.kayako.sdk.android.k5.messenger.messagelistpage.MessageListContainerFragment;

public class KayakoSelectConversationActivity extends BaseMessengerActivity {

    public static final String ARG_CONVERSATION_ID = "conversation_id";

    /**
     * Use this constructor to start a new conversation
     *
     * @param context
     * @return
     */
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, KayakoSelectConversationActivity.class);
        return intent;
    }

    /**
     * Use this constructor to open an existing conversation
     *
     * @param context
     * @return
     */
    public static Intent getIntent(Context context, long conversationId) {
        Intent intent = new Intent(context, KayakoSelectConversationActivity.class);
        intent.putExtra(ARG_CONVERSATION_ID, conversationId);
        return intent;
    }

    @Override
    protected Fragment getContainerFragment() {
        return new MessageListContainerFragment();
    }
}
