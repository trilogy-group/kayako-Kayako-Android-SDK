package com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel;

import com.kayako.sdk.android.k5.EqualsAndHashTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;

public class ClientTypingActivityTest extends EqualsAndHashTest<ClientTypingActivity>{

    private static final boolean IS_TYPING = Boolean.TRUE;
    private UserViewModel user;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        final String avatar = "avatar";
        final String fullName = "full_name";
        final long lastActiveAt = 12_345L;
        user = new UserViewModel(avatar, fullName, lastActiveAt);
        one = new ClientTypingActivity(IS_TYPING, user);
        same = new ClientTypingActivity(IS_TYPING, user);
        secondSame = new ClientTypingActivity(IS_TYPING, user);
        other = new ClientTypingActivity(false, user);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        //Assert
        errorCollector.checkThat(one.isTyping(), is(IS_TYPING));
        errorCollector.checkThat(one.getUser(), is(user));
    }

    @Test
    public void whenIsTypingFalseThenObjectCreated() {
        //Act
        ClientTypingActivity clientTypingActivityLocal = new ClientTypingActivity(false);

        //Assert
        errorCollector.checkThat(clientTypingActivityLocal.isTyping(), is(false));
    }

    @Test
    public void whenIsTypingTrueThenIllegalArgumentException() {
        //Arrange
        final String exceptionMessage =
            "If isTyping is true, then user  must also be provided. Use the appropriate constructor";

        //Contract
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(containsString(exceptionMessage));

        //Act
        new ClientTypingActivity(IS_TYPING);
    }

    @Test
    public void whenUserIsNullThenIllegalArgumentException() {
        //Arrange
        final String exceptionMessage =
                "If isTyping is true, then user  must also be provided!";

        //Contract
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(containsString(exceptionMessage));

        //Act
        new ClientTypingActivity(IS_TYPING, null);
    }

    @Test
    public void getContents() {
        //Act
        Map<String, String > contentsMap = one.getContents();

        //Assert
        errorCollector.checkThat(contentsMap.get("isTyping"), is(String.valueOf(IS_TYPING)));
    }
}
