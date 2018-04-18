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
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.stub;
import static org.powermock.api.mockito.PowerMockito.method;
import static org.powermock.api.mockito.PowerMockito.doReturn;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        MimeTypeMap.class,
        TextUtils.class,
        Uri.class,
        InputStream.class
})
public class FileStorageUtilTest {
    private static final String TEST_FILE_NAME = "Not Valid File Name $%&$%/(.txt";
    private static final String CORRECT_TEST_FILE_NAME = "NotValidFileName.txt";
    private static final String ATTACHMENT_NAME_PREFIX = "attachment__";
    private static final String JPG_MIME_TYPE = "image/jpg";
    private static final String TEST_FILE_EXTENSION = "txt";
    private static final String IMAGE_FILE_EXTENSION = "jpg";
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
    public void verifyIfPurifyFileName() {
        //Arrange
        String resultedFileName;

        //Act
        resultedFileName = FileStorageUtil.purify(TEST_FILE_NAME);

        //Assert
        assertEquals(CORRECT_TEST_FILE_NAME, resultedFileName);
    }

    @Test
    public void verifyIfReturnsCorrectFilePath() {
        //Arrange
        String returnedFilePath;

        //Act
        returnedFilePath = FileStorageUtil.getFilePath(testFile);

        //Assert
        assertTrue(new File(returnedFilePath).exists());
    }

    @Test
    public void verifyIfReturnFileSize() {
        //Arrange
        Long returnedSize;

        //Act
        returnedSize = FileStorageUtil.getFileSize(testFile);

        //Assert
        assertNotNull(returnedSize);
    }

    @Test
    public void verifyIfReturningCorrectFileExtension() {
        //Arrange
        String returnedFileExtension;
        mockStatic(MimeTypeMap.class);
        when(MimeTypeMap.getFileExtensionFromUrl(testFile.getAbsolutePath()))
                .thenReturn(TEST_FILE_EXTENSION);

        //Act
        returnedFileExtension = FileStorageUtil.getFileExtension(testFile);

        //Assert
        assertEquals("txt", returnedFileExtension);
    }

    @Test
    public void verifyIfVerifyIfGetMimeTypeCorrectly() throws Exception {
        //Arrange
        String returnedMimeType;
        MimeTypeMap mockHelper = Whitebox.invokeConstructor(MimeTypeMap.class);
        mockStatic(MimeTypeMap.class);
        when(MimeTypeMap.getFileExtensionFromUrl(testFile.getAbsolutePath()))
                .thenReturn(IMAGE_FILE_EXTENSION);
        when(MimeTypeMap.getSingleton()).thenReturn(mockHelper);
        stub(method(MimeTypeMap.class, "getMimeTypeFromExtension", String.class))
                .toReturn(JPG_MIME_TYPE);
        mockStatic(TextUtils.class);
        stub(method(TextUtils.class, "isEmpty", String.class))
                .toReturn(false);

        //Act
        returnedMimeType = FileStorageUtil.getMimeType(testFile);

        //Assert
        assertEquals(JPG_MIME_TYPE, returnedMimeType);
    }

    @Test
    public void verifyIfReturningCorrectFileName() {
        //Arrange
        String returnedFileName;

        //Act
        returnedFileName = FileStorageUtil.getFileName(testFile);

        //Assert
        assertEquals(CORRECT_TEST_FILE_NAME, returnedFileName);
    }

    @Test
    public void verifyIfSaveFileCorrectly() throws Exception {
        //Arrange
        File saveFile;
        mockStatic(Uri.class);
        Uri uri = mock(Uri.class);
        when(Uri.class, "parse", anyString()).thenReturn(uri);
        doReturn(contentResolver).when(context).getContentResolver();
        when(inputStream, "read", any()).thenReturn(-1);
        when(contentResolver, "openInputStream", uri).thenReturn(inputStream);
        when(context, "getFileStreamPath", anyString()).thenReturn(testFile);

        //Act
        saveFile = FileStorageUtil
                .saveFile(context, Uri.parse(new java.net.URI("").toString()), CORRECT_TEST_FILE_NAME);

        //Assert
        assertTrue(saveFile.isFile());
    }

    @Test
    public void verifyIfDeleteFilesCorrectly() throws Exception {
        //Arrange
        boolean deleted;
        when(context, "fileList")
                .thenReturn(new String[]{ATTACHMENT_NAME_PREFIX + CORRECT_TEST_FILE_NAME});

        //Act
        deleted = FileStorageUtil.deleteSavedFiles(context);

        //Assert
        assertTrue(deleted);
    }

    @Test
    public void verifyIfValidatingImageCorrectly() throws Exception {
        //Arrange
        boolean isImage;
        MimeTypeMap mockHelper = Whitebox.invokeConstructor(MimeTypeMap.class);
        mockStatic(MimeTypeMap.class);
        when(MimeTypeMap.getFileExtensionFromUrl(testFile.getAbsolutePath()))
                .thenReturn(IMAGE_FILE_EXTENSION);
        when(MimeTypeMap.getSingleton()).thenReturn(mockHelper);
        stub(method(MimeTypeMap.class, "getMimeTypeFromExtension", String.class))
                .toReturn(JPG_MIME_TYPE);
        mockStatic(TextUtils.class);
        stub(method(TextUtils.class, "isEmpty", String.class))
                .toReturn(false);

        //Act
        isImage = FileStorageUtil.isImage(testFile);

        //Assert
        assertTrue(isImage);
    }

    @Test
    public void verifyIfVerifyingExistingCorrectly() {
        //Arrange
        boolean fileExists;

        //Act
        fileExists = FileStorageUtil.isExisting(testFile);

        //Assert
        assertTrue(fileExists);
    }
}
