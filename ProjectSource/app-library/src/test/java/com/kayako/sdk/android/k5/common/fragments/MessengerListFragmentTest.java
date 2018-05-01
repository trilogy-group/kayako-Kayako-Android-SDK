package com.kayako.sdk.android.k5.common.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollAdapter;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollListener;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerAdapter;
import com.kayako.sdk.android.k5.common.viewhelpers.CustomStateViewHelper;
import com.kayako.sdk.android.k5.common.viewhelpers.DefaultStateViewHelper;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        MessengerListFragment.class,
        LinearLayoutManager.class,
        MessengerAdapter.class,
        Handler.class,
        View.class,
        BaseListFragment.class,
        DefaultStateViewHelper.class,
        CustomStateViewHelper.class
})
public class MessengerListFragmentTest {
    private static final int INITIAL_POSITION = 0;
    private static MessengerListFragment messengerListFragment;

    @Mock
    private Bundle bundle;

    @Mock
    private View view;

    @Mock
    private LinearLayoutManager linearLayoutManager;

    @Mock
    private Context context;

    @Mock
    private MessengerAdapter messengerAdapter;

    @Mock
    private RecyclerView recyclerView;

    @Mock
    private BaseListItem baseListItem;

    @Mock
    private MessengerAdapter.OnItemClickListener onItemClickListener;

    @Mock
    private MessengerAdapter.OnAvatarClickListener onAvatarClickListener;

    @Mock
    private MessengerAdapter.OnAttachmentClickListener onAttachmentClickListener;

    @Mock
    private ViewStub viewStub;

    @Mock
    private TextView textView;

    @Mock
    private LinearLayout linearLayout;

    @Mock
    private CustomStateViewHelper customStateViewHelper;

    @Mock
    private DefaultStateViewHelper defaultStateViewHelper;

    @Mock
    private RecyclerView.OnScrollListener onScrollListener;

    @Mock
    private  EndlessRecyclerViewScrollAdapter.OnLoadMoreListener onLoadMoreListener;

    @BeforeClass
    public static void setUpClass(){
        messengerListFragment = new MessengerListFragment();
    }

    @Test
    public void givenSavedInstanceWhenOnCreateThenCallOnCreate(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        doNothing().when((BaseListFragment)messengerListFragmentPartialMock).onCreate(bundle);

        //Act
        messengerListFragmentPartialMock.onCreate(bundle);

        //Assert
        verify(messengerListFragmentPartialMock).onCreate(bundle);
    }

    @Test
    public void givenSavedInstanceViewWhenOnViewCreateThenCallOnViewCreate(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        doNothing().when((BaseListFragment)messengerListFragmentPartialMock).onViewCreated(view,bundle);

        //Act
        messengerListFragmentPartialMock.onViewCreated(view,bundle);

        //Assert
        verify(messengerListFragmentPartialMock).onViewCreated(view,bundle);
    }

    @Test
    public void givenSavedInstanceWhenOnActivityCreatedThenCallOnActivityCreated(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        doNothing().when((BaseListFragment)messengerListFragmentPartialMock).onActivityCreated(bundle);

        //Act
        messengerListFragmentPartialMock.onActivityCreated(bundle);

        //Assert
        verify(messengerListFragmentPartialMock).onActivityCreated(bundle);
    }

    @Test
    public void givenBaseListItemListWhenInitMessengerListThenCallInitMessengerList() throws Exception {
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        doNothing().when((BaseListFragment)messengerListFragmentPartialMock).scrollToBeginningOfList();
        List<BaseListItem> testList = new ArrayList<>();
        testList.add(baseListItem);
        whenNew(LinearLayoutManager.class).withAnyArguments().thenReturn(linearLayoutManager);
        messengerListFragmentPartialMock.mRoot = view;
        when(view.getContext()).thenReturn(context);
        whenNew(MessengerAdapter.class).withAnyArguments().thenReturn(messengerAdapter);
        messengerListFragmentPartialMock.mRecyclerView = recyclerView;
        when(messengerAdapter.getData()).thenReturn(testList);
        mockStatic(Handler.class);
        suppress(method(Handler.class,"post"));

        //Act
        messengerListFragmentPartialMock.initMessengerList(testList);

        //Assert
        verify(messengerListFragmentPartialMock).scrollToEndOfList();
    }

