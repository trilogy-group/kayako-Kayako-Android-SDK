package com.kayako.sdk.android.k5.messenger.homescreenpage;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
public class HomeScreenContainerPresenterTest {

    private HomeScreenContainerPresenter homeScreenContainerPresenter;

    @Mock
    private HomeScreenContainerContract.View view;

    @Before
    public void setUp() {
        homeScreenContainerPresenter = new HomeScreenContainerPresenter(view);
    }

    @Test
    public void OperationOnConversationButton() {
        //Act
        homeScreenContainerPresenter.initPage();
        homeScreenContainerPresenter.onClickNewConversationButton();
        final HomeScreenContainerContract.View viewLocal =
                Whitebox.getInternalState(homeScreenContainerPresenter, "mView");

        //Assert
        Assert.assertEquals(view, viewLocal);
        verify(view).showNewConversationButton();
        verify(view).openNewConversationPage();
    }

    @Test
    public void onScrollListWhenScrolling() {
        //Act
        homeScreenContainerPresenter.onScrollList(true);

        //Assert
        verify(view).hideNewConversationButton();
    }


    @Test
    public void onScrollListWhenNotScrolling() {
        //Act
        homeScreenContainerPresenter.onScrollList(false);

        //Assert
        verify(view).showNewConversationButton();
    }
}
