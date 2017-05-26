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


    private static void setErrorFieldState(InputEmailViewHolder viewHolder) {
        Context context = Kayako.getApplicationContext();

        InputFieldCommonStateHelper.setErrorFieldState(context, viewHolder.emailFieldLayout, viewHolder.messageHint, true);
        viewHolder.messageHint.setText(Html.fromHtml(context.getString(R.string.ko__messenger_input_email_message_hint_error)));
        viewHolder.submitButton.setText(context.getResources().getString(R.string.ko__label_submit));
    }

    private static void setFocusedFieldState(InputEmailViewHolder viewHolder) {
        Context context = Kayako.getApplicationContext();
        InputFieldCommonStateHelper.setFocusedFieldState(context, viewHolder.emailFieldLayout, viewHolder.messageHint, true);
        viewHolder.messageHint.setText(Html.fromHtml(context.getString(R.string.ko__messenger_input_email_message_hint_default)));
        viewHolder.submitButton.setText(context.getResources().getString(R.string.ko__label_submit));
    }

    private static void setUnfocusedFieldState(InputEmailViewHolder viewHolder) {
        Context context = Kayako.getApplicationContext();
        InputFieldCommonStateHelper.setUnfocusedFieldState(context, viewHolder.emailFieldLayout, viewHolder.messageHint, true);
        viewHolder.messageHint.setText(Html.fromHtml(context.getString(R.string.ko__messenger_input_email_message_hint_default)));
        viewHolder.submitButton.setText(context.getResources().getString(R.string.ko__label_submit));
    }

    private static boolean isValidEmailField(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private static boolean isFocused(View view) {
        return view.isFocused(); // TODO: Check if this is working when there is more than one focusable entity
    }

    private static void setEmailFieldState(InputEmailViewHolder viewHolder, boolean validateEmail) {
        String enteredEmail = viewHolder.emailEditText.getText().toString();
        if (validateEmail && !isValidEmailField(enteredEmail)) {
            setErrorFieldState(viewHolder);
        } else if (isFocused(viewHolder.emailEditText)) {
            setFocusedFieldState(viewHolder);
        } else {
            setUnfocusedFieldState(viewHolder);
        }
    }

    public static void configureInputEmailField(final InputEmailViewHolder viewHolder, final InputEmailListItem listItem) {
        InputFieldHelper.configureInputField(viewHolder, R.string.ko__messenger_input_email_message_instruction);

        // Set up Input field
        if (listItem.hasSubmittedValue()) {
            InputFieldHelper.enableSubmittedLayout(viewHolder, listItem.getSubmittedValue());
        } else {
            InputFieldHelper.enableInputLayoutWithButton(viewHolder);

            // Set up email field
            setFocusedFieldState(viewHolder);
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