    @Test
    public void givenBaseListItemListWhenReplaceMessengerListThenCallReplaceAllData(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        List<BaseListItem> testList = new ArrayList<>();
        testList.add(baseListItem);
        messengerListFragmentPartialMock.mRoot = view;
        messengerListFragmentPartialMock.mRecyclerView = recyclerView;
        when(messengerAdapter.getData()).thenReturn(testList);
        mockStatic(Handler.class);
        suppress(method(Handler.class,"post"));
        messengerListFragmentPartialMock.initMessengerList(messengerAdapter);

        //Act
        messengerListFragmentPartialMock.replaceMessengerList(testList);

        //Assert
        verify(messengerAdapter).replaceAllData(testList, Boolean.FALSE);
    }

    @Test
    public void givenOnItemClickListenerWhenSetOnItemClickListenerThenCallSetListener(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        List<BaseListItem> testList = new ArrayList<>();
        testList.add(baseListItem);
        messengerListFragmentPartialMock.mRoot = view;
        messengerListFragmentPartialMock.mRecyclerView = recyclerView;
        when(messengerAdapter.getData()).thenReturn(testList);
        mockStatic(Handler.class);
        suppress(method(Handler.class,"post"));
        messengerListFragmentPartialMock.initMessengerList(messengerAdapter);

        //Act
        messengerListFragmentPartialMock.setOnItemClickListener(onItemClickListener);

        //Assert
        verify(messengerAdapter).setOnItemClickListener(onItemClickListener);
    }

    @Test
    public void givenOnAvatarClickListenerWhenSetOnAvatarClickListenerThenCallSetListener(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        List<BaseListItem> testList = new ArrayList<>();
        testList.add(baseListItem);
        messengerListFragmentPartialMock.mRoot = view;
        messengerListFragmentPartialMock.mRecyclerView = recyclerView;
        when(messengerAdapter.getData()).thenReturn(testList);
        mockStatic(Handler.class);
        suppress(method(Handler.class,"post"));
        messengerListFragmentPartialMock.initMessengerList(messengerAdapter);

        //Act
        messengerListFragmentPartialMock.setOnAvatarClickListener(onAvatarClickListener);

        //Assert
        verify(messengerAdapter).setOnAvatarClickListener(onAvatarClickListener);
    }

    @Test
    public void givenOnListAttachmentClickListenerWhenSetOnClickListenerThenCallSetListener(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        List<BaseListItem> testList = new ArrayList<>();
        testList.add(baseListItem);
        messengerListFragmentPartialMock.mRoot = view;
        messengerListFragmentPartialMock.mRecyclerView = recyclerView;
        when(messengerAdapter.getData()).thenReturn(testList);
        mockStatic(Handler.class);
        suppress(method(Handler.class,"post"));
        messengerListFragmentPartialMock.initMessengerList(messengerAdapter);

        //Act
        messengerListFragmentPartialMock.setOnListAttachmentClickListener(onAttachmentClickListener);

        //Assert
        verify(messengerAdapter).setOnAttachmentClickListener(onAttachmentClickListener);
    }

    @Test
    public void givenBaseListItemListWhenAddItemsToEndOfListThenCallAddItems(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        List<BaseListItem> testList = new ArrayList<>();
        testList.add(baseListItem);
        messengerListFragmentPartialMock.mRoot = view;
        messengerListFragmentPartialMock.mRecyclerView = recyclerView;
        when(messengerAdapter.getData()).thenReturn(testList);
        mockStatic(Handler.class);
        suppress(method(Handler.class,"post"));
        messengerListFragmentPartialMock.initMessengerList(messengerAdapter);

        //Act
        messengerListFragmentPartialMock.addItemsToEndOfList(testList);

        //Assert
        verify(messengerAdapter).hideLoadMoreProgress();
    }

    @Test
    public void givenBaseListItemListWhenAddItemsToBeginningOfListThenCallAddItems(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        List<BaseListItem> testList = new ArrayList<>();
        testList.add(baseListItem);
        messengerListFragmentPartialMock.mRoot = view;
        messengerListFragmentPartialMock.mRecyclerView = recyclerView;
        when(messengerAdapter.getData()).thenReturn(testList);
        mockStatic(Handler.class);
        suppress(method(Handler.class,"post"));
        messengerListFragmentPartialMock.initMessengerList(messengerAdapter);

        //Act
        messengerListFragmentPartialMock.addItemsToBeginningOfList(testList);

        //Assert
        verify(messengerAdapter).hideLoadMoreProgress();
    }

