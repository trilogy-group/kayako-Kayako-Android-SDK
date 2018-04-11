package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;

public class UserDecorationTest {

    private static final String NAME = "name";
    private static final String AVATAR_URL = "/avatarUrl";
    private boolean isSelf;
    private long userId;
    private UserDecoration userDecoration;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setup() {
        userId = 1L;
        isSelf = true;
        userDecoration = new UserDecoration(NAME, AVATAR_URL, userId, isSelf);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        errorCollector.checkThat(userDecoration.getName(), is(equalTo(NAME)));
        errorCollector.checkThat(userDecoration.getAvatarUrl(), is(equalTo(AVATAR_URL)));
        errorCollector.checkThat(userDecoration.getUserId(), is(userId));
        errorCollector.checkThat(userDecoration.isSelf(), is(isSelf));
    }

    @Test
    public void whenValidParamsInConstructorThenObjectCreated() {
        UserDecoration userDecorationLocal = new UserDecoration(AVATAR_URL, userId, isSelf);
        errorCollector.checkThat(userDecorationLocal.getAvatarUrl(), is(equalTo(AVATAR_URL)));
        errorCollector.checkThat(userDecoration.getUserId(), is(userId));
        errorCollector.checkThat(userDecorationLocal.isSelf(), is(true));
        errorCollector.checkThat(userDecorationLocal.getName(), nullValue());
    }

    @Test
    public void setName() {
        String nameLocal = "name_local";
        userDecoration.setName(nameLocal);
        errorCollector.checkThat(userDecoration.getName(), is(not(NAME)));
        errorCollector.checkThat(userDecoration.getName(), is(equalTo(nameLocal)));
    }

    @Test
    public void setUserId() {
        userDecoration.setUserId(0L);
        errorCollector.checkThat(userDecoration.getUserId(), is(not(userId)));
        errorCollector.checkThat(userDecoration.getUserId(), is(0L));
    }

    @Test
    public void setAvatarUrl() {
        String avatarUrlLocal = "/avatarUrlLocal";
        userDecoration.setAvatarUrl(avatarUrlLocal);
        errorCollector.checkThat(userDecoration.getAvatarUrl(), is(not(AVATAR_URL)));
        errorCollector.checkThat(userDecoration.getAvatarUrl(), is(equalTo(avatarUrlLocal)));
    }

    @Test
    public void setSelf() {
        userDecoration.setSelf(false);
        errorCollector.checkThat(userDecoration.isSelf(), is(false));
    }

    @Test
    public void getContents() {
        Map<String, String> contentsMap = userDecoration.getContents();
        errorCollector.checkThat(contentsMap.size(), is(4));
        errorCollector.checkThat(contentsMap.get("name"), is(equalTo(NAME)));
        errorCollector.checkThat(contentsMap.get("userId"), is(equalTo(String.valueOf(userId))));
        errorCollector.checkThat(contentsMap.get("avatarUrl"), is(equalTo(AVATAR_URL)));
        errorCollector.checkThat(contentsMap.get("isSelf"), is(equalTo(String.valueOf(isSelf))));
    }
}