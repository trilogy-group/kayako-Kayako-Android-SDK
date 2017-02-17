package com.kayako.sdk.android.k5.messagelistpage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kayako.sdk.android.k5.R;

public class ReplyBoxFragment extends Fragment implements ReplyBoxContract.View, ReplyBoxContract.ConfigureView, TextWatcher {

    private ReplyBoxContract.Presenter mPresenter;
    private View mRoot;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = ReplyBoxFactory.getPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mRoot = inflater.inflate(R.layout.ko__fragment_reply_box, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up send button
        View sendButton = mRoot.findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onClickSend();
            }
        });

        // Set up ReplyBoxText
        EditText replyBoxText = (EditText) mRoot.findViewById(R.id.reply_box_edittext);
        replyBoxText.addTextChangedListener(this);
        replyBoxText.setText(null); // trigger textChangeListener for first time
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.initPage();
    }

    private boolean hasPageLoaded() {
        return isAdded() && getActivity() != null;
    }

    @Override
    public String getReplyBoxText() {
        if (!hasPageLoaded()) {
            return null;
        }

        EditText replyBoxText = (EditText) mRoot.findViewById(R.id.reply_box_edittext);
        return replyBoxText.getText().toString();
    }

    @Override
    public void setReplyBoxText(String message) {
        EditText replyBoxText = (EditText) mRoot.findViewById(R.id.reply_box_edittext);
        replyBoxText.setText(message);
    }

    @Override
    public void hideReplyBox() {
        View view = mRoot.findViewById(R.id.reply_box_layout);
        view.setVisibility(View.GONE);
    }

    @Override
    public void showReplyBox() {
        View view = mRoot.findViewById(R.id.reply_box_layout);
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void enableSendButton() {
        Button button = (Button) mRoot.findViewById(R.id.send_button);
        button.setEnabled(true);
    }

    @Override
    public void disableSendButton() {
        Button button = (Button) mRoot.findViewById(R.id.send_button);
        button.setEnabled(false);
    }

    @Override
    public void setReplyBoxListener(ReplyBoxContract.ReplyBoxListener listener) {
        mPresenter.setReplyBoxListener(listener);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mPresenter.onReplyTyped(s.toString());
    }
}