    @Test
    public void givenMessengerAdapterWhenScrollToBeginningOfListThenCallScroll(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        List<BaseListItem> testList = new ArrayList<>();
        testList.add(baseListItem);
        messengerListFragmentPartialMock.mRoot = view;
        messengerListFragmentPartialMock.mRecyclerView = recyclerView;
        when(messengerAdapter.getData()).thenReturn(testList);
        mockStatic(Handler.class);
        suppress(method(Handler.class,"post"));
        messengerListFragmentPartialMock.initMessengerList(messengerAdapter);

        //Act
        messengerListFragmentPartialMock.scrollToBeginningOfList();

        //Assert
        verify(messengerListFragmentPartialMock).scrollToBeginningOfList();
    }

    @Test
    public void givenPositionWhenScrollToPositionThenCallScroll(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        List<BaseListItem> testList = new ArrayList<>();
        testList.add(baseListItem);
        messengerListFragmentPartialMock.mRoot = view;
        messengerListFragmentPartialMock.mRecyclerView = recyclerView;
        when(messengerAdapter.getData()).thenReturn(testList);
        when(messengerAdapter.getItemCount()).thenReturn(testList.size());
        mockStatic(Handler.class);
        suppress(method(Handler.class,"post"));
        messengerListFragmentPartialMock.initMessengerList(messengerAdapter);

        //Act
        messengerListFragmentPartialMock.scrollToPosition(INITIAL_POSITION);

        //Assert
        verify(messengerListFragmentPartialMock).scrollToPosition(INITIAL_POSITION);
    }

    @Test
    public void givenManagerWhenFindFirstVisibleItemPositionThenReturnInteger(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        messengerListFragmentPartialMock.mRecyclerView = recyclerView;
        messengerListFragmentPartialMock.init(messengerAdapter, linearLayoutManager);
        when(linearLayoutManager.findLastVisibleItemPosition()).thenReturn(INITIAL_POSITION);

        //Act
        int returnedValue = messengerListFragmentPartialMock.findFirstVisibleItemPosition();

        //Assert
        assertEquals(INITIAL_POSITION, returnedValue);
    }

    @Test
    public void givenManagerWhenFindLastVisibleItemPositionThenReturnInteger(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        messengerListFragmentPartialMock.mRecyclerView = recyclerView;
        messengerListFragmentPartialMock.init(messengerAdapter, linearLayoutManager);
        when(linearLayoutManager.findLastVisibleItemPosition()).thenReturn(INITIAL_POSITION);

        //Act
        int returnedValue = messengerListFragmentPartialMock.findLastVisibleItemPosition();

        //Assert
        assertEquals(INITIAL_POSITION, returnedValue);
    }

    @Test
    public void givenManagerWhenShowEmptyViewAndHideOtherThenShowEmptyViewAndHideOthers(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        final View powerView = PowerMockito.mock(View.class);
        when(powerView.findViewById(R.id.ko__list)).thenReturn(recyclerView);
        when(powerView.findViewById(R.id.ko__stub_empty_state)).thenReturn(viewStub);
        when(powerView.findViewById(R.id.ko__stub_loading_state)).thenReturn(viewStub);
        when(powerView.findViewById(R.id.ko__stub_error_state)).thenReturn(viewStub);
        when(powerView.findViewById(R.id.ko__inflated_stub_empty_state)).thenReturn(powerView);
        when(powerView.findViewById(R.id.ko__empty_state_description)).thenReturn(textView);
        when(powerView.findViewById(R.id.ko__empty_state_title)).thenReturn(textView);
        suppress(method(DefaultStateViewHelper.class,"showEmptyView", eq(Context.class)));
        when(powerView.findViewById(R.id.ko__custom_state_container)).thenReturn(linearLayout);
        suppress(method(CustomStateViewHelper.class,"hideAll"));
        messengerListFragmentPartialMock.setupView(powerView);

        //Act
        messengerListFragmentPartialMock.showEmptyViewAndHideOthers();

        //Assert
        verify(messengerListFragmentPartialMock).showEmptyViewAndHideOthers();
    }

