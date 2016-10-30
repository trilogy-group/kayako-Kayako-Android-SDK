package com.kayako.sdk.android.k5.activities;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.TestUtils;
import com.kayako.sdk.android.k5.core.Kayako;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
@RunWith(AndroidJUnit4.class)
public class KayakoHelpCenterActivityTest {

    @Rule
    public ActivityTestRule<KayakoHelpCenterActivity> mActivityRule = new ActivityTestRule<>(KayakoHelpCenterActivity.class);

    @Before
    public void setUp() throws Exception {
        Kayako.initialize(mActivityRule.getActivity());
    }

    public static void checkIfActivityOpened(){
        onView(withId(R.id.ko__fragment_listing)).check(matches(isDisplayed()));
    }

    public static void checkIfArticleListingPageDisplayed() {
        onView(withId(R.id.ko__section_title)).check(matches(isDisplayed()));
    }

    public static void checkIfSectionsByCategoryPageIsDisplayed() {
        onView(withId(R.id.ko__search_bar)).check(matches(isDisplayed()));
    }

    private static void navigateToArticleListingPage() {
        onView(withId(R.id.ko__list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
    }

    @Test
    public void clickSearchBarToOpenSearchActivity() throws Exception {
        onView(withId(R.id.ko__search_bar)).perform(click());
        KayakoSearchArticleActivityTest.checkIfSearchActivityDisplayed();
    }

    @Test
    public void clickSectionItemToOpenArticleListingPage() throws Exception {
        onView(withId(R.id.ko__list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        checkIfArticleListingPageDisplayed();
    }

    @Test
    public void clickArticleItemToOpenArticlePage() throws Exception {
        clickSectionItemToOpenArticleListingPage();
        navigateToArticleListingPage();
        KayakoArticleActivityTest.checkIfArticlePageDisplayed();
    }

    @Test
    public void ensureArticleListingPageIsDisplayedOnOrientationChange() {
        Kayako.initialize(mActivityRule.getActivity());
        navigateToArticleListingPage();
        TestUtils.changeOrientationToLandscapeMode(mActivityRule.getActivity());
        checkIfArticleListingPageDisplayed();
    }

}