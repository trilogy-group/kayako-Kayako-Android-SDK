package com.kayako.sdk.android.k5.common.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.view.CircleImageView;
import com.kayako.sdk.android.k5.common.view.CropCircleTransformation;

public class ImageUtils {

    /**
     *
     * Used to set public image url to imageView
     *
     * @param context Context of the activity
     * @param avatarView ImageView to show avatar
     * @param avatarUrl URL of the avatar image to show
     */
    public static void setImage(Context context, ImageView avatarView, String avatarUrl) {
        Glide.with(context)
                .load(avatarUrl)
                .bitmapTransform(new CropCircleTransformation(context))
                .placeholder(R.color.ko__avatar_image_background)
                .into(avatarView);
    }

    /**
     * Used to set public image url to imageView
     *
     * @param context Context of the activity
     * @param avatarView CircularImageView to show avatar
     * @param avatarUrl URL of the avatar image to show
     */
    public static void setImage(Context context, CircleImageView avatarView, String avatarUrl) {
        Glide.with(context)
                .load(avatarUrl)
                .placeholder(R.color.ko__avatar_image_background)
                .into(avatarView);
    }

}
