package com.kayako.sdk.android.k5.messenger.messagelistpage;

import android.test.mock.MockContext;
import android.text.TextUtils;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.Attachment;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.AttachmentUrlType;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.AttachmentHelper;
import com.kayako.sdk.android.k5.common.fragments.ListPageState;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.ConversationHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.DownloadAttachment;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.FileAttachmentDownloadHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.FileAttachmentHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.MessengerListHelper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.is;

import static org.mockito.Mockito.verify;

import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        MessengerPref.class,
        Kayako.class,
        AttachmentHelper.class,
        FileAttachmentDownloadHelper.class,

})
public class MessageListContainerPresenterTest {

    private static final String BRAND_NAME = "BRAND_NAME";
    private static final String IMAGE_URL = "IMAGE_URL";
    private static final String DOWNLOAD_URL = "DOWNLOAD_URL";
    private static final String IMAGE_NAME = "IMAGE_NAME";
    private static final String ERROR_MESSAGE = "ConversationId must be valid to call this method!";
    private static final String THUMBNAIL_IMAGE_URL = "imageUrl";
    private static final String FORMAT_PARAM = "Message %1$s";
    private static final long CONVERSATION_ID = 123l;
    private static final long TIME_CREATED = System.currentTimeMillis();
    private static final long FILE_SIZE = 1234l;
    private static final ListPageState pageState = ListPageState.EMPTY;

    @Mock
    private MockContext mockContext;

    @Mock
    private MessengerPref sInstance;

    @Mock
    private MessageListContainerContract.View mView;

    @Mock
    private MessageListContainerContract.Data mData;

    @Mock
    private MessengerListHelper mMessengerListHelper;

    @Mock
    private ConversationHelper mConversationHelper;

    @Mock
    private FileAttachmentDownloadHelper mFileAttachmentDownloadHelper;

    @Mock
    private DownloadAttachment downloadAttachment;

