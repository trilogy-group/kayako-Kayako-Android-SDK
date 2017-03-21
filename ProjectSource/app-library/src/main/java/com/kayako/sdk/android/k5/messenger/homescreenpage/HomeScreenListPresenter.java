package com.kayako.sdk.android.k5.messenger.homescreenpage;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.HeaderListItem;

import java.util.ArrayList;
import java.util.List;

public class HomeScreenListPresenter implements HomeScreenListContract.Presenter {

    private HomeScreenListContract.View mView;

    public HomeScreenListPresenter(HomeScreenListContract.View view) {
        mView = view;
    }

    @Override
    public void initPage() {
        List<BaseListItem> baseListItems = new ArrayList<>();
        baseListItems.add(new HeaderListItem("Howdy", "What's up?")); // TODO: Default from strings.xml
        mView.setupList(baseListItems);
    }
}
