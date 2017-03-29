package com.kayako.sdk.android.k5.messenger.style;

import android.view.View;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.core.MessengerStylePref;
import com.kayako.sdk.android.k5.messenger.style.type.Background;
import com.kayako.sdk.android.k5.messenger.style.type.Foreground;

/**
 * This ViewUtils class should be used for any layout that includes "ko__include_messenger_custom_background.xml"
 */
public class MessengerTemplateHelper {

    private MessengerTemplateHelper() {
    }

    public static void applyBackgroundTheme(View root, Background background, Foreground foreground) {
        if (background == null) { // Background is mandatory
            background = getDefaultBackground(); // GET DEFAULT FOREGROUND
        }
        MessengerBackgroundViewUtils.setBackground(root, background.getBackgroundDrawable());

        if (foreground != null) { // Foreground is optional
            if (background.isDarkBackground()) { // Foregrounds should check if the background is dark or light
                MessengerBackgroundViewUtils.setForeground(root, foreground.getDrawableForDarkBackground());
            } else {
                MessengerBackgroundViewUtils.setForeground(root, foreground.getDrawableForLightBackground());
            }
        }
    }

    public static void applyBackgroundTheme(View root) {
        if (root == null) {
            throw new IllegalArgumentException("Invalid argument. Can't be null");
        }

        Background background = getSelectedBackground();
        Foreground foreground = getSelectedForeground();
        applyBackgroundTheme(root, background, foreground);
    }

    public static void applyTextColor(TextView textView) {
        if (textView == null) {
            throw new IllegalArgumentException("Can't be null!");
        }

        final int textColorForDarkBackgrounds = R.color.ko__messenger_default_light_text_color;
        final int textColorForLightBackgrounds = R.color.ko__messenger_default_dark_text_color;

        int selectedTextColor;

        Background background = MessengerTemplateHelper.getSelectedBackground();
        if (background.isDarkBackground()) {
            selectedTextColor = textColorForDarkBackgrounds;
        } else {
            selectedTextColor = textColorForLightBackgrounds;
        }

        textView.setTextColor(Kayako.getApplicationContext().getResources().getColor(selectedTextColor));
    }

    public static void applyBackgroundColor(View view) {
        if (view == null) {
            throw new IllegalArgumentException("Can't be null!");
        }

        final int textColorForDarkBackgrounds = R.color.ko__messenger_default_light_view_background_color;
        final int textColorForLightBackgrounds = R.color.ko__messenger_default_dark_view_background_color;

        int selectedBackgroundColor;

        Background background = MessengerTemplateHelper.getSelectedBackground();
        if (background.isDarkBackground()) {
            selectedBackgroundColor = textColorForDarkBackgrounds;
        } else {
            selectedBackgroundColor = textColorForLightBackgrounds;
        }

        view.setBackgroundResource(selectedBackgroundColor);
    }

    public static Background getDefaultBackground() {
        return BackgroundFactory.getBackground(BackgroundFactory.BackgroundOption.GRADIENT_1);
    }

    public static Foreground getDefaultForeground() {
        return ForegroundFactory.getForeground(ForegroundFactory.ForegroundOption.CONFETTI_TEXTURE);
    }

    public static Background getSelectedBackground() {
        Background background = MessengerStylePref.getInstance().getBackground();
        if (background == null) { // Background is mandatory
            background = getDefaultBackground(); // GET DEFAULT FOREGROUND
        }
        return background;
    }

    public static Foreground getSelectedForeground() {
        return MessengerStylePref.getInstance().getForeground();
    }
}
