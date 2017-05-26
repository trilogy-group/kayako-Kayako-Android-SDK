package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

public class InputFeedback{

    public enum RATING {
        GOOD, BAD
    }

    public interface OnSelectRatingListener {
        void onSelectRating(RATING rating);
    }
}
