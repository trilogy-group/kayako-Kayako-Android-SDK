package com.kayako.sdk.android.k5.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.kayako.sdk.android.k5.common.activities.BaseMessengerActivity;
import com.kayako.sdk.android.k5.messenger.conversationlistpage.ConversationListContainerFragment;

public class KayakoConversationListActivity extends BaseMessengerActivity {

    public static void startActivity(AppCompatActivity activity) {
        activity.startActivity(new Intent(activity, KayakoConversationListActivity.class), getAnimation(activity).toBundle());
    }

    @Override
    protected Fragment getContainerFragment() {
        return new ConversationListContainerFragment();
    }
}
