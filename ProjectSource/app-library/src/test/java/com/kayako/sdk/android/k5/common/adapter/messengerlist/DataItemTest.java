package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataItemTest {

    Long id;
    Map<String, Object> data;
    UserDecoration userDecoration;
    String message;
    Long timeInMilliseconds;
    ChannelDecoration channelDecoration;
    DeliveryIndicator deliveryIndicator;
    boolean isRead;
    Attachment attachment;
    List<Attachment> attachments;


    DataItem dataItem;

    @Before
    public void setup(){
        id = 1L;
        data = new HashMap<>();
        data.put("test", "object");
        userDecoration = new UserDecoration(null, 1L, false);
        message = "test message";
        timeInMilliseconds = 1000L;
        channelDecoration = new ChannelDecoration(5);
        deliveryIndicator = new DeliveryIndicator(0,0,0L);
        isRead = true;
        attachment = new Attachment(Attachment.TYPE.URL);
        attachments = new ArrayList<Attachment>();
        attachments.add(attachment);
        dataItem = new DataItem(id,data,userDecoration,channelDecoration,
                deliveryIndicator,message,timeInMilliseconds,attachments,isRead);
    }

    @Test
    public void test_constructor(){
        assertNotNull(dataItem);
        assertTrue(dataItem instanceof DataItem);
        assertTrue(dataItem.isRead());
        assertEquals(deliveryIndicator, dataItem.getDeliveryIndicator());
    }

    @Test
    public void test_getId() {
        assertEquals(id, dataItem.getId());
    }

    @Test
    public void test_setId() {
        dataItem.setId(5L);
        assertEquals(5L, dataItem.getId().longValue());
    }

    @Test
    public void test_getData() {
        assertEquals(1, dataItem.getData().size());
        assertEquals("object", dataItem.getData().get("test"));
    }

    @Test
    public void test_setData() {
        data.put("test_1", "object_1");
        dataItem.setData(data);
        assertEquals(2, dataItem.getData().size());
        assertEquals("object_1", dataItem.getData().get("test_1"));
    }

    @Test
    public void test_getMessage() {
        assertEquals(message, dataItem.getMessage());
    }

    @Test
    public void test_setMessage() {
        String message = "message to test setMessage()";
        dataItem.setMessage(message);
        assertEquals(message,dataItem.getMessage());
    }

    @Test
    public void test_getTimeInMilliseconds() {
        assertEquals(timeInMilliseconds, dataItem.getTimeInMilliseconds());
    }

    @Test
    public void test_getUserDecoration() {
        assertNotNull(dataItem.getUserDecoration());
        assertTrue(dataItem.getUserDecoration() instanceof UserDecoration);
        assertEquals(userDecoration, dataItem.getUserDecoration());
    }

    @Test
    public void test_getChannelDecoration() {
        assertNotNull(dataItem.getChannelDecoration());
        assertTrue(dataItem.getChannelDecoration() instanceof ChannelDecoration);
        assertEquals(channelDecoration, dataItem.getChannelDecoration());
    }

    @Test
    public void test_isRead() {
        assertTrue(dataItem.isRead());
    }

    @Test
    public void test_getAttachments() {
        List<Attachment> attachments = dataItem.getAttachments();
        assertEquals(1, attachments.size());
        assertTrue(attachments.get(0) instanceof Attachment);
        assertEquals(attachment, attachments.get(0));
    }

    @Test
    public void test_getDeliveryIndicator() {
        assertNotNull(dataItem.getDeliveryIndicator());
        assertTrue(dataItem.getDeliveryIndicator() instanceof DeliveryIndicator);
        assertEquals(deliveryIndicator, dataItem.getDeliveryIndicator());
    }
}
