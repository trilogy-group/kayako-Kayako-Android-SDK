package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.Attachment;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.DeliveryIndicator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;

public class AttachmentMessageContinuedSelfListItemTest {

    private long time;
    private Attachment attachment;
    private DeliveryIndicator deliveryIndicator;
    private boolean fadeBackground;
    private long id;
    private Map<String, Object> data;
    private AttachmentMessageContinuedSelfListItem listItem;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        id = 1L;
        data = new HashMap<String, Object>();
        time = 1000L;
        attachment = new Attachment(Attachment.TYPE.URL);
        deliveryIndicator = new DeliveryIndicator(1, 1, 2000L);
        fadeBackground = false;
        listItem = new AttachmentMessageContinuedSelfListItem(id, attachment, time, deliveryIndicator, fadeBackground,data);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        errorCollector.checkThat(listItem, notNullValue());
        errorCollector.checkThat(listItem.getTime(), is(equalTo(time)));
        errorCollector.checkThat(listItem.getId(), is(id));
    }

    @Test
    public void getAttachment() {
        errorCollector.checkThat(listItem.getAttachment(), is(attachment));
        errorCollector.checkThat(listItem.getAttachment().getType(), is(Attachment.TYPE.URL));

    }

    @Test
    public void setAttachment() {
        Attachment attachment1 = new Attachment(Attachment.TYPE.FILE);
        listItem.setAttachment(attachment1);
        errorCollector.checkThat(listItem.getAttachment(), is(equalTo(attachment1)));
        errorCollector.checkThat(listItem.getAttachment().getType(), is(equalTo(Attachment.TYPE.FILE)));
    }

    @Test
    public void getTime() {
        errorCollector.checkThat(listItem.getTime(), is(equalTo(time)));
    }

    @Test
    public void getDeliveryIndicator() {
        errorCollector.checkThat(listItem.getDeliveryIndicator(), is(deliveryIndicator));
    }

    @Test
    public void isFadeBackground() {
        errorCollector.checkThat(listItem.isFadeBackground(), is(false));
    }

    @Test
    public void getContents() {
        errorCollector.checkThat(listItem.getContents().size() > 0, is(true));
        errorCollector.checkThat(listItem.getContents().size(), is(6));
        errorCollector.checkThat(listItem.getContents().get("fadeBackground"), is(equalTo(String.valueOf(fadeBackground))));
    }
}
