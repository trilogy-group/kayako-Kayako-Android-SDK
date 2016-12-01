package com.kayako.sdk.android.k5.common.adapter.messengerlist;

public class ChannelDecoration {

    private int sourceDrawable;
    private boolean isNote;
    private String name;

    public ChannelDecoration(int sourceDrawable) {
        this.sourceDrawable = sourceDrawable;
    }

    public ChannelDecoration(boolean isNote, String name) {
        this.isNote = isNote;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSourceDrawable() {
        return sourceDrawable;
    }

    public void setSourceDrawable(int sourceDrawable) {
        this.sourceDrawable = sourceDrawable;
    }

    public boolean isNote() {
        return isNote;
    }

    public void setNote(boolean note) {
        isNote = note;
    }
}
