package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.annotation.NonNull;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

public class InputEmailListItem extends BaseListItem {

    private OnClickSubmitListener onClickSubmitListener;

    public InputEmailListItem(@NonNull OnClickSubmitListener onClickSubmitListener) {
        super(MessengerListType.INPUT_EMAIL);
        this.onClickSubmitListener = onClickSubmitListener;
    }

    public OnClickSubmitListener getOnClickSubmitListener() {
        return onClickSubmitListener;
    }

    public interface OnClickSubmitListener {
        void onClickSubmit(String email);
    }
}
