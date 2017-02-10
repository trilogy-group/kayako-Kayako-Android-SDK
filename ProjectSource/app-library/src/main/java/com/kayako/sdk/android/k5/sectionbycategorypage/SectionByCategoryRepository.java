package com.kayako.sdk.android.k5.sectionbycategorypage;

import com.kayako.sdk.android.k5.core.HelpCenterPref;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.helpcenter.HelpCenter;
import com.kayako.sdk.helpcenter.category.Category;
import com.kayako.sdk.helpcenter.section.Section;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SectionByCategoryRepository implements SectionByCategoryContract.Data {

    private List<Category> mCategories = null;
    private Map<Category, List<Section>> mSectionsByCategory = null;

    private HelpCenter mHelpCenter;
    private String mHelpCenterUrl;
    private Locale mLocale;

    public SectionByCategoryRepository(String helpCenterUrl, Locale locale) {
        mHelpCenter = new HelpCenter(helpCenterUrl, locale);
        mHelpCenterUrl = helpCenterUrl;
        mLocale = locale;
    }

    public List<Category> getCategories(boolean useCache) throws KayakoException {
        if (useCache && areCategoriesCached()) {
            return mCategories;
        } else {
            return mCategories = mHelpCenter.getCategories(0, 999); // hack to load all categories
        }
    }

    public Map<Category, List<Section>> getSectionsByCategory(List<Category> categories, boolean useCache) throws KayakoException{
        if (!areCategoriesCached()) {
            throw new NullPointerException("Categories have not been fetched yet. Please call getCategories() first");
        }

        if (useCache && areSectionsByCategoryCached()) { // Also check size - since hashmap is instantiated before the network call can fail
            return mSectionsByCategory;
        } else {
            mSectionsByCategory = new HashMap<>();
            for (Category category : categories) {
                List<Section> sections = mHelpCenter.getSections(category.getId(), 0, 999); // hack to load all sections
                if (sections != null && sections.size() > 0) {
                    mSectionsByCategory.put(category, sections);
                } else {
                    mSectionsByCategory.put(category, new ArrayList<Section>());
                }
            }
            return mSectionsByCategory;
        }
    }

    public boolean isCached() {
        return areSectionsByCategoryCached();
    }

    private boolean areCategoriesCached() {
        return mCategories != null
                && mCategories.size() > 0;
    }

    private boolean areSectionsByCategoryCached() {
        return areCategoriesCached()
                && mSectionsByCategory != null
                && mSectionsByCategory.size() == mCategories.size();
    }

    public boolean doHelpCenterPreferencesMatch() {
        return mHelpCenterUrl.equals(HelpCenterPref.getInstance().getHelpCenterUrl())
                && mLocale.equals(HelpCenterPref.getInstance().getLocale());
    }

}
