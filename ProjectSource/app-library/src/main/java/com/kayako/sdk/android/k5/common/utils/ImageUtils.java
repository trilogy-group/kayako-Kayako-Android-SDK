package com.kayako.sdk.android.k5.common.utils;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.ChannelDecoration;
import com.kayako.sdk.android.k5.common.view.CircleImageView;
import com.kayako.sdk.android.k5.common.view.CropCircleTransformation;

public class ImageUtils {

    /**
     * Set public image url to imageView
     *
     * @param context    Context of the activity
     * @param avatarView ImageView to show avatar
     * @param avatarUrl  URL of the avatar image to show
     */
    public static void setAvatarImage(Context context, ImageView avatarView, String avatarUrl) {
        Glide.with(context)
                .load(avatarUrl)
                .bitmapTransform(new CropCircleTransformation(context))
                .placeholder(R.color.ko__avatar_image_background)
                .signature(new StringSignature(avatarUrl))
                .into(avatarView);
    }

    /**
     * Set public image url imageView and specify default placeholder resource
     *
     * @param context
     * @param imageView
     * @param imageUrl
     */
    public static void setImage(Context context, ImageView imageView, String imageUrl) {
        Glide.with(context)
                .load(imageUrl)
                .signature(new StringSignature(imageUrl))
                .placeholder(R.color.ko__avatar_image_background)
                .into(imageView);
    }

    /**
     * Set public image url to imageView
     *
     * @param context    Context of the activity
     * @param avatarView CircularImageView to show avatar
     * @param avatarUrl  URL of the avatar image to show
     */
    public static void setAvatarImage(Context context, CircleImageView avatarView, String avatarUrl) {
        Glide.with(context)
                .load(avatarUrl)
                .placeholder(R.color.ko__avatar_image_background)
                .signature(new StringSignature(avatarUrl))
                .into(avatarView);
    }

    /**
     * Set local drawable and background to CircleImageView
     *
     * @param context
     * @param imageView
     * @param channelDecoration
     */
    public static void setChannelImage(Context context, CircleImageView imageView, ChannelDecoration channelDecoration) {
        int drawableResourceId = channelDecoration.getSourceDrawable();

        if (drawableResourceId != 0) {
            imageView.setImageResource(drawableResourceId);
        }
    }

}
