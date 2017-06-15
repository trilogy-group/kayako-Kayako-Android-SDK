package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import android.content.Context;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedback;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedbackCommentListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedbackRatingListItem;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.messenger.rating.Rating;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * All logic involving offboarding items that ask for feedback
 */
public class OffboardingHelper {

    public OffboardingHelper() {
    }

    // Interactions and switching between different Feedback views are done based on in-memory values (not api state)
    private Rating.SCORE currentRatingSubmittedViaUI;
    private String currentFeedbackSubmittedViaUI;

    // Queuing requests to ensure new ratings are not created if the existing ones can be updated
    private Queue<UnsentRating> updateRatingQueue = new ConcurrentLinkedQueue<>();
    private AtomicReference<UnsentRating> lastRatingBeingSent = new AtomicReference<>();

    private Boolean mWasConversationOriginallyCompleted = null;
    private AtomicBoolean mWasConversationStatusChanged = new AtomicBoolean(false);
    private AtomicBoolean mIsConversationCompleted = new AtomicBoolean(false);
    private AtomicLong mIdOfLastRatingAdded = new AtomicLong();

    //////////// API CALLBACK METHODS ////////////

    public void onUpdateRating(Rating rating, OffboardingHelperViewCallback callback) {
        /*
            This method is called when feedback has been successfully sent via API.
         */

        mIdOfLastRatingAdded.set(rating.getId());

        removeFromQueue(callback, rating.getScore(), rating.getComment()); // since successful
        resetRatingSendingState(callback); // no longer sending
        runNextInQueueIfReady(callback);

        callback.onRefreshListView(true);
    }

    public void onFailureToUpdateRating(Rating.SCORE score, String feedback, OffboardingHelperViewCallback callback) {
        /*
            This method is called when feedback has failed to be sent via API.
         */

        resetRatingSendingState(callback);
        runNextInQueueIfReady(callback);
    }

    public void onLoadConversation(boolean isConversationCompleted, OffboardingHelperViewCallback callback) {
        // Both when a new conversation is created and when loading an existing conversation
        // This is done only once a conversation is loaded because to load ratings of a conversation, we need to make sure the conversation exists

        boolean firstLoad = mWasConversationOriginallyCompleted == null;

        if (firstLoad) {
            mWasConversationOriginallyCompleted = isConversationCompleted;
            mIsConversationCompleted.set(isConversationCompleted);

            // DO NOT REFRESH LIST - if a conversation is completed originally, we do NOT show the rating prompt nor a pre-added rating

        } else { // not the first load

            // current status is different from previous status and current status is complete
            if (mIsConversationCompleted.get() != isConversationCompleted) {

                mWasConversationStatusChanged.set(true);
                mIsConversationCompleted.set(isConversationCompleted);

                // reset every time there is a conversation status change from completed to another status
                if (!mIsConversationCompleted.get()) {
                    resetRatingAddedByUser();
                }

                callback.onRefreshListView(true); // Ensure that during status changes from open -> completed -> open, the feedback is removed
            }
        }
    }

    public List<BaseListItem> getOffboardingListItems(String nameToAddRatingAndFeedbackOf,
                                                      boolean isConversationCompleted, final OffboardingHelperViewCallback callback) {
        if (nameToAddRatingAndFeedbackOf == null || callback == null) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        if (!isConversationCompleted // Do not show feedback items if conversation is not completed
                ||
                (mWasConversationOriginallyCompleted != null
                        && mWasConversationOriginallyCompleted
                        && !mWasConversationStatusChanged.get())) { // Do not ask for feedback items if conversation is originally completed and no changes to status have been made since then
            return Collections.EMPTY_LIST;
        }

        if (currentRatingSubmittedViaUI == null) {
            return getOffboardingItemsToSelectRating(new InputFeedback.OnSelectRatingListener() {
                @Override
                public void onSelectRating(InputFeedback.RATING rating) {

                    Rating.SCORE score = convertFromInputFieldRating(rating);
                    addToQueue(callback, score, null);
                    runNextInQueueIfReady(callback);

                    callback.onRefreshListView(true);
                }
            });


        } else if (currentFeedbackSubmittedViaUI == null) {

            /*
            NOTE: New feedback view combines rating and feedback views - no more intermediate state
            if (latestRatingOfConversation.get() == null) {
                throw new IllegalStateException("This should never be called if the latestRating is not loaded");
            }
            */

            List<BaseListItem> list = new ArrayList<>();
            list.addAll(getOffboardingItemsToAddComment(currentRatingSubmittedViaUI, new InputFeedbackCommentListItem.OnAddFeedbackCommentCallback() {
                @Override
                public void onChangeFeedbackRating(InputFeedback.RATING rating) {
                    Rating.SCORE score = convertFromInputFieldRating(rating);
                    addToQueue(callback, score, null);
                    runNextInQueueIfReady(callback);

                    callback.onRefreshListView(false);
                }

                @Override
                public void onAddFeedbackComment(InputFeedback.RATING rating, String feedback) {
                    Rating.SCORE score = convertFromInputFieldRating(rating);
                    addToQueue(callback, score, feedback);
                    runNextInQueueIfReady(callback);

                    callback.onRefreshListView(false);
                    callback.onHideKeyboard();
                    callback.onShowMessage(R.string.ko__messenger_input_feedback_comment_message_submitted);
                }
            }));
            return list;

        } else {
            List<BaseListItem> list = new ArrayList<>();
            return list; // Show nothing - once the comment is added, it should disappear
        }
    }

