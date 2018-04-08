package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;

public class UserDecorationTest {

    private static final String NAME = "name";
    private static final String AVATARURL = "/avatarUrl";
    private boolean isSelf;
    private long userId;
    private UserDecoration userDecoration;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setup() {
        userId = 1L;
        isSelf = true;
        userDecoration = new UserDecoration(NAME, AVATARURL, userId, isSelf);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        errorCollector.checkThat(userDecoration, notNullValue());
        errorCollector.checkThat(userDecoration.getName(), is(equalTo(NAME)));
        errorCollector.checkThat(userDecoration.getAvatarUrl(), is(equalTo(AVATARURL)));
        errorCollector.checkThat(userDecoration.getUserId().longValue(), is(not(2L)));
    }

    @Test
    public void whenValidParamsInConstructorThenObjectCreated() {
        UserDecoration userDecoration1 = new UserDecoration(AVATARURL, userId, isSelf);
        errorCollector.checkThat(userDecoration1, notNullValue());
        errorCollector.checkThat(userDecoration1.getAvatarUrl(), is(equalTo(AVATARURL)));
        errorCollector.checkThat(userDecoration1.getName(), nullValue());
        errorCollector.checkThat(userDecoration1.isSelf(), is(true));
    }

    @Test
    public void getName() {
        errorCollector.checkThat(userDecoration.getName(), is(equalTo(NAME)));
    }

    @Test
    public void setName() {
        userDecoration.setName("name_2");
        errorCollector.checkThat(userDecoration.getName(), is(not(NAME)));
        errorCollector.checkThat(userDecoration.getName(), is(equalTo("name_2")));
    }

    @Test
    public void getUserId() {
        errorCollector.checkThat(userDecoration.getUserId(), is(userId));
    }

    @Test
    public void setUserId() {
        userDecoration.setUserId(0L);
        errorCollector.checkThat(userDecoration.getUserId(), is(not(userId)));
        errorCollector.checkThat(userDecoration.getUserId().longValue(), is(0L));
    }

    @Test
    public void getAvatarUrl() {
        errorCollector.checkThat(userDecoration.getAvatarUrl(), is(equalTo(AVATARURL)));
    }

    @Test
    public void setAvatarUrl() {
        userDecoration.setAvatarUrl("/avatarUrl2");
        errorCollector.checkThat(userDecoration.getAvatarUrl(), is(not(AVATARURL)));
        errorCollector.checkThat(userDecoration.getAvatarUrl(), is(equalTo("/avatarUrl2")));
    }

    @Test
    public void isSelf() {
        errorCollector.checkThat(userDecoration.isSelf(), is(true));
    }

    @Test
    public void setSelf() {
        userDecoration.setSelf(false);
        errorCollector.checkThat(userDecoration.isSelf(), is(false));
    }

    @Test
    public void getContents() {
        Map map = userDecoration.getContents();
        errorCollector.checkThat(map.size(), is(4));
        errorCollector.checkThat(map.get("name").toString(), is(equalTo(NAME)));
        errorCollector.checkThat(map.get("userId").toString(), is(equalTo(String.valueOf(userId))));
        errorCollector.checkThat(map.get("avatarUrl").toString(), is(equalTo(AVATARURL)));
        errorCollector.checkThat(map.get("isSelf").toString(), is(equalTo("true")));
    }
}