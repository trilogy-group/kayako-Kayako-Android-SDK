package com.kayako.sdk.android.k5.common.adapter.messengerlist;

public class Attachment {

    private Long id;
    private String thumbnailUrl;
    private String caption;

    private String fileName;
    private long fileSize;
    private String thumbnailType;
    private String downloadUrl;

    public Attachment(Long id, String thumbnailUrl, String caption) {
        assert thumbnailUrl != null;

        this.id = id;
        this.caption = caption;
        this.thumbnailUrl = thumbnailUrl;
    }

    public Attachment(String thumbnailUrl,String fileName, long fileSize, String thumbnailType, String downloadUrl) {
        this.thumbnailUrl = thumbnailUrl;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.thumbnailType = thumbnailType;
        this.downloadUrl = downloadUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getThumbnailType() {
        return thumbnailType;
    }

    public void setThumbnailType(String thumbnailType) {
        this.thumbnailType = thumbnailType;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}