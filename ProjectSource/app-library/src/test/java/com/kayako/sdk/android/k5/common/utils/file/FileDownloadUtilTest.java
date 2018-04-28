package com.kayako.sdk.android.k5.common.utils.file;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.kayako.sdk.android.k5.R;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import android.text.format.Formatter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        DownloadManager.Query.class,
        FileDownloadUtil.class,
        Uri.class,
        Formatter.class
})
public class FileDownloadUtilTest {
    private static final long DOWNLOAD_ID = 1;
    private static final String SUCCESSFUL_STATUS = "Attachment download is successful";
    private static final int COLUMN_INDEX = 0;
    private static final int STATUS = 8;
    private static final Map<String, String> HEADERS = new HashMap<>();
    private static final String URL_DOWNLOAD = "downloadurl";
    private static final String ATTACHMENT_NAME = "testAttach";
    private static final long SIZE = 10;
    private static final String PAUSED_QUEUED_FOR_WIFI = "PAUSED_QUEUED_FOR_WIFI";
    private static final String PAUSED_UNKNOWN = "PAUSED_UNKNOWN";
    private static final String PAUSED_WAITING_FOR_NETWORK = "PAUSED_WAITING_FOR_NETWORK";
    private static final String PAUSED_WAITING_TO_RETRY = "PAUSED_WAITING_TO_RETRY";
    private static final String ERROR_CANNOT_RESUME = "ERROR_CANNOT_RESUME";
    private static final String ERROR_DEVICE_NOT_FOUND = "ERROR_DEVICE_NOT_FOUND";
    private static final String ERROR_FILE_ALREADY_EXISTS = "ERROR_FILE_ALREADY_EXISTS";
    private static final String ERROR_FILE_ERROR = "ERROR_FILE_ERROR";
    private static final String ERROR_HTTP_DATA_ERROR = "ERROR_HTTP_DATA_ERROR";
    private static final String ERROR_INSUFFICIENT_SPACE = "ERROR_INSUFFICIENT_SPACE";
    private static final String ERROR_TOO_MANY_REDIRECTS = "ERROR_TOO_MANY_REDIRECTS";
    private static final String ERROR_UNHANDLED_HTTP_CODE = "ERROR_UNHANDLED_HTTP_CODE";
    private static final String ERROR_UNKNOWN = "ERROR_UNKNOWN";

    @Mock
    private Context context;
    @Mock
    private DownloadManager downloadManager;
    @Mock
    private DownloadManager.Query query;
    @Mock
    private Cursor cursor;
    @Mock
    private Uri uri;
    @Mock
    private DownloadManager.Request request;

    private void arrangeSwitchCases() throws Exception {
        when(context.getSystemService(Context.DOWNLOAD_SERVICE))
                .thenReturn(downloadManager);
        whenNew(DownloadManager.Query.class).withNoArguments()
                .thenReturn(query);
        doReturn(null).when(query).setFilterById(DOWNLOAD_ID);
        when(downloadManager.query(query)).thenReturn(cursor);
        when(cursor.moveToFirst()).thenReturn(Boolean.TRUE);
    }

    @Test
    public void givenContextWhenCheckDownloadStatusThenReturnString()
            throws Exception {
        //Arrange
        arrangeSwitchCases();
        when(cursor.getInt(COLUMN_INDEX)).thenReturn(STATUS);
        when(context.getString(R.string.ko__attachment_msg_download_successful))
                .thenReturn(SUCCESSFUL_STATUS);

        //Act
        String returnedValue = FileDownloadUtil
                .checkDownloadStatus(context, DOWNLOAD_ID);

        //Assert
        assertEquals(SUCCESSFUL_STATUS, returnedValue);
    }

