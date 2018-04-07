package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AttachmentUrlTypeTest {

    private String thumbnailUrl;
    private String originalImageUrl;
    private Long id;
    private String caption;
    private String fileName;
    private long fileSize;
    private String thumbnailType;
    private long timeCreated;
    private String downloadUrl;

    AttachmentUrlType attachmentUrlType;

    @Before
    public void setup() {
        thumbnailUrl = "/thumbnailUrl";
        originalImageUrl = "/originalImageUrl";
        id = 1L;
        caption = "test attachmentUrlType";
        fileName = "test.txt";
        fileSize = 1000;
        thumbnailType = "thumbnailType";
        timeCreated = 2000;
        downloadUrl = "/downloadUrl";
        attachmentUrlType = new AttachmentUrlType(
                thumbnailUrl, originalImageUrl, fileName, fileSize, thumbnailType, timeCreated, downloadUrl);
    }

    @Test
    public void test_constructor1() {
        AttachmentUrlType attachmentUrlType = new AttachmentUrlType(id, thumbnailUrl, caption);
        assertNotNull(attachmentUrlType);
        assertEquals(AttachmentUrlType.TYPE.URL, attachmentUrlType.getType());
        assertEquals(id, attachmentUrlType.getId());
    }

    @Test(expected = AssertionError.class)
    public void test_constructor2() {
        AttachmentUrlType attachmentUrlType = new AttachmentUrlType(id, null, caption);
    }

    @Test
    public void test_constructor3() {
        AttachmentUrlType attachmentUrlType = new AttachmentUrlType(
                thumbnailUrl, originalImageUrl, fileName, fileSize, thumbnailType, timeCreated, downloadUrl);
        assertNotNull(attachmentUrlType);
        assertEquals(AttachmentUrlType.TYPE.URL, attachmentUrlType.getType());
        assertNotEquals("/url", attachmentUrlType.getDownloadUrl());
    }

    @Test
    public void test_getFileName() {
        assertEquals(fileName, attachmentUrlType.getFileName());
    }

    @Test
    public void test_setFileName() {
        attachmentUrlType.setFileName("test2.txt");
        assertNotEquals(fileName, attachmentUrlType.getFileName());
        assertEquals("test2.txt", attachmentUrlType.getFileName());
    }

    @Test
    public void test_getFileSize(){
        assertEquals(fileSize, attachmentUrlType.getFileSize());
    }

    @Test
    public void test_setFileSize(){
        attachmentUrlType.setFileSize(3000L);
        assertNotEquals(fileSize, attachmentUrlType.getFileSize());
        assertEquals(3000L, attachmentUrlType.getFileSize());
    }

    @Test
    public void test_getThumbnailType() {
        assertEquals(thumbnailType, attachmentUrlType.getThumbnailType());
    }

    @Test
    public void test_setThumbnailType() {
        attachmentUrlType.setThumbnailType("thumbnail_type");
        assertNotEquals(thumbnailType, attachmentUrlType.getThumbnailType());
        assertEquals("thumbnail_type", attachmentUrlType.getThumbnailType());
    }

    @Test
    public void test_getDownloadUrl() {
        assertEquals(downloadUrl, attachmentUrlType.getDownloadUrl());
    }

    @Test
    public void test_setDownloadUrl() {
        attachmentUrlType.setDownloadUrl("/downloadUrl2");
        assertNotEquals(downloadUrl, attachmentUrlType.getDownloadUrl());
        assertEquals("/downloadUrl2", attachmentUrlType.getDownloadUrl());
    }

    @Test
    public void test_getId() {
        attachmentUrlType.setId(id);
        assertEquals(id, attachmentUrlType.getId());
    }

    @Test
    public void test_setId() {
        attachmentUrlType.setId(5L);
        assertNotEquals(id, attachmentUrlType.getId());
        assertEquals(5L, attachmentUrlType.getId().longValue());
    }

    @Test
    public void test_getThumbnailUrl() {
        assertEquals(thumbnailUrl, attachmentUrlType.getThumbnailUrl());
    }

    @Test
    public void test_setThumbnailUrl() {
        attachmentUrlType.setThumbnailUrl("/thumbnailUrl2");
        assertNotEquals(thumbnailUrl, attachmentUrlType.getThumbnailUrl());
        assertEquals("/thumbnailUrl2", attachmentUrlType.getThumbnailUrl());
    }

    @Test
    public void test_getCaption() {
        attachmentUrlType.setCaption(caption);
        assertEquals(caption, attachmentUrlType.getCaption());
    }

    @Test
    public void test_setCaption() {
        attachmentUrlType.setCaption("caption test");
        assertNotEquals(caption, attachmentUrlType.getCaption());
        assertEquals("caption test", attachmentUrlType.getCaption());
    }

    @Test
    public void test_getTimeCreated() {
        assertEquals(timeCreated, attachmentUrlType.getTimeCreated());
    }

    @Test
    public void test_getOriginalImageUrl() {
        assertEquals(originalImageUrl, attachmentUrlType.getOriginalImageUrl());
    }
}