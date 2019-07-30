package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.view.View;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.DeliveryIndicator;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.BaseDeliveryIndicatorViewHolder;
import com.kayako.sdk.android.k5.common.utils.DateTimeUtils;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.messenger.message.Message;

public class DeliveryIndicatorHelper {

    public static DeliveryIndicator getDeliveryIndicator(ClientDeliveryStatus clientDeliveryStatus) {
        if (clientDeliveryStatus == null) {
            return null;
        }

        switch (clientDeliveryStatus) {
            case SENDING:
                return new DeliveryIndicator(R.drawable.ko__delivery_indicator_sending, R.string.ko__messenger_delivery_indicators_sending, null);

            case FAILED_TO_SEND:
                return new DeliveryIndicator(R.drawable.ko__delivery_indicator_failed, R.string.ko__messenger_delivery_indicators_failed_to_send, null);
        default:
            break;
        }

        return null;
    }

    public static DeliveryIndicator getDeliveryIndicator(Message message) {
        if (message == null || message.getMessageStatus() == null) {
            return null;
        }

        switch (message.getMessageStatus()) {
            case DELIVERED:
                return new DeliveryIndicator(R.drawable.ko__delivery_indicator_delivered, R.string.ko__messenger_delivery_indicators_delivered, message.getMessageStatusUpdatedAt());

            case REJECTED:
                return new DeliveryIndicator(R.drawable.ko__delivery_indicator_failed, R.string.ko__messenger_delivery_indicators_rejected, null);

            case SEEN:
                return new DeliveryIndicator(R.drawable.ko__delivery_indicator_seen, R.string.ko__messenger_delivery_indicators_seen, message.getMessageStatusUpdatedAt());

            case SENT:
                return new DeliveryIndicator(R.drawable.ko__delivery_indicator_sent, R.string.ko__messenger_delivery_indicators_sent, message.getMessageStatusUpdatedAt());
        default:
            break;
        }

        return null;
    }

    public static void setDeliveryIndicatorView(DeliveryIndicator deliveryIndicator, Long timeMessageCreated, BaseDeliveryIndicatorViewHolder deliveryIndicatorViewHolder) {
        if (deliveryIndicator == null && timeMessageCreated != null && timeMessageCreated != 0) {
            deliveryIndicatorViewHolder.deliveryIndicatorView.setVisibility(View.VISIBLE);

            deliveryIndicatorViewHolder.deliveryIndicatorIcon.setVisibility(View.GONE);
            deliveryIndicatorViewHolder.dotSeparator.setVisibility(View.GONE);
            deliveryIndicatorViewHolder.deliveryIndicatorText.setVisibility(View.GONE);

            setDeliveryIndicatorTime(timeMessageCreated, deliveryIndicatorViewHolder); // Set time without any delivery indication mentioned

        } else if (deliveryIndicator == null) {
            deliveryIndicatorViewHolder.deliveryIndicatorView.setVisibility(View.GONE);

            deliveryIndicatorViewHolder.deliveryIndicatorIcon.setVisibility(View.GONE);
            deliveryIndicatorViewHolder.dotSeparator.setVisibility(View.GONE);
            deliveryIndicatorViewHolder.deliveryIndicatorText.setVisibility(View.GONE);
            deliveryIndicatorViewHolder.deliveryIndicatorTime.setVisibility(View.GONE);
        } else {
            deliveryIndicatorViewHolder.deliveryIndicatorView.setVisibility(View.VISIBLE);

            // Configure Delivery Indicator Icon
            setDeliveryIndicatorIcon(deliveryIndicator, deliveryIndicatorViewHolder);

            // Configure Delivery Indicator Status text
            setDeliveryIndicatorStatus(deliveryIndicator, deliveryIndicatorViewHolder);

            // Configure Delivery Indicator Dot Separator
            setDotSeparatorVisibility(deliveryIndicator, deliveryIndicatorViewHolder);

            // Configure Delivery Indicator Time
            setDeliveryIndicatorTime(deliveryIndicator.getDeliveryTime(), deliveryIndicatorViewHolder);
        }
    }

    private static void setDeliveryIndicatorTime(Long timeInMilliseconds, BaseDeliveryIndicatorViewHolder deliveryIndicatorViewHolder) {
        if (timeInMilliseconds == null || timeInMilliseconds == 0) {
            deliveryIndicatorViewHolder.deliveryIndicatorTime.setVisibility(View.GONE);
        } else {
            deliveryIndicatorViewHolder.deliveryIndicatorTime.setVisibility(View.VISIBLE);
            deliveryIndicatorViewHolder.deliveryIndicatorTime.setText(
                    DateTimeUtils.formatTime(Kayako.getApplicationContext(), timeInMilliseconds)
            );
        }
    }

    private static void setDotSeparatorVisibility(DeliveryIndicator deliveryIndicator, BaseDeliveryIndicatorViewHolder deliveryIndicatorViewHolder) {
        if (deliveryIndicator.getDeliveryTime() == null || deliveryIndicator.getDeliveryTime() == 0) {
            deliveryIndicatorViewHolder.dotSeparator.setVisibility(View.GONE);
        } else {
            deliveryIndicatorViewHolder.dotSeparator.setVisibility(View.VISIBLE);
        }
    }

    private static void setDeliveryIndicatorStatus(DeliveryIndicator deliveryIndicator, BaseDeliveryIndicatorViewHolder deliveryIndicatorViewHolder) {
        if (deliveryIndicator.getDeliveryStatusTextResId() == null) {
            deliveryIndicatorViewHolder.deliveryIndicatorText.setVisibility(View.GONE);
        } else {
            deliveryIndicatorViewHolder.deliveryIndicatorText.setText(deliveryIndicator.getDeliveryStatusTextResId());
            deliveryIndicatorViewHolder.deliveryIndicatorText.setVisibility(View.VISIBLE);
        }
    }

    private static void setDeliveryIndicatorIcon(DeliveryIndicator deliveryIndicator, BaseDeliveryIndicatorViewHolder deliveryIndicatorViewHolder) {
        if (deliveryIndicator.getDeliveryStatusIconResId() == null) {
            deliveryIndicatorViewHolder.deliveryIndicatorIcon.setVisibility(View.GONE);
        } else {
            deliveryIndicatorViewHolder.deliveryIndicatorIcon.setImageResource(deliveryIndicator.getDeliveryStatusIconResId());
            deliveryIndicatorViewHolder.deliveryIndicatorIcon.setVisibility(View.VISIBLE);

        }
    }

}