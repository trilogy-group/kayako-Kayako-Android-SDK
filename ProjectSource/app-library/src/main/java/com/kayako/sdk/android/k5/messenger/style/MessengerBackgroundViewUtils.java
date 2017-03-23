package com.kayako.sdk.android.k5.messenger.style;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.View;

import com.kayako.sdk.android.k5.R;

/**
 * This ViewUtils class should be used for any layout that includes "ko__include_messenger_custom_background.xml"
 */
public class MessengerBackgroundViewUtils {

    private MessengerBackgroundViewUtils() {
    }

    private static final int RESOURCE_ID_BACKGROUND = R.id.ko__messenger_custom_background;
    private static final int RESOURCE_ID_FOREGROUND = R.id.ko__messenger_custom_foreground;

    public static void setForeground(View root, Drawable drawable) {
        if (root == null) {
            throw new IllegalArgumentException("Can not be null!");
        }

        if (drawable == null) {
            setBackgroundDrawable(root.findViewById(RESOURCE_ID_FOREGROUND), null); // remove foreground
        } else {
            // Set the customized drawable
            setBackgroundDrawable(root.findViewById(RESOURCE_ID_FOREGROUND), drawable); // set new foreground
        }

        /* NOTE:
            Choosing to use a viewgroup parent and view child instead of using the setForeground and setBackground view methods. Why?
            - This is done as a precaution because setForeground() should support lower Android API versions, but currently demands API Version 23 according to lint errors
            - Official Documentation says it's supported despite above error: https://developer.android.com/reference/android/view/View.html#setForeground(android.graphics.drawable.Drawable)
            - However, on emulators using lower versions, app crashes with the following: java.lang.NoSuchMethodError: No virtual method setForeground(Landroid/graphics/drawable/Drawable;)V in class Landroid/view/View; or its super classes (declaration of 'android.view.View' appears in /system/framework/framework.jar:classes2.dex)
         */
    }

    public static void setBackground(View root, Drawable drawable) {
        if (drawable == null || root == null) {
            throw new IllegalArgumentException("Can not be null!");
        }

        setBackgroundDrawable(root.findViewById(RESOURCE_ID_BACKGROUND), drawable);
    }

    private static void setBackgroundDrawable(View backgroundView, @Nullable Drawable drawable) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            backgroundView.setBackground(drawable);
        } else {
            backgroundView.setBackgroundDrawable(drawable);
        }
    }
}
