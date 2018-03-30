package com.kayako.sdk.android.k5.common.utils.file;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.kayako.sdk.android.k5.R;

/**
 * Created on 12/03/18.
 */

public class FileAccessPermissions {

    private FileAccessPermissions() {
    }

    public static final int REQUEST_ATTACH_FILE = 2201;

    public static boolean isPermitted(Activity activity) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_ATTACH_FILE);
            }

            return false;
        } else {
            // Permission has already been granted
            return true;
        }
    }

    public static boolean wasRequestedPermissionAllowed(Activity activity, int requestCode, String permissions[], int[] grantResults) {
        return grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }

    public static void onRequestPermissionsResult(Activity activity, int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ATTACH_FILE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(activity, R.string.ko__permission_granted_to_access_attachments, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, R.string.ko__permission_request_to_access_attachments, Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
