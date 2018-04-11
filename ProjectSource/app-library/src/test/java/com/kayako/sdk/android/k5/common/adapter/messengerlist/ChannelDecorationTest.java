package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

public class ChannelDecorationTest {

    private static final String NAME = "test";
    private int sourceDrawable;
    private boolean isNote;
    private ChannelDecoration channelDecoration;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setup() {
        sourceDrawable = 10;
        isNote = true;
        channelDecoration = new ChannelDecoration(isNote, NAME);
    }

    @Test
    public void givenValidParamsThenObjectCreated() {
        errorCollector.checkThat(channelDecoration.isNote(), is(isNote));
        errorCollector.checkThat(channelDecoration.getName(), is(equalTo(NAME)));
    }

    @Test
    public void givenValidParametersThenObjectCreated() {
        ChannelDecoration channelDecorationLocal = new ChannelDecoration(sourceDrawable);
        errorCollector.checkThat(channelDecorationLocal.getSourceDrawable(), is(equalTo(sourceDrawable)));
    }

    @Test
    public void setName() {
        channelDecoration.setName("test2");
        errorCollector.checkThat(channelDecoration.getName(), not(equalTo(NAME)));
        errorCollector.checkThat(channelDecoration.getName(), is(equalTo("test2")));
    }

    @Test
    public void setSourceDrawable() {
        channelDecoration.setSourceDrawable(100);
        errorCollector.checkThat(channelDecoration.getSourceDrawable(), not(equalTo(sourceDrawable)));
        errorCollector.checkThat(channelDecoration.getSourceDrawable(), is(equalTo(100)));
    }

    @Test
    public void setNote() {
        channelDecoration.setNote(false);
        errorCollector.checkThat(channelDecoration.isNote(), is(false));
    }

    @Test
    public void getContents() {
        Map<String, String> contentsMap = channelDecoration.getContents();
        errorCollector.checkThat(contentsMap.size(), is(equalTo(3)));
        errorCollector.checkThat(contentsMap.get("isNote"), is(equalTo("true")));
        errorCollector.checkThat(contentsMap.get("sourceDrawable"), is(equalTo("0")));
        errorCollector.checkThat(contentsMap.get("name"), is(equalTo("test")));
    }
}
