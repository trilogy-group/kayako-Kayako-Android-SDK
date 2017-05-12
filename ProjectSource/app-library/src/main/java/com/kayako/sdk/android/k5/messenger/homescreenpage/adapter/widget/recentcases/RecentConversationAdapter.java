package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.recentcases;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.conversationlist.ConversationViewItemViewHelper;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.ConversationViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecentConversationAdapter extends RecyclerView.Adapter {

    List<ConversationViewModel> recentConversationList = new ArrayList<>();
    OnClickRecentConversationListener onClickRecentConversationListener;

    public RecentConversationAdapter(List<ConversationViewModel> conversations, OnClickRecentConversationListener onClickRecentConversationListener) {
        this.recentConversationList = conversations;
        this.onClickRecentConversationListener = onClickRecentConversationListener;

        if (recentConversationList == null || onClickRecentConversationListener == null) {
            throw new IllegalArgumentException("Can't be null");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View recentConversationViewHolder = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ko__list_messengerhome_screen_widget_conversation, parent, false);
        return new RecentConversationViewHolder(recentConversationViewHolder);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecentConversationViewHolder viewHolder = (RecentConversationViewHolder) holder;
        final ConversationViewModel recentConversation = recentConversationList.get(position);

        Context context = Kayako.getApplicationContext();
        viewHolder.subject.setText(recentConversation.getSubject());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickRecentConversationListener != null) {
                    onClickRecentConversationListener.onClickRecentConversation(recentConversation.getConversationId());
                }
            }
        });

        ConversationViewItemViewHelper.setName(viewHolder.name, recentConversation.getName());
        ConversationViewItemViewHelper.setAvatar(context, viewHolder.avatar, recentConversation.getAvatarUrl());

        ConversationViewItemViewHelper.setUnreadCounter(viewHolder.unreadCounter, recentConversation.getUnreadCount());
        ConversationViewItemViewHelper.setFormattedTime(viewHolder.time, recentConversation.getTimeInMilleseconds());

        ConversationViewItemViewHelper.setTypingIndicator(
                viewHolder.typingLoader,
                viewHolder.subjectLine,
                recentConversation.getLastAgentReplierTyping().isTyping());

        if (position == getItemCount() - 1) {
            viewHolder.separator.setVisibility(View.GONE);
        } else {
            viewHolder.separator.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return recentConversationList.size();
    }
}