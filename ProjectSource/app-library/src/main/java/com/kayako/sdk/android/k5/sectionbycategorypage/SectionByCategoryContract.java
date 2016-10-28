package com.kayako.sdk.android.k5.sectionbycategorypage;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.mvp.BaseData;
import com.kayako.sdk.android.k5.common.mvp.BasePresenter;
import com.kayako.sdk.android.k5.common.mvp.BaseView;
import com.kayako.sdk.helpcenter.category.Category;
import com.kayako.sdk.helpcenter.section.Section;

import java.util.List;
import java.util.Map;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public interface SectionByCategoryContract {

    interface Data extends BaseData {
        List<Category> getCategories(boolean useCacheIfAvailable);

        Map<Category, List<Section>> getSectionsByCategory(List<Category> categories, boolean useCacheIfAvailable);

        boolean isCached();

        boolean doHelpCenterPreferencesMatch();
    }

    interface View extends BaseView {

        void setUpList(List<BaseListItem> items);

        void showOnlyListView();

        void showOnlyEmptyView();

        void showOnlyErrorView();

        void showOnlyLoadingView();

        void startBackgroundTask();

        void openArticleListingPage(Section section);

        void openSearchPage();
    }

    interface Presenter extends BasePresenter<SectionByCategoryContract.View> {

        void setData(SectionByCategoryContract.Data data);

        void initPage();

        boolean loadDataInBackground();

        void onDataLoaded(boolean isSuccessful);

        void onClickListItem(BaseListItem listItem);

        void onClickSearch();

        void reloadPage();
    }

}
