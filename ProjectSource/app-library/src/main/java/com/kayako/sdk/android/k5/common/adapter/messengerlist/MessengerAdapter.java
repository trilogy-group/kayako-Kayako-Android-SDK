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
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.BotMessageHelper;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.InputFieldEmailHelper;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.AttachmentMessageContinuedOtherListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.AttachmentMessageContinuedOtherViewHolder;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.AttachmentMessageContinuedSelfListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.AttachmentMessageContinuedSelfViewHolder;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.AttachmentMessageOtherListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.AttachmentMessageOtherViewHolder;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.AttachmentMessageSelfListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.AttachmentMessageSelfViewHolder;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.BotMessageListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.BotMessageViewHolder;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.DateSeparatorListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.DateSeparatorViewHolder;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.EmptyViewHolder;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputEmailListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputEmailViewHolder;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.SimpleMessageContinuedOtherListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.SimpleMessageContinuedOtherViewHolder;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.SimpleMessageContinuedSelfListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.SimpleMessageContinuedSelfViewHolder;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.SimpleMessageOtherListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.SimpleMessageOtherViewHolder;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.SimpleMessageSelfListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.SimpleMessageSelfViewHolder;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.UnreadSeparatorListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.UnreadSeparatorViewHolder;
import com.kayako.sdk.android.k5.common.utils.DateTimeUtils;
import com.kayako.sdk.android.k5.common.utils.ImageUtils;
import com.kayako.sdk.android.k5.core.Kayako;

import java.util.List;
import java.util.Map;

public class MessengerAdapter extends EndlessRecyclerViewScrollAdapter {

    private OnItemClickListener mItemClickListener;
    private OnAvatarClickListener mAvatarClickListener;
    private OnAttachmentClickListener mAttachmentClickListener;

    private static final int DEFAULT_PLACEHOLDER_ATTACHMENT_DARK = R.drawable.ko__img_attachment_placeholder_dark;
    private static final int DEFAULT_PLACEHOLDER_ATTACHMENT_LIGHT = R.drawable.ko__img_attachment_placeholder_light;

