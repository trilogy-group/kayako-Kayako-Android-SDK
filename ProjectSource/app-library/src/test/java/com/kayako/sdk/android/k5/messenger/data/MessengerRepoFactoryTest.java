package com.kayako.sdk.android.k5.messenger.data;

import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.IConversationStarterRepository;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@PrepareForTest(MessengerPref.class)
@RunWith(PowerMockRunner.class)
public class MessengerRepoFactoryTest {

    private MessengerPref messengerPref;

    @Before
    public void setUp() {
        mockStatic(MessengerPref.class);
        messengerPref = Mockito.mock(MessengerPref.class);
        when(MessengerPref.getInstance()).thenReturn(messengerPref);
        when(messengerPref.getFingerprintId()).thenReturn("123ABC");
    }

    @Test
    public void getConversationStarterRepository() {
        //Act
        final IConversationStarterRepository iConversationStarterRepositoryOne =
                MessengerRepoFactory.getConversationStarterRepository();
        final IConversationStarterRepository iConversationStarterRepositorySecond =
                MessengerRepoFactory.getConversationStarterRepository();

        //Assert
        assertEquals(iConversationStarterRepositoryOne, iConversationStarterRepositorySecond);
    }

    @Test
    public void reset() {
        //Act
        MessengerRepoFactory.getConversationStarterRepository();
        MessengerRepoFactory.reset();
        final IConversationStarterRepository mConversationStarterData =
                Whitebox.getInternalState(MessengerRepoFactory.class, "mConversationStarterData");

        //Assert
        assertNull(mConversationStarterData);
    }
}
