package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class UserDecorationTest {

    private String name;
    private Long userId;
    private String avatarUrl;
    private boolean isSelf;
    private UserDecoration userDecoration;

    @Before
    public void setup() {
        name = "name";
        userId = 1L;
        avatarUrl = "/avatarUrl";
        isSelf = true;

        userDecoration = new UserDecoration(name, avatarUrl, userId, isSelf);
    }

    @Test
    public void test_constructor1() {
        assertNotNull(userDecoration);
        assertEquals(name, userDecoration.getName());
        assertEquals(avatarUrl, userDecoration.getAvatarUrl());
        assertNotEquals(2L, userDecoration.getUserId().longValue());
    }

    @Test
    public void test_constructor2() {
        UserDecoration userDecoration1 = new UserDecoration(avatarUrl, userId, isSelf);
        assertNotNull(userDecoration1);
        assertEquals(avatarUrl, userDecoration1.getAvatarUrl());
        assertNull(userDecoration1.getName());
        assertTrue(userDecoration1.isSelf());
    }

    @Test
    public void test_getName() {
        assertEquals(name, userDecoration.getName());
    }

    @Test
    public void test_setName() {
        userDecoration.setName("name_2");
        assertNotEquals(name, userDecoration.getName());
        assertEquals("name_2", userDecoration.getName());
    }

    @Test
    public void test_getUserId() {
        assertEquals(userId, userDecoration.getUserId());
    }

    @Test
    public void test_setUserId() {
        userDecoration.setUserId(0L);
        assertNotEquals(userId, userDecoration.getUserId());
        assertEquals(0L, userDecoration.getUserId().longValue());
    }

    @Test
    public void test_getAvatarUrl() {
        assertEquals(avatarUrl, userDecoration.getAvatarUrl());
    }

    @Test
    public void test_setAvatarUrl() {
        userDecoration.setAvatarUrl("/avatarUrl2");
        assertNotEquals(avatarUrl, userDecoration.getAvatarUrl());
        assertEquals("/avatarUrl2", userDecoration.getAvatarUrl());
    }

    @Test
    public void test_isSelf() {
        assertTrue(userDecoration.isSelf());
    }

    @Test
    public void test_setSelf() {
        userDecoration.setSelf(false);
        assertFalse(userDecoration.isSelf());
    }

    @Test
    public void test_getContents() {
        Map map = userDecoration.getContents();
        assertEquals(4, map.size());
        assertEquals(name, map.get("name"));
        assertEquals(userId.toString(), map.get("userId"));
        assertEquals(avatarUrl, map.get("avatarUrl"));
        assertEquals("true", map.get("isSelf"));
    }
}