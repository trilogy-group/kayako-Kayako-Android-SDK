package com.kayako.sdk.android.k5.sectionbycategorypage;

import com.kayako.sdk.android.k5.core.HelpCenterPref;
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
public class SectionByCategoryPresenter implements SectionByCategoryContract.Presenter {

    private SectionByCategoryContract.View mSectionByCategoryView;
    private SectionByCategoryContract.Data mSectionByCategoryData;
    private List<ListItem> mListItems;

    private String mHelpCenterUrl;
    private Locale mLocale;

    public SectionByCategoryPresenter(SectionByCategoryContract.View mWelcomePageView) {
        this.mSectionByCategoryView = mWelcomePageView;
        setUpData();
    }

    @Override
    public void setView(SectionByCategoryContract.View view) {
        this.mSectionByCategoryView = view;
    }

    @Override
    public void initPage() {
        resetDataIfHelpCenterValuesHaveChanged();

        invalidateOldValues();
        if (!mSectionByCategoryData.isCached()) { // Avoid showing a visual flicker when navigating between pages
            mSectionByCategoryView.showOnlyLoadingView();
        }
        mSectionByCategoryView.startBackgroundTask();
    }

    @Override
    public void onClickListItem(ListItem listItem) {
        Resource resource = listItem.getResource();
        if (resource instanceof Section) {
            mSectionByCategoryView.openArticleListingPage(((Section) resource));
        }
    }

    @Override
    public void onClickSearch() {
        mSectionByCategoryView.openSearchPage();
    }

    @Override
    public void reloadPage() {
        mSectionByCategoryView.showOnlyLoadingView();
        mSectionByCategoryView.startBackgroundTask();
    }

    @Override
    public boolean loadDataInBackground() {
        try {
            // TODO: Throw Exception in JAR. If it fails to load, do not return null - Shows empty view when it shouldn't
            // TODO: Strangely, if there's an error on the first call, there's a null returned on the second call - CHECK WHY?
            // TODO: Test empty
            List<Category> categories = mSectionByCategoryData.getCategories(true);
            Map<Category, List<Section>> sectionsByCategory = mSectionByCategoryData.getSectionsByCategory(categories, true);
            mListItems = setUpList(categories, sectionsByCategory);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: Find a better way to catch exceptions. Throw likely exceptions in each method
            // TODO: Throw custom errors? java.net.SocketException: Network is unreachable
            return false;
        }
    }

    @Override
    public void onDataLoaded(boolean isSuccessful) {
        if (isSuccessful) {
            mSectionByCategoryView.setUpList(mListItems);
            mSectionByCategoryView.showOnlyListView();
        } else {
            mSectionByCategoryView.showOnlyErrorView();
        }
        // TODO: Check if categories loaded properly, and sectiosn - error?
    }

    private List<ListItem> setUpList(List<Category> categories, Map<Category, List<Section>> sectionsByCategory) {
        if (categories.size() == 0) {
            mSectionByCategoryView.showOnlyEmptyView();
            return new ArrayList<>();
        } else {
            List<ListItem> items = new ArrayList<>();

            for (Category category : categories) {
                items.add(new ListItem(true, category.getTitle(), null, category));

                if (sectionsByCategory.containsKey(category)) {
                    for (Section section : sectionsByCategory.get(category)) {
                        items.add(new ListItem(false, section.getTitle(), section.getDescription(), section));
                    }
                }
            }

            return items;
        }
    }

    private void invalidateOldValues() {
        mListItems = null;
    }

    private void resetDataIfHelpCenterValuesHaveChanged() {
        String currentHelpCenterUrl = HelpCenterPref.getInstance().getHelpCenterUrl();
        Locale currentLocale = HelpCenterPref.getInstance().getLocale();
        if (!mHelpCenterUrl.equals(currentHelpCenterUrl) || !mLocale.equals(currentLocale)) {
            setUpData();
        }
    }

    private void setUpData() {
        mHelpCenterUrl = HelpCenterPref.getInstance().getHelpCenterUrl();
        mLocale = HelpCenterPref.getInstance().getLocale();
        mSectionByCategoryData = SectionByCategoryFactory.getDataSource(mHelpCenterUrl, mLocale);
    }
}
