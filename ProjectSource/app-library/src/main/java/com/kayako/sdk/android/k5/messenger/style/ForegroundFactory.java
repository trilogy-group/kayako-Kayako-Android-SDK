package com.kayako.sdk.android.k5.messenger.style;

import android.graphics.drawable.Drawable;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.messenger.style.type.BlankForground;
import com.kayako.sdk.android.k5.messenger.style.type.Foreground;
import com.kayako.sdk.android.k5.messenger.style.type.Texture;

public class ForegroundFactory {

    private ForegroundFactory() {
    }

    public enum ForegroundOption {
        NONE,
        TEXTURE_1,
        TEXTURE_2,
        TEXTURE_3,
        TEXTURE_4,
        TEXTURE_5,
        TEXTURE_6,
        TEXTURE_7,
        TEXTURE_8,
        TEXTURE_9,
        TEXTURE_10
    }


    public static Foreground getForeground(ForegroundOption foregroundOption) {
        if (foregroundOption == null) {
            return null;
        }

        switch (foregroundOption) {
            case NONE:
                return new BlankForground();

            case TEXTURE_1:
                return new Texture(R.drawable.ko__texture_1_dark, R.drawable.ko__texture_1_light);

            case TEXTURE_2:
                return new Texture(R.drawable.ko__texture_2_dark, R.drawable.ko__texture_2_light);

            case TEXTURE_3:
                return new Texture(R.drawable.ko__texture_3_dark, R.drawable.ko__texture_3_light);

            case TEXTURE_4:
                return new Texture(R.drawable.ko__texture_4_dark, R.drawable.ko__texture_4_light);

            case TEXTURE_5:
                return new Texture(R.drawable.ko__texture_5_dark, R.drawable.ko__texture_5_light);

            case TEXTURE_6:
                return new Texture(R.drawable.ko__texture_6_dark, R.drawable.ko__texture_6_light);

            case TEXTURE_7:
                return new Texture(R.drawable.ko__texture_7_dark, R.drawable.ko__texture_7_light);

            case TEXTURE_8:
                return new Texture(R.drawable.ko__texture_8_dark, R.drawable.ko__texture_8_light);

            case TEXTURE_9:
                return new Texture(R.drawable.ko__texture_9_dark, R.drawable.ko__texture_9_light);

            case TEXTURE_10:
                return new Texture(R.drawable.ko__texture_10_dark, R.drawable.ko__texture_10_light);

        }

        return null;
    }
}
