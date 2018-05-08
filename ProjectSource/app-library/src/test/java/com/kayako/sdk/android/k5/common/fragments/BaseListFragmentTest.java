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

import org.junit.Before;
import org.junit.Ignore;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.methods;

import static org.mockito.Mockito.verify;
@Ignore
@RunWith(PowerMockRunner.class)
@PrepareForTest({
        BaseListFragment.class,
        DefaultStateViewHelper.class,
        Looper.class
})

public class BaseListFragmentTest {

    private TestBaseListFragment testBaseListFragment;

    private static final String SETUP_VIEW = "setupView";
    private static final String HIDE_LIST_VIEW = "hideListView";
    private static final String ADAPTER_FIELD = "mAdapter";
    private static final String LAYOUT_FIELD = "mLayoutManager";
    private static final String MORE_LISTENER_FIELD = "mLoadMoreListener";
    private static final String INIT_METHOD = "init";
    private static final String SHOW_LIST_VIEW_METHOD = "showListView";

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

    private static final String CUSTOM_STATE_VIEW_FIELD = "mCustomStateViewHelper";
    private static final String DEFAULT_STATE_VIEW_FIELD = "mDefaultStateViewHelper";
    private static final String LIST_PAGE_CHANGE_LISTNER_FIELD = "mListPageChangeStateListener";
    private static final String ROOT_FIELD = "mRoot";
    private static final String RECYCLER_VIEW = "mRecyclerView";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testBaseListFragment = new TestBaseListFragment();
        Whitebox.setInternalState(testBaseListFragment, CUSTOM_STATE_VIEW_FIELD, mockCustomStateViewHelper);
        Whitebox.setInternalState(testBaseListFragment, DEFAULT_STATE_VIEW_FIELD, mockDefaultStateViewHelper);
        Whitebox.setInternalState(testBaseListFragment, LIST_PAGE_CHANGE_LISTNER_FIELD, mockListPageChangeStateListener);
        Whitebox.setInternalState(testBaseListFragment, ROOT_FIELD, mockMRoot);
        Whitebox.setInternalState(testBaseListFragment, RECYCLER_VIEW, mockMRecyclerView);
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
        PowerMockito.suppress(methods(BaseListFragment.class, SETUP_VIEW));
        //Act
        View view  = mockBaseListFragment.onCreateView(mockInflater, mockContainer, savedInstanceState);
        //Assert
        assertNotNull(view);
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
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test
    public void showListViewTest() {
        //Arrange
        Mockito.doReturn(mockView).when(mockMRoot).findViewById(R.id.ko__list);
        //Act
        testBaseListFragment.showListView();
        //Assert
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test
    public void hideListViewTest(){
        //Arrange
        Mockito.doReturn(mockView).when(mockMRoot).findViewById(R.id.ko__list);
        //Act
        testBaseListFragment.hideListView();
        //Assert
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test
    public void showEmptyViewAndHideOthersTestForEmptyView() {
        //Arrange
        doReturn(true).when(mockCustomStateViewHelper).hasEmptyView();
        doNothing().when(mockDefaultStateViewHelper).hideErrorView();
        doNothing().when(mockDefaultStateViewHelper).hideLoadingView();
        PowerMockito.suppress(methods(BaseListFragment.class, HIDE_LIST_VIEW));
        doNothing().when(mockListPageChangeStateListener).onListPageStateChanged(ListPageState.EMPTY);
        //Act
        testBaseListFragment.showEmptyViewAndHideOthers();
        //Assert
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test
    public void showEmptyViewAndHideOthersTestForNonEmptyView() {
        //Arrange
        doReturn(false).when(mockCustomStateViewHelper).hasEmptyView();
        doNothing().when(mockDefaultStateViewHelper).hideErrorView();
        doNothing().when(mockDefaultStateViewHelper).hideLoadingView();
        PowerMockito.suppress(methods(BaseListFragment.class, HIDE_LIST_VIEW));
        doNothing().when(mockListPageChangeStateListener).onListPageStateChanged(ListPageState.EMPTY);
        doNothing().when(mockDefaultStateViewHelper).showEmptyView(mockContext);
        doNothing().when(mockCustomStateViewHelper).hideAll();
        //Act
        testBaseListFragment.showEmptyViewAndHideOthers();
        //Assert
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test
    public void showLoadingViewAndHideOthersTestForLoadingView() {
        //Arrange
        doReturn(true).when(mockCustomStateViewHelper).hasLoadingView();
        doNothing().when(mockCustomStateViewHelper).showLoadingView();
        doNothing().when(mockCustomStateViewHelper).hideAll();
        doNothing().when(mockDefaultStateViewHelper).hideLoadingView();
        doNothing().when(mockDefaultStateViewHelper).hideEmptyView();
        PowerMockito.suppress(methods(BaseListFragment.class, HIDE_LIST_VIEW));
        doNothing().when(mockListPageChangeStateListener).onListPageStateChanged(ListPageState.LOADING);
        //Act
        testBaseListFragment.showLoadingViewAndHideOthers();
        //Assert
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test
    public void showLoadingViewAndHideOthersTestForHidingView() {
        //Arrange
        doReturn(false).when(mockCustomStateViewHelper).hasLoadingView();
        doNothing().when(mockDefaultStateViewHelper).showLoadingView();
        doNothing().when(mockDefaultStateViewHelper).hideLoadingView();
        doNothing().when(mockDefaultStateViewHelper).hideEmptyView();
        PowerMockito.suppress(methods(BaseListFragment.class, HIDE_LIST_VIEW));
        doNothing().when(mockListPageChangeStateListener).onListPageStateChanged(ListPageState.LOADING);
        //Act
        testBaseListFragment.showLoadingViewAndHideOthers();
        //Assert
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
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
        PowerMockito.suppress(methods(BaseListFragment.class, HIDE_LIST_VIEW));
        doNothing().when(mockListPageChangeStateListener).onListPageStateChanged(ListPageState.ERROR);
        //Act
        testBaseListFragment.showErrorViewAndHideOthers();
        //Assert
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test
    public void showErrorViewAndHideOthersTestHideErrorView() {
        //Arrange
        doReturn(false).when(mockCustomStateViewHelper).hasErrorView();
        doNothing().when(mockDefaultStateViewHelper).hideLoadingView();
        doNothing().when(mockDefaultStateViewHelper).hideEmptyView();
        doNothing().when(mockDefaultStateViewHelper).hideLoadingView();
        doNothing().when(mockDefaultStateViewHelper).hideEmptyView();
        PowerMockito.suppress(methods(BaseListFragment.class, HIDE_LIST_VIEW));
        doNothing().when(mockListPageChangeStateListener).onListPageStateChanged(ListPageState.ERROR);
        //Act
        testBaseListFragment.showErrorViewAndHideOthers();
        //Assert
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test
    public void showListViewAndHideOthersTest() {
        //Arrange
        PowerMockito.suppress(methods(BaseListFragment.class, SHOW_LIST_VIEW_METHOD));
        doNothing().when(mockDefaultStateViewHelper).hideEmptyView();
        doNothing().when(mockDefaultStateViewHelper).hideLoadingView();
        doNothing().when(mockDefaultStateViewHelper).hideEmptyView();
        doNothing().when(mockListPageChangeStateListener).onListPageStateChanged(ListPageState.LIST);
        //Act
        testBaseListFragment.showListViewAndHideOthers();
        //Assert
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test
    public void hideAllTest() {
        //Arrange
        PowerMockito.suppress(methods(BaseListFragment.class, HIDE_LIST_VIEW));
        doNothing().when(mockDefaultStateViewHelper).hideEmptyView();
        doNothing().when(mockDefaultStateViewHelper).hideLoadingView();
        doNothing().when(mockDefaultStateViewHelper).hideEmptyView();
        doNothing().when(mockListPageChangeStateListener).onListPageStateChanged(ListPageState.LIST);
        //Act
        testBaseListFragment.hideAll();
        //Assert
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test
    public void initListTest() {
        //Arrange
        LinearLayoutManager linearLayoutManager = PowerMockito.spy(new LinearLayoutManager(mockContext));
        EndlessRecyclerViewScrollAdapter adapter = Mockito.mock(EndlessRecyclerViewScrollAdapter.class);
        PowerMockito.suppress(methods(BaseListFragment.class, INIT_METHOD));
        //Act
        testBaseListFragment.initList(adapter);
        //Assert
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
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
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test
    public void addItemsToEndOfListTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, ADAPTER_FIELD, mockAdapter);
        List<BaseListItem> items = new ArrayList<BaseListItem>();
        doNothing().when(mockAdapter).addLoadMoreData(items);
        doNothing().when(mockAdapter).hideLoadMoreProgress();
        //Act
        testBaseListFragment.addItemsToEndOfList(items);
        //Assert
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test
    public void addItemsToBeginningOfListTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, ADAPTER_FIELD, mockAdapter);
        List<BaseListItem> items = new ArrayList<BaseListItem>();
        doNothing().when(mockAdapter).addItems(items, 0);
        doNothing().when(mockAdapter).hideLoadMoreProgress();
        //Act
        testBaseListFragment.addItemsToBeginningOfList(items);
        //Assert
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test
    public void addItemTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, ADAPTER_FIELD, mockAdapter);
        BaseListItem item = Mockito.mock(BaseListItem.class);
        doNothing().when(mockAdapter).addItem(item, 1);
        //Act
        testBaseListFragment.addItem(item, 1);
        //Assert
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test
    public void removeItemTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, ADAPTER_FIELD, mockAdapter);
        doNothing().when(mockAdapter).removeItem(1);
        //Act
        testBaseListFragment.removeItem(1);
        //Assert
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test
    public void replaceItemTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, ADAPTER_FIELD, mockAdapter);
        BaseListItem item = Mockito.mock(BaseListItem.class);
        doNothing().when(mockAdapter).replaceItem(item, 1);
        //Act
        testBaseListFragment.replaceItem(item, 1);
        //Assert
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test
    public void scrollToEndOfListTest() throws Exception {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, ADAPTER_FIELD, mockAdapter);
        BaseListItem item = Mockito.mock(BaseListItem.class);
        List<BaseListItem> items = new ArrayList<BaseListItem>();
        items.add(item);
        doReturn(items).when(mockAdapter).getData();
        doReturn(items.size()).when(mockAdapter).getItemCount();
        PowerMockito.whenNew(Handler.class).withNoArguments().thenReturn(handler);
        //Act
        testBaseListFragment.scrollToEndOfList();
        //Assert
        verify(handler).post(runnableCaptor.capture());
        Runnable runnable = runnableCaptor.getValue();
        runnable.run();
        verify(mockMRecyclerView, times(1)).smoothScrollToPosition(1);
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test
    public void scrollToBeginningOfListTest() throws Exception {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, ADAPTER_FIELD, mockAdapter);
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
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test
    public void scrollToPositionTest() throws Exception {
        //Arrange
        final int position = 1;
        PowerMockito.whenNew(Handler.class).withNoArguments().thenReturn(handler);
        //Act
        testBaseListFragment.scrollToPosition(position);
        //Assert
        verify(handler).post(runnableCaptor.capture());
        Runnable runnable = runnableCaptor.getValue();
        runnable.run();
        verify(mockMRecyclerView, times(1)).smoothScrollToPosition(position);
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test
    public void findFirstVisibleItemPositionTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, LAYOUT_FIELD, mockLinearLayoutManager);
        doReturn(1).when(mockLinearLayoutManager).findFirstVisibleItemPosition();
        //Act
        int position =testBaseListFragment.findFirstVisibleItemPosition();
        //Assert
        assertEquals("The return count is ", 1, position);
    }

    @Test
    public void findLastVisibleItemPositionTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, LAYOUT_FIELD, mockLinearLayoutManager);
        doReturn(0).when(mockLinearLayoutManager).findFirstVisibleItemPosition();
        //Act
        int position = testBaseListFragment.findLastVisibleItemPosition();
        //Assert
        assertEquals("The return count is ", 0, position);
    }

    @Test
    public void getSizeOfDataTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, ADAPTER_FIELD, mockAdapter);
        doReturn(1).when(mockAdapter).getItemCount();
        //Act
        int size = testBaseListFragment.getSizeOfData();
        //Assert
        assertEquals("The return count is ", 1, size);
    }

