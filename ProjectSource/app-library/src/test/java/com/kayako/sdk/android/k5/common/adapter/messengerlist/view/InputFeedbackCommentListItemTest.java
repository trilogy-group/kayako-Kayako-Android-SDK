package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(MockitoJUnitRunner.class)
public class InputFeedbackCommentListItemTest {

    private static final String INSTRUCTION_MESSAGE = "instruction";
    private static final String EXCEPTION_MESSAGE = "Invalid arguments";
    private InputFeedback.RATING rating;
    private InputFeedbackCommentListItem listItem;

    @Mock
    private InputFeedbackCommentListItem.OnAddFeedbackCommentCallback onAddFeedbackComment;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        rating = InputFeedback.RATING.GOOD;
        listItem = new InputFeedbackCommentListItem(INSTRUCTION_MESSAGE, rating, onAddFeedbackComment);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated(){
        errorCollector.checkThat(listItem.getInstructionMessage(), is(equalTo(INSTRUCTION_MESSAGE)));
        errorCollector.checkThat(listItem.getRating(), is(rating));
        errorCollector.checkThat(listItem.getOnAddFeedbackComment(), is(onAddFeedbackComment));
    }

    @Test
    public void whenNullInstructionMessageThenException(){
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(containsString(EXCEPTION_MESSAGE));
        InputFeedbackCommentListItem listItemLocal =
                new InputFeedbackCommentListItem(null, rating, onAddFeedbackComment);
    }

    @Test
    public void whenNullRatingThenIllegalArgumentException(){
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(containsString(EXCEPTION_MESSAGE));
        InputFeedbackCommentListItem listItemLocal =
                new InputFeedbackCommentListItem(INSTRUCTION_MESSAGE, null, onAddFeedbackComment);
    }

    @Test
    public void validParamsGivenInConstructorThenObjectCreated(){
        final String submittedComment = "submitted_Comment";
        listItem = new InputFeedbackCommentListItem(INSTRUCTION_MESSAGE, submittedComment);
        errorCollector.checkThat(listItem.getItemType(), is(equalTo(MessengerListType.INPUT_FIELD_FEEDBACK_COMMENT)));
    }

    @Test
    public void whenNullSubmittedCommentThenException(){
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(containsString(EXCEPTION_MESSAGE));
        InputFeedbackCommentListItem listItemLocal =
                new InputFeedbackCommentListItem(INSTRUCTION_MESSAGE, null);
    }

    @Test
    public void getContents() {
        errorCollector.checkThat(listItem.getContents().isEmpty(), is(false));
        errorCollector.checkThat(listItem.getContents().get(
                "instructionMessage"), is(equalTo(INSTRUCTION_MESSAGE)));
    }
}