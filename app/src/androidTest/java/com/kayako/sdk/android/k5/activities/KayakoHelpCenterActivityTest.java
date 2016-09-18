package com.kayako.sdk.android.k5.activities;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.kayako.sdk.android.k5.R;

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

    public static void checkIfArticleListingPageDisplayed() {
        onView(withId(R.id.ko__section_title)).check(matches(isDisplayed()));
    }

    @Rule
    public ActivityTestRule<KayakoHelpCenterActivity> mActivityRule = new ActivityTestRule<>(KayakoHelpCenterActivity.class);

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
        onView(withId(R.id.ko__list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        KayakoArticleActivityTest.checkIfArticlePageDisplayed();
    }

}