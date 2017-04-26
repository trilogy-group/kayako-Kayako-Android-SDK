package com.kayako.sdk.android.k5.common.utils.file;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.core.KayakoLogHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.isEmpty;

public class FileAttachmentUtil {

    // TODO Add support for Multiple Attachments - Only supported in API level 18+ - intentBrowseFiles.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
    // TODO: Test status of each field through the steps
    // TODO: Test for different versions

    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_FAIL_NO_HANDLER = 2;
    public static final int STATUS_FAIL_INVALID_FILE = 3;
    private static final int STATUS_FAIL_UNEXPECTED_ERROR = 4;

    private static final String TAG = "FileAttachmentUtil";

    /**
     * Open File Picker from fragment
     *
     * @param fragment
     * @param requestCode
     */
    public static void openFileChooserActivityFromFragment(Fragment fragment, int requestCode) {
        Intent intentBrowseFiles = new Intent(Intent.ACTION_GET_CONTENT);
        intentBrowseFiles.setType("*/*");
        intentBrowseFiles.addCategory(Intent.CATEGORY_OPENABLE);
        fragment.startActivityForResult(intentBrowseFiles, requestCode);
    }

    /**
     * Open File Picker from activity
     *
     * @param activity
     * @param requestCode
     */
    public static void openFileChooserActivityFromActivity(Activity activity, int requestCode) {
        Intent intentBrowseFiles = new Intent(Intent.ACTION_GET_CONTENT);
        intentBrowseFiles.setType("*/*");
        intentBrowseFiles.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(intentBrowseFiles, requestCode);
    }

    /**
     * Call this method in the onActivityResult() lifecycle callback
     *
     * @param resultCode
     * @param data
     * @return
     * @throws NullPointerException
     */
    public static File getFileOnActivityResult(int resultCode, Intent data) throws NullPointerException, SecurityException {
        Context context = Kayako.getApplicationContext();
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String fileName = getFileName(context, uri);
            File file = FileStorageUtil.saveFile(context, uri, fileName);
            return file;
        }
        return null;
    }

    /**
     * Clear saved files once the attachments have been used or discarded
     */
    public static void clearSavedAttachments() {
        try {
            Context context = Kayako.getApplicationContext();
            FileStorageUtil.deleteSavedFiles(context);
        } catch (Exception e) {
            KayakoLogHelper.e(TAG, "A little test to see how reliable the fileList() methods are across different android versions. Ideally, this shouldn't crash.");
            KayakoLogHelper.logException(TAG, e);
        }
    }

    public static void clearSavedAttachment(File file) {
        try {
            Context context = Kayako.getApplicationContext();
            FileStorageUtil.deleteSavedFile(context, file.getName());
        } catch (Exception e) {
            KayakoLogHelper.e(TAG, "A little test to see how reliable the fileList() methods are across different android versions. Ideally, this shouldn't crash.");
            KayakoLogHelper.logException(TAG, e);
        }
    }

    /**
     * Get the File class object from the FileAttachment object
     *
     * @param attachment
     * @return
     */
    public static File getFile(FileAttachment attachment) {
        return new File(attachment.getPath());
    }

    public static Intent getFileOpenIntent(@NonNull File file) {
        Intent newIntent = new Intent(Intent.ACTION_VIEW);
        newIntent.setDataAndType(Uri.fromFile(file), FileStorageUtil.getMimeType(file));
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return newIntent;
    }

    /**
     * Open the file attachment
     *
     * @param context
     * @param caseAttachment
     * @return
     */
    public static int openFileAttachment(@NonNull Context context, @NonNull FileAttachment caseAttachment) {
        // Assertions
        if (isEmpty(caseAttachment.getPath())) {
            return STATUS_FAIL_INVALID_FILE;
        }
        return openFile(context, caseAttachment.getFile());
    }

    /**
     * Open the file attachment
     *
     * @param context
     * @param file
     * @return
     */
    public static int openFile(@NonNull Context context, @NonNull File file) {
        // Assertions
        if (isEmpty(file.getPath())) {
            return STATUS_FAIL_INVALID_FILE;
        }
        return openFileIntent(context, getFileOpenIntent(file));
    }

    /**
     * Open the file intent
     *
     * @param context
     * @param intent
     * @return
     */
    public static int openFileIntent(@NonNull Context context, @NonNull Intent intent) {
        // Open File Intent
        try {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Added to avoid AndroidRunTimeExceptions: Calling startActivity() from outside of an Activity  context requires the FLAG_ACTIVITY_NEW_TASK flag.
            context.startActivity(intent);
            return STATUS_SUCCESS;
        } catch (ActivityNotFoundException e) {
            return STATUS_FAIL_NO_HANDLER;
        } catch (NullPointerException e2) {
            return STATUS_FAIL_NO_HANDLER;
        } catch (Exception e3) {
            // Added because weird device/oem specific errors like permission denied - com.htc.sense.permission.album.GENERAL happens here
            KayakoLogHelper.logException(TAG, e3);
            return STATUS_FAIL_UNEXPECTED_ERROR;
        }
    }

    public static FileAttachment generateFileAttachment(@NonNull String key, @NonNull File file) {
        return new FileAttachment(key, file.getPath());
    }

    private static String getFileName(Context context, Uri uri) throws NullPointerException {
        String fileName = null;
        if ("file".equals(uri.getScheme())) {
            // Process as a uri that points to a file
            fileName = uri.getLastPathSegment();

        } else if ("content".equals(uri.getScheme())) {
            // Changes for new storage access framework in KitKat
            // Process as a uri that points to a content item
            String[] proj = {OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE};

            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
            cursor.moveToFirst();
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            fileName = cursor.getString(nameIndex);
            cursor.close();
        }

        return fileName;
    }

    public static List<FileAttachment> convertFiles(@NonNull String commonKey, @Nullable List<File> files) {
        List<FileAttachment> fileAttachments = new ArrayList<>();
        if (files == null) {
            return fileAttachments;
        }

        for (File file : files) {
            try {
                fileAttachments.add(new FileAttachment(commonKey, file));
            } catch (AssertionError e) {
                KayakoLogHelper.e(TAG, e.getMessage());
                // if a file doesn't exist, skip and convert what is available
                // TODO: This error happens due to improper clearing of Cached Attachments
            }
        }
        return fileAttachments;
    }

    public static List<File> convertFiles(@Nullable List<FileAttachment> attachments) {
        List<File> files = new ArrayList<>();
        if (attachments == null) {
            return files;
        }

        for (FileAttachment attachment : attachments) {
            files.add(attachment.getFile());
        }

        return files;
    }

    public static void removeFiles(@NonNull List<FileAttachment> attachments) {
        Context context = Kayako.getApplicationContext();
        if (attachments != null) {
            for (FileAttachment attachment : attachments) {
                // Clear attachments only if existing (prevent deleting the same file again)
                if (FileStorageUtil.isExisting(attachment.getFile())) {
                    FileStorageUtil.deleteSavedFile(context, attachment.getName());
                }
            }
        }
    }

    public static void showErrorMessages(Context context, int status) {
        switch (status) {
            case FileAttachmentUtil.STATUS_SUCCESS:
                // No Messsage for success
                break;

            case FileAttachmentUtil.STATUS_FAIL_INVALID_FILE:
            case FileAttachmentUtil.STATUS_FAIL_UNEXPECTED_ERROR:
                KayakoLogHelper.e(TAG, context.getString(R.string.ko__attachment_msg_unable_to_open_file));
                Toast.makeText(context, R.string.ko__attachment_msg_unable_to_open_file, Toast.LENGTH_LONG).show();
                break;
            case FileAttachmentUtil.STATUS_FAIL_NO_HANDLER:
                KayakoLogHelper.e(TAG, context.getString(R.string.ko__attachment_msg_no_handler_for_file_type));
                Toast.makeText(context, R.string.ko__attachment_msg_no_handler_for_file_type, Toast.LENGTH_LONG).show();
                break;

        }
    }
}

