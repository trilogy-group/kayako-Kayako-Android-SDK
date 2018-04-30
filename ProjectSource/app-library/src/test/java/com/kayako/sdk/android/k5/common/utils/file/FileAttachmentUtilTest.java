package com.kayako.sdk.android.k5.common.utils.file;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.Toast;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.utils.ViewUtils;
import com.kayako.sdk.android.k5.core.Kayako;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        Intent.class,
        FileAttachmentUtil.class,
        FileAccessPermissions.class,
        ViewUtils.class,
        Kayako.class,
        FileStorageUtil.class,
        FileAttachment.class,
        Uri.class,
        TextUtils.class,
        Toast.class
})
public class FileAttachmentUtilTest {
    private static final int REQUEST_CODE = 1;
    private static final String FILE_TYPE = "*/*";
    private static final String IMAGE_TYPE = "image/*";
    private static final String PERMISSION_NOT_FOUND_MESSAGE = "Permissions Not Found!";
    private static final String[] PERMISSIONS = new String[]{""};
    private static final int[] GRANT_RESULTS = new int[]{1};
    private static final int RESULT_CODE = -1;
    private static final String FILE_SCHEME = "file";
    private static final String FILE_NAME = "test.xml";
    private static final int BUFFER_SIZE = 1024 * 4; // 4 KB
    private static final byte[] BUFFER = new byte[BUFFER_SIZE];
    private static final String MIME_TYPE = "text";
    private static final int STATUS_SUCCESS = 1;
    private static final int STATUS_FAIL_NO_HANDLER = 2;
    private static final int STATUS_FAIL_INVALID_FILE = 3;
    private static final String KEY = "key";
    private static final String CONTENT_SCHEME = "content";
    private static List<File> fileList;
    private static File testFile;

    @Mock
    private Fragment fragment;

    @Mock
    private Intent intent;

    @Mock
    private Activity activity;

    @Mock
    private Resources resources;

    @Mock
    private Context context;

    @Mock
    private Uri uri;

    @Mock
    private FileAttachment fileAttachment;

    @Mock
    private ContentResolver contentResolver;

    @Mock
    private InputStream inputStream;

    @Mock
    private Cursor cursor;

    @Mock
    private Toast toast;

    @Rule
    private final TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUpClass() throws IOException {
        testFile = folder.newFile(FILE_NAME);
    }

    @Test
    public void givenFragmentWhenFileChooserActivityThenStartActivityForResult()
            throws Exception {
        //Arrange
        whenNew(Intent.class).withAnyArguments().thenReturn(intent);
        when(intent.setType(FILE_TYPE)).thenReturn(intent);
        when(intent.addCategory(Intent.CATEGORY_OPENABLE))
                .thenReturn(intent);

        //Act
        FileAttachmentUtil.openFileChooserActivityFromFragment
                (fragment, REQUEST_CODE);

        //Assert
        verify(fragment).startActivityForResult(intent, REQUEST_CODE);
    }

    @Test
    public void givenFragmentWhenImageChooserActivityThenStartActivityForResult()
            throws Exception {
        //Arrange
        whenNew(Intent.class).withAnyArguments().thenReturn(intent);
        when(intent.setType(IMAGE_TYPE)).thenReturn(intent);
        when(intent.addCategory(Intent.CATEGORY_OPENABLE))
                .thenReturn(intent);

        //Act
        FileAttachmentUtil.openImageChooserActivityFromFragment
                (fragment, REQUEST_CODE);

        //Assert
        verify(fragment).startActivityForResult(intent, REQUEST_CODE);
    }

    @Test
    public void givenActivityWhenCheckFileAccessPermissionsThenReturnBoolean() {
        //Arrange
        mockStatic(FileAccessPermissions.class);
        when(FileAccessPermissions.isPermitted(activity))
                .thenReturn(Boolean.FALSE);
        mockStatic(ViewUtils.class);
        when(activity.getResources()).thenReturn(resources);
        when(resources
                .getString(R.string.ko__permission_request_to_access_attachments))
                .thenReturn(PERMISSION_NOT_FOUND_MESSAGE);

        //Act
        boolean returnedValue = FileAttachmentUtil
                .checkFileAccessPermissions(activity);

        //Assert
        assertFalse(returnedValue);
    }

    @Test
    public void givenActivityWhenRequestPermissionsThenCallOnRequestPermissionsResult() {
        //Arrange
        mockStatic(FileAccessPermissions.class);

        //Act
        FileAttachmentUtil.onRequestPermissionsResult
                (activity, REQUEST_CODE, PERMISSIONS, GRANT_RESULTS);

        //Assert
        verifyStatic(FileAccessPermissions.class);
        FileAccessPermissions.onRequestPermissionsResult
                (activity, REQUEST_CODE, PERMISSIONS, GRANT_RESULTS);
    }

