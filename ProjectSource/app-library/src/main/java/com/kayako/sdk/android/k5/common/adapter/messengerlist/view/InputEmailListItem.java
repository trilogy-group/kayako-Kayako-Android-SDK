package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.annotation.NonNull;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

public class InputEmailListItem extends InputFieldlListItem {

    private String submittedEmail;
    private OnClickSubmitListener onClickSubmitListener;

    public InputEmailListItem(@NonNull OnClickSubmitListener onClickSubmitListener) {
        super(MessengerListType.INPUT_FIELD_EMAIL);
        this.onClickSubmitListener = onClickSubmitListener;
    }

    public InputEmailListItem(String submittedEmail) {
        super(MessengerListType.INPUT_FIELD_EMAIL, submittedEmail);
    }

    public OnClickSubmitListener getOnClickSubmitListener() {
        return onClickSubmitListener;
    }

    public interface OnClickSubmitListener {
        void onClickSubmit(String email);
    }
}
