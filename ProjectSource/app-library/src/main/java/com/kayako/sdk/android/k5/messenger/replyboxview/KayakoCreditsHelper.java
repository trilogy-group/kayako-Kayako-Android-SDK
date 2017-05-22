package com.kayako.sdk.android.k5.messenger.replyboxview;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.core.Kayako;

import java.util.Random;

public class KayakoCreditsHelper {

    private static int randomPick = new Random().nextInt(2); // generated ONCE when class is statically loaded so that credit is fixed for an app session

    public static String getPoweredByMessage() {
        switch (randomPick) {
            case 0: // Messenger by Kayako
                return Kayako.getApplicationContext().getString(R.string.ko__messenger_credits_messenger_by);

            case 1: // Live chat by Kayako
                return Kayako.getApplicationContext().getString(R.string.ko__messenger_credits_live_chat_by);

            default:
                throw new IllegalStateException("Unhandled case");
        }
    }
}
