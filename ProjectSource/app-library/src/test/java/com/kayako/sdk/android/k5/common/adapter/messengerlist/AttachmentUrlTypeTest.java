package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

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
        errorCollector.checkThat(attachmentUrlType.getThumbnailUrl(), is(equalTo(THUMBNAIL_URL)));
        errorCollector.checkThat(attachmentUrlType.getType(), is(equalTo(AttachmentUrlType.TYPE.URL)));
        errorCollector.checkThat(attachmentUrlType.getOriginalImageUrl(), is(equalTo(ORIGINAL_IMAGE_URL)));
        errorCollector.checkThat(attachmentUrlType.getFileName(), is(equalTo(FILE_NAME)));
        errorCollector.checkThat(attachmentUrlType.getFileSize(), is(fileSize));
        errorCollector.checkThat(attachmentUrlType.getThumbnailType(), is(equalTo(THUMBNAIL_TYPE)));
        errorCollector.checkThat(attachmentUrlType.getTimeCreated(), is(timeCreated));
        errorCollector.checkThat(attachmentUrlType.getDownloadUrl(), is(equalTo(DOWNLOAD_URL)));
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        AttachmentUrlType attachmentUrlTypeLocal = new AttachmentUrlType(id, THUMBNAIL_URL, CAPTION);
        errorCollector.checkThat(attachmentUrlTypeLocal.getType(), is(equalTo(AttachmentUrlType.TYPE.URL)));
        errorCollector.checkThat(attachmentUrlTypeLocal.getId(), is(id));
        errorCollector.checkThat(attachmentUrlTypeLocal.getCaption(), is(equalTo(CAPTION)));
    }

    @Test
    public void whenNullThumbNailUrlThenAssertionError() {
        thrown.expect(AssertionError.class);
        AttachmentUrlType attachmentUrlType = new AttachmentUrlType(id, null, CAPTION);
    }

    @Test
    public void setFileName() {
        attachmentUrlType.setFileName("test2.txt");
        errorCollector.checkThat(attachmentUrlType.getFileName(), not(FILE_NAME));
        errorCollector.checkThat(attachmentUrlType.getFileName(), equalTo("test2.txt"));
    }

    @Test
    public void setFileSize(){
        attachmentUrlType.setFileSize(3_000L);
        errorCollector.checkThat(fileSize, not(attachmentUrlType.getFileSize()));
        errorCollector.checkThat(attachmentUrlType.getFileSize(), is(3_000L));
    }

    @Test
    public void setThumbnailType() {
        attachmentUrlType.setThumbnailType("thumbnail_type");
        errorCollector.checkThat(attachmentUrlType.getThumbnailType(), not(THUMBNAIL_TYPE));
        errorCollector.checkThat(attachmentUrlType.getThumbnailType(), equalTo("thumbnail_type"));
    }

    @Test
    public void setDownloadUrl() {
        attachmentUrlType.setDownloadUrl("/downloadUrl2");
        errorCollector.checkThat(attachmentUrlType.getDownloadUrl(), not(DOWNLOAD_URL));
        errorCollector.checkThat(attachmentUrlType.getDownloadUrl(), equalTo("/downloadUrl2"));
    }

    @Test
    public void setId() {
        attachmentUrlType.setId(5L);
        errorCollector.checkThat(attachmentUrlType.getId(), not(id));
        errorCollector.checkThat(attachmentUrlType.getId(), is(5L));
    }

    @Test
    public void setThumbnailUrl() {
        attachmentUrlType.setThumbnailUrl("/thumbnailUrl2");
        errorCollector.checkThat(attachmentUrlType.getThumbnailUrl(), not(THUMBNAIL_URL));
        errorCollector.checkThat(attachmentUrlType.getThumbnailUrl(), equalTo("/thumbnailUrl2"));
    }

    @Test
    public void setCaption() {
        attachmentUrlType.setCaption("caption test");
        errorCollector.checkThat(attachmentUrlType.getCaption(), not(CAPTION));
        errorCollector.checkThat(attachmentUrlType.getCaption(), is(equalTo("caption test")));
    }
}