package com.kayako.sdk.android.k5.common.utils;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.kayako.sdk.utils.LogUtils;

public class KeyboardUtils {

    /**
     * To hide the soft keyboard
     */
    public static void hideKeyboard(AppCompatActivity activity) {
        try {
            // Check if no view has focus:
            if (activity != null) {
                View view = activity.getCurrentFocus();
                if (view != null) {
                    InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage() != null) {
                LogUtils.logError(KeyboardUtils.class, e.getMessage());
            }
        }
    }

    /**
     * To show the soft keyboard
     */
    public static void showKeyboard(AppCompatActivity activity) {
        try {
            // Check if no view has focus:
            if (activity != null) {
                View view = activity.getCurrentFocus();
                if (view != null) {
                    InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage() != null) {
                LogUtils.logError(KeyboardUtils.class, e.getMessage());
            }
        }
    }
}
