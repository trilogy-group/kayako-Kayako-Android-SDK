package com.kayako.sdk.android.k5.common.adapter.messengerlist;

public class Attachment {
    private Long id;
    private String thumbnailUrl;
    private String caption;

    public Attachment(Long id, String thumbnailUrl, String caption) {
        assert thumbnailUrl != null;

        this.id = id;
        this.caption = caption;
        this.thumbnailUrl = thumbnailUrl;
    }

    public Attachment(String thumbnailUrl) {
        assert thumbnailUrl != null;

        this.thumbnailUrl = thumbnailUrl;
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