package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

public abstract class InputFieldlListItem extends BaseListItem {

    private boolean hasSubmittedValue;
    private String submittedValue;

    public InputFieldlListItem(int type) {
        super(type);
        hasSubmittedValue = false;
        this.submittedValue = null;
    }

    public InputFieldlListItem(int type, String submittedValue) {
        super(type);
        this.hasSubmittedValue = true;
        this.submittedValue = submittedValue;
    }

    public boolean hasSubmittedValue() {
        return hasSubmittedValue;
    }

    public String getSubmittedValue() {
        return submittedValue;
    }
}
