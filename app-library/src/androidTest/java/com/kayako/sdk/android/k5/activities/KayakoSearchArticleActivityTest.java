package com.kayako.sdk.android.k5.activities;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.core.KayakoHC;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class KayakoSearchArticleActivityTest {

    @Rule
    public ActivityTestRule<KayakoSearchArticleActivity> mActivityRule = new ActivityTestRule<>(
            KayakoSearchArticleActivity.class);

    @Before
    public void setUp() throws Exception {
        KayakoHC.initialize(mActivityRule.getActivity().getApplicationContext());
    }


    public static void checkIfSearchActivityDisplayed() {
        onView(withHint(R.string.ko__label_search_hint)).check(matches(isDisplayed()));
    }

    @Test
    public void whenQueryIsLessThanThreeCharactersShowBlankScreen() {
        onView(withId(R.id.ko__toolbar_search_edittext)).perform(typeText("Oye"));
        onView(withId(R.id.ko__inflated_stub_loading_state)).check(doesNotExist());
    }

    @Test
    public void whenQueryIsMoreThanThreeCharactersShowResults() {
        onView(withId(R.id.ko__toolbar_search_edittext)).perform(typeText("Kayako"));
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.ko__list)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void whenQueryHasMoreThanThreeCharactersAndHasNoResultsThenShowEmptyView() {
        onView(withId(R.id.ko__toolbar_search_edittext)).perform(typeText("dasuaf"));
        onView(withId(R.id.ko__inflated_stub_empty_state)).check(matches(isDisplayed()));
    }

}