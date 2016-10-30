package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollAdapter;
import com.kayako.sdk.android.k5.common.utils.DateTimeUtils;
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

                simpleMessageSelfViewHolder.message.setText(Html.fromHtml(simpleMessageSelfListItem.getMessage()));
                ImageUtils.setAvatarImage(Kayako.getApplicationContext(), simpleMessageSelfViewHolder.avatar, simpleMessageSelfListItem.getAvatarUrl());

                if (simpleMessageSelfListItem.getTime() == 0) {
                    simpleMessageSelfViewHolder.time.setVisibility(View.GONE);
                } else {
                    simpleMessageSelfViewHolder.time.setVisibility(View.VISIBLE);
                    simpleMessageSelfViewHolder.time.setText(DateTimeUtils.formatTime(Kayako.getApplicationContext(), simpleMessageSelfListItem.getTime()));
                }

                setAvatarClickListenerOnView(simpleMessageSelfViewHolder.avatar, simpleMessageSelfListItem.getItemType(), simpleMessageSelfListItem.getData());
                setItemClickListenerOnView(simpleMessageSelfViewHolder.itemView, simpleMessageSelfListItem.getItemType(), simpleMessageSelfListItem.getData());

                break;

            case MessengerListType.SIMPLE_MESSAGE_OTHER:
                SimpleMessageOtherListItem simpleMessageOtherListItem = (SimpleMessageOtherListItem) getData().get(position);
                SimpleMessageOtherViewHolder simpleMessageOtherViewHolder = (SimpleMessageOtherViewHolder) viewHolder;

                simpleMessageOtherViewHolder.message.setText(Html.fromHtml(simpleMessageOtherListItem.getMessage()));
                ImageUtils.setAvatarImage(Kayako.getApplicationContext(), simpleMessageOtherViewHolder.avatar, simpleMessageOtherListItem.getAvatarUrl());

                if (simpleMessageOtherListItem.getTime() == 0) {
                    simpleMessageOtherViewHolder.time.setVisibility(View.GONE);
                } else {
                    simpleMessageOtherViewHolder.time.setVisibility(View.VISIBLE);
                    simpleMessageOtherViewHolder.time.setText(DateTimeUtils.formatTime(Kayako.getApplicationContext(), simpleMessageOtherListItem.getTime()));
                }


                setAvatarClickListenerOnView(simpleMessageOtherViewHolder.avatar, simpleMessageOtherListItem.getItemType(), simpleMessageOtherListItem.getData());
                setItemClickListenerOnView(simpleMessageOtherViewHolder.itemView, simpleMessageOtherListItem.getItemType(), simpleMessageOtherListItem.getData());

                break;

            case MessengerListType.SIMPLE_MESSAGE_CONTINUED_SELF:
                SimpleMessageContinuedSelfListItem simpleMessageContinuedSelfListItem = (SimpleMessageContinuedSelfListItem) getData().get(position);
                SimpleMessageContinuedSelfViewHolder simpleMessageContinuedSelfViewHolder = (SimpleMessageContinuedSelfViewHolder) viewHolder;

                simpleMessageContinuedSelfViewHolder.message.setText(Html.fromHtml(simpleMessageContinuedSelfListItem.getMessage()));

                if (simpleMessageContinuedSelfListItem.getTime() == 0) {
                    simpleMessageContinuedSelfViewHolder.time.setVisibility(View.GONE);
                } else {
                    simpleMessageContinuedSelfViewHolder.time.setVisibility(View.VISIBLE);
                    simpleMessageContinuedSelfViewHolder.time.setText(DateTimeUtils.formatTime(Kayako.getApplicationContext(), simpleMessageContinuedSelfListItem.getTime()));
                }

                setItemClickListenerOnView(simpleMessageContinuedSelfViewHolder.itemView, simpleMessageContinuedSelfListItem.getItemType(), simpleMessageContinuedSelfListItem.getData());

                break;

            case MessengerListType.SIMPLE_MESSAGE_CONTINUED_OTHER:
                SimpleMessageContinuedOtherListItem simpleMessageContinuedOtherListItem = (SimpleMessageContinuedOtherListItem) getData().get(position);
                SimpleMessageContinuedOtherViewHolder simpleMessageContinuedOtherViewHolder = (SimpleMessageContinuedOtherViewHolder) viewHolder;

                simpleMessageContinuedOtherViewHolder.message.setText(Html.fromHtml(simpleMessageContinuedOtherListItem.getMessage()));

                if (simpleMessageContinuedOtherListItem.getTime() == 0) {
                    simpleMessageContinuedOtherViewHolder.time.setVisibility(View.GONE);
                } else {
                    simpleMessageContinuedOtherViewHolder.time.setVisibility(View.VISIBLE);
                    simpleMessageContinuedOtherViewHolder.time.setText(DateTimeUtils.formatTime(Kayako.getApplicationContext(), simpleMessageContinuedOtherListItem.getTime()));
                }

                setItemClickListenerOnView(simpleMessageContinuedOtherViewHolder.itemView, simpleMessageContinuedOtherListItem.getItemType(), simpleMessageContinuedOtherListItem.getData());
                break;

            case MessengerListType.ATTACHMENT_MESSAGE_SELF:
                AttachmentMessageSelfViewHolder attachmentMessageSelfViewHolder = (AttachmentMessageSelfViewHolder) viewHolder;
                AttachmentMessageSelfListItem attachmentMessageSelfListItem = (AttachmentMessageSelfListItem) getData().get(position);

                if (attachmentMessageSelfListItem.getTime() == 0) {
                    attachmentMessageSelfViewHolder.time.setVisibility(View.GONE);
                } else {
                    attachmentMessageSelfViewHolder.time.setVisibility(View.VISIBLE);
                    attachmentMessageSelfViewHolder.time.setText(DateTimeUtils.formatTime(Kayako.getApplicationContext(), attachmentMessageSelfListItem.getTime()));
                }

                if (TextUtils.isEmpty(attachmentMessageSelfListItem.getMessage())) {
                    attachmentMessageSelfViewHolder.message.setVisibility(View.GONE);
                } else {
                    attachmentMessageSelfViewHolder.message.setVisibility(View.VISIBLE);
                    attachmentMessageSelfViewHolder.message.setText(Html.fromHtml(attachmentMessageSelfListItem.getMessage()));
                }

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

                if (attachmentMessageOtherListItem.getTime() == 0) {
                    attachmentMessageOtherViewHolder.time.setVisibility(View.GONE);
                } else {
                    attachmentMessageOtherViewHolder.time.setVisibility(View.VISIBLE);
                    attachmentMessageOtherViewHolder.time.setText(DateTimeUtils.formatTime(Kayako.getApplicationContext(), attachmentMessageOtherListItem.getTime()));
                }

                if (TextUtils.isEmpty(attachmentMessageOtherListItem.getMessage())) {
                    attachmentMessageOtherViewHolder.message.setVisibility(View.GONE);
                } else {
                    attachmentMessageOtherViewHolder.message.setVisibility(View.VISIBLE);
                    attachmentMessageOtherViewHolder.message.setText(Html.fromHtml(attachmentMessageOtherListItem.getMessage()));
                }

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

                if (attachmentMessageContinuedSelfListItem.getTime() == 0) {
                    attachmentMessageContinuedSelfViewHolder.time.setVisibility(View.GONE);
                } else {
                    attachmentMessageContinuedSelfViewHolder.time.setVisibility(View.VISIBLE);
                    attachmentMessageContinuedSelfViewHolder.time.setText(DateTimeUtils.formatTime(Kayako.getApplicationContext(), attachmentMessageContinuedSelfListItem.getTime()));
                }

                if (TextUtils.isEmpty(attachmentMessageContinuedSelfListItem.getMessage())) {
                    attachmentMessageContinuedSelfViewHolder.message.setVisibility(View.GONE);
                } else {
                    attachmentMessageContinuedSelfViewHolder.message.setVisibility(View.VISIBLE);
                    attachmentMessageContinuedSelfViewHolder.message.setText(Html.fromHtml(attachmentMessageContinuedSelfListItem.getMessage()));
                }

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

                if (attachmentMessageContinuedOtherListItem.getTime() == 0) {
                    attachmentMessageContinuedOtherViewHolder.time.setVisibility(View.GONE);
                } else {
                    attachmentMessageContinuedOtherViewHolder.time.setVisibility(View.VISIBLE);
                    attachmentMessageContinuedOtherViewHolder.time.setText(DateTimeUtils.formatTime(Kayako.getApplicationContext(), attachmentMessageContinuedOtherListItem.getTime()));
                }

                if (TextUtils.isEmpty(attachmentMessageContinuedOtherListItem.getMessage())) {
                    attachmentMessageContinuedOtherViewHolder.message.setVisibility(View.GONE);
                } else {
                    attachmentMessageContinuedOtherViewHolder.message.setVisibility(View.VISIBLE);
                    attachmentMessageContinuedOtherViewHolder.message.setText(Html.fromHtml(attachmentMessageContinuedOtherListItem.getMessage()));
                }

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

            case MessengerListType.DATE_SEPARATOR:
                DateSeparatorViewHolder dateSeparatorViewHolder = (DateSeparatorViewHolder) viewHolder;
                DateSeparatorListItem dateSeparatorListItem = (DateSeparatorListItem) getData().get(position);

                dateSeparatorViewHolder.time.setText(DateTimeUtils.formatDate(Kayako.getApplicationContext(), dateSeparatorListItem.getTimeInMilliseconds()));
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

            case MessengerListType.DATE_SEPARATOR:
                View dateSeparatorView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ko__list_messenger_date_separator, parent, false);
                return new DateSeparatorViewHolder(dateSeparatorView);

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
