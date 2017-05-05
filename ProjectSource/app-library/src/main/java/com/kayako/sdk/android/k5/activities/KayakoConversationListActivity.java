package com.kayako.sdk.android.k5.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.activities.BaseMessengerActivity;
import com.kayako.sdk.android.k5.messenger.conversationlistpage.ConversationListContainerFragment;

public class KayakoConversationListActivity extends BaseMessengerActivity {

    public static Intent getIntent(Context context) {
        return new Intent(context, KayakoConversationListActivity.class);
    }

    @Override
    protected Fragment getContainerFragment() {
        return new ConversationListContainerFragment();
    }
}
