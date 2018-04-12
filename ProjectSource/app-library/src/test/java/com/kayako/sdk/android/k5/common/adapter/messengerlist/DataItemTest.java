package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataItemTest {

    private static final String MESSAGE = "test message";
    private final Map<String, Object> data = new HashMap<>();
    private long id;
    private long timeInMilliseconds;
    private boolean isRead;
    private ChannelDecoration channelDecoration;
    private DeliveryIndicator deliveryIndicator;
    private UserDecoration userDecoration;
    private Attachment attachment;
    private List<Attachment> attachments;
    private DataItem dataItem;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setup() {
        id = 1L;
        data.put("test", "object");
        userDecoration = new UserDecoration("/avatarUrl", id, false);
        timeInMilliseconds = 1_000L;
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
    public void whenValidParamsConstructorThenObjectCreated() {
        errorCollector.checkThat(dataItem.getId(), is(id));
        errorCollector.checkThat(dataItem.getData(), is(data));
        errorCollector.checkThat(dataItem.getUserDecoration(), is(userDecoration));
        errorCollector.checkThat(dataItem.getChannelDecoration(), is(channelDecoration));
        errorCollector.checkThat(dataItem.getDeliveryIndicator(), is(deliveryIndicator));
        errorCollector.checkThat(dataItem.getMessage(), is(MESSAGE));
        errorCollector.checkThat(dataItem.getTimeInMilliseconds(), is(timeInMilliseconds));
        errorCollector.checkThat(dataItem.getAttachments(), is(attachments));
        errorCollector.checkThat(dataItem.isRead(), is(isRead));
    }

    @Test
    public void setId() {
        //Arrange
        final long newId = 5L;

        //Act
        dataItem.setId(newId);

        //Assert
        errorCollector.checkThat(dataItem.getId(), is(newId));
    }

    @Test
    public void setData() {
        //Arrange
        final String key = "test_1";
        final String value = "object_1";
        data.put(key, value);

        //Act
        dataItem.setData(data);

        //Assert
        errorCollector.checkThat(dataItem.getData().size(), is(2));
        errorCollector.checkThat(dataItem.getData().get(key).toString(), is(value));
    }

    @Test
    public void setMessage() {
        //Arrange
        final String newMessage = "message to test setMessage()";

        //Act
        dataItem.setMessage(newMessage);

        //Assert
        errorCollector.checkThat(dataItem.getMessage(), is(newMessage));
    }
}
