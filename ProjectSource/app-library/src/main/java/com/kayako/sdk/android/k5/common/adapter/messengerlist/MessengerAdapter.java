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
    private OnAvatarClickListener mAvatarClickListener;
    private OnAttachmentClickListener mAttachmentClickListener;

    public MessengerAdapter(List<BaseListItem> itemList) {
        super(itemList);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public void setOnAvatarClickListener(OnAvatarClickListener listener) {
        mAvatarClickListener = listener;
    }

    public void setOnAttachmentClickListener(OnAttachmentClickListener listener) {
        mAttachmentClickListener = listener;
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
                ImageUtils.setAvatarImage(Kayako.getApplicationContext(), simpleMessageSelfViewHolder.avatar, simpleMessageSelfListItem.getAvatarUrl());

                setAvatarClickListenerOnView(simpleMessageSelfViewHolder.avatar, simpleMessageSelfListItem.getItemType(), simpleMessageSelfListItem.getData());
                setItemClickListenerOnView(simpleMessageSelfViewHolder.itemView, simpleMessageSelfListItem.getItemType(), simpleMessageSelfListItem.getData());

                break;

            case MessengerListType.SIMPLE_MESSAGE_OTHER:
                SimpleMessageOtherListItem simpleMessageOtherListItem = (SimpleMessageOtherListItem) getData().get(position);
                SimpleMessageOtherViewHolder simpleMessageOtherViewHolder = (SimpleMessageOtherViewHolder) viewHolder;

                simpleMessageOtherViewHolder.message.setText(simpleMessageOtherListItem.getMessage());
                ImageUtils.setAvatarImage(Kayako.getApplicationContext(), simpleMessageOtherViewHolder.avatar, simpleMessageOtherListItem.getAvatarUrl());

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

            case MessengerListType.ATTACHMENT_MESSAGE_SELF:
                AttachmentMessageSelfViewHolder attachmentMessageSelfViewHolder = (AttachmentMessageSelfViewHolder) viewHolder;
                AttachmentMessageSelfListItem attachmentMessageSelfListItem = (AttachmentMessageSelfListItem) getData().get(position);

                attachmentMessageSelfViewHolder.message.setText(attachmentMessageSelfListItem.getMessage());

                if (attachmentMessageSelfListItem.getAttachmentThumbnailUrl() == null) {
                    attachmentMessageSelfViewHolder.attachmentPlaceholder.setVisibility(View.VISIBLE);
                    attachmentMessageSelfViewHolder.attachmentThumbnail.setVisibility(View.GONE);
                } else {
                    attachmentMessageSelfViewHolder.attachmentPlaceholder.setVisibility(View.GONE);
                    attachmentMessageSelfViewHolder.attachmentThumbnail.setVisibility(View.VISIBLE);
                    ImageUtils.setImage(Kayako.getApplicationContext(), attachmentMessageSelfViewHolder.attachmentThumbnail, attachmentMessageSelfListItem.getAttachmentThumbnailUrl());
                }
                setAttachmentClickListenerOnView(attachmentMessageSelfViewHolder.attachmentThumbnail, attachmentMessageSelfListItem.getItemType(), attachmentMessageSelfListItem.getData());
                setAttachmentClickListenerOnView(attachmentMessageSelfViewHolder.attachmentPlaceholder, attachmentMessageSelfListItem.getItemType(), attachmentMessageSelfListItem.getData());

                break;

            case MessengerListType.ATTACHMENT_MESSAGE_OTHER:
                AttachmentMessageOtherViewHolder attachmentMessageOtherViewHolder = (AttachmentMessageOtherViewHolder) viewHolder;
                AttachmentMessageOtherListItem attachmentMessageOtherListItem = (AttachmentMessageOtherListItem) getData().get(position);

                attachmentMessageOtherViewHolder.message.setText(attachmentMessageOtherListItem.getMessage());

                if (attachmentMessageOtherListItem.getAttachmentThumbnailUrl() == null) {
                    attachmentMessageOtherViewHolder.attachmentPlaceholder.setVisibility(View.VISIBLE);
                    attachmentMessageOtherViewHolder.attachmentThumbnail.setVisibility(View.GONE);
                } else {
                    attachmentMessageOtherViewHolder.attachmentPlaceholder.setVisibility(View.GONE);
                    attachmentMessageOtherViewHolder.attachmentThumbnail.setVisibility(View.VISIBLE);
                    ImageUtils.setImage(Kayako.getApplicationContext(), attachmentMessageOtherViewHolder.attachmentThumbnail, attachmentMessageOtherListItem.getAttachmentThumbnailUrl());
                }
                setAttachmentClickListenerOnView(attachmentMessageOtherViewHolder.attachmentThumbnail, attachmentMessageOtherListItem.getItemType(), attachmentMessageOtherListItem.getData());
                setAttachmentClickListenerOnView(attachmentMessageOtherViewHolder.attachmentPlaceholder, attachmentMessageOtherListItem.getItemType(), attachmentMessageOtherListItem.getData());

                break;

            case MessengerListType.ATTACHMENT_MESSAGE_CONTINUED_SELF:
                AttachmentMessageContinuedSelfViewHolder attachmentMessageContinuedSelfViewHolder = (AttachmentMessageContinuedSelfViewHolder) viewHolder;
                AttachmentMessageContinuedSelfListItem attachmentMessageContinuedSelfListItem = (AttachmentMessageContinuedSelfListItem) getData().get(position);

                attachmentMessageContinuedSelfViewHolder.message.setText(attachmentMessageContinuedSelfListItem.getMessage());

                if (attachmentMessageContinuedSelfListItem.getAttachmentThumbnailUrl() == null) {
                    attachmentMessageContinuedSelfViewHolder.attachmentPlaceholder.setVisibility(View.VISIBLE);
                    attachmentMessageContinuedSelfViewHolder.attachmentThumbnail.setVisibility(View.GONE);
                } else {
                    attachmentMessageContinuedSelfViewHolder.attachmentPlaceholder.setVisibility(View.GONE);
                    attachmentMessageContinuedSelfViewHolder.attachmentThumbnail.setVisibility(View.VISIBLE);
                    ImageUtils.setImage(Kayako.getApplicationContext(), attachmentMessageContinuedSelfViewHolder.attachmentThumbnail, attachmentMessageContinuedSelfListItem.getAttachmentThumbnailUrl());
                }
                setAttachmentClickListenerOnView(attachmentMessageContinuedSelfViewHolder.attachmentThumbnail, attachmentMessageContinuedSelfListItem.getItemType(), attachmentMessageContinuedSelfListItem.getData());
                setAttachmentClickListenerOnView(attachmentMessageContinuedSelfViewHolder.attachmentPlaceholder, attachmentMessageContinuedSelfListItem.getItemType(), attachmentMessageContinuedSelfListItem.getData());

                break;

            case MessengerListType.ATTACHMENT_MESSAGE_CONTINUED_OTHER:
                AttachmentMessageContinuedOtherViewHolder attachmentMessageContinuedOtherViewHolder = (AttachmentMessageContinuedOtherViewHolder) viewHolder;
                AttachmentMessageContinuedOtherListItem attachmentMessageContinuedOtherListItem = (AttachmentMessageContinuedOtherListItem) getData().get(position);

                attachmentMessageContinuedOtherViewHolder.message.setText(attachmentMessageContinuedOtherListItem.getMessage());

                if (attachmentMessageContinuedOtherListItem.getAttachmentThumbnailUrl() == null) {
                    attachmentMessageContinuedOtherViewHolder.attachmentPlaceholder.setVisibility(View.VISIBLE);
                    attachmentMessageContinuedOtherViewHolder.attachmentThumbnail.setVisibility(View.GONE);
                } else {
                    attachmentMessageContinuedOtherViewHolder.attachmentPlaceholder.setVisibility(View.GONE);
                    attachmentMessageContinuedOtherViewHolder.attachmentThumbnail.setVisibility(View.VISIBLE);
                    ImageUtils.setImage(Kayako.getApplicationContext(), attachmentMessageContinuedOtherViewHolder.attachmentThumbnail, attachmentMessageContinuedOtherListItem.getAttachmentThumbnailUrl());
                }

                setAttachmentClickListenerOnView(attachmentMessageContinuedOtherViewHolder.attachmentThumbnail, attachmentMessageContinuedOtherListItem.getItemType(), attachmentMessageContinuedOtherListItem.getData());
                setAttachmentClickListenerOnView(attachmentMessageContinuedOtherViewHolder.attachmentPlaceholder, attachmentMessageContinuedOtherListItem.getItemType(), attachmentMessageContinuedOtherListItem.getData());

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

            case MessengerListType.ATTACHMENT_MESSAGE_SELF:
                View attachmentMessageSelfView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ko__list_messenger_attachment_message_self, parent, false);
                return new AttachmentMessageSelfViewHolder(attachmentMessageSelfView);

            case MessengerListType.ATTACHMENT_MESSAGE_OTHER:
                View attachmentMessageOtherView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ko__list_messenger_attachment_message_other, parent, false);
                return new AttachmentMessageOtherViewHolder(attachmentMessageOtherView);

            case MessengerListType.ATTACHMENT_MESSAGE_CONTINUED_SELF:
                View attachmentMessageContinuedSelfView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ko__list_messenger_attachment_message_continued_self, parent, false);
                return new AttachmentMessageContinuedSelfViewHolder(attachmentMessageContinuedSelfView);

            case MessengerListType.ATTACHMENT_MESSAGE_CONTINUED_OTHER:
                View attachmentMessageContinuedOtherView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ko__list_messenger_attachment_message_continued_other, parent, false);
                return new AttachmentMessageContinuedOtherViewHolder(attachmentMessageContinuedOtherView);

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

    private void setAttachmentClickListenerOnView(View view, final int itemType, final Map<String, Object> data) {
        if (mAttachmentClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAttachmentClickListener.onClickAttachment(itemType, data);
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

    public interface OnAvatarClickListener {
        /**
         * Called when an avatar in the Messenger List is clicked
         *
         * @param messageType
         * @param messageData
         */
        void OnClickAvatar(int messageType, Map<String, Object> messageData);
    }

    public interface OnAttachmentClickListener {
        /**
         * Called when an attachment in the Messenger List is clicked
         *
         * @param messageType
         * @param messageData
         */
        void onClickAttachment(int messageType, Map<String, Object> messageData);
    }
}
