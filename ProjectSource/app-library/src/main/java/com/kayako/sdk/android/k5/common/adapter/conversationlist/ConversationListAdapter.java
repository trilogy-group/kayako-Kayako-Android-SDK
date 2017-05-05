package com.kayako.sdk.android.k5.common.adapter.conversationlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollAdapter;
import com.kayako.sdk.android.k5.common.utils.ImageUtils;
import com.kayako.sdk.android.k5.core.Kayako;

import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ConversationListAdapter extends EndlessRecyclerViewScrollAdapter {


    private OnClickConversationListener mListener;

    public ConversationListAdapter(List<BaseListItem> items, OnClickConversationListener listener) {
        super(items);
        mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return getData().get(position).getItemType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ConversationListType.CONVERSATION_LIST_ITEM:
                View viewItem = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.ko__list_conversation, parent, false);
                return new ConversationItemViewHolder(viewItem);
            default:
                return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case ConversationListType.CONVERSATION_LIST_ITEM:
                ConversationItemViewHolder conversationViewHolder = (ConversationItemViewHolder) viewHolder;
                final ConversationListItem conversationListItem = (ConversationListItem) getData().get(position);

                Context context = Kayako.getApplicationContext();

                conversationViewHolder.subject.setText(conversationListItem.conversationViewModel.getSubject());
                conversationViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ConversationListItem item = (ConversationListItem) getData().get(viewHolder.getAdapterPosition());
                        mListener.onClickConversation(conversationListItem.conversationViewModel.getConversationId());
                    }
                });

                ConversationViewItemViewHelper.setName(conversationViewHolder.name, conversationListItem.conversationViewModel.getName());
                ConversationViewItemViewHelper.setAvatar(context, conversationViewHolder.avatar, conversationListItem.conversationViewModel.getAvatarUrl());
                ConversationViewItemViewHelper.setFormattedTime(conversationViewHolder.time, conversationListItem.conversationViewModel.getTimeInMilleseconds());
                ConversationViewItemViewHelper.setUnreadCounter(conversationViewHolder.unreadCounter, conversationListItem.conversationViewModel.getUnreadCount());
                ConversationViewItemViewHelper.setTypingIndicator(conversationViewHolder.typingLoader, conversationViewHolder.subjectLine, conversationListItem.conversationViewModel.getLastAgentReplierTyping().isTyping());
                break;

            default:
                super.onBindViewHolder(viewHolder, position);
        }
    }

    public interface OnClickConversationListener {
        void onClickConversation(long conversationId);
    }

}