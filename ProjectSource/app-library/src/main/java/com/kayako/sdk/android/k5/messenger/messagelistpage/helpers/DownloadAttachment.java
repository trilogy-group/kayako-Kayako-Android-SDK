package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kayako.sdk.auth.Auth;

public class DownloadAttachment {

    @NonNull
    private String fileName;

    @NonNull
    private long fileSize;

    @NonNull
    private String downloadUrl;

    @Nullable
    private Auth auth;

    public DownloadAttachment(@NonNull String fileName, @NonNull Long fileSize, @NonNull String downloadUrl, @Nullable Auth auth) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.downloadUrl = downloadUrl;
        this.auth = auth;

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

    @Nullable
    public Auth getAuth() {
        return auth;
    }
}