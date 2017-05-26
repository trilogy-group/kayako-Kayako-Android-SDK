package com.kayako.sdk.android.k5.common.utils.file;

import android.support.annotation.NonNull;

import java.io.File;

public class FileAttachment {

    private String key; // key required by Kayako API

    private String path; // path to file

    private File file;

    public FileAttachment(@NonNull String key, @NonNull File file) {
        this.key = key;
        this.file = file;
        this.path = file.getPath();

        if (!new File(path).exists()) {
            throw new AssertionError("File does not exist!");
        }
    }


    public FileAttachment(@NonNull String key, @NonNull String path) {
        this.key = key;
        this.path = path;

        if (!new File(path).exists()) {
            throw new AssertionError("File does not exist!");
        }
    }

    public File getFile() {
        if (file == null) {
            file = FileAttachmentUtil.getFile(this);
        }
        return file;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        if (getFile() != null) {
            return FileStorageUtil.getFileName(getFile());
        } else {
            return null;
        }
    }

    public boolean isImage() {
        return FileStorageUtil.isImage(getFile());
    }

    public String getExtension() {
        if (getFile() != null) {
            return FileStorageUtil.getFileExtension(getFile());
        } else {
            return "";
        }
    }

    public Long getSizeInBytes() {
        if (getFile() != null) {
            return FileStorageUtil.getFileSize(getFile());
        } else {
            return null;
        }
    }

    public String getFormattedSize() {
        return FileFormatUtil.formatFileSize(getSizeInBytes());
    }

    public String getMimeType() {
        if (getFile() != null) {
            return FileStorageUtil.getMimeType(getFile());
        } else {
            return null;
        }
    }

    public String getKey() {
        return key;
    }
}
