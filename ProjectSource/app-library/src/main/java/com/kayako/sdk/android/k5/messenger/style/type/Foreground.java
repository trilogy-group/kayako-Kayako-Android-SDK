package com.kayako.sdk.android.k5.messenger.style.type;

import android.graphics.drawable.Drawable;

public abstract class Foreground {

    private ForegroundType type;

    public Foreground(ForegroundType type) {
        this.type = type;
    }

    public ForegroundType getType() {
        return type;
    }

    public abstract Drawable getDrawableForDarkBackground();

    public abstract Drawable getDrawableForLightBackground();

    public enum ForegroundType {
        NONE,
        TEXTURE
    }
}
