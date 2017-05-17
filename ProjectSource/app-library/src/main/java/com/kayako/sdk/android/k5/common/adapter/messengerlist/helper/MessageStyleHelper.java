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

        if (isFadedBackground) {
            messageView.setBackgroundResource(R.drawable.ko__speech_bubble_self_optimistic_sending);
        } else {
            messageView.setBackgroundResource(R.drawable.ko__speech_bubble_self);
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


    // Source: http://stackoverflow.com/questions/28366172/check-if-letter-is-emoji
    private static final String EMOJI_REGEX = "([\\u20a0-\\u32ff\\ud83c\\udc00-\\ud83d\\udeff\\udbb9\\udce5-\\udbb9\\udcee])"; // "(?:\\[\\\\0x2700-\\\\0x27bf]|(?:\\\\0xd83c\\[\\\\0xdde6-\\\\0xddff]){2}|\\[\\\\0xd800-\\\\0xdbff]\\[\\\\0xdc00-\\\\0xdfff]|\\[\\\\0x0023-\\\\0x0039]\\\\0xfe0f?\\\\0x20e3|\\\\0x3299|\\\\0x3297|\\\\0x303d|\\\\0x3030|\\\\0x24c2|\\\\0xd83c\\[\\\\0xdd70-\\\\0xdd71]|\\\\0xd83c\\[\\\\0xdd7e-\\\\0xdd7f]|\\\\0xd83c\\\\0xdd8e|\\\\0xd83c\\[\\\\0xdd91-\\\\0xdd9a]|\\\\0xd83c\\[\\\\0xdde6-\\\\0xddff]|\\[\\\\0xd83c\\[\\\\0xde01-\\\\0xde02]|\\\\0xd83c\\\\0xde1a|\\\\0xd83c\\\\0xde2f|\\[\\\\0xd83c\\[\\\\0xde32-\\\\0xde3a]|\\[\\\\0xd83c\\[\\\\0xde50-\\\\0xde51]|\\\\0x203c|\\\\0x2049|\\[\\\\0x25aa-\\\\0x25ab]|\\\\0x25b6|\\\\0x25c0|\\[\\\\0x25fb-\\\\0x25fe]|\\\\0x00a9|\\\\0x00ae|\\\\0x2122|\\\\0x2139|\\\\0xd83c\\\\0xdc04|\\[\\\\0x2600-\\\\0x26FF]|\\\\0x2b05|\\\\0x2b06|\\\\0x2b07|\\\\0x2b1b|\\\\0x2b1c|\\\\0x2b50|\\\\0x2b55|\\\\0x231a|\\\\0x231b|\\\\0x2328|\\\\0x23cf|\\[\\\\0x23e9-\\\\0x23f3]|\\[\\\\0x23f8-\\\\0x23fa]|\\\\0xd83c\\\\0xdccf|\\\\0x2934|\\\\0x2935|\\[\\\\0x2190-\\\\0x21ff])";

    private static boolean isEmote(String message) {
        if (message == null
                || message.trim().split(" ").length == 0) { // should be a single word
            return false;
        }

        Matcher matcher = Pattern.compile(EMOJI_REGEX).matcher(message.trim()); // trim spaces to allow emotes with spaces before and after it
        int count = 0;
        while (matcher.find()) {
            count++;
        }

        return count == 1; // only one emote in the word
    }
}