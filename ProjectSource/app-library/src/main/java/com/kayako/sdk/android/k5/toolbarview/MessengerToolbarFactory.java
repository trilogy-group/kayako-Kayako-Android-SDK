package com.kayako.sdk.android.k5.toolbarview;

public class MessengerToolbarFactory {

    private static MessengerToolbarContract.Data mData; // Cached because it's used commonly across pages

    public static MessengerToolbarContract.Presenter getPresenter(MessengerToolbarContract.ConfigureView view) {
        return new MessengerToolbarPresenter(view, getData());
    }

    public static MessengerToolbarContract.Data getData() {
        if (mData == null) {
            return mData = new MessengerToolbarRepository();
        } else {
            return mData;
        }
    }
}
