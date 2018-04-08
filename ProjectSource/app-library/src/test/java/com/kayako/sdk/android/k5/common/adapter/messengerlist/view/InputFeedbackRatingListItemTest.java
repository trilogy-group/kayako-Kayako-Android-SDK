package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.mock;

public class InputFeedbackRatingListItemTest {

    private String instructionMessage;
    private InputFeedback.OnSelectRatingListener onSelectRatingListener;
    private InputFeedbackRatingListItem ratingListItem;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        instructionMessage = "instruction";
        onSelectRatingListener = mock(InputFeedback.OnSelectRatingListener.class);
        ratingListItem = new InputFeedbackRatingListItem(instructionMessage, onSelectRatingListener);
    }

    @Test
    public void test_constructor(){
        errorCollector.checkThat(ratingListItem, notNullValue());
        errorCollector.checkThat(ratingListItem.getInstructionMessage(), is(equalTo(instructionMessage)));
        errorCollector.checkThat(ratingListItem.getItemType(), is(equalTo(MessengerListType.INPUT_FIELD_FEEDBACK_RATING)));
    }

    @Test(expected = IllegalStateException.class)
    public void test_constructor1(){
        instructionMessage = null;
        ratingListItem = new InputFeedbackRatingListItem(instructionMessage, onSelectRatingListener);
    }

    @Test(expected = IllegalStateException.class)
    public void test_constructor2(){
        InputFeedback.OnSelectRatingListener onSelectRatingListener = null;
        ratingListItem = new InputFeedbackRatingListItem(instructionMessage, onSelectRatingListener);
    }

    @Test
    public void test_constructor3() {
        InputFeedbackRatingListItem listItem = new InputFeedbackRatingListItem(instructionMessage, "submitted_rating");
        errorCollector.checkThat(listItem, notNullValue());
        errorCollector.checkThat(listItem.getItemType(), notNullValue());
    }

    @Test(expected = IllegalStateException.class)
    public void test_constructor4() {
        String submittedRating = null;
        InputFeedbackRatingListItem listItem = new InputFeedbackRatingListItem(instructionMessage, submittedRating);
    }


    @Test
    public void test_getInstructionMessage() {
        errorCollector.checkThat(ratingListItem.getInstructionMessage(), is(equalTo(instructionMessage)));
    }

    @Test
    public void test_getOnSelectRatingListener() {
        errorCollector.checkThat(ratingListItem.getOnSelectRatingListener(), notNullValue());
        errorCollector.checkThat(ratingListItem.getOnSelectRatingListener(), is(equalTo(onSelectRatingListener)));
    }

    @Test
    public void test_getContents() {
        InputFeedbackRatingListItem listItem = new InputFeedbackRatingListItem(instructionMessage, "submitted_rating");
        errorCollector.checkThat(listItem.getContents().size() > 0, is(true));
        errorCollector.checkThat(ratingListItem.getContents().get("instructionMessage"), is(equalTo(instructionMessage)));
    }
}
