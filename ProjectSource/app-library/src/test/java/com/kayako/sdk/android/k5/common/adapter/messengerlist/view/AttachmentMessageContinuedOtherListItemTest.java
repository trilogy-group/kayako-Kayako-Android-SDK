package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.Attachment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class AttachmentMessageContinuedOtherListItemTest {

    private Attachment attachment;
    private Long id = 1L;
    private long time = 1000L;
    private Map<String, Object> data = new HashMap<String, Object>();
    private AttachmentMessageContinuedOtherListItem attachmentMessageContinuedOtherListItem;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        attachment = new Attachment(Attachment.TYPE.URL);
        attachmentMessageContinuedOtherListItem = new AttachmentMessageContinuedOtherListItem(id, attachment, time, data);
    }

    @Test
    public void test_constructor() {
        errorCollector.checkThat(attachmentMessageContinuedOtherListItem, notNullValue());
        errorCollector.checkThat(attachmentMessageContinuedOtherListItem.getId(), is(equalTo(id)));
    }

    @Test
    public void test_getAttachment() {
        errorCollector.checkThat(attachmentMessageContinuedOtherListItem.getAttachment(), is(equalTo(attachment)));
        errorCollector.checkThat(attachmentMessageContinuedOtherListItem.getAttachment().getType(), is(equalTo(Attachment.TYPE.URL)));

    }

    @Test
    public void test_setAttachment() {
        Attachment attachment1 = new Attachment(Attachment.TYPE.FILE);
        attachmentMessageContinuedOtherListItem.setAttachment(attachment1);
        errorCollector.checkThat(attachmentMessageContinuedOtherListItem.getAttachment(), is(equalTo(attachment1)));
        errorCollector.checkThat(attachmentMessageContinuedOtherListItem.getAttachment().getType(), is(equalTo(Attachment.TYPE.FILE)));
    }

    @Test
    public void test_getTime(){
        errorCollector.checkThat(attachmentMessageContinuedOtherListItem.getTime(), is(equalTo(time)));
    }

    @Test
    public void test_setTime(){
        attachmentMessageContinuedOtherListItem.setTime(2000L);
        errorCollector.checkThat(attachmentMessageContinuedOtherListItem.getTime(), is(equalTo(2000L)));
    }

    @Test
    public void test_getContents() {
        errorCollector.checkThat(attachmentMessageContinuedOtherListItem.getContents().size() > 0, is(true));
        errorCollector.checkThat(attachmentMessageContinuedOtherListItem.getContents().size(), is(2));
        errorCollector.checkThat(attachmentMessageContinuedOtherListItem.getContents().get("time"), is(equalTo(String.valueOf(time))));
    }
}
