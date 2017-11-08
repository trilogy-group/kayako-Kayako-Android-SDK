package com.kayako.sdk.android.k5.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.View;

import com.kayako.sdk.android.k5.common.activities.BaseMessengerActivity;
import com.kayako.sdk.android.k5.messenger.attachmentpreview.AttachmentPreviewFragment;

public class KayakoAttachmentPreviewActivity extends BaseMessengerActivity {

    public static final String ARG_IMAGE_URL = "attachmentimageurl";
    public static final String ARG_FILE_PATH = "attachmentfilepath";
    public static final String ARG_SHOW_SEND_BUTTON = "showsendbutton";

    public static final String ARG_ATTACHMENT_NAME = "attachmentname";
    public static final String ARG_ATTACHMENT_TIME = "attachmenttime";
    public static final String ARG_ATTACHMENT_DOWNLOAD_URL = "attachmentdownloadurl";
    public static final String ARG_ATTACHMENT_FILE_SIZE = "attachmentfilesize";

    public static final int RESULT_EXIT = 1;
    public static final int RESULT_SEND = 2;

    public static void startActivityForPreview(Activity activity, Fragment fragment, View attachmentView, String imageUrl, String attachmentName, String attachmentDownloadUrl, long attachmentTime, long attachmentFileSize, int requestCode) {
        Intent intent = new Intent(activity, KayakoAttachmentPreviewActivity.class);
        intent.putExtra(ARG_IMAGE_URL, imageUrl);
        intent.putExtra(ARG_SHOW_SEND_BUTTON, false);
        intent.putExtra(ARG_ATTACHMENT_NAME, attachmentName);
        intent.putExtra(ARG_ATTACHMENT_TIME, attachmentTime);
        intent.putExtra(ARG_ATTACHMENT_DOWNLOAD_URL, attachmentDownloadUrl);
        intent.putExtra(ARG_ATTACHMENT_FILE_SIZE, attachmentFileSize);

        ActivityOptionsCompat activityOptionsCompat = getAttachmentAnimation(activity, attachmentView);
        fragment.startActivityForResult(intent, requestCode, activityOptionsCompat.toBundle());
    }

    public static void startActivityForConfirmation(Activity activity, Fragment fragment, String filePath, int requestCode) {
        Intent intent = new Intent(activity, KayakoAttachmentPreviewActivity.class);
        intent.putExtra(ARG_FILE_PATH, filePath);
        intent.putExtra(ARG_SHOW_SEND_BUTTON, true);

        fragment.startActivityForResult(intent, requestCode);
    }

    @Override
    protected Fragment getContainerFragment() {
        return new AttachmentPreviewFragment();
    }

    protected static ActivityOptionsCompat getAttachmentAnimation(Activity activity, View attachmentView) {
        if (attachmentView != null) { // From Screens with Toolbars
            return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, attachmentView, "ko__attachment_image_preview");
        } else {
            return ActivityOptionsCompat.makeSceneTransitionAnimation(activity);
        }
    }
}
