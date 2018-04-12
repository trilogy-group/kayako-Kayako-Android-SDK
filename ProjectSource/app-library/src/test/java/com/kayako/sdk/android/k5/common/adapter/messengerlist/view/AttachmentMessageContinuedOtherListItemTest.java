package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.Attachment;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;

public class AttachmentMessageContinuedOtherListItemTest {

    private static final long ID = 1L;
    private static final long TIME = 1_000L;
    private final Map<String, Object> data = new HashMap<>();
    private Attachment attachment;
    private AttachmentMessageContinuedOtherListItem otherListItem;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        attachment = new Attachment(Attachment.TYPE.URL);
        otherListItem = new AttachmentMessageContinuedOtherListItem(ID, attachment, TIME, data);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        errorCollector.checkThat(otherListItem.getId(), is(ID));
        errorCollector.checkThat(otherListItem.getAttachment(), is(attachment));
        errorCollector.checkThat(otherListItem.getTime(), is(TIME));
        errorCollector.checkThat(otherListItem.getData(), is(data));
    }

    @Test
    public void setAttachment() {
        //Arrange
        final Attachment attachmentLocal = new Attachment(Attachment.TYPE.FILE);

        //Act
        otherListItem.setAttachment(attachmentLocal);

        //Assert
        errorCollector.checkThat(otherListItem.getAttachment(), is(attachmentLocal));
        errorCollector.checkThat(otherListItem.getAttachment().getType(), is(Attachment.TYPE.FILE));
    }

    @Test
    public void setTime() {
        //Arrange
        final long timeLocal = 2_000L;

        //Act
        otherListItem.setTime(timeLocal);

        //Assert
        errorCollector.checkThat(otherListItem.getTime(), is(timeLocal));
    }

    @Test
    public void getContents() {
        errorCollector.checkThat(otherListItem.getContents().isEmpty(), is(false));
        errorCollector.checkThat(otherListItem.getContents().size(), is(2));
        errorCollector.checkThat(otherListItem.getContents().get("time"), is(String.valueOf(TIME)));
    }
}
