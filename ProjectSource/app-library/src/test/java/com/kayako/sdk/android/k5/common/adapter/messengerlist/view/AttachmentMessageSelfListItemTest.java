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

public class AttachmentMessageSelfListItemTest {

    private long time;
    private Attachment attachment;
    private DeliveryIndicator deliveryIndicator;
    private boolean fadeBackground;
    private AttachmentMessageSelfListItem listItem;
    private Long id;
    private Map<String, Object> data;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        id = 1L;
        data = new HashMap<String, Object>();
        time = 1000L;
        attachment = new Attachment(Attachment.TYPE.URL);
        deliveryIndicator = new DeliveryIndicator(1, 1, 2000L);
        fadeBackground = false;
        listItem = new AttachmentMessageSelfListItem(id, deliveryIndicator, fadeBackground,attachment,time,data);
    }

    @Test
    public void test_constructor() {
        errorCollector.checkThat(listItem, notNullValue());
        errorCollector.checkThat(listItem.getTime(), is(equalTo(time)));
        errorCollector.checkThat(listItem.getId(), is(id));
    }

    @Test
    public void test_getAttachment() {
        errorCollector.checkThat(listItem.getAttachment(), is(attachment));
        errorCollector.checkThat(listItem.getAttachment().getType(), is(Attachment.TYPE.URL));

    }

    @Test
    public void test_setAttachment() {
        Attachment attachment1 = new Attachment(Attachment.TYPE.FILE);
        listItem.setAttachment(attachment1);
        errorCollector.checkThat(listItem.getAttachment(), is(attachment1));
        errorCollector.checkThat(listItem.getAttachment().getType(), is(Attachment.TYPE.FILE));
    }

    @Test
    public void test_getTime(){
        errorCollector.checkThat(listItem.getTime(), is(time));
    }

    @Test
    public void test_setTime(){
        listItem.setTime(2000L);
        errorCollector.checkThat(listItem.getTime(), is(2000L));
    }

    @Test
    public void test_getDeliveryIndicator() {
        errorCollector.checkThat(listItem.getDeliveryIndicator(), is(deliveryIndicator));
    }

    @Test
    public void test_isFadeBackground() {
        errorCollector.checkThat(listItem.isFadeBackground(), is(false));
    }

    @Test
    public void test_getContents() {
        errorCollector.checkThat(listItem.getContents().size() > 0, is(true));
        errorCollector.checkThat(listItem.getContents().size(), is(6));
        errorCollector.checkThat(listItem.getContents().get("fadeBackground"), is(equalTo(String.valueOf(fadeBackground))));
    }
}
