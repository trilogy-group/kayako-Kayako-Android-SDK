package com.kayako.sdk.android.k5.common.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kayako.sdk.android.k5.R;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ViewUtils {

    public static void showDialogMessage(final Context context, String title, String message, String yesButtonMessage) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(yesButtonMessage, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    public static Snackbar createSnackBar(@NonNull View view, String message, int length) throws NullPointerException {
        Snackbar snackbar = Snackbar.make(view, message, length);
        TextView textView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(4);
        return snackbar;
    }

    public static void showSnackBar(@NonNull View view, String message) {
        try {
            Snackbar snackbar = createSnackBar(view, message, Snackbar.LENGTH_SHORT);
            snackbar.setAction(R.string.ko__action_ok, null);
            snackbar.show();
        } catch (NullPointerException e) {
            // If unable to create snackbar (view is no longer visible to user), do not notify user
        }
    }

    public static void showToastMessage(Context context, String message, int toastDurationType) {
        Toast.makeText(context, message, toastDurationType).show();
    }

}
