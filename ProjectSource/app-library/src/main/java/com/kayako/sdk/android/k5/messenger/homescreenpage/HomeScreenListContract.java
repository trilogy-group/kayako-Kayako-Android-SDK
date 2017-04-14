package com.kayako.sdk.android.k5.messenger.homescreenpage;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;

import java.util.List;

public class HomeScreenListContract {

    public interface View {

        void setupList(List<BaseListItem> list);

        void openConversationListingPage();

        void openSelectConversationPage(long conversationId);

        String getResourceString(int stringResId);
    }

    public interface Presenter {

        void initPage();
    }

    public interface ConfigureView {

        void setOnScrollListener(OnScrollListListener listener);

    }

    public interface OnScrollListListener {
        void onScroll(boolean isScrolling);
    }

}
