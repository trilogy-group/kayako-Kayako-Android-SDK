package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.view.View;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedback;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedbackRatingListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedbackRatingViewHolder;

public class InputFieldFeedbackRatingHelper {

    private InputFieldFeedbackRatingHelper() {
    }

    public static void configureInputFeedbackField(final InputFeedbackRatingViewHolder viewHolder, final InputFeedbackRatingListItem listItem) {
        if (listItem.hasSubmittedValue()) {
            setSelectedRatingState(viewHolder, listItem);
        } else {
            setSelectRatingState(viewHolder, listItem);
        }
    }

    public static void setRatingView(InputFeedback.RATING rating, InputFeedbackRatingViewHolder viewHolder) {
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

    public static void setSelectRatingListener(final InputFeedbackRatingViewHolder viewHolder, final InputFeedback.OnSelectRatingListener onSelectRatingListener) {
        viewHolder.badRatingViewSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRatingView(InputFeedback.RATING.BAD, viewHolder);

                onSelectRatingListener.onSelectRating(InputFeedback.RATING.BAD);
            }
        });


        viewHolder.goodRatingViewSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRatingView(InputFeedback.RATING.GOOD, viewHolder);

                onSelectRatingListener.onSelectRating(InputFeedback.RATING.GOOD);
            }
        });
    }

    private static void setSelectedRatingState(final InputFeedbackRatingViewHolder viewHolder, final InputFeedbackRatingListItem listItem) {
        InputFieldHelper.configureInputField(viewHolder, listItem.getInstructionMessage());
        InputFieldHelper.enableSubmittedLayout(viewHolder, listItem.getSubmittedValue());
    }

    private static void setSelectRatingState(final InputFeedbackRatingViewHolder viewHolder, final InputFeedbackRatingListItem listItem) {
        InputFieldHelper.configureInputField(viewHolder, listItem.getInstructionMessage());
        InputFieldHelper.enableInputLayoutWithoutButton(viewHolder);

        setSelectRatingListener(viewHolder, listItem.getOnSelectRatingListener());
    }

    /**
     * @param viewHolder
     * @return null if no rating is selected, rating is it is selected by user
     */
    private static InputFeedback.RATING getRating(InputFeedbackRatingViewHolder viewHolder) {
        if (viewHolder.badRatingViewSwitcher.getCurrentView() == viewHolder.selectedBadRatingView
                && viewHolder.goodRatingViewSwitcher.getCurrentView() == viewHolder.selectedGoodRatingView) {
            throw new IllegalStateException("Both Good and Bad ratings can not be selected at the same time");
        }

        if (viewHolder.badRatingViewSwitcher.getCurrentView() == viewHolder.selectedBadRatingView) {
            return InputFeedback.RATING.BAD;
        } else if (viewHolder.goodRatingViewSwitcher.getCurrentView() == viewHolder.selectedGoodRatingView) {
            return InputFeedback.RATING.GOOD;
        } else {
            return null; // NO RATING SELECTED
        }
    }

}
