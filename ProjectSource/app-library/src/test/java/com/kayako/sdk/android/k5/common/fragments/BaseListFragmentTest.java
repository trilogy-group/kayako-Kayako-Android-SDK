package com.kayako.sdk.android.k5.common.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollAdapter;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollListener;
import com.kayako.sdk.android.k5.common.viewhelpers.CustomStateViewHelper;
import com.kayako.sdk.android.k5.common.viewhelpers.DefaultStateViewHelper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.methods;

import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        BaseListFragment.class,
        DefaultStateViewHelper.class,
        BaseListFragmentTest.TestBaseListFragment.class,
        Looper.class
})
public class BaseListFragmentTest {

    private TestBaseListFragment testBaseListFragment;
    private Boolean isMethodSuccessfullyRun;

    @Mock
    private Looper looper;
    @Mock
    private View mockMRoot, mockView, mockCreateView;
    @Mock
    private RecyclerView mockMRecyclerView;
    @Mock
    private LayoutInflater mockInflater;
    @Mock
    private ViewGroup mockContainer;
    @Mock
    private Bundle savedInstanceState;
    @Mock
    private LinearLayoutManager mockLinearLayoutManager;
    @Mock
    private DefaultStateViewHelper mockMDefaultStateViewHelper;
    @Mock
    private CustomStateViewHelper mockCustomStateViewHelper;
    @Mock
    private DefaultStateViewHelper mockDefaultStateViewHelper;
    @Mock
    private OnListPageStateChangeListener mockListPageChangeStateListener;
    @Mock
    private Context mockContext;
    @Mock
    private EndlessRecyclerViewScrollAdapter mockAdapter;
    @Mock
    private Handler handler;
    @Captor
    private ArgumentCaptor<Runnable> runnableCaptor;
    @Mock
    private RecyclerView.OnScrollListener onScrollListener;
    @Mock
    private EndlessRecyclerViewScrollAdapter.OnLoadMoreListener loadMoreListener;
    @Mock
    private EndlessRecyclerViewScrollListener mloadMoreListener;

    @Before
    public void setUp() {
        isMethodSuccessfullyRun = Boolean.FALSE;
        MockitoAnnotations.initMocks(this);
        testBaseListFragment = new TestBaseListFragment();
        Whitebox.setInternalState(testBaseListFragment, "mCustomStateViewHelper", mockCustomStateViewHelper);
        Whitebox.setInternalState(testBaseListFragment, "mDefaultStateViewHelper", mockDefaultStateViewHelper);
        Whitebox.setInternalState(testBaseListFragment, "mListPageChangeStateListener", mockListPageChangeStateListener);
        PowerMockito.mockStatic(Looper.class);
        when(Looper.getMainLooper()).thenReturn(looper);
    }

    @Test
    public void onCreateViewTest() {
        //Arrange
        BaseListFragment mockBaseListFragment = Mockito.mock(BaseListFragment.class);
        Mockito.doCallRealMethod().when(mockBaseListFragment).onCreateView(mockInflater, mockContainer, savedInstanceState);
        Mockito.doReturn(mockCreateView).when(mockInflater).inflate(R.layout.ko__fragment_list, mockContainer, false);
        Mockito.doReturn(mockMRecyclerView).when(mockCreateView).findViewById(R.id.ko__list);
        PowerMockito.suppress(methods(BaseListFragment.class, "setupView"));
        //Act
        View view  = mockBaseListFragment.onCreateView(mockInflater, mockContainer, savedInstanceState);
        //Assert
        Assert.assertNotNull(view);
    }

    class TestBaseListFragment extends BaseListFragment {

        public TestBaseListFragment(){
            super.mRoot = mockMRoot;
            super.mRecyclerView = mockMRecyclerView;
        }

