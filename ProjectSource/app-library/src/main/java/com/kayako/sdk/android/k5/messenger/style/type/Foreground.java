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

    abstract public Drawable getDrawableForDarkBackground();

    abstract public Drawable getDrawableForLightBackground();

    public enum ForegroundType {
        NONE,
        TEXTURE
    }
}
