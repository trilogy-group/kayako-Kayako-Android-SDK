package com.kayako.sdk.android.k5.activities;

import android.support.test.runner.AndroidJUnit4;

import com.kayako.sdk.android.k5.R;

import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class KayakoArticleActivityTest {

    public static void checkIfArticlePageDisplayed() {
        onView(withId(R.id.ko__article_title)).check(matches(isDisplayed()));
    }
}