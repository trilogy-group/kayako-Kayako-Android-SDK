package com.kayako.sdk.android.k5.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kayako.sdk.android.k5.R;

public class KayakoSelectConversationActivity extends AppCompatActivity {

    public static final String ARG_CONVERSATION_ID = "conversation_id";

    public static Intent getIntent(Context context, long conversationId) {
        Intent intent = new Intent(context, KayakoSelectConversationActivity.class);
        intent.putExtra(ARG_CONVERSATION_ID, conversationId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ko__activity_message_listing);
    }
}
