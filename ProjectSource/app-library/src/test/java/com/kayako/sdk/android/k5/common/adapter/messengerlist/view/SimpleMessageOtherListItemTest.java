package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.ChannelDecoration;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import java.util.HashMap;
import java.util.Map;

public class SimpleMessageOtherListItemTest {

    private Long id;
    private static final String MESSAGE = "test message";
    private static final String AVATARURL = "/avatarUrl";
    private long time;
    private ChannelDecoration channel;
    private Map<String, Object> data;
    private SimpleMessageOtherListItem otherListItem;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp(){
        id = 1L;
        time  =1234L;
        data = new HashMap<String, Object>();
        channel = new ChannelDecoration(1);
        otherListItem = new SimpleMessageOtherListItem(id, MESSAGE, AVATARURL, channel,time, data);
    }

    @Test
    public void test_constructor() {
        errorCollector.checkThat(otherListItem, notNullValue());
        errorCollector.checkThat(otherListItem.getItemType(), is(equalTo(MessengerListType.SIMPLE_MESSAGE_OTHER)));
        errorCollector.checkThat(otherListItem.getId(), is(id));
        errorCollector.checkThat(otherListItem.getMessage(), is(equalTo(MESSAGE)));
    }

    @Test
    public void test_getMessage() {
        errorCollector.checkThat(otherListItem.getMessage(), is(equalTo(MESSAGE)));
    }

    @Test
    public void test_setMessage() {
        otherListItem.setMessage("message_2");
        errorCollector.checkThat(otherListItem.getMessage(), is(equalTo("message_2")));
    }

    @Test
    public void test_getAvatarUrl(){
        errorCollector.checkThat(otherListItem.getAvatarUrl(), is(equalTo(AVATARURL)));
    }

    @Test
    public void test_setAvatarUrl(){
        otherListItem.setAvatarUrl("/avatarUrl_2");
        errorCollector.checkThat(otherListItem.getAvatarUrl(), is(equalTo("/avatarUrl_2")));
    }

    @Test
    public void test_getTime(){
        errorCollector.checkThat(otherListItem.getTime(), is(time));
    }

    @Test
    public void test_setTime(){
        otherListItem.setTime(54321L);
        errorCollector.checkThat(otherListItem.getTime(), is(54321L));
    }

    @Test
    public void test_getChannel() {
        errorCollector.checkThat(otherListItem.getChannel(), is(equalTo(channel)));
        errorCollector.checkThat(otherListItem.getChannel().getSourceDrawable(), is(1));
    }

    @Test
    public void test_setChannel() {
        ChannelDecoration channelDecoration = new ChannelDecoration(5);
        otherListItem.setChannel(channelDecoration);
        errorCollector.checkThat(otherListItem.getChannel(), is(not(channel)));
        errorCollector.checkThat(otherListItem.getChannel().getSourceDrawable(), is(5));
    }

    @Test
    public void test_getContents(){
        Map map = otherListItem.getContents();
        errorCollector.checkThat(map.size() > 0, is(true));
        errorCollector.checkThat(map.size(), is(6));
        errorCollector.checkThat(map.get("isNote").toString(), is(equalTo("false")));
    }
}
