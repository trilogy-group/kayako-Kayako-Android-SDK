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

    public static void configureInputField(InputFieldViewHolder viewHolder, @DrawableRes int avatarDrawableId, @StringRes int instructionMessageStringResId) {
        configureInputField(viewHolder, avatarDrawableId, Kayako.getApplicationContext().getResources().getString(instructionMessageStringResId));
    }

    public static void configureInputField(InputFieldViewHolder viewHolder, @DrawableRes int avatarDrawableId, @NonNull String instructionMessageString) {
        if (instructionMessageString == null) {
            throw new IllegalArgumentException("instructionMessageString can not be null");
        }
        Context context = Kayako.getApplicationContext();
        ImageUtils.setAvatarImage(context, viewHolder.avatar, avatarDrawableId);
        viewHolder.messageInstruction.setText(Html.fromHtml(instructionMessageString));
    }

    public static void enableInputLayout(InputFieldViewHolder viewHolder) {
        viewHolder.submittedLayout.setVisibility(View.GONE);
        viewHolder.inputLayout.setVisibility(View.VISIBLE);
    }

    public static void enableSubmittedLayout(InputFieldViewHolder viewHolder, String submittedText) {
        viewHolder.submittedLayout.setVisibility(View.VISIBLE);
        viewHolder.inputLayout.setVisibility(View.GONE);

        viewHolder.submittedAnswer.setText(submittedText);
    }

}
