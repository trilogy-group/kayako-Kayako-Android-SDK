package com.kayako.sdk.android.k5.common.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentController;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.DragEvent;
import android.view.View;
import android.view.Window;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.messenger.data.realtime.RealtimeConversationHelper;
import com.kayako.sdk.android.k5.messenger.data.realtime.RealtimeCurrentUserTrackerHelper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.support.membermodification.MemberMatcher.methods;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.suppress;
import static org.mockito.ArgumentMatchers.eq;

@PrepareForTest({
        Class.class,
        AppCompatActivity.class,
        FragmentActivity.class,
        BaseMessengerActivity.class,
        MessengerActivityTracker.class,
        ActivityOptionsCompat.class
})
@RunWith(PowerMockRunner.class)
public class BaseMessengerActivityTest {

    private static final String CREATE_METHOD = "onCreate";
    private static final String FRAGMENT_TAG = "fragment_tag";
    private static final String FRAGMENT_FIELD = "mFragments";
    private static final String RETAINED_FRAGMENT_FIELD = "mRetainedFragment";
    private static final String CONTENT_VIEW_METHOD = "setContentView";
    private static final String REQ_ORIENTATION_METHOD = "setRequestedOrientation";
    private static final String LOAD_STATIC_CLASS_METHOD = "loadRelevantStaticClasses";
    private static final String ANIMATION_DURATION_METHOD = "setAnimationDuration";
    private static final String START_METHOD = "onStart";
    private static final String PAUSE_METHOD = "onPause";
    private static final String DESTROY_METHOD = "onDestroy";
    private static final String FINISH_METHOD = "finish";
    private static final String FINISH_ALL_ACTIVITIES_METHOD = "finishAllActivitiesOfTask";
    private static final String EXCEPTION_MSG = "Invalid fragment returned in abstract method getContainerFragment()";

    private BaseMessengerActivity baseMessengerActivityMock;
    private Fragment mRetainedFragmentMock;
    private FragmentManager managerMock;

    @Mock
    private Bundle savedInstanceState;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Captor
    private ArgumentCaptor<View.OnDragListener> dragListnerCaptor;
    @Captor
    private ArgumentCaptor<View.OnClickListener> clickListnerCaptor;

    @Before
    public void setUp() {
        mockStatic(Class.class);
        mockStatic(MessengerActivityTracker.class);
        baseMessengerActivityMock = spy(BaseMessengerActivity.class);
        FragmentController mFragmentsMock = mock(FragmentController.class);
        managerMock = mock(FragmentManager.class);
        Whitebox.setInternalState(baseMessengerActivityMock, FRAGMENT_FIELD, mFragmentsMock);
        when(baseMessengerActivityMock.getSupportFragmentManager()).thenReturn(managerMock);
        mRetainedFragmentMock = mock(Fragment.class);
    }

    @Test
    public void onCreateWhenFragmentIsAvailableByTag() throws ClassNotFoundException {
        //Arrange
        suppressMethods();
        Window winMock = mock(Window.class);
        doReturn(winMock).when((Activity)baseMessengerActivityMock).getWindow();
        Transition transition = mock(Transition.class);
        doNothing().when(winMock).setSharedElementEnterTransition(eq(transition));
        when(Class.forName(eq(RealtimeCurrentUserTrackerHelper.class.getName()))).thenThrow(ClassNotFoundException.class);
        when(Class.forName(eq(RealtimeConversationHelper.class.getName()))).thenThrow(ClassNotFoundException.class);
        when(managerMock.findFragmentByTag(FRAGMENT_TAG)).thenReturn(mRetainedFragmentMock);
        doCallRealMethod().when(baseMessengerActivityMock).onCreate(savedInstanceState);
        //Act
        baseMessengerActivityMock.onCreate(savedInstanceState);
        //Assert
        verify(managerMock, times(1)).findFragmentByTag(FRAGMENT_TAG);
    }

