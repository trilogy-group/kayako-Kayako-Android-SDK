package com.kayako.sdk.android.k5.messenger.style.type;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;

import com.kayako.sdk.android.k5.core.Kayako;

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

        // Conversion is done in the constructor so that if it fails, the developer is informed at the time of creation
        Color.parseColor(hexColor);
    }

    public SolidColor(@ColorRes int colorResId, boolean isDarkColor) {
        super(BackgroundType.SOLID_COLOR);
        this.hexColor = String.format("#%X", ContextCompat.getColor(Kayako.getApplicationContext(), colorResId));
        this.isDarkColor = isDarkColor;

        if (hexColor == null) {
            throw new IllegalArgumentException("HexColor can not be null");
        }

        // Conversion is done in the constructor so that if it fails, the developer is informed at the time of creation
        Color.parseColor(hexColor);
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