    //////////// LIST ITEM METHODS ////////////


    private void resetRatingAddedByUser() {
        mIdOfLastRatingAdded.set(0);
        currentRatingSubmittedViaUI = null;
        currentFeedbackSubmittedViaUI = null;
    }

    private List<BaseListItem> getOffboardingItemsToSelectRating(
            final InputFeedback.OnSelectRatingListener onSelectRatingListener) {
        List<BaseListItem> listItems = new ArrayList<>();
        listItems.add(new InputFeedbackRatingListItem(
                Kayako.getApplicationContext().getString(R.string.ko__messenger_input_feedback_rating_instruction_message),
                onSelectRatingListener)
        );
        return listItems;
    }

    private List<BaseListItem> getOffboardingItemsToAddComment(Rating.SCORE
                                                                       rating, InputFeedbackCommentListItem.OnAddFeedbackCommentCallback onAddFeedbackComment) {
        List<BaseListItem> listItems = new ArrayList<>();
        listItems.add(new InputFeedbackCommentListItem(
                getCommentInstructionMessage(rating),
                convert(rating),
                onAddFeedbackComment));
        return listItems;
    }


    //////////// UTIL METHODS ////////////

    private InputFeedback.RATING convert(Rating.SCORE score) {
        if (score == Rating.SCORE.BAD) {
            return InputFeedback.RATING.BAD;
        } else if (score == Rating.SCORE.GOOD) {
            return InputFeedback.RATING.GOOD;
        } else {
            throw new IllegalStateException("Unhandled enum state!");
        }
    }

    private Rating.SCORE convertFromInputFieldRating(InputFeedback.RATING rating) {
        if (rating == InputFeedback.RATING.BAD) {
            return Rating.SCORE.BAD;
        } else if (rating == InputFeedback.RATING.GOOD) {
            return Rating.SCORE.GOOD;
        } else {
            throw new IllegalStateException("Unhandled enum state!");
        }
    }

    private String getCommentInstructionMessage(Rating.SCORE score) {
        Context context = Kayako.getApplicationContext();
        return context.getResources().getString(R.string.ko__messenger_input_feedback_rating_instruction_message); // Same as Rating message
    }

    //////////// QUEUEING CODE ////////////

    private void addOrUpdateRating(OffboardingHelperViewCallback callback, Rating.SCORE rating, String feedback) {
        if (mIdOfLastRatingAdded.get() == 0) {
            callback.onAddRating(rating, feedback);
        } else if (feedback == null) {
            callback.onUpdateRating(mIdOfLastRatingAdded.get(), rating);
        } else {
            callback.onUpdateFeedback(mIdOfLastRatingAdded.get(), rating, feedback);
        }
    }

    private void addToQueue(OffboardingHelperViewCallback callback, Rating.SCORE rating, String feedback) {
        updateRatingQueue.add(new UnsentRating(rating, feedback));

        currentFeedbackSubmittedViaUI = feedback;
        currentRatingSubmittedViaUI = rating;
    }

    private void removeFromQueue(OffboardingHelperViewCallback callback, Rating.SCORE rating, String feedback) {
        if (!updateRatingQueue.peek().equals(new UnsentRating(rating, feedback))) {
            throw new IllegalStateException("Should remove the same rating from queue. State should be consistent!");
        }
        updateRatingQueue.remove();
    }

    private void resetRatingSendingState(OffboardingHelperViewCallback callback) {
        lastRatingBeingSent.set(null);
    }

    private void runNextInQueueIfReady(OffboardingHelperViewCallback callback) {
        if (updateRatingQueue.size() == 0
                || lastRatingBeingSent.get() != null) {
            return; // Wait till it's ready
        }

        UnsentRating unsentRating = updateRatingQueue.peek();
        lastRatingBeingSent.set(unsentRating);
        addOrUpdateRating(callback, unsentRating.getRatingScore(), unsentRating.getFeedback());
    }

//////////// INTERFACES & PVT CLASSES ////////////


    public interface OffboardingHelperViewCallback {

        void onRefreshListView(boolean scrollToBottom);

        void onHideKeyboard();

        void onAddRating(Rating.SCORE ratingScore, String message);

        void onUpdateRating(long ratingId, Rating.SCORE score);

        void onUpdateFeedback(long ratingId, Rating.SCORE score, String message);

        void onShowMessage(int stringResId);
    }

    private static class UnsentRating {

        private Rating.SCORE ratingScore;
        private String feedback;

        public UnsentRating(Rating.SCORE ratingScore, String feedback) {
            this.ratingScore = ratingScore;
            this.feedback = feedback;
        }

        public Rating.SCORE getRatingScore() {
            return ratingScore;
        }

        public String getFeedback() {
            return feedback;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            UnsentRating that = (UnsentRating) o;

            if (ratingScore != that.ratingScore) return false;
            return feedback != null ? feedback.equals(that.feedback) : that.feedback == null;
        }

        @Override
        public int hashCode() {
            int result = ratingScore != null ? ratingScore.hashCode() : 0;
            result = 31 * result + (feedback != null ? feedback.hashCode() : 0);
            return result;
        }
    }
}