    @Test
    public void onCreateWhenFragmentIsNotAvailableByTag() {
        //Arrange
        suppressMethods();
        suppress(methods(BaseMessengerActivity.class, LOAD_STATIC_CLASS_METHOD));
        suppress(methods(BaseMessengerActivity.class, ANIMATION_DURATION_METHOD));
        when(managerMock.findFragmentByTag(FRAGMENT_TAG)).thenReturn(null);
        when(baseMessengerActivityMock.getContainerFragment()).thenReturn(null);
        doCallRealMethod().when(baseMessengerActivityMock).onCreate(savedInstanceState);
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage(EXCEPTION_MSG);
        //Act
        baseMessengerActivityMock.onCreate(savedInstanceState);
    }

    @Test
    public void onCreateWhenContainerFragmentIsAvailable() {
        //Arrange
        suppressMethods();
        suppress(methods(BaseMessengerActivity.class, LOAD_STATIC_CLASS_METHOD));
        suppress(methods(BaseMessengerActivity.class, ANIMATION_DURATION_METHOD));
        when(managerMock.findFragmentByTag(FRAGMENT_TAG)).thenReturn(null);
        Fragment containerFragmentMock = mock(Fragment.class);
        when(baseMessengerActivityMock.getContainerFragment()).thenReturn(containerFragmentMock);
        FragmentTransaction fragmentTransactionMock = mock(FragmentTransaction.class);
        when(managerMock.beginTransaction()).thenReturn(fragmentTransactionMock);
        FragmentTransaction fragmentTransactionForReplaceMock = mock(FragmentTransaction.class);
        when(fragmentTransactionMock.replace(eq(R.id.ko__fragment_container), eq(containerFragmentMock),
                eq(FRAGMENT_TAG))).thenReturn(fragmentTransactionForReplaceMock);
        when(fragmentTransactionForReplaceMock.commitAllowingStateLoss()).thenReturn(1);
        doCallRealMethod().when(baseMessengerActivityMock).onCreate(savedInstanceState);
        //Act
        baseMessengerActivityMock.onCreate(savedInstanceState);
        //Assert
        verify(managerMock, times(1)).findFragmentByTag(FRAGMENT_TAG);
        verify(fragmentTransactionForReplaceMock, times(1)).commitAllowingStateLoss();
    }

    @Test
    public void onStartWithDragEvent() throws Exception {
        //Arrange
        View viewMock = mock(View.class);
        commonMocksForStarts(viewMock);
        //Act
        baseMessengerActivityMock.onStart();
        //Assert
        verify(viewMock).setOnDragListener((View.OnDragListener) dragListnerCaptor.capture());
        View.OnDragListener dragListenerOb = dragListnerCaptor.getValue();
        boolean isOnDragEvent = dragListenerOb.onDrag(mock(View.class), mock(DragEvent.class));
        verifyPrivate(baseMessengerActivityMock, times(1)).invoke(FINISH_ALL_ACTIVITIES_METHOD);
        assertTrue("The event returned is TRUE ", isOnDragEvent);
    }

    @Test
    public void onStartWithClickEvent() throws Exception {
        //Arrange
        View viewMock = mock(View.class);
        commonMocksForStarts(viewMock);
        //Act
        baseMessengerActivityMock.onStart();
        //Assert
        verify(viewMock).setOnClickListener((View.OnClickListener) clickListnerCaptor.capture());
        View.OnClickListener clickListenerOb = clickListnerCaptor.getValue();
        clickListenerOb.onClick(mock(View.class));
        verifyPrivate(baseMessengerActivityMock, times(1)).invoke(FINISH_ALL_ACTIVITIES_METHOD);
    }

    @Test
    public void onPause() {
        //Arrange
        Whitebox.setInternalState(baseMessengerActivityMock, RETAINED_FRAGMENT_FIELD, mRetainedFragmentMock);
        suppress(methods(AppCompatActivity.class, PAUSE_METHOD));
        doReturn(true).when(((Activity) baseMessengerActivityMock)).isFinishing();
        FragmentTransaction fragmentTransactionMock = mock(FragmentTransaction.class);
        when(managerMock.beginTransaction()).thenReturn(fragmentTransactionMock);
        FragmentTransaction fragmentTransactionForRemoveMock = mock(FragmentTransaction.class);
        when(fragmentTransactionMock.remove(eq(mRetainedFragmentMock))).thenReturn(fragmentTransactionForRemoveMock);
        when(fragmentTransactionForRemoveMock.commit()).thenReturn(1);
        //Act
        baseMessengerActivityMock.onPause();
        //Assert
        verify(fragmentTransactionForRemoveMock, times(1)).commit();
    }