    @Test
    public void givenManagerWhenShowLoadingViewAndHideOthersThenShowLoadingViewAndHideOthers(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        final View powerView = PowerMockito.mock(View.class);
        when(powerView.findViewById(R.id.ko__list)).thenReturn(recyclerView);
        when(powerView.findViewById(R.id.ko__stub_empty_state)).thenReturn(viewStub);
        when(powerView.findViewById(R.id.ko__stub_loading_state)).thenReturn(viewStub);
        when(powerView.findViewById(R.id.ko__stub_error_state)).thenReturn(viewStub);
        when(powerView.findViewById(R.id.ko__inflated_stub_empty_state)).thenReturn(powerView);
        when(powerView.findViewById(R.id.ko__empty_state_description)).thenReturn(textView);
        when(powerView.findViewById(R.id.ko__empty_state_title)).thenReturn(textView);
        suppress(method(DefaultStateViewHelper.class,"showLoadingView"));
        when(powerView.findViewById(R.id.ko__custom_state_container)).thenReturn(linearLayout);
        suppress(method(CustomStateViewHelper.class,"hideAll"));
        messengerListFragmentPartialMock.setupView(powerView);

        //Act
        messengerListFragmentPartialMock.showLoadingViewAndHideOthers();

        //Assert
        verify(messengerListFragmentPartialMock).showLoadingViewAndHideOthers();
    }

    @Test
    public void givenManagerWhenShowErrorViewAndHideOthersThenShowErrorViewAndHideOthers(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        final View powerView = PowerMockito.mock(View.class);
        when(powerView.findViewById(R.id.ko__list)).thenReturn(recyclerView);
        when(powerView.findViewById(R.id.ko__stub_empty_state)).thenReturn(viewStub);
        when(powerView.findViewById(R.id.ko__stub_loading_state)).thenReturn(viewStub);
        when(powerView.findViewById(R.id.ko__stub_error_state)).thenReturn(viewStub);
        when(powerView.findViewById(R.id.ko__inflated_stub_empty_state)).thenReturn(powerView);
        when(powerView.findViewById(R.id.ko__empty_state_description)).thenReturn(textView);
        when(powerView.findViewById(R.id.ko__empty_state_title)).thenReturn(textView);
        suppress(method(DefaultStateViewHelper.class,"showErrorView", eq(Context.class)));
        when(powerView.findViewById(R.id.ko__custom_state_container)).thenReturn(linearLayout);
        suppress(method(CustomStateViewHelper.class,"hideAll"));
        messengerListFragmentPartialMock.setupView(powerView);

        //Act
        messengerListFragmentPartialMock.showErrorViewAndHideOthers();

        //Assert
        verify(messengerListFragmentPartialMock).showErrorViewAndHideOthers();
    }

    @Test
    public void givenManagerWhenShowListViewAndHideOthersThenShowListViewAndHideOthers(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        final View powerView = PowerMockito.mock(View.class);
        when(powerView.findViewById(R.id.ko__list)).thenReturn(recyclerView);
        when(powerView.findViewById(R.id.ko__stub_empty_state)).thenReturn(viewStub);
        when(powerView.findViewById(R.id.ko__stub_loading_state)).thenReturn(viewStub);
        when(powerView.findViewById(R.id.ko__stub_error_state)).thenReturn(viewStub);
        when(powerView.findViewById(R.id.ko__inflated_stub_empty_state)).thenReturn(powerView);
        when(powerView.findViewById(R.id.ko__empty_state_description)).thenReturn(textView);
        when(powerView.findViewById(R.id.ko__empty_state_title)).thenReturn(textView);
        //suppress(method(DefaultStateViewHelper.class,"showErrorView", eq(Context.class)));
        when(powerView.findViewById(R.id.ko__custom_state_container)).thenReturn(linearLayout);
        suppress(method(CustomStateViewHelper.class,"hideAll"));
        messengerListFragmentPartialMock.setupView(powerView);

        //Act
        messengerListFragmentPartialMock.showListViewAndHideOthers();

        //Assert
        verify(messengerListFragmentPartialMock).showListViewAndHideOthers();
    }

