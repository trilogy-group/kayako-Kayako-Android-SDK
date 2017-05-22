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

    public static final int RESULT_EXIT = 1;

    public static void startActivityForResult(Activity activity, Fragment fragment, View attachmentView, String imageUrl, int requestCode) {
        Intent intent = new Intent(activity, KayakoAttachmentPreviewActivity.class);
        intent.putExtra(ARG_IMAGE_URL, imageUrl);

        ActivityOptionsCompat activityOptionsCompat = getAttachmentAnimation(activity, attachmentView);
        fragment.startActivityForResult(intent, requestCode, activityOptionsCompat.toBundle());
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