    public MessengerAdapter(List<BaseListItem> itemList) {
        super(itemList);
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

                if (simpleMessageSelfListItem.getChannel() == null) {
                    simpleMessageSelfViewHolder.channel.setVisibility(View.GONE);
                } else {
                    simpleMessageSelfViewHolder.channel.setVisibility(View.VISIBLE);
                    ImageUtils.setChannelImage(Kayako.getApplicationContext(), simpleMessageSelfViewHolder.channel, simpleMessageSelfListItem.getChannel());
                }

                if (simpleMessageSelfListItem.getTime() == 0) {
                    simpleMessageSelfViewHolder.time.setVisibility(View.GONE);
                } else {
                    simpleMessageSelfViewHolder.time.setVisibility(View.VISIBLE);
                    simpleMessageSelfViewHolder.time.setText(DateTimeUtils.formatTime(Kayako.getApplicationContext(), simpleMessageSelfListItem.getTime()));
                }

                setAvatarClickListenerOnView(simpleMessageSelfViewHolder.avatar, simpleMessageSelfListItem.getItemType(), simpleMessageSelfListItem.getId(), simpleMessageSelfListItem.getData());
                setItemClickListenerOnView(simpleMessageSelfViewHolder.itemView, simpleMessageSelfListItem.getItemType(), simpleMessageSelfListItem.getId(), simpleMessageSelfListItem.getData());

                break;

            case MessengerListType.SIMPLE_MESSAGE_OTHER:
                SimpleMessageOtherListItem simpleMessageOtherListItem = (SimpleMessageOtherListItem) getData().get(position);
                SimpleMessageOtherViewHolder simpleMessageOtherViewHolder = (SimpleMessageOtherViewHolder) viewHolder;

                simpleMessageOtherViewHolder.message.setText(Html.fromHtml(simpleMessageOtherListItem.getMessage()));
                ImageUtils.setAvatarImage(Kayako.getApplicationContext(), simpleMessageOtherViewHolder.avatar, simpleMessageOtherListItem.getAvatarUrl());

                if (simpleMessageOtherListItem.getChannel() == null) {
                    simpleMessageOtherViewHolder.channel.setVisibility(View.GONE);
                } else {
                    simpleMessageOtherViewHolder.channel.setVisibility(View.VISIBLE);
                    ImageUtils.setChannelImage(Kayako.getApplicationContext(), simpleMessageOtherViewHolder.channel, simpleMessageOtherListItem.getChannel());
                }

                if (simpleMessageOtherListItem.getTime() == 0) {
                    simpleMessageOtherViewHolder.time.setVisibility(View.GONE);
                } else {
                    simpleMessageOtherViewHolder.time.setVisibility(View.VISIBLE);
                    simpleMessageOtherViewHolder.time.setText(DateTimeUtils.formatTime(Kayako.getApplicationContext(), simpleMessageOtherListItem.getTime()));
                }

                setAvatarClickListenerOnView(simpleMessageOtherViewHolder.avatar, simpleMessageOtherListItem.getItemType(), simpleMessageOtherListItem.getId(), simpleMessageOtherListItem.getData());
                setItemClickListenerOnView(simpleMessageOtherViewHolder.itemView, simpleMessageOtherListItem.getItemType(), simpleMessageOtherListItem.getId(), simpleMessageOtherListItem.getData());
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

                setItemClickListenerOnView(simpleMessageContinuedSelfViewHolder.itemView, simpleMessageContinuedSelfListItem.getItemType(), simpleMessageContinuedSelfListItem.getId(), simpleMessageContinuedSelfListItem.getData());

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

                setItemClickListenerOnView(simpleMessageContinuedOtherViewHolder.itemView, simpleMessageContinuedOtherListItem.getItemType(), simpleMessageContinuedOtherListItem.getId(), simpleMessageContinuedOtherListItem.getData());
                break;

            case MessengerListType.ATTACHMENT_MESSAGE_SELF:
                AttachmentMessageSelfViewHolder attachmentMessageSelfViewHolder = (AttachmentMessageSelfViewHolder) viewHolder;
                AttachmentMessageSelfListItem attachmentMessageSelfListItem = (AttachmentMessageSelfListItem) getData().get(position);

                assert attachmentMessageSelfListItem.getAttachment() != null;

                ImageUtils.setAvatarImage(Kayako.getApplicationContext(), attachmentMessageSelfViewHolder.avatar, attachmentMessageSelfListItem.getAvatarUrl());

                if (attachmentMessageSelfListItem.getChannel() == null) {
                    attachmentMessageSelfViewHolder.channel.setVisibility(View.GONE);
                } else {
                    attachmentMessageSelfViewHolder.channel.setVisibility(View.VISIBLE);
                    ImageUtils.setChannelImage(Kayako.getApplicationContext(), attachmentMessageSelfViewHolder.channel, attachmentMessageSelfListItem.getChannel());
                }

                if (attachmentMessageSelfListItem.getTime() == 0) {
                    attachmentMessageSelfViewHolder.time.setVisibility(View.GONE);
                } else {
                    attachmentMessageSelfViewHolder.time.setVisibility(View.VISIBLE);
                    attachmentMessageSelfViewHolder.time.setText(DateTimeUtils.formatTime(Kayako.getApplicationContext(), attachmentMessageSelfListItem.getTime()));
                }

                if (TextUtils.isEmpty(attachmentMessageSelfListItem.getAttachment().getCaption())) {
                    attachmentMessageSelfViewHolder.message.setVisibility(View.GONE);
                } else {
                    attachmentMessageSelfViewHolder.message.setVisibility(View.VISIBLE);
                    attachmentMessageSelfViewHolder.message.setText(Html.fromHtml(attachmentMessageSelfListItem.getAttachment().getCaption()));
                }

                if (attachmentMessageSelfListItem.getAttachment().getThumbnailUrl() == null) {
                    attachmentMessageSelfViewHolder.attachmentPlaceholder.setVisibility(View.VISIBLE);
                    attachmentMessageSelfViewHolder.attachmentThumbnail.setVisibility(View.GONE);
                } else {
                    attachmentMessageSelfViewHolder.attachmentPlaceholder.setVisibility(View.GONE);
                    attachmentMessageSelfViewHolder.attachmentThumbnail.setVisibility(View.VISIBLE);
                    ImageUtils.setImage(Kayako.getApplicationContext(), attachmentMessageSelfViewHolder.attachmentThumbnail, attachmentMessageSelfListItem.getAttachment().getThumbnailUrl(), DEFAULT_PLACEHOLDER_ATTACHMENT_LIGHT);
                }

                setAvatarClickListenerOnView(attachmentMessageSelfViewHolder.avatar, attachmentMessageSelfListItem.getItemType(), attachmentMessageSelfListItem.getId(), attachmentMessageSelfListItem.getData());
                setAttachmentClickListenerOnView(attachmentMessageSelfViewHolder.attachmentThumbnail, attachmentMessageSelfListItem.getItemType(), attachmentMessageSelfListItem.getId(), attachmentMessageSelfListItem.getData());
                setAttachmentClickListenerOnView(attachmentMessageSelfViewHolder.attachmentPlaceholder, attachmentMessageSelfListItem.getItemType(), attachmentMessageSelfListItem.getId(), attachmentMessageSelfListItem.getData());

                break;

            case MessengerListType.ATTACHMENT_MESSAGE_OTHER:
                AttachmentMessageOtherViewHolder attachmentMessageOtherViewHolder = (AttachmentMessageOtherViewHolder) viewHolder;
                AttachmentMessageOtherListItem attachmentMessageOtherListItem = (AttachmentMessageOtherListItem) getData().get(position);

                assert attachmentMessageOtherListItem.getAttachment() != null;

                ImageUtils.setAvatarImage(Kayako.getApplicationContext(), attachmentMessageOtherViewHolder.avatar, attachmentMessageOtherListItem.getAvatarUrl());

                if (attachmentMessageOtherListItem.getChannel() == null) {
                    attachmentMessageOtherViewHolder.channel.setVisibility(View.GONE);
                } else {
                    attachmentMessageOtherViewHolder.channel.setVisibility(View.VISIBLE);
                    ImageUtils.setChannelImage(Kayako.getApplicationContext(), attachmentMessageOtherViewHolder.channel, attachmentMessageOtherListItem.getChannel());
                }

                if (attachmentMessageOtherListItem.getTime() == 0) {
                    attachmentMessageOtherViewHolder.time.setVisibility(View.GONE);
                } else {
                    attachmentMessageOtherViewHolder.time.setVisibility(View.VISIBLE);
                    attachmentMessageOtherViewHolder.time.setText(DateTimeUtils.formatTime(Kayako.getApplicationContext(), attachmentMessageOtherListItem.getTime()));
                }

                if (TextUtils.isEmpty(attachmentMessageOtherListItem.getAttachment().getCaption())) {
                    attachmentMessageOtherViewHolder.message.setVisibility(View.GONE);
                } else {
                    attachmentMessageOtherViewHolder.message.setVisibility(View.VISIBLE);
                    attachmentMessageOtherViewHolder.message.setText(Html.fromHtml(attachmentMessageOtherListItem.getAttachment().getCaption()));
                }

                if (attachmentMessageOtherListItem.getAttachment().getThumbnailUrl() == null) {
                    attachmentMessageOtherViewHolder.attachmentPlaceholder.setVisibility(View.VISIBLE);
                    attachmentMessageOtherViewHolder.attachmentThumbnail.setVisibility(View.GONE);
                } else {
                    attachmentMessageOtherViewHolder.attachmentPlaceholder.setVisibility(View.GONE);
                    attachmentMessageOtherViewHolder.attachmentThumbnail.setVisibility(View.VISIBLE);
                    ImageUtils.setImage(Kayako.getApplicationContext(), attachmentMessageOtherViewHolder.attachmentThumbnail, attachmentMessageOtherListItem.getAttachment().getThumbnailUrl(), DEFAULT_PLACEHOLDER_ATTACHMENT_DARK);
                }

                setAvatarClickListenerOnView(attachmentMessageOtherViewHolder.avatar, attachmentMessageOtherListItem.getItemType(), attachmentMessageOtherListItem.getId(), attachmentMessageOtherListItem.getData());
                setAttachmentClickListenerOnView(attachmentMessageOtherViewHolder.attachmentThumbnail, attachmentMessageOtherListItem.getItemType(), attachmentMessageOtherListItem.getId(), attachmentMessageOtherListItem.getData());
                setAttachmentClickListenerOnView(attachmentMessageOtherViewHolder.attachmentPlaceholder, attachmentMessageOtherListItem.getItemType(), attachmentMessageOtherListItem.getId(), attachmentMessageOtherListItem.getData());

                break;

            case MessengerListType.ATTACHMENT_MESSAGE_CONTINUED_SELF:
                AttachmentMessageContinuedSelfViewHolder attachmentMessageContinuedSelfViewHolder = (AttachmentMessageContinuedSelfViewHolder) viewHolder;
                AttachmentMessageContinuedSelfListItem attachmentMessageContinuedSelfListItem = (AttachmentMessageContinuedSelfListItem) getData().get(position);

                assert attachmentMessageContinuedSelfListItem.getAttachment() != null;

                if (attachmentMessageContinuedSelfListItem.getTime() == 0) {
                    attachmentMessageContinuedSelfViewHolder.time.setVisibility(View.GONE);
                } else {
                    attachmentMessageContinuedSelfViewHolder.time.setVisibility(View.VISIBLE);
                    attachmentMessageContinuedSelfViewHolder.time.setText(DateTimeUtils.formatTime(Kayako.getApplicationContext(), attachmentMessageContinuedSelfListItem.getTime()));
                }

                if (TextUtils.isEmpty(attachmentMessageContinuedSelfListItem.getAttachment().getCaption())) {
                    attachmentMessageContinuedSelfViewHolder.message.setVisibility(View.GONE);
                } else {
                    attachmentMessageContinuedSelfViewHolder.message.setVisibility(View.VISIBLE);
                    attachmentMessageContinuedSelfViewHolder.message.setText(Html.fromHtml(attachmentMessageContinuedSelfListItem.getAttachment().getCaption()));
                }

                if (attachmentMessageContinuedSelfListItem.getAttachment().getThumbnailUrl() == null) {
                    attachmentMessageContinuedSelfViewHolder.attachmentPlaceholder.setVisibility(View.VISIBLE);
                    attachmentMessageContinuedSelfViewHolder.attachmentThumbnail.setVisibility(View.GONE);
                } else {
                    attachmentMessageContinuedSelfViewHolder.attachmentPlaceholder.setVisibility(View.GONE);
                    attachmentMessageContinuedSelfViewHolder.attachmentThumbnail.setVisibility(View.VISIBLE);
                    ImageUtils.setImage(Kayako.getApplicationContext(), attachmentMessageContinuedSelfViewHolder.attachmentThumbnail, attachmentMessageContinuedSelfListItem.getAttachment().getThumbnailUrl(), DEFAULT_PLACEHOLDER_ATTACHMENT_LIGHT);
                }
                setAttachmentClickListenerOnView(attachmentMessageContinuedSelfViewHolder.attachmentThumbnail, attachmentMessageContinuedSelfListItem.getItemType(), attachmentMessageContinuedSelfListItem.getId(), attachmentMessageContinuedSelfListItem.getData());
                setAttachmentClickListenerOnView(attachmentMessageContinuedSelfViewHolder.attachmentPlaceholder, attachmentMessageContinuedSelfListItem.getItemType(), attachmentMessageContinuedSelfListItem.getId(), attachmentMessageContinuedSelfListItem.getData());

                break;

            case MessengerListType.ATTACHMENT_MESSAGE_CONTINUED_OTHER:
                AttachmentMessageContinuedOtherViewHolder attachmentMessageContinuedOtherViewHolder = (AttachmentMessageContinuedOtherViewHolder) viewHolder;
                AttachmentMessageContinuedOtherListItem attachmentMessageContinuedOtherListItem = (AttachmentMessageContinuedOtherListItem) getData().get(position);

                assert attachmentMessageContinuedOtherListItem.getAttachment() != null;

                if (attachmentMessageContinuedOtherListItem.getTime() == 0) {
                    attachmentMessageContinuedOtherViewHolder.time.setVisibility(View.GONE);
                } else {
                    attachmentMessageContinuedOtherViewHolder.time.setVisibility(View.VISIBLE);
                    attachmentMessageContinuedOtherViewHolder.time.setText(DateTimeUtils.formatTime(Kayako.getApplicationContext(), attachmentMessageContinuedOtherListItem.getTime()));
                }

                if (TextUtils.isEmpty(attachmentMessageContinuedOtherListItem.getAttachment().getCaption())) {
                    attachmentMessageContinuedOtherViewHolder.message.setVisibility(View.GONE);
                } else {
                    attachmentMessageContinuedOtherViewHolder.message.setVisibility(View.VISIBLE);
                    attachmentMessageContinuedOtherViewHolder.message.setText(Html.fromHtml(attachmentMessageContinuedOtherListItem.getAttachment().getCaption()));
                }

                if (attachmentMessageContinuedOtherListItem.getAttachment().getThumbnailUrl() == null) {
                    attachmentMessageContinuedOtherViewHolder.attachmentPlaceholder.setVisibility(View.VISIBLE);
                    attachmentMessageContinuedOtherViewHolder.attachmentThumbnail.setVisibility(View.GONE);
                } else {
                    attachmentMessageContinuedOtherViewHolder.attachmentPlaceholder.setVisibility(View.GONE);
                    attachmentMessageContinuedOtherViewHolder.attachmentThumbnail.setVisibility(View.VISIBLE);
                    ImageUtils.setImage(Kayako.getApplicationContext(), attachmentMessageContinuedOtherViewHolder.attachmentThumbnail, attachmentMessageContinuedOtherListItem.getAttachment().getThumbnailUrl(), DEFAULT_PLACEHOLDER_ATTACHMENT_DARK);
                }

                setAttachmentClickListenerOnView(attachmentMessageContinuedOtherViewHolder.attachmentThumbnail, attachmentMessageContinuedOtherListItem.getItemType(), attachmentMessageContinuedOtherListItem.getId(), attachmentMessageContinuedOtherListItem.getData());
                setAttachmentClickListenerOnView(attachmentMessageContinuedOtherViewHolder.attachmentPlaceholder, attachmentMessageContinuedOtherListItem.getItemType(), attachmentMessageContinuedOtherListItem.getId(), attachmentMessageContinuedOtherListItem.getData());

                break;

            case MessengerListType.DATE_SEPARATOR:
                DateSeparatorViewHolder dateSeparatorViewHolder = (DateSeparatorViewHolder) viewHolder;
                DateSeparatorListItem dateSeparatorListItem = (DateSeparatorListItem) getData().get(position);

                dateSeparatorViewHolder.time.setText(DateTimeUtils.formatDate(Kayako.getApplicationContext(), dateSeparatorListItem.getTimeInMilliseconds()));
                break;

            case MessengerListType.UNREAD_SEPARATOR:
                UnreadSeparatorListItem unreadSeparatorListItem = (UnreadSeparatorListItem) getData().get(position);
                UnreadSeparatorViewHolder unreadSeparatorViewHolder = (UnreadSeparatorViewHolder) viewHolder;

                if (unreadSeparatorListItem.getText() != null) {
                    unreadSeparatorViewHolder.unreadText.setText(unreadSeparatorListItem.getText());
                }
                break;

            case MessengerListType.EMPTY_SEPARATOR:
                // Nothing to modify
                break;

            case MessengerListType.BOT_MESSAGE:
                BotMessageListItem botMessageListItem = (BotMessageListItem) getData().get(position);
                BotMessageViewHolder botMessageViewHolder = (BotMessageViewHolder) viewHolder;

                botMessageViewHolder.message.setText(Html.fromHtml(botMessageListItem.getMessage()));
                ImageUtils.setAvatarImage(Kayako.getApplicationContext(), botMessageViewHolder.avatar, BotMessageHelper.getBotDrawable());

                if (botMessageListItem.getTime() == 0) {
                    botMessageViewHolder.time.setVisibility(View.GONE);
                } else {
                    botMessageViewHolder.time.setVisibility(View.VISIBLE);
                    botMessageViewHolder.time.setText(DateTimeUtils.formatTime(Kayako.getApplicationContext(), botMessageListItem.getTime()));
                }

                // No Item Click Listener
                // No Avatar Click Listener
                break;

            case MessengerListType.INPUT_FIELD_EMAIL:
                final InputEmailViewHolder inputEmailViewHolder = (InputEmailViewHolder) viewHolder;
                final InputEmailListItem inputEmailListItem = (InputEmailListItem) getData().get(position);

                InputFieldEmailHelper.configureInputEmailField(inputEmailViewHolder, inputEmailListItem);
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

            case MessengerListType.UNREAD_SEPARATOR:
                View unreadSeparatorView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ko__list_messenger_unread_separator, parent, false);
                return new UnreadSeparatorViewHolder(unreadSeparatorView);