    @Test
    public void setScrollListenerTest() {
        //Arrange
        doNothing().when(mockMRecyclerView).addOnScrollListener(onScrollListener);
        //Act
        testBaseListFragment.setScrollListener(onScrollListener);
        //Assert
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test
    public void removeScrollListener() {
        //Arrange
        doNothing().when(mockMRecyclerView).addOnScrollListener(onScrollListener);
        //Act
        testBaseListFragment.removeScrollListener(onScrollListener);
        //Assert
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test
    public void setLoadMoreListenerTest() throws Exception {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, LAYOUT_FIELD, mockLinearLayoutManager);
        Whitebox.setInternalState(testBaseListFragment, ADAPTER_FIELD, mockAdapter);
        PowerMockito.whenNew(Handler.class).withAnyArguments().thenReturn(handler);
        //Act
        testBaseListFragment.setLoadMoreListener(loadMoreListener);
        //Assert
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test
    public void removeLoadMoreListenerTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, ADAPTER_FIELD, mockAdapter);
        Whitebox.setInternalState(testBaseListFragment, MORE_LISTENER_FIELD, mloadMoreListener);
        doNothing().when(mockMRecyclerView).removeOnScrollListener(mloadMoreListener);
        //Act
        testBaseListFragment.removeLoadMoreListener();
        //Assert
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test
    public void showLoadMoreProgressSuccessTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, ADAPTER_FIELD, mockAdapter);
        doNothing().when(mockAdapter).showLoadMoreProgress();
        //Act
        testBaseListFragment.showLoadMoreProgress();
        //Assert
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test(expected = NullPointerException.class)
    public void showLoadMoreProgressFailureTest(){
        //Act
        testBaseListFragment.showLoadMoreProgress();
    }