    @InjectMocks
    private MessageListContainerPresenter messageListContainerPresenter;

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
    }

    @Test
    public void setData() throws Exception {
        //Act
        messageListContainerPresenter.setData(mData);
        Field f = messageListContainerPresenter.getClass().getDeclaredField("mData");
        f.setAccessible(true);

        //Assert
        assertEquals(mData, f.get(messageListContainerPresenter));
    }

    @Test
    public void onClickRetryInErrorViewWhenIsConversationCreatedFalse() {
        //Arrange
        mockStatic(Kayako.class);
        mockStatic(MessengerPref.class);
        when(Kayako.getApplicationContext()).thenReturn(mockContext);
        when(MessengerPref.getInstance()).thenReturn(sInstance);
        when(MessengerPref.getInstance().getBrandName()).thenReturn(BRAND_NAME);
        when(mConversationHelper.isConversationCreated()).thenReturn(true);
        doNothing().when(mView).showLoadingViewInMessageListingView();
        doNothing().when(mView).expandToolbar();

        //Act
        messageListContainerPresenter.onClickRetryInErrorView();

        //Assert
        verify(mView, times(1)).showLoadingViewInMessageListingView();
        verify(mView, times(1)).expandToolbar();
    }

    @Test
    public void onClickRetryInErrorViewWhenIsConversationCreatedTrueAndConversationIdIsNull() {
        //Arrange
        mockStatic(Kayako.class);
        mockStatic(MessengerPref.class);
        when(Kayako.getApplicationContext()).thenReturn(mockContext);
        when(MessengerPref.getInstance()).thenReturn(sInstance);
        when(MessengerPref.getInstance().getBrandName()).thenReturn(BRAND_NAME);
        when(mConversationHelper.isConversationCreated()).thenReturn(true);
        when(mConversationHelper.getConversationId()).thenReturn(null);
        doNothing().when(mView).showLoadingViewInMessageListingView();
        doNothing().when(mView).expandToolbar();
        Whitebox.setInternalState(messageListContainerPresenter, "mConversationHelper",
                mConversationHelper);
        exception.expect(AssertionError.class);
        exception.expectMessage(ERROR_MESSAGE);

        //Act
        messageListContainerPresenter.onClickRetryInErrorView();

        //Assert
        verify(mView, times(1)).showLoadingViewInMessageListingView();
        verify(mView, never()).expandToolbar();
    }

    @Test
    public void onClickRetryInErrorViewWhenIsConversationCreatedTrueAndConversationIdIsNotNull() {
        //Arrange
        mockStatic(Kayako.class);
        mockStatic(MessengerPref.class);
        when(Kayako.getApplicationContext()).thenReturn(mockContext);
        when(mockContext.getString(R.string.ko__messenger_reply_box_hint_to_brand))
                .thenReturn(FORMAT_PARAM);
        when(MessengerPref.getInstance()).thenReturn(sInstance);
        when(MessengerPref.getInstance().getBrandName()).thenReturn(BRAND_NAME);
        when(mConversationHelper.isConversationCreated()).thenReturn(true);
        when(mConversationHelper.getConversationId()).thenReturn(CONVERSATION_ID);
        doNothing().when(mView).showLoadingViewInMessageListingView();
        doNothing().when(mView).expandToolbar();
        Whitebox.setInternalState(messageListContainerPresenter, "mConversationHelper",
                mConversationHelper);

        //Act
        messageListContainerPresenter.onClickRetryInErrorView();

        //Assert
        verify(mView, times(1)).showLoadingViewInMessageListingView();
        verify(mView, never()).expandToolbar();
    }

    @Test
    public void onPageStateChange() {
        //Arrange
        mockStatic(Kayako.class);
        mockStatic(MessengerPref.class);
        when(Kayako.getApplicationContext()).thenReturn(mockContext);
        when(MessengerPref.getInstance()).thenReturn(sInstance);
        when(MessengerPref.getInstance().getBrandName()).thenReturn(BRAND_NAME);
        Whitebox.setInternalState(messageListContainerPresenter, "mMessengerListHelper",
                mMessengerListHelper);
        //Act
        messageListContainerPresenter.onPageStateChange(pageState);

        //Asert
        verify(mMessengerListHelper, times(1)).setListPageState(pageState);
    }


    @Test
    public void onScrollList() {
    }

    @Test
    public void onListItemClick() {
    }

    @Test
    public void onListAttachmentClick() {
        //Arrange
        AttachmentUrlType attachmentUrlType = mock(AttachmentUrlType.class);
        mockStatic(AttachmentHelper.class);
        mockStatic(Kayako.class);
        mockStatic(MessengerPref.class);
        when(Kayako.getApplicationContext()).thenReturn(mockContext);
        when(MessengerPref.getInstance()).thenReturn(sInstance);
        when(attachmentUrlType.getType()).thenReturn(Attachment.TYPE.URL);
        when(attachmentUrlType.getOriginalImageUrl()).thenReturn(IMAGE_URL);
        when(attachmentUrlType.getThumbnailUrl()).thenReturn(THUMBNAIL_IMAGE_URL);
        when(attachmentUrlType.getOriginalImageUrl()).thenReturn(IMAGE_URL);
        when(attachmentUrlType.getFileName()).thenReturn(IMAGE_NAME);
        when(attachmentUrlType.getDownloadUrl()).thenReturn(DOWNLOAD_URL);
        when(attachmentUrlType.getTimeCreated()).thenReturn(TIME_CREATED);
        when(attachmentUrlType.getFileSize()).thenReturn(FILE_SIZE);
        when(AttachmentHelper.identifyType(
                attachmentUrlType.getThumbnailType(),
                attachmentUrlType.getFileName())
        ).thenReturn(AttachmentHelper.AttachmentFileType.IMAGE);
        Whitebox.setInternalState(messageListContainerPresenter, "mView", mView);

        //Act
        messageListContainerPresenter.onListAttachmentClick(attachmentUrlType);

        //Assert
        verify(mView, times(1)).
                showAttachmentPreview(
                        eq(IMAGE_URL),
                        eq(IMAGE_NAME),
                        eq(TIME_CREATED),
                        eq(DOWNLOAD_URL),
                        eq(FILE_SIZE)
                );
    }

    @Test
    public void onListAttachmentClickElseCase() {
        //Arrange
        AttachmentUrlType attachmentUrlType = mock(AttachmentUrlType.class);
        mockStatic(AttachmentHelper.class);
        mockStatic(Kayako.class);
        mockStatic(MessengerPref.class);
        mockStatic(FileAttachmentDownloadHelper.class);
        Whitebox.setInternalState(messageListContainerPresenter, "mFileAttachmentDownloadHelper", mFileAttachmentDownloadHelper);
        when(Kayako.getApplicationContext()).thenReturn(mockContext);
        when(MessengerPref.getInstance()).thenReturn(sInstance);
        when(attachmentUrlType.getType()).thenReturn(Attachment.TYPE.URL);
        when(attachmentUrlType.getOriginalImageUrl()).thenReturn(IMAGE_URL);
        when(attachmentUrlType.getThumbnailUrl()).thenReturn(THUMBNAIL_IMAGE_URL);
        when(attachmentUrlType.getOriginalImageUrl()).thenReturn(IMAGE_URL);
        when(attachmentUrlType.getFileName()).thenReturn(IMAGE_NAME);
        when(attachmentUrlType.getDownloadUrl()).thenReturn(DOWNLOAD_URL);
        when(attachmentUrlType.getTimeCreated()).thenReturn(TIME_CREATED);
        when(attachmentUrlType.getFileSize()).thenReturn(FILE_SIZE);
        when(AttachmentHelper.identifyType(
                attachmentUrlType.getThumbnailType(),
                attachmentUrlType.getFileName())
        ).thenReturn(AttachmentHelper.AttachmentFileType.OTHER);
        when(FileAttachmentDownloadHelper.generateDownloadAttachmentForMessenger(
                eq(IMAGE_NAME), eq(FILE_SIZE), eq(DOWNLOAD_URL))).thenReturn(downloadAttachment);

        //Act
        messageListContainerPresenter.onListAttachmentClick(attachmentUrlType);

        //Assert
        verifyStatic(FileAttachmentHelper.class);
        FileAttachmentDownloadHelper.generateDownloadAttachmentForMessenger(
                IMAGE_NAME, FILE_SIZE, DOWNLOAD_URL);
    }

    @Test
    public void onLoadMoreItems() {
    }

    @Test
    public void setView() {
    }

    @Test
    public void initPage() {
    }

    @Test
    public void closePage() {
    }

    @Test
    public void onClickSendInReplyView() {
    }

    @Test
    public void onTypeReply() {
    }

    @Test
    public void onClickAddAttachment() {
    }

    @Test
    public void onConfirmSendingOfAttachment() {
    }

    @Test
    public void loadNextMessages() {
    }

    @Test
    public void reloadConversation() {
    }

    @Test
    public void resetVariables() throws Exception {
        //method is private, i am going to use reflection
        //Arrange
        Method method = messageListContainerPresenter.getClass().getDeclaredMethod("resetVariables");
        method.setAccessible(true);

        //Act
        method.invoke(messageListContainerPresenter);
    }
}
