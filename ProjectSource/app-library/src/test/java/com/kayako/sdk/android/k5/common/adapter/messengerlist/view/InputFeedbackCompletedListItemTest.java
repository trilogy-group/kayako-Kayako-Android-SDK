package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class InputFeedbackCompletedListItemTest {

    private static final String INSTRUCTIONMESSAGE = "message";
    private InputFeedback.RATING rating;
    private static final String FEEDBACK = "test feedback";
    private InputFeedbackCompletedListItem listItem;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp(){
        rating = InputFeedback.RATING.GOOD;
        listItem = new InputFeedbackCompletedListItem(INSTRUCTIONMESSAGE, rating, FEEDBACK);
    }

    @Test
    public void test_constructor1() {
        errorCollector.checkThat(listItem, notNullValue());
        errorCollector.checkThat(listItem.getFeedback(), is(equalTo(FEEDBACK)));

    }

    @Test(expected = IllegalArgumentException.class)
    public void test_constructor2() {
        rating = null;
        listItem = new InputFeedbackCompletedListItem(INSTRUCTIONMESSAGE, rating, FEEDBACK);
    }

    @Test
    public void test_getInstructionMessage() {
        errorCollector.checkThat(listItem.getInstructionMessage(), is(equalTo(INSTRUCTIONMESSAGE)));
    }

    @Test
    public void test_getRating() {
        errorCollector.checkThat(listItem.getRating().name(), is(equalTo("GOOD")));
    }

    @Test
    public void test_getFeedback() {
        errorCollector.checkThat(listItem.getFeedback(), is(equalTo(FEEDBACK)));
    }

    @Test
    public void test_getContents() {
        errorCollector.checkThat(listItem.getContents().size() > 0, is(true));
        errorCollector.checkThat(listItem.getContents().size(), is(5));
        errorCollector.checkThat(listItem.getContents().get("rating"), is(equalTo("GOOD")));
    }
}
