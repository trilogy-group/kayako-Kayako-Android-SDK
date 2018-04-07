package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import org.junit.Test;
import java.io.File;
import static org.junit.Assert.*;

public class AttachmentFileTypeTest {

    @Test(expected = AssertionError.class)
    public void test_constructor1() {
        AttachmentFileType attachmentFileType = new AttachmentFileType(1L, null, "file type test");
    }

    @Test
    public void test_constructor2() {
        File file = new File("");
        AttachmentFileType attachmentFileType = new AttachmentFileType(1L, file, "file type test");
        assertNotNull(attachmentFileType);
        assertEquals(AttachmentFileType.TYPE.FILE, attachmentFileType.getType());
    }

    @Test
    public void test_getId() {
        AttachmentFileType attachmentFileType = getAttachmentFileType();
        assertEquals(1L, attachmentFileType.getId().longValue());
    }

    @Test
    public void test_getCaption() {
        AttachmentFileType attachmentFileType = getAttachmentFileType();
        assertEquals("file type test", attachmentFileType.getCaption());
    }

    @Test
    public void test_getThumbnailFile() {
        AttachmentFileType attachmentFileType = getAttachmentFileType();
        assertNotNull(attachmentFileType.getThumbnailFile());
    }

    private AttachmentFileType getAttachmentFileType() {
        File file = new File("");
        return new AttachmentFileType(1L, file, "file type test");
    }
}
