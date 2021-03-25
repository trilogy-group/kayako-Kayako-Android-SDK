package com.kayako.sdk.android.k5.messenger.data.realtime;

import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.core.MessengerUserPref;
import static junit.framework.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@PrepareForTest({
        MessengerPref.class,
        MessengerUserPref.class})
@RunWith(PowerMockRunner.class)
public class KreStarterFactoryTest {

    private MessengerPref messengerPref;
    private MessengerUserPref messengerUserPref;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        mockStatic(MessengerPref.class);
        mockStatic(MessengerUserPref.class);
        messengerPref = mock(MessengerPref.class);
        messengerUserPref = mock(MessengerUserPref.class);
        when(MessengerPref.getInstance()).thenReturn(messengerPref);
        when(MessengerUserPref.getInstance()).thenReturn(messengerUserPref);
    }

    @Test
    public void whenInvalidParamsThenException() {
        //Arrange
        final String exceptionMessage = "Invalid Arguments";
        when(messengerPref.getUrl()).thenReturn(null);

        //Assert
        thrown.expectMessage(exceptionMessage);
        thrown.expect(IllegalStateException.class);

        //Act
        KreStarterFactory.getKreStarterValues();
    }

    @Test
    public void whenValidParamsThenObjectCreated() {
        //Arrange
        when(messengerPref.getUrl()).thenReturn("/url");
        when(messengerPref.getRealtimeUrl()).thenReturn("/realTimeUrl");
        when(messengerPref.getFingerprintId()).thenReturn("123ABC");
        when(messengerUserPref.getUserId()).thenReturn(1L);

        //Act
        final KreStarter kreStarter = KreStarterFactory.getKreStarterValues();

        //Assert
        assertNotNull(kreStarter);
    }
}