    @Test
    public void givenActivityWhenFileChooserActivityThenStartActivityForResult()
            throws Exception {
        //Arrange
        whenNew(Intent.class).withAnyArguments().thenReturn(intent);
        when(intent.setType(FILE_TYPE)).thenReturn(intent);
        when(intent.addCategory(Intent.CATEGORY_OPENABLE))
                .thenReturn(intent);

        //Act
        FileAttachmentUtil
                .openFileChooserActivityFromActivity(activity, REQUEST_CODE);

        //Assert
        verify(activity).startActivityForResult(intent, REQUEST_CODE);
    }

    @Test
    public void givenResultCodeWhenGetFileOnActivityResultThenReturnFile()
            throws Exception {
        //Arrange
        mockStatic(Kayako.class);
        when(Kayako.getApplicationContext()).thenReturn(context);
        when(intent.getData()).thenReturn(uri);
        when(uri.getScheme()).thenReturn(FILE_SCHEME);
        when(uri.getLastPathSegment()).thenReturn(FILE_NAME);
        doReturn(contentResolver).when(context)
                .getContentResolver();
        when(inputStream, "read", eq(BUFFER))
                .thenReturn(RESULT_CODE);
        when(contentResolver, "openInputStream", uri)
                .thenReturn(inputStream);
        when(context, "getFileStreamPath", contains(FILE_NAME))
                .thenReturn(testFile);

        //Act
        File returnedFile = FileAttachmentUtil
                .getFileOnActivityResult(RESULT_CODE, intent);

        //Assert
        assertTrue(returnedFile.isFile());
    }

    @Test
    public void givenContextWhenClearSavedAttachmentsThenCallDeletedSavedFiles()
            throws Exception {
        //Arrange
        mockStatic(Kayako.class);
        when(Kayako.getApplicationContext()).thenReturn(context);
        mockStatic(FileStorageUtil.class);
        when(FileStorageUtil.deleteSavedFiles(context))
                .thenReturn(Boolean.TRUE);

        //Act
        FileAttachmentUtil.clearSavedAttachments();

        //Assert
        verifyStatic(FileStorageUtil.class);
        FileStorageUtil.deleteSavedFiles(context);
    }

    @Test
    public void givenContextAndFileNameWhenClearSavedAttachmentsThenCallDeletedSavedFiles()
            throws Exception {
        //Arrange
        mockStatic(Kayako.class);
        when(Kayako.getApplicationContext())
                .thenReturn(context);
        mockStatic(FileStorageUtil.class);
        when(FileStorageUtil.deleteSavedFiles(context))
                .thenReturn(Boolean.TRUE);

        //Act
        FileAttachmentUtil.clearSavedAttachment(testFile);

        //Assert
        verifyStatic(FileStorageUtil.class);
        FileStorageUtil.deleteSavedFile(context, FILE_NAME);
    }

    @Test
    public void givenFileAttachmentWhenGetFileThenReturnFile() {
        //Arrange
        when(fileAttachment.getPath()).thenReturn(FILE_NAME);

        //Act
        File returnedFile = FileAttachmentUtil
                .getFile(fileAttachment);

        //Assert
        assertNotNull(returnedFile);
    }

    @Test
    public void givenFileWhenGetFileOpenIntentThenReturnIntent()
            throws Exception {
        //Arrange
        mockStatic(Uri.class);
        when(Uri.fromFile(testFile)).thenReturn(uri);
        mockStatic(FileStorageUtil.class);
        when(FileStorageUtil.getMimeType(testFile))
                .thenReturn(MIME_TYPE);
        whenNew(Intent.class).withAnyArguments()
                .thenReturn(intent);
        when(intent.setDataAndType(uri, MIME_TYPE))
                .thenReturn(intent);
        when(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                .thenReturn(intent);

        //Act
        Intent returnedValue = FileAttachmentUtil
                .getFileOpenIntent(testFile);

        //Assert
        assertEquals(intent, returnedValue);
    }

    @Test
    public void givenContextFileAttachmentWhenOpenFileAttachmentThenReturnInt() {
        //Arrange
        when(fileAttachment.getPath()).thenReturn(FILE_NAME);
        mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(FILE_NAME)).thenReturn(Boolean.TRUE);

        //Act
        int returnedValue = FileAttachmentUtil
                .openFileAttachment(context, fileAttachment);

        //Assert
        assertEquals(STATUS_FAIL_INVALID_FILE, returnedValue);
    }

    @Test
    public void givenContextFileWhenOpenFileThenReturnInt() {
        //Arrange
        mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(testFile.getPath()))
                .thenReturn(Boolean.TRUE);

        //Act
        int returnedValue = FileAttachmentUtil
                .openFile(context, testFile);

