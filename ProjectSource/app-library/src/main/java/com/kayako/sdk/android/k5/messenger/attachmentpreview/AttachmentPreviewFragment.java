package com.kayako.sdk.android.k5.messenger.attachmentpreview;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.DownloadAttachment;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.FileAttachmentDownloadHelper;
import com.kayako.sdk.auth.Auth;
import com.kayako.sdk.auth.SessionAuth;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AttachmentPreviewFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    private View mRoot;
    private FileAttachmentDownloadHelper mFileAttachmentDownloadHelper = new FileAttachmentDownloadHelper();

    private DownloadAttachment mDownloadAttachment;

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

        // Assertion
        if (!(getActivity() instanceof KayakoAttachmentPreviewActivity)) {
            throw new IllegalStateException("This fragment is meant to be used with KayakoAttachmentPreviewActivity");
        }

        // Extract info
        Bundle bundle = getActivity().getIntent().getExtras();
        boolean showSendButton = bundle.getBoolean(KayakoAttachmentPreviewActivity.ARG_SHOW_SEND_BUTTON, false);
        String imageUrl = bundle.getString(KayakoAttachmentPreviewActivity.ARG_IMAGE_URL);
        String filePath = bundle.getString(KayakoAttachmentPreviewActivity.ARG_FILE_PATH);

        // Extract Agent App Specific Info
        boolean useSessionAuth = bundle.getBoolean(KayakoAttachmentPreviewActivity.ARG_REQ_SESSION_AUTH, false);
        Auth auth = useSessionAuth ? extractAuthInfo(bundle) : null;

        // Configure the Image Preview
        View attachmentPlaceholder = mRoot.findViewById(R.id.ko__attachment_placeholder);
        ImageView imageView = ((ImageView) mRoot.findViewById(R.id.ko__attachment_image));
        View loadingView = mRoot.findViewById(R.id.ko__attachment_loader);
        if (imageUrl != null) { // Load Image Preview via URL
            configureImageForUrlAttachment(imageView, attachmentPlaceholder, loadingView, imageUrl, auth);

        } else if (filePath != null) { // Load Preview via local FILE
            configureImageForFileAttachment(imageView, attachmentPlaceholder, filePath);

        } else {
            throw new IllegalStateException("INVALID STATE - need at least imageUrl or filePath");
        }

        // Configure Exit / back Button
        View exitButton = mRoot.findViewById(R.id.ko__button_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishByExit();
            }
        });

        // Configure View depending on whether it's for previewing an Unsent attachment or Sent attachment
        View sendButton = mRoot.findViewById(R.id.ko__reply_box_send_button);
        View attachmentDetails = mRoot.findViewById(R.id.ko__attachment_details);
        View optionsButton = mRoot.findViewById(R.id.ko__button_options);
        if (showSendButton) {
            // Configure View
            configureViewForConfirmingAttachmentBeforeSending(sendButton, optionsButton, attachmentDetails);
        } else {
            // Extract values
            Long time = bundle.containsKey(KayakoAttachmentPreviewActivity.ARG_ATTACHMENT_NAME) ? bundle.getLong(KayakoAttachmentPreviewActivity.ARG_ATTACHMENT_TIME, 0) : null;
            String name = bundle.containsKey(KayakoAttachmentPreviewActivity.ARG_ATTACHMENT_NAME) ? bundle.getString(KayakoAttachmentPreviewActivity.ARG_ATTACHMENT_NAME, null) : null;
            String downloadUrl = bundle.containsKey(KayakoAttachmentPreviewActivity.ARG_ATTACHMENT_DOWNLOAD_URL) ? bundle.getString(KayakoAttachmentPreviewActivity.ARG_ATTACHMENT_DOWNLOAD_URL, null) : null;
            Long fileSize = bundle.containsKey(KayakoAttachmentPreviewActivity.ARG_ATTACHMENT_NAME) ? bundle.getLong(KayakoAttachmentPreviewActivity.ARG_ATTACHMENT_TIME, 0) : null;

            // Configure View
            configureViewForPreviewingUploadedAttachments(sendButton, optionsButton, attachmentDetails, time, name, downloadUrl, fileSize, auth);
        }
    }

    private Auth extractAuthInfo(Bundle bundle) {
        boolean containsSessionInfo = bundle.containsKey(KayakoAttachmentPreviewActivity.ARG_AGENT_SESSION_ID) && bundle.containsKey(KayakoAttachmentPreviewActivity.ARG_AGENT_USER_AGENT);
        String sessionId = containsSessionInfo ? bundle.getString(KayakoAttachmentPreviewActivity.ARG_AGENT_SESSION_ID, null) : null;
        String userAgent = containsSessionInfo ? bundle.getString(KayakoAttachmentPreviewActivity.ARG_AGENT_USER_AGENT, null) : null;
        if (containsSessionInfo && sessionId != null && userAgent != null) {
            return new SessionAuth(
                    sessionId,
                    userAgent
            );
        }
        return null;
    }

    private void configureImageForUrlAttachment(@NonNull final ImageView imageView, @NonNull final View attachmentPlaceholder, @NonNull final View loadingView, @NonNull final String imageUrl, @Nullable final Auth auth) {
        if (imageView == null || attachmentPlaceholder == null || loadingView == null || imageUrl == null) {
            throw new IllegalStateException();
        }

        // Show imageView
        imageView.setVisibility(View.VISIBLE);
        attachmentPlaceholder.setVisibility(View.GONE);

        // Show loading
        loadingView.setVisibility(View.VISIBLE);

        ImageUtils.loadUrlAsAttachmentImage(getContext(), imageView, imageUrl, false, false, auth, new ImageUtils.OnImageLoadedListener() {
            @Override
            public void onImageLoaded() {
                if (getActivity() == null || !isAdded()) {
                    return;
                }

                loadingView.setVisibility(View.GONE);
            }

            @Override
            public void onImageFailedToLoad() {
                if (getActivity() == null || !isAdded()) {
                    return;
                }

                loadingView.setVisibility(View.GONE);

                getActivity().onBackPressed(); // not finish() to retain animation

                Toast.makeText(getContext(), R.string.ko__messenger_attachment_preview_failed_to_load, Toast.LENGTH_SHORT).show();
            }
        });
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
            if (FileAttachmentUtil.checkFileAccessPermissions(getActivity())) {
                mFileAttachmentDownloadHelper.onClickAttachmentToDownload(mDownloadAttachment, true);
            }
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

    private void configureImageForFileAttachment(@NonNull final ImageView imageView, @NonNull final View attachmentPlaceholder, @NonNull final String filePath) {
        if (imageView == null || attachmentPlaceholder == null || filePath == null) {
            throw new IllegalStateException();
        }

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
    }

    private void configureViewForPreviewingUploadedAttachments(final @NonNull View sendButton, final @NonNull View optionsButton, final @NonNull View attachmentDetails, @Nullable Long time, @Nullable String name, @Nullable String downloadUrl, @Nullable Long fileSize, Auth auth) {
        if (sendButton == null || optionsButton == null || attachmentDetails == null) {
            throw new IllegalStateException();
        }

        // Hide send button
        sendButton.setVisibility(View.GONE);

        // Show options (currently only supports download attachment url)
        if (downloadUrl != null) {
            mDownloadAttachment = new DownloadAttachment(
                    name == null ? "attachment" : name,
                    fileSize == null ? 0 : fileSize,
                    downloadUrl,
                    auth
            );
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

    private void configureViewForConfirmingAttachmentBeforeSending(final @NonNull View sendButton, final @NonNull View optionsButton, final @NonNull View attachmentDetails) {
        if (sendButton == null || optionsButton == null || attachmentDetails == null) {
            throw new IllegalStateException();
        }

        // Show Send button
        sendButton.setVisibility(View.VISIBLE);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishByClickingSend();
            }
        });

        // Hide others not relevant to sending
        attachmentDetails.setVisibility(View.GONE);
        optionsButton.setVisibility(View.GONE);
    }
}
