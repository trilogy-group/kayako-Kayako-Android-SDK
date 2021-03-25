package com.kayako.sdk.android.k5.messenger.messagelistpage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import com.kayako.sdk.android.k5.activities.KayakoSelectConversationActivity;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollAdapter;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerAdapter;
import com.kayako.sdk.android.k5.common.fragments.BaseListFragment;
import com.kayako.sdk.android.k5.common.fragments.ListPageState;
import com.kayako.sdk.android.k5.common.fragments.MessengerListFragment;
import com.kayako.sdk.android.k5.common.fragments.OnListPageStateChangeListener;
import com.kayako.sdk.android.k5.common.fragments.OnScrollListListener;
import com.kayako.sdk.android.k5.core.Kayako;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.methods;
import static org.powermock.api.support.membermodification.MemberModifier.replace;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@PrepareForTest({
        Fragment.class,
        MessengerListFragment.class,
        BaseListFragment.class,
        Kayako.class
})
@RunWith(PowerMockRunner.class)
public class MessageListFragmentTest {

    private final KayakoSelectConversationActivity kayakoSelectConversationActivity = new KayakoSelectConversationActivity();
    private final MessageListFragment messageListFragment = new MessageListFragment();

    @Mock
    private Context context;

    @Mock
    private View view;

    @Mock
    private Bundle bundle;

    @Mock
    private RecyclerView recyclerView;

    @Mock
    private MotionEvent motionEvent;

    @Mock
    private FragmentActivity fragmentActivity;

    @Mock
    private OnListPageStateChangeListener onListPageStateChangeListener;

    @Mock
    private List<BaseListItem> messageList;

    @Mock
    private MessengerAdapter.OnItemClickListener onItemClickListener;

    @Mock
    private OnScrollListListener onScrollListListener;

    @Mock
    private EndlessRecyclerViewScrollAdapter.OnLoadMoreListener onLoadMoreListener;

    @Mock
    private MessengerAdapter messengerAdapter;

    @Mock
    private MessengerAdapter.OnAttachmentClickListener onAttachmentClickListener;

    @Mock
    private MessageListContract.OnErrorListener onErrorListener;

    @Mock
    private EndlessRecyclerViewScrollAdapter adapter;

    @Captor
    private ArgumentCaptor<ListPageState> listPageStateArgumentCaptor;

    @Captor
    private ArgumentCaptor<MessengerAdapter.OnItemClickListener> onItemClickListenerArgumentCaptor;