        //Assert
        assertEquals(STATUS_FAIL_INVALID_FILE, returnedValue);
    }

    @Test
    public void givenContextIntentWhenOpenFileIntentThenReturnInt() {
        //Act
        int returnedValue = FileAttachmentUtil
                .openFileIntent(context, intent);

        //Assert
        assertEquals(STATUS_SUCCESS, returnedValue);
    }

    @Test
    public void givenKeyFileWhenGenerateFileAttachmentThenReturnFileAttachment()
            throws Exception {
        //Arrange
        whenNew(FileAttachment.class).withAnyArguments()
                .thenReturn(fileAttachment);

        //Act
        FileAttachment returnedValue = FileAttachmentUtil
                .generateFileAttachment(KEY, testFile);

        //Assert
        assertEquals(fileAttachment, returnedValue);
    }

    @Test
    public void givenResultCodeWhenGetFileOnActivityResultContentThenReturnFile()
            throws Exception {
        //Arrange
        mockStatic(Kayako.class);
        when(Kayako.getApplicationContext()).thenReturn(context);
        when(intent.getData()).thenReturn(uri);
        when(uri.getScheme()).thenReturn(CONTENT_SCHEME);
        when(uri.getLastPathSegment()).thenReturn(FILE_NAME);
        doReturn(contentResolver).when(context).getContentResolver();
        when(inputStream, "read", eq(BUFFER))
                .thenReturn(RESULT_CODE);
        when(contentResolver, "openInputStream", uri)
                .thenReturn(inputStream);
        when(context, "getFileStreamPath", contains(FILE_NAME))
                .thenReturn(testFile);
        String[] proj = {OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE};
        when(contentResolver,
                "query", uri, proj, null, null, null)
                .thenReturn(cursor);
        when(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                .thenReturn(REQUEST_CODE);
        when(cursor.getString(REQUEST_CODE)).thenReturn(FILE_NAME);

        //Act
        File returnedFile = FileAttachmentUtil
                .getFileOnActivityResult(RESULT_CODE, intent);

        //Assert
        assertTrue(returnedFile.isFile());
    }

    @Test
    public void givenKeyFilesListWhenConvertFilesThenReturnFileAttachmentList()
            throws Exception {
        //Arrange
        fileList = new ArrayList<>();
        fileList.add(testFile);
        List<FileAttachment> expectedValue = new ArrayList<>();
        expectedValue.add(fileAttachment);
        whenNew(FileAttachment.class).withAnyArguments()
                .thenReturn(fileAttachment);

        //Act
        List<FileAttachment> returnedValue = FileAttachmentUtil
                .convertFiles(KEY, fileList);

        //Assert
        assertEquals(expectedValue, returnedValue);
    }

    @Test
    public void givenFileAttachmentListWhenConvertFilesThenReturnFileList() {
        //Arrange
        fileList = new ArrayList<>();
        fileList.add(testFile);
        List<FileAttachment> testList = new ArrayList<>();
        testList.add(fileAttachment);
        when(fileAttachment.getFile()).thenReturn(testFile);

        //Act
        List<File> returnedValue = FileAttachmentUtil
                .convertFiles(testList);

        //Assert
        assertEquals(fileList, returnedValue);
    }

    @Test
    public void givenFileAttachmentListWhenRemoveFilesThenCallDeleteSavedFile() {
        //Arrange
        List<FileAttachment> testList = new ArrayList<>();
        testList.add(fileAttachment);
        when(fileAttachment.getFile()).thenReturn(testFile);
        mockStatic(Kayako.class);
        when(Kayako.getApplicationContext()).thenReturn(context);
        mockStatic(FileStorageUtil.class);
        when(FileStorageUtil.isExisting(testFile)).thenReturn(Boolean.TRUE);

        //Act
        FileAttachmentUtil.removeFiles(testList);

        //Assert
        verifyStatic(FileStorageUtil.class);
        FileStorageUtil.deleteSavedFile(context, fileAttachment.getName());
    }

    @Test
    public void giveContextStatusWhenShowErrorMessagesThenCallToastMakeText() {
        //Arrange
        mockStatic(Toast.class);
        when(Toast.makeText(context,
                R.string.ko__attachment_msg_unable_to_open_file, Toast.LENGTH_LONG))
                .thenReturn(toast);

        //Act
        FileAttachmentUtil.showErrorMessages(context, STATUS_FAIL_INVALID_FILE);

        //Assert
        verify(toast).show();
    }

    @Test
    public void giveContextStatusWhenShowErrorMessagesThenCallToastMakeTextHandlerError() {
        //Arrange
        mockStatic(Toast.class);
        when(Toast.makeText(context,
                R.string.ko__attachment_msg_no_handler_for_file_type, Toast.LENGTH_LONG))
                .thenReturn(toast);

        //Act
        FileAttachmentUtil.showErrorMessages(context, STATUS_FAIL_NO_HANDLER);

        //Assert
        verify(toast).show();
    }

    @After
    public void validate() {
        validateMockitoUsage();
    }
}
