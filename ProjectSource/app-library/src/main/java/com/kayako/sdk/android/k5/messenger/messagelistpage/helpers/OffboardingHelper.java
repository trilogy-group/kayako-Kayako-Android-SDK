package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UnsentMessage;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.BotMessageListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedbackListItem;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.messenger.rating.Rating;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * All logic involving offboarding items that ask for feedback
 */
public class OffboardingHelper {

    public OffboardingHelper() {
    }

    private InputFeedbackListItem.RATING currentRating;
    private String currentFeedback;

    private AtomicBoolean hasRatingsBeenLoadedViaApi = new AtomicBoolean(false);

    public void onLoadRatings(List<Rating> ratings, OffboardingHelperViewCallback callback) {
        setCurrentRating(ratings);


        if (!hasRatingsBeenLoadedViaApi.get()) {
            hasRatingsBeenLoadedViaApi.set(true);
            callback.onRefreshListView();
        }
    }

    public void onUpdateRating(Rating rating, OffboardingHelperViewCallback callback) {
        /*
            This method is called when feedback has been successfully sent via API.
         */

        setCurrentRating(rating);
        hasRatingsBeenLoadedViaApi.set(true);
        callback.onRefreshListView();
    }

    public void onLoadConversation(OffboardingHelperViewCallback callback) {
        // Both when a new conversation is created and when loading an existing conversation
        // This is done only once a conversation is loaded because to load ratings of a conversation, we need to make sure the conversation exists

        // Load rating via API as long current rating is null
        if (currentRating == null) {
            callback.onLoadRating();
        }
    }

    public List<BaseListItem> getOffboardingListItems(String nameToAddRatingAndFeedbackOf, boolean isConversationClosed, final OffboardingHelperViewCallback callback) {
        if (nameToAddRatingAndFeedbackOf == null || callback == null) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        if (!isConversationClosed ||   // Do not show feedback items if conversation is not closed-
                !hasRatingsBeenLoadedViaApi.get()) { // Do not show feedback items if ratings have not been loaded via API yet (user may have already selected a rating before)
            return Collections.EMPTY_LIST;
        }


        if (currentRating == null) {
            return getOffboardingItemsToSelectRating(nameToAddRatingAndFeedbackOf, new InputFeedbackListItem.OnSelectRatingListener() {
                @Override
                public void onSelectRating(InputFeedbackListItem.RATING rating) {
                    callback.onAddRating(convert(rating));
                    callback.onRefreshListView();
                }
            });
        } else {
            return getOffboardingItemsOnRatingSubmission(nameToAddRatingAndFeedbackOf, currentRating);
        }

        // TODO: Handle situation when rating is assigned but feedback is not
    }

    private List<BaseListItem> getOffboardingItemsToSelectRating(String lastAgentReplierName,
                                                                 final InputFeedbackListItem.OnSelectRatingListener onSelectRatingListener) {
        List<BaseListItem> listItems = new ArrayList<>();
        listItems.add(new InputFeedbackListItem(
                String.format(
                        Kayako.getApplicationContext().getString(R.string.ko__messenger_input_feedback_rating_instruction_message),
                        lastAgentReplierName),
                new InputFeedbackListItem.OnSelectRatingListener() {
                    @Override
                    public void onSelectRating(InputFeedbackListItem.RATING rating) {
                        currentRating = rating;
                        onSelectRatingListener.onSelectRating(rating);
                    }
                })
        );
        return listItems;
    }

    private List<BaseListItem> getOffboardingItemsOnRatingSubmission(String lastAgentReplierName, InputFeedbackListItem.RATING rating) {
        // TODO: Should we sort in order of when the rating was applied or always leave at the end of a conversation?

        // TODO: Show feedback item directly
        List<BaseListItem> listItems = new ArrayList<>();
        listItems.add(new InputFeedbackListItem(
                String.format(
                        Kayako.getApplicationContext().getString(R.string.ko__messenger_input_feedback_rating_instruction_message),
                        lastAgentReplierName),
                rating.name())
        );
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

    private void setCurrentRating(List<Rating> ratings) {
        if (ratings == null || ratings.size() == 0) {
            currentRating = null;
        } else {
            setCurrentRating(
                    getLatestRating(ratings));
        }
    }

    private void setCurrentRating(Rating rating) {
        if (rating == null || rating == null) {
            throw new IllegalArgumentException("Invalid argument");
        }

        currentRating = convert(rating.getScore());
        if (rating.getComment() != null) {
            currentFeedback = rating.getComment();
        }
    }

    private InputFeedbackListItem.RATING convert(Rating.SCORE score) {
        if (score == Rating.SCORE.BAD) {
            return InputFeedbackListItem.RATING.BAD;
        } else if (score == Rating.SCORE.GOOD) {
            return InputFeedbackListItem.RATING.GOOD;
        } else {
            throw new IllegalStateException("Unhandled enum state!");
        }
    }

    private Rating.SCORE convert(InputFeedbackListItem.RATING rating) {
        if (rating == InputFeedbackListItem.RATING.BAD) {
            return Rating.SCORE.BAD;
        } else if (rating == InputFeedbackListItem.RATING.GOOD) {
            return Rating.SCORE.GOOD;
        } else {
            throw new IllegalStateException("Unhandled enum state!");
        }
    }


    public interface OffboardingHelperViewCallback {
        void onRefreshListView();

        void onLoadRating();

        void onAddRating(Rating.SCORE ratingScore);

        void onAddFeedback(Rating.SCORE ratingScore, String message);
    }

}
