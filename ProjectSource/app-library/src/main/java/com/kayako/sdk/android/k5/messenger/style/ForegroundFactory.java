package com.kayako.sdk.android.k5.messenger.style;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.messenger.style.type.BlankForground;
import com.kayako.sdk.android.k5.messenger.style.type.Foreground;
import com.kayako.sdk.android.k5.messenger.style.type.Texture;

public class ForegroundFactory {

    private ForegroundFactory() {
    }

    public enum ForegroundOption {
        NO_TEXTURE,
        CONFETTI,
        DOTS,
        CONSTELLATION,
        SAND,
        CHEERIOS,
        MOSAIC,
        POLKA,
        STARS,
        NACHOS,
        ZIGZAG
    }


    public static Foreground getForeground(ForegroundOption foregroundOption) {
        if (foregroundOption == null) {
            return null;
        }

        switch (foregroundOption) {
            case NO_TEXTURE:
                return new BlankForground();

            case CONFETTI:
                return new Texture(R.drawable.ko__texture_1_dark, R.drawable.ko__texture_1_light);

            case DOTS:
                return new Texture(R.drawable.ko__texture_2_dark, R.drawable.ko__texture_2_light);

            case CONSTELLATION:
                return new Texture(R.drawable.ko__texture_3_dark, R.drawable.ko__texture_3_light);

            case SAND:
                return new Texture(R.drawable.ko__texture_4_dark, R.drawable.ko__texture_4_light);

            case CHEERIOS:
                return new Texture(R.drawable.ko__texture_5_dark, R.drawable.ko__texture_5_light);

            case MOSAIC:
                return new Texture(R.drawable.ko__texture_6_dark, R.drawable.ko__texture_6_light);

            case POLKA:
                return new Texture(R.drawable.ko__texture_7_dark, R.drawable.ko__texture_7_light);

            case STARS:
                return new Texture(R.drawable.ko__texture_8_dark, R.drawable.ko__texture_8_light);

            case NACHOS:
                return new Texture(R.drawable.ko__texture_9_dark, R.drawable.ko__texture_9_light);

            case ZIGZAG:
                return new Texture(R.drawable.ko__texture_10_dark, R.drawable.ko__texture_10_light);
        default:
            break;

        }

        return null;
    }
}
