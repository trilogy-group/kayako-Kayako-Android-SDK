package com.kayako.sdk.android.k5.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.activities.BaseMessengerActivity;
import com.kayako.sdk.android.k5.messenger.messagelistpage.MessageListContainerFragment;

public class KayakoSelectConversationActivity extends BaseMessengerActivity {

    public static final String ARG_CONVERSATION_ID = "conversation_id";

    public static void startActivity(AppCompatActivity activity) {
        activity.startActivity(new Intent(activity, KayakoSelectConversationActivity.class));
        overridePendingTransitionEnter(activity);
    }

    public static void startActivity(AppCompatActivity activity, long conversationId) {
        Intent intent = new Intent(activity, KayakoSelectConversationActivity.class);
        intent.putExtra(ARG_CONVERSATION_ID, conversationId);

        activity.startActivity(intent);
        overridePendingTransitionEnter(activity);
    }

    public static void startActivityForResult(AppCompatActivity activity, Fragment fragment, int requestCode) {
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(activity, R.anim.slide_from_right, R.anim.slide_to_left);
        fragment.startActivityForResult(new Intent(activity, KayakoSelectConversationActivity.class), requestCode, activityOptionsCompat.toBundle());
    }

    public static void startActivityForResult(Activity activity, Fragment fragment, long conversationId, int requestCode) {
        Intent intent = new Intent(activity, KayakoSelectConversationActivity.class);
        intent.putExtra(ARG_CONVERSATION_ID, conversationId);

        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(activity, R.anim.slide_from_right, R.anim.slide_to_left);
        fragment.startActivityForResult(intent, requestCode, activityOptionsCompat.toBundle());
    }

    @Override
    protected Fragment getContainerFragment() {
        return new MessageListContainerFragment();
    }
}
