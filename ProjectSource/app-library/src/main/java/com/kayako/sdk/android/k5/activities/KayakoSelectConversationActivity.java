package com.kayako.sdk.android.k5.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.activities.BaseMessengerActivity;
import com.kayako.sdk.android.k5.messenger.messagelistpage.MessageListContainerFragment;

public class KayakoSelectConversationActivity extends BaseMessengerActivity {

    public static final String ARG_CONVERSATION_ID = "conversation_id";

    public static void startActivity(AppCompatActivity activity) {
        ActivityOptionsCompat activityOptionsCompat = getAnimation(activity);
        activity.startActivity(new Intent(activity, KayakoSelectConversationActivity.class), activityOptionsCompat.toBundle());
    }

    public static void startActivity(AppCompatActivity activity, long conversationId) {
        Intent intent = new Intent(activity, KayakoSelectConversationActivity.class);
        intent.putExtra(ARG_CONVERSATION_ID, conversationId);

        ActivityOptionsCompat activityOptionsCompat = getAnimation(activity);
        activity.startActivity(intent, activityOptionsCompat.toBundle());
    }

    public static void startActivityForResult(AppCompatActivity activity, Fragment fragment, int requestCode) {
        ActivityOptionsCompat activityOptionsCompat = getAnimation(activity);
        fragment.startActivityForResult(new Intent(activity, KayakoSelectConversationActivity.class), requestCode, activityOptionsCompat.toBundle());
    }

    public static void startActivityForResult(Activity activity, Fragment fragment, long conversationId, int requestCode) {
        Intent intent = new Intent(activity, KayakoSelectConversationActivity.class);
        intent.putExtra(ARG_CONVERSATION_ID, conversationId);

        ActivityOptionsCompat activityOptionsCompat = getAnimation(activity);
        fragment.startActivityForResult(intent, requestCode, activityOptionsCompat.toBundle());
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected Fragment getContainerFragment() {
        return new MessageListContainerFragment();
    }
}