    @Test
    public void givenManagerWhenHideAllThenHideAllViews(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        final View powerView = PowerMockito.mock(View.class);
        when(powerView.findViewById(R.id.ko__list)).thenReturn(recyclerView);
        when(powerView.findViewById(R.id.ko__stub_empty_state)).thenReturn(viewStub);
        when(powerView.findViewById(R.id.ko__stub_loading_state)).thenReturn(viewStub);
        when(powerView.findViewById(R.id.ko__stub_error_state)).thenReturn(viewStub);
        when(powerView.findViewById(R.id.ko__inflated_stub_empty_state)).thenReturn(powerView);
        when(powerView.findViewById(R.id.ko__empty_state_description)).thenReturn(textView);
        when(powerView.findViewById(R.id.ko__empty_state_title)).thenReturn(textView);
        when(powerView.findViewById(R.id.ko__custom_state_container)).thenReturn(linearLayout);
        suppress(method(CustomStateViewHelper.class,"hideAll"));
        messengerListFragmentPartialMock.setupView(powerView);

        //Act
        messengerListFragmentPartialMock.hideAll();

        //Assert
        verify(messengerListFragmentPartialMock).hideAll();
    }

    @Test
    public void givenFragmentWhenGetCustomStateViewHelperThenGetCustomStateViewHelper() throws Exception {
        //Arrange
        whenNew(CustomStateViewHelper.class).withAnyArguments().thenReturn(customStateViewHelper);
        final View powerView = PowerMockito.mock(View.class);
        when(powerView.findViewById(R.id.ko__list)).thenReturn(recyclerView);
        when(powerView.findViewById(R.id.ko__stub_empty_state)).thenReturn(viewStub);
        when(powerView.findViewById(R.id.ko__stub_loading_state)).thenReturn(viewStub);
        when(powerView.findViewById(R.id.ko__stub_error_state)).thenReturn(viewStub);
        when(powerView.findViewById(R.id.ko__inflated_stub_empty_state)).thenReturn(powerView);
        when(powerView.findViewById(R.id.ko__empty_state_description)).thenReturn(textView);
        when(powerView.findViewById(R.id.ko__empty_state_title)).thenReturn(textView);
        when(powerView.findViewById(R.id.ko__custom_state_container)).thenReturn(linearLayout);
        suppress(method(CustomStateViewHelper.class,"hideAll"));
        messengerListFragment.setupView(powerView);

        //Act
        CustomStateViewHelper returnedValue = messengerListFragment.getCustomStateViewHelper();

        //Assert
        assertEquals(customStateViewHelper, returnedValue);
    }

    @Test
    public void givenFragmentWhenGetDefaultStateViewHelperThenGetDefaultStateViewHelper() throws Exception {
        //Arrange
        whenNew(DefaultStateViewHelper.class).withAnyArguments().thenReturn(defaultStateViewHelper);
        final View powerView = PowerMockito.mock(View.class);
        when(powerView.findViewById(R.id.ko__list)).thenReturn(recyclerView);
        when(powerView.findViewById(R.id.ko__stub_empty_state)).thenReturn(viewStub);
        when(powerView.findViewById(R.id.ko__stub_loading_state)).thenReturn(viewStub);
        when(powerView.findViewById(R.id.ko__stub_error_state)).thenReturn(viewStub);
        when(powerView.findViewById(R.id.ko__inflated_stub_empty_state)).thenReturn(powerView);
        when(powerView.findViewById(R.id.ko__empty_state_description)).thenReturn(textView);
        when(powerView.findViewById(R.id.ko__empty_state_title)).thenReturn(textView);
        when(powerView.findViewById(R.id.ko__custom_state_container)).thenReturn(linearLayout);
        suppress(method(CustomStateViewHelper.class,"hideAll"));
        messengerListFragment.setupView(powerView);

        //Act
        DefaultStateViewHelper returnedValue = messengerListFragment.getDefaultStateViewHelper();

        //Assert
        assertEquals(defaultStateViewHelper, returnedValue);
    }

