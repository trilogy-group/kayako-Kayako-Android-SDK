package com.kayako.sdk.android.k5.common.adapter.messengerlist;

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
import java.util.Map;

public class MessengerAdapter extends EndlessRecyclerViewScrollAdapter {

    private OnItemClickListener mItemClickListener;
    private OnAvatarClickListenr mAvatarClickListener;

    public MessengerAdapter(List<BaseListItem> itemList) {
        super(itemList);
    }

    public MessengerAdapter(List<BaseListItem> itemList, OnItemClickListener itemClickListener, OnAvatarClickListenr avatarClickListener) {
        super(itemList);
        setOnItemClickListener(itemClickListener);
        setOnAvatarClickListener(avatarClickListener);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public void setOnAvatarClickListener(OnAvatarClickListenr listener) {
        mAvatarClickListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO: Header/Footer Implementation
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case MessengerListType.SIMPLE_MESSAGE_SELF:
                final SimpleMessageSelfListItem simpleMessageSelfListItem = (SimpleMessageSelfListItem) getData().get(position);
                final SimpleMessageSelfViewHolder simpleMessageSelfViewHolder = (SimpleMessageSelfViewHolder) viewHolder;

                simpleMessageSelfViewHolder.message.setText(simpleMessageSelfListItem.getMessage());
                ImageUtils.setImage(Kayako.getApplicationContext(), simpleMessageSelfViewHolder.avatar, simpleMessageSelfListItem.getAvatarUrl());

                setAvatarClickListenerOnView(simpleMessageSelfViewHolder.avatar, simpleMessageSelfListItem.getItemType(), simpleMessageSelfListItem.getData());
                setItemClickListenerOnView(simpleMessageSelfViewHolder.itemView, simpleMessageSelfListItem.getItemType(), simpleMessageSelfListItem.getData());

                break;

            case MessengerListType.SIMPLE_MESSAGE_OTHER:
                SimpleMessageOtherListItem simpleMessageOtherListItem = (SimpleMessageOtherListItem) getData().get(position);
                SimpleMessageOtherViewHolder simpleMessageOtherViewHolder = (SimpleMessageOtherViewHolder) viewHolder;

                simpleMessageOtherViewHolder.message.setText(simpleMessageOtherListItem.getMessage());
                ImageUtils.setImage(Kayako.getApplicationContext(), simpleMessageOtherViewHolder.avatar, simpleMessageOtherListItem.getAvatarUrl());

                setAvatarClickListenerOnView(simpleMessageOtherViewHolder.avatar, simpleMessageOtherListItem.getItemType(), simpleMessageOtherListItem.getData());
                setItemClickListenerOnView(simpleMessageOtherViewHolder.itemView, simpleMessageOtherListItem.getItemType(), simpleMessageOtherListItem.getData());

                break;

            case MessengerListType.SIMPLE_MESSAGE_CONTINUED_SELF:
                SimpleMessageContinuedSelfListItem simpleMessageContinuedSelfListItem = (SimpleMessageContinuedSelfListItem) getData().get(position);
                SimpleMessageContinuedSelfViewHolder simpleMessageContinuedSelfViewHolder = (SimpleMessageContinuedSelfViewHolder) viewHolder;

                simpleMessageContinuedSelfViewHolder.message.setText(simpleMessageContinuedSelfListItem.getMessage());

                setItemClickListenerOnView(simpleMessageContinuedSelfViewHolder.itemView, simpleMessageContinuedSelfListItem.getItemType(), simpleMessageContinuedSelfListItem.getData());

                break;

            case MessengerListType.SIMPLE_MESSAGE_CONTINUED_OTHER:
                SimpleMessageContinuedOtherListItem simpleMessageContinuedOtherListItem = (SimpleMessageContinuedOtherListItem) getData().get(position);
                SimpleMessageContinuedOtherViewHolder simpleMessageContinuedOtherViewHolder = (SimpleMessageContinuedOtherViewHolder) viewHolder;

                simpleMessageContinuedOtherViewHolder.message.setText(simpleMessageContinuedOtherListItem.getMessage());

                setItemClickListenerOnView(simpleMessageContinuedOtherViewHolder.itemView, simpleMessageContinuedOtherListItem.getItemType(), simpleMessageContinuedOtherListItem.getData());
                break;

            default:
                super.onBindViewHolder(viewHolder, position);
                break;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case MessengerListType.SIMPLE_MESSAGE_SELF:
                View simpleMessageSelfView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.ko__list_messenger_simple_message_self, parent, false);
                return new SimpleMessageSelfViewHolder(simpleMessageSelfView);

            case MessengerListType.SIMPLE_MESSAGE_OTHER:
                View simpleMessageOtherView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.ko__list_messenger_simple_message_other, parent, false);
                return new SimpleMessageOtherViewHolder(simpleMessageOtherView);

            case MessengerListType.SIMPLE_MESSAGE_CONTINUED_SELF:
                View simpleMessageContinuedSelfView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ko__list_messenger_simple_message_continued_self, parent, false);
                return new SimpleMessageContinuedSelfViewHolder(simpleMessageContinuedSelfView);

            case MessengerListType.SIMPLE_MESSAGE_CONTINUED_OTHER:
                View simpleMessageContinuedOtherView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ko__list_messenger_simple_message_continued_other, parent, false);
                return new SimpleMessageContinuedOtherViewHolder(simpleMessageContinuedOtherView);

            default:
                return super.onCreateViewHolder(parent, viewType);
        }
    }

    private void setAvatarClickListenerOnView(View view, final int itemType, final Map<String, Object> data) {
        if (mAvatarClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAvatarClickListener.OnClickAvatar(itemType, data);
                }
            });
        }
    }

    private void setItemClickListenerOnView(View view, final int itemType, final Map<String, Object> data) {
        if (mItemClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onClickItem(itemType, data);
                }
            });
        }
    }

    public interface OnItemClickListener {
        /**
         * Called when an item in the Messenger List is clicked
         *
         * @param messageType One of the List Item Types specified in MessengerListType interface
         * @param messageData Map of data sent via the adapter while initializing the list
         */
        void onClickItem(int messageType, Map<String, Object> messageData);
    }

    public interface OnAvatarClickListenr {
        /**
         * Called when an avatar in the Messenger List is clicked
         *
         * @param messageType
         * @param messageData
         */
        void OnClickAvatar(int messageType, Map<String, Object> messageData);
    }
}