            case MessengerListType.EMPTY_SEPARATOR:
                View emptySeparatorView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ko__list_messenger_empty_separator, parent, false);
                return new EmptyViewHolder(emptySeparatorView);

            case MessengerListType.BOT_MESSAGE: // Note: Bot message shares the same layout as SIMPLE_MESSAGE_OTHER
                View botMessageView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.ko__list_messenger_simple_message_other, parent, false);
                return new BotMessageViewHolder(botMessageView);

            case MessengerListType.INPUT_FIELD_EMAIL:
                View inputEmailView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ko__list_messenger_input_field, parent, false);
                return new InputEmailViewHolder(inputEmailView);

            default:
                return super.onCreateViewHolder(parent, viewType);
        }
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

    protected void setAvatarClickListenerOnView(final View view, final int itemType, final Long id, final Map<String, Object> data) {
        if (mAvatarClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAvatarClickListener.OnClickAvatar(view, itemType, id, data);
                }
            });
        }
    }

    protected void setItemClickListenerOnView(View view, final int itemType, final Long id, final Map<String, Object> data) {
        if (mItemClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onClickItem(itemType, id, data);
                }
            });
        }
    }

    protected void setAttachmentClickListenerOnView(View view, final int itemType, final Long id, final Map<String, Object> data) {
        if (mAttachmentClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAttachmentClickListener.onClickAttachment(itemType, id, data);
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
        void onClickItem(int messageType, Long id, Map<String, Object> messageData);
    }

    public interface OnAvatarClickListener {
        /**
         * Called when an avatar in the Messenger List is clicked
         *
         * @param messageType
         * @param messageData
         */
        void OnClickAvatar(View avatarView, int messageType, Long id, Map<String, Object> messageData);
    }

    public interface OnAttachmentClickListener {
        /**
         * Called when an attachment in the Messenger List is clicked
         *
         * @param messageType
         * @param messageData
         */
        void onClickAttachment(int messageType, Long id, Map<String, Object> messageData);
    }
}
