package com.kayako.sdk.android.k5.kre.helpers.presence;

public class PresenceMetaActivityData {

    // NOTE: retain this pascal case - required for json representation

    private Boolean isTyping; // indicate the user is typing
    private Boolean isUpdating; // indicate the user is updating the case (via properties or via reply response)
    private Long lastActiveAt; // indicate when user was active last (assuming this is the current system time whenever meta values need to be updated)
    private Boolean isViewing; // indicate if a tab exists on frontend
    private Boolean isForeground; // indicate if the user has that tab open (since only one tab can be opened at a time)

    public PresenceMetaActivityData(Boolean is_typing, Boolean is_updating, Long last_active_at, Boolean is_viewing, Boolean is_foreground) {
        this.isTyping = is_typing;
        this.isUpdating = is_updating;
        this.lastActiveAt = last_active_at;
        this.isViewing = is_viewing;
        this.isForeground = is_foreground;
    }

    public Boolean isTyping() {
        return isTyping;
    }

    public Boolean isUpdating() {
        return isUpdating;
    }

    public Long getLastActiveAt() {
        return lastActiveAt;
    }

    public Boolean isViewing() {
        return isViewing;
    }

    public Boolean isForeground() {
        return isForeground;
    }

    public void setTyping(Boolean typing) {
        isTyping = typing;
    }

    public void setUpdating(Boolean updating) {
        isUpdating = updating;
    }

    public void setLastActiveAt(Long lastActiveAt) {
        this.lastActiveAt = lastActiveAt;
    }

    public void setViewing(Boolean viewing) {
        isViewing = viewing;
    }

    public void setForeground(Boolean foreground) {
        isForeground = foreground;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            PresenceMetaActivityData otherObj = (PresenceMetaActivityData) obj;
            PresenceMetaActivityData thisObj = this;
            return otherObj.isForeground().equals(thisObj.isForeground())
                    && otherObj.isViewing().equals(thisObj.isViewing())
                    && otherObj.isTyping().equals(thisObj.isTyping())
                    && otherObj.isUpdating().equals(thisObj.isUpdating())
                    && otherObj.getLastActiveAt().equals(thisObj.getLastActiveAt());
        } catch (NullPointerException | ClassCastException e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
