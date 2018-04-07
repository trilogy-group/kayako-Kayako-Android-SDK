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
    public ErrorCollector errorCollector = new ErrorCollector();

    private static final String THUMBNAILURL =  "/thumbnailUrl";
    private static final String ORIGINALIMAGEURL = "/originalImageUrl";
    private static final String CAPTION = "test attachmentUrlType";
    private static final String FILENAME = "test.txt";
    private static final String THUMBNAILTYPE = "thumbnailType";
    private static final String DOWNLOADURL = "/downloadUrl";
    private Long id;
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
    public void test_constructor1() {
        AttachmentUrlType attachmentUrlType = new AttachmentUrlType(id, THUMBNAILURL, CAPTION);
        errorCollector.checkThat(attachmentUrlType, notNullValue());
        errorCollector.checkThat(AttachmentUrlType.TYPE.URL, equalTo(attachmentUrlType.getType()));
        errorCollector.checkThat(id, is(attachmentUrlType.getId()));
    }

    @Test(expected = AssertionError.class)
    public void test_constructor2() {
        AttachmentUrlType attachmentUrlType = new AttachmentUrlType(id, null, CAPTION);
    }

    @Test
    public void test_constructor3() {
        errorCollector.checkThat(attachmentUrlType, notNullValue());
        errorCollector.checkThat(AttachmentUrlType.TYPE.URL, equalTo(attachmentUrlType.getType()));
        errorCollector.checkThat("/url", not(attachmentUrlType.getDownloadUrl()));
    }

    @Test
    public void test_getFileName() {
        errorCollector.checkThat(FILENAME, equalTo(attachmentUrlType.getFileName()));
    }

    @Test
    public void test_setFileName() {
        attachmentUrlType.setFileName("test2.txt");
        errorCollector.checkThat(FILENAME, not(attachmentUrlType.getFileName()));
        errorCollector.checkThat("test2.txt", equalTo(attachmentUrlType.getFileName()));
    }

    @Test
    public void test_getFileSize(){
        errorCollector.checkThat(fileSize, is(attachmentUrlType.getFileSize()));
    }

    @Test
    public void test_setFileSize(){
        attachmentUrlType.setFileSize(3000L);
        errorCollector.checkThat(fileSize, not(attachmentUrlType.getFileSize()));
        errorCollector.checkThat(3000L, is(attachmentUrlType.getFileSize()));
    }

    @Test
    public void test_getThumbnailType() {
        errorCollector.checkThat(THUMBNAILTYPE, equalTo(attachmentUrlType.getThumbnailType()));
    }

    @Test
    public void test_setThumbnailType() {
        attachmentUrlType.setThumbnailType("thumbnail_type");
        errorCollector.checkThat(THUMBNAILTYPE, not(attachmentUrlType.getThumbnailType()));
        errorCollector.checkThat("thumbnail_type", equalTo(attachmentUrlType.getThumbnailType()));
    }

    @Test
    public void test_getDownloadUrl() {
        errorCollector.checkThat(DOWNLOADURL, equalTo(attachmentUrlType.getDownloadUrl()));
    }

    @Test
    public void test_setDownloadUrl() {
        attachmentUrlType.setDownloadUrl("/downloadUrl2");
        errorCollector.checkThat(DOWNLOADURL, not(attachmentUrlType.getDownloadUrl()));
        errorCollector.checkThat("/downloadUrl2", equalTo(attachmentUrlType.getDownloadUrl()));
    }

    @Test
    public void test_getId() {
        attachmentUrlType.setId(id);
        errorCollector.checkThat(id, equalTo(attachmentUrlType.getId()));
    }

    @Test
    public void test_setId() {
        attachmentUrlType.setId(5L);
        errorCollector.checkThat(id, not(attachmentUrlType.getId()));
        errorCollector.checkThat(5L, is(attachmentUrlType.getId().longValue()));
    }

    @Test
    public void test_getThumbnailUrl() {
        errorCollector.checkThat(THUMBNAILURL, equalTo(attachmentUrlType.getThumbnailUrl()));
    }

    @Test
    public void test_setThumbnailUrl() {
        attachmentUrlType.setThumbnailUrl("/thumbnailUrl2");
        errorCollector.checkThat(THUMBNAILURL, not(attachmentUrlType.getThumbnailUrl()));
        errorCollector.checkThat("/thumbnailUrl2", equalTo(attachmentUrlType.getThumbnailUrl()));
    }

    @Test
    public void test_getCaption() {
        attachmentUrlType.setCaption(CAPTION);
        errorCollector.checkThat(CAPTION, equalTo(attachmentUrlType.getCaption()));
    }

    @Test
    public void test_setCaption() {
        attachmentUrlType.setCaption("caption test");
        errorCollector.checkThat(CAPTION, not(attachmentUrlType.getCaption()));
        errorCollector.checkThat("caption test", is(equalTo(attachmentUrlType.getCaption())));
    }

    @Test
    public void test_getTimeCreated() {
        errorCollector.checkThat(timeCreated, is(attachmentUrlType.getTimeCreated()));
    }

    @Test
    public void test_getOriginalImageUrl() {
        errorCollector.checkThat(ORIGINALIMAGEURL, equalTo(attachmentUrlType.getOriginalImageUrl()));
    }
}