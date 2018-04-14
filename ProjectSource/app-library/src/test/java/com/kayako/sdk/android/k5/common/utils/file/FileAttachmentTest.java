package com.kayako.sdk.android.k5.common.utils.file;

import android.text.TextUtils;
import android.webkit.MimeTypeMap;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MimeTypeMap.class, TextUtils.class})
public class FileAttachmentTest {
    private static final String TEST_API_KEY = "QWE";
    private static FileAttachment fileAttachment;
    private static final String TEST_FILE_NAME = "test.jpg";
    private static File testFile;


    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUpClass() throws IOException {
        testFile = folder.newFile(TEST_FILE_NAME);
        fileAttachment = new FileAttachment(TEST_API_KEY, testFile);
    }

    @Test
    public void verifyIfFileIsReturnAndExists() {
        File expectedFile = fileAttachment.getFile();

        assertTrue(expectedFile.exists());
    }

    @Test
    public void verifyIfReturningPathNotNull() {
        String expectedPath = fileAttachment.getPath();

        assertNotNull(expectedPath);
    }

    @Test
    public void verifyIfReturningCorrectFileName() {
        String returnedFileName = fileAttachment.getName();

        assertEquals(TEST_FILE_NAME, returnedFileName);

    }

    @Test
    public void verifyIfVerifyImageCorrectly() throws Exception {
        MimeTypeMap mockHelper = Whitebox.invokeConstructor(MimeTypeMap.class);
        PowerMockito.mockStatic(MimeTypeMap.class);
        PowerMockito.when(MimeTypeMap.getFileExtensionFromUrl(testFile.getAbsolutePath())).thenReturn("jpg");
        PowerMockito.when(MimeTypeMap.getSingleton()).thenReturn(mockHelper);
        PowerMockito.stub(PowerMockito.method(MimeTypeMap.class, "getMimeTypeFromExtension", String.class)).toReturn("image/jpg");
        PowerMockito.mockStatic(TextUtils.class);
        PowerMockito.stub(PowerMockito.method(TextUtils.class, "isEmpty", String.class)).toReturn(false);
        assertTrue(fileAttachment.isImage());
    }

    @Test
    public void verifyIfReturningCorrectFileExtension() {
        PowerMockito.mockStatic(MimeTypeMap.class);
        PowerMockito.when(MimeTypeMap.getFileExtensionFromUrl(testFile.getAbsolutePath())).thenReturn("jpg");
        String returnedFileExtension = fileAttachment.getExtension();

        assertEquals("jpg", returnedFileExtension);
    }

    @Test
    public void verifyIfReturnFileSize() {
        Long returnedSize = fileAttachment.getSizeInBytes();
        assertNotNull(returnedSize);
    }

    @Test
    public void verifyIfReturnedFormattedFileSize() {
        String returnedSize = fileAttachment.getFormattedSize();
        assertNotNull(returnedSize);
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
        String returnedMimeType = fileAttachment.getMimeType();
        assertEquals("image/jpg", returnedMimeType);
    }

    @After
    public void tearDownClassAndFiles() {
        folder.delete();
    }

}
