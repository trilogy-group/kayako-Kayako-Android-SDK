package com.kayako.sdk.android.k5.messenger.style.type;

import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

import com.kayako.sdk.android.k5.core.Kayako;

public class Texture extends Foreground {

    @DrawableRes
    private int drawableResIdForDarkBackgrounds;

    @DrawableRes
    private int drawableResIdForLightBackgrounds;

    /**
     * Create texture
     *
     * @param drawableResIdForDarkBackgrounds
     * @param drawableResIdForLightBackgrounds
     */
    public Texture(@DrawableRes int drawableResIdForDarkBackgrounds, @DrawableRes int drawableResIdForLightBackgrounds) {
        super(ForegroundType.TEXTURE);
        this.drawableResIdForDarkBackgrounds = drawableResIdForDarkBackgrounds;
        this.drawableResIdForLightBackgrounds = drawableResIdForLightBackgrounds;
    }

    @Override
    public Drawable getDrawableForDarkBackground() {
        return getAsTiledBackground(drawableResIdForDarkBackgrounds);
    }

    @Override
    public Drawable getDrawableForLightBackground() {
        return getAsTiledBackground(drawableResIdForLightBackgrounds);
    }

    private Drawable getAsTiledBackground(int drawableResId) {
        // Programmatically create the repeating background effect
        BitmapDrawable bitmapDrawable = (BitmapDrawable) Kayako.getApplicationContext().getResources().getDrawable(drawableResId);
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        return bitmapDrawable;
    }
}
