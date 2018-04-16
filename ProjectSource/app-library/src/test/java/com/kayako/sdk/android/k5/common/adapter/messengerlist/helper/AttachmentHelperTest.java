package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.Attachment;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.AttachmentFileType;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.AttachmentUrlType;
import com.kayako.sdk.android.k5.common.utils.file.FileStorageUtil;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        TextUtils.class,
        Html.class,
        FileStorageUtil.class
})
public class AttachmentHelperTest {

    private static final String AUDIO_FILE_TYPE = "audio";
    private static final String IMAGE_FILE_TYPE = "image";
    private static final String VIDEO_FILE_TYPE = "video";
    private static final String FILE_CAPTION = "caption";
    private static final String FILE_NAME = "filename";
    private static final String THUMBNAIL_IMAGE_URL = "imageUrl";

    @Mock
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

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

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
        AttachmentHelper.setUpAttachmentImages(attachmentFileType, attachmentPlaceholder,
                thumbnailImageView, captionTextView);

        //Assert
        verify(attachmentPlaceholder, times(1)).setVisibility(View.VISIBLE);
        verify(thumbnailImageView, times(1)).setVisibility(View.GONE);
        collector.checkThat(attachmentPlaceholder.getVisibility(), notNullValue());
        collector.checkThat(thumbnailImageView.getVisibility(), notNullValue());
    }

    @Test
    public void setUpAttachmentImagesWhenTypeIsFileElseCase() {
        //Arrange
        AttachmentFileType attachmentFileType = mock(AttachmentFileType.class);
        mockStatic(TextUtils.class);
        mockStatic(Html.class);
        mockStatic(FileStorageUtil.class);
        when(TextUtils.isEmpty(attachmentFileType.getCaption())).thenReturn(false);
        when(Html.fromHtml(FILE_CAPTION)).thenReturn(mock(Spanned.class));
        when(FileStorageUtil.getMimeType(attachmentFile)).thenReturn(VIDEO_FILE_TYPE);
        when(attachmentFileType.getType()).thenReturn(Attachment.TYPE.FILE);
        when(attachmentFileType.getThumbnailFile()).thenReturn(attachmentFile);
        when(attachmentFileType.getCaption()).thenReturn(FILE_CAPTION);
        when(attachmentPlaceholder.findViewById(R.id.ko__attachment_placeholder_icon))
                .thenReturn(mockImageView);
        when(attachmentPlaceholder.findViewById(R.id.ko__attachment_placeholder_text))
                .thenReturn(mockTextView);

        //Act
        AttachmentHelper.setUpAttachmentImages(attachmentFileType, attachmentPlaceholder,
                thumbnailImageView, captionTextView);

        //Assert
        verify(attachmentPlaceholder, times(1)).setVisibility(View.VISIBLE);
        verify(thumbnailImageView, times(1)).setVisibility(View.GONE);
        collector.checkThat(attachmentPlaceholder.getVisibility(), notNullValue());
        collector.checkThat(thumbnailImageView.getVisibility(), notNullValue());
    }

    @Test
    public void setUpAttachmentImagesWhenTypeIsURL() {
        //Arrange
        AttachmentUrlType attachmentUrlType = mock(AttachmentUrlType.class);
        when(attachmentUrlType.getType()).thenReturn(Attachment.TYPE.URL);
        when(attachmentUrlType.getThumbnailUrl()).thenReturn(THUMBNAIL_IMAGE_URL);
        when(attachmentPlaceholder.findViewById(R.id.ko__attachment_placeholder_icon))
                .thenReturn(mockImageView);
        when(attachmentPlaceholder.findViewById(R.id.ko__attachment_placeholder_text))
                .thenReturn(mockTextView);
        mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(attachmentUrlType.getCaption())).thenReturn(true);

        //Act
        AttachmentHelper.setUpAttachmentImages(attachmentUrlType, attachmentPlaceholder,
                thumbnailImageView, captionTextView);

        //Assert
        verify(attachmentPlaceholder, times(1)).setVisibility(View.VISIBLE);
        verify(thumbnailImageView, times(1)).setVisibility(View.GONE);
        verify(mockTextView, times(1))
                .setText(R.string.ko__messenger_attachment_placeholder_untitled);
        verify(mockImageView, times(1))
                .setImageResource(R.drawable.ko__ic_attachment_generic);
        collector.checkThat(thumbnailImageView.getVisibility(), notNullValue());
        collector.checkThat(attachmentPlaceholder.getVisibility(), notNullValue());
    }

    @Test
    public void identifyTypeWhenTypeIsAudio() {
        //Act
        AttachmentHelper.AttachmentFileType attachmentFileType = AttachmentHelper
                .identifyType(AUDIO_FILE_TYPE, FILE_NAME);

        //Assert
        assertThat(attachmentFileType, is(AttachmentHelper.AttachmentFileType.AUDIO));
    }

    @Test
    public void identifyTypeWhenTypeIsImage() {
        //Act
        AttachmentHelper.AttachmentFileType attachmentFileType = AttachmentHelper
                .identifyType(IMAGE_FILE_TYPE, FILE_NAME);

        //Assert
        assertThat(attachmentFileType, is(AttachmentHelper.AttachmentFileType.IMAGE));
    }
}
