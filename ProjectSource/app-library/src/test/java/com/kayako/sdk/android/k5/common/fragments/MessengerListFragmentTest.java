package com.kayako.sdk.android.k5.common.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerAdapter;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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
        Handler.class
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
}