    @Test
    public void onDestroy() {
        //Arrange
        suppress(methods(AppCompatActivity.class, DESTROY_METHOD));
        //Act
        baseMessengerActivityMock.onDestroy();
        //Assert
        verifyStatic(MessengerActivityTracker.class);
        MessengerActivityTracker.refreshList();
    }

    @Test
    public void finishFinal() {
        //Arrange
        suppress(methods(Activity.class, FINISH_METHOD));
        doNothing().when((AppCompatActivity)baseMessengerActivityMock).overridePendingTransition(eq(R.anim.slide_from_bottom),
                eq(R.anim.slide_to_top));
        //Act
        baseMessengerActivityMock.finishFinal();
        //Assert
        verify((AppCompatActivity)baseMessengerActivityMock).overridePendingTransition(eq(R.anim.slide_from_bottom),
                eq(R.anim.slide_to_top));
    }

    @Test
    public void getAnimationWithBackgroundViewAndNoButton() {
        //Arrange
        Activity activityMock = mock(Activity.class);
        View backgroundView = mock(View.class);
        doReturn(backgroundView).when(activityMock).findViewById(eq(R.id.ko__messenger_custom_background));
        doReturn(null).when(activityMock).findViewById(eq(R.id.ko__messenger_toolbar_back_button));
        mockStatic(ActivityOptionsCompat.class);
        //Act
        ActivityOptionsCompat returnedOb = baseMessengerActivityMock.getAnimation(activityMock);
        //Assert
        verifyStatic(ActivityOptionsCompat.class);
        ActivityOptionsCompat.makeSceneTransitionAnimation(eq(activityMock));
    }

    @Test
    public void getAnimationWithBackgroundViewAndButton() {
        //Arrange
        Activity activityMock = mock(Activity.class);
        View backgroundView = mock(View.class);
        View backButton = mock(View.class);
        doReturn(backgroundView).when(activityMock).findViewById(eq(R.id.ko__messenger_custom_background));
        doReturn(backButton).when(activityMock).findViewById(eq(R.id.ko__messenger_toolbar_back_button));
        mockStatic(ActivityOptionsCompat.class);
        Pair<View, String> pairBackground = new Pair<>(backgroundView, "ko__messenger_background");
        Pair<View, String> pairBackButton = new Pair<>(backButton, "ko__messenger_toolbar_back_button");
        //Act
        ActivityOptionsCompat returnedOb = baseMessengerActivityMock.getAnimation(activityMock);
        //Assert
        verifyStatic(ActivityOptionsCompat.class);
        ActivityOptionsCompat.makeSceneTransitionAnimation(eq(activityMock), eq(pairBackground), eq(pairBackButton));
    }

    @Test
    public void getAnimationWithNoBackgroundViewAndButton() {
        //Arrange
        Activity activityMock = mock(Activity.class);
        doReturn(null).when(activityMock).findViewById(eq(R.id.ko__messenger_custom_background));
        doReturn(null).when(activityMock).findViewById(eq(R.id.ko__messenger_toolbar_back_button));
        mockStatic(ActivityOptionsCompat.class);
        //Act
        baseMessengerActivityMock.getAnimation(activityMock);
        //Assert
        verifyStatic(ActivityOptionsCompat.class);
        ActivityOptionsCompat.makeSceneTransitionAnimation(eq(activityMock));
    }

    private void commonMocksForStarts(View viewMock) {
        suppress(methods(AppCompatActivity.class, START_METHOD));
        doCallRealMethod().when(baseMessengerActivityMock).onStart();
        doReturn(viewMock).when(baseMessengerActivityMock).findViewById(eq(R.id.ko__space_to_close_messenger));
    }

    private void suppressMethods() {
        suppress(methods(AppCompatActivity.class, CREATE_METHOD));
        suppress(methods(AppCompatActivity.class, CONTENT_VIEW_METHOD));
        suppress(methods(Activity.class, REQ_ORIENTATION_METHOD));
    }
}
