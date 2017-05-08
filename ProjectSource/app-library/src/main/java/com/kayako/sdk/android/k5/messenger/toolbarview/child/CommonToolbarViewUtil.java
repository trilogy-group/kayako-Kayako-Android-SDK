package com.kayako.sdk.android.k5.messenger.toolbarview.child;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.LastActiveAgentsData;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.ConversationStarterHelper;
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
        if (brandName == null) { // Get default title
            throw new IllegalArgumentException("Brand Name can not be null!");
        }

        ((TextView) mRoot.findViewById(R.id.ko__messenger_toolbar_title)).setText(brandName);
    }

    public static void setSubtitleForAverageResponseTime(View mRoot, Long averageReplyTimeInMilliseconds) {
        String subtitle = ConversationStarterHelper.getAverageResponseTimeCaption(averageReplyTimeInMilliseconds);

        TextView subtitleView = ((TextView) mRoot.findViewById(R.id.ko__messenger_toolbar_subtitle));

        if (TextUtils.isEmpty(subtitle)) {
            subtitleView.setVisibility(View.GONE);
        } else {
            subtitleView.setVisibility(View.VISIBLE);
            subtitleView.setText(subtitle);
        }
    }

    public static void setLastActiveAgentAvatars(View mRoot, LastActiveAgentsData lastActiveAgentsData) {
        // TODO: Name of Users?
        ImageView imageView1 = (ImageView) mRoot.findViewById(R.id.ko__messenger_toolbar_avatar1);
        ImageView imageView2 = (ImageView) mRoot.findViewById(R.id.ko__messenger_toolbar_avatar2);
        ImageView imageView3 = (ImageView) mRoot.findViewById(R.id.ko__messenger_toolbar_avatar3);

        ConversationStarterHelper.setAgentAvatar(imageView1, lastActiveAgentsData.getUser1());
        ConversationStarterHelper.setAgentAvatar(imageView2, lastActiveAgentsData.getUser2());
        ConversationStarterHelper.setAgentAvatar(imageView3, lastActiveAgentsData.getUser3());

        TextView textView = (TextView) mRoot.findViewById(R.id.ko__messenger_toolbar_avatar_caption_text);
        if (textView != null) { // available in expandable, not collapsed view
            String captionText = ConversationStarterHelper.getLastActiveAgentsCaption(lastActiveAgentsData);
            ConversationStarterHelper.setCaptionText(captionText, textView);
        }
    }

    public static void setOnlyTitle(View root, String title) {
        if (root.findViewById(R.id.ko__messenger_toolbar_avatar_caption_text) != null) {
            throw new IllegalStateException("This method should only be called on a collapsed view!");
        }

        TextView titleView = ((TextView) root.findViewById(R.id.ko__messenger_toolbar_title));
        titleView.setVisibility(View.VISIBLE);
        titleView.setText(title);

        root.findViewById(R.id.ko__messenger_toolbar_subtitle).setVisibility(View.GONE);
        root.findViewById(R.id.ko__messenger_toolbar_avatars).setVisibility(View.GONE);
    }


    public static void customizeColorsToMatchMessengerStyle(View mRoot) {
        MessengerTemplateHelper.applyTextColor(((TextView) mRoot.findViewById(R.id.ko__messenger_toolbar_subtitle)));
        MessengerTemplateHelper.applyTextColor(((TextView) mRoot.findViewById(R.id.ko__messenger_toolbar_title)));
    }

    public static void customizeColorsToMatchMessengerStyleForExpandedToolbar(View mRoot) {
        MessengerTemplateHelper.applyTextColor(((TextView) mRoot.findViewById(R.id.ko__messenger_toolbar_avatar_caption_text)));
        MessengerTemplateHelper.applyBackgroundColor(mRoot.findViewById(R.id.ko__messenger_toolbar_avatar_separator));
    }
}
