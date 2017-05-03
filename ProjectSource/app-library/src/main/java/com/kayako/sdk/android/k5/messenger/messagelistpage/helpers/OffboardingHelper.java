package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import android.content.Context;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedbackCommentListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedbackRatingListItem;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.messenger.rating.Rating;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * All logic involving offboarding items that ask for feedback
 */
public class OffboardingHelper {

    public OffboardingHelper() {
    }

    /**
     * NOTE:
     * - A conversation can have multiple ratings.
     * - Select the latest rating to update the feedback
     * <p>
     * Currently:
     * - This code is made in such a way that only one rating per customer is allowed for a conversation
     */

    // State after sending via API are saved here
    private AtomicReference<Rating> latestRatingOfConversation = new AtomicReference<>();

    // Interactions and switching between different Feedback views are done based on in-memory values (not api state)
    private Rating.SCORE currentRatingSubmittedViaUI;
    private String currentFeedbackSubmittedViaUI;

    private AtomicBoolean hasRatingsBeenLoadedViaApi = new AtomicBoolean(false);


    public void onLoadRatings(List<Rating> ratings, OffboardingHelperViewCallback callback) {
        if (ratings == null) { // ratings.size = 0 is allowed -> no ratings assigned to case but ratings are loaded
            throw new IllegalStateException("This method should not have been called with invalid arguments");
        }

        latestRatingOfConversation.set(getLatestRating(ratings));

        // Update current rating if api values are not null
        // This prevents additional Ratings to be applied if already applied once before
        if (latestRatingOfConversation.get() != null) {
            setCurrentRatingViaUI(latestRatingOfConversation.get());
        }

        // Refresh list view if ratings loaded via API for first time and a rating is available to show
        if (latestRatingOfConversation.get() != null && !hasRatingsBeenLoadedViaApi.get()) {
            callback.onRefreshListView();
        }

        // Set ratings have been loaded via API successfully
        hasRatingsBeenLoadedViaApi.set(true); // Should be at end of method
    }

    public void onUpdateRating(Rating rating, OffboardingHelperViewCallback callback) {
        /*
            This method is called when feedback has been successfully sent via API.
         */

        latestRatingOfConversation.set(rating);

        // setCurrentRatingViaAPI(rating);
        hasRatingsBeenLoadedViaApi.set(true);
        callback.onRefreshListView();
    }

    public void onLoadConversation(boolean isConversationCompletedOrClosed, OffboardingHelperViewCallback callback) {
        // Both when a new conversation is created and when loading an existing conversation
        // This is done only once a conversation is loaded because to load ratings of a conversation, we need to make sure the conversation exists

        // Load rating via API as long current rating is null
        if (latestRatingOfConversation.get() == null) {
            callback.onLoadRatings();
        }

        // TODO: Handle a situation where a customer may want to rate the conversation multiple times
    }

    public List<BaseListItem> getOffboardingListItems(String nameToAddRatingAndFeedbackOf, boolean isConversationClosed, final OffboardingHelperViewCallback callback) {
        if (nameToAddRatingAndFeedbackOf == null || callback == null) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        if (!isConversationClosed ||   // Do not show feedback items if conversation is not closed
                !hasRatingsBeenLoadedViaApi.get()) { // Do not show feedback items if ratings have not been loaded via API yet (user may have already selected a rating before)
            return Collections.EMPTY_LIST;
        }


        if (currentRatingSubmittedViaUI == null) {
            return getOffboardingItemsToSelectRating(nameToAddRatingAndFeedbackOf, new InputFeedbackRatingListItem.OnSelectRatingListener() {
                @Override
                public void onSelectRating(InputFeedbackRatingListItem.RATING rating) {
                    Rating.SCORE score = convertFromFeedbackRating(rating);
                    currentRatingSubmittedViaUI = score;

                    callback.onAddRating(score);
                    callback.onRefreshListView();
                }
            });

        } else if (latestRatingOfConversation.get() == null) {
            /*
                If the network fails or is slow and the latestRating hasn't been loaded yet, then we can not update the rating without the id.
                Therefore, do not show the user the input field to add Feedback. Only show to the user that rating is submitted (even though we are unsure if it failed or not)
                // TODO: There are better ways to handle this! Inform user of bad network, consider failure states, cache task, etc. Right now, if network is bad, rating is never sent - although visually the user is informed that it is
             */

            return getOffboardingItemsOnRatingSubmission(nameToAddRatingAndFeedbackOf, convertToFeedbackRating(currentRatingSubmittedViaUI));

        } else if (currentFeedbackSubmittedViaUI == null) {

            if (latestRatingOfConversation.get() == null) {
                throw new IllegalStateException("This should never be called if the latestRating is not loaded");
            }

            List<BaseListItem> list = new ArrayList<>();
            list.addAll(getOffboardingItemsOnRatingSubmission(nameToAddRatingAndFeedbackOf, convertToFeedbackRating(currentRatingSubmittedViaUI)));
            list.addAll(getOffboardingItemsToAddComment(currentRatingSubmittedViaUI, new InputFeedbackCommentListItem.OnAddFeedbackCommentCallback() {
                @Override
                public void onAddFeedbackComment(InputFeedbackCommentListItem.RATING rating, String feedback) {
                    currentFeedbackSubmittedViaUI = feedback;

                    callback.onAddFeedback(latestRatingOfConversation.get().getId(), convertFromFeedbackComment(rating), feedback);
                    callback.onRefreshListView();
                }
            }));
            return list;

        } else {
            List<BaseListItem> list = new ArrayList<>();
            list.addAll(getOffboardingItemsOnRatingSubmission(nameToAddRatingAndFeedbackOf, convertToFeedbackRating(currentRatingSubmittedViaUI)));
            list.addAll(getOffboardingItemsOnCommentSubmission(currentRatingSubmittedViaUI, currentFeedbackSubmittedViaUI));
            return list;
        }
    }

