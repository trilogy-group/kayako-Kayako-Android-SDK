package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

public class ChannelDecorationTest {

    private int sourceDrawable;
    private boolean isNote;
    private static final String NAME = "test";

    private ChannelDecoration channelDecoration;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setup() {
        sourceDrawable = 10;
        isNote = true;
        channelDecoration = new ChannelDecoration(isNote, NAME);
    }

    @Test
    public void test_constructor1() {
        errorCollector.checkThat(channelDecoration, notNullValue());
        errorCollector.checkThat(channelDecoration.isNote(), is(true));
        errorCollector.checkThat(channelDecoration.getName(), is(equalTo(NAME)));
    }

    @Test
    public void test_constructor2() {
        ChannelDecoration channelDecoration1 = new ChannelDecoration(sourceDrawable);
        errorCollector.checkThat(channelDecoration1, notNullValue());
        errorCollector.checkThat(sourceDrawable, is(equalTo(channelDecoration1.getSourceDrawable())));
    }

    @Test
    public void test_getName() {
        errorCollector.checkThat(NAME, is(equalTo(channelDecoration.getName())));
    }

    @Test
    public void test_setName() {
        channelDecoration.setName("test2");
        errorCollector.checkThat(channelDecoration.getName(), not(equalTo(NAME)));
        errorCollector.checkThat(channelDecoration.getName(), is(equalTo("test2")));
    }

    @Test
    public void test_getSourceDrawable() {
        channelDecoration.setSourceDrawable(10);
        errorCollector.checkThat(channelDecoration.getSourceDrawable(), is(sourceDrawable));
    }

    @Test
    public void test_setSourceDrawable() {
        channelDecoration.setSourceDrawable(100);
        errorCollector.checkThat(channelDecoration.getSourceDrawable(), not(equalTo(sourceDrawable)));
        errorCollector.checkThat(channelDecoration.getSourceDrawable(), is(equalTo(100)));
    }

    @Test
    public void test_isNote() {
        errorCollector.checkThat(channelDecoration.isNote(), is(true));
    }

    @Test
    public void test_setNote() {
        channelDecoration.setNote(false);
        errorCollector.checkThat(channelDecoration.isNote(), is(false));
    }

    @Test
    public void test_getContents() {
        errorCollector.checkThat(channelDecoration.getContents().size(), is(equalTo(3)));
        errorCollector.checkThat(channelDecoration.getContents().get("isNote"), is(equalTo("true")));
        errorCollector.checkThat(channelDecoration.getContents().get("sourceDrawable"), is(equalTo("0")));
        errorCollector.checkThat(channelDecoration.getContents().get("name"), is(equalTo("test")));
    }
}
