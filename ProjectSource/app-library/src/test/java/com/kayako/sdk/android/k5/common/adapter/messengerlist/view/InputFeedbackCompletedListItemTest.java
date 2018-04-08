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
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp(){
        rating = InputFeedback.RATING.GOOD;
        listItem = new InputFeedbackCompletedListItem(INSTRUCTIONMESSAGE, rating, FEEDBACK);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        errorCollector.checkThat(listItem, notNullValue());
        errorCollector.checkThat(listItem.getFeedback(), is(equalTo(FEEDBACK)));

    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNullRatingThenIllegalArgumentException() {
        rating = null;
        listItem = new InputFeedbackCompletedListItem(INSTRUCTIONMESSAGE, rating, FEEDBACK);
    }

    @Test
    public void getInstructionMessage() {
        errorCollector.checkThat(listItem.getInstructionMessage(), is(equalTo(INSTRUCTIONMESSAGE)));
    }

    @Test
    public void getRating() {
        errorCollector.checkThat(listItem.getRating().name(), is(equalTo("GOOD")));
    }

    @Test
    public void getFeedback() {
        errorCollector.checkThat(listItem.getFeedback(), is(equalTo(FEEDBACK)));
    }

    @Test
    public void getContents() {
        errorCollector.checkThat(listItem.getContents().size() > 0, is(true));
        errorCollector.checkThat(listItem.getContents().size(), is(5));
        errorCollector.checkThat(listItem.getContents().get("rating"), is(equalTo("GOOD")));
    }
}
