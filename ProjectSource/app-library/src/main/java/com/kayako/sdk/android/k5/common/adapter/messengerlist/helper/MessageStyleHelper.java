package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Patterns;
import android.util.TypedValue;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.core.Kayako;

import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class was created because with Optimisitic Sending, some customizations were done programmatically, and some via xml.
 * Android Styles can not be programmatically applied and therefore, it causes redundant code. This will be difficult to maintain later.
 * <p>
 * Therefore, changing the way the message views are styled by programmatically applying them as opposed to relying on xml Styles
 */
public class MessageStyleHelper {

    private MessageStyleHelper() {
    }

    public static void setMessageStyle(boolean isSelf, boolean isFadedBackground, TextView messageView, @Nullable String message) {
        if (isEmote(message)) {
            setEmoteMessageStyle(messageView);
        } else if (isSelf) {
            setSelfMessageStyle(messageView, isFadedBackground);
        } else {
            setOtherMessageStyle(messageView);
        }
    }

    private static void setSelfMessageStyle(TextView messageView, boolean isFadedBackground) {
        messageView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        messageView.setTextColor(Kayako.getApplicationContext().getResources().getColor(R.color.ko__messenger_speech_bubble_self_text_color));
        messageView.setBackgroundResource(R.drawable.ko__speech_bubble_self);

        if (isFadedBackground) {
            messageView.setAlpha(0.3f); // 70% opacity = 30% transparency
        } else {
            messageView.setAlpha(1f);
        }
    }

    private static void setOtherMessageStyle(TextView messageView) {
        messageView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        messageView.setTextColor(Kayako.getApplicationContext().getResources().getColor(R.color.ko__messenger_speech_bubble_other_text_color));
        messageView.setBackgroundResource(R.drawable.ko__speech_bubble_other);
    }

    private static void setEmoteMessageStyle(TextView messageView) {
        messageView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 42); // TODO: Move to the dimens.xml
        messageView.setBackgroundResource(0); // Remove background, don't set it as transparent to remove padding of background drawable
        messageView.setPadding(0, 4, 0, 4); // Remove padding
    }

    // TODO: Find a perfect regex that recognizes all emojis

    // Testing area: http://regexr.com/3buf8
    // Source: http://stackoverflow.com/questions/28366172/check-if-letter-is-emoji
    // NOT ALL EMOJIs RECOGNIZED
    private static final String EMOJI_REGEX = "([\\u20a0-\\u32ff\\ud83c\\udc00-\\ud83d\\udeff\\udbb9\\udce5-\\udbb9\\udcee])"; // Doesn't cover all the emotes like flags

    // Source: https://medium.com/reactnative/emojis-in-javascript-f693d0eb79fb (The lodash library version)
    // ERROR, but COVERS EVERYTHING: private static final String EMOJI_REGEX = "(?:[\\u2700-\\u27bf]|(?:\\ud83c[\\udde6-\\uddff]){2}|[\\ud800-\\udbff][\\udc00-\\udfff])[\\ufe0e\\ufe0f]?(?:[\\u0300-\\u036f\\ufe20-\\ufe23\\u20d0-\\u20f0]|\\ud83c[\\udffb-\\udfff])?(?:\\u200d(?:[^\\ud800-\\udfff]|(?:\\ud83c[\\udde6-\\uddff]){2}|[\\ud800-\\udbff][\\udc00-\\udfff])[\\ufe0e\\ufe0f]?(?:[\\u0300-\\u036f\\ufe20-\\ufe23\\u20d0-\\u20f0]|\\ud83c[\\udffb-\\udfff])?)*";

    private static boolean isEmote(String message) {
        if (message == null) {
            return false;
        }
        boolean hasAlphanumericContent = Pattern.compile("[a-zA-Z0-9]").matcher(message).find();
        boolean isOneWord = message.trim().split(" ").length == 0;
        if (isOneWord || hasAlphanumericContent) {
            return false;
        }

        Matcher matcher = Pattern.compile(EMOJI_REGEX, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)
                .matcher(message.trim()); // trim spaces to allow emotes with spaces before and after it
        int count = 0;
        while (matcher.find()) {
            count++;
        }

        return count == 1; // only one emote in the word
    }
}