package com.kayako.sdk.android.k5.messenger.homescreenpage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.HomeScreenListAdapter;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.support.membermodification.MemberModifier.replace;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@PrepareForTest(Fragment.class)
@RunWith(PowerMockRunner.class)
public class HomeScreenListFragmentTest {

    private final HomeScreenListFragment homeScreenListFragment = new HomeScreenListFragment();

    @Mock
    private Bundle bundle;

    @Mock
    private LayoutInflater layoutInflater;

    @Mock
    private ViewGroup viewGroup;

    @Mock
    private View view;

    @Mock
    private RecyclerView recyclerView;

    @Mock
    private HomeScreenListContract.Presenter presenter;

    @Mock
    private AppCompatActivity appCompatActivity;

    @Mock
    private Context context;

    @Mock
    private List<BaseListItem> list;

    @Mock
    private HomeScreenListContract.OnScrollListListener onScrollListListener;

    @Mock
    private HomeScreenListAdapter homeScreenListAdapter;

    @Captor
    private ArgumentCaptor<Boolean> booleanArgumentCaptor;

    @Captor
    private ArgumentCaptor<List<BaseListItem>> listArgumentCaptor;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Test
    public void onCreate() {
        //Act
        homeScreenListFragment.onCreate(bundle);

        //Assert
        final HomeScreenListContract.Presenter presenterLocal =
                Whitebox.getInternalState(homeScreenListFragment, "mPresenter");
        assertNotNull(presenterLocal);
    }

    @Test
    public void onCreateView() {
        //Arrange
        when(layoutInflater.inflate(R.layout.ko__fragment_home_screen_list, viewGroup, false)).thenReturn(view);
        when(view.findViewById(R.id.ko__list)).thenReturn(recyclerView);

        //Act
        final View viewLocal = homeScreenListFragment.onCreateView(layoutInflater, viewGroup, bundle);

        //Assert
        assertEquals(view, viewLocal);
    }

    @Test
    public void operationOnView() {
        //Arrange
        Whitebox.setInternalState(homeScreenListFragment, "mPresenter", presenter);

        //Act
        homeScreenListFragment.onViewCreated(view, bundle);
        homeScreenListFragment.onResume();
        homeScreenListFragment.onDestroyView();

        //Assert
        verify(presenter, times(1)).initPage();
        verify(presenter, times(1)).onResume();
        verify(presenter, times(1)).closePage();
    }

    @Test
    public void setupListWhenNullHomeScreenListAdapter() throws NoSuchMethodException {
        //Arrange
        hasPageLoaded();
        Whitebox.setInternalState(homeScreenListFragment, "mRecyclerView", recyclerView);
        Whitebox.setInternalState(homeScreenListFragment, "mScrollListener", onScrollListListener);
        final List<RecyclerView.OnScrollListener> onScrollListeners = new ArrayList<>();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                RecyclerView.OnScrollListener onScrollListenerInner =
                        (RecyclerView.OnScrollListener)arguments[0];
                onScrollListeners.add(onScrollListenerInner);
                return null;
            }
        }).when(recyclerView).addOnScrollListener(any(RecyclerView.OnScrollListener.class));

        //Act
        homeScreenListFragment.setupList(list);
        RecyclerView.OnScrollListener onScrollListener = onScrollListeners.get(0);
        onScrollListener.onScrolled(recyclerView, 1 , 1);
        onScrollListener.onScrollStateChanged(recyclerView, RecyclerView.SCROLL_STATE_IDLE);
        onScrollListener.onScrollStateChanged(recyclerView, RecyclerView.SCROLL_INDICATOR_END);
        verify(onScrollListListener, times(2)).onScroll(booleanArgumentCaptor.capture());

        //Assert
        errorCollector.checkThat(booleanArgumentCaptor.getAllValues().get(0), CoreMatchers.is(false));
        errorCollector.checkThat(booleanArgumentCaptor.getAllValues().get(1), CoreMatchers.is(true));
    }

    @Test
    public void setupListWhenNotNullHomeScreenListAdapter() throws NoSuchMethodException {
        //Arrange
        hasPageLoaded();
        Whitebox.setInternalState(homeScreenListFragment, "mAdapter", homeScreenListAdapter);

        //Act
        homeScreenListFragment.setupList(list);
        verify(homeScreenListAdapter).replaceData(listArgumentCaptor.capture());

        //Arrange
        errorCollector.checkThat(listArgumentCaptor.getValue(), CoreMatchers.is(list));
    }

    @Test
    public void setOnScrollListener() {
        homeScreenListFragment.setOnScrollListener(onScrollListListener);
        final HomeScreenListContract.OnScrollListListener listenerLocal =
                Whitebox.getInternalState(homeScreenListFragment, "mScrollListener");
        errorCollector.checkThat(listenerLocal, CoreMatchers.is(onScrollListListener));
    }

    private void hasPageLoaded() throws NoSuchMethodException {
        Method superGetActivity = Fragment.class.getMethod("getActivity");
        Method superIsAdded = Fragment.class.getMethod("isAdded");
        Method superGetContext = Fragment.class.getMethod("getContext");
        replace(superGetActivity).with(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return appCompatActivity;
            }
        });

        replace(superIsAdded).with(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return true;
            }
        });

        replace(superGetContext).with(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return context;
            }
        });
    }
}
