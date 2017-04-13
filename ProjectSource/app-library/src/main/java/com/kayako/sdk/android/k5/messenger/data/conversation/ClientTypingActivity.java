package com.kayako.sdk.android.k5.messenger.data.conversation;

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
}