    private List<BaseListItem> getOffboardingItemsToSelectRating(String lastAgentReplierName,
                                                                 final InputFeedbackRatingListItem.OnSelectRatingListener onSelectRatingListener) {
        List<BaseListItem> listItems = new ArrayList<>();
        listItems.add(new InputFeedbackRatingListItem(
                String.format(
                        Kayako.getApplicationContext().getString(R.string.ko__messenger_input_feedback_rating_instruction_message),
                        lastAgentReplierName),
                onSelectRatingListener)
        );
        return listItems;
    }

    private List<BaseListItem> getOffboardingItemsOnRatingSubmission(String lastAgentReplierName, InputFeedbackRatingListItem.RATING rating) {
        // TODO: Should we sort in order of when the rating was applied or always leave at the end of a conversation?

        // TODO: Show feedback item directly
        List<BaseListItem> listItems = new ArrayList<>();
        listItems.add(new InputFeedbackRatingListItem(
                String.format(
                        Kayako.getApplicationContext().getString(R.string.ko__messenger_input_feedback_rating_instruction_message),
                        lastAgentReplierName),
                rating.name())
        );
        return listItems;
    }

    private List<BaseListItem> getOffboardingItemsToAddComment(Rating.SCORE rating, InputFeedbackCommentListItem.OnAddFeedbackCommentCallback onAddFeedbackComment) {
        List<BaseListItem> listItems = new ArrayList<>();
        listItems.add(new InputFeedbackCommentListItem(
                getCommentInstructionMessage(rating),
                convertToFeebackComment(rating),
                onAddFeedbackComment));
        return listItems;
    }

    private List<BaseListItem> getOffboardingItemsOnCommentSubmission(Rating.SCORE rating, String feedback) {
        List<BaseListItem> listItems = new ArrayList<>();
        listItems.add(new InputFeedbackCommentListItem(
                getCommentInstructionMessage(rating),
                Kayako.getApplicationContext().getString(R.string.ko__messenger_input_feedback_comment_message_submitted)));
        return listItems;
    }

    private Rating getLatestRating(List<Rating> ratings) {
        Rating latestRating = null;
        for (Rating rating : ratings) {
            if (latestRating == null) {
                latestRating = rating;
            } else if (latestRating.getCreatedAt() < rating.getCreatedAt()) { // latest has greater epoch in milliseconds
                latestRating = rating;
            }
        }

        return latestRating;
    }

    private void setCurrentRatingViaUI(Rating rating) {
        if (rating == null || rating == null) {
            throw new IllegalArgumentException("Invalid argument");
        }

        // DO NOT REPLACE an existing UI submitted value with null value via API
        // Null Checks are added to safegaurd against inconsitent values b/w api and ui submitted values caused due to the async nature of the 2-step API requests made to add a complete feedback rating & comment

        if (rating.getScore() != null) {
            currentRatingSubmittedViaUI = rating.getScore();
        }

        if (rating.getComment() != null) {
            currentFeedbackSubmittedViaUI = rating.getComment();
        }
    }


    private InputFeedbackRatingListItem.RATING convertToFeedbackRating(Rating.SCORE score) {
        if (score == Rating.SCORE.BAD) {
            return InputFeedbackRatingListItem.RATING.BAD;
        } else if (score == Rating.SCORE.GOOD) {
            return InputFeedbackRatingListItem.RATING.GOOD;
        } else {
            throw new IllegalStateException("Unhandled enum state!");
        }
    }

    private InputFeedbackCommentListItem.RATING convertToFeebackComment(Rating.SCORE score) {
        if (score == Rating.SCORE.BAD) {
            return InputFeedbackCommentListItem.RATING.BAD;
        } else if (score == Rating.SCORE.GOOD) {
            return InputFeedbackCommentListItem.RATING.GOOD;
        } else {
            throw new IllegalStateException("Unhandled enum state!");
        }
    }


    private Rating.SCORE convertFromFeedbackRating(InputFeedbackRatingListItem.RATING rating) {
        if (rating == InputFeedbackRatingListItem.RATING.BAD) {
            return Rating.SCORE.BAD;
        } else if (rating == InputFeedbackRatingListItem.RATING.GOOD) {
            return Rating.SCORE.GOOD;
        } else {
            throw new IllegalStateException("Unhandled enum state!");
        }
    }

    private Rating.SCORE convertFromFeedbackComment(InputFeedbackCommentListItem.RATING rating) {
        if (rating == InputFeedbackCommentListItem.RATING.BAD) {
            return Rating.SCORE.BAD;
        } else if (rating == InputFeedbackCommentListItem.RATING.GOOD) {
            return Rating.SCORE.GOOD;
        } else {
            throw new IllegalStateException("Unhandled enum state!");
        }
    }


    private String getCommentInstructionMessage(Rating.SCORE score) {
        Context context = Kayako.getApplicationContext();
        switch (score) {
            case BAD:
                return context.getResources().getString(R.string.ko__messenger_input_feedback_comment_instruction_message_bad);
            case GOOD:
                return context.getResources().getString(R.string.ko__messenger_input_feedback_comment_instruction_message_good);
            default:
                return context.getResources().getString(R.string.ko__messenger_input_feedback_comment_instruction_message_generic);
        }
    }

    public interface OffboardingHelperViewCallback {
        void onRefreshListView();

        void onLoadRatings();

        void onAddRating(Rating.SCORE ratingScore);

        void onAddFeedback(long ratingId, Rating.SCORE score, String message);
    }

}