    @Captor
    private ArgumentCaptor<Boolean> booleanArgumentCaptor;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void onViewCreated() {
        //Arrange
        Whitebox.setInternalState(messageListFragment, "mRecyclerView", recyclerView);
        final List<View.OnTouchListener> onTouchListenerList = new ArrayList<>();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                onTouchListenerList.add((View.OnTouchListener)arguments[0]);
                return null;
            }
        }).when(recyclerView).setOnTouchListener(any(View.OnTouchListener.class));

        //Act
        messageListFragment.onViewCreated(view, bundle);
        View.OnTouchListener onTouchListenerLocal = onTouchListenerList.get(0);
        boolean flag = onTouchListenerLocal.onTouch(view, motionEvent);

        //Assert
        errorCollector.checkThat(flag, is(false));
    }

    @Test
    public void onActivityCreatedWhenClassNotKayakoSelectConversationActivity() throws NoSuchMethodException {
        //Arrange
        final String exceptionMessage =
                "This fragment was intended to be used with KayakoSelectConversationActivity!";
        Method superGetActivity = Fragment.class.getMethod("getActivity");

        //Assert
        thrown.expect(AssertionError.class);
        thrown.expectMessage(exceptionMessage);

        //Act
        replace(superGetActivity).with(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return fragmentActivity;
            }
        });
        messageListFragment.onActivityCreated(bundle);
    }

    @Test
    public void onActivityCreatedWhenClassIsKayakoSelectConversationActivity() throws NoSuchMethodException {
        //Arrange
        Whitebox.setInternalState(messageListFragment, "mOnListPageStateChangeListener", onListPageStateChangeListener);
        Whitebox.setInternalState(messageListFragment, "mListPageState", ListPageState.LIST);
        Method superGetActivity = Fragment.class.getMethod("getActivity");
        replace(superGetActivity).with(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return kayakoSelectConversationActivity;
            }
        });

        //Act
        messageListFragment.onActivityCreated(bundle);
        verify(onListPageStateChangeListener).onListPageStateChanged(listPageStateArgumentCaptor.capture());

        //Assert
        errorCollector.checkThat(listPageStateArgumentCaptor.getValue(), is(ListPageState.LIST));
    }

    @Test
    public void setupListWhenMessageListNull() throws NoSuchMethodException {
        //Arrange
        hasPageLoaded();
        final List<BaseListItem> messageListLocal = null;
        final String exceptionMessage = "Null argument unacceptable!";

        //Assert
        thrown.expectMessage(exceptionMessage);
        thrown.expect(IllegalStateException.class);

        //Act
        messageListFragment.setupList(messageListLocal);
    }

    @Test
    public void setupListWhenMessageListNotNull() throws NoSuchMethodException {
        //Arrange
        hasPageLoaded();
        suppress(methods(MessengerListFragment.class, "initMessengerList"));
        suppress(methods(MessengerListFragment.class, "scrollToNewMessagesIfNearby"));
        suppress(methods(BaseListFragment.class, "showListViewAndHideOthers"));
        suppress(methods(BaseListFragment.class, "addScrollListListener"));
        suppress(methods(BaseListFragment.class, "setLoadMoreListener"));
        Whitebox.setInternalState(messageListFragment, "mOnItemClickListener", onItemClickListener);
        Whitebox.setInternalState(messageListFragment, "mOnScrollListener", onScrollListListener);
        Whitebox.setInternalState(messageListFragment, "mLoadMoreListener", onLoadMoreListener);
        Whitebox.setInternalState(messageListFragment, "mMessengerAdapter", messengerAdapter);

        //Act
        messageListFragment.setupList(messageList);
        verify(messengerAdapter).setOnItemClickListener(onItemClickListenerArgumentCaptor.capture());
        final boolean flag = Whitebox.getInternalState(messageListFragment, "mIsListAlreadyInitialized");

        //Assert
        errorCollector.checkThat(onItemClickListenerArgumentCaptor.getValue(), is(onItemClickListener));
        errorCollector.checkThat(flag, is(true));
    }

    @Test
    public void validateSetMethod() throws NoSuchMethodException {
        //Arrange
        hasPageLoaded();
        Whitebox.setInternalState(messageListFragment, "mMessengerAdapter", messengerAdapter);

        //Act
        messageListFragment.setOnListScrollListener(onScrollListListener);
        messageListFragment.setOnListAttachmentClickListener(onAttachmentClickListener);
        messageListFragment.setOnListItemClickListener(onItemClickListener);
        messageListFragment.setListOnLoadMoreListener(onLoadMoreListener);
        messageListFragment.setOnErrorListener(onErrorListener);
        messageListFragment.setOnListPageStateChangeListener(onListPageStateChangeListener);
        final OnListPageStateChangeListener onListPageStateChangeListenerLocal =
                Whitebox.getInternalState(messageListFragment, "mOnListPageStateChangeListener");
        final OnScrollListListener onScrollListListenerLocal =
                Whitebox.getInternalState(messageListFragment, "mOnScrollListener");
        final EndlessRecyclerViewScrollAdapter.OnLoadMoreListener onLoadMoreListenerLocal =
                Whitebox.getInternalState(messageListFragment, "mLoadMoreListener");
        final MessageListContract.OnErrorListener onErrorListenerLocal =
                Whitebox.getInternalState(messageListFragment, "mErrorListener");

        //Assert
        errorCollector.checkThat(onErrorListenerLocal, is(onErrorListener));
        errorCollector.checkThat(onLoadMoreListenerLocal, is(onLoadMoreListener));
        errorCollector.checkThat(onScrollListListenerLocal, is(onScrollListListener));
        errorCollector.checkThat(onListPageStateChangeListenerLocal, is(onListPageStateChangeListener));
    }

    @Test
    public void verifySuperMethodCalls() throws NoSuchMethodException {
        //Arrange
        hasPageLoaded();
        mockStatic(Kayako.class);
        when(Kayako.getApplicationContext()).thenReturn(context);
        Whitebox.setInternalState(messageListFragment, "mAdapter", adapter);
        suppress(methods(BaseListFragment.class, "scrollToBeginningOfList"));
        suppress(methods(BaseListFragment.class, "findFirstVisibleItemPosition"));
        suppress(methods(BaseListFragment.class, "showEmptyViewAndHideOthers"));
        suppress(methods(BaseListFragment.class, "showLoadingViewAndHideOthers"));
        suppress(methods(BaseListFragment.class, "showErrorViewAndHideOthers"));
        final boolean hasMoreItems = Boolean.TRUE;

        //Act
        messageListFragment.onCreate(bundle);
        messageListFragment.setHasMoreItemsToLoad(hasMoreItems);
        verify(adapter).setHasMoreItems(booleanArgumentCaptor.capture());
        messageListFragment.showLoadMoreView();
        messageListFragment.hideLoadMoreProgress();
        messageListFragment.scrollToBottomOfList();
        messageListFragment.isNearBottomOfList();
        messageListFragment.showEmptyView();
        messageListFragment.showLoadingView();
        messageListFragment.showErrorView();
        messageListFragment.hideLoadMoreView();
        final boolean flag = messageListFragment.hasUserInteractedWithList();

        //Assert
        errorCollector.checkThat(booleanArgumentCaptor.getValue(), is(true));
        verify(adapter, times(1)).showLoadMoreProgress();
        verify(adapter, times(2)).hideLoadMoreProgress();
        errorCollector.checkThat(flag, is(false));
    }

    private void hasPageLoaded() throws NoSuchMethodException {
        Method superIsAdded = Fragment.class.getMethod("isAdded");
        replace(superIsAdded).with(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return true;
            }
        });
    }
}
