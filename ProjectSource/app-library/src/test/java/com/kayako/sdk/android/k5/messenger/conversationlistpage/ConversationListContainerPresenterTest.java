package com.kayako.sdk.android.k5.messenger.conversationlistpage;

import com.kayako.sdk.android.k5.common.fragments.ListPageState;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

@RunWith(MockitoJUnitRunner.class)
public class ConversationListContainerPresenterTest {

    private ConversationListContainerPresenter listContainerPresenter;

    @Mock
    private ConversationListContainerContract.View view;

    @Captor
    private ArgumentCaptor<Integer> integerArgumentCaptor;

    @Before
     public void setUp() {
        listContainerPresenter = new ConversationListContainerPresenter(view);
    }

    @Test
    public void setView() {
        //Act
        listContainerPresenter.setView(view);
        final ConversationListContainerContract.View viewLocal =
                Whitebox.getInternalState(listContainerPresenter, "mView");

        //Assert
        assertEquals(view, viewLocal);
    }

    @Test
    public void onClickNewConversation() {
        //Act
        listContainerPresenter.onClickNewConversation();

        //Assert
        verify(view).openNewConversationPage(integerArgumentCaptor.capture());
        assertEquals(RequestCodes.REQUEST_CODE_CREATE_NEW_CONVERSATION,
                integerArgumentCaptor.getValue().intValue());
    }

    @Test
    public void onActivityResult() {
        //Arrange
        final int requestCode = RequestCodes.REQUEST_CODE_CREATE_NEW_CONVERSATION;
        final int resultCode = 1;

        //Act
        listContainerPresenter.onActivityResult(requestCode, resultCode);

        //Assert
        verify(view, times(1)).reloadConversations();
    }

    @Test
    public void onScrollConversationList() {
        //Arrange
        final boolean isScrolling = Boolean.TRUE;

        //Act
        listContainerPresenter.onScrollConversationList(isScrolling);

        //Assert
        final boolean mIsScrolling = Whitebox.getInternalState(listContainerPresenter, "mIsScrolling");
        verify(view, times(1)).hideNewConversationButton();
        assertEquals(isScrolling, mIsScrolling);
    }

    @Test
    public void onPageStateChangeWhenStateListAndScrolling() {
        //Arrange
        final ListPageState state = ListPageState.LIST;

        //Act
        listContainerPresenter.onScrollConversationList(true);
        listContainerPresenter.onPageStateChange(state);

        //Assert
        final ListPageState newState = Whitebox.getInternalState(listContainerPresenter, "mListPageState");
        verify(view, times(2)).hideNewConversationButton();
        assertEquals(state, newState);
    }

    @Test
    public void onPageStateChangeWhenStateListAndNotScrolling() {
        //Arrange
        final ListPageState state = ListPageState.LIST;

        //Act
        listContainerPresenter.onPageStateChange(state);

        //Assert
        final ListPageState newState = Whitebox.getInternalState(listContainerPresenter, "mListPageState");
        verify(view, times(1)).showNewConversationButton();
        assertEquals(state, newState);
    }

    @Test
    public void onPageStateChangeWhenStateEmpty() {
        //Arrange
        final ListPageState state = ListPageState.EMPTY;

        //Act
        listContainerPresenter.onPageStateChange(state);

        //Assert
        final ListPageState newState = Whitebox.getInternalState(listContainerPresenter, "mListPageState");
        verify(view, times(1)).showNewConversationButton();
        assertEquals(state, newState);
    }

    @Test
    public void onPageStateChangeWhenStateLoading() {
        //Arrange
        final ListPageState state = ListPageState.LOADING;

        //Act
        listContainerPresenter.onPageStateChange(state);

        //Assert
        final ListPageState newState = Whitebox.getInternalState(listContainerPresenter, "mListPageState");
        verify(view, times(1)).hideNewConversationButton();
        assertEquals(state, newState);
    }
}
