package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.support.annotation.NonNull;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.Attachment;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.DataItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.DeliveryIndicator;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.ViewBehaviour;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.AttachmentMessageContinuedOtherListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.AttachmentMessageContinuedSelfListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.AttachmentMessageOtherListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.AttachmentMessageSelfListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.DateSeparatorListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.SimpleMessageContinuedOtherListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.SimpleMessageContinuedSelfListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.SimpleMessageOtherListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.SimpleMessageSelfListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.UnreadSeparatorListItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataItemHelper {

    public static final long MINIMUM_MILLISECONDS_TO_GROUP = 3 * 60 * 1000L;

    private static DataItemHelper mDataItemHelper;

    public static DataItemHelper getInstance() {
        if (mDataItemHelper == null) {
            mDataItemHelper = new DataItemHelper();
        }
        return mDataItemHelper;
    }

    public List<BaseListItem> convertDataItemToListItems(List<DataItem> dataItems) {
        DataItem currentDataItem;
        DataItem nextDataItem;
        DataItem previousDataItem;

        List<BaseListItem> viewItems = new ArrayList<>();
        Set<Long> ids = new HashSet<>();

        for (int i = 0; i < dataItems.size(); i++) {
            // Get the current dataItem
            currentDataItem = dataItems.get(i);

            // Get the previous dataItem (null if current is first item)
            if (i == 0) {
                previousDataItem = null;
            } else {
                previousDataItem = dataItems.get(i - 1);
            }

            // Get the previous dataItem (null if current is first item)
            if (i == dataItems.size() - 1) {
                nextDataItem = null;
            } else {
                nextDataItem = dataItems.get(i + 1);
            }

            // Assertions on time
            if (previousDataItem != null && !areItemsInDescendingOrderOfTimeCreated(previousDataItem.getTimeInMilliseconds(), currentDataItem.getTimeInMilliseconds())) {
                throw new AssertionError("The list is not sorted by time! Should be in descending order of creation time with newest item on top of list");
            }

            // Assertions on ids
            if (ids.contains(currentDataItem.getId())) {
                throw new AssertionError("Every item of the list should have a unique id!");
            } else {
                ids.add(currentDataItem.getId());
            }

            // Add Date Separators wherever applicable
            addDateSeparators(viewItems, currentDataItem, previousDataItem); // also shown at top of every conversation listing

            // Add Unread Separator wherever applicable
            if (previousDataItem != null) { // Prevent it from showing before first message
                addUnreadSeparator(viewItems, currentDataItem, previousDataItem);
            }

            // Define ViewBehaviour
            ViewBehaviour viewBehaviour = defineViewBehaviour(currentDataItem, previousDataItem, nextDataItem);
            assert viewBehaviour != null;

            // Generate Message
            List<BaseListItem> generatedItems = generateMessageViews(viewBehaviour, currentDataItem);

            viewItems.addAll(generatedItems);
        }

        return viewItems;
    }

    protected List<BaseListItem> generateMessageViews(ViewBehaviour viewBehaviour, DataItem currentDataItem) {
        List<BaseListItem> generatedViewItems = new ArrayList<>();

        switch (viewBehaviour.messageType) {
            case SIMPLE:
                generatedViewItems.add(generateSimpleMessage(currentDataItem, viewBehaviour));
                return generatedViewItems;

            case ATTACHMENT:
                generatedViewItems.addAll(generateAttachmentMessages(currentDataItem, viewBehaviour));
                return generatedViewItems;

            default:
                throw new AssertionError("Unhandled situation in generating messages for custom UI");
        }
    }

    protected ViewBehaviour defineViewBehaviour(DataItem currentDataItem, DataItem previousDataItem, DataItem nextDataItem) {
        ViewBehaviour.MessageType messageType = getMessageType(currentDataItem);
        boolean showTime = getTimeVisibility(currentDataItem, nextDataItem);
        boolean showAvatar = getAvatarVisibility(currentDataItem, previousDataItem);
        boolean showChannel = getChannelVisibility(showAvatar, currentDataItem);
        boolean showAsSelf = getIfSelf(currentDataItem);

        boolean showDeliveryIndicator = getDeliveryIndicatorVisibility(currentDataItem, nextDataItem);

        return new ViewBehaviour(showTime, showAvatar, showChannel, showAsSelf, showDeliveryIndicator, messageType);
    }

    /**
     * Check if the current data item was posted on a day after the previous data item.
     */
    protected boolean getDateSeparatorVisibility(@NonNull DataItem currentDataItem, DataItem previousDataItem) {
        // Show date separator if previous item = null
        if (previousDataItem == null) {
            return true;
        } else {
            // Else, show it only if the day of year is different between the previous item and the current item
            Calendar currentDate = Calendar.getInstance();
            currentDate.setTimeInMillis(currentDataItem.getTimeInMilliseconds());

            Calendar previousDate = Calendar.getInstance();
            previousDate.setTimeInMillis(previousDataItem.getTimeInMilliseconds());

            if (currentDate.get(Calendar.DAY_OF_YEAR) > previousDate.get(Calendar.DAY_OF_YEAR)
                    || currentDate.get(Calendar.YEAR) > previousDate.get(Calendar.YEAR)) {
                return true;
            }
        }

        return false;
    }

    /**
     * If date visibility returns true, add the DATE SEPARATOR.
     */
    protected void addDateSeparators(List<BaseListItem> viewItems, DataItem currentDataItem, DataItem previousDataItem) {
        if (getDateSeparatorVisibility(currentDataItem, previousDataItem)) {
            viewItems.add(new DateSeparatorListItem(currentDataItem.getTimeInMilliseconds()));
        }
    }

    /**
     * Check if the readStatus of the previousDataItem is false and the currentDataItem is true.
     * If true, add the UNREAD SEPARATOR
     */
    protected void addUnreadSeparator(List<BaseListItem> viewItems, DataItem currentDataItem, DataItem previousDataItem) {
        if (previousDataItem.isRead() && !currentDataItem.isRead()) { // If the current message is sent by the current user, it's assumed to be read
            viewItems.add(new UnreadSeparatorListItem());
        }
    }

    /**
     * Get Message Type to decide what to show
     *
     * @param currentDataItem
     * @return
     */
    protected ViewBehaviour.MessageType getMessageType(DataItem currentDataItem) {
        if (currentDataItem.getAttachments() != null && currentDataItem.getAttachments().size() > 0) {
            return ViewBehaviour.MessageType.ATTACHMENT;
        } else {
            return ViewBehaviour.MessageType.SIMPLE;
        }

        // TODO: Activity Message
    }

    /**
     * check if current message sent within the minimum time interval from previous message) and therefore can be grouped
     *
     * @param previousDataItem
     * @param currentDataItem
     * @return
     */
    protected boolean isGroupableByTime(DataItem previousDataItem, DataItem currentDataItem) {
        return true; // Set to always true.  TIME is only shown for last element now and therefore everything is grouped as one block.
        //Math.abs(currentDataItem.getTimeInMilliseconds() - previousDataItem.getTimeInMilliseconds()) <= MINIMUM_MILLISECONDS_TO_GROUP;
    }

    /**
     * Check if the current view should show the time
     *
     * @param currentDataItem
     * @param nextDataItem
     * @return
     */
    protected boolean getTimeVisibility(DataItem currentDataItem, DataItem nextDataItem) {
        assert currentDataItem != null;

        // Show Time ONLY if is last element of list
        return nextDataItem == null;
    }

    /**
     * Check if the current view should show avatar
     *
     * @param currentDataItem
     * @param previousDataItem
     * @return
     */
    protected boolean getAvatarVisibility(DataItem currentDataItem, DataItem previousDataItem) {

        assert currentDataItem != null;

        // NOTE: Self Messages no longer show avatar but the grouping logic is still based on the avatars.
        // Meaning, if self-message has showAvatar = true, it uses a SelfMessage. If showAvatar = false, it uses a SelfContinuedMessage
        // For the sake of the padding, use showAvatar to choose between continued and starting message

        // Show avatar if first item of list
        if (previousDataItem == null) {
            return true;
        }

        // Show Avatar if the previous message is sent by someone other than who sent the current message
        if (!previousDataItem.getUserDecoration().getUserId().equals(currentDataItem.getUserDecoration().getUserId())) {
            return true;
        }

        // Show Avatar if the previous message is sent via another channel as compared to the current message
        if (previousDataItem.getChannelDecoration() != null &&
                currentDataItem.getChannelDecoration() != null &&
                previousDataItem.getChannelDecoration().getSourceDrawable() != currentDataItem.getChannelDecoration().getSourceDrawable()) {
            return true;
        }

        // Show Avatar if current item can NOT be grouped with previous item
        return !isGroupableByTime(previousDataItem, currentDataItem);
    }

    /**
     * Check if the current view should show delivery indicator
     *
     * @param currentDataItem
     * @param previousDataItem
     * @return
     */
    /**
     * @return
     */
    protected boolean getDeliveryIndicatorVisibility(DataItem currentDataItem, DataItem nextDataItem) {
        /* Show Delivery Indicator if:
            1. It is the last message (Assuming whenever an agent replies, the messages before that are considered read by the customer)
            2. The message was sent by the current user (delivery indicators are not relevant to other users messages)
            2. It is only shown at the end of a group of messages (same rules as time visibility)
         */
        return getTimeVisibility(currentDataItem, nextDataItem) && getIfSelf(currentDataItem) && nextDataItem == null;
    }

    /**
     * Check if channel should be shown in view along with avatar
     *
     * @param showAvatar
     * @param currentDataItem
     * @return
     */
    protected boolean getChannelVisibility(boolean showAvatar, DataItem currentDataItem) {
        return showAvatar && currentDataItem.getChannelDecoration() != null;
    }

    /**
     * Check if view should be shown as message sent by self
     *
     * @param currentDataItem
     * @return
     */
    protected boolean getIfSelf(DataItem currentDataItem) {
        return currentDataItem.getUserDecoration().isSelf();
    }

    protected BaseListItem generateSimpleMessage(DataItem currentDataItem, ViewBehaviour viewBehaviour) {
        long time = 0;
        if (viewBehaviour.showTime) {
            time = currentDataItem.getTimeInMilliseconds();
        } else {
            time = 0;
        }

        DeliveryIndicator deliveryIndicator = null;
        if (viewBehaviour.showDeliveryIndicator) {
            deliveryIndicator = currentDataItem.getDeliveryIndicator();
        } else {
            deliveryIndicator = null;
        }

        if (viewBehaviour.showAsSelf && viewBehaviour.showAvatar) {
            // Self is now shown without Avatar - but the padding is different for starting message and continued message - therefore continue using the showAvatar boolean
            return new SimpleMessageSelfListItem(currentDataItem.getId(), currentDataItem.getMessage(), time, deliveryIndicator, false, currentDataItem.getData());
        } else if (viewBehaviour.showAsSelf) {
            return new SimpleMessageContinuedSelfListItem(currentDataItem.getId(), currentDataItem.getMessage(), time, deliveryIndicator, false, currentDataItem.getData());
        } else if (viewBehaviour.showAvatar) {
            return new SimpleMessageOtherListItem(currentDataItem.getId(), currentDataItem.getMessage(), currentDataItem.getUserDecoration().getAvatarUrl(), currentDataItem.getChannelDecoration(), time, currentDataItem.getData());
        } else {
            return new SimpleMessageContinuedOtherListItem(currentDataItem.getId(), currentDataItem.getMessage(), time, currentDataItem.getData());
        }
    }


    protected BaseListItem generateAttachmentMessage(DataItem currentDataItem, ViewBehaviour viewBehaviour, Attachment attachment) {
        long time = 0;
        if (viewBehaviour.showTime) {
            time = currentDataItem.getTimeInMilliseconds();
        } else {
            time = 0;
        }

        DeliveryIndicator deliveryIndicator = null;
        if (viewBehaviour.showDeliveryIndicator) {
            deliveryIndicator = currentDataItem.getDeliveryIndicator();
        } else {
            deliveryIndicator = null;
        }

        if (viewBehaviour.showAvatar && viewBehaviour.showAsSelf) {
            // Self is now shown without Avatar - but the padding is different for starting message and continued message
            return new AttachmentMessageSelfListItem(currentDataItem.getId(), deliveryIndicator, false, attachment, time, currentDataItem.getData());
        } else if (viewBehaviour.showAvatar) {
            return new AttachmentMessageOtherListItem(currentDataItem.getId(), currentDataItem.getUserDecoration().getAvatarUrl(), currentDataItem.getChannelDecoration(), attachment, time, currentDataItem.getData());
        } else if (viewBehaviour.showAsSelf) {
            return new AttachmentMessageContinuedSelfListItem(currentDataItem.getId(), attachment, time, deliveryIndicator, false, currentDataItem.getData());
        } else {
            return new AttachmentMessageContinuedOtherListItem(currentDataItem.getId(), attachment, time, currentDataItem.getData());
        }
    }

    protected List<BaseListItem> generateAttachmentMessages(DataItem currentDataItem, ViewBehaviour viewBehaviour) {
        assert currentDataItem.getAttachments() != null;

        List<BaseListItem> attachmentMessages = new ArrayList<>();

        boolean originalShowTime = viewBehaviour.showTime;
        boolean originalShowDeliveryIndicator = viewBehaviour.showDeliveryIndicator;

        boolean hasMessage = currentDataItem.getMessage() != null && !currentDataItem.getMessage().isEmpty();
        if (hasMessage) {
            viewBehaviour.showTime = false; // the simple message should NOT show time since it's the first element of group
            viewBehaviour.showDeliveryIndicator = false;
            attachmentMessages.add(generateSimpleMessage(currentDataItem, viewBehaviour));
            viewBehaviour.showAvatar = false; // subsequent messages should NOT show the avatar since they're part of the same group
        }

        Attachment lastAttachment = currentDataItem.getAttachments().get(currentDataItem.getAttachments().size() - 1);
        for (Attachment attachment : currentDataItem.getAttachments()) {
            if (attachment == lastAttachment) {
                viewBehaviour.showDeliveryIndicator = originalShowDeliveryIndicator;
                viewBehaviour.showTime = originalShowTime;
            }
            attachmentMessages.add(generateAttachmentMessage(currentDataItem, viewBehaviour, attachment));
        }

        return attachmentMessages;
    }

    private boolean areItemsInDescendingOrderOfTimeCreated(long previousTimeInMilliseconds, long currentTimeInMilliseconds) {
        /*
           ## PROBLEM:
            NOTICED that sometimes, the previous message has a lastCreatedAt time a few seconds AFTER the current message's lastCreatedAt
            - Previous Time in Milliseconds: 1491558789000
            - Next Time in Milliseconds: 1491558788000
            = Which means the previous message was created AFTER the next message?

          ## SOLUTION:
            Compare in terms of hours.
         */

        long previousTimeInMinutes = previousTimeInMilliseconds / (60 * 60 * 1000);
        long currentTimeInMinutes = currentTimeInMilliseconds / (60 * 60 * 1000);

        return currentTimeInMinutes >= previousTimeInMinutes; // current item more recently created than previous item
    }
}