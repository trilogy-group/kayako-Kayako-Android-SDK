package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import android.content.Context;
import android.content.res.Resources;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedback;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedbackRatingListItem;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.messenger.rating.Rating;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.reflect.Whitebox.invokeMethod;
import static org.powermock.reflect.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        OffboardingHelper.class,
        Kayako.class
})
public class OffboardingHelperTest {
    private OffboardingHelper underTest;
    @Rule
    private ExpectedException expectedException = ExpectedException.none();
    @Mock
    private Rating rating;
    @Mock
    Resources resources;
    @Mock
    private Context context;
    @Mock
    private OffboardingHelper.OffboardingHelperViewCallback offboardingHelperViewCallback;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        staticMock();
        underTest = spy(new OffboardingHelper());
    }

    @Test
    public void onUpdateRating() throws Exception {
        // Arrange
        when(rating.getId()).thenReturn(42L);
        when(rating.getScore()).thenReturn(Rating.SCORE.GOOD);
        when(rating.getComment()).thenReturn("good");

        doNothing().when(underTest, method(OffboardingHelper.class, "removeFromQueue"))
                .withArguments(offboardingHelperViewCallback, Rating.SCORE.GOOD, "good");

        // Act
        underTest.onUpdateRating(rating, offboardingHelperViewCallback);
        // Assert
        ArgumentCaptor<Boolean> argumentCaptor = ArgumentCaptor.forClass(Boolean.class);
        verify(offboardingHelperViewCallback).onRefreshListView(argumentCaptor.capture());
        assertTrue(argumentCaptor.getValue());
    }

    @Test
    public void onFailureToUpdateRating() {
        // Act
        underTest.onFailureToUpdateRating(Rating.SCORE.GOOD, "feedback",
                offboardingHelperViewCallback);
        // Assert cannot be done due to private calls
    }

    @Test
    public void givenOnLoadConversationWhenMWasConversationOriginallyCompletedNull() {
        // Act
        boolean isConversationCompleted = true;
        underTest.onLoadConversation(isConversationCompleted, offboardingHelperViewCallback);
        // Assert cannot be done due to private calls
    }

    @Test
    public void
    givenOnLoadConversationWhenMWasConversationOriginallyCompletedNotNullThenCallbackOnRefreshListView() {
        // Arrange
        setInternalState(underTest, "mWasConversationOriginallyCompleted", true);
        setInternalState(underTest, "mIsConversationCompleted", new AtomicBoolean(false));
        // Act
        boolean isConversationCompleted = true;
        underTest.onLoadConversation(isConversationCompleted, offboardingHelperViewCallback);
        // Assert
        ArgumentCaptor<Boolean> argumentCaptor = ArgumentCaptor.forClass(Boolean.class);
        verify(offboardingHelperViewCallback).onRefreshListView(argumentCaptor.capture());
        assertTrue(argumentCaptor.getValue());
    }

    @Test
    public void givenGetOffboardingListItemsWhenNameNullThenException() {
        // Assert
        expectedException.expect(Exception.class);
        // Act
        underTest.getOffboardingListItems(null, true, offboardingHelperViewCallback);
    }

    @Test
    public void givenGetOffboardingListItemsWhenConversationNotCompletedThenEmptyList() {
        // Act
        List<BaseListItem> result = underTest.getOffboardingListItems("name", false,
                offboardingHelperViewCallback);
        // Assert
        assertEquals(Collections.EMPTY_LIST, result);
    }

    @Test
    public void
    givenGetOffboardingListItemsWhenCurrentRatingSubmittedViaUINullThenGetOffboardingItemsToSelectRating() {
        // Arrange
        Rating.SCORE currentRatingSubmittedViaUI = null;
        setInternalState(underTest, "currentRatingSubmittedViaUI", currentRatingSubmittedViaUI);
        when(Kayako.getApplicationContext()).thenReturn(context);
        when(context.getString(R.string.ko__messenger_input_feedback_rating_instruction_message))
                .thenReturn("message");
        // Act
        List<BaseListItem> result = underTest.getOffboardingListItems("name", true,
                offboardingHelperViewCallback);
        // Assert
        assertTrue(result.get(0) instanceof InputFeedbackRatingListItem);
    }

    @Test
    public void
    givenGetOffboardingListItemsWhenCurrentFeedSubmittedViaUINullThenGetOffboardingItemsToSelectRating() {
        // Arrange
        Rating.SCORE currentRatingSubmittedViaUI = Rating.SCORE.GOOD;
        setInternalState(underTest, "currentRatingSubmittedViaUI", currentRatingSubmittedViaUI);
        String currentFeedbackSubmittedViaUI = null;
        setInternalState(underTest, "currentFeedbackSubmittedViaUI", currentFeedbackSubmittedViaUI);

        when(Kayako.getApplicationContext()).thenReturn(context);
        when(context.getResources()).thenReturn(resources);
        when(resources.getString(R.string
                .ko__messenger_input_feedback_rating_instruction_message)).thenReturn("message");
        // Act
        List<BaseListItem> result = underTest.getOffboardingListItems("name", true,
                offboardingHelperViewCallback);
        // Assert
        assertFalse(result.isEmpty());
    }

    @Test
    public void
    givenGetOffboardingListItemsWhenFeedbackAndRatingNotNullThenGetOffboardingItemsToSelectRating
            () {
        // Arrange
        Rating.SCORE currentRatingSubmittedViaUI = Rating.SCORE.GOOD;
        setInternalState(underTest, "currentRatingSubmittedViaUI", currentRatingSubmittedViaUI);
        String currentFeedbackSubmittedViaUI = "good";
        setInternalState(underTest, "currentFeedbackSubmittedViaUI", currentFeedbackSubmittedViaUI);
        // Act
        List<BaseListItem> result = underTest.getOffboardingListItems("name", true,
                offboardingHelperViewCallback);
        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    public void givenResetRatingAddedByUserThenIdOfLastRatingSetToZero() throws Exception {
        // Arrange
        AtomicLong mIdOfLastRatingAdded = mock(AtomicLong.class);
        setInternalState(underTest, "mIdOfLastRatingAdded", mIdOfLastRatingAdded);
        // Act
        invokeMethod(underTest, "resetRatingAddedByUser");
        // Assert
        assertTrue(0L == mIdOfLastRatingAdded.get());
    }

    @Test
    public void givenConvertWhenScoreBadThenInputFeedbackRatingBad() throws Exception {
        // Arrange
        Rating.SCORE score = Rating.SCORE.BAD;
        // Act
        InputFeedback.RATING result = invokeMethod(underTest, "convert", score);
        // Assert
        assertEquals(InputFeedback.RATING.BAD, result);
    }

    @Test
    public void givenConvertWhenScoreNullThenException() throws Exception {
        // Arrange
        Rating.SCORE score = null;
        // Assert
        expectedException.expect(Exception.class);
        // Act
        invokeMethod(underTest, "convert", score);
    }

    @Test
    public void givenConvertFromInputFieldRatingWhenRatingBadThenScoreBad() throws Exception {
        // Arrange
        InputFeedback.RATING rating = InputFeedback.RATING.BAD;
        // Act
        Rating.SCORE result = invokeMethod(underTest, "convertFromInputFieldRating", rating);
        // Assert
        assertEquals(Rating.SCORE.BAD, result);
    }

    @Test
    public void givenConvertFromInputFieldRatingWhenRatingGoodThenScoreGood() throws Exception {
        // Arrange
        InputFeedback.RATING rating = InputFeedback.RATING.GOOD;
        // Act
        Rating.SCORE result = invokeMethod(underTest, "convertFromInputFieldRating", rating);
        // Assert
        assertEquals(Rating.SCORE.GOOD, result);
    }

    @Test
    public void givenConvertFromInputFieldRatingWhenRatingNullThenException() throws Exception {
        // Arrange
        InputFeedback.RATING rating = null;
        // Assert
        expectedException.expect(Exception.class);
        // Act
        invokeMethod(underTest, "convert", rating);
    }

    @Test
    public void givenAddOrUpdateRatingWhenIdOfLastRatingZeroThenOnAddRating() throws Exception {
        // Arrange
        String feedback = "good";
        // Act
        invokeMethod(underTest, "addOrUpdateRating", offboardingHelperViewCallback, Rating.SCORE
                .GOOD, feedback);
        // Assert
        ArgumentCaptor<Rating.SCORE> argumentCaptor1 = ArgumentCaptor.forClass(Rating.SCORE.class);
        ArgumentCaptor<String> argumentCaptor2 = ArgumentCaptor.forClass(String.class);
        verify(offboardingHelperViewCallback).onAddRating(argumentCaptor1.capture(),
                argumentCaptor2.capture());
        assertEquals(Rating.SCORE.GOOD, argumentCaptor1.getValue());
        assertEquals(feedback, argumentCaptor2.getValue());
    }

    @Test
    public void givenAddOrUpdateRatingWhenFeedbackNullThenOnUpdateRating() throws Exception {
        // Arrange
        String feedback = null;
        AtomicLong mIdOfLastRatingAdded = new AtomicLong();
        mIdOfLastRatingAdded.set(42L);
        setInternalState(underTest, "mIdOfLastRatingAdded", mIdOfLastRatingAdded);
        // Act
        invokeMethod(underTest, "addOrUpdateRating", offboardingHelperViewCallback, Rating.SCORE
                .GOOD, feedback);
        // Assert
        ArgumentCaptor<Long> argumentCaptor1 = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Rating.SCORE> argumentCaptor2 = ArgumentCaptor.forClass(Rating.SCORE.class);
        verify(offboardingHelperViewCallback).onUpdateRating(argumentCaptor1.capture(),
                argumentCaptor2.capture());
        assertEquals(Long.valueOf(42L), argumentCaptor1.getValue());
        assertEquals(Rating.SCORE.GOOD, argumentCaptor2.getValue());
    }

    @Test
    public void givenAddOrUpdateRatingWhenFeedbackNotNullAndLastAddedIdNotZeroThenOnUpdateRating
            () throws Exception {
        // Arrange
        String feedback = "feedback";
        AtomicLong mIdOfLastRatingAdded = new AtomicLong();
        mIdOfLastRatingAdded.set(42L);
        setInternalState(underTest, "mIdOfLastRatingAdded", mIdOfLastRatingAdded);
        // Act
        invokeMethod(underTest, "addOrUpdateRating", offboardingHelperViewCallback, Rating.SCORE
                .GOOD, feedback);
        // Assert
        ArgumentCaptor<Long> argumentCaptor1 = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Rating.SCORE> argumentCaptor2 = ArgumentCaptor.forClass(Rating.SCORE.class);
        ArgumentCaptor<String> argumentCaptor3 = ArgumentCaptor.forClass(String.class);
        verify(offboardingHelperViewCallback).onUpdateFeedback(argumentCaptor1.capture(),
                argumentCaptor2.capture(), argumentCaptor3.capture());
        assertEquals(Long.valueOf(42L), argumentCaptor1.getValue());
        assertEquals(Rating.SCORE.GOOD, argumentCaptor2.getValue());
        assertEquals("feedback", argumentCaptor3.getValue());
    }

    @Test
    public void givenAddToQueue() throws Exception {
        // Arrange
        String feedback = "good";
        // Act
        invokeMethod(underTest, "addToQueue", offboardingHelperViewCallback, Rating.SCORE.GOOD,
                feedback);
        // Assert cannot be done due to private calls
    }

    @Test
    public void givenRemoveFromQueueThenException() throws Exception {
        // Arrange
        String feedback = "good";
        // Assert
        expectedException.expect(Exception.class);
        // Act
        invokeMethod(underTest, "removeFromQueue", offboardingHelperViewCallback, Rating.SCORE
                .GOOD, feedback);
    }

    private void staticMock() {
        mockStatic(
                OffboardingHelper.class,
                Kayako.class
        );
    }

    @After
    public void tearDown() {
        reset(underTest);
    }
}