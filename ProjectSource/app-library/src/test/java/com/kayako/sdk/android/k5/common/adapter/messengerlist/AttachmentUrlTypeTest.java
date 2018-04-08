package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

public class AttachmentUrlTypeTest {

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    private static final String THUMBNAILURL =  "/thumbnailUrl";
    private static final String ORIGINALIMAGEURL = "/originalImageUrl";
    private static final String CAPTION = "test attachmentUrlType";
    private static final String FILENAME = "test.txt";
    private static final String THUMBNAILTYPE = "thumbnailType";
    private static final String DOWNLOADURL = "/downloadUrl";
    private long id;
    private long fileSize;
    private long timeCreated;
    private AttachmentUrlType attachmentUrlType;

    @Before
    public void setup() {
        fileSize = 1000;
        timeCreated = 2000;
        attachmentUrlType = new AttachmentUrlType(
                THUMBNAILURL, ORIGINALIMAGEURL, FILENAME, fileSize, THUMBNAILTYPE, timeCreated, DOWNLOADURL);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        AttachmentUrlType attachmentUrlType = new AttachmentUrlType(id, THUMBNAILURL, CAPTION);
        errorCollector.checkThat(attachmentUrlType, notNullValue());
        errorCollector.checkThat(AttachmentUrlType.TYPE.URL, equalTo(attachmentUrlType.getType()));
        errorCollector.checkThat(id, is(attachmentUrlType.getId()));
    }

    @Test(expected = AssertionError.class)
    public void whenNullThumbNailUrlThenAssertionError() {
        AttachmentUrlType attachmentUrlType = new AttachmentUrlType(id, null, CAPTION);
    }

    @Test
    public void whenValidParamsInConstructorThenObjectCreated() {
        errorCollector.checkThat(attachmentUrlType, notNullValue());
        errorCollector.checkThat(AttachmentUrlType.TYPE.URL, equalTo(attachmentUrlType.getType()));
        errorCollector.checkThat("/url", not(attachmentUrlType.getDownloadUrl()));
    }

    @Test
    public void getFileName() {
        errorCollector.checkThat(FILENAME, equalTo(attachmentUrlType.getFileName()));
    }

    @Test
    public void setFileName() {
        attachmentUrlType.setFileName("test2.txt");
        errorCollector.checkThat(FILENAME, not(attachmentUrlType.getFileName()));
        errorCollector.checkThat("test2.txt", equalTo(attachmentUrlType.getFileName()));
    }

    @Test
    public void getFileSize(){
        errorCollector.checkThat(fileSize, is(attachmentUrlType.getFileSize()));
    }

    @Test
    public void setFileSize(){
        attachmentUrlType.setFileSize(3000L);
        errorCollector.checkThat(fileSize, not(attachmentUrlType.getFileSize()));
        errorCollector.checkThat(3000L, is(attachmentUrlType.getFileSize()));
    }

    @Test
    public void getThumbnailType() {
        errorCollector.checkThat(THUMBNAILTYPE, equalTo(attachmentUrlType.getThumbnailType()));
    }

    @Test
    public void setThumbnailType() {
        attachmentUrlType.setThumbnailType("thumbnail_type");
        errorCollector.checkThat(THUMBNAILTYPE, not(attachmentUrlType.getThumbnailType()));
        errorCollector.checkThat("thumbnail_type", equalTo(attachmentUrlType.getThumbnailType()));
    }

    @Test
    public void getDownloadUrl() {
        errorCollector.checkThat(DOWNLOADURL, equalTo(attachmentUrlType.getDownloadUrl()));
    }

    @Test
    public void setDownloadUrl() {
        attachmentUrlType.setDownloadUrl("/downloadUrl2");
        errorCollector.checkThat(DOWNLOADURL, not(attachmentUrlType.getDownloadUrl()));
        errorCollector.checkThat("/downloadUrl2", equalTo(attachmentUrlType.getDownloadUrl()));
    }

    @Test
    public void getId() {
        attachmentUrlType.setId(id);
        errorCollector.checkThat(id, equalTo(attachmentUrlType.getId()));
    }

    @Test
    public void setId() {
        attachmentUrlType.setId(5L);
        errorCollector.checkThat(id, not(attachmentUrlType.getId()));
        errorCollector.checkThat(5L, is(attachmentUrlType.getId().longValue()));
    }

    @Test
    public void getThumbnailUrl() {
        errorCollector.checkThat(THUMBNAILURL, equalTo(attachmentUrlType.getThumbnailUrl()));
    }

    @Test
    public void setThumbnailUrl() {
        attachmentUrlType.setThumbnailUrl("/thumbnailUrl2");
        errorCollector.checkThat(THUMBNAILURL, not(attachmentUrlType.getThumbnailUrl()));
        errorCollector.checkThat("/thumbnailUrl2", equalTo(attachmentUrlType.getThumbnailUrl()));
    }

    @Test
    public void getCaption() {
        attachmentUrlType.setCaption(CAPTION);
        errorCollector.checkThat(CAPTION, equalTo(attachmentUrlType.getCaption()));
    }

    @Test
    public void setCaption() {
        attachmentUrlType.setCaption("caption test");
        errorCollector.checkThat(CAPTION, not(attachmentUrlType.getCaption()));
        errorCollector.checkThat("caption test", is(equalTo(attachmentUrlType.getCaption())));
    }

    @Test
    public void getTimeCreated() {
        errorCollector.checkThat(timeCreated, is(attachmentUrlType.getTimeCreated()));
    }

    @Test
    public void getOriginalImageUrl() {
        errorCollector.checkThat(ORIGINALIMAGEURL, equalTo(attachmentUrlType.getOriginalImageUrl()));
    }
}