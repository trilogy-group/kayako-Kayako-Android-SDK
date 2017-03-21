package com.kayako.sdk.android.k5.messenger.messagelistpage;

import com.kayako.sdk.android.k5.core.HelpCenterPref;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.auth.FingerprintAuth;

public class MessageListContainerFactory {

    private MessageListContainerFactory() {
    }

    private static MessageListContainerContract.Presenter mPresenter;
    private static MessageListContainerContract.Data mData;

    public static MessageListContainerContract.Presenter getPresenter(MessageListContainerContract.View view) {
        mData = getData();

        if (mPresenter == null) {
            return new MessageListContainerPresenter(view, mData);
        } else {
            mPresenter.setView(view);
            mPresenter.setData(mData);
        }

        return mPresenter;
    }

    public static MessageListContainerContract.Data getData() {
        return new MessageListContainerRepository(HelpCenterPref.getInstance().getHelpCenterUrl(), new FingerprintAuth(MessengerPref.getInstance().getFingerprintId()));
    }
}
