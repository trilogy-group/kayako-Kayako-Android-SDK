package com.kayako.sdk.android.k5.sectionbycategoryfragment;

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
    private List<Category> categories;
    private Map<Category, List<Section>> sectionsByCategory;

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
            System.out.println(((Section) resource).getId());
            // TODO: Intent should open new Activity by passing Section id
        }
    }

    @Override
    public void onClickSearch() {
        // TODO: Open intent
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
            categories = mSectionByCategoryData.getCategories(false);
            sectionsByCategory = mSectionByCategoryData.getSectionsByCategory(categories, false);
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
            setUpList(categories, sectionsByCategory); // TODO: Test empty
        } else {
            showErrorViewAndHideOthers();
        }
        // TODO: Check if categories loaded properly, and sectiosn - error?
    }

    private void setUpList(List<Category> categories, Map<Category, List<Section>> sectionsByCategory) {
        if (categories.size() == 0) {
            showEmptyViewAndHideOthers();
        } else {
            List<ListItem> items = new ArrayList<>();

            for (Category category : categories) {
                items.add(new ListItem(true, category.getTitle(), null, category));
                for (Section section : sectionsByCategory.get(category)) {
                    items.add(new ListItem(false, section.getTitle(), section.getDescription(), section));
                }
            }

            mSectionByCategoryView.setUpList(items);
            showDataViewAndHideOthers();
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
