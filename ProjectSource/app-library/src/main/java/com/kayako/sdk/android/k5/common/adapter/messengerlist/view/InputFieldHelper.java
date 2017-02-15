package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.core.Kayako;

public class InputFieldHelper {

    private InputFieldHelper() {
    }

    private static final int HINT_TEXT_COLOR_ERROR = R.color.ko__messenger_input_field_red_hint_text_color;
    private static final int HINT_TEXT_COLOR_DEFAULT = R.color.ko__messenger_input_field_blue_hint_text_color;
    private static final int HINT_TEXT_COLOR_FILLED = R.color.ko__messenger_input_field_white_hint_text_color;

    private static final int INPUT_FIELD_BACKGROUND_COLOR_ERROR = R.drawable.ko__speech_bubble_input_field_background_red;
    private static final int INPUT_FIELD_BACKGROUND_COLOR_DEFAULT = R.drawable.ko__speech_bubble_input_field_background_blue;
    private static final int INPUT_FIELD_BACKGROUND_COLOR_FILLED = R.drawable.ko__speech_bubble_input_field_background_white;

    public static void configureAvatar(InputFieldViewHolder viewHolder, @DrawableRes int drawableId) {
        viewHolder.avatar.setBackgroundColor(Color.RED); // TODO: Use Kayako Bot Logo
    }

    public static void setErrorState(InputFieldViewHolder viewHolder) {
        Context context = Kayako.getApplicationContext();
        viewHolder.inputFieldLayout.setBackgroundResource(INPUT_FIELD_BACKGROUND_COLOR_ERROR);
        viewHolder.messageHint.setTextColor(context.getResources().getColor(HINT_TEXT_COLOR_ERROR));
        viewHolder.messageHint.setText(Html.fromHtml(context.getString(R.string.ko__messenger_input_email_message_hint_error)));
        viewHolder.submitButton.setText(context.getResources().getString(R.string.ko__label_submit));
    }

    public static void setDefaultState(InputFieldViewHolder viewHolder) {
        Context context = Kayako.getApplicationContext();
        viewHolder.inputFieldLayout.setBackgroundResource(INPUT_FIELD_BACKGROUND_COLOR_DEFAULT);
        viewHolder.messageHint.setTextColor(context.getResources().getColor(HINT_TEXT_COLOR_DEFAULT));
        viewHolder.messageHint.setText(Html.fromHtml(context.getString(R.string.ko__messenger_input_email_message_hint_default)));
        viewHolder.submitButton.setText(context.getResources().getString(R.string.ko__label_submit));
    }

    public static void setFilledState(InputFieldViewHolder viewHolder) {
        Context context = Kayako.getApplicationContext();
        viewHolder.inputFieldLayout.setBackgroundResource(INPUT_FIELD_BACKGROUND_COLOR_FILLED);
        viewHolder.messageHint.setTextColor(context.getResources().getColor(HINT_TEXT_COLOR_FILLED));
        viewHolder.messageHint.setText(Html.fromHtml(context.getString(R.string.ko__messenger_input_email_message_hint_default)));
        viewHolder.submitButton.setText(context.getResources().getString(R.string.ko__label_submit));
    }

    public static boolean isValidEmailField(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static void setEmailFieldState(InputEmailViewHolder viewHolder, boolean validateEmail) {
        String enteredEmail = viewHolder.emailEditText.getText().toString();
        if (!validateEmail && (enteredEmail == null || enteredEmail.length() == 0)) {
            setDefaultState(viewHolder);
        } else if (validateEmail && !isValidEmailField(viewHolder.emailEditText.getText().toString())) {
            setErrorState(viewHolder);
        } else {
            setFilledState(viewHolder);
        }
    }

    public static void configureOnClickSubmitListener(final InputEmailViewHolder viewHolder, final InputEmailListItem.OnClickSubmitListener listener) {
        viewHolder.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEmailFieldState(viewHolder, true);

                String email = viewHolder.emailEditText.getText().toString();
                if (isValidEmailField(email)) {
                    listener.onClickSubmit(email);
                }
            }
        });
    }

    public static void configureEmailFieldText(final InputEmailViewHolder viewHolder) {
        Context context = Kayako.getApplicationContext();
        setDefaultState(viewHolder);

        viewHolder.messageInstruction.setText(Html.fromHtml(context.getString(R.string.ko__messenger_input_email_message_instruction)));
        viewHolder.emailEditText.setHint(R.string.ko__messenger_input_email_edittext_hint);
        viewHolder.emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setEmailFieldState(viewHolder, false);
            }
        });
    }

}
