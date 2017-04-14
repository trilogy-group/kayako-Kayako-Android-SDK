package com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel;

public class ClientTypingActivity {

    private UserViewModel user;
    private boolean isTyping;

    public ClientTypingActivity(boolean isTyping) {
        if (isTyping == true) {
            throw new IllegalArgumentException("If isTyping is true, then user  must also be provided. Use the appropriate constructor");
        }
        this.isTyping = isTyping;
    }

    public ClientTypingActivity(boolean isTyping, UserViewModel user) {
        if (isTyping == true && user == null) {
            throw new IllegalArgumentException("If isTyping is true, then user  must also be provided!");
        }

        this.user = user;
        this.isTyping = isTyping;
    }

    public UserViewModel getUser() {
        return user;
    }

    public boolean isTyping() {
        return isTyping;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientTypingActivity that = (ClientTypingActivity) o;

        return isTyping == that.isTyping;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (isTyping ? 1 : 0);
        return result;
    }
}
