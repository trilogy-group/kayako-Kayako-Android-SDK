package com.kayako.sdk.android.k5.messenger.replyboxview;

import com.kayako.sdk.android.k5.common.mvp.BasePresenter;
import com.kayako.sdk.android.k5.common.mvp.BaseView;
import com.kayako.sdk.android.k5.common.utils.file.FileAttachment;

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

        void onClickEnter();

        void onClickAddAttachment();

        void setReplyBoxListener(ReplyBoxListener listener);
    }

    public interface ConfigureView {

        void setReplyBoxListener(ReplyBoxListener listener);

        void setReplyBoxText(String message);

        void hideReplyBox();

        void showReplyBox();

        void focusOnReplyBox();

        void setAttachmentButtonVisibility(boolean showAttachment);
    }

    public interface ReplyBoxListener {
        void onClickSend(String message);

        void onClickAddAttachment();

        void onTypeReply(String typedMessage);
    }
}
