package com.kayako.sdk.android.k5.common.fragments;

public interface OnScrollListListener {

    enum ScrollDirection {
        UP, DOWN, LEFT, RIGHT, NONE
    }

    void onScrollList(boolean isScrolling, ScrollDirection direction);
}
