package com.kayako.sdk.android.k5.messenger.style;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.messenger.style.type.BlankForground;
import com.kayako.sdk.android.k5.messenger.style.type.Foreground;
import com.kayako.sdk.android.k5.messenger.style.type.Texture;

public class ForegroundFactory {

    private ForegroundFactory() {
    }

    public enum ForegroundOption {
        NONE,
        CONFETTI_TEXTURE,
        DOTS_TEXTURE,
        CONSTELLATION_TEXTURE,
        SAND_TEXTURE,
        CHEERIOS_TEXTURE,
        MOSAIC_TEXTURE,
        POLKA_TEXTURE,
        STARS_TEXTURE,
        NACHOS_TEXTURE,
        ZIGZAG_TEXTURE
    }


    public static Foreground getForeground(ForegroundOption foregroundOption) {
        if (foregroundOption == null) {
            return null;
        }

        switch (foregroundOption) {
            case NONE:
                return new BlankForground();

            case CONFETTI_TEXTURE:
                return new Texture(R.drawable.ko__texture_1_dark, R.drawable.ko__texture_1_light);

            case DOTS_TEXTURE:
                return new Texture(R.drawable.ko__texture_2_dark, R.drawable.ko__texture_2_light);

            case CONSTELLATION_TEXTURE:
                return new Texture(R.drawable.ko__texture_3_dark, R.drawable.ko__texture_3_light);

            case SAND_TEXTURE:
                return new Texture(R.drawable.ko__texture_4_dark, R.drawable.ko__texture_4_light);

            case CHEERIOS_TEXTURE:
                return new Texture(R.drawable.ko__texture_5_dark, R.drawable.ko__texture_5_light);

            case MOSAIC_TEXTURE:
                return new Texture(R.drawable.ko__texture_6_dark, R.drawable.ko__texture_6_light);

            case POLKA_TEXTURE:
                return new Texture(R.drawable.ko__texture_7_dark, R.drawable.ko__texture_7_light);

            case STARS_TEXTURE:
                return new Texture(R.drawable.ko__texture_8_dark, R.drawable.ko__texture_8_light);

            case NACHOS_TEXTURE:
                return new Texture(R.drawable.ko__texture_9_dark, R.drawable.ko__texture_9_light);

            case ZIGZAG_TEXTURE:
                return new Texture(R.drawable.ko__texture_10_dark, R.drawable.ko__texture_10_light);

        }

        return null;
    }
}
