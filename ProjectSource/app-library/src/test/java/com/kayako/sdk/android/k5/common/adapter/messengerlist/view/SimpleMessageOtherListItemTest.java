package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.ChannelDecoration;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import java.util.HashMap;
import java.util.Map;

public class SimpleMessageOtherListItemTest {

    private Map<String, Object> data = new HashMap<>();
    private long id;
    private static final String MESSAGE = "test message";
    private static final String AVATAR_URL = "/avatarUrl";
    private long time;
    private ChannelDecoration channel;
    private SimpleMessageOtherListItem otherListItem;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp(){
        id = 1L;
        time = 1_234L;
        channel = new ChannelDecoration(1);
        otherListItem = new SimpleMessageOtherListItem(id, MESSAGE, AVATAR_URL, channel,time, data);
    }

    @Test
    public void whenValidParamsInConstructorThenObjectCreated() {
        errorCollector.checkThat(otherListItem.getItemType(), is(equalTo(MessengerListType.SIMPLE_MESSAGE_OTHER)));
        errorCollector.checkThat(otherListItem.getId(), is(id));
        errorCollector.checkThat(otherListItem.getAvatarUrl(), is(equalTo(AVATAR_URL)));
        errorCollector.checkThat(otherListItem.getMessage(), is(equalTo(MESSAGE)));
        errorCollector.checkThat(otherListItem.getChannel(), is(channel));
        errorCollector.checkThat(otherListItem.getTime(), is(time));
        errorCollector.checkThat(otherListItem.getData(), is(data));
    }

    @Test
    public void setMessage() {
        final String newMessage = "new_message";
        otherListItem.setMessage(newMessage);
        errorCollector.checkThat(otherListItem.getMessage(), is(equalTo(newMessage)));
    }

    @Test
    public void setAvatarUrl(){
        final String newAvatarUrl = "/avatarUrl_2";
        otherListItem.setAvatarUrl(newAvatarUrl);
        errorCollector.checkThat(otherListItem.getAvatarUrl(), is(equalTo(newAvatarUrl)));
    }

    @Test
    public void setTime(){
        final long newTime = 54_321L;
        otherListItem.setTime(newTime);
        errorCollector.checkThat(otherListItem.getTime(), is(newTime));
    }

    @Test
    public void setChannel() {
        final int sourceDrawable = 5;
        ChannelDecoration channelDecorationLocal = new ChannelDecoration(sourceDrawable);
        otherListItem.setChannel(channelDecorationLocal);
        errorCollector.checkThat(otherListItem.getChannel(), is(not(channel)));
        errorCollector.checkThat(otherListItem.getChannel(), is(channelDecorationLocal));
        errorCollector.checkThat(otherListItem.getChannel().getSourceDrawable(), is(sourceDrawable));
    }

    @Test
    public void getContents(){
        Map<String, String> contentsMap = otherListItem.getContents();
        errorCollector.checkThat(contentsMap.size(), is(6));
        errorCollector.checkThat(contentsMap.get("isNote"), is(equalTo("false")));
    }
}