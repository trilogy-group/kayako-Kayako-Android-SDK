package com.kayako.sdk.android.k5.messenger.toolbarview;

import android.support.annotation.NonNull;
import android.view.View;

import com.kayako.sdk.android.k5.messenger.data.conversationstarter.AssignedAgentData;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.LastActiveAgentsData;

public class MessengerToolbarContract {

    public interface Presenter {
        void initPage();

        void closePage();

        void configureDefaultView();

        void configureOtherView(boolean showUnreadCounter);

        int getUnreadCount();
    }

    public interface ConfigureView {

        void configureDefaultView();

        void configureForLastActiveUsersView(@NonNull LastActiveAgentsData data, boolean showUnreadCount);

        void configureForAssignedAgentView(@NonNull AssignedAgentData data, boolean showUnreadCount);

        void configureForSimpleTitle(@NonNull String title, boolean showUnreadCount);

        void expandToolbarView();

        void collapseToolbarView();

        boolean isToolbarCollapsed();

        boolean isToolbarExpanded();

        boolean isToolbarAreadyConfigured();

        void refreshUnreadCounter(int newUnreadCount);
    }

    public interface ChildToolbarConfigureView {

        View getView();

        void update(@NonNull LastActiveAgentsData data, int unreadCount);

        void update(@NonNull AssignedAgentData data, int unreadCount);

        void update(@NonNull String title, int unreadCount);

        void setExpandCollapseButtonClicked(OnExpandOrCollapseListener listener);
    }


    public enum MessengerToolbarType {
        ASSIGNED_AGENT, LAST_ACTIVE_AGENTS, SIMPLE_TITLE
    }

    public interface OnExpandOrCollapseListener {
        void onCollapseOrExpand();
    }
}
