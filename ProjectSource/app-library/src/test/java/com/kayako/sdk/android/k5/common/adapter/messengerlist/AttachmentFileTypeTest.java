package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import java.io.File;

public class AttachmentFileTypeTest {

    @Rule
    public final ErrorCollector errorCollector  = new ErrorCollector();

    private static final String CAPTION = "file type test";

    @Test(expected = AssertionError.class)
    public void givenNullFileWhenConstructorThenAssertionError() {
        AttachmentFileType attachmentFileType = new AttachmentFileType(1L, null, CAPTION);
    }

    @Test
    public void givenValidParamsWhenConstructorThenObjectCreated() {
        File file = new File("");
        AttachmentFileType attachmentFileType = new AttachmentFileType(1L, file, CAPTION);
        errorCollector.checkThat(attachmentFileType, notNullValue());
        errorCollector.checkThat(AttachmentFileType.TYPE.FILE, equalTo(attachmentFileType.getType()));
    }

    @Test
    public void getId() {
        AttachmentFileType attachmentFileType = getAttachmentFileType();
        errorCollector.checkThat(1L, is(attachmentFileType.getId().longValue()));
    }

    @Test
    public void getCaption() {
        AttachmentFileType attachmentFileType = getAttachmentFileType();
        errorCollector.checkThat(CAPTION, equalTo(attachmentFileType.getCaption()));
    }

    @Test
    public void getThumbnailFile() {
        AttachmentFileType attachmentFileType = getAttachmentFileType();
        errorCollector.checkThat(attachmentFileType.getThumbnailFile(), notNullValue());
    }

    private AttachmentFileType getAttachmentFileType() {
        File file = new File("");
        return new AttachmentFileType(1L, file, CAPTION);
    }
}
