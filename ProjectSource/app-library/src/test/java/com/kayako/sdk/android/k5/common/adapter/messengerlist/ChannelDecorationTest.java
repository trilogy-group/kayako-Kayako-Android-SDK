package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
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
        errorCollector.checkThat(channelDecoration.getName(), is(NAME));
    }

    @Test
    public void givenValidParametersThenObjectCreated() {
        ChannelDecoration channelDecorationLocal = new ChannelDecoration(sourceDrawable);
        errorCollector.checkThat(channelDecorationLocal.getSourceDrawable(), is(sourceDrawable));
    }

    @Test
    public void setName() {
        //Arrange
        final String newName = "Test Name";

        //Act
        channelDecoration.setName(newName);

        //Assert
        errorCollector.checkThat(channelDecoration.getName(), not(NAME));
        errorCollector.checkThat(channelDecoration.getName(), is(newName));
    }

    @Test
    public void setSourceDrawable() {
        //Arrange
        final int newSourceDrawable = 100;

        //Act
        channelDecoration.setSourceDrawable(newSourceDrawable);

        //Assert
        errorCollector.checkThat(channelDecoration.getSourceDrawable(), not(sourceDrawable));
        errorCollector.checkThat(channelDecoration.getSourceDrawable(), is(newSourceDrawable));
    }

    @Test
    public void setNote() {
        //Act
        channelDecoration.setNote(false);

        //Assert
        errorCollector.checkThat(channelDecoration.isNote(), is(false));
    }

    @Test
    public void getContents() {
        final Map<String, String> contentsMap = channelDecoration.getContents();
        errorCollector.checkThat(contentsMap.size(), is(3));
        errorCollector.checkThat(contentsMap.get("isNote"), is("true"));
        errorCollector.checkThat(contentsMap.get("sourceDrawable"), is("0"));
        errorCollector.checkThat(contentsMap.get("name"), is("test"));
    }
}
