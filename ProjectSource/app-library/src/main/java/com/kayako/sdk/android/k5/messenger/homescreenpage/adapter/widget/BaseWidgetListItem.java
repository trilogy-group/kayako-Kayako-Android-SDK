package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget;

import android.support.annotation.NonNull;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;

public abstract class BaseWidgetListItem extends BaseListItem {

    private String title;
    private boolean hasActionButton;
    private String actionButtonLabel;
    private OnClickActionListener onClickActionListener;

    public BaseWidgetListItem(int listType, @NonNull String title) {
        super(listType);
        this.title = title;
        this.hasActionButton = false;

        if (title == null) {
            throw new IllegalArgumentException("Arguments can not be null");
        }
    }

    public BaseWidgetListItem(int listType, @NonNull String title, @NonNull String actionButtonLabel, @NonNull OnClickActionListener onClickActionListener) {
        super(listType);
        this.title = title;
        this.actionButtonLabel = actionButtonLabel;
        this.onClickActionListener = onClickActionListener;
        this.hasActionButton = true;

        if (title == null
                || actionButtonLabel == null
                || onClickActionListener == null) {
            throw new IllegalArgumentException("Arguments can not be null");
        }
    }

    public enum WidgetType {
        PRESENCE, RECENT_CASES
    }

    public interface OnClickActionListener {
        void onClickActionButton();
    }

    public String getTitle() {
        return title;
    }

    public String getActionButtonLabel() {
        return actionButtonLabel;
    }

    public boolean hasActionButton() {
        return hasActionButton;
    }

    public OnClickActionListener getOnClickActionListener() {
        return onClickActionListener;
    }
}
