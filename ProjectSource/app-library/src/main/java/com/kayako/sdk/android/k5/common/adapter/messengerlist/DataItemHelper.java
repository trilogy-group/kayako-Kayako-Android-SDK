package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
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
            if (previousDataItem != null && previousDataItem.getTimeInMilliseconds() > currentDataItem.getTimeInMilliseconds()) {
                throw new AssertionError("The list is not sorted by time! Should be in descending order of creation time with newest item on top of list");
            }

            // Assertions on ids
            if (ids.contains(currentDataItem.getId())) {
                throw new AssertionError("Every item of the list should have a unique id!");
            } else {
                ids.add(currentDataItem.getId());
            }

            // Add Date Separators wherever applicable
            if (previousDataItem != null) {
                addDateSeparators(viewItems, currentDataItem, previousDataItem);
            }

            // Add Unread Separator wherever applicable
            if (previousDataItem != null) {
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

        return new ViewBehaviour(showTime, showAvatar, showChannel, showAsSelf, messageType);
    }

    /**
     * Check if the current data item was posted on a day after the previous data item.
     * If true, add the DATE SEPARATOR.
     */
    protected void addDateSeparators(List<BaseListItem> viewItems, DataItem currentDataItem, DataItem previousDataItem) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTimeInMillis(currentDataItem.getTimeInMilliseconds());

        Calendar previousDate = Calendar.getInstance();
        previousDate.setTimeInMillis(previousDataItem.getTimeInMilliseconds());

        if (currentDate.get(Calendar.DAY_OF_YEAR) > previousDate.get(Calendar.DAY_OF_YEAR)
                || currentDate.get(Calendar.YEAR) > previousDate.get(Calendar.YEAR)) {
            viewItems.add(new DateSeparatorListItem(currentDataItem.getTimeInMilliseconds()));
        }
    }

    /**
     * Check if the readStatus of the previousDataItem is false and the currentDataItem is true.
     * If true, add the UNREAD SEPARATOR
     */
    protected void addUnreadSeparator(List<BaseListItem> viewItems, DataItem currentDataItem, DataItem previousDataItem) {
        if (previousDataItem.isRead() && !currentDataItem.isRead()) {
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
    protected boolean isGroupable(DataItem previousDataItem, DataItem currentDataItem) {
        return Math.abs(currentDataItem.getTimeInMilliseconds() - previousDataItem.getTimeInMilliseconds()) <= MINIMUM_MILLISECONDS_TO_GROUP;
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

        // Don't show Time if is last element of list
        if (nextDataItem == null) {
            return false;
        }

        // Show time if the next message is sent by someone other than who sent the current message
        if (!nextDataItem.getUserDecoration().getUserId().equals(currentDataItem.getUserDecoration().getUserId())) {
            return true;
        }

        // Show time if current item can NOT be grouped with the next item
        return !isGroupable(currentDataItem, nextDataItem);
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

        // Show avatar if first item of list
        if (previousDataItem == null) {
            return true;
        }

        // Show Avatar if the previous message is sent by someone other than who sent the current message
        if (!previousDataItem.getUserDecoration().getUserId().equals(currentDataItem.getUserDecoration().getUserId())) {
            return true;
        }

        // Show Avatar if the previous message is sent via another channel as compared to the current message
        if (previousDataItem.getChannelDecoration().getSourceDrawable() != currentDataItem.getChannelDecoration().getSourceDrawable()) {
            return true;
        }

        // Show Avatar if current item can NOT be grouped with previous item
        return !isGroupable(previousDataItem, currentDataItem);
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

        if (viewBehaviour.showAvatar && viewBehaviour.showAsSelf) {
            return new SimpleMessageSelfListItem(currentDataItem.getId(), currentDataItem.getMessage(), currentDataItem.getUserDecoration().getAvatarUrl(), currentDataItem.getChannelDecoration(), time, currentDataItem.getData());
        } else if (viewBehaviour.showAvatar) {
            return new SimpleMessageOtherListItem(currentDataItem.getId(), currentDataItem.getMessage(), currentDataItem.getUserDecoration().getAvatarUrl(), currentDataItem.getChannelDecoration(), time, currentDataItem.getData());
        } else if (viewBehaviour.showAsSelf) {
            return new SimpleMessageContinuedSelfListItem(currentDataItem.getId(), currentDataItem.getMessage(), time, currentDataItem.getData());
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

        if (viewBehaviour.showAvatar && viewBehaviour.showAsSelf) {
            return new AttachmentMessageSelfListItem(currentDataItem.getId(), currentDataItem.getUserDecoration().getAvatarUrl(), currentDataItem.getChannelDecoration(), attachment, time, currentDataItem.getData());
        } else if (viewBehaviour.showAvatar) {
            return new AttachmentMessageOtherListItem(currentDataItem.getId(), currentDataItem.getUserDecoration().getAvatarUrl(), currentDataItem.getChannelDecoration(), attachment, time, currentDataItem.getData());
        } else if (viewBehaviour.showAsSelf) {
            return new AttachmentMessageContinuedSelfListItem(currentDataItem.getId(), attachment, time, currentDataItem.getData());
        } else {
            return new AttachmentMessageContinuedOtherListItem(currentDataItem.getId(), attachment, time, currentDataItem.getData());
        }
    }

    protected List<BaseListItem> generateAttachmentMessages(DataItem currentDataItem, ViewBehaviour viewBehaviour) {
        assert currentDataItem.getAttachments() != null;

        List<BaseListItem> attachmentMessages = new ArrayList<>();

        boolean originalShowTime = viewBehaviour.showTime;

        boolean hasMessage = currentDataItem.getMessage() != null && !currentDataItem.getMessage().isEmpty();
        if (hasMessage) {
            viewBehaviour.showTime = false; // the simple message should NOT show time since it's the first element of group
            attachmentMessages.add(generateSimpleMessage(currentDataItem, viewBehaviour));
            viewBehaviour.showAvatar = false; // subsequent messages should NOT show the avatar since they're part of the same group
        }

        Attachment lastAttachment = currentDataItem.getAttachments().get(currentDataItem.getAttachments().size() - 1);
        for (Attachment attachment : currentDataItem.getAttachments()) {
            if (attachment == lastAttachment) {
                viewBehaviour.showTime = originalShowTime;
            }
            attachmentMessages.add(generateAttachmentMessage(currentDataItem, viewBehaviour, attachment));
        }

        return attachmentMessages;
    }

}


