package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.messenger.message.Message;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * All logic involved in marking a message as read
 */
public class MarkReadHelper {

    private static final long UNASSIGNED_VALUE = -100; // This is used so that originalLastMessageMarkedRead is not set during a conversation (when agents make reply before marked as read)

    private AtomicBoolean mUseOriginalLastMessage = new AtomicBoolean(true);
    private AtomicLong mOriginalLastMessageMarkedRead = new AtomicLong(UNASSIGNED_VALUE); // the getOriginalLastMessageMarkedReadlatest message that was read (only when this page is opened, should not update later!)
    private AtomicLong mLastMessageMarkedReadSuccessfully = new AtomicLong(UNASSIGNED_VALUE); // the latest message that has been read (constantly updates as the latest messages gets loaded and read)
    private AtomicLong mLastMessageBeingMarkedRead = new AtomicLong(UNASSIGNED_VALUE); // the latest message that is being marked read (created to prevent multiple network requests being made for same id)

    // TODO: When KRE is implemented, ensure the messages and conversartion is not reloaded unless there is a change made!
    // This should be done because the app is already making requests on its own

    /**
     * Call this to set the mLastMessageMarkedReadSuccessfully.
     * This method only sets the value if it hasn't been set before, thus ensuring only the original is saved
     *
     * @param lastMessageMarkedRead
     */
    public void setOriginalLastMessageMarkedReadIfNotSetBefore(long lastMessageMarkedRead) {
        // Set the original last message read (This will be the first non-zero value set)
        if (mOriginalLastMessageMarkedRead.get() == UNASSIGNED_VALUE) {
            mOriginalLastMessageMarkedRead.set(lastMessageMarkedRead);
        }
    }

    /**
     * @return
     */
    public long getOriginalLastMessageMarkedRead() {
        // Save the unread marker so that it doesn't go away when conversation is reloaded.
        // Instead, it should only be reset when the user leaves the conversation.
        // Code should rely on this presaved value, and not the conversation variable to check read marker status
        if (mUseOriginalLastMessage.get()) {
            return mOriginalLastMessageMarkedRead.get();
        } else {
            return 0; // If original last message is not used, then no read marker should be shown!
        }
    }

    /**
     * This method should be called when the originalLastMessageRead is no longer needed.
     * Example: When a reply is made by the user, the "new messages" separator should disappear.
     *
     * @return
     */
    public void disableOriginalLastMessageMarked() {
        mUseOriginalLastMessage.set(false);
    }

    public void setLastMessageBeingMarkedRead(Long lastMessageBeingMarkedRead) {
        if (lastMessageBeingMarkedRead == null) {
            lastMessageBeingMarkedRead = 0L;
        }
        mLastMessageBeingMarkedRead.set(lastMessageBeingMarkedRead);
    }

    public void setLastMessageMarkedReadSuccessfully(Long lastMessageMarkedReadSuccessfully) {
        if (lastMessageMarkedReadSuccessfully == null) {
            lastMessageMarkedReadSuccessfully = 0L;
        }

        mLastMessageMarkedReadSuccessfully.set(lastMessageMarkedReadSuccessfully);
    }

    public boolean shouldMarkMessageAsRead(Long lastMessageToBeMarkedRead) {
        if (lastMessageToBeMarkedRead == null) {
            return false;
        }

        // If an earlier message (say id=120) needs to be marked as read, when the latest message is already marked read (id=150), then skip.
        if (lastMessageToBeMarkedRead < mLastMessageMarkedReadSuccessfully.get() // Prevents redundant calls as we load more messages
                || lastMessageToBeMarkedRead < mOriginalLastMessageMarkedRead.get()) { // Prevents marking read an earlier message than the message in conversation resource read marker node
            return false;
        }

        if (this.mLastMessageMarkedReadSuccessfully.get() == lastMessageToBeMarkedRead // Prevents redundant calls as we mark the same message as read again and again (while
                || lastMessageToBeMarkedRead == mLastMessageBeingMarkedRead.get()) { // Prevents redundant calls as we mark the same message as read again and again (while it is being marked)
            return false;
        }

        return true;
    }

    public void markLastMessageAsDelivered() {
        // TODO: Ask Harminder when he wants a message to be marked delivered?
        // TODO: Does marking a message as delivered override it's seen status?
    }

    public boolean markLastMessageAsRead(final List<Message> messages, final Long conversationId, MarkReadCallback listener) {
        // Call this method to mark the last message as read by the current customer. It involves making an API request.

        // If the argument is null, skip
        if (messages == null || messages.size() == 0 || conversationId == null) {
            return false;
        }

        final Long messageIdToBeMarkedRead = extractLastMessageId(messages);
        if (shouldMarkMessageAsRead(messageIdToBeMarkedRead)) {
            setLastMessageBeingMarkedRead(messageIdToBeMarkedRead);

            // Call API to mark message read
            listener.markRead(conversationId, messageIdToBeMarkedRead);
            return true;
        } else {
            return false;
        }
    }

    private Long extractLastMessageId(List<Message> messageList) {
        if (messageList == null || messageList.size() == 0) {
            return null;
        }

        /*
        There's a lot of order reversing going on in the code which may cause issues in this snippet
        Therefore, to be absolutely certain the last element (greatest id) is selected, messages at the extremes are collected and the largest id is returned.
        This is based on the assumption that the items are sorted (which it should be!)
         */
        Message firstMessage = messageList.get(0);
        Message lastMessage = messageList.get(messageList.size() - 1);

        return firstMessage.getId() > lastMessage.getId() ? firstMessage.getId() : lastMessage.getId();
    }


    public interface MarkReadCallback {
        void markDelivered(long conversationId, long postId);

        void markRead(long conversationId, long postId);
    }


}