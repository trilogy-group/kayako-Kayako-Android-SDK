package com.kayako.sdk.android.k5.common.utils.file;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import com.kayako.sdk.android.k5.core.KayakoLogHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class FileStorageUtil {

    /* TODO: Generate a unique file
         - should be short (Not required but preferrable)
         - should be unique
         IDEA: Save original and current filename, then rename file just before sending to something simple like originalName + counterNumber
     */

    private static final String TAG = "FileStorageUtil";
    private static final String ATTACHMENT_NAME_PREFIX = "attachment__";

    /**
     * Generate a unique file name with a specific prefix to ensure only the files created by this class are deleted.
     * This is to ensure that other internal files (created by Google Analytics & Fabric) are not deleted as well
     *
     * @param originalFileName
     * @return
     */
    private static String getUniqueFileNameForInternalStorage(@NonNull String originalFileName) {
        String uniqueId = UUID.randomUUID().toString();
        return String.format("%s%s_%s", ATTACHMENT_NAME_PREFIX, uniqueId, originalFileName);
    }

    /**
     * Extract the file path
     *
     * @param file
     * @return
     */
    public static String getFilePath(@NonNull File file) {
        return file.getAbsolutePath();
    }

    /**
     * Extract the size of the file
     *
     * @param file
     * @return
     */
    public static Long getFileSize(@NonNull File file) {
        return file.length();
    }

    /**
     * Extract the file extension from the file name
     *
     * @param file
     * @return
     */
    public static String getFileExtension(@NonNull File file) {
        String filePath = getFilePath(file);
        return MimeTypeMap.getFileExtensionFromUrl(filePath);
    }

    /**
     * Extract Mime Type (defaulting to application/octet-stream if unavailable)
     *
     * @param file
     * @return
     */
    public static String getMimeType(@NonNull File file) {
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(getFileExtension(file));
        // If the mimeType can not be identified, set it as 'application/octet-stream'
        return TextUtils.isEmpty(mimeType) ? "application/octet-stream" : mimeType;
    }

    /**
     * Extract file Name
     *
     * @param file
     * @return
     */
    public static String getFileName(File file) {
        return file.getName();
    }

    /**
     * Save file in internal storage to ensure access
     *
     * @param context
     * @param contentUri
     * @param originalFileName
     * @return
     */
    public static File saveFile(Context context, Uri contentUri, String originalFileName) {
        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        ContentResolver resolver = context.getContentResolver();

        try {
            inputStream = resolver.openInputStream(contentUri);

            // Create a unique filename that does not already exist in internal storage
            String fileName = getUniqueFileNameForInternalStorage(originalFileName);

            // Create file in Internal App Directory
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE); // creates file if not existing

            // Transfer bytes from uri inputstream to temporary file outputstream
            int bufferSize = 1024 * 4; // 4 KB
            byte[] buffer = new byte[bufferSize];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }

            return context.getFileStreamPath(fileName);

        } catch (IOException e) {
            KayakoLogHelper.printStackTrace(TAG, e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e1) {
                    KayakoLogHelper.printStackTrace(TAG, e1);
                }
            }

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e1) {
                    KayakoLogHelper.printStackTrace(TAG, e1);
                }
            }
        }

        return null;
    }

    /**
     * Delete a file saved in internal storage
     *
     * @param context
     * @param fileName
     */
    public static void deleteSavedFile(Context context, String fileName) {
        context.deleteFile(fileName);
    }

    /**
     * Clear existing files in internal storage (previous attachments)
     *
     * @param context
     */
    public static boolean deleteSavedFiles(Context context) throws Exception {
        boolean isDeleted = false;
        for (String fileName : context.fileList()) {
            // Only deleting files with a prefix to ensure that other internal files (created by Google Analytics & Fabric) are not deleted as well
            if (fileName.startsWith(ATTACHMENT_NAME_PREFIX)) {
                deleteSavedFile(context, fileName);
                isDeleted = true;
            }
        }
        return isDeleted;
    }

    public static boolean isImage(File file) {
        String mimeType = getMimeType(file);
        return mimeType != null && mimeType.startsWith("image");
    }

    public static boolean isExisting(@Nullable File file) {
        return file != null && file.exists();
    }
}
