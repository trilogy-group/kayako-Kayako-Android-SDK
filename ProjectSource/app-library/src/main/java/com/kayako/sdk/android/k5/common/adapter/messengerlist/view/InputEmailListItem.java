package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.support.annotation.NonNull;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

import java.util.HashMap;
import java.util.Map;

public class InputEmailListItem extends InputFieldlListItem {

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

    @Override
    public Map<String, String> getContents() {
        Map<String, String> map = new HashMap<>();
        map.put("hasSubmittedValue", String.valueOf(hasSubmittedValue()));
        map.put("getSubmittedValue", String.valueOf(getSubmittedValue()));
        return map;
    }
}
