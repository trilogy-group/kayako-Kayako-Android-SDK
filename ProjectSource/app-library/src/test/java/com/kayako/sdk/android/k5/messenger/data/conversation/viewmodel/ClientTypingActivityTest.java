package com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;

public class ClientTypingActivityTest {

    private static final boolean IS_TYPING = Boolean.TRUE;
    private UserViewModel user;
    private ClientTypingActivity one;
    private ClientTypingActivity same;
    private ClientTypingActivity secondSame;
    private ClientTypingActivity other;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

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
        errorCollector.checkThat(one.isTyping(), is(IS_TYPING));
        errorCollector.checkThat(one.getUser(), is(user));
    }

    @Test
    public void whenIsTypingFalseThenObjectCreated() {
        ClientTypingActivity clientTypingActivityLocal = new ClientTypingActivity(false);
        errorCollector.checkThat(clientTypingActivityLocal.isTyping(), is(false));
    }

    @Test
    public void whenIsTypingTrueThenIllegalArgumentException() {
        final String exceptionMessage =
            "If isTyping is true, then user  must also be provided. Use the appropriate constructor";
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(containsString(exceptionMessage));
        new ClientTypingActivity(IS_TYPING);
    }

    @Test
    public void whenUserIsNullThenIllegalArgumentException() {
        final String exceptionMessage =
                "If isTyping is true, then user  must also be provided!";
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(containsString(exceptionMessage));
        new ClientTypingActivity(IS_TYPING, null);
    }

    @Test
    public void reflexivity() {
        errorCollector.checkThat(one, is(one));
    }

    @Test
    public void nullInequality() {
        errorCollector.checkThat(one.equals(null), is(false));
    }

    @Test
    public void symmetry() {
        errorCollector.checkThat(one, is(same));
        errorCollector.checkThat(same, is(one));
        errorCollector.checkThat(one.hashCode(), is(same.hashCode()));
        errorCollector.checkThat(one.equals(other), is(false));
        errorCollector.checkThat(other.equals(one), is(false));
    }

    @Test
    public void transitivity() {
        errorCollector.checkThat(one, is(same));
        errorCollector.checkThat(same, is(secondSame));
        errorCollector.checkThat(one, is(secondSame));
        errorCollector.checkThat(one.hashCode(), is(same.hashCode()));
        errorCollector.checkThat(one.hashCode(), is(secondSame.hashCode()));
    }

    @Test
    public void getContents() {
        Map<String, String > contentsMap = one.getContents();
        errorCollector.checkThat(contentsMap.get("isTyping"), is(String.valueOf(IS_TYPING)));
    }
}