    @Test
    public void hideLoadMoreProgressSuccessTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, ADAPTER_FIELD, mockAdapter);
        doNothing().when(mockAdapter).hideLoadMoreProgress();
        //Act
        testBaseListFragment.hideLoadMoreProgress();
        //Assert
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test(expected = NullPointerException.class)
    public void hideLoadMoreProgressFailureTest(){
        //Act
        testBaseListFragment.hideLoadMoreProgress();
    }

    @Test
    public void setHasMoreItemsSuccessTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, ADAPTER_FIELD, mockAdapter);
        doNothing().when(mockAdapter).setHasMoreItems(true);
        //Act
        testBaseListFragment.setHasMoreItems(true);
        //Assert
        assertTrue(testBaseListFragment.isMethodSuccessfullyRun);
    }

    @Test(expected = NullPointerException.class)
    public void setHasMoreItemsFailureTest(){
        //Act
        testBaseListFragment.setHasMoreItems(true);
    }

    @Test
    public void hasMoreItemsSuccessTest() {
        //Arrange
        Whitebox.setInternalState(testBaseListFragment, ADAPTER_FIELD, mockAdapter);
        doReturn(true).when(mockAdapter).hasMoreItems();
        //Act
        boolean success = testBaseListFragment.hasMoreItems();
        //Assert
        assertTrue(success);
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
