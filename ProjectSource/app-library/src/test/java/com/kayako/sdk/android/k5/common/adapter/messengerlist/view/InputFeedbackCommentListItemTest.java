package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.mock;

public class InputFeedbackCommentListItemTest {

    private static final String INSTRUCTIONMESSAGE = "instruction";
    private InputFeedback.RATING rating;
    private InputFeedbackCommentListItem.OnAddFeedbackCommentCallback onAddFeedbackComment;
    private InputFeedbackCommentListItem listItem;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        rating = InputFeedback.RATING.GOOD;
        onAddFeedbackComment = mock(InputFeedbackCommentListItem.OnAddFeedbackCommentCallback.class);
        listItem = new InputFeedbackCommentListItem(INSTRUCTIONMESSAGE, rating, onAddFeedbackComment);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated(){
        errorCollector.checkThat(listItem, notNullValue());
        errorCollector.checkThat(listItem.getInstructionMessage(), is(equalTo(INSTRUCTIONMESSAGE)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNullInstructionMessageThenException(){
        listItem = new InputFeedbackCommentListItem(null, rating, onAddFeedbackComment);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNullRatingThenIllegalArgumentException(){
        rating = null;
        listItem = new InputFeedbackCommentListItem(INSTRUCTIONMESSAGE, rating, onAddFeedbackComment);
    }

    @Test
    public void validParamsGivenInConstructorThenObjectCreated(){
        listItem = new InputFeedbackCommentListItem(INSTRUCTIONMESSAGE, "submittedComment");
        errorCollector.checkThat(listItem.getItemType(), is(equalTo(MessengerListType.INPUT_FIELD_FEEDBACK_COMMENT)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNullSubmittedCommentThenException(){
        listItem = new InputFeedbackCommentListItem(INSTRUCTIONMESSAGE, null);
        errorCollector.checkThat(listItem.getItemType(), is(MessengerListType.INPUT_FIELD_FEEDBACK_COMMENT));
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
    public void getOnAddFeedbackComment() {
        errorCollector.checkThat(listItem.getOnAddFeedbackComment(), notNullValue());
        errorCollector.checkThat(listItem.getOnAddFeedbackComment(), is(onAddFeedbackComment));
    }

    @Test
    public void getContents() {
        listItem = new InputFeedbackCommentListItem(INSTRUCTIONMESSAGE, "submittedComment");
        errorCollector.checkThat(listItem.getContents().size() > 0, is(true));
        errorCollector.checkThat(listItem.getContents().get("instructionMessage"), is(equalTo(INSTRUCTIONMESSAGE)));
    }
}