    @Test
    public void givenBaseListItemWhenAddItemThenAddItem(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        messengerListFragmentPartialMock.mRecyclerView = recyclerView;
        messengerListFragmentPartialMock.init(messengerAdapter, linearLayoutManager);
        when(linearLayoutManager.findLastVisibleItemPosition()).thenReturn(INITIAL_POSITION);

        //Act
        messengerListFragmentPartialMock.addItem(baseListItem, INITIAL_POSITION);

        //Assert
        verify(messengerListFragmentPartialMock).addItem(baseListItem, INITIAL_POSITION);
    }

    @Test
    public void givenPositionWhenRemoveItemThenRemoveItem(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        messengerListFragmentPartialMock.mRecyclerView = recyclerView;
        messengerListFragmentPartialMock.init(messengerAdapter, linearLayoutManager);
        when(linearLayoutManager.findLastVisibleItemPosition()).thenReturn(INITIAL_POSITION);

        //Act
        messengerListFragmentPartialMock.removeItem(INITIAL_POSITION);

        //Assert
        verify(messengerListFragmentPartialMock).removeItem(INITIAL_POSITION);
    }

    @Test
    public void givenBaseListItemWhenReplaceItemThenReplaceItem(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        messengerListFragmentPartialMock.mRecyclerView = recyclerView;
        messengerListFragmentPartialMock.init(messengerAdapter, linearLayoutManager);
        when(linearLayoutManager.findLastVisibleItemPosition()).thenReturn(INITIAL_POSITION);

        //Act
        messengerListFragmentPartialMock.replaceItem(baseListItem, INITIAL_POSITION);

        //Assert
        verify(messengerListFragmentPartialMock).replaceItem(baseListItem, INITIAL_POSITION);
    }

    @Test
    public void givenAdapterWhenGetSizeOfDataThenGetSizeOfData(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        messengerListFragmentPartialMock.mRecyclerView = recyclerView;
        List<BaseListItem> testList = new ArrayList<>();
        testList.add(baseListItem);
        when(messengerAdapter.getData()).thenReturn(testList);
        when(messengerAdapter.getItemCount()).thenReturn(testList.size());
        messengerListFragmentPartialMock.init(messengerAdapter, linearLayoutManager);
        when(linearLayoutManager.findLastVisibleItemPosition()).thenReturn(INITIAL_POSITION);

        //Act
        int returnedValue = messengerListFragmentPartialMock.getSizeOfData();

        //Assert
        assertEquals(testList.size(), returnedValue);
    }

    @Test
    public void givenScrollListenerWhenSetScrollListenerThenSetScrollListener(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        messengerListFragmentPartialMock.mRecyclerView = recyclerView;

        //Act
        messengerListFragmentPartialMock.setScrollListener(onScrollListener);

        //Assert
        verify(messengerListFragmentPartialMock).setScrollListener(onScrollListener);
    }

    @Test
    public void givenScrollListenerWhenRemoveScrollListenerThenRemoveScrollListener(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        messengerListFragmentPartialMock.mRecyclerView = recyclerView;

        //Act
        messengerListFragmentPartialMock.removeScrollListener(onScrollListener);

        //Assert
        verify(messengerListFragmentPartialMock).removeScrollListener(onScrollListener);
    }

    @Test
    public void givenScrollListenerWhenSetLoadMoreListenerThenSetLoadMoreListener(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        messengerListFragmentPartialMock.mRecyclerView = recyclerView;
        messengerListFragmentPartialMock.init(messengerAdapter, linearLayoutManager);

        //Act
        messengerListFragmentPartialMock.setLoadMoreListener(onLoadMoreListener);

        //Assert
        verify(messengerListFragmentPartialMock).setLoadMoreListener(onLoadMoreListener);
    }

    @Test
    public void givenScrollListenerWhenRemoveLoadMoreListenerThenRemoveLoadMoreListener(){
        //Arrange
        MessengerListFragment messengerListFragmentPartialMock = spy(new MessengerListFragment());
        messengerListFragmentPartialMock.mRecyclerView = recyclerView;
        messengerListFragmentPartialMock.init(messengerAdapter, linearLayoutManager);
        messengerListFragmentPartialMock.setLoadMoreListener(onLoadMoreListener);

        //Act
        messengerListFragmentPartialMock.removeLoadMoreListener();

        //Assert
        verify(messengerListFragmentPartialMock).removeLoadMoreListener();
    }
}
