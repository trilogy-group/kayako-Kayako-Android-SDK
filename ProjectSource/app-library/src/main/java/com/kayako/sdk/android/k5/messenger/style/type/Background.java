package com.kayako.sdk.android.k5.messenger.style.type;

import android.graphics.drawable.Drawable;

public abstract class Background {

    private BackgroundType type;

    public Background(BackgroundType type) {
        this.type = type;
    }

    abstract public boolean isDarkBackground();

    abstract public Drawable getBackgroundDrawable();

    public BackgroundType getType() {
        return type;
    }

    public enum BackgroundType {
        GRADIENT,
        SOLID_COLOR;
    }
}
