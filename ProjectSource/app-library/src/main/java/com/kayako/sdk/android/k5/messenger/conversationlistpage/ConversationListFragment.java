package com.kayako.sdk.android.k5.messenger.conversationlistpage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.activities.KayakoSelectConversationActivity;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.conversationlist.ConversationListAdapter;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollAdapter;
import com.kayako.sdk.android.k5.common.fragments.BaseListFragment;
import com.kayako.sdk.android.k5.common.fragments.ListPageState;
import com.kayako.sdk.android.k5.common.fragments.OnListPageStateChangeListener;
import com.kayako.sdk.android.k5.core.Kayako;

import java.util.List;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

public class ConversationListFragment extends BaseListFragment implements ConversationListContract.View, ConversationListContract.ConfigureView {

    private ConversationListContract.Presenter mPresenter;
    private ConversationListContract.OnScrollListener mScrollListener;
    private OnListPageStateChangeListener mOnListPageStateChangeListener;

    private ConversationListAdapter mConversationListAdapter;
    private boolean mIsListInitialized;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = ConversationListFactory.getPresenter(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.setOnListPageChangeStateListener(new OnListPageStateChangeListener() {
            @Override
            public void onListPageStateChanged(ListPageState state) {
                if (mOnListPageStateChangeListener != null) {
                    mOnListPageStateChangeListener.onListPageStateChanged(state);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.initPage();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.closePage();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    private boolean hasPageLoaded() {
        return isAdded() && getActivity() != null;
    }

    @Override
    public void setupList(final List<BaseListItem> conversations) {
        if (!hasPageLoaded()) {
            return;
        }

        if (!mIsListInitialized) {
            mConversationListAdapter = new ConversationListAdapter(conversations, new ConversationListAdapter.OnClickConversationListener() {
                @Override
                public void onClickConversation(long conversationId) {
                    mPresenter.onClickConversation(conversationId);
                }
            });

            initList(mConversationListAdapter);
            setLoadMoreListener(new EndlessRecyclerViewScrollAdapter.OnLoadMoreListener() {
                @Override
                public void loadMoreItems() {
                    mPresenter.onLoadMoreItems();
                }
            });
            super.setScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (mScrollListener == null) {
                        return;
                    }
                    if (dy != 0) {
                        mScrollListener.onScroll(true);
                    } else {
                        mScrollListener.onScroll(false);
                    }
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (mScrollListener == null) {
                        return;
                    }
                    switch (newState) {
                    case SCROLL_STATE_IDLE:
                        mScrollListener.onScroll(false);
                        break;
                    default:
                        break;
                    }
                }
            });

            mIsListInitialized = true;
        } else {
            mConversationListAdapter.replaceAllData(conversations);
        }

        showListViewAndHideOthers();
    }

    @Override
    public void showEmptyView() {
        if (!hasPageLoaded()) {
            return;
        }

        Context context = Kayako.getApplicationContext();
        getDefaultStateViewHelper().setupErrorView(context.getString(R.string.ko__label_error_view_title), context.getString(R.string.ko__label_error_view_description), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onClickRetryOnError();
            }
        });
        showEmptyViewAndHideOthers();
    }

    @Override
    public void showErrorView() {
        if (!hasPageLoaded()) {
            return;
        }

        Context context = Kayako.getApplicationContext();
        getDefaultStateViewHelper().setupErrorView(context.getResources().getString(R.string.ko__label_error_view_title), context.getResources().getString(R.string.ko__label_error_view_description), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onClickRetryOnError();
            }
        });
        showErrorViewAndHideOthers();
    }

    @Override
    public void showLoadingView() {
        if (!hasPageLoaded()) {
            return;
        }

        showLoadingViewAndHideOthers();
    }

    @Override
    public void openMessageListPage(final Long id, final int requestCode) {
        if (!hasPageLoaded()) {
            return;
        }

        KayakoSelectConversationActivity.startActivityForResult(getActivity(), this, id, requestCode);
    }

    @Override
    public void showMessage(int stringResId) {
        if (!hasPageLoaded()) {
            return;
        }

        Context context = Kayako.getApplicationContext();
        if (context != null) {
            Toast.makeText(Kayako.getApplicationContext(), stringResId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setListHasMoreItems(boolean hasMore) {
        if (!hasPageLoaded()) {
            return;
        }

        setHasMoreItems(hasMore);
    }

    @Override
    public void configureLoadMoreView(boolean showLoadMoreProgress) {
        if (!hasPageLoaded()) {
            return;
        }

        if (showLoadMoreProgress) {
            showLoadMoreProgress();
        } else {
            hideLoadMoreProgress();
        }
    }

    @Override
    public boolean getIfListHasMoreItems() {
        if (!hasPageLoaded()) {
            return false;
        }

        return hasMoreItems();
    }

    @Override
    public void reloadConversations() {
        mPresenter.reloadConversations();
    }

    @Override
    public void setOnScrollListener(ConversationListContract.OnScrollListener onScrollListener) {
        mScrollListener = onScrollListener;
    }

    @Override
    public void setOnPageStateChangeListener(OnListPageStateChangeListener onListPageStateChangeListener) {
        mOnListPageStateChangeListener = onListPageStateChangeListener;
    }

}
