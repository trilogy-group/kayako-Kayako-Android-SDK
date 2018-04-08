package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.Attachment;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.ChannelDecoration;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import java.util.HashMap;
import java.util.Map;

public class AttachmentMessageOtherListItemTest {

    private static final String AVATARURL = "/avatarUrl";
    private long time;
    private ChannelDecoration channel;
    private Attachment attachment;
    private long id;
    private Map<String, Object> data;
    private AttachmentMessageOtherListItem item;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        id = 1L;
        data = new HashMap<String, Object>();
        time = 1000L;
        attachment = new Attachment(Attachment.TYPE.URL);
        channel = new ChannelDecoration(1);
        item = new AttachmentMessageOtherListItem(id, AVATARURL, channel, attachment, time, data);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        errorCollector.checkThat(item, notNullValue());
        errorCollector.checkThat(item.getTime(), is(time));
    }

    @Test
    public void getAttachment() {
        errorCollector.checkThat(item.getAttachment(), is(attachment));
        errorCollector.checkThat(item.getAttachment().getType(), is(Attachment.TYPE.URL));

    }

    @Test
    public void setAttachment() {
        Attachment attachment1 = new Attachment(Attachment.TYPE.FILE);
        item.setAttachment(attachment1);
        errorCollector.checkThat(item.getAttachment(), is(equalTo(attachment1)));
        errorCollector.checkThat(item.getAttachment().getType(), is(Attachment.TYPE.FILE));
    }

    @Test
    public void getTime(){
        errorCollector.checkThat(item.getTime(), is(time));
    }

    @Test
    public void setTime(){
        item.setTime(2000L);
        errorCollector.checkThat(item.getTime(), is(2000L));
    }

    @Test
    public void getAvatarUrl(){
        errorCollector.checkThat(item.getAvatarUrl(), is(AVATARURL));
    }

    @Test
    public void setAvatarUrl(){
        item.setAvatarUrl("/avatarUrl2");
        errorCollector.checkThat(item.getAvatarUrl(), is("/avatarUrl2"));
    }

    @Test
    public void getChannel() {
        errorCollector.checkThat(item.getChannel(), is(channel));
    }

    @Test
    public void setChannel() {
        ChannelDecoration channel1 = new ChannelDecoration(2);
        item.setChannel(channel1);
        errorCollector.checkThat(item.getChannel(), is(channel1));
        errorCollector.checkThat(item.getChannel().getSourceDrawable(), is(2));
    }

    @Test
    public void getContents() {
        errorCollector.checkThat(item.getContents().size() > 0, is(true));
        errorCollector.checkThat(item.getContents().size(), is(6));
        errorCollector.checkThat(item.getContents().get("time"), is(equalTo(String.valueOf(time))));
    }

}
