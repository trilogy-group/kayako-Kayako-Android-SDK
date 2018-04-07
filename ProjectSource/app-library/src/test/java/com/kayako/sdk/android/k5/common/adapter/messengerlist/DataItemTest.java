package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataItemTest {

    private Long id;
    private Map<String, Object> data;
    private UserDecoration userDecoration;
    private static final String MESSAGE = "test message";
    private Long timeInMilliseconds;
    private ChannelDecoration channelDecoration;
    private DeliveryIndicator deliveryIndicator;
    private boolean isRead;
    private Attachment attachment;
    private List<Attachment> attachments;

    private DataItem dataItem;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setup(){
        id = 1L;
        data = new HashMap<>();
        data.put("test", "object");
        userDecoration = new UserDecoration(null, 1L, false);
        timeInMilliseconds = 1000L;
        channelDecoration = new ChannelDecoration(5);
        deliveryIndicator = new DeliveryIndicator(0,0,0L);
        isRead = true;
        attachment = new Attachment(Attachment.TYPE.URL);
        attachments = new ArrayList<Attachment>();
        attachments.add(attachment);
        dataItem = new DataItem(id,data,userDecoration,channelDecoration,
                deliveryIndicator,MESSAGE,timeInMilliseconds,attachments,isRead);
    }

    @Test
    public void test_constructor(){
        errorCollector.checkThat(dataItem, notNullValue());
        errorCollector.checkThat(dataItem, is(instanceOf(DataItem.class)));
        errorCollector.checkThat(dataItem.isRead(), is(true));
        errorCollector.checkThat(dataItem.getDeliveryIndicator(), is(equalTo(deliveryIndicator)));
    }

    @Test
    public void test_getId() {
        errorCollector.checkThat(dataItem.getId(), is(equalTo(id)));
    }

    @Test
    public void test_setId() {
        dataItem.setId(5L);
        errorCollector.checkThat(dataItem.getId(), is(equalTo(5L)));
    }

    @Test
    public void test_getData() {
        errorCollector.checkThat(dataItem.getData().size(), is(equalTo(1)));
        errorCollector.checkThat(dataItem.getData().get("test").toString(), is(equalTo("object")));
    }

    @Test
    public void test_setData() {
        data.put("test_1", "object_1");
        dataItem.setData(data);
        errorCollector.checkThat(dataItem.getData().size(), is(equalTo(2)));
        errorCollector.checkThat(dataItem.getData().get("test_1").toString(), is(equalTo("object_1")));
    }

    @Test
    public void test_getMessage() {
        errorCollector.checkThat(dataItem.getMessage(), is(equalTo(MESSAGE)));
    }

    @Test
    public void test_setMessage() {
        String message = "message to test setMessage()";
        dataItem.setMessage(message);
        errorCollector.checkThat(dataItem.getMessage(), is(equalTo(message)));
    }

    @Test
    public void test_getTimeInMilliseconds() {
        errorCollector.checkThat(dataItem.getTimeInMilliseconds(), is(equalTo(timeInMilliseconds)));
    }

    @Test
    public void test_getUserDecoration() {
        errorCollector.checkThat(dataItem.getUserDecoration(), notNullValue());
        errorCollector.checkThat(dataItem.getUserDecoration(), is(instanceOf(UserDecoration.class)));
        errorCollector.checkThat(dataItem.getUserDecoration(), is(equalTo(userDecoration)));
    }

    @Test
    public void test_getChannelDecoration() {
        errorCollector.checkThat(dataItem.getChannelDecoration(), notNullValue());
        errorCollector.checkThat(dataItem.getChannelDecoration(), is(instanceOf(ChannelDecoration.class)));
        errorCollector.checkThat(dataItem.getChannelDecoration(), is(equalTo(channelDecoration)));
    }

    @Test
    public void test_isRead() {
        errorCollector.checkThat(dataItem.isRead(), is(true));
    }

    @Test
    public void test_getAttachments() {
        List<Attachment> attachments = dataItem.getAttachments();
        errorCollector.checkThat(attachments.size(), is(equalTo(1)));
        errorCollector.checkThat(attachments.get(0), is(instanceOf(Attachment.class)));
        errorCollector.checkThat(attachments.get(0), is(equalTo(attachment)));
    }

    @Test
    public void test_getDeliveryIndicator() {
        errorCollector.checkThat(dataItem.getDeliveryIndicator(), notNullValue());
        errorCollector.checkThat(dataItem.getDeliveryIndicator(), is(instanceOf(DeliveryIndicator.class)));
        errorCollector.checkThat(dataItem.getDeliveryIndicator(), is(equalTo(deliveryIndicator)));
    }
}
