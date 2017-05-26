package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFieldViewHolder;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFieldlListItem;
import com.kayako.sdk.android.k5.common.utils.ImageUtils;
import com.kayako.sdk.android.k5.core.Kayako;

public class InputFieldHelper {

    private InputFieldHelper() {
    }

    public static boolean isPreSubmittedField(InputFieldlListItem inputFieldlListItem) {
        return inputFieldlListItem.hasSubmittedValue();
    }

    public static void configureInputField(InputFieldViewHolder viewHolder, @StringRes int instructionMessageStringResId) {
        configureInputField(viewHolder, Kayako.getApplicationContext().getResources().getString(instructionMessageStringResId));
    }

    public static void configureInputField(InputFieldViewHolder viewHolder, @NonNull String instructionMessageString) {
        if (instructionMessageString == null) {
            throw new IllegalArgumentException("instructionMessageString can not be null");
        }

        viewHolder.messageInstruction.setText(Html.fromHtml(instructionMessageString));
    }

    public static void enableInputLayoutWithoutButton(InputFieldViewHolder viewHolder) {
        viewHolder.submittedLayout.setVisibility(View.GONE);
        viewHolder.inputLayout.setVisibility(View.VISIBLE);
        viewHolder.inputLayout.setBackgroundResource(R.drawable.ko__speech_bubble_input_container_rounded_background);
    }


    public static void enableInputLayoutWithButton(InputFieldViewHolder viewHolder) {
        viewHolder.submittedLayout.setVisibility(View.GONE);
        viewHolder.inputLayout.setVisibility(View.VISIBLE);
        viewHolder.inputLayout.setBackgroundResource(R.drawable.ko__speech_bubble_input_container_rounded_top_background);
    }

    public static void enableSubmittedLayout(InputFieldViewHolder viewHolder, String submittedText) {
        viewHolder.submittedLayout.setVisibility(View.VISIBLE);
        viewHolder.inputLayout.setVisibility(View.GONE);

        viewHolder.submittedAnswer.setText(submittedText);
    }

}
