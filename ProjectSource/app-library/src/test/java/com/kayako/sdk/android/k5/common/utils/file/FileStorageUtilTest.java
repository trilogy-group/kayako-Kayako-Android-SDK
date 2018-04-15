package com.kayako.sdk.android.k5.common.utils.file;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.doReturn;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        MimeTypeMap.class,
        TextUtils.class,
        Uri.class,
        InputStream.class
})
public class FileStorageUtilTest {
    private final static String TEST_FILE_NAME = "Not Valid File Name $%&$%/(.txt";
    private final static String CORRECT_TEST_FILE_NAME = "NotValidFileName.txt";
    private static final String ATTACHMENT_NAME_PREFIX = "attachment__";
    private static File testFile;

    @Mock
    Context context;

    @Mock
    ContentResolver contentResolver;

    @Mock
    InputStream inputStream;

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUpClass() throws IOException {
        testFile = folder.newFile(CORRECT_TEST_FILE_NAME);
    }

    @Test
    public void verifyIfPurifyFileName(){
        String resultedFileName = FileStorageUtil.purify(TEST_FILE_NAME);
        assertEquals(CORRECT_TEST_FILE_NAME,resultedFileName);
    }

    @Test
    public void verifyIfReturnsCorrectFilePath(){
        String returnedFilePath = FileStorageUtil.getFilePath(testFile);
        assertTrue(new File(returnedFilePath).exists());
    }

    @Test
    public void verifyIfReturnFileSize() {
        Long returnedSize = FileStorageUtil.getFileSize(testFile);
        assertNotNull(returnedSize);
    }

    @Test
    public void verifyIfReturningCorrectFileExtension() {
        PowerMockito.mockStatic(MimeTypeMap.class);
        PowerMockito.when(MimeTypeMap.getFileExtensionFromUrl(testFile.getAbsolutePath())).thenReturn("txt");
        String returnedFileExtension = FileStorageUtil.getFileExtension(testFile);
        assertEquals("txt", returnedFileExtension);
    }

    @Test
    public void verifyIfVerifyIfGetMimeTypeCorrectly() throws Exception {
        MimeTypeMap mockHelper = Whitebox.invokeConstructor(MimeTypeMap.class);
        PowerMockito.mockStatic(MimeTypeMap.class);
        PowerMockito.when(MimeTypeMap.getFileExtensionFromUrl(testFile.getAbsolutePath())).thenReturn("jpg");
        PowerMockito.when(MimeTypeMap.getSingleton()).thenReturn(mockHelper);
        PowerMockito.stub(PowerMockito.method(MimeTypeMap.class, "getMimeTypeFromExtension", String.class)).toReturn("image/jpg");
        PowerMockito.mockStatic(TextUtils.class);
        PowerMockito.stub(PowerMockito.method(TextUtils.class, "isEmpty", String.class)).toReturn(false);
        String returnedMimeType = FileStorageUtil.getMimeType(testFile);
        assertEquals("image/jpg", returnedMimeType);
    }

    @Test
    public void verifyIfReturningCorrectFileName() {
        String returnedFileName = FileStorageUtil.getFileName(testFile);
        assertEquals(CORRECT_TEST_FILE_NAME, returnedFileName);
    }

    @Test
    public void verifyIfSaveFileCorrectly() throws Exception {
        PowerMockito.mockStatic(Uri.class);
        Uri uri = mock(Uri.class);
        PowerMockito.when(Uri.class, "parse", anyString()).thenReturn(uri);
        doReturn(contentResolver).when(context).getContentResolver();
        PowerMockito.when(inputStream, "read",any()).thenReturn(-1);
        PowerMockito.when(contentResolver,"openInputStream",uri).thenReturn(inputStream);
        PowerMockito.when(context,"getFileStreamPath",anyString()).thenReturn(testFile);
        File saveFile = FileStorageUtil.saveFile(context, Uri.parse(new java.net.URI("").toString()),CORRECT_TEST_FILE_NAME);
        assertTrue(saveFile.exists());
    }

    @Test
    public void verifyIfDeleteFilesCorrectly() throws Exception {
        PowerMockito.when(context,"fileList").thenReturn(new String[]{ATTACHMENT_NAME_PREFIX+CORRECT_TEST_FILE_NAME});
        assertTrue(FileStorageUtil.deleteSavedFiles(context));
    }

    @Test
    public void verifyIfValidatingImageCorrectly() throws Exception {
        MimeTypeMap mockHelper = Whitebox.invokeConstructor(MimeTypeMap.class);
        PowerMockito.mockStatic(MimeTypeMap.class);
        PowerMockito.when(MimeTypeMap.getFileExtensionFromUrl(testFile.getAbsolutePath())).thenReturn("jpg");
        PowerMockito.when(MimeTypeMap.getSingleton()).thenReturn(mockHelper);
        PowerMockito.stub(PowerMockito.method(MimeTypeMap.class, "getMimeTypeFromExtension", String.class)).toReturn("image/jpg");
        PowerMockito.mockStatic(TextUtils.class);
        PowerMockito.stub(PowerMockito.method(TextUtils.class, "isEmpty", String.class)).toReturn(false);
        assertTrue(FileStorageUtil.isImage(testFile));
    }

    @Test
    public void verifyIfVerifyingExistingCorrectly(){
        assertTrue(FileStorageUtil.isExisting(testFile));
    }
}
