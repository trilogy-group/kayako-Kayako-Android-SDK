package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputEmailListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputEmailViewHolder;
import com.kayako.sdk.android.k5.core.Kayako;

public class InputFieldEmailHelper {

    private InputFieldEmailHelper() {
    }

    private static final int HINT_TEXT_COLOR_ERROR = R.color.ko__messenger_input_field_red_hint_text_color;
    private static final int HINT_TEXT_COLOR_DEFAULT = R.color.ko__messenger_input_field_blue_hint_text_color;
    private static final int HINT_TEXT_COLOR_FILLED = R.color.ko__messenger_input_field_white_hint_text_color;

    private static final int INPUT_FIELD_BACKGROUND_COLOR_ERROR = R.drawable.ko__speech_bubble_input_field_background_red;
    private static final int INPUT_FIELD_BACKGROUND_COLOR_DEFAULT = R.drawable.ko__speech_bubble_input_field_background_blue;
    private static final int INPUT_FIELD_BACKGROUND_COLOR_FILLED = R.drawable.ko__speech_bubble_input_field_background_white;

    private static void setErrorFieldState(InputEmailViewHolder viewHolder) {
        Context context = Kayako.getApplicationContext();
        viewHolder.emailFieldLayout.setBackgroundResource(INPUT_FIELD_BACKGROUND_COLOR_ERROR);
        viewHolder.messageHint.setTextColor(context.getResources().getColor(HINT_TEXT_COLOR_ERROR));
        viewHolder.messageHint.setText(Html.fromHtml(context.getString(R.string.ko__messenger_input_email_message_hint_error)));
        viewHolder.submitButton.setText(context.getResources().getString(R.string.ko__label_submit));
    }

    private static void setDefaultFieldState(InputEmailViewHolder viewHolder) {
        Context context = Kayako.getApplicationContext();
        viewHolder.emailFieldLayout.setBackgroundResource(INPUT_FIELD_BACKGROUND_COLOR_DEFAULT);
        viewHolder.messageHint.setTextColor(context.getResources().getColor(HINT_TEXT_COLOR_DEFAULT));
        viewHolder.messageHint.setText(Html.fromHtml(context.getString(R.string.ko__messenger_input_email_message_hint_default)));
        viewHolder.submitButton.setText(context.getResources().getString(R.string.ko__label_submit));
    }

    private static void setFilledFieldState(InputEmailViewHolder viewHolder) {
        Context context = Kayako.getApplicationContext();
        viewHolder.emailFieldLayout.setBackgroundResource(INPUT_FIELD_BACKGROUND_COLOR_FILLED);
        viewHolder.messageHint.setTextColor(context.getResources().getColor(HINT_TEXT_COLOR_FILLED));
        viewHolder.messageHint.setText(Html.fromHtml(context.getString(R.string.ko__messenger_input_email_message_hint_default)));
        viewHolder.submitButton.setText(context.getResources().getString(R.string.ko__label_submit));
    }

    private static boolean isValidEmailField(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private static void setEmailFieldState(InputEmailViewHolder viewHolder, boolean validateEmail) {
        String enteredEmail = viewHolder.emailEditText.getText().toString();
        if (!validateEmail && (enteredEmail == null || enteredEmail.length() == 0)) {
            setDefaultFieldState(viewHolder);
        } else if (validateEmail && !isValidEmailField(viewHolder.emailEditText.getText().toString())) {
            setErrorFieldState(viewHolder);
        } else {
            setFilledFieldState(viewHolder);
        }
    }

    public static void configureInputEmailField(final InputEmailViewHolder viewHolder, final InputEmailListItem listItem) {
        InputFieldHelper.configureInputField(viewHolder, BotMessageHelper.getBotDrawable(), R.string.ko__messenger_input_email_message_instruction);

        // Set up Input field
        if (listItem.hasSubmittedValue()) {
            InputFieldHelper.enableSubmittedLayout(viewHolder, listItem.getSubmittedValue());
        } else {
            InputFieldHelper.enableInputLayout(viewHolder);

            // Set up email field
            setDefaultFieldState(viewHolder);
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

            // Submit button
            viewHolder.submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setEmailFieldState(viewHolder, true);

                    String email = viewHolder.emailEditText.getText().toString();
                    if (isValidEmailField(email)) {
                        InputFieldHelper.enableSubmittedLayout(viewHolder, email);
                        listItem.getOnClickSubmitListener().onClickSubmit(email);
                    }
                }
            });
        }


    }

}
