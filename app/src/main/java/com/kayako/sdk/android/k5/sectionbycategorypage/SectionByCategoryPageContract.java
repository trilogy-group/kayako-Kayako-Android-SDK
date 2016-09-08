package com.kayako.sdk.android.k5.sectionbycategorypage;

import com.kayako.sdk.android.k5.common.mvp.BaseData;
import com.kayako.sdk.android.k5.common.mvp.BasePresenter;
import com.kayako.sdk.android.k5.common.mvp.BaseView;
import com.kayako.sdk.android.k5.common.data.ListItem;
import com.kayako.sdk.helpcenter.category.Category;
import com.kayako.sdk.helpcenter.section.Section;

import java.util.List;
import java.util.Map;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public interface SectionByCategoryPageContract {

    interface Data extends BaseData {
        List<Category> getCategories(boolean forceNetwork);

        Map<Category, List<Section>> getSectionsByCategory(List<Category> categories, boolean forceNetwork);
    }

    interface View extends BaseView {

        void setUpList(List<ListItem> items);

        void showOnlyListView();

        void showOnlyEmptyView();

        void showOnlyErrorView();

        void showOnlyLoadingView();

        void startBackgroundTask();

        void openArticleListingPage(long sectionId);

        void openSearchPage();
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
