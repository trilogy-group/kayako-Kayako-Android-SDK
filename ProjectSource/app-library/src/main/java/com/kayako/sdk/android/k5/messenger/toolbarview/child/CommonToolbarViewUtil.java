package com.kayako.sdk.android.k5.messenger.toolbarview.child;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.LastActiveAgentsData;
import com.kayako.sdk.android.k5.messenger.style.ConversationStarterHelper;
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
        ((TextView) mRoot.findViewById(R.id.ko__messenger_toolbar_subtitle))
                .setText(
                        ConversationStarterHelper.getAverageResponseTimeCaption(averageReplyTimeInMilliseconds)
                );
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

    public static void customizeColorsToMatchMessengerStyle(View mRoot) {
        MessengerTemplateHelper.applyTextColor(((TextView) mRoot.findViewById(R.id.ko__messenger_toolbar_subtitle)));
        MessengerTemplateHelper.applyTextColor(((TextView) mRoot.findViewById(R.id.ko__messenger_toolbar_title)));
    }

    public static void customizeColorsToMatchMessengerStyleForExpandedToolbar(View mRoot) {
        MessengerTemplateHelper.applyTextColor(((TextView) mRoot.findViewById(R.id.ko__messenger_toolbar_avatar_caption_text)));
        MessengerTemplateHelper.applyBackgroundColor(mRoot.findViewById(R.id.ko__messenger_toolbar_avatar_separator));
    }

}
