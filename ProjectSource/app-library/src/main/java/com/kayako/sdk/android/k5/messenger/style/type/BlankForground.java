package com.kayako.sdk.android.k5.messenger.style.type;

import android.graphics.drawable.Drawable;

public class BlankForground extends Foreground {

    public BlankForground() {
        super(ForegroundType.NONE);
    }

    @Override
    public Drawable getDrawableForDarkBackground() {
        return null;
    }

    @Override
    public Drawable getDrawableForLightBackground() {
        return null;
    }
}
