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

    @Test
    public void givenContextWhenCheckDownloadStatusThenReturnString()
            throws Exception {
        //Arrange
        when(context.getSystemService(Context.DOWNLOAD_SERVICE))
                .thenReturn(downloadManager);
        whenNew(DownloadManager.Query.class).withNoArguments()
                .thenReturn(query);
        doReturn(null).when(query).setFilterById(DOWNLOAD_ID);
        when(downloadManager.query(query)).thenReturn(cursor);
        when(cursor.moveToFirst()).thenReturn(Boolean.TRUE);
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
}
