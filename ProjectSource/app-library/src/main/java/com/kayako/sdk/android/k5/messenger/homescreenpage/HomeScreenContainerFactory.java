package com.kayako.sdk.android.k5.messenger.homescreenpage;

public class HomeScreenContainerFactory {

    private HomeScreenContainerFactory() {
    }

    public static HomeScreenContainerContract.Presenter getPresenter(HomeScreenContainerContract.View view) {
        return new HomeScreenContainerPresenter(view);
    }
}
