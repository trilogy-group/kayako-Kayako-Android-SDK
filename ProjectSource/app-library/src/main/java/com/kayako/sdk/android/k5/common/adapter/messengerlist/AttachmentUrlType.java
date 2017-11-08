package com.kayako.sdk.android.k5.common.adapter.messengerlist;

public class AttachmentUrlType extends Attachment {

    private String thumbnailUrl;
    private String originalImageUrl;

    private Long id;
    private String caption;

    private String fileName;
    private long fileSize;
    private String thumbnailType;
    private long timeCreated;
    private String downloadUrl;


    /**
     * Minimal contructor for attachments loaded over net
     *
     * @param id
     * @param thumbnailUrl
     * @param caption
     */
    public AttachmentUrlType(Long id, String thumbnailUrl, String caption) {
        super(TYPE.URL);
        assert thumbnailUrl != null;

        this.id = id;
        this.caption = caption;
        this.thumbnailUrl = thumbnailUrl;
    }

    /**
     * Constructor for atacchments loaded over net
     *
     * @param thumbnailUrl
     * @param fileName
     * @param fileSize
     * @param thumbnailType
     * @param downloadUrl
     */
    public AttachmentUrlType(String thumbnailUrl, String originalImageUrl, String fileName, long fileSize, String thumbnailType, long timeCreated, String downloadUrl) {
        super(TYPE.URL);
        this.thumbnailUrl = thumbnailUrl;
        this.originalImageUrl = originalImageUrl;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.thumbnailType = thumbnailType;
        this.timeCreated = timeCreated;
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

    public long getTimeCreated() {
        return timeCreated;
    }

    public String getOriginalImageUrl() {
        return originalImageUrl;
    }
}