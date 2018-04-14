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
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(MockitoJUnitRunner.class)
public class InputFeedbackRatingListItemTest {

    private static final String INSTRUCTION_MESSAGE = "instruction_message";
    private static final String EXCEPTION_MESSAGE = "Invalid arguments";
    private static final String SUBMITTED_RATING = "submitted_rating";
    private InputFeedbackRatingListItem ratingListItem;

    @Mock
    private InputFeedback.OnSelectRatingListener onSelectRatingListener;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        ratingListItem = new InputFeedbackRatingListItem(INSTRUCTION_MESSAGE, onSelectRatingListener);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        errorCollector.checkThat(ratingListItem.getInstructionMessage(), is(INSTRUCTION_MESSAGE));
        errorCollector.checkThat(ratingListItem.getOnSelectRatingListener(), is(onSelectRatingListener));
        errorCollector.checkThat(ratingListItem.getItemType(), is(MessengerListType.INPUT_FIELD_FEEDBACK_RATING));
    }

    @Test
    public void whenNullInstructionMessageThenIllegalStateException() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage(containsString(EXCEPTION_MESSAGE));
        new InputFeedbackRatingListItem(null, onSelectRatingListener);
    }

    @Test
    public void whenNullOnSelectRatingListenerThenException() {
        InputFeedback.OnSelectRatingListener onSelectRatingListener = null;
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage(containsString(EXCEPTION_MESSAGE));
        new InputFeedbackRatingListItem(INSTRUCTION_MESSAGE, onSelectRatingListener);
    }

    @Test
    public void whenValidParamsGivenConstructorThenObjectCreated() {
        InputFeedbackRatingListItem listItemLocal = new InputFeedbackRatingListItem(INSTRUCTION_MESSAGE, SUBMITTED_RATING);
        errorCollector.checkThat(listItemLocal.getInstructionMessage(), is(INSTRUCTION_MESSAGE));
    }

    @Test
    public void whenNullSubmittedRatingThenIllegalStateException() {
        String submittedRating = null;
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage(containsString(EXCEPTION_MESSAGE));
        InputFeedbackRatingListItem listItem = new InputFeedbackRatingListItem(INSTRUCTION_MESSAGE, submittedRating);
    }

    @Test
    public void getContents() {
        InputFeedbackRatingListItem listItem =
                new InputFeedbackRatingListItem(INSTRUCTION_MESSAGE, SUBMITTED_RATING);
        errorCollector.checkThat(listItem.getContents().isEmpty(), is(false));
        errorCollector.checkThat(ratingListItem.getContents().get("instructionMessage"), is(INSTRUCTION_MESSAGE));
    }
}
