package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.view.View;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedbackListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedbackViewHolder;

public class InputFieldFeedbackHelper {

    private InputFieldFeedbackHelper() {
    }

    public static void configureInputFeedbackField(final InputFeedbackViewHolder viewHolder, final InputFeedbackListItem listItem) {
        if (listItem.hasSubmittedValue()) {
            setSelectedRatingState(viewHolder, listItem);
        } else {
            setSelectRatingState(viewHolder, listItem);
        }
    }

    private static void setSelectedRatingState(final InputFeedbackViewHolder viewHolder, final InputFeedbackListItem listItem) {
        InputFieldHelper.configureInputField(viewHolder, BotMessageHelper.getBotDrawable(), listItem.getInstructionMessage());
        InputFieldHelper.enableSubmittedLayout(viewHolder, listItem.getSubmittedValue());
    }

    private static void setSelectRatingState(final InputFeedbackViewHolder viewHolder, final InputFeedbackListItem listItem) {
        InputFieldHelper.configureInputField(viewHolder, BotMessageHelper.getBotDrawable(), listItem.getInstructionMessage());
        InputFieldHelper.enableInputLayout(viewHolder);

        configureSubmitButtonVisibility(viewHolder);

        viewHolder.badRatingViewSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRating(InputFeedbackListItem.RATING.BAD, viewHolder);
                configureSubmitButtonVisibility(viewHolder);
            }
        });


        viewHolder.goodRatingViewSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRating(InputFeedbackListItem.RATING.GOOD, viewHolder);
                configureSubmitButtonVisibility(viewHolder);
            }
        });


        viewHolder.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputFeedbackListItem.RATING rating = getRating(viewHolder);
                if (rating == null) {
                    throw new IllegalStateException("Submit button should not be visibile if rating is not first applied");
                } else {
                    listItem
                            .getOnSelectRatingListener()
                            .onSelectRating(rating);
                }
            }
        });
    }

    private static void configureSubmitButtonVisibility(InputFeedbackViewHolder viewHolder) {
        // Submit button should not be visibile if rating is not first applied. This is done instead of showing an error response when submit button is clicked without selecting a rating.
        if (getRating(viewHolder) == null) {
            viewHolder.submitButtonLayout.setVisibility(View.GONE);
        } else {
            viewHolder.submitButtonLayout.setVisibility(View.VISIBLE);
        }
    }

    private static void setRating(InputFeedbackListItem.RATING rating, InputFeedbackViewHolder viewHolder) {
        switch (rating) {
            case GOOD:
                // Show default BAD Rating view
                if (viewHolder.badRatingViewSwitcher.getCurrentView() == viewHolder.selectedBadRatingView) {
                    viewHolder.badRatingViewSwitcher.showNext();
                }

                // Show selected GOOD Rating View
                if (viewHolder.goodRatingViewSwitcher.getCurrentView() != viewHolder.selectedGoodRatingView) {
                    viewHolder.goodRatingViewSwitcher.showNext();
                }
                break;

            case BAD:
                // Show selected BAD Rating view
                if (viewHolder.badRatingViewSwitcher.getCurrentView() != viewHolder.selectedBadRatingView) {
                    viewHolder.badRatingViewSwitcher.showNext();
                }

                // Show default GOOD Rating View
                if (viewHolder.goodRatingViewSwitcher.getCurrentView() == viewHolder.selectedGoodRatingView) {
                    viewHolder.goodRatingViewSwitcher.showNext();
                }
                break;

            default:
                throw new IllegalStateException("Unhandled RATING");
        }
    }

    /**
     * @param viewHolder
     * @return null if no rating is selected, rating is it is selected by user
     */
    private static InputFeedbackListItem.RATING getRating(InputFeedbackViewHolder viewHolder) {
        if (viewHolder.badRatingViewSwitcher.getCurrentView() == viewHolder.selectedBadRatingView
                && viewHolder.goodRatingViewSwitcher.getCurrentView() == viewHolder.selectedGoodRatingView) {
            throw new IllegalStateException("Both Good and Bad ratings can not be selected at the same time");
        }

        if (viewHolder.badRatingViewSwitcher.getCurrentView() == viewHolder.selectedBadRatingView) {
            return InputFeedbackListItem.RATING.BAD;
        } else if (viewHolder.goodRatingViewSwitcher.getCurrentView() == viewHolder.selectedGoodRatingView) {
            return InputFeedbackListItem.RATING.GOOD;
        } else {
            return null; // NO RATING SELECTED
        }
    }

}
