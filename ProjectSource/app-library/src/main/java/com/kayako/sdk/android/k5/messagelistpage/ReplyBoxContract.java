package com.kayako.sdk.android.k5.messagelistpage;

import com.kayako.sdk.android.k5.common.mvp.BasePresenter;
import com.kayako.sdk.android.k5.common.mvp.BaseView;

public class ReplyBoxContract {

    public interface View extends BaseView {

        String getReplyBoxText();

        void setReplyBoxText(String message); // TODO: Attachments later?

        void enableSendButton();

        void disableSendButton();
    }

    public interface Presenter extends BasePresenter<ReplyBoxContract.View> {

        void initPage();

        void onReplyTyped(String message);

        void onClickSend();

        void setReplyBoxListener(ReplyBoxListener listener);
    }

    public interface ConfigureView {
        void setReplyBoxListener(ReplyBoxListener listener);

        void setReplyBoxText(String message); // TODO: Attachments later?

        void disableReplyBox();

        void enableReplyBox();
    }

    public interface ReplyBoxListener {
        void onClickSend(String message);
    }
}
