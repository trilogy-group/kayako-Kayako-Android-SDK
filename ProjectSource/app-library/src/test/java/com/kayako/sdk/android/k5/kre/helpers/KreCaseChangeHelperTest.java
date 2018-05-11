package com.kayako.sdk.android.k5.kre.helpers;

import com.kayako.sdk.android.k5.kre.base.KreSubscription;
import com.kayako.sdk.android.k5.kre.data.Change;
import static junit.framework.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class KreCaseChangeHelperTest {

    private static final String EVENT_CHANGE = "CHANGE";
    private static final String MESSAGE = "message";

    @Mock
    private KreSubscription kreSubscription;

    @Mock
    private RawCaseChangeListener rawCaseChangeListener;

    @Captor
    private ArgumentCaptor<Change> changeArgumentCaptor;

    @Test
    public void addRawCaseChangeListener() {
        //Arrange
        final String event = "testEvent";
        final String jsonBody = "{\n" +
                "\t\"resource_url\": \"https://kayako-mobile-testing.kayako.com/api/v1/cases/posts/2191\",\n" +
                "\t\"resource_type\": \"post\",\n" +
                "\t\"resource_id\": 2191,\n" +
                "\t\"customer_resource_url\": \"https://kayako-mobile-testing.kayako.com/api/v1/conversations/258/message/2191\",\n" +
                "\t\"changed_properties\": [],\n" +
                "\t\"agent_resource_url\": \"https://kayako-mobile-testing.kayako.com/api/v1/cases/posts/2191\"\n" +
                "}";
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                KreSubscription.OnEventListener onEventListener =
                        (KreSubscription.OnEventListener)arguments[1];
                onEventListener.onEvent(event, jsonBody);
                onEventListener.onError(MESSAGE);
                return null;
            }
        }).when(kreSubscription).listenFor(eq(EVENT_CHANGE), any(KreSubscription.OnEventListener.class));

        //Act
        KreCaseChangeHelper.addRawCaseChangeListener(kreSubscription, rawCaseChangeListener);

        //Assert
        verify(rawCaseChangeListener).onCaseChange(changeArgumentCaptor.capture());
        assertNull(changeArgumentCaptor.getValue());
        verify(rawCaseChangeListener, times(1)).onConnectionError();
    }
}
