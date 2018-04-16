package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.auth.Auth;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.hamcrest.CoreMatchers.is;

@RunWith(MockitoJUnitRunner.class)
public class DownloadAttachmentTest {

    private static final String FILE_NAME = "abc.txt";
    private static final long FILE_SIZE = 56_987L;
    private static final String DOWNLOAD_URL = "/getFile";
    private final Matcher<String> nullMatcher = new IsNull<>();

    @Mock
    private Auth auth;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void whenFileNameNullThenIllegalStateException() {
        final String newFileName = null;
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage(nullMatcher);
        new DownloadAttachment(newFileName, FILE_SIZE, DOWNLOAD_URL, auth);
    }

    @Test
    public void whenDownloadUrlNullThenIllegalStateException() {
        final String newDownloadUrl = null;
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage(nullMatcher);
        new DownloadAttachment(FILE_NAME, FILE_SIZE, newDownloadUrl, auth);
    }

    @Test
    public void whenValidParamsThenObjectCreated() {
        final DownloadAttachment downloadAttachment =
                new DownloadAttachment(FILE_NAME, FILE_SIZE, DOWNLOAD_URL, auth);
        errorCollector.checkThat(downloadAttachment.getFileName(), is(FILE_NAME));
        errorCollector.checkThat(downloadAttachment.getFileSize(), is(FILE_SIZE));
        errorCollector.checkThat(downloadAttachment.getDownloadUrl(), is(DOWNLOAD_URL));
        errorCollector.checkThat(downloadAttachment.getAuth(), is(auth));
    }
}
