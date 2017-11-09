package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.Attachment;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.AttachmentUrlType;
import com.kayako.sdk.android.k5.common.utils.ImageUtils;
import com.kayako.sdk.android.k5.common.utils.file.FileAttachmentUtil;
import com.kayako.sdk.android.k5.common.utils.file.FileStorageUtil;
import com.kayako.sdk.android.k5.core.Kayako;

import java.io.File;

public class AttachmentHelper {

    public static void setUpAttachmentImages(Attachment attachment, View attachmentPlaceholder, ImageView thumbnailImageView, TextView captionTextView) {
        switch (attachment.getType()) {
            case URL:
                AttachmentUrlType attachmentUrlType = ((AttachmentUrlType) attachment);
                AttachmentFileType type = identifyType(attachmentUrlType.getThumbnailType(), ((AttachmentUrlType) attachment).getFileName());

                String imageUrl = ((AttachmentUrlType) attachment).getThumbnailUrl();
                if (type != null && type == AttachmentFileType.IMAGE && imageUrl != null) {
                    attachmentPlaceholder.setVisibility(View.GONE);
                    thumbnailImageView.setVisibility(View.VISIBLE);

                    configureAttachmentImage(thumbnailImageView, imageUrl);
                } else {
                    attachmentPlaceholder.setVisibility(View.VISIBLE);
                    thumbnailImageView.setVisibility(View.GONE);

                    configureAttachmentPlaceholder(attachmentPlaceholder, type, attachmentUrlType.getFileName());
                }

                String attachmentCaption = ((AttachmentUrlType) attachment).getCaption();
                setUpAttachmentCaption(attachmentCaption, captionTextView);
                break;

            case FILE:
                File attachmentFile = ((com.kayako.sdk.android.k5.common.adapter.messengerlist.AttachmentFileType) attachment).getThumbnailFile();
                try {
                    // If file is unavailable, show blank placeholder
                    if (attachmentFile == null) {
                        attachmentPlaceholder.setVisibility(View.VISIBLE);
                        thumbnailImageView.setVisibility(View.GONE);
                        break;
                    }

                    // Else, show appropriate placeholder
                    AttachmentFileType type2 = identifyType(FileStorageUtil.getMimeType(attachmentFile), attachmentFile.getName());
                    if (type2 != null && type2 == AttachmentFileType.IMAGE) {
                        attachmentPlaceholder.setVisibility(View.GONE);
                        thumbnailImageView.setVisibility(View.VISIBLE);

                        ImageUtils.loadFileAsAttachmentImage(attachmentPlaceholder.getContext(), thumbnailImageView, attachmentFile, true, true);
                    } else {
                        attachmentPlaceholder.setVisibility(View.VISIBLE);
                        thumbnailImageView.setVisibility(View.GONE);

                        configureAttachmentPlaceholder(attachmentPlaceholder, type2, attachmentFile.getName());
                    }

                } finally {
                    // Ensure caption is always shown
                    String attachmentFileCaption = ((com.kayako.sdk.android.k5.common.adapter.messengerlist.AttachmentFileType) attachment).getCaption();
                    setUpAttachmentCaption(attachmentFileCaption, captionTextView);
                }
                break;

            default:
                throw new IllegalStateException("Unhandled attachment type");
        }
    }

    public static void configureAttachmentPlaceholder(View placeholderView, AttachmentFileType fileType, String fileName) {
        ImageView iconView = (ImageView) placeholderView.findViewById(R.id.ko__attachment_placeholder_icon);
        TextView textView = (TextView) placeholderView.findViewById(R.id.ko__attachment_placeholder_text);


        if (fileName != null) {
            textView.setText(fileName);
        } else {
            textView.setText(R.string.ko__messenger_attachment_placeholder_untitled);
        }

        if (fileType == null) {
            iconView.setImageResource(R.drawable.ko__ic_attachment_generic);
        } else {
            switch (fileType) {
                case IMAGE:
                    throw new IllegalStateException("IMAGE type should not be handled here");
                    // Handled differently from the rest

                case VIDEO:
                    iconView.setImageResource(R.drawable.ko__ic_attachment_video);
                    break;

                case AUDIO:
                    iconView.setImageResource(R.drawable.ko__ic_attachment_audio);
                    break;

                case OTHER:
                default:
                    iconView.setImageResource(R.drawable.ko__ic_attachment_generic);
                    break;
            }
        }
    }

    public static AttachmentFileType identifyType(String type, String fileName) {
        if (type != null && type.startsWith("image")) {
            return AttachmentFileType.IMAGE;
        } else if (type != null && type.startsWith("video")) {
            return AttachmentFileType.VIDEO;
        } else if (type != null && type.startsWith("audio")) {
            return AttachmentFileType.AUDIO;
        } else {
            return AttachmentFileType.OTHER;
        }
    }

    private static void setUpAttachmentCaption(String caption, TextView captionTextView) {
        if (TextUtils.isEmpty(caption)) {
            captionTextView.setVisibility(View.GONE);
        } else {
            captionTextView.setVisibility(View.VISIBLE);
            captionTextView.setText(Html.fromHtml(caption));
        }
    }

    private static void configureAttachmentImage(ImageView imageView, String imageUrl) {
        ImageUtils.loadUrlAsAttachmentImage(Kayako.getApplicationContext(), imageView, imageUrl, true, true, null, null);
    }

    public enum AttachmentFileType {
        IMAGE, VIDEO, AUDIO, TEXT, OTHER
    }

}
