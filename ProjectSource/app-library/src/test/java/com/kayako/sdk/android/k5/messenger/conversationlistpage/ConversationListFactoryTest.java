package com.kayako.sdk.android.k5.messenger.conversationlistpage;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConversationListFactoryTest {

    @Mock
    private ConversationListContract.View view;

    @Test
    public void getPresenter() {
        //Act
        final ConversationListContract.Presenter presenterOne =
                ConversationListFactory.getPresenter(view);
        final ConversationListContract.Presenter presenterSecond =
                ConversationListFactory.getPresenter(view);

        //Assert
        assertEquals(presenterOne, presenterSecond);
    }
}
