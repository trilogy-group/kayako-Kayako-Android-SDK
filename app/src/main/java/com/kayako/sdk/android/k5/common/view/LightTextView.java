package com.kayako.sdk.android.k5.common.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.kayako.sdk.android.k5.common.utils.FontUtils;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class LightTextView extends TextView {
    public LightTextView(Context context) {
        super(context);
        applyFont(context);
    }

    public LightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyFont(context);
    }

    public LightTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyFont(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LightTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyFont(context);
    }

    private void applyFont(Context context) {
        FontUtils.applyFont(context, this, FontUtils.FontStyle.LIGHT);
    }
}
