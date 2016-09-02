package com.kayako.sdk.android.k5.sectionbycategoryfragment;

import com.kayako.sdk.android.k5.ui.BasePresenter;
import com.kayako.sdk.android.k5.ui.BaseView;
import com.kayako.sdk.android.k5.ui.ListItem;

import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public interface SectionByCategoryPageContract {

// TODO: Interface for Repo class (so that it can replaced with caching alternatives?
//    interface Data  {
//
//        List<Category> getCategories();
//    }


    interface View extends BaseView {

        void setUpList(List<ListItem> items);

        void showOnlyListView();

        void showOnlyEmptyView();

        void showOnlyErrorView();

        void showOnlyLoadingView();

        void startBackgroundTask();

    }

    interface Presenter extends BasePresenter<SectionByCategoryPageContract.View> {

        void initPage();

        void loadDataInBackground();

        void onDataLoaded();

        void onClickListItem(ListItem listItem);
    }
}
