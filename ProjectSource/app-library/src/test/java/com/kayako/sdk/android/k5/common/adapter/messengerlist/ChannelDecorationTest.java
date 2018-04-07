package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ChannelDecorationTest {

    private int sourceDrawable;
    private boolean isNote;
    private String name;

    ChannelDecoration channelDecoration;

    @Before
    public void setup() {
        sourceDrawable = 10;
        isNote = true;
        name = "test";
        channelDecoration = new ChannelDecoration(isNote, name);
    }

    @Test
    public void test_constructor1() {
        assertNotNull(channelDecoration);
        assertTrue(channelDecoration.isNote());
        assertEquals(name, channelDecoration.getName());
    }

    @Test
    public void test_constructor2() {
        ChannelDecoration channelDecoration1 = new ChannelDecoration(sourceDrawable);
        assertNotNull(channelDecoration1);
        assertEquals(sourceDrawable, channelDecoration1.getSourceDrawable());
    }

    @Test
    public void test_getName() {
        assertEquals(name, channelDecoration.getName());
    }

    @Test
    public void test_setName() {
        channelDecoration.setName("test2");
        assertNotEquals(name, channelDecoration.getName());
        assertEquals("test2", channelDecoration.getName());
    }

    @Test
    public void test_getSourceDrawable() {
        channelDecoration.setSourceDrawable(10);
        assertEquals(sourceDrawable, channelDecoration.getSourceDrawable());
    }

    @Test
    public void test_setSourceDrawable() {
        channelDecoration.setSourceDrawable(100);
        assertNotEquals(sourceDrawable, channelDecoration.getSourceDrawable());
        assertEquals(100, channelDecoration.getSourceDrawable());
    }

    @Test
    public void test_isNote() {
        assertTrue(channelDecoration.isNote());
    }

    @Test
    public void test_setNote() {
        channelDecoration.setNote(false);
        assertFalse(channelDecoration.isNote());
    }

    @Test
    public void test_getContents() {
        assertEquals(3, channelDecoration.getContents().size());
        assertEquals("true", channelDecoration.getContents().get("isNote"));
        assertEquals("0", channelDecoration.getContents().get("sourceDrawable"));
        assertEquals("test", channelDecoration.getContents().get("name"));
    }

}
