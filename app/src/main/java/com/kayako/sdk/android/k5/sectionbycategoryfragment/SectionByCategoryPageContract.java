package com.kayako.sdk.android.k5.sectionbycategoryfragment;

import com.kayako.sdk.android.k5.common.mvp.BasePresenter;
import com.kayako.sdk.android.k5.common.mvp.BaseView;
import com.kayako.sdk.android.k5.common.data.ListItem;

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

        boolean loadDataInBackground();

        void onDataLoaded(boolean isSuccessful);

        void onClickListItem(ListItem listItem);

        void onClickSearch();

        void reloadPage();
    }

}
