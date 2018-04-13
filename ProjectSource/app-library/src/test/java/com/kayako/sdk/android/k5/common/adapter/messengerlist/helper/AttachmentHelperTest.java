package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.test.mock.MockContext;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.Attachment;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.AttachmentFileType;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.AttachmentUrlType;
import com.kayako.sdk.android.k5.core.Kayako;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.doNothing;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        TextUtils.class,
        Html.class
})
public class AttachmentHelperTest {

    private static final String THUMBNAIL_IMAGE_URL = "imageUrl";
    private static final String FILE_TYPE = "image";
    private static final String FILE_CAPTION = "caption";

    private File attachmentFile;

    @Mock
    private View attachmentPlaceholder;

    @Mock
    private ImageView thumbnailImageView;

    @Mock
    private TextView captionTextView;

    @Mock
    private TextView mockTextView;

    @Mock
    private ImageView mockImageView;

    @Before
    public void setUp() {
    }

    @Test
    public void setUpAttachmentImagesWhenTypeIsFile() {
        //Arrange
        AttachmentFileType attachmentFileType = mock(AttachmentFileType.class);
        mockStatic(TextUtils.class);
        mockStatic(Html.class);
        when(TextUtils.isEmpty(attachmentFileType.getCaption())).thenReturn(false);
        when(Html.fromHtml(FILE_CAPTION)).thenReturn(mock(Spanned.class));
        when(attachmentFileType.getType()).thenReturn(Attachment.TYPE.FILE);
        when(attachmentFileType.getThumbnailFile()).thenReturn(null);
        when(attachmentFileType.getCaption()).thenReturn(FILE_CAPTION);

        //Act
        AttachmentHelper.setUpAttachmentImages(attachmentFileType, attachmentPlaceholder, thumbnailImageView, captionTextView);

        //Assert
        verify(attachmentPlaceholder, times(1)).setVisibility(View.VISIBLE);
        verify(thumbnailImageView, times(1)).setVisibility(View.GONE);
    }

    @Test
    public void setUpAttachmentImagesWhenTypeIsFileElseCase() {
        //Arrange
        AttachmentFileType attachmentFileType = mock(AttachmentFileType.class);
        mockStatic(TextUtils.class);
        mockStatic(Html.class);
        when(TextUtils.isEmpty(attachmentFileType.getCaption())).thenReturn(false);
        when(Html.fromHtml(FILE_CAPTION)).thenReturn(mock(Spanned.class));
        when(attachmentFileType.getType()).thenReturn(Attachment.TYPE.FILE);
        when(attachmentFileType.getThumbnailFile()).thenReturn(attachmentFile);
        when(attachmentFileType.getCaption()).thenReturn(FILE_CAPTION);

        //Act
        AttachmentHelper.setUpAttachmentImages(attachmentFileType, attachmentPlaceholder, thumbnailImageView, captionTextView);

        //Assert
        verify(attachmentPlaceholder, times(1)).setVisibility(View.VISIBLE);
        verify(thumbnailImageView, times(1)).setVisibility(View.GONE);
    }



    @Test
    public void setUpAttachmentImagesWhenTypeIsURLElseCase() {
        //Arrange
        AttachmentUrlType attachmentUrlType = mock(AttachmentUrlType.class);
        when(attachmentUrlType.getType()).thenReturn(Attachment.TYPE.URL);
        when(attachmentUrlType.getThumbnailUrl()).thenReturn(THUMBNAIL_IMAGE_URL);
        when(attachmentPlaceholder.findViewById(R.id.ko__attachment_placeholder_icon)).thenReturn(mockImageView);
        when(attachmentPlaceholder.findViewById(R.id.ko__attachment_placeholder_text)).thenReturn(mockTextView);

        mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(attachmentUrlType.getCaption())).thenReturn(true);

        //Act
        AttachmentHelper.setUpAttachmentImages(attachmentUrlType, attachmentPlaceholder, thumbnailImageView, captionTextView);

        //Assert
        verify(attachmentPlaceholder, times(1)).setVisibility(View.VISIBLE);
        verify(thumbnailImageView, times(1)).setVisibility(View.GONE);

        verify(mockTextView, times(1)).setText(R.string.ko__messenger_attachment_placeholder_untitled);
        verify(mockImageView, times(1)).setImageResource(R.drawable.ko__ic_attachment_generic);
    }

    @Test
    public void configureAttachmentPlaceholder() {
    }

    @Test
    public void identifyType() {
    }
}