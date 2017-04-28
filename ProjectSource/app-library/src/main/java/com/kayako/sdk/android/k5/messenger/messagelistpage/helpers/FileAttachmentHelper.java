package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.android.k5.common.utils.file.FileAttachmentUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileAttachmentHelper {

    private Map<String, File> mCachedAttachments = new HashMap<>();

    public FileAttachmentHelper() {
    }

    public void onSuccessfulSendingOfMessage(String clientId) {
        mCachedAttachments.remove(clientId);
        File file = mCachedAttachments.get(clientId);
        if (file != null) {
            FileAttachmentUtil.clearSavedAttachment(file);
        }
    }

    public void onSendingUnsentMessage(String clientId, File file) {
        mCachedAttachments.put(clientId, file);
    }

    public boolean getAttachmentButtonVisibility(boolean hasConversationBeenCreated) {
        return hasConversationBeenCreated;
    }

    public void onReset() {
        FileAttachmentUtil.clearSavedAttachments();
    }
}
