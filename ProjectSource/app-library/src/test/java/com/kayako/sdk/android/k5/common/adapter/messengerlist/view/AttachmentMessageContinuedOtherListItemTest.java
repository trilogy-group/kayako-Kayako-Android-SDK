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

public class AttachmentMessageContinuedOtherListItemTest {

    private final long id = 1L;
    private final long time = 1_000L;
    private final Map<String, Object> data = new HashMap<>();
    private Attachment attachment;
    private AttachmentMessageContinuedOtherListItem otherListItem;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        attachment = new Attachment(Attachment.TYPE.URL);
        otherListItem = new AttachmentMessageContinuedOtherListItem(id, attachment, time, data);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        errorCollector.checkThat(otherListItem.getId(), is(id));
        errorCollector.checkThat(otherListItem.getAttachment(), is(attachment));
        errorCollector.checkThat(otherListItem.getTime(), is(time));
        errorCollector.checkThat(otherListItem.getData(), is(data));
    }

    @Test
    public void setAttachment() {
        Attachment attachment1 = new Attachment(Attachment.TYPE.FILE);
        otherListItem.setAttachment(attachment1);
        errorCollector.checkThat(otherListItem.getAttachment(), is(equalTo(attachment1)));
        errorCollector.checkThat(otherListItem.getAttachment().getType(), is(equalTo(Attachment.TYPE.FILE)));
    }

    @Test
    public void setTime(){
        long timeLocal = 2_000L;
        otherListItem.setTime(timeLocal);
        errorCollector.checkThat(otherListItem.getTime(), is(equalTo(timeLocal)));
    }

    @Test
    public void getContents() {
        errorCollector.checkThat(otherListItem.getContents().isEmpty(), is(false));
        errorCollector.checkThat(otherListItem.getContents().size(), is(2));
        errorCollector.checkThat(otherListItem.getContents().get("time"), is(equalTo(String.valueOf(time))));
    }
}