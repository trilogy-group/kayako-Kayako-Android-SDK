package com.kayako.sdk.android.k5.messenger.replyboxview;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

@RunWith(MockitoJUnitRunner.class)
public class ReplyBoxPresenterTest {

    private ReplyBoxPresenter replyBoxPresenter;

    @Mock
    private ReplyBoxContract.View view;

    @Mock
    private ReplyBoxContract.ReplyBoxListener listener;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Before
    public void setUp() {
        replyBoxPresenter = new ReplyBoxPresenter(view);
    }

    @Test
    public void setView() {
        //Act
        replyBoxPresenter.setView(view);

        //Assert
        final ReplyBoxContract.View mView = Whitebox.getInternalState(replyBoxPresenter, "mView");
        Assert.assertEquals(view, mView);
    }

    @Test
    public void onReplyTyped() {
        //Arrange
        final String message = "";
        replyBoxPresenter.setReplyBoxListener(listener);

        //Act
        replyBoxPresenter.onReplyTyped(message);
        verify(listener).onTypeReply(stringArgumentCaptor.capture());

        //Assert
        Assert.assertEquals(message, stringArgumentCaptor.getValue());
    }

    @Test
    public void onClickEnter() {
        //Act
        replyBoxPresenter.setReplyBoxListener(listener);
        replyBoxPresenter.onClickEnter();

        //Assert
        verify(view, times(1)).disableSendButton();
    }

    @Test
    public void onClickSendWithMessageContent() {
        //Arrange
        final String text = "text";
        PowerMockito.when(view.getReplyBoxText()).thenReturn(text);

        //Act
        replyBoxPresenter.setReplyBoxListener(listener);
        replyBoxPresenter.onClickSend();

        //Assert
        verify(listener).onClickSend(stringArgumentCaptor.capture());
        Assert.assertEquals(text, stringArgumentCaptor.getValue());
    }

    @Test
    public void onClickAddAttachment() {
        //Act
        replyBoxPresenter.setReplyBoxListener(listener);
        replyBoxPresenter.onClickAddAttachment();

        //Assert
        verify(listener, times(1)).onClickAddAttachment();
    }
}
