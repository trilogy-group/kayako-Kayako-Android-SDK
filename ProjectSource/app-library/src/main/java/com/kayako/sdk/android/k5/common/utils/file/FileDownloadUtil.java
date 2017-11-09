package com.kayako.sdk.android.k5.common.utils.file;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.format.Formatter;

import com.kayako.sdk.android.k5.R;

import java.util.Map;

public class FileDownloadUtil {

    /**
     * Returns a message that tells the status of the download.
     */
    public static String checkDownloadStatus(@NonNull Context context, long downloadId) {
        StringBuffer message = new StringBuffer();
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
            int status = cursor.getInt(columnIndex);
            int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
            int reason = cursor.getInt(columnReason);

            switch (status) {
                case DownloadManager.STATUS_FAILED:
                    message.append(context.getString(R.string.ko__attachment_msg_download_failed));
                    switch (reason) {
                        case DownloadManager.ERROR_CANNOT_RESUME:
                            message.append("ERROR_CANNOT_RESUME");
                            break;
                        case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                            message.append("ERROR_DEVICE_NOT_FOUND");
                            break;
                        case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                            message.append("ERROR_FILE_ALREADY_EXISTS");
                            break;
                        case DownloadManager.ERROR_FILE_ERROR:
                            message.append("ERROR_FILE_ERROR");
                            break;
                        case DownloadManager.ERROR_HTTP_DATA_ERROR:
                            message.append("ERROR_HTTP_DATA_ERROR");
                            break;
                        case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                            message.append("ERROR_INSUFFICIENT_SPACE");
                            break;
                        case DownloadManager.ERROR_TOO_MANY_REDIRECTS:
                            message.append("ERROR_TOO_MANY_REDIRECTS");
                            break;
                        case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
                            message.append("ERROR_UNHANDLED_HTTP_CODE");
                            break;
                        case DownloadManager.ERROR_UNKNOWN:
                            message.append("ERROR_UNKNOWN");
                            break;
                    }
                    break;
                case DownloadManager.STATUS_PAUSED:
                    message.append(context.getString(R.string.ko__attachment_msg_download_paused));
                    switch (reason) {
                        case DownloadManager.PAUSED_QUEUED_FOR_WIFI:
                            message.append("PAUSED_QUEUED_FOR_WIFI");
                            break;
                        case DownloadManager.PAUSED_UNKNOWN:
                            message.append("PAUSED_UNKNOWN");
                            break;
                        case DownloadManager.PAUSED_WAITING_FOR_NETWORK:
                            message.append("PAUSED_WAITING_FOR_NETWORK");
                            break;
                        case DownloadManager.PAUSED_WAITING_TO_RETRY:
                            message.append("PAUSED_WAITING_TO_RETRY");
                            break;
                    }
                    break;
                case DownloadManager.STATUS_PENDING:
                    message.append(context.getString(R.string.ko__attachment_msg_download_pending));
                    break;
                case DownloadManager.STATUS_RUNNING:
                    message.append(context.getString(R.string.ko__attachment_msg_download_running));
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    message.append(context.getString(R.string.ko__attachment_msg_download_successful));
                    break;
            }
        }
        return message.toString();
    }

    /**
     * Check if file is existing
     *
     * @param context
     * @param downloadId
     * @return
     */
    public static boolean isExistingFile(@NonNull Context context, long downloadId) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
            int status = cursor.getInt(columnIndex);
            int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
            int reason = cursor.getInt(columnReason);
            if (status == DownloadManager.STATUS_FAILED && reason == DownloadManager.ERROR_FILE_ALREADY_EXISTS) {
                return true;
            }
        }
        return false;
    }

    /**
     * Download a file via Kayako APIs - with the proper request headers
     *
     * @param context
     * @param downloadManager
     * @param headers
     * @param urlDownload
     * @param attachmentName
     * @param size
     * @return
     */
    public static long downloadFile(@NonNull Context context, @NonNull DownloadManager downloadManager, Map<String, String> headers, String urlDownload, String attachmentName, long size) {
        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse(urlDownload));
        for (String key : headers.keySet()) {
            request.addRequestHeader(key, headers.get(key));
        }
        request.setTitle(attachmentName);
        request.setDescription(Formatter.formatFileSize(context, size));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        return downloadManager.enqueue(request);
    }

    public static long downloadFile(@NonNull Context context, @NonNull DownloadManager downloadManager, String urlDownload, String attachmentName, long size) {
        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse(urlDownload));
        request.setTitle(attachmentName);
        request.setDescription(Formatter.formatFileSize(context, size));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        return downloadManager.enqueue(request);
    }

}
