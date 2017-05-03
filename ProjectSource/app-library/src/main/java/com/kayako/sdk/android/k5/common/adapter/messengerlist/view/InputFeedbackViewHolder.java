package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import android.view.View;
import android.widget.Button;
import android.widget.ViewSwitcher;

import com.kayako.sdk.android.k5.R;

public class InputFeedbackViewHolder extends InputFieldViewHolder {

    public ViewSwitcher goodRatingViewSwitcher;
    public ViewSwitcher badRatingViewSwitcher;

    public View selectedGoodRatingView;
    public View selectedBadRatingView;
    public View submitButtonLayout;

    public Button submitButton;

    public InputFeedbackViewHolder(View itemView) {
        super(itemView, R.layout.ko__include_messenger_input_field_feedback_rating);

        goodRatingViewSwitcher = (ViewSwitcher) itemView.findViewById(R.id.ko__messenger_input_feedback_rating_field_good_view_switcher);
        badRatingViewSwitcher = (ViewSwitcher) itemView.findViewById(R.id.ko__messenger_input_feedback_rating_field_bad_view_switcher);
        submitButton = (Button) itemView.findViewById(R.id.ko__email_input_field_submit_button);
        submitButtonLayout = itemView.findViewById(R.id.ko__email_input_field_submit_button_layout);

        selectedGoodRatingView = itemView.findViewById(R.id.ko__messenger_input_feedback_rating_field_good_view_selected);
        selectedBadRatingView = itemView.findViewById(R.id.ko__messenger_input_feedback_rating_field_bad_view_selected);
    }
}
