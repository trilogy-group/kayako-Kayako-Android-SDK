package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.Attachment;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.AttachmentFileType;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.AttachmentUrlType;
import com.kayako.sdk.android.k5.common.utils.ImageUtils;
import com.kayako.sdk.android.k5.core.Kayako;

import java.io.File;

public class AttachmentHelper {

    public static void setUpAttachmentImages(Attachment attachment, View attachmentPlaceholder, ImageView thumbnailImageView, TextView captionTextView) {
        switch (attachment.getType()) {
            case URL:
                String attachmentUrl = ((AttachmentUrlType) attachment).getThumbnailUrl();
                if (attachmentUrl == null) {
                    attachmentPlaceholder.setVisibility(View.VISIBLE);
                    thumbnailImageView.setVisibility(View.GONE);
                } else {
                    attachmentPlaceholder.setVisibility(View.GONE);
                    thumbnailImageView.setVisibility(View.VISIBLE);
                    ImageUtils.loadUrlAsAttachmentImage(Kayako.getApplicationContext(), thumbnailImageView, attachmentUrl);
                }

                String attachmentCaption = ((AttachmentUrlType) attachment).getCaption();
                setUpAttachmentCaption(attachmentCaption, captionTextView);
                break;

            case FILE:
                File attachmentFile = ((AttachmentFileType) attachment).getThumbnailFile();
                if (attachmentFile == null) {
                    attachmentPlaceholder.setVisibility(View.VISIBLE);
                    thumbnailImageView.setVisibility(View.GONE);
                } else {
                    attachmentPlaceholder.setVisibility(View.GONE);
                    thumbnailImageView.setVisibility(View.VISIBLE);
                    ImageUtils.loadFileAsAttachmentImage(Kayako.getApplicationContext(), thumbnailImageView, attachmentFile);
                }

                String attachmentFileCaption = ((AttachmentFileType) attachment).getCaption();
                setUpAttachmentCaption(attachmentFileCaption, captionTextView);
                break;

            default:
                throw new IllegalStateException("Unhandled attachment type");
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

}
