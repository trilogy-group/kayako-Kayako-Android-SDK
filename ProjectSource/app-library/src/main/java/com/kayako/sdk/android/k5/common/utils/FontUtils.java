package com.kayako.sdk.android.k5.common.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import java.util.HashMap;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class FontUtils {

    private static HashMap<FontStyle, Typeface> fontCache = new HashMap<>();

    public enum FontStyle {
        LIGHT,
        REGULAR,
        SEMIBOLD
    }

    public static void applyFont(Context context, TextView view, FontStyle fontStyle) {
        Typeface tf = getTypeface(context, fontStyle);
        if (tf != null) {
            view.setTypeface(tf);
        }
    }

    private static Typeface getTypeface(Context context, FontStyle fontStyle) {
        Typeface typeface = fontCache.get(fontStyle);

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), getFontPath(fontStyle));
            } catch (Exception e) {
                return null;
            }

            fontCache.put(fontStyle, typeface);
        }

        return typeface;
    }

    private static String getFontPath(FontStyle fontStyle) {
        switch (fontStyle) {
            case LIGHT:
                return "fonts/SourceSansPro-Light.otf";
            case SEMIBOLD:
                return "SourceSansPro-Semibold.otf";
            default:
                return "SourceSansPro-Regular.otf";
        }
    }

}
