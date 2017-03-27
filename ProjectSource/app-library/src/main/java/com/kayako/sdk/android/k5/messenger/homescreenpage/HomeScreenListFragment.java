package com.kayako.sdk.android.k5.messenger.homescreenpage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.HomeScreenListAdapter;

import java.util.List;

public class HomeScreenListFragment extends Fragment implements HomeScreenListContract.View, HomeScreenListContract.ConfigureView {

    private View mRoot;
    private RecyclerView mRecyclerView;
    private HomeScreenListAdapter mAdapter;
    private HomeScreenListContract.Presenter mPresenter;
    private HomeScreenListContract.OnScrollListListener mScrollListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = HomeScreenListFactory.getPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.ko__fragment_home_screen_list, container, false);
        mRecyclerView = (RecyclerView) mRoot.findViewById(R.id.ko__list);
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.initPage();
    }

    private boolean hasPageLoaded() {
        return isAdded() && getActivity() != null && getContext() != null;
    }

    @Override
    public void setupList(List<BaseListItem> list) {
        if (!hasPageLoaded()) {
            return;
        }

        if (mAdapter == null) {
            mAdapter = new HomeScreenListAdapter(list);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);

                    if (mScrollListener != null) {
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            mScrollListener.onScroll(false);
                        } else {
                            mScrollListener.onScroll(true);
                        }
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
        } else {
            mAdapter.replaceData(list);
        }
    }

    @Override
    public void setOnScrollListener(HomeScreenListContract.OnScrollListListener listener) {
        mScrollListener = listener;
    }
}
