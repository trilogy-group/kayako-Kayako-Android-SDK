package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;

public class InputFieldCommonStateHelper {

    private InputFieldCommonStateHelper() {
    }

    private static final int HINT_TEXT_COLOR_ERROR = R.color.ko__messenger_input_field_red_hint_text_color;
    private static final int HINT_TEXT_COLOR_DEFAULT = R.color.ko__messenger_input_field_blue_hint_text_color;
    private static final int HINT_TEXT_COLOR_FILLED = R.color.ko__messenger_input_field_white_hint_text_color;

    private static final int INPUT_FIELD_ROUNDED_TOP_BACKGROUND_COLOR_ERROR = R.drawable.ko__speech_bubble_input_field_rounded_top_background_red;
    private static final int INPUT_FIELD_ROUNDED_TOP_BACKGROUND_COLOR_DEFAULT = R.drawable.ko__speech_bubble_input_field_rounded_top_background;
    private static final int INPUT_FIELD_ROUNDED_TOP_BACKGROUND_COLOR_FILLED = R.drawable.ko__speech_bubble_input_field_rounded_top_background_white;

    private static final int INPUT_FIELD_ROUNDED_BACKGROUND_COLOR_ERROR = R.drawable.ko__speech_bubble_input_field_background_red;
    private static final int INPUT_FIELD_ROUNDED_BACKGROUND_COLOR_DEFAULT = R.drawable.ko__speech_bubble_input_field_background;
    private static final int INPUT_FIELD_ROUNDED_BACKGROUND_COLOR_FILLED = R.drawable.ko__speech_bubble_input_field_background_white;

    public static void setErrorFieldState(Context context, ViewGroup inputFieldBackgroundLayout, TextView hintTextView, boolean isRoundedTop) {
        if (isRoundedTop) {
            inputFieldBackgroundLayout.setBackgroundResource(INPUT_FIELD_ROUNDED_TOP_BACKGROUND_COLOR_ERROR);
        } else {
            inputFieldBackgroundLayout.setBackgroundResource(INPUT_FIELD_ROUNDED_BACKGROUND_COLOR_ERROR);
        }
        hintTextView.setTextColor(context.getResources().getColor(HINT_TEXT_COLOR_ERROR));
    }

    public static void setFocusedFieldState(Context context, ViewGroup inputFieldBackgroundLayout, TextView hintTextView, boolean isRoundedTop) {
        if (isRoundedTop) {
            inputFieldBackgroundLayout.setBackgroundResource(INPUT_FIELD_ROUNDED_TOP_BACKGROUND_COLOR_DEFAULT);
        } else {
            inputFieldBackgroundLayout.setBackgroundResource(INPUT_FIELD_ROUNDED_BACKGROUND_COLOR_DEFAULT);
        }
        hintTextView.setTextColor(context.getResources().getColor(HINT_TEXT_COLOR_DEFAULT));
    }

    public static void setUnfocusedFieldState(Context context, ViewGroup inputFieldBackgroundLayout, TextView hintTextView, boolean isRoundedTop) {
        if (isRoundedTop) {
            inputFieldBackgroundLayout.setBackgroundResource(INPUT_FIELD_ROUNDED_TOP_BACKGROUND_COLOR_FILLED);
        } else {
            inputFieldBackgroundLayout.setBackgroundResource(INPUT_FIELD_ROUNDED_BACKGROUND_COLOR_FILLED);
        }
        hintTextView.setTextColor(context.getResources().getColor(HINT_TEXT_COLOR_FILLED));
    }
}
