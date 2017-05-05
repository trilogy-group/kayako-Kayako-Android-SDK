package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;

public class FadedBackgroundHelper {

    public static void setBackgroundColorForOptimisticSending(@NonNull TextView messageView, boolean fadeBackground) {
        // This method was designed to only be called for SELF message!
        MessageStyleHelper.setSelfMessageStyle(messageView);

        if (fadeBackground) {
            messageView.setBackgroundResource(R.drawable.ko__speech_bubble_self_optimistic_sending);
        } else {
            messageView.setBackgroundResource(R.drawable.ko__speech_bubble_self);
        }
    }

}
