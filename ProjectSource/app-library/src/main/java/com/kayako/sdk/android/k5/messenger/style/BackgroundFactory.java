package com.kayako.sdk.android.k5.messenger.style;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.messenger.style.type.Background;
import com.kayako.sdk.android.k5.messenger.style.type.Gradient;
import com.kayako.sdk.android.k5.messenger.style.type.SolidColor;

public class BackgroundFactory {

    private BackgroundFactory() {
    }

    public enum BackgroundOption {
        EGGPLANT,
        BEETROOT,
        PEACH,
        RAW_MANGO,
        GREEN_APPLE,
        AQUA,
        MIDNIGHT_BLUE,
        PURPLE,
        BLUE,
        TEAL,
        GREEN,
        YELLOW,
        ORANGE,
        RED
    }

    public static Background getBackground(BackgroundOption option) {
        switch (option) {
            case EGGPLANT:
                return new Gradient(R.drawable.ko__gradient_bg_1, true);

            case BEETROOT:
                return new Gradient(R.drawable.ko__gradient_bg_2, true);

            case PEACH:
                return new Gradient(R.drawable.ko__gradient_bg_3, false);

            case RAW_MANGO:
                return new Gradient(R.drawable.ko__gradient_bg_4, false);

            case GREEN_APPLE:
                return new Gradient(R.drawable.ko__gradient_bg_5, false);

            case AQUA:
                return new Gradient(R.drawable.ko__gradient_bg_6, false);

            case MIDNIGHT_BLUE:
                return new Gradient(R.drawable.ko__gradient_bg_7, true);

            case PURPLE: // blue?
                return new SolidColor("#5856D6", true);

            case BLUE:
                return new SolidColor("#007AFF", true);

            case TEAL:
                return new SolidColor("#5AC8FA", false);

            case GREEN:
                return new SolidColor("#4CD964", true);

            case YELLOW:
                return new SolidColor("#FFCC00", false);

            case ORANGE:
                return new SolidColor("#FF9500", true);

            case RED:
                return new SolidColor("#FF3B30", true);
        default:
            break;
        }

        return null;
    }

}
