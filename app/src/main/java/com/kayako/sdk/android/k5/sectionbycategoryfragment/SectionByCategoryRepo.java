package com.kayako.sdk.android.k5.sectionbycategoryfragment;

import com.kayako.sdk.helpcenter.HelpCenter;
import com.kayako.sdk.helpcenter.category.Category;
import com.kayako.sdk.helpcenter.section.Section;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SectionByCategoryRepo {

    private List<Category> mCategories = null;
    private Map<Category, List<Section>> mSectionsByCategory = null;

    private HelpCenter mHelpCenter;

    public SectionByCategoryRepo(String helpCenterUrl, Locale locale) {
        mHelpCenter = new HelpCenter(helpCenterUrl, locale);
    }

    public List<Category> getCategories(boolean forceNetwork) {
        if (mCategories != null && !forceNetwork) {
            return mCategories;
        } else {
            return mCategories = mHelpCenter.getCategories(0, 999); // hack to load all categories
        }
    }

    public Map<Category, List<Section>> getSectionsByCategory(List<Category> categories, boolean forceNetwork) {
        if (mSectionsByCategory != null && !forceNetwork) {
            return mSectionsByCategory;

        } else {
            mSectionsByCategory = new HashMap<>();
            for (Category category : categories) {
                List<Section> sections = mHelpCenter.getSections(category.getId(), 0, 999); // hack to load all sections
                if (sections != null && sections.size() > 0) {
                    mSectionsByCategory.put(category, sections);
                }
            }
            return mSectionsByCategory;
        }
    }
}
