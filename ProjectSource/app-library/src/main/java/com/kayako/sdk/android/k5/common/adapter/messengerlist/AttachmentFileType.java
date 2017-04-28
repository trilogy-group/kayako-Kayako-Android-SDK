package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import java.io.File;

public class AttachmentFileType extends Attachment {

    private Long id;
    private File thumbnailFile;
    private String caption;

    /**
     * Minimal contructor for attachments recently attached
     *
     * @param id
     * @param thumbnailFile
     * @param caption
     */
    public AttachmentFileType(Long id, File thumbnailFile, String caption) {
        super(TYPE.FILE);
        assert thumbnailFile != null;

        this.id = id;
        this.caption = caption;
        this.thumbnailFile = thumbnailFile;
    }

    public Long getId() {
        return id;
    }

    public String getCaption() {
        return caption;
    }

    public File getThumbnailFile() {
        return thumbnailFile;
    }
}