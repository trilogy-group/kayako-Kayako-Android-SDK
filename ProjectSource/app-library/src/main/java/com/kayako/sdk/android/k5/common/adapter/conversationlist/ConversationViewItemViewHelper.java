package com.kayako.sdk.android.k5.common.adapter.conversationlist;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.BotMessageHelper;
import com.kayako.sdk.android.k5.common.utils.DateTimeUtils;
import com.kayako.sdk.android.k5.common.utils.ImageUtils;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.helpcenter.articlelistpage.ArticleListContainerContract;

public class ConversationViewItemViewHelper {

    private ConversationViewItemViewHelper() {
    }

    public static void setUnreadCounter(TextView unreadCounterView, int unreadCount) {
        if (unreadCount == 0) {
            unreadCounterView.setVisibility(View.GONE);
        } else if (unreadCount > 9) {
            unreadCounterView.setVisibility(View.VISIBLE);
            unreadCounterView.setText("+9"); // Not adding to strings.xml because assuming numbers stay the same across languages
        } else {
            unreadCounterView.setVisibility(View.VISIBLE);
            unreadCounterView.setText(String.valueOf(unreadCount));
        }
    }

    public static void setFormattedTime(TextView timeView, long timeInMilliseconds) {
        timeView.setText(DateTimeUtils.formatShortDateTime(System.currentTimeMillis(), timeInMilliseconds));
    }

    public static void setTypingIndicator(ImageView typingLoader, View subjectLine, boolean isTyping) {
        if (isTyping) {
            Context context = Kayako.getApplicationContext();
            ImageUtils.setImage(context, typingLoader, null, R.drawable.ko__img_loading_dots);

            typingLoader.setVisibility(View.VISIBLE);
            subjectLine.setVisibility(View.GONE);
        } else {
            typingLoader.setVisibility(View.GONE);
            subjectLine.setVisibility(View.VISIBLE);
        }
    }

    public static void setAvatar(Context context, ImageView avatar, String avatarUrl) {
        if (avatarUrl == null) {
            ImageUtils.setAvatarImage(context, avatar, BotMessageHelper.getDefaultDrawableForConversation());
        } else {
            ImageUtils.setAvatarImage(context, avatar, avatarUrl);
        }
    }

    public static void setName(TextView view, String name) {
        if (name == null) {
            view.setText(MessengerPref.getInstance().getBrandName());
        } else {
            view.setText(name);
        }
    }
}
