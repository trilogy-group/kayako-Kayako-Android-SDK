package com.kayako.sdk.android.k5.messenger.style.type;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

public class SolidColor extends Background {

    private String hexColor;

    private boolean isDarkColor;

    public SolidColor(String hexColor, boolean isDarkColor) {
        super(BackgroundType.SOLID_COLOR);
        this.hexColor = hexColor;
        this.isDarkColor = isDarkColor;

        if (hexColor == null) {
            throw new IllegalArgumentException("HexColor can not be null");
        }
    }

    @Override
    public boolean isDarkBackground() {
        return isDarkColor;
    }

    @Override
    public Drawable getBackgroundDrawable() {
        return new ColorDrawable(Color.parseColor(hexColor));
    }
}
