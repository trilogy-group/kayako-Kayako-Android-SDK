package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
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
        final Matcher<String> nullMatcher = new IsNull<>();
        thrown.expect(AssertionError.class);
        thrown.expectMessage(nullMatcher);
        new AttachmentFileType(1L, null, CAPTION);
    }

    @Test
    public void givenValidParamsWhenConstructorThenObjectCreated() {
        //Arrange
        File file = new File("");

        //Act
        final AttachmentFileType attachmentFileType = new AttachmentFileType(1L, file, CAPTION);

        //Assert
        errorCollector.checkThat(attachmentFileType.getId(), is(1L));
        errorCollector.checkThat(attachmentFileType.getCaption(), is(CAPTION));
        errorCollector.checkThat(attachmentFileType.getThumbnailFile(), is(file));
        errorCollector.checkThat(attachmentFileType.getType(), is(AttachmentFileType.TYPE.FILE));
    }
}
