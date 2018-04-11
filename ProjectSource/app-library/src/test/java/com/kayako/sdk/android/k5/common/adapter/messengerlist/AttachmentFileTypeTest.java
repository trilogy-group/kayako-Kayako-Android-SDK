package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import java.io.File;

public class AttachmentFileTypeTest {

    private static final String CAPTION = "file type test";

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void givenNullFileWhenConstructorThenAssertionError() {
        thrown.expect(AssertionError.class);
        AttachmentFileType attachmentFileType = new AttachmentFileType(1L, null, CAPTION);
    }

    @Test
    public void givenValidParamsWhenConstructorThenObjectCreated() {
        File file = new File("");
        AttachmentFileType attachmentFileType = new AttachmentFileType(1L, file, CAPTION);
        errorCollector.checkThat(attachmentFileType.getId(), is(1L));
        errorCollector.checkThat(attachmentFileType.getCaption(), is(equalTo(CAPTION)));
        errorCollector.checkThat(attachmentFileType.getThumbnailFile(), is(file));
        errorCollector.checkThat(attachmentFileType.getType(), is(AttachmentFileType.TYPE.FILE));
    }
}
