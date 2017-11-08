package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.AttachmentUrlType;
import com.kayako.sdk.android.k5.common.utils.file.FileDownloadUtil;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.core.MessengerPref;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FileAttachmentDownloadHelper {

    private DownloadManager mDownloadManager;
    private Set<Long> mDownloadedLinks = new HashSet<>();
    private Map<String, Long> mMapUrlToDownloadId = new HashMap<>();

    /**
     * @param attachment
     * @param forceDownload true, if it should always start download, false, if it opens an already downloaded file
     */
    public void onClickAttachmentToDownload(DownloadAttachment attachment, boolean forceDownload) {
        if (attachment == null) {
            throw new IllegalStateException();
        }
        Long downloadId = mMapUrlToDownloadId.get(attachment.getDownloadUrl());
        if (downloadId == null) {
            downloadId = 0L;
        }

        Context context = Kayako.getInstance().getApplicationContext();

        if (!forceDownload && mDownloadedLinks.contains(downloadId) && mDownloadManager != null && mDownloadManager.getUriForDownloadedFile(downloadId) != null) { // if file has already downloaded
            Intent intent = new Intent();
            intent.setData(mDownloadManager.getUriForDownloadedFile(downloadId));
            intent.setType(mDownloadManager.getMimeTypeForDownloadedFile(downloadId));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } else if (!forceDownload && mDownloadedLinks.contains(downloadId) && mDownloadManager != null && mDownloadManager.getUriForDownloadedFile(downloadId) == null) { // if file is downloading
            Toast.makeText(context, FileDownloadUtil.checkDownloadStatus(context, downloadId), Toast.LENGTH_SHORT).show();

        } else { // if file download has not been initiated yet
            startReceiver();
            startDownload(attachment);
            Toast.makeText(context, context.getString(R.string.ko__attachment_msg_download_running), Toast.LENGTH_SHORT).show();
        }
    }

    private void startDownload(DownloadAttachment attachment) {
        Context context = Kayako.getApplicationContext();
        String fingerprintId = MessengerPref.getInstance().getFingerprintId();

        mDownloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        long downloadId = FileDownloadUtil.downloadFile(context,
                mDownloadManager,
                fingerprintId,
                attachment.getDownloadUrl(),
                attachment.getFileName(),
                attachment.getFileSize());

        mDownloadedLinks.add(downloadId);

        mMapUrlToDownloadId.put(attachment.getDownloadUrl(), downloadId);
    }

    private void startReceiver() {
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);

                // Check added because every case post would have it's own receiver
                if (mDownloadedLinks.contains(downloadId)) {
                    Toast.makeText(context, FileDownloadUtil.checkDownloadStatus(context, downloadId), Toast.LENGTH_SHORT).show();
                }
            }
        };

        Context context = Kayako.getApplicationContext();
        context.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public static class DownloadAttachment {

        @NonNull
        private String fileName;

        @NonNull
        private long fileSize;

        @NonNull
        private String downloadUrl;

        public DownloadAttachment(@NonNull AttachmentUrlType attachmentUrlType) {
            this(attachmentUrlType.getFileName(), attachmentUrlType.getFileSize(), attachmentUrlType.getDownloadUrl());
        }

        public DownloadAttachment(@NonNull String fileName, @NonNull Long fileSize, @NonNull String downloadUrl) {
            this.fileName = fileName;
            this.fileSize = fileSize;
            this.downloadUrl = downloadUrl;

            if (fileName == null || fileSize == null || downloadUrl == null) {
                throw new IllegalStateException();
            }
        }

        @NonNull
        public String getFileName() {
            return fileName;
        }

        @NonNull
        public long getFileSize() {
            return fileSize;
        }

        @NonNull
        public String getDownloadUrl() {
            return downloadUrl;
        }
    }
}
