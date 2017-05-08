package com.kayako.sdk.android.k5.messenger.toolbarview;

import android.support.annotation.NonNull;
import android.view.View;

import com.kayako.sdk.android.k5.messenger.data.conversationstarter.AssignedAgentData;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.LastActiveAgentsData;

public class MessengerToolbarContract {

    public interface Presenter {
        void initPage();

        void configureDefaultView();

        void configureOtherView();
    }

    public interface ConfigureView {

        void configureDefaultView();

        void configureForLastActiveUsersView(@NonNull LastActiveAgentsData data);

        void configureForAssignedAgentView(@NonNull AssignedAgentData data);

        void configureForSimpleTitle(@NonNull String title);

        void expandToolbarView();

        void collapseToolbarView();

        boolean isToolbarCollapsed();

        boolean isToolbarExpanded();

        boolean isToolbarAreadyConfigured();
    }

    public interface ChildToolbarConfigureView {

        View getView();

        void update(@NonNull LastActiveAgentsData data);

        void update(@NonNull AssignedAgentData data);

        void update(@NonNull String title);

        void setExpandCollapseButtonClicked(OnExpandOrCollapseListener listener);
    }


    public enum MessengerToolbarType {
        ASSIGNED_AGENT, LAST_ACTIVE_AGENTS, SIMPLE_TITLE
    }

    public interface OnExpandOrCollapseListener {
        void onCollapseOrExpand();
    }
}
