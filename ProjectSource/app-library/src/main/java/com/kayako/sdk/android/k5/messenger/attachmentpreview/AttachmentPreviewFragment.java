package com.kayako.sdk.android.k5.messenger.attachmentpreview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.activities.KayakoAttachmentPreviewActivity;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.AttachmentHelper;
import com.kayako.sdk.android.k5.common.utils.ImageUtils;
import com.kayako.sdk.android.k5.common.utils.file.FileAttachment;
import com.kayako.sdk.android.k5.common.utils.file.FileAttachmentUtil;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.FileAttachmentDownloadHelper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AttachmentPreviewFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    private View mRoot;
    private FileAttachmentDownloadHelper mFileAttachmentDownloadHelper = new FileAttachmentDownloadHelper();
    private FileAttachmentDownloadHelper.DownloadAttachment mDownloadAttachment;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.ko__fragment_attachment_preview, container, false);
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRoot.findViewById(R.id.ko__button_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishByExit();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (!(getActivity() instanceof KayakoAttachmentPreviewActivity)) {
            throw new IllegalStateException("This fragment is meant to be used with KayakoAttachmentPreviewActivity");
        }

        Bundle bundle = getActivity().getIntent().getExtras();

        boolean showSendButton = bundle.getBoolean(KayakoAttachmentPreviewActivity.ARG_SHOW_SEND_BUTTON, false);
        String imageUrl = bundle.getString(KayakoAttachmentPreviewActivity.ARG_IMAGE_URL);
        String filePath = bundle.getString(KayakoAttachmentPreviewActivity.ARG_FILE_PATH);

        View attachmentPlaceholder = mRoot.findViewById(R.id.ko__attachment_placeholder);
        ImageView imageView = ((ImageView) mRoot.findViewById(R.id.ko__attachment_image));
        if (imageUrl != null) { // Load Image Preview via URL
            imageView.setVisibility(View.VISIBLE);
            attachmentPlaceholder.setVisibility(View.GONE);

            mRoot.findViewById(R.id.ko__attachment_loader).setVisibility(View.VISIBLE);
            ImageUtils.loadUrlAsAttachmentImage(getContext(), imageView, imageUrl, false, false, new ImageUtils.OnImageLoadedListener() {
                @Override
                public void onImageLoaded() {
                    if (getActivity() == null || !isAdded()) {
                        return;
                    }

                    mRoot.findViewById(R.id.ko__attachment_loader).setVisibility(View.GONE);
                }

                @Override
                public void onImageFailedToLoad() {
                    if (getActivity() == null || !isAdded()) {
                        return;
                    }

                    getActivity().onBackPressed(); // not finish() to retain animation

                    Toast.makeText(getContext(), R.string.ko__messenger_attachment_preview_failed_to_load, Toast.LENGTH_SHORT).show();
                }
            });
        } else if (filePath != null) { // Load Preview via local FILE

            FileAttachment fileAttachment = FileAttachmentUtil.generateFileAttachment("preview", new File(filePath));
            AttachmentHelper.AttachmentFileType attachmentFileType = AttachmentHelper.identifyType(fileAttachment.getMimeType(), fileAttachment.getName());

            if (attachmentFileType != null && attachmentFileType == AttachmentHelper.AttachmentFileType.IMAGE) { // Load local image
                imageView.setVisibility(View.VISIBLE);
                attachmentPlaceholder.setVisibility(View.GONE);

                ImageUtils.loadFileAsAttachmentImage(getContext(), imageView, new File(filePath), false, false);

            } else { // Load other file
                imageView.setVisibility(View.GONE);
                attachmentPlaceholder.setVisibility(View.VISIBLE);

                AttachmentHelper.configureAttachmentPlaceholder(attachmentPlaceholder, attachmentFileType, fileAttachment.getName());
            }

        } else {
            throw new IllegalStateException("INVALID STATE - need at least imageUrl or filePath");
        }

        View exitButton = mRoot.findViewById(R.id.ko__button_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishByExit();
            }
        });

        View sendButton = mRoot.findViewById(R.id.ko__reply_box_send_button);
        View attachmentDetails = mRoot.findViewById(R.id.ko__attachment_details);
        final View optionsButton = mRoot.findViewById(R.id.ko__button_options);
        if (showSendButton) {
            sendButton.setVisibility(View.VISIBLE);
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishByClickingSend();
                }
            });
            attachmentDetails.setVisibility(View.GONE);
            optionsButton.setVisibility(View.GONE);
        } else {
            sendButton.setVisibility(View.GONE);

            // Extract values
            Long time = bundle.containsKey(KayakoAttachmentPreviewActivity.ARG_ATTACHMENT_NAME) ? bundle.getLong(KayakoAttachmentPreviewActivity.ARG_ATTACHMENT_TIME, 0) : null;
            String name = bundle.containsKey(KayakoAttachmentPreviewActivity.ARG_ATTACHMENT_NAME) ? bundle.getString(KayakoAttachmentPreviewActivity.ARG_ATTACHMENT_NAME, null) : null;
            String downloadUrl = bundle.containsKey(KayakoAttachmentPreviewActivity.ARG_ATTACHMENT_DOWNLOAD_URL) ? bundle.getString(KayakoAttachmentPreviewActivity.ARG_ATTACHMENT_DOWNLOAD_URL, null) : null;
            Long fileSize = bundle.containsKey(KayakoAttachmentPreviewActivity.ARG_ATTACHMENT_NAME) ? bundle.getLong(KayakoAttachmentPreviewActivity.ARG_ATTACHMENT_TIME, 0) : null;

            // Show options (currently only supports download attachment url)
            if (downloadUrl != null) {
                mDownloadAttachment = new FileAttachmentDownloadHelper.DownloadAttachment(name == null ? "attachment" : name, fileSize == null ? 0 : fileSize, downloadUrl);
                optionsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAttachmentMenu(optionsButton);
                    }
                });
                optionsButton.setVisibility(View.VISIBLE);
            } else {
                optionsButton.setVisibility(View.GONE);
            }

            // Show name of attachment
            if (name != null) {
                TextView attachmentName = (TextView) mRoot.findViewById(R.id.ko__attachment_name);
                attachmentName.setText(name);
                attachmentDetails.setVisibility(View.VISIBLE);
            } else {
                attachmentDetails.setVisibility(View.GONE); // if name not available, hide everything
            }

            // Show creation time of attachment
            TextView attachmentTime = (TextView) mRoot.findViewById(R.id.ko__attachment_date);
            if (time != null && time != 0) {
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, YYYY 'at' hh:mm a", Locale.US);
                    attachmentTime.setText(simpleDateFormat.format(new Date(time)));
                    attachmentTime.setVisibility(View.VISIBLE); // Show the time
                } catch (Exception e) {
                    // If there are issues due to date formatting, hide the time
                    attachmentTime.setVisibility(View.GONE);
                }
            } else {
                attachmentTime.setVisibility(View.GONE); // Hide only the time
            }
        }
    }

    private void showAttachmentMenu(View v) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.ko__menu_attachment);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.ko__action_download) {
            mFileAttachmentDownloadHelper.onClickAttachmentToDownload(mDownloadAttachment, true);
            return true;
        } else {
            return false;
        }
    }

    public void finishByExit() {
        if (getActivity() == null) {
            return;
        }

        getActivity().setResult(KayakoAttachmentPreviewActivity.RESULT_EXIT);
        getActivity().onBackPressed(); // not finish() to retain animation
    }

    public void finishByClickingSend() {
        if (getActivity() == null) {
            return;
        }

        getActivity().setResult(KayakoAttachmentPreviewActivity.RESULT_SEND);
        getActivity().finish(); // needs finish to pass result code and animation not required here
    }

}
