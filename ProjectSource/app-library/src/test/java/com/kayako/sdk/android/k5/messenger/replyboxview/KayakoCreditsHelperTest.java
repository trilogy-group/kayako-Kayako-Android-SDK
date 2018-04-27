package com.kayako.sdk.android.k5.messenger.replyboxview;

import android.content.Context;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.core.MessengerPref;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@PrepareForTest({
        Kayako.class,
        MessengerPref.class})
@RunWith(PowerMockRunner.class)
public class KayakoCreditsHelperTest {

    private MessengerPref messengerPref;
    @Mock
    private Context context;

    @Rule
    private ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        mockStatic(Kayako.class);
        mockStatic(Kayako.class);
        mockStatic(MessengerPref.class);
        messengerPref = mock(MessengerPref.class);
        when(Kayako.getApplicationContext()).thenReturn(context);
    }

    @Test
    public void getPoweredByMessageWhenRandomPickZero() {
        //Arrange
        final String message = "Messenger by ";
        Whitebox.setInternalState(KayakoCreditsHelper.class, "randomPick", 0);
        when(context.getString(R.string.ko__messenger_credits_messenger_by)).thenReturn(message);

        //Act & Assert
        assertEquals(message, KayakoCreditsHelper.getPoweredByMessage());
    }

    @Test
    public void exceptionWhenRandomPickOutofRange() {
        //Arrange
        final String exceptionMessage = "Unhandled case";
        Whitebox.setInternalState(KayakoCreditsHelper.class, "randomPick", 2);

        //Assert
        thrown.expectMessage(exceptionMessage);
        thrown.expect(IllegalStateException.class);

        //Act
        KayakoCreditsHelper.getPoweredByMessage();
    }

    @Test
    public void getLink() {
        //Arrange
        final String link = new StringBuilder(
                "https://www.kayako.com/?utm_source=url&utm_medium=messenger-android&")
        .append("utm_content=Live chat by &utm_campaign=product_links").toString();
        final String message = "Live chat by ";
        when(MessengerPref.getInstance()).thenReturn(messengerPref);
        when(messengerPref.getUrl()).thenReturn("url");
        Whitebox.setInternalState(KayakoCreditsHelper.class, "randomPick", 1);
        when(context.getString(R.string.ko__messenger_credits_live_chat_by)).thenReturn(message);

        //Act & Assert
        assertEquals(link, KayakoCreditsHelper.getLink());
    }
}
