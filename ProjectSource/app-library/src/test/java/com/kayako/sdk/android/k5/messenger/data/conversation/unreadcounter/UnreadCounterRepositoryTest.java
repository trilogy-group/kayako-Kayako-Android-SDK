package com.kayako.sdk.android.k5.messenger.data.conversation.unreadcounter;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UnreadCounterRepositoryTest {

    @Mock
    private OnUnreadCountChangeListener onUnreadCountChangeListener;

    @Captor
    private ArgumentCaptor<Integer> integerArgumentCaptor;

    @Test
    public void removeListener() {
        //Act
        UnreadCounterRepository.addListener(onUnreadCountChangeListener);
        UnreadCounterRepository.removeListener(onUnreadCountChangeListener);

        //Assert
        final List<OnUnreadCountChangeListener> sListeners =
                Whitebox.getInternalState(UnreadCounterRepository.class, "sListeners");
        assertTrue(sListeners.isEmpty());
    }

    @Test
    public void clear() {
        //Act
        UnreadCounterRepository.addListener(onUnreadCountChangeListener);
        UnreadCounterRepository.setCurrentConversationBeingViewed(1L);
        UnreadCounterRepository.clear();

        //Assert
        final List<OnUnreadCountChangeListener> sListeners =
                Whitebox.getInternalState(UnreadCounterRepository.class, "sListeners");
        assertTrue(sListeners.isEmpty());
    }

    @Test
    public void callListeners() {
        //Arrange
        final int unreadCount = 1;
        UnreadCounterRepository.addListener(onUnreadCountChangeListener);
        UnreadCounterRepository.callListeners(unreadCount);

        //Assert
        verify(onUnreadCountChangeListener).onUnreadCountChanged(integerArgumentCaptor.capture());
        assertEquals(unreadCount, integerArgumentCaptor.getValue().intValue());
    }
}
