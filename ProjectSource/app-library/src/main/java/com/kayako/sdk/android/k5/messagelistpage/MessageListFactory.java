package com.kayako.sdk.android.k5.messagelistpage;

import com.kayako.sdk.android.k5.core.HelpCenterPref;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.auth.FingerprintAuth;

public class MessageListFactory {

    private static MessageListContract.Data mData;
    private static MessageListContract.Presenter mPresenter;

    public static MessageListContract.Presenter getPresenter(MessageListContract.View view) {
        // Reset mData every time - especially important if helpCenterUrl changes, etc
        mData = getDataSource(HelpCenterPref.getInstance().getHelpCenterUrl(), MessengerPref.getInstance().getFingerprintId());

        if (mPresenter == null) {
            return mPresenter = new MessageListPresenter(view, mData);// TODO: new ConversationListPresenter(view, mData);
        } else {
            mPresenter.setView(view);
            mPresenter.setData(mData);
            return mPresenter;
        }
    }

    public static MessageListContract.Data getDataSource(String helpDeskUrl, String fingerprintId) {
        return new MessageListRepository(helpDeskUrl, new FingerprintAuth(fingerprintId));
    }

}
