package com.kayako.sdk.android.k5.messenger.style.type;

import android.graphics.drawable.Drawable;

import com.kayako.sdk.android.k5.core.Kayako;

public class Gradient extends Background {

    private boolean isDarkBackground;
    private int backgroundResDrawable;

    public Gradient(int backgroundResDrawable, boolean isDarkBackground) {
        super(BackgroundType.GRADIENT);
        this.isDarkBackground = isDarkBackground;
        this.backgroundResDrawable = backgroundResDrawable;
    }

    @Override
    public boolean isDarkBackground() {
        return isDarkBackground;
    }

    @Override
    public Drawable getBackgroundDrawable() {
        return Kayako.getApplicationContext().getResources().getDrawable(backgroundResDrawable);
    }
}
