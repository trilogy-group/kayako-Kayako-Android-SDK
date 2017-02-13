package com.kayako.sdk.android.k5.conversationlistpage;

import com.kayako.sdk.android.k5.core.HelpCenterPref;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.auth.FingerprintAuth;

public class ConversationListFactory {

    private static ConversationListContract.Data mData;
    private static ConversationListContract.Presenter mPresenter;

    public static ConversationListContract.Presenter getPresenter(ConversationListContract.View view) {
        // TODO: ArticleListFactory takes into consideration that multiple Helpcenters are possible - should messenger too?
        // Reset mData every time - especially important if helpCenterUrl changes, etc
        mData = getDataSource(HelpCenterPref.getInstance().getHelpCenterUrl(), MessengerPref.getInstance().getFingerprintId());

        if (mPresenter == null) {
            return mPresenter = new ConversationListPresenter(view, mData);
        } else {
            mPresenter.setView(view);
            mPresenter.setData(mData);
            return mPresenter;
        }
    }

    public static ConversationListContract.Data getDataSource(String helpDeskUrl, String fingerprintId) { // TODO: Parameters
        return new ConversationListRepository(helpDeskUrl, fingerprintId != null ? new FingerprintAuth(fingerprintId) : null);
    }

}
