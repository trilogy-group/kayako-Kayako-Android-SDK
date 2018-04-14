package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.Attachment;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.ChannelDecoration;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;
import java.util.HashMap;
import java.util.Map;

public class AttachmentMessageOtherListItemTest {

    private static final String AVATAR_URL = "/avatarUrl";
    private final Map<String, Object> data = new HashMap<>();
    private long time;
    private long id;
    private ChannelDecoration channel;
    private Attachment attachment;
    private AttachmentMessageOtherListItem item;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        id = 1L;
        time = 1_000L;
        attachment = new Attachment(Attachment.TYPE.URL);
        channel = new ChannelDecoration(1);
        item = new AttachmentMessageOtherListItem(id, AVATAR_URL, channel, attachment, time, data);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        errorCollector.checkThat(item.getId(), is(id));
        errorCollector.checkThat(item.getAvatarUrl(), is(AVATAR_URL));
        errorCollector.checkThat(item.getChannel(), is(channel));
        errorCollector.checkThat(item.getTime(), is(time));
        errorCollector.checkThat(item.getData(), is(data));
    }

    @Test
    public void setAttachment() {
        Attachment attachmentLocal = new Attachment(Attachment.TYPE.FILE);
        item.setAttachment(attachmentLocal);
        errorCollector.checkThat(item.getAttachment(), is(attachmentLocal));
        errorCollector.checkThat(item.getAttachment().getType(), is(Attachment.TYPE.FILE));
    }

    @Test
    public void setTime() {
        final long timeLocal = 2_000L;
        item.setTime(timeLocal);
        errorCollector.checkThat(item.getTime(), is(timeLocal));
    }

    @Test
    public void setAvatarUrl() {
        final String avatarUrlLocal = "/avatarUrlLocal";
        item.setAvatarUrl(avatarUrlLocal);
        errorCollector.checkThat(item.getAvatarUrl(), is(avatarUrlLocal));
    }

    @Test
    public void setChannel() {
        ChannelDecoration channelLocal = new ChannelDecoration(2);
        item.setChannel(channelLocal);
        errorCollector.checkThat(item.getChannel(), is(channelLocal));
        errorCollector.checkThat(item.getChannel().getSourceDrawable(), is(2));
    }

    @Test
    public void getContents() {
        errorCollector.checkThat(item.getContents().size(), is(6));
        errorCollector.checkThat(item.getContents().get("time"), is(String.valueOf(time)));
    }
}
