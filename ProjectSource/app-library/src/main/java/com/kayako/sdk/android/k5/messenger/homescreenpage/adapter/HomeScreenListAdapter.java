package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.header.HeaderListItem;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.header.HeaderViewHolder;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.BaseWidgetListItem;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.presence.PresenceWidgetListItem;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.presence.PresenceWidgetViewHolder;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.recentcases.OnClickRecentConversationListener;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.recentcases.RecentConversationAdapter;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.recentcases.RecentConversationsWidgetListItem;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.recentcases.RecentConversationsWidgetViewHolder;
import com.kayako.sdk.android.k5.messenger.style.ConversationStarterHelper;
import com.kayako.sdk.android.k5.messenger.style.MessengerTemplateHelper;

import java.util.ArrayList;
import java.util.List;

public class HomeScreenListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<BaseListItem> mItems;

    public HomeScreenListAdapter(List<BaseListItem> items) {
        if (items == null) {
            items = new ArrayList<>();
        }
        this.mItems = items;
    }

    public void replaceData(List<BaseListItem> newData) {
        if (newData == null) {
            newData = new ArrayList<>();
        }

        this.mItems = newData;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HomeScreenListType.WIDGET_HEADER:
                View headerView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.ko__list_home_screen_header, parent, false);
                return new HeaderViewHolder(headerView);

            case HomeScreenListType.WIDGET_PRESENCE:
                View presenceWidgetView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.ko__list_messenger_home_screen_widget, parent, false);
                return new PresenceWidgetViewHolder(presenceWidgetView);

            case HomeScreenListType.WIDGET_RECENT_CONVERSATIONS:
                View recentConversationWidgetView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.ko__list_messenger_home_screen_widget, parent, false);
                return new RecentConversationsWidgetViewHolder(recentConversationWidgetView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case HomeScreenListType.WIDGET_HEADER:
                HeaderListItem headerListItem = (HeaderListItem) mItems.get(position);
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
                headerViewHolder.title.setText(headerListItem.getTitle());
                headerViewHolder.subtitle.setText(headerListItem.getSubtitle());

                MessengerTemplateHelper.applyTextColor(headerViewHolder.title);
                MessengerTemplateHelper.applyTextColor(headerViewHolder.subtitle);
                break;

            case HomeScreenListType.WIDGET_PRESENCE:
                final PresenceWidgetListItem presenceWidgetListItem = (PresenceWidgetListItem) mItems.get(position);
                PresenceWidgetViewHolder presenceViewHolder = (PresenceWidgetViewHolder) holder;

                presenceViewHolder.title.setText(presenceWidgetListItem.getTitle());
                ConversationStarterHelper.setCaptionText(presenceWidgetListItem.getPresenceCaption(), presenceViewHolder.presenceCaptionTextView);
                ConversationStarterHelper.setAgentAvatar(presenceViewHolder.avatar1, presenceWidgetListItem.getAvatarUrl1());
                ConversationStarterHelper.setAgentAvatar(presenceViewHolder.avatar2, presenceWidgetListItem.getAvatarUrl2());
                ConversationStarterHelper.setAgentAvatar(presenceViewHolder.avatar3, presenceWidgetListItem.getAvatarUrl3());

                if (presenceWidgetListItem.hasActionButton()) {
                    presenceViewHolder.actionButton.setText(presenceWidgetListItem.getActionButtonLabel());
                    presenceViewHolder.actionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BaseWidgetListItem.OnClickActionListener listener = presenceWidgetListItem.getOnClickActionListener();
                            if (listener != null) {
                                listener.onClickActionButton();
                            }
                        }
                    });
                }
                break;

            case HomeScreenListType.WIDGET_RECENT_CONVERSATIONS:
                final RecentConversationsWidgetListItem recentConversationListItem = (RecentConversationsWidgetListItem) mItems.get(position);
                RecentConversationsWidgetViewHolder recentCasesWidgetViewHolder = (RecentConversationsWidgetViewHolder) holder;

                recentCasesWidgetViewHolder.title.setText(recentConversationListItem.getTitle());
                recentCasesWidgetViewHolder.actionButton.setText(recentConversationListItem.getActionButtonLabel());
                recentCasesWidgetViewHolder.actionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recentConversationListItem.getOnClickActionListener().onClickActionButton();
                    }
                });
                recentCasesWidgetViewHolder.recyclerView.setAdapter(new RecentConversationAdapter(
                        recentConversationListItem.getConversations(),
                        new OnClickRecentConversationListener() {
                            @Override
                            public void onClickRecentConversation(long conversationId) {
                                recentConversationListItem.getOnClickRecentConversationListener().onClickRecentConversation(conversationId);
                            }
                        }
                ));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getItemType();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
