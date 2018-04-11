package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.containsString;

public class InputFeedbackCompletedListItemTest {

    private static final String INSTRUCTION_MESSAGE = "instruction_message";
    private static final String FEEDBACK = "test feedback";
    private InputFeedback.RATING rating;
    private InputFeedbackCompletedListItem listItem;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp(){
        rating = InputFeedback.RATING.GOOD;
        listItem = new InputFeedbackCompletedListItem(INSTRUCTION_MESSAGE, rating, FEEDBACK);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        errorCollector.checkThat(listItem.getInstructionMessage(), is(equalTo(INSTRUCTION_MESSAGE)));
        errorCollector.checkThat(listItem.getRating(), is(rating));
        errorCollector.checkThat(listItem.getFeedback(), is(equalTo(FEEDBACK)));
    }

    @Test
    public void whenNullRatingThenIllegalArgumentException() {
        final String exceptionMessage = "Invalid arguments";
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(containsString(exceptionMessage));
        InputFeedbackCompletedListItem listItemLocal = new InputFeedbackCompletedListItem(
                                        INSTRUCTION_MESSAGE, null, FEEDBACK);
    }

    @Test
    public void getContents() {
        final String good = "GOOD";
        errorCollector.checkThat(listItem.getContents().size(), is(5));
        errorCollector.checkThat(listItem.getContents().get("rating"), is(equalTo(good)));
    }
}