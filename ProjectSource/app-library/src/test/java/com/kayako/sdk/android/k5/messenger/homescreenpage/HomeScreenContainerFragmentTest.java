package com.kayako.sdk.android.k5.messenger.homescreenpage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.core.MessengerStylePref;
import com.kayako.sdk.android.k5.messenger.style.MessengerTemplateHelper;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.support.membermodification.MemberMatcher.methods;
import static org.powermock.api.support.membermodification.MemberModifier.replace;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import static org.powermock.api.mockito.PowerMockito.suppress;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@PrepareForTest({
        MessengerStylePref.class,
        MessengerTemplateHelper.class,
        Fragment.class
})
@RunWith(PowerMockRunner.class)
public class HomeScreenContainerFragmentTest {

    private final HomeScreenContainerFragment homeScreenContainerFragment =
            new HomeScreenContainerFragment();

    @Mock
    private Bundle bundle;

    @Mock
    private LayoutInflater layoutInflater;

    @Mock
    private ViewGroup viewGroup;

    @Mock
    private View view;

    @Mock
    private MessengerStylePref messengerStylePref;

    @Mock
    private HomeScreenContainerContract.Presenter presenter;

    @Mock
    private AppCompatActivity appCompatActivity;

    @Mock
    private HomeScreenListFragment homeScreenListFragment;

    @Mock
    private FragmentManager fragmentManager;

    @Captor
    private ArgumentCaptor<Integer> integerArgumentCaptor;

    @Captor
    private ArgumentCaptor<Boolean> booleanArgumentCaptor;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        mockStatic(MessengerStylePref.class);
        when(MessengerStylePref.getInstance()).thenReturn(messengerStylePref);
    }

    @Test
    public void onCreate() {
        homeScreenContainerFragment.onCreate(bundle);
        final HomeScreenContainerContract.Presenter mPresenter =
                Whitebox.getInternalState(homeScreenContainerFragment, "mPresenter");
        errorCollector.checkThat(mPresenter, notNullValue());
    }

    @Test
    public void onCreateView() {
        //Arrange
        when(layoutInflater.inflate(R.layout.ko__fragment_home_screen_container,
                viewGroup, false)).thenReturn(view);
        suppress(methods(MessengerTemplateHelper.class, "applyBackgroundTheme"));

        //Act
        final View viewLocal = homeScreenContainerFragment.onCreateView(layoutInflater, viewGroup, bundle);

        //Assert
        errorCollector.checkThat(viewLocal, is(view));
    }

    @Test
    public void onActivityCreated() {
        //Arrange
        Whitebox.setInternalState(homeScreenContainerFragment, "mPresenter", presenter);

        //Act
        homeScreenContainerFragment.onActivityCreated(bundle);

        //Assert
        verify(presenter, times(1)).initPage();
    }

    @Test
    public void showAndHideConversationButton() throws NoSuchMethodException {
        //Arrange
        hasPageLoaded();
        Whitebox.setInternalState(homeScreenContainerFragment, "mRoot", view);
        when(view.findViewById(R.id.ko__new_conversation_button)).thenReturn(view);

        //Act
        homeScreenContainerFragment.showNewConversationButton();
        homeScreenContainerFragment.hideNewConversationButton();

        //Assert
        verify(view, times(2)).setVisibility(integerArgumentCaptor.capture());
        errorCollector.checkThat(integerArgumentCaptor.getAllValues().get(0), is(View.VISIBLE));
        errorCollector.checkThat(integerArgumentCaptor.getAllValues().get(1), is(View.GONE));
    }

    @Test
    public void onViewCreated() throws NoSuchMethodException {
        //Arrange
        hasPageLoaded();
        Whitebox.setInternalState(homeScreenContainerFragment, "mPresenter", presenter);
        Whitebox.setInternalState(homeScreenContainerFragment, "mRoot", view);
        when(view.findViewById(R.id.ko__new_conversation_button)).thenReturn(view);
        when(fragmentManager.findFragmentById(R.id.ko__home_screen_list_fragment)).thenReturn(homeScreenListFragment);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                View.OnClickListener onClickListener = (View.OnClickListener)arguments[0];
                onClickListener.onClick(view);
                return null;
            }
        }).when(view).setOnClickListener(any(View.OnClickListener.class));

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                HomeScreenListContract.OnScrollListListener onScrollListListener =
                        (HomeScreenListContract.OnScrollListListener)arguments[0];
                onScrollListListener.onScroll(true);
                return null;
            }
        }).when(homeScreenListFragment).setOnScrollListener(any(HomeScreenListContract.OnScrollListListener.class));

        //Act
        homeScreenContainerFragment.onViewCreated(view, bundle);
        verify(presenter).onScrollList(booleanArgumentCaptor.capture());

        //Assert
        errorCollector.checkThat(booleanArgumentCaptor.getValue(), is(true));
    }

    private void hasPageLoaded() throws NoSuchMethodException {
        Method superGetActivity = Fragment.class.getMethod("getActivity");
        Method superIsAdded = Fragment.class.getMethod("isAdded");
        Method superGetChildFragmentManager = Fragment.class.getMethod("getChildFragmentManager");
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

        replace(superGetChildFragmentManager).with(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return fragmentManager;
            }
        });
    }
}
