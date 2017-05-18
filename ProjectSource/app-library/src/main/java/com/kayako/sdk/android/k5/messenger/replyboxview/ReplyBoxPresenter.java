package com.kayako.sdk.android.k5.messenger.replyboxview;

public class ReplyBoxPresenter implements ReplyBoxContract.Presenter {

    private ReplyBoxContract.View mView;
    private ReplyBoxContract.ReplyBoxListener mListener;

    public ReplyBoxPresenter(ReplyBoxContract.View view) {
        mView = view;
    }

    @Override
    public void setView(ReplyBoxContract.View view) {
        mView = view;
    }

    @Override
    public void initPage() {
    }

    @Override
    public void onReplyTyped(String message) {
        if (!isContentValidForSending(message)) {
            mView.disableSendButton();
        } else {
            mView.enableSendButton();
        }

        if (mListener != null) {
            mListener.onTypeReply(message);
        }
    }

    @Override
    public void onClickSend() {
        String messageContent = mView.getReplyBoxText();

        if (!isContentValidForSending(messageContent)) {
            mView.disableSendButton(); // Disable button, shouldn't have been enabled
        } else {
            if (mListener != null) {
                mListener.onClickSend(messageContent);
            }
            mView.setReplyBoxText(null); // Clear reply box after clicking send
        }
    }

    @Override
    public void onClickEnter() {
        onClickSend();
    }

    @Override
    public void onClickAddAttachment() {
        if (mListener != null) {
            mListener.onClickAddAttachment();
        }
    }

    @Override
    public void setReplyBoxListener(ReplyBoxContract.ReplyBoxListener listener) {
        mListener = listener;
    }

    private boolean isContentValidForSending(String content) {
        return content != null
                && content.trim().length() > 0; // trim messageContent to prevent user from sending spaces
    }
}
