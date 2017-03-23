package com.kayako.sdk.android.k5.messenger.style;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.messenger.style.type.Background;
import com.kayako.sdk.android.k5.messenger.style.type.Gradient;
import com.kayako.sdk.android.k5.messenger.style.type.SolidColor;

public class BackgroundFactory {

    private BackgroundFactory() {
    }

    public enum BackgroundOption {
        GRADIENT_1,
        GRADIENT_2,
        GRADIENT_3,
        GRADIENT_4,
        GRADIENT_5,
        GRADIENT_6,
        GRADIENT_7,
        SOLID_COLOR_1,
        SOLID_COLOR_2,
        SOLID_COLOR_3,
        SOLID_COLOR_4,
        SOLID_COLOR_5,
        SOLID_COLOR_6,
        SOLID_COLOR_7,
    }

    public static Background getBackground(BackgroundOption option) {
        switch (option) {
            case GRADIENT_1:
                return new Gradient(R.drawable.ko__gradient_bg_1, true);

            case GRADIENT_2:
                return new Gradient(R.drawable.ko__gradient_bg_2, true);

            case GRADIENT_3:
                return new Gradient(R.drawable.ko__gradient_bg_3, false);

            case GRADIENT_4:
                return new Gradient(R.drawable.ko__gradient_bg_4, false);

            case GRADIENT_5:
                return new Gradient(R.drawable.ko__gradient_bg_5, false);

            case GRADIENT_6:
                return new Gradient(R.drawable.ko__gradient_bg_6, false);

            case GRADIENT_7:
                return new Gradient(R.drawable.ko__gradient_bg_7, true);

            case SOLID_COLOR_1:
                return new SolidColor("#5856D6", true);

            case SOLID_COLOR_2:
                return new SolidColor("#007AFF", true);

            case SOLID_COLOR_3:
                return new SolidColor("#5AC8FA", false);

            case SOLID_COLOR_4:
                return new SolidColor("#4CD964", true);

            case SOLID_COLOR_5:
                return new SolidColor("#FC0", false);

            case SOLID_COLOR_6:
                return new SolidColor("#FF9500", true);

            case SOLID_COLOR_7:
                return new SolidColor("#FF3B30", true);
        }

        return null;
    }

}
