package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.is;

public class AttachmentUrlTypeTest {

    private static final String THUMBNAIL_URL =  "/thumbnailUrl";
    private static final String ORIGINAL_IMAGE_URL = "/originalImageUrl";
    private static final String CAPTION = "test attachmentUrlType";
    private static final String FILE_NAME = "test.txt";
    private static final String THUMBNAIL_TYPE = "thumbnailType";
    private static final String DOWNLOAD_URL = "/downloadUrl";
    private long id;
    private long fileSize;
    private long timeCreated;
    private AttachmentUrlType attachmentUrlType;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        id = 1L;
        fileSize = 1_000L;
        timeCreated = 2_000L;
        attachmentUrlType = new AttachmentUrlType(
                THUMBNAIL_URL, ORIGINAL_IMAGE_URL, FILE_NAME, fileSize, THUMBNAIL_TYPE, timeCreated, DOWNLOAD_URL);
    }

    @Test
    public void whenValidParamsInConstructorThenObjectCreated() {
        errorCollector.checkThat(attachmentUrlType.getThumbnailUrl(), is(THUMBNAIL_URL));
        errorCollector.checkThat(attachmentUrlType.getType(), is(AttachmentUrlType.TYPE.URL));
        errorCollector.checkThat(attachmentUrlType.getOriginalImageUrl(), is(ORIGINAL_IMAGE_URL));
        errorCollector.checkThat(attachmentUrlType.getFileName(), is(FILE_NAME));
        errorCollector.checkThat(attachmentUrlType.getFileSize(), is(fileSize));
        errorCollector.checkThat(attachmentUrlType.getThumbnailType(), is(THUMBNAIL_TYPE));
        errorCollector.checkThat(attachmentUrlType.getTimeCreated(), is(timeCreated));
        errorCollector.checkThat(attachmentUrlType.getDownloadUrl(), is(DOWNLOAD_URL));
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        final AttachmentUrlType attachmentUrlTypeLocal = new AttachmentUrlType(id, THUMBNAIL_URL, CAPTION);
        errorCollector.checkThat(attachmentUrlTypeLocal.getType(), is(AttachmentUrlType.TYPE.URL));
        errorCollector.checkThat(attachmentUrlTypeLocal.getId(), is(id));
        errorCollector.checkThat(attachmentUrlTypeLocal.getCaption(), is(CAPTION));
    }

    @Test
    public void whenNullThumbNailUrlThenAssertionError() {
        final Matcher<String> nullMatcher = new IsNull<>();
        thrown.expect(AssertionError.class);
        thrown.expectMessage(nullMatcher);
        new AttachmentUrlType(id, null, CAPTION);
    }

    @Test
    public void setFileName() {
        //Arrange
        final String newFileName = "test2.txt";

        //Act
        attachmentUrlType.setFileName(newFileName);

        //Assert
        errorCollector.checkThat(attachmentUrlType.getFileName(), not(FILE_NAME));
        errorCollector.checkThat(attachmentUrlType.getFileName(), is(newFileName));
    }

    @Test
    public void setFileSize() {
        //Arrange
        final long newFileSize = 3_000L;

        //Act
        attachmentUrlType.setFileSize(newFileSize);

        //Assert
        errorCollector.checkThat(fileSize, not(attachmentUrlType.getFileSize()));
        errorCollector.checkThat(attachmentUrlType.getFileSize(), is(newFileSize));
    }

    @Test
    public void setThumbnailType() {
        //Arrange
        final String newThumbnailType = "thumbnail_type";

        //Act
        attachmentUrlType.setThumbnailType(newThumbnailType);

        //Assert
        errorCollector.checkThat(attachmentUrlType.getThumbnailType(), is(newThumbnailType));
    }

    @Test
    public void setDownloadUrl() {
        //Arrange
        final String newDownloadUrl = "/newDownloadUrl";

        //Act
        attachmentUrlType.setDownloadUrl(newDownloadUrl);

        //Assert
        errorCollector.checkThat(attachmentUrlType.getDownloadUrl(), is(newDownloadUrl));
    }

    @Test
    public void setId() {
        //Arrange
        final long newId = 5L;

        //Act
        attachmentUrlType.setId(newId);

        //Assert
        errorCollector.checkThat(attachmentUrlType.getId(), is(newId));
    }

    @Test
    public void setThumbnailUrl() {
        //Arrange
        final String newThumbNailUrl = "/newThumbnailUrl";

        //Act
        attachmentUrlType.setThumbnailUrl(newThumbNailUrl);

        //Assert
        errorCollector.checkThat(attachmentUrlType.getThumbnailUrl(), not(THUMBNAIL_URL));
        errorCollector.checkThat(attachmentUrlType.getThumbnailUrl(), is(newThumbNailUrl));
    }

    @Test
    public void setCaption() {
        //Arrange
        final String newCaption = "new_caption";

        //Act
        attachmentUrlType.setCaption(newCaption);

        //Assert
        errorCollector.checkThat(attachmentUrlType.getCaption(), not(CAPTION));
        errorCollector.checkThat(attachmentUrlType.getCaption(), is(newCaption));
    }
}
