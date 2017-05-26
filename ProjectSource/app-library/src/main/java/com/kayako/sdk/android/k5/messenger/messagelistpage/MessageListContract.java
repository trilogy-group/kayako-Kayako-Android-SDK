package com.kayako.sdk.android.k5.messenger.messagelistpage;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollAdapter;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerAdapter;
import com.kayako.sdk.android.k5.common.fragments.OnListPageStateChangeListener;
import com.kayako.sdk.android.k5.common.fragments.OnScrollListListener;
import com.kayako.sdk.android.k5.common.mvp.BasePresenter;
import com.kayako.sdk.android.k5.common.mvp.BaseView;

import java.util.List;

public class MessageListContract {

    interface View extends BaseView {

    }

    interface ConfigureView {

        void setupList(List<BaseListItem> conversation);

        void showEmptyView();

        void showErrorView();

        void showLoadingView();

        void setOnErrorListener(MessageListContract.OnErrorListener listener);

        void setOnListPageStateChangeListener(OnListPageStateChangeListener listener);

        void setOnListScrollListener(OnScrollListListener listener);

        void setOnListItemClickListener(MessengerAdapter.OnItemClickListener listener);

        void setOnListAttachmentClickListener(MessengerAdapter.OnAttachmentClickListener listener);

        void setListOnLoadMoreListener(EndlessRecyclerViewScrollAdapter.OnLoadMoreListener listener);

        void setHasMoreItemsToLoad(boolean hasMoreItems);

        void showLoadMoreView();

        void hideLoadMoreView();

        void scrollToBottomOfList();

        boolean isNearBottomOfList();
    }

    public interface OnErrorListener {
        void onClickRetry();
    }
}
