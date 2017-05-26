package com.kayako.sdk.android.k5.messenger.homescreenpage;

public class HomeScreenListFactory {

    private HomeScreenListFactory() {
    }

    public static HomeScreenListContract.Presenter getPresenter(HomeScreenListContract.View view) {
        return new HomeScreenListPresenter(view);
    }
}
