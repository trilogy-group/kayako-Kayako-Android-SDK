package com.kayako.sdk.android.k5.sectionbycategorypage;

import com.kayako.sdk.android.k5.common.data.ListItem;
import com.kayako.sdk.helpcenter.base.Resource;
import com.kayako.sdk.helpcenter.category.Category;
import com.kayako.sdk.helpcenter.section.Section;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SectionByCategoryPresenter implements SectionByCategoryPageContract.Presenter {

    private SectionByCategoryPageContract.View mSectionByCategoryView;
    private SectionByCategoryPageContract.Data mSectionByCategoryData;
    private List<ListItem> mListItems;

    public SectionByCategoryPresenter(SectionByCategoryPageContract.View mWelcomePageView) {
        this.mSectionByCategoryView = mWelcomePageView;
        mSectionByCategoryData = SectionByCategoryPageFactory.getDataSource("https://support.kayako.com", new Locale("en", "us"));
        // TODO: Figure out the best way to handle HelpCenter later.
    }

    @Override
    public void setView(SectionByCategoryPageContract.View view) {
        this.mSectionByCategoryView = view;
    }

    @Override
    public void initPage() {
        showLoadingViewAndHideOthers();
        mSectionByCategoryView.startBackgroundTask();
    }

    @Override
    public void onClickListItem(ListItem listItem) {
        Resource resource = listItem.getResource();
        if (resource instanceof Section) {
            mSectionByCategoryView.openArticleListingPage(((Section) resource).getId());
        }
    }

    @Override
    public void onClickSearch() {
        // TODO: Open intent
        mSectionByCategoryView.openSearchPage();
    }

    @Override
    public void reloadPage() {
        mSectionByCategoryView.startBackgroundTask();
    }

    @Override
    public boolean loadDataInBackground() {
        try {
            // TODO: Throw Exception in JAR. If it fails to load, do not return null - Shows empty view when it shouldn't
            // TODO: Strangely, if there's an error on the first call, there's a null returned on the second call - CHECK WHY?
            // TODO: Test empty
            List<Category> categories = mSectionByCategoryData.getCategories(false);
            Map<Category, List<Section>> sectionsByCategory = mSectionByCategoryData.getSectionsByCategory(categories, false);
            mListItems = setUpList(categories, sectionsByCategory);
            return true;
        } catch (Exception e) {
            // TODO: Find a better way to catch exceptions. Throw likely exceptions in each method
            // TODO: Throw custom errors? java.net.SocketException: Network is unreachable
            return false;
        }
    }

    @Override
    public void onDataLoaded(boolean isSuccessful) {
        if (isSuccessful) {
            mSectionByCategoryView.setUpList(mListItems);
            showDataViewAndHideOthers();
        } else {
            showErrorViewAndHideOthers();
        }
        // TODO: Check if categories loaded properly, and sectiosn - error?
    }

    private List<ListItem> setUpList(List<Category> categories, Map<Category, List<Section>> sectionsByCategory) {
        if (categories.size() == 0) {
            showEmptyViewAndHideOthers();
            return new ArrayList<>();
        } else {
            List<ListItem> items = new ArrayList<>();

            for (Category category : categories) {
                items.add(new ListItem(true, category.getTitle(), null, category));
                for (Section section : sectionsByCategory.get(category)) {
                    items.add(new ListItem(false, section.getTitle(), section.getDescription(), section));
                }
            }

            return items;
        }
    }

    private void showEmptyViewAndHideOthers() {
        mSectionByCategoryView.showOnlyEmptyView();
    }

    private void showLoadingViewAndHideOthers() {
        mSectionByCategoryView.showOnlyLoadingView();
    }

    private void showErrorViewAndHideOthers() {
        mSectionByCategoryView.showOnlyErrorView();
    }

    private void showDataViewAndHideOthers() {
        mSectionByCategoryView.showOnlyListView();
    }
}