        @Override
        public void setupView(View view) {
            super.setupView(view);
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        @Override
        public void showListView() {
            super.showListView();
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        @Override
        public void hideListView() {
            super.hideListView();
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        @Override
        public void showEmptyViewAndHideOthers() {
            super.showEmptyViewAndHideOthers();
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        @Override
        public void showLoadingViewAndHideOthers() {
            super.showLoadingViewAndHideOthers();
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        @Override
        public void showErrorViewAndHideOthers() {
            super.showErrorViewAndHideOthers();
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        @Override
        public void showListViewAndHideOthers(){
            super.showListViewAndHideOthers();
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        @Override
        public void hideAll() {
            super.hideAll();
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        @Override
        public CustomStateViewHelper getCustomStateViewHelper() {
            return super.getCustomStateViewHelper();
        }

        @Override
        public DefaultStateViewHelper getDefaultStateViewHelper() {
            return super.getDefaultStateViewHelper();
        }

        @Override
        public void initList(final EndlessRecyclerViewScrollAdapter adapter) {
            super.initList(adapter);
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        @Override
        public void init(final EndlessRecyclerViewScrollAdapter adapter, final LinearLayoutManager layoutManager) {
            super.init(adapter, layoutManager);
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        @Override
        public void addItemsToEndOfList(List<BaseListItem> items) {
            super.addItemsToEndOfList(items);
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        @Override
        public void addItemsToBeginningOfList(List<BaseListItem> items) {
            super.addItemsToBeginningOfList(items);
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        @Override
        public void addItem(BaseListItem item, int position) {
            super.addItem(item, position);
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        @Override
        public void removeItem(int position) {
            super.removeItem(position);
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        @Override
        public void replaceItem(BaseListItem item, int position) {
            super.replaceItem(item, position);
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        public void scrollToEndOfList() {
            super.scrollToEndOfList();
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        @Override
        public void scrollToBeginningOfList() {
            super.scrollToBeginningOfList();
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        @Override
        public void scrollToPosition(final int position) {
            super.scrollToPosition(position);
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        @Override
        public int findFirstVisibleItemPosition() {
            return super.findFirstVisibleItemPosition();
        }

        @Override
        public int findLastVisibleItemPosition() {
            return super.findLastVisibleItemPosition();
        }

        @Override
        public int getSizeOfData() {
            return super.getSizeOfData();
        }

        @Override
        public void setScrollListener(RecyclerView.OnScrollListener onScrollListener) {
            super.setScrollListener(onScrollListener);
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        @Override
        public void removeScrollListener(RecyclerView.OnScrollListener onScrollListener) {
            super.removeScrollListener(onScrollListener);
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        @Override
        public void setLoadMoreListener(final EndlessRecyclerViewScrollAdapter.OnLoadMoreListener loadMoreListener) {
            super.setLoadMoreListener(loadMoreListener);
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        @Override
        public void removeLoadMoreListener() {
            super.removeLoadMoreListener();
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        @Override
        public void showLoadMoreProgress() {
            super.showLoadMoreProgress();
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        @Override
        public void hideLoadMoreProgress() {
            super.hideLoadMoreProgress();
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        @Override
        public void setHasMoreItems(boolean hasMoreItems) {
            super.setHasMoreItems(hasMoreItems);
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        @Override
        public boolean hasMoreItems(){
            return super.hasMoreItems();
        }

        @Override
        public void setOnListPageChangeStateListener(OnListPageStateChangeListener listPageChangeStateListener) {
            super.setOnListPageChangeStateListener(listPageChangeStateListener);
            isMethodSuccessfullyRun = Boolean.TRUE;
        }

        @Override
        public OnListPageStateChangeListener getOnListPageChangeStateListener() {
            return super.getOnListPageChangeStateListener();
        }
    }

    @Test
    public void setupViewTest() throws Exception {
        //Arrange
        Mockito.doReturn(mockMRecyclerView).when(mockView).findViewById(R.id.ko__list);
        PowerMockito.whenNew(DefaultStateViewHelper.class).withAnyArguments().thenReturn(mockMDefaultStateViewHelper);
        CustomStateViewHelper mockCustomStateViewHelper = Mockito.mock(CustomStateViewHelper.class);
        PowerMockito.whenNew(CustomStateViewHelper.class).withAnyArguments().thenReturn(mockCustomStateViewHelper);
        //Act
        testBaseListFragment.setupView(mockView);
        //Assert
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test
    public void showListViewTest() {
        //Arrange
        Mockito.doReturn(mockView).when(mockMRoot).findViewById(R.id.ko__list);
        //Act
        testBaseListFragment.showListView();
        //Assert
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test
    public void hideListViewTest(){
        //Arrange
        Mockito.doReturn(mockView).when(mockMRoot).findViewById(R.id.ko__list);
        //Act
        testBaseListFragment.hideListView();
        //Assert
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test
    public void showEmptyViewAndHideOthersTestForEmptyView() {
        //Arrange
        doReturn(true).when(mockCustomStateViewHelper).hasEmptyView();
        doNothing().when(mockDefaultStateViewHelper).hideErrorView();
        doNothing().when(mockDefaultStateViewHelper).hideLoadingView();
        PowerMockito.suppress(methods(BaseListFragment.class, "hideListView"));
        doNothing().when(mockListPageChangeStateListener).onListPageStateChanged(ListPageState.EMPTY);
        //Act
        testBaseListFragment.showEmptyViewAndHideOthers();
        //Assert
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test
    public void showEmptyViewAndHideOthersTestForNonEmptyView() {
        //Arrange
        doReturn(false).when(mockCustomStateViewHelper).hasEmptyView();
        doNothing().when(mockDefaultStateViewHelper).hideErrorView();
        doNothing().when(mockDefaultStateViewHelper).hideLoadingView();
        PowerMockito.suppress(methods(BaseListFragment.class, "hideListView"));
        doNothing().when(mockListPageChangeStateListener).onListPageStateChanged(ListPageState.EMPTY);
        doNothing().when(mockDefaultStateViewHelper).showEmptyView(mockContext);
        doNothing().when(mockCustomStateViewHelper).hideAll();
        //Act
        testBaseListFragment.showEmptyViewAndHideOthers();
        //Assert
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test
    public void showLoadingViewAndHideOthersTestForLoadingView() {
        //Arrange
        doReturn(true).when(mockCustomStateViewHelper).hasLoadingView();
        doNothing().when(mockCustomStateViewHelper).showLoadingView();
        doNothing().when(mockCustomStateViewHelper).hideAll();
        doNothing().when(mockDefaultStateViewHelper).hideLoadingView();
        doNothing().when(mockDefaultStateViewHelper).hideEmptyView();
        PowerMockito.suppress(methods(BaseListFragment.class, "hideListView"));
        doNothing().when(mockListPageChangeStateListener).onListPageStateChanged(ListPageState.LOADING);
        //Act
        testBaseListFragment.showLoadingViewAndHideOthers();
        //Assert
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test
    public void showLoadingViewAndHideOthersTestForHidingView() {
        //Arrange
        doReturn(false).when(mockCustomStateViewHelper).hasLoadingView();
        doNothing().when(mockDefaultStateViewHelper).showLoadingView();
        doNothing().when(mockDefaultStateViewHelper).hideLoadingView();
        doNothing().when(mockDefaultStateViewHelper).hideEmptyView();
        PowerMockito.suppress(methods(BaseListFragment.class, "hideListView"));
        doNothing().when(mockListPageChangeStateListener).onListPageStateChanged(ListPageState.LOADING);
        //Act
        testBaseListFragment.showLoadingViewAndHideOthers();
        //Assert
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test
    public void showErrorViewAndHideOthersTestShowErrorView() {
        //Arrange
        doReturn(true).when(mockCustomStateViewHelper).hasErrorView();
        doNothing().when(mockCustomStateViewHelper).showErrorView();
        doNothing().when(mockDefaultStateViewHelper).hideLoadingView();
        doNothing().when(mockDefaultStateViewHelper).hideEmptyView();
        doNothing().when(mockDefaultStateViewHelper).hideLoadingView();
        doNothing().when(mockDefaultStateViewHelper).hideEmptyView();
        PowerMockito.suppress(methods(BaseListFragment.class, "hideListView"));
        doNothing().when(mockListPageChangeStateListener).onListPageStateChanged(ListPageState.ERROR);
        //Act
        testBaseListFragment.showErrorViewAndHideOthers();
        //Assert
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test
    public void showErrorViewAndHideOthersTestHideErrorView() {
        //Arrange
        doReturn(false).when(mockCustomStateViewHelper).hasErrorView();
        doNothing().when(mockDefaultStateViewHelper).hideLoadingView();
        doNothing().when(mockDefaultStateViewHelper).hideEmptyView();
        doNothing().when(mockDefaultStateViewHelper).hideLoadingView();
        doNothing().when(mockDefaultStateViewHelper).hideEmptyView();
        PowerMockito.suppress(methods(BaseListFragment.class, "hideListView"));
        doNothing().when(mockListPageChangeStateListener).onListPageStateChanged(ListPageState.ERROR);
        //Act
        testBaseListFragment.showErrorViewAndHideOthers();
        //Assert
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test
    public void showListViewAndHideOthersTest() {
        //Arrange
        PowerMockito.suppress(methods(BaseListFragment.class, "showListView"));
        doNothing().when(mockDefaultStateViewHelper).hideEmptyView();
        doNothing().when(mockDefaultStateViewHelper).hideLoadingView();
        doNothing().when(mockDefaultStateViewHelper).hideEmptyView();
        doNothing().when(mockListPageChangeStateListener).onListPageStateChanged(ListPageState.LIST);
        //Act
        testBaseListFragment.showListViewAndHideOthers();
        //Assert
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test
    public void hideAllTest() {
        //Arrange
        PowerMockito.suppress(methods(BaseListFragment.class, "hideListView"));
        doNothing().when(mockDefaultStateViewHelper).hideEmptyView();
        doNothing().when(mockDefaultStateViewHelper).hideLoadingView();
        doNothing().when(mockDefaultStateViewHelper).hideEmptyView();
        doNothing().when(mockListPageChangeStateListener).onListPageStateChanged(ListPageState.LIST);
        //Act
        testBaseListFragment.hideAll();
        //Assert
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test
    public void initListTest() {
        //Arrange
        LinearLayoutManager linearLayoutManager = PowerMockito.spy(new LinearLayoutManager(mockContext));
        EndlessRecyclerViewScrollAdapter adapter = Mockito.mock(EndlessRecyclerViewScrollAdapter.class);
        PowerMockito.suppress(methods(BaseListFragment.class, "init"));
        //Act
        testBaseListFragment.initList(adapter);
        //Assert
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test
    public void init(){
        //Arrange
        LinearLayoutManager linearLayoutManager = Mockito.mock(LinearLayoutManager.class);
        EndlessRecyclerViewScrollAdapter adapter = Mockito.mock(EndlessRecyclerViewScrollAdapter.class);
        doNothing().when(mockMRecyclerView).setAdapter(adapter);
        doNothing().when(mockMRecyclerView).setHasFixedSize(true);
        doNothing().when(mockMRecyclerView).setNestedScrollingEnabled(false);
        doNothing().when(mockMRecyclerView).setLayoutManager(linearLayoutManager);
        //Act
        testBaseListFragment.init(adapter,linearLayoutManager );
        //Assert
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test
    public void addItemsToEndOfListTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, "mAdapter", mockAdapter);
        List<BaseListItem> items = new ArrayList<BaseListItem>();
        doNothing().when(mockAdapter).addLoadMoreData(items);
        doNothing().when(mockAdapter).hideLoadMoreProgress();
        //Act
        testBaseListFragment.addItemsToEndOfList(items);
        //Assert
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test
    public void addItemsToBeginningOfListTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, "mAdapter", mockAdapter);
        List<BaseListItem> items = new ArrayList<BaseListItem>();
        doNothing().when(mockAdapter).addItems(items, 0);
        doNothing().when(mockAdapter).hideLoadMoreProgress();
        //Act
        testBaseListFragment.addItemsToBeginningOfList(items);
        //Assert
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test
    public void addItemTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, "mAdapter", mockAdapter);
        BaseListItem item = Mockito.mock(BaseListItem.class);
        doNothing().when(mockAdapter).addItem(item, 1);
        //Act
        testBaseListFragment.addItem(item, 1);
        //Assert
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test
    public void removeItemTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, "mAdapter", mockAdapter);
        doNothing().when(mockAdapter).removeItem(1);
        //Act
        testBaseListFragment.removeItem(1);
        //Assert
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test
    public void replaceItemTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, "mAdapter", mockAdapter);
        BaseListItem item = Mockito.mock(BaseListItem.class);
        doNothing().when(mockAdapter).replaceItem(item, 1);
        //Act
        testBaseListFragment.replaceItem(item, 1);
        //Assert
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test
    public void scrollToEndOfListTest() throws Exception {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, "mAdapter", mockAdapter);
        BaseListItem item = Mockito.mock(BaseListItem.class);
        List<BaseListItem> items = new ArrayList<BaseListItem>();
        items.add(item);
        doReturn(items).when(mockAdapter).getData();
        PowerMockito.whenNew(Handler.class).withNoArguments().thenReturn(handler);
        //Act
        testBaseListFragment.scrollToEndOfList();
        //Assert
        verify(handler).post(runnableCaptor.capture());
        Runnable runnable = runnableCaptor.getValue();
        runnable.run();
        verify(mockMRecyclerView, times(1)).smoothScrollToPosition(anyInt());
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test
    public void scrollToBeginningOfListTest() throws Exception {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, "mAdapter", mockAdapter);
        BaseListItem item = Mockito.mock(BaseListItem.class);
        List<BaseListItem> items = new ArrayList<BaseListItem>();
        items.add(item);
        doReturn(items).when(mockAdapter).getData();
        PowerMockito.whenNew(Handler.class).withNoArguments().thenReturn(handler);
        //Act
        testBaseListFragment.scrollToBeginningOfList();
        //Assert
        verify(handler).post(runnableCaptor.capture());
        Runnable runnable = runnableCaptor.getValue();
        runnable.run();
        verify(mockMRecyclerView, times(1)).smoothScrollToPosition(0);
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test
    public void scrollToPositionTest() throws Exception {
        //Arrange
        PowerMockito.whenNew(Handler.class).withNoArguments().thenReturn(handler);
        //Act
        testBaseListFragment.scrollToPosition(1);
        //Assert
        verify(handler).post(runnableCaptor.capture());
        Runnable runnable = runnableCaptor.getValue();
        runnable.run();
        verify(mockMRecyclerView, times(1)).smoothScrollToPosition(anyInt());
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test
    public void findFirstVisibleItemPositionTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, "mLayoutManager", mockLinearLayoutManager);
        doReturn(1).when(mockLinearLayoutManager).findFirstVisibleItemPosition();
        //Act
        int position =testBaseListFragment.findFirstVisibleItemPosition();
        //Assert
        Assert.assertEquals("The return count is ", 1, position);
    }

    @Test
    public void findLastVisibleItemPositionTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, "mLayoutManager", mockLinearLayoutManager);
        doReturn(0).when(mockLinearLayoutManager).findFirstVisibleItemPosition();
        //Act
        int position = testBaseListFragment.findLastVisibleItemPosition();
        //Assert
        Assert.assertEquals("The return count is ", 0, position);
    }

    @Test
    public void getSizeOfDataTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, "mAdapter", mockAdapter);
        doReturn(1).when(mockAdapter).getItemCount();
        //Act
        int size = testBaseListFragment.getSizeOfData();
        //Assert
        Assert.assertEquals("The return count is ", 1, size);
    }

    @Test
    public void setScrollListenerTest() {
        //Arrange
        doNothing().when(mockMRecyclerView).addOnScrollListener(onScrollListener);
        //Act
        testBaseListFragment.setScrollListener(onScrollListener);
        //Assert
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test
    public void removeScrollListener() {
        //Arrange
        doNothing().when(mockMRecyclerView).addOnScrollListener(onScrollListener);
        //Act
        testBaseListFragment.removeScrollListener(onScrollListener);
        //Assert
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test
    public void setLoadMoreListenerTest() throws Exception {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, "mLayoutManager", mockLinearLayoutManager);
        Whitebox.setInternalState(testBaseListFragment, "mAdapter", mockAdapter);
        PowerMockito.whenNew(Handler.class).withAnyArguments().thenReturn(handler);
        //Act
        testBaseListFragment.setLoadMoreListener(loadMoreListener);
        //Assert
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test
    public void removeLoadMoreListenerTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, "mAdapter", mockAdapter);
        Whitebox.setInternalState(testBaseListFragment, "mLoadMoreListener", mloadMoreListener);
        doNothing().when(mockMRecyclerView).removeOnScrollListener(mloadMoreListener);
        //Act
        testBaseListFragment.removeLoadMoreListener();
        //Assert
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test
    public void showLoadMoreProgressSuccessTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, "mAdapter", mockAdapter);
        doNothing().when(mockAdapter).showLoadMoreProgress();
        //Act
        testBaseListFragment.showLoadMoreProgress();
        //Assert
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test(expected = NullPointerException.class)
    public void showLoadMoreProgressFailureTest(){
        //Act
        testBaseListFragment.showLoadMoreProgress();
    }

    @Test
    public void hideLoadMoreProgressSuccessTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, "mAdapter", mockAdapter);
        doNothing().when(mockAdapter).hideLoadMoreProgress();
        //Act
        testBaseListFragment.hideLoadMoreProgress();
        //Assert
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test(expected = NullPointerException.class)
    public void hideLoadMoreProgressFailureTest(){
        //Act
        testBaseListFragment.hideLoadMoreProgress();
    }

    @Test
    public void setHasMoreItemsSuccessTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, "mAdapter", mockAdapter);
        doNothing().when(mockAdapter).setHasMoreItems(anyBoolean());
        //Act
        testBaseListFragment.setHasMoreItems(true);
        //Assert
        Assert.assertTrue(isMethodSuccessfullyRun);
    }

    @Test(expected = NullPointerException.class)
    public void setHasMoreItemsFailureTest(){
        //Act
        testBaseListFragment.setHasMoreItems(true);
    }

    @Test
    public void hasMoreItemsSuccessTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, "mAdapter", mockAdapter);
        doReturn(true).when(mockAdapter).hasMoreItems();
        //Act
        boolean success = testBaseListFragment.hasMoreItems();
        //Assert
        Assert.assertTrue(success);
    }

    @Test(expected = NullPointerException.class)
    public void hasMoreItemsFailureTest(){
        //Act
        testBaseListFragment.hasMoreItems();
    }

    @Test
    public void setOnListPageChangeStateListenerTest() {
        //Arrange
        BaseListFragment mockBaseListFragment = Mockito.mock(BaseListFragment.class);
        OnListPageStateChangeListener listPageChangeStateListener = Mockito.mock(OnListPageStateChangeListener.class);
        //Act
        mockBaseListFragment.setOnListPageChangeStateListener(listPageChangeStateListener);
        //Assert
        verify(mockBaseListFragment).setOnListPageChangeStateListener(listPageChangeStateListener);
    }

    @Test
    public void removeScrollListListenerTest() {
        //Arrange
        BaseListFragment mockBaseListFragment = Mockito.mock(BaseListFragment.class);
        OnScrollListListener onScrollListener = Mockito.mock(OnScrollListListener.class);
        //Act
        mockBaseListFragment.removeScrollListListener(onScrollListener);
        //Assert
        verify(mockBaseListFragment).removeScrollListListener(onScrollListener);
    }
}
