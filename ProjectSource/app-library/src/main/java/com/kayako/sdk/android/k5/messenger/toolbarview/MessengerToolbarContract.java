package com.kayako.sdk.android.k5.messenger.toolbarview;

import android.support.annotation.NonNull;
import android.view.View;

import com.kayako.sdk.android.k5.messenger.toolbarview.child.AssignedAgentData;
import com.kayako.sdk.android.k5.messenger.toolbarview.child.LastActiveAgentsData;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.messenger.conversationstarter.ConversationStarter;

public class MessengerToolbarContract {

    public interface Presenter {
        void initPage();

        void configureDefaultView();
    }

    public interface ConfigureView {

        void configureDefaultView();

        void configureForLastActiveUsersView(@NonNull LastActiveAgentsData data);

        void configureForAssignedAgentView(@NonNull AssignedAgentData data);

        void expandToolbarView();

        void collapseToolbarView();
    }

    public interface ChildToolbarConfigureView {

        View getView();

        void update(@NonNull LastActiveAgentsData data);

        void update(@NonNull AssignedAgentData data);

        void setExpandCollapseButtonClicked(OnExpandOrCollapseListener listener);
    }


    public enum MessengerToolbarType {
        ASSIGNED_AGENT, LAST_ACTIVE_AGENTS
    }

    public interface OnExpandOrCollapseListener {
        void onCollapseOrExpand();
    }
}