    @Test
    public void givenDownloadIdWhenExistingFileThenReturnBoolean()
            throws Exception {
        //Arrange
        when(context.getSystemService(Context.DOWNLOAD_SERVICE))
                .thenReturn(downloadManager);
        whenNew(DownloadManager.Query.class).withNoArguments()
                .thenReturn(query);
        doReturn(null).when(query)
                .setFilterById(DOWNLOAD_ID);
        when(downloadManager.query(query)).thenReturn(cursor);
        when(cursor.moveToFirst()).thenReturn(Boolean.TRUE);
        when(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                .thenReturn(DownloadManager.STATUS_FAILED);
        when(cursor.getInt(DownloadManager.STATUS_FAILED))
                .thenReturn(DownloadManager.STATUS_FAILED);
        when(cursor.getColumnIndex(DownloadManager.COLUMN_REASON))
                .thenReturn(DownloadManager.ERROR_FILE_ALREADY_EXISTS);
        when(cursor.getInt(DownloadManager.ERROR_FILE_ALREADY_EXISTS))
                .thenReturn(DownloadManager.ERROR_FILE_ALREADY_EXISTS);

        //Act
        boolean returnedValue = FileDownloadUtil
                .isExistingFile(context, DOWNLOAD_ID);

        //Assert
        assertEquals(Boolean.TRUE, returnedValue);
    }

    @Test
    public void givenContextWhenDownloadFileThenReturnLong()
            throws Exception {
        //Arrange
        mockStatic(Uri.class);
        when(Uri.parse(URL_DOWNLOAD)).thenReturn(uri);
        whenNew(DownloadManager.Request.class).withAnyArguments()
                .thenReturn(request);
        when(downloadManager.enqueue(request)).thenReturn(DOWNLOAD_ID);
        mockStatic(Formatter.class);
        when(Formatter.formatFileSize(context, SIZE))
                .thenReturn(SUCCESSFUL_STATUS);

        //Act
        long returnValue = FileDownloadUtil
                .downloadFile(context, downloadManager, HEADERS,
                        URL_DOWNLOAD, ATTACHMENT_NAME, SIZE);

        //Assert
        assertEquals(DOWNLOAD_ID, returnValue);
    }

    @Test
    public void givenContextWithoutMapWhenDownloadFileThenReturnLong()
            throws Exception {
        //Arrange
        mockStatic(Uri.class);
        when(Uri.parse(URL_DOWNLOAD)).thenReturn(uri);
        whenNew(DownloadManager.Request.class).withAnyArguments()
                .thenReturn(request);
        when(downloadManager.enqueue(request)).thenReturn(DOWNLOAD_ID);
        mockStatic(Formatter.class);
        when(Formatter.formatFileSize(context, SIZE))
                .thenReturn(SUCCESSFUL_STATUS);

        //Act
        long returnValue = FileDownloadUtil
                .downloadFile(context, downloadManager, URL_DOWNLOAD,
                        ATTACHMENT_NAME, SIZE);

        //Assert
        assertEquals(DOWNLOAD_ID, returnValue);
    }

    @Test
    public void givenContextWhenCheckDownloadStatusThenReturnString2()
            throws Exception {
        //Arrange
        arrangeSwitchCases();
        when(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                .thenReturn(DownloadManager.STATUS_RUNNING);
        when(cursor.getInt(DownloadManager.STATUS_RUNNING))
                .thenReturn(DownloadManager.STATUS_RUNNING);
        when(context.getString(R.string.ko__attachment_msg_download_running))
                .thenReturn(SUCCESSFUL_STATUS);

        //Act
        String returnedValue = FileDownloadUtil
                .checkDownloadStatus(context, DOWNLOAD_ID);

        //Assert
        assertEquals(SUCCESSFUL_STATUS, returnedValue);
    }

    @Test
    public void givenContextWhenCheckDownloadStatusThenReturnString3()
            throws Exception {
        //Arrange
        arrangeSwitchCases();
        when(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                .thenReturn(DownloadManager.STATUS_PENDING);
        when(cursor.getInt(DownloadManager.STATUS_PENDING))
                .thenReturn(DownloadManager.STATUS_PENDING);
        when(context.getString(R.string.ko__attachment_msg_download_pending))
                .thenReturn(SUCCESSFUL_STATUS);

        //Act
        String returnedValue = FileDownloadUtil
                .checkDownloadStatus(context, DOWNLOAD_ID);

        //Assert
        assertEquals(SUCCESSFUL_STATUS, returnedValue);
    }

    @Test
    public void givenContextWhenCheckDownloadStatusThenReturnString4()
            throws Exception {
        //Arrange
        arrangeSwitchCases();
        when(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                .thenReturn(DownloadManager.STATUS_RUNNING);
        when(cursor.getInt(DownloadManager.STATUS_RUNNING))
                .thenReturn(DownloadManager.STATUS_RUNNING);
        when(context.getString(R.string.ko__attachment_msg_download_running))
                .thenReturn(SUCCESSFUL_STATUS);

        //Act
        String returnedValue = FileDownloadUtil
                .checkDownloadStatus(context, DOWNLOAD_ID);

        //Assert
        assertEquals(SUCCESSFUL_STATUS, returnedValue);
    }

    @Test
    public void givenContextWhenCheckDownloadStatusThenReturnString5()
            throws Exception {
        //Arrange
        arrangeSwitchCases();
        when(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                .thenReturn(DownloadManager.STATUS_PAUSED);
        when(cursor.getInt(DownloadManager.STATUS_PAUSED))
                .thenReturn(DownloadManager.STATUS_PAUSED);
        when(context.getString(R.string.ko__attachment_msg_download_paused))
                .thenReturn(SUCCESSFUL_STATUS);
        when(cursor.getColumnIndex(DownloadManager.COLUMN_REASON))
                .thenReturn(DownloadManager.PAUSED_QUEUED_FOR_WIFI);
        when(cursor.getInt(DownloadManager.PAUSED_QUEUED_FOR_WIFI))
                .thenReturn(DownloadManager.PAUSED_QUEUED_FOR_WIFI);
        //Act
        String returnedValue = FileDownloadUtil
                .checkDownloadStatus(context, DOWNLOAD_ID);

        //Assert
        assertEquals(SUCCESSFUL_STATUS+PAUSED_QUEUED_FOR_WIFI, returnedValue);
    }


    @Test
    public void givenContextWhenCheckDownloadStatusThenReturnString6()
            throws Exception {
        //Arrange
        arrangeSwitchCases();
        when(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                .thenReturn(DownloadManager.STATUS_PAUSED);
        when(cursor.getInt(DownloadManager.STATUS_PAUSED))
                .thenReturn(DownloadManager.STATUS_PAUSED);
        when(context.getString(R.string.ko__attachment_msg_download_paused))
                .thenReturn(SUCCESSFUL_STATUS);
        when(cursor.getColumnIndex(DownloadManager.COLUMN_REASON))
                .thenReturn(DownloadManager.PAUSED_UNKNOWN);
        when(cursor.getInt(DownloadManager.PAUSED_UNKNOWN))
                .thenReturn(DownloadManager.PAUSED_UNKNOWN);
        //Act
        String returnedValue = FileDownloadUtil
                .checkDownloadStatus(context, DOWNLOAD_ID);

        //Assert
        assertEquals(SUCCESSFUL_STATUS+PAUSED_UNKNOWN, returnedValue);
    }

    @Test
    public void givenContextWhenCheckDownloadStatusThenReturnString7()
            throws Exception {
        //Arrange
        arrangeSwitchCases();
        when(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                .thenReturn(DownloadManager.STATUS_PAUSED);
        when(cursor.getInt(DownloadManager.STATUS_PAUSED))
                .thenReturn(DownloadManager.STATUS_PAUSED);
        when(context.getString(R.string.ko__attachment_msg_download_paused))
                .thenReturn(SUCCESSFUL_STATUS);
        when(cursor.getColumnIndex(DownloadManager.COLUMN_REASON))
                .thenReturn(DownloadManager.PAUSED_WAITING_FOR_NETWORK);
        when(cursor.getInt(DownloadManager.PAUSED_WAITING_FOR_NETWORK))
                .thenReturn(DownloadManager.PAUSED_WAITING_FOR_NETWORK);
        //Act
        String returnedValue = FileDownloadUtil
                .checkDownloadStatus(context, DOWNLOAD_ID);

        //Assert
        assertEquals(SUCCESSFUL_STATUS+PAUSED_WAITING_FOR_NETWORK, returnedValue);
    }

    @Test
    public void givenContextWhenCheckDownloadStatusThenReturnString8()
            throws Exception {
        //Arrange
        arrangeSwitchCases();
        when(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                .thenReturn(DownloadManager.STATUS_PAUSED);
        when(cursor.getInt(DownloadManager.STATUS_PAUSED))
                .thenReturn(DownloadManager.STATUS_PAUSED);
        when(context.getString(R.string.ko__attachment_msg_download_paused))
                .thenReturn(SUCCESSFUL_STATUS);
        when(cursor.getColumnIndex(DownloadManager.COLUMN_REASON))
                .thenReturn(DownloadManager.PAUSED_WAITING_TO_RETRY);
        when(cursor.getInt(DownloadManager.PAUSED_WAITING_TO_RETRY))
                .thenReturn(DownloadManager.PAUSED_WAITING_TO_RETRY);
        //Act
        String returnedValue = FileDownloadUtil
                .checkDownloadStatus(context, DOWNLOAD_ID);

        //Assert
        assertEquals(SUCCESSFUL_STATUS+PAUSED_WAITING_TO_RETRY, returnedValue);
    }

    @Test
    public void givenContextWhenCheckDownloadStatusThenReturnString9()
            throws Exception {
        //Arrange
        arrangeSwitchCases();
        when(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                .thenReturn(DownloadManager.STATUS_FAILED);
        when(cursor.getInt(DownloadManager.STATUS_FAILED))
                .thenReturn(DownloadManager.STATUS_FAILED);
        when(context.getString(R.string.ko__attachment_msg_download_failed))
                .thenReturn(SUCCESSFUL_STATUS);
        when(cursor.getColumnIndex(DownloadManager.COLUMN_REASON))
                .thenReturn(DownloadManager.ERROR_CANNOT_RESUME);
        when(cursor.getInt(DownloadManager.ERROR_CANNOT_RESUME))
                .thenReturn(DownloadManager.ERROR_CANNOT_RESUME);
        //Act
        String returnedValue = FileDownloadUtil
                .checkDownloadStatus(context, DOWNLOAD_ID);

        //Assert
        assertEquals(SUCCESSFUL_STATUS+ERROR_CANNOT_RESUME, returnedValue);
    }

    @Test
    public void givenContextWhenCheckDownloadStatusThenReturnString10()
            throws Exception {
        //Arrange
        arrangeSwitchCases();
        when(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                .thenReturn(DownloadManager.STATUS_FAILED);
        when(cursor.getInt(DownloadManager.STATUS_FAILED))
                .thenReturn(DownloadManager.STATUS_FAILED);
        when(context.getString(R.string.ko__attachment_msg_download_failed))
                .thenReturn(SUCCESSFUL_STATUS);
        when(cursor.getColumnIndex(DownloadManager.COLUMN_REASON))
                .thenReturn(DownloadManager.ERROR_DEVICE_NOT_FOUND);
        when(cursor.getInt(DownloadManager.ERROR_DEVICE_NOT_FOUND))
                .thenReturn(DownloadManager.ERROR_DEVICE_NOT_FOUND);
        //Act
        String returnedValue = FileDownloadUtil
                .checkDownloadStatus(context, DOWNLOAD_ID);

        //Assert
        assertEquals(SUCCESSFUL_STATUS+ERROR_DEVICE_NOT_FOUND, returnedValue);
    }

    @Test
    public void givenContextWhenCheckDownloadStatusThenReturnString11()
            throws Exception {
        //Arrange
        arrangeSwitchCases();
        when(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                .thenReturn(DownloadManager.STATUS_FAILED);
        when(cursor.getInt(DownloadManager.STATUS_FAILED))
                .thenReturn(DownloadManager.STATUS_FAILED);
        when(context.getString(R.string.ko__attachment_msg_download_failed))
                .thenReturn(SUCCESSFUL_STATUS);
        when(cursor.getColumnIndex(DownloadManager.COLUMN_REASON))
                .thenReturn(DownloadManager.ERROR_FILE_ALREADY_EXISTS);
        when(cursor.getInt(DownloadManager.ERROR_FILE_ALREADY_EXISTS))
                .thenReturn(DownloadManager.ERROR_FILE_ALREADY_EXISTS);
        //Act
        String returnedValue = FileDownloadUtil
                .checkDownloadStatus(context, DOWNLOAD_ID);

        //Assert
        assertEquals(SUCCESSFUL_STATUS+ERROR_FILE_ALREADY_EXISTS, returnedValue);
    }

    @Test
    public void givenContextWhenCheckDownloadStatusThenReturnString12()
            throws Exception {
        //Arrange
        arrangeSwitchCases();
        when(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                .thenReturn(DownloadManager.STATUS_FAILED);
        when(cursor.getInt(DownloadManager.STATUS_FAILED))
                .thenReturn(DownloadManager.STATUS_FAILED);
        when(context.getString(R.string.ko__attachment_msg_download_failed))
                .thenReturn(SUCCESSFUL_STATUS);
        when(cursor.getColumnIndex(DownloadManager.COLUMN_REASON))
                .thenReturn(DownloadManager.ERROR_FILE_ERROR);
        when(cursor.getInt(DownloadManager.ERROR_FILE_ERROR))
                .thenReturn(DownloadManager.ERROR_FILE_ERROR);
        //Act
        String returnedValue = FileDownloadUtil
                .checkDownloadStatus(context, DOWNLOAD_ID);

        //Assert
        assertEquals(SUCCESSFUL_STATUS+ERROR_FILE_ERROR, returnedValue);
    }

    @Test
    public void givenContextWhenCheckDownloadStatusThenReturnString13()
            throws Exception {
        //Arrange
        arrangeSwitchCases();
        when(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                .thenReturn(DownloadManager.STATUS_FAILED);
        when(cursor.getInt(DownloadManager.STATUS_FAILED))
                .thenReturn(DownloadManager.STATUS_FAILED);
        when(context.getString(R.string.ko__attachment_msg_download_failed))
                .thenReturn(SUCCESSFUL_STATUS);
        when(cursor.getColumnIndex(DownloadManager.COLUMN_REASON))
                .thenReturn(DownloadManager.ERROR_HTTP_DATA_ERROR);
        when(cursor.getInt(DownloadManager.ERROR_HTTP_DATA_ERROR))
                .thenReturn(DownloadManager.ERROR_HTTP_DATA_ERROR);
        //Act
        String returnedValue = FileDownloadUtil
                .checkDownloadStatus(context, DOWNLOAD_ID);

        //Assert
        assertEquals(SUCCESSFUL_STATUS+ERROR_HTTP_DATA_ERROR, returnedValue);
    }

    @Test
    public void givenContextWhenCheckDownloadStatusThenReturnString14()
            throws Exception {
        //Arrange
        arrangeSwitchCases();
        when(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                .thenReturn(DownloadManager.STATUS_FAILED);
        when(cursor.getInt(DownloadManager.STATUS_FAILED))
                .thenReturn(DownloadManager.STATUS_FAILED);
        when(context.getString(R.string.ko__attachment_msg_download_failed))
                .thenReturn(SUCCESSFUL_STATUS);
        when(cursor.getColumnIndex(DownloadManager.COLUMN_REASON))
                .thenReturn(DownloadManager.ERROR_INSUFFICIENT_SPACE);
        when(cursor.getInt(DownloadManager.ERROR_INSUFFICIENT_SPACE))
                .thenReturn(DownloadManager.ERROR_INSUFFICIENT_SPACE);
        //Act
        String returnedValue = FileDownloadUtil
                .checkDownloadStatus(context, DOWNLOAD_ID);

        //Assert
        assertEquals(SUCCESSFUL_STATUS+ERROR_INSUFFICIENT_SPACE, returnedValue);
    }

    @Test
    public void givenContextWhenCheckDownloadStatusThenReturnString15()
            throws Exception {
        //Arrange
        arrangeSwitchCases();
        when(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                .thenReturn(DownloadManager.STATUS_FAILED);
        when(cursor.getInt(DownloadManager.STATUS_FAILED))
                .thenReturn(DownloadManager.STATUS_FAILED);
        when(context.getString(R.string.ko__attachment_msg_download_failed))
                .thenReturn(SUCCESSFUL_STATUS);
        when(cursor.getColumnIndex(DownloadManager.COLUMN_REASON))
                .thenReturn(DownloadManager.ERROR_TOO_MANY_REDIRECTS);
        when(cursor.getInt(DownloadManager.ERROR_TOO_MANY_REDIRECTS))
                .thenReturn(DownloadManager.ERROR_TOO_MANY_REDIRECTS);
        //Act
        String returnedValue = FileDownloadUtil
                .checkDownloadStatus(context, DOWNLOAD_ID);

        //Assert
        assertEquals(SUCCESSFUL_STATUS+ERROR_TOO_MANY_REDIRECTS, returnedValue);
    }

    @Test
    public void givenContextWhenCheckDownloadStatusThenReturnString16()
            throws Exception {
        //Arrange
        arrangeSwitchCases();
        when(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                .thenReturn(DownloadManager.STATUS_FAILED);
        when(cursor.getInt(DownloadManager.STATUS_FAILED))
                .thenReturn(DownloadManager.STATUS_FAILED);
        when(context.getString(R.string.ko__attachment_msg_download_failed))
                .thenReturn(SUCCESSFUL_STATUS);
        when(cursor.getColumnIndex(DownloadManager.COLUMN_REASON))
                .thenReturn(DownloadManager.ERROR_UNHANDLED_HTTP_CODE);
        when(cursor.getInt(DownloadManager.ERROR_UNHANDLED_HTTP_CODE))
                .thenReturn(DownloadManager.ERROR_UNHANDLED_HTTP_CODE);
        //Act
        String returnedValue = FileDownloadUtil
                .checkDownloadStatus(context, DOWNLOAD_ID);

        //Assert
        assertEquals(SUCCESSFUL_STATUS+ERROR_UNHANDLED_HTTP_CODE, returnedValue);
    }

    @Test
    public void givenContextWhenCheckDownloadStatusThenReturnString17()
            throws Exception {
        //Arrange
        arrangeSwitchCases();
        when(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                .thenReturn(DownloadManager.STATUS_FAILED);
        when(cursor.getInt(DownloadManager.STATUS_FAILED))
                .thenReturn(DownloadManager.STATUS_FAILED);
        when(context.getString(R.string.ko__attachment_msg_download_failed))
                .thenReturn(SUCCESSFUL_STATUS);
        when(cursor.getColumnIndex(DownloadManager.COLUMN_REASON))
                .thenReturn(DownloadManager.ERROR_UNKNOWN);
        when(cursor.getInt(DownloadManager.ERROR_UNKNOWN))
                .thenReturn(DownloadManager.ERROR_UNKNOWN);
        //Act
        String returnedValue = FileDownloadUtil
                .checkDownloadStatus(context, DOWNLOAD_ID);

        //Assert
        assertEquals(SUCCESSFUL_STATUS+ERROR_UNKNOWN, returnedValue);
    }
}
