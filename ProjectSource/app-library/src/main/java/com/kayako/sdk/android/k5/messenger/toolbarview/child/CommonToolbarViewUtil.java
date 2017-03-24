package com.kayako.sdk.android.k5.messenger.toolbarview.child;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.utils.ImageUtils;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.messenger.style.MessengerTemplateHelper;

public class CommonToolbarViewUtil {

    private CommonToolbarViewUtil() {
    }

    public static void setupCommonToolbar(View root, final Activity activity, View.OnClickListener onAvatarsClick) {
        root.findViewById(R.id.ko__messenger_toolbar_title);
        root.findViewById(R.id.ko__messenger_toolbar_subtitle);
        root.findViewById(R.id.ko__messenger_toolbar_back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                }
            }
        });

        root.findViewById(R.id.ko__messenger_toolbar_avatars).setOnClickListener(onAvatarsClick);
    }

    public static void setTitle(View mRoot, String brandName) {
        String title;

        if (brandName == null) { // Get default title
            title = Kayako.getApplicationContext().getResources().getString(R.string.ko__messenger_toolbar_default_title);
        } else {
            title = brandName;
        }

        ((TextView) mRoot.findViewById(R.id.ko__messenger_toolbar_title)).setText(title);
    }


    public static void setSubtitleForAverageResponseTime(View mRoot, Long averageReplyTimeInMilliseconds) {
        final long ONE_HOUR_IN_MILLISECONDS = 60 * 60 * 1000;
        final long ONE_DAY_IN_MILLISECONDS = 24 * ONE_HOUR_IN_MILLISECONDS;

        String subtitle;
        if (averageReplyTimeInMilliseconds == null || averageReplyTimeInMilliseconds == -1L || averageReplyTimeInMilliseconds == 0L) { // Get default subtitle
            // TODO: Check if correct DEFAULT to show?
            subtitle = null; // Show nothing if unknown
        } else if (Math.round(averageReplyTimeInMilliseconds / ONE_DAY_IN_MILLISECONDS) > 1) { // replies in more than one day
            subtitle = String.format(Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_subtitle_average_reply_time_in_days), Math.round(averageReplyTimeInMilliseconds / ONE_DAY_IN_MILLISECONDS));
        } else if (Math.round(averageReplyTimeInMilliseconds / ONE_DAY_IN_MILLISECONDS) == 1) { // replies in about 1.4 days time (1.5 rounds off to 2)
            subtitle = Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_subtitle_average_reply_time_in_a_day);
        } else {
            subtitle = String.format(Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_subtitle_average_reply_time_in_hours), Math.round(averageReplyTimeInMilliseconds / ONE_HOUR_IN_MILLISECONDS));
        }

        ((TextView) mRoot.findViewById(R.id.ko__messenger_toolbar_subtitle)).setText(subtitle);
    }

    public static void setLastActiveAgentAvatars(View mRoot, ActiveUser user1, ActiveUser user2, ActiveUser user3) {
        // TODO: Name of Users?
        ImageView imageView1 = (ImageView) mRoot.findViewById(R.id.ko__messenger_toolbar_avatar1);
        ImageView imageView2 = (ImageView) mRoot.findViewById(R.id.ko__messenger_toolbar_avatar2);
        ImageView imageView3 = (ImageView) mRoot.findViewById(R.id.ko__messenger_toolbar_avatar3);

        setLastActiveAgentAvatars(imageView1, user1);
        setLastActiveAgentAvatars(imageView2, user2);
        setLastActiveAgentAvatars(imageView3, user3);


        TextView textView = (TextView) mRoot.findViewById(R.id.ko__messenger_toolbar_avatar_caption_text);
        if (textView != null) { // available in expandable, not collapsed view

            // TODO: Currently setting everyone as online.
            String captionText;
            if (user1 != null && user1.getFullName() != null &&
                    user2 != null && user2.getFullName() != null &&
                    user3 != null && user3.getFullName() != null) {
                captionText = String.format(Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_avatar_caption_text_three_agent),
                        extractFirstName(user1.getFullName()),
                        extractFirstName(user2.getFullName()),
                        extractFirstName(user3.getFullName()));
            } else if (user1 != null && user1.getFullName() != null &&
                    user2 != null && user2.getFullName() != null) {
                captionText = String.format(Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_avatar_caption_text_two_agent),
                        extractFirstName(user1.getFullName()),
                        extractFirstName(user2.getFullName()));
            } else if (user1 != null && user1.getFullName() != null) {
                captionText = String.format(Kayako.getApplicationContext().getString(R.string.ko__messenger_toolbar_avatar_caption_text_one_agent),
                        extractFirstName(user1.getFullName()));
            } else {
                captionText = null;
            }

            if (captionText == null) {
                textView.setVisibility(View.GONE);
            } else {
                textView.setVisibility(View.VISIBLE);
                textView.setText(captionText);
            }
        }
    }

    public static void customizeColorsToMatchMessengerStyle(View mRoot) {
        MessengerTemplateHelper.applyTextColor(((TextView) mRoot.findViewById(R.id.ko__messenger_toolbar_subtitle)));
        MessengerTemplateHelper.applyTextColor(((TextView) mRoot.findViewById(R.id.ko__messenger_toolbar_title)));
    }

    public static void customizeColorsToMatchMessengerStyleForExpandedToolbar(View mRoot) {
        MessengerTemplateHelper.applyTextColor(((TextView) mRoot.findViewById(R.id.ko__messenger_toolbar_avatar_caption_text)));
        MessengerTemplateHelper.applyBackgroundColor(mRoot.findViewById(R.id.ko__messenger_toolbar_avatar_separator));
    }

    private static String extractFirstName(String fullName) {
        if (fullName == null) {
            return null;
        }

        // trim extra spaces
        fullName = fullName.trim();

        // break up name by spaces
        String[] parts = fullName.split(" ");
        if (parts.length > 0) {
            return parts[0];
        } else {
            return fullName;
        }
    }

    private static void setLastActiveAgentAvatars(ImageView imageView, ActiveUser user) {
        if (user != null && user.getAvatar() != null) {
            ImageUtils.setAvatarImage(Kayako.getApplicationContext(), imageView, user.getAvatar());
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
    }

}
