package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
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
        errorCollector.checkThat(userDecoration.getName(), is(NAME));
        errorCollector.checkThat(userDecoration.getAvatarUrl(), is(AVATAR_URL));
        errorCollector.checkThat(userDecoration.getUserId(), is(userId));
        errorCollector.checkThat(userDecoration.isSelf(), is(isSelf));
    }

    @Test
    public void whenValidParamsInConstructorThenObjectCreated() {
        //Act
        final UserDecoration userDecorationLocal = new UserDecoration(AVATAR_URL, userId, isSelf);

        //Assert
        errorCollector.checkThat(userDecorationLocal.getAvatarUrl(), is(AVATAR_URL));
        errorCollector.checkThat(userDecoration.getUserId(), is(userId));
        errorCollector.checkThat(userDecorationLocal.isSelf(), is(true));
        errorCollector.checkThat(userDecorationLocal.getName(), nullValue());
    }

    @Test
    public void setName() {
        //Arrange
        final String nameLocal = "name_local";

        //Act
        userDecoration.setName(nameLocal);

        //Assert
        errorCollector.checkThat(userDecoration.getName(), is(not(NAME)));
        errorCollector.checkThat(userDecoration.getName(), is(nameLocal));
    }

    @Test
    public void setUserId() {
        //Arrange
        final long newUserId = 0L;

        //Act
        userDecoration.setUserId(newUserId);

        //Assert
        errorCollector.checkThat(userDecoration.getUserId(), is(not(userId)));
        errorCollector.checkThat(userDecoration.getUserId(), is(newUserId));
    }

    @Test
    public void setAvatarUrl() {
        //Arrange
        final String avatarUrlLocal = "/avatarUrlLocal";

        //Act
        userDecoration.setAvatarUrl(avatarUrlLocal);

        //Assert
        errorCollector.checkThat(userDecoration.getAvatarUrl(), is(not(AVATAR_URL)));
        errorCollector.checkThat(userDecoration.getAvatarUrl(), is(avatarUrlLocal));
    }

    @Test
    public void setSelf() {
        //Act
        userDecoration.setSelf(false);

        //Assert
        errorCollector.checkThat(userDecoration.isSelf(), is(false));
    }

    @Test
    public void getContents() {
        final Map<String, String> contentsMap = userDecoration.getContents();
        errorCollector.checkThat(contentsMap.size(), is(4));
        errorCollector.checkThat(contentsMap.get("name"), is(NAME));
        errorCollector.checkThat(contentsMap.get("userId"), is(String.valueOf(userId)));
        errorCollector.checkThat(contentsMap.get("avatarUrl"), is(AVATAR_URL));
        errorCollector.checkThat(contentsMap.get("isSelf"), is(String.valueOf(isSelf)));
    }
}
