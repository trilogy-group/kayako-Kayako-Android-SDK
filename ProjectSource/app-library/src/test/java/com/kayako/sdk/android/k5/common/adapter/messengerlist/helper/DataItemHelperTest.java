package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.Attachment;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.ChannelDecoration;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.DataItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.UserDecoration;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static junit.framework.Assert.assertFalse;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DataItemHelperTest {

    private static final String MESSAGE = "message";
    private final List<DataItem> dataItems = new ArrayList<>();
    private DataItemHelper dataItemHelper;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Mock
    private UserDecoration userDecoration;

    @Mock
    private ChannelDecoration channelDecoration;

    @Mock
    private List<Attachment> attachments;

    @Mock
    private Attachment attachment;

    @Mock
    private Iterator<Attachment> attachmentIterator;

    @Before
    public void setup(){
        dataItemHelper = DataItemHelper.getInstance();
        DataItem firstDataItem = new DataItem(1L, null, userDecoration,
                channelDecoration, null,MESSAGE,1_000L,null,false);
        DataItem secondDataItem = new DataItem(2L, null, userDecoration,
                channelDecoration,null,MESSAGE,2_000L,null,true);
        DataItem thirdDataItem = new DataItem(3L, null, userDecoration,
                channelDecoration,null,MESSAGE,3_000L,null,false);
        DataItem fourthDataItem = new DataItem(4L, null, userDecoration,
                channelDecoration,null,MESSAGE,4_000L, attachments,false);
        dataItems.add(firstDataItem);
        dataItems.add(secondDataItem);
        dataItems.add(thirdDataItem);
        dataItems.add(fourthDataItem);
    }

    @Test
    public void singleToneObjectValidate() {
        //Assert
        assertEquals(dataItemHelper, DataItemHelper.getInstance());
    }

    @Test
    public void whenDuplicateIdThenAssertionError() {
        //Arrange
        final String exceptionMessage =
                "Every item of the list should have a unique id!";
        dataItems.get(1).setId(1L);

        //Assert
        thrown.expect(AssertionError.class);
        thrown.expectMessage(exceptionMessage);

        //Act
        dataItemHelper.convertDataItemToListItems(dataItems);
    }

    @Test
    public void whenListNotSortedWithTimeThenAssertionError() {
        //Arrange
        final String exceptionMessage = new StringBuilder()
                .append("The list is not sorted by time! Should be in descending order ")
                .append("of creation time with newest item on top of list").toString();
        dataItems.add(new DataItem(5L, null, userDecoration,null,null,
                MESSAGE,90000000L,null,false));
        dataItems.add(new DataItem(6L, null, userDecoration,null,null,
                MESSAGE,1000L,null,false));

        //Contract
        thrown.expect(AssertionError.class);
        thrown.expectMessage(exceptionMessage);

        //Act
        dataItemHelper.convertDataItemToListItems(dataItems);
    }

    @Test
    public void convertDataItemToListItems() {
        //Arrange
        when(userDecoration.getUserId()).thenReturn(1L, 2L);
        when(dataItems.get(3).getAttachments().size()).thenReturn(2);
        when(attachmentIterator.hasNext()).thenReturn(true).thenReturn(false);
        when(attachmentIterator.next()).thenReturn(attachment);
        when(attachments.iterator()).thenReturn(attachmentIterator);
        when(channelDecoration.getSourceDrawable()).thenReturn(10, 11);

        //Act
        List<BaseListItem> viewItems = dataItemHelper.convertDataItemToListItems(dataItems);

        //Assert
        assertFalse(viewItems.isEmpty());
    }
}
