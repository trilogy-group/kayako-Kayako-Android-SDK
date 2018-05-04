package com.kayako.sdk.android.k5.messenger.toolbarview;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentHostCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.AssignedAgentData;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.LastActiveAgentsData;
import com.kayako.sdk.android.k5.messenger.style.MessengerTemplateHelper;
import com.kayako.sdk.android.k5.messenger.toolbarview.child.MessengerToolbarCollapsedFragment;
import com.kayako.sdk.android.k5.messenger.toolbarview.child.MessengerToolbarExpandedFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.kayako.sdk.android.k5.messenger.toolbarview.MessengerToolbarContract.MessengerToolbarType.ASSIGNED_AGENT;
import static com.kayako.sdk.android.k5.messenger.toolbarview.MessengerToolbarContract.MessengerToolbarType.LAST_ACTIVE_AGENTS;
import static com.kayako.sdk.android.k5.messenger.toolbarview.MessengerToolbarContract.MessengerToolbarType.SIMPLE_TITLE;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        Build.VERSION.class,
        Fragment.class,
        Looper.class,
        MessengerToolbarFragment.class,
        MessengerToolbarFactory.class,
        MessengerTemplateHelper.class
})
public class MessengerToolbarFragmentTest {

    private static final String SDK_INT = "SDK_INT";
    private static final String TEST_TEXT = "testText";

    private static final String NULL_ASSIGNED_AGENT_DATA_MESSAGE = "Null AssignedAgent Data!";
    private static final String NULL_LAST_ACTIVE_AGENTS_DATA_MESSAGE = "Null LastActiveAgents Data!";
    private static final String NULL_NOT_ALLOWED_MESSAGE = "Null not allowed!";
    private static final String THE_NECESSARY_FIELDS_ARE_NOT_INITIALIZED_MESSAGE = "The necessary fields are not initialized!";
    private static final String THIS_METHOD_SHOULD_BE_CALLED_ONCE_THE_FRAGMENT_IS_ADDED_MESSAGE = "This method should be called once the fragment is added!";

    private static final String APPLY_BACKGROUND_THEME_METHOD = "applyBackgroundTheme";
    private static final String CONFIGURE_CHILD_FRAGMENT_METHOD = "configureChildFragment";
    private static final String COMMIT_TRANSACTION_WITH_ANIMATION_METHOD = "commitTransactionWithAnimation";
    private static final String GET_ACTIVITY_METHOD = "getActivity";
    private static final String GET_HANDLER_METHOD = "getHandler";
    private static final String GET_PRESENTER_METHOD = "getPresenter";
    private static final String GET_VIEW_METHOD = "getView";
    private static final String IS_ADDED_METHOD = "isAdded";
    private static final String MY_LOOPER_METHOD = "myLooper";
    private static final String ON_ACTIVITY_CREATED_METHOD = "onActivityCreated";
    private static final String ON_CREATE_METHOD = "onCreate";
    private static final String ON_DESTROY_VIEW_METHOD = "onDestroyView";
    private static final String ON_VIEW_CREATED_METHOD = "onViewCreated";
    private static final String REMOVE_ADDED_TRANSACTED_FRAGMENT_METHOD = "removeAddedTransactedFragment";
    private static final String SETUP_TOOLBAR_METHOD = "setupToolbar";

    private static final String ADDED_FIELD = "mAdded";
    private static final String HANDLER_FIELD = "mHandler";
    private static final String HOST_FIELD = "mHost";
    private static final String IS_EXPANDED_FIELD = "mIsExpanded";
    private static final String LAST_ADDED_CHILD_FRAGMENT_FIELD = "mLastAddedChildFragment";
    private static final String TOOLBAR_TYPE_FIELD = "mToolbarType";

    private MessengerToolbarFragment messengerToolbarFragment;

    @Mock
    Bundle bundle;

    @Mock
    LayoutInflater inflater;

    @Mock
    ViewGroup container;

    @Mock
    MessengerToolbarContract.Presenter presenter;

    @Mock
    View view;

    @Mock
    MessengerToolbarCollapsedFragment messengerToolbarCollapsedFragment;

    @Mock
    MessengerToolbarExpandedFragment messengerToolbarExpandedFragment;

    @Mock
    LastActiveAgentsData lastActiveAgentsData;

    @Mock
    AssignedAgentData assignedAgentData;

    @Mock
    FragmentActivity fragmentActivity;

    @Mock
    FragmentHostCallback fragmentHostCallback;

    @Mock
    Looper looper;

    @Mock
    Handler handler;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        messengerToolbarFragment = new MessengerToolbarFragment();

        bundle = mock(Bundle.class);
        inflater = mock(LayoutInflater.class);
        container = mock(ViewGroup.class);
        presenter = mock(MessengerToolbarContract.Presenter.class);
        view = mock(View.class);
        lastActiveAgentsData = mock(LastActiveAgentsData.class);
        assignedAgentData = mock(AssignedAgentData.class);
        fragmentActivity = mock(FragmentActivity.class);
        fragmentHostCallback = mock(FragmentHostCallback.class);
        looper = mock(Looper.class);
        handler = PowerMockito.mock(Handler.class);
        messengerToolbarCollapsedFragment = PowerMockito.mock(MessengerToolbarCollapsedFragment.class);
        messengerToolbarExpandedFragment = PowerMockito.mock(MessengerToolbarExpandedFragment.class);

        Whitebox.setInternalState(messengerToolbarFragment, HOST_FIELD, fragmentHostCallback);
        Whitebox.setInternalState(messengerToolbarFragment, ADDED_FIELD, true);
        Whitebox.setInternalState(fragmentHostCallback, HANDLER_FIELD, handler);

        when(inflater.inflate(R.layout.ko__messenger_toolbar, container)).thenReturn(view);
        when(view.findViewById(R.id.ko__messenger_child_toolbar_container)).thenReturn(container);
        when(view.findViewById(R.id.ko__messenger_toolbar_back_button)).thenReturn(view);
        when(view.findViewById(R.id.ko__messenger_toolbar_title)).thenReturn(view);
        when(view.findViewById(R.id.ko__messenger_toolbar_subtitle)).thenReturn(view);
        when(view.findViewById(R.id.ko__messenger_toolbar_avatar1)).thenReturn(view);
        when(view.findViewById(R.id.ko__messenger_toolbar_avatar2)).thenReturn(view);
        when(view.findViewById(R.id.ko__messenger_toolbar_avatar3)).thenReturn(view);
        when(view.getTransitionName()).thenReturn(TEST_TEXT);
        when(fragmentActivity.isFinishing()).thenReturn(false);
        when(handler.getLooper()).thenReturn(looper);

        PowerMockito.whenNew(MessengerToolbarCollapsedFragment.class).withNoArguments().thenReturn(messengerToolbarCollapsedFragment);
        PowerMockito.whenNew(MessengerToolbarExpandedFragment.class).withNoArguments().thenReturn(messengerToolbarExpandedFragment);

        PowerMockito.mockStatic(Fragment.class);
        PowerMockito.mockStatic(MessengerToolbarFactory.class);
        PowerMockito.mockStatic(MessengerTemplateHelper.class);
        PowerMockito.mockStatic(Looper.class);
        PowerMockito.mockStatic(Build.VERSION.class);

        Whitebox.setInternalState(android.os.Build.VERSION.class, SDK_INT, Build.VERSION_CODES.LOLLIPOP);

        PowerMockito.when(Looper.class, MY_LOOPER_METHOD).thenReturn(looper);
        PowerMockito.when(MessengerToolbarFactory.class, GET_PRESENTER_METHOD, messengerToolbarFragment).
                thenReturn(presenter);
        PowerMockito.when(fragmentHostCallback, GET_ACTIVITY_METHOD).thenReturn(fragmentActivity);
        PowerMockito.when(fragmentHostCallback, GET_HANDLER_METHOD).thenReturn(handler);
        PowerMockito.when(messengerToolbarCollapsedFragment, IS_ADDED_METHOD).thenReturn(true);
        PowerMockito.when(messengerToolbarCollapsedFragment, GET_VIEW_METHOD).thenReturn(view);
        PowerMockito.when(messengerToolbarExpandedFragment, IS_ADDED_METHOD).thenReturn(true);
        PowerMockito.when(messengerToolbarExpandedFragment, GET_VIEW_METHOD).thenReturn(view);

        PowerMockito.suppress(PowerMockito.methods(Fragment.class, ON_CREATE_METHOD));
        PowerMockito.suppress(PowerMockito.methods(Fragment.class, ON_VIEW_CREATED_METHOD));
        PowerMockito.suppress(PowerMockito.methods(Fragment.class, ON_ACTIVITY_CREATED_METHOD));
        PowerMockito.suppress(PowerMockito.methods(Fragment.class, ON_DESTROY_VIEW_METHOD));
        PowerMockito.suppress(PowerMockito.methods(MessengerTemplateHelper.class, APPLY_BACKGROUND_THEME_METHOD));
    }

    @Test
    public void onCreate() {
        // Arrange & Act
        messengerToolbarFragment.onCreate(bundle);
    }

    @Test
    public void onCreateView() {
        // Arrange & Act
        View viewResult = messengerToolbarFragment.onCreateView(inflater, container, bundle);

        // Assert
        assertNotNull(viewResult);
    }

    @Test
    public void onViewCreated() {
        // Arrange
        messengerToolbarFragment.onCreateView(inflater, container, bundle);

        // Act
        messengerToolbarFragment.onViewCreated(view, bundle);
    }

    @Test
    public void onActivityCreated() {
        // Arrange
        messengerToolbarFragment.onCreate(bundle);

        // Act
        messengerToolbarFragment.onActivityCreated(bundle);
    }

    @Test
    public void onDestroyView() {
        // Arrange
        messengerToolbarFragment.onCreate(bundle);

        // Act
        messengerToolbarFragment.onDestroyView();
    }

    @Test
    public void configureDefaultView() {
        // Arrange
        messengerToolbarFragment.onCreate(bundle);

        // Act
        messengerToolbarFragment.configureDefaultView();
    }

    @Test
    public void configureForLastActiveUsersViewWhenExpanded() {
        // Arrange
        prepareMessengerToolbarFragment();

        Whitebox.setInternalState(messengerToolbarFragment, IS_EXPANDED_FIELD, true);

        // Act
        messengerToolbarFragment.configureForLastActiveUsersView(lastActiveAgentsData, true);
    }

    @Test
    public void configureForLastActiveUsersViewWhenCollapsed() {
        // Arrange
        prepareMessengerToolbarFragment();

        Whitebox.setInternalState(messengerToolbarFragment, IS_EXPANDED_FIELD, false);

        // Act
        messengerToolbarFragment.configureForLastActiveUsersView(lastActiveAgentsData, true);
    }

    @Test
    public void configureForLastActiveUsersViewWhenViewNotReady() throws Exception {
        // Arrange
        prepareMessengerToolbarFragment();

        PowerMockito.when(fragmentHostCallback, GET_ACTIVITY_METHOD).thenReturn(null);

        // Act
        messengerToolbarFragment.configureForLastActiveUsersView(lastActiveAgentsData, true);
    }

    @Test
    public void configureForLastActiveUsersViewWhenDataIsNull() throws Exception {
        // Arrange
        prepareMessengerToolbarFragment();

        expectErrorMessage(IllegalArgumentException.class, NULL_NOT_ALLOWED_MESSAGE);

        // Act
        messengerToolbarFragment.configureForLastActiveUsersView(null, true);
    }

    @Test
    public void configureForAssignedAgentView() {
        // Arrange
        prepareMessengerToolbarFragment();

        // Act
        messengerToolbarFragment.configureForAssignedAgentView(assignedAgentData, true);
    }

    @Test
    public void configureForAssignedAgentViewWhenViewNotReady() throws Exception {
        // Arrange
        prepareMessengerToolbarFragment();

        PowerMockito.when(fragmentHostCallback, GET_ACTIVITY_METHOD).thenReturn(null);

        // Act
        messengerToolbarFragment.configureForAssignedAgentView(assignedAgentData, true);
    }

    @Test
    public void configureForAssignedAgentViewWhenDataIsNull() throws Exception {
        // Arrange
        prepareMessengerToolbarFragment();

        expectErrorMessage(IllegalArgumentException.class, NULL_NOT_ALLOWED_MESSAGE);

        // Act
        messengerToolbarFragment.configureForAssignedAgentView(null, true);
    }

    @Test
    public void configureForSimpleTitle() {
        // Arrange
        prepareMessengerToolbarFragment();

        // Act
        messengerToolbarFragment.configureForSimpleTitle(TEST_TEXT, true);
    }

    @Test
    public void configureForSimpleTitleViewNotReady() throws Exception {
        // Arrange
        prepareMessengerToolbarFragment();

        PowerMockito.when(fragmentHostCallback, GET_ACTIVITY_METHOD).thenReturn(null);

        // Act
        messengerToolbarFragment.configureForSimpleTitle(TEST_TEXT, true);
    }

    @Test
    public void configureForSimpleTitleDataIsNull() {
        // Arrange
        prepareMessengerToolbarFragment();

        expectErrorMessage(IllegalArgumentException.class, NULL_NOT_ALLOWED_MESSAGE);

        // Act
        messengerToolbarFragment.configureForSimpleTitle(null, true);
    }

    @Test
    public void expandToolbarViewWhenDefault() {
        // Act
        messengerToolbarFragment.expandToolbarView();
    }

    @Test
    public void expandToolbarView() {
        // Arrange
        prepareMessengerToolbarFragment();
        messengerToolbarFragment.configureForSimpleTitle(TEST_TEXT, true);

        // Act
        messengerToolbarFragment.expandToolbarView();
    }

    @Test
    public void collapseToolbarViewWhenDefault() {
        // Act
        messengerToolbarFragment.collapseToolbarView();
    }

    @Test
    public void collapseToolbarViewToolbarExpanded() {
        // Arrange
        prepareMessengerToolbarFragment();
        messengerToolbarFragment.configureForSimpleTitle(TEST_TEXT, true);

        Whitebox.setInternalState(messengerToolbarFragment, LAST_ADDED_CHILD_FRAGMENT_FIELD, messengerToolbarExpandedFragment);

        // Act
        messengerToolbarFragment.collapseToolbarView();
    }

    @Test
    public void isToolbarAreadyConfiguredWhenDefault() {
        // Act
        boolean toolbarAreadyConfigured = messengerToolbarFragment.isToolbarAreadyConfigured();

        // Assert
        assertFalse(toolbarAreadyConfigured);
    }

    @Test
    public void refreshUnreadCounter() {
        // Act
        messengerToolbarFragment.refreshUnreadCounter(0);
    }

    @Test
    public void removeAddedTransactedFragment() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Arrange
        prepareMessengerToolbarFragment();
        messengerToolbarFragment.configureForSimpleTitle(TEST_TEXT, true);

        Method removeAddedTransactedFragmentMethod =
                messengerToolbarFragment.getClass().getDeclaredMethod(REMOVE_ADDED_TRANSACTED_FRAGMENT_METHOD);
        removeAddedTransactedFragmentMethod.setAccessible(true);

        // Act
        removeAddedTransactedFragmentMethod.invoke(messengerToolbarFragment);
    }

    @Test
    public void commitTransactionWithAnimationWhenLastActiveAgents() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Arrange
        prepareMessengerToolbarFragment();
        messengerToolbarFragment.configureForLastActiveUsersView(lastActiveAgentsData, true);

        Method commitTransactionWithAnimationMethod =
                messengerToolbarFragment.getClass().getDeclaredMethod(COMMIT_TRANSACTION_WITH_ANIMATION_METHOD);
        commitTransactionWithAnimationMethod.setAccessible(true);

        // Act
        commitTransactionWithAnimationMethod.invoke(messengerToolbarFragment);
    }

    @Test
    public void commitTransactionWithAnimationWhenAssignedAgents() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Arrange
        prepareMessengerToolbarFragment();
        messengerToolbarFragment.configureForAssignedAgentView(assignedAgentData, true);

        Method commitTransactionWithAnimationMethod =
                messengerToolbarFragment.getClass().getDeclaredMethod(MessengerToolbarFragmentTest.COMMIT_TRANSACTION_WITH_ANIMATION_METHOD);
        commitTransactionWithAnimationMethod.setAccessible(true);

        // Act
        commitTransactionWithAnimationMethod.invoke(messengerToolbarFragment);
    }

    @Test
    public void commitTransactionWithAnimationWhenSimpleTitle() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Arrange
        prepareMessengerToolbarFragment();
        messengerToolbarFragment.configureForSimpleTitle(TEST_TEXT, true);

        Method commitTransactionWithAnimationMethod =
                messengerToolbarFragment.getClass().getDeclaredMethod(COMMIT_TRANSACTION_WITH_ANIMATION_METHOD);
        commitTransactionWithAnimationMethod.setAccessible(true);

        // Act
        commitTransactionWithAnimationMethod.invoke(messengerToolbarFragment);
    }

    @Test
    public void commitTransactionWithAnimationWhenConfigureViewEmpty() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Arrange
        prepareMessengerToolbarFragment();
        messengerToolbarFragment.configureForSimpleTitle(TEST_TEXT, true);

        when(messengerToolbarCollapsedFragment.getView()).thenReturn(null);

        Method commitTransactionWithAnimationMethod = messengerToolbarFragment.getClass().getDeclaredMethod(COMMIT_TRANSACTION_WITH_ANIMATION_METHOD);
        commitTransactionWithAnimationMethod.setAccessible(true);

        // Act
        commitTransactionWithAnimationMethod.invoke(messengerToolbarFragment);
    }

    @Test
    public void commitTransactionWithAnimationWhenAndroidKitKat() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Arrange
        prepareMessengerToolbarFragment();
        messengerToolbarFragment.configureForSimpleTitle(TEST_TEXT, true);

        Method commitTransactionWithAnimationMethod = messengerToolbarFragment.getClass().getDeclaredMethod(COMMIT_TRANSACTION_WITH_ANIMATION_METHOD);
        commitTransactionWithAnimationMethod.setAccessible(true);

        Whitebox.setInternalState(android.os.Build.VERSION.class, SDK_INT, Build.VERSION_CODES.KITKAT);

        // Act
        commitTransactionWithAnimationMethod.invoke(messengerToolbarFragment);
    }

    @Test
    public void configureChildFragmentWhenFragmentIsNotAdded() throws Exception {
        // Arrange
        Method configureChildFragmentMethod = messengerToolbarFragment.getClass().getDeclaredMethod(MessengerToolbarFragmentTest.CONFIGURE_CHILD_FRAGMENT_METHOD, android.support.v4.app.Fragment.class);
        configureChildFragmentMethod.setAccessible(true);

        PowerMockito.when(messengerToolbarCollapsedFragment, IS_ADDED_METHOD).thenReturn(false);

        expectErrorMessage(IllegalStateException.class, THIS_METHOD_SHOULD_BE_CALLED_ONCE_THE_FRAGMENT_IS_ADDED_MESSAGE);

        // Act
        configureChildFragmentMethod.invoke(messengerToolbarFragment, messengerToolbarCollapsedFragment);
    }

    @Test
    public void configureChildFragmentWhenAssignedAgentDataIsNull() throws Exception {
        // Arrange
        messengerToolbarFragment.onCreate(bundle);

        Method configureChildFragmentMethod = messengerToolbarFragment.getClass().getDeclaredMethod(CONFIGURE_CHILD_FRAGMENT_METHOD, android.support.v4.app.Fragment.class);
        configureChildFragmentMethod.setAccessible(true);

        Whitebox.setInternalState(messengerToolbarFragment, TOOLBAR_TYPE_FIELD, ASSIGNED_AGENT);

        expectErrorMessage(IllegalStateException.class, NULL_ASSIGNED_AGENT_DATA_MESSAGE);

        // Act
        configureChildFragmentMethod.invoke(messengerToolbarFragment, messengerToolbarCollapsedFragment);
    }

    @Test
    public void configureChildFragmentWhenLastActiveAgentsIsNull() throws Exception {
        // Arrange
        messengerToolbarFragment.onCreate(bundle);

        Method configureChildFragmentMethod = messengerToolbarFragment.getClass().getDeclaredMethod(CONFIGURE_CHILD_FRAGMENT_METHOD, android.support.v4.app.Fragment.class);
        configureChildFragmentMethod.setAccessible(true);

        Whitebox.setInternalState(messengerToolbarFragment, TOOLBAR_TYPE_FIELD, LAST_ACTIVE_AGENTS);

        expectErrorMessage(IllegalStateException.class, NULL_LAST_ACTIVE_AGENTS_DATA_MESSAGE);

        // Act
        configureChildFragmentMethod.invoke(messengerToolbarFragment, messengerToolbarCollapsedFragment);
    }

    @Test
    public void configureChildFragmentWhenTitleDataIsNull() throws Exception {
        // Arrange
        messengerToolbarFragment.onCreate(bundle);

        Method configureChildFragmentMethod =
                messengerToolbarFragment.getClass().getDeclaredMethod(CONFIGURE_CHILD_FRAGMENT_METHOD, android.support.v4.app.Fragment.class);
        configureChildFragmentMethod.setAccessible(true);

        Whitebox.setInternalState(messengerToolbarFragment, TOOLBAR_TYPE_FIELD, SIMPLE_TITLE);

        expectErrorMessage(IllegalStateException.class, NULL_LAST_ACTIVE_AGENTS_DATA_MESSAGE);

        // Act
        configureChildFragmentMethod.invoke(messengerToolbarFragment, messengerToolbarCollapsedFragment);
    }

    @Test
    public void setupToolbarWhenTypeIsNull() throws Exception {
        // Arrange
        Method setupToolbarMethod = messengerToolbarFragment.getClass().getDeclaredMethod(SETUP_TOOLBAR_METHOD);
        setupToolbarMethod.setAccessible(true);

        expectErrorMessage(IllegalStateException.class, THE_NECESSARY_FIELDS_ARE_NOT_INITIALIZED_MESSAGE);

        // Act
        setupToolbarMethod.invoke(messengerToolbarFragment);
    }

    private void prepareMessengerToolbarFragment() {
        messengerToolbarFragment.onCreate(bundle);
        messengerToolbarFragment.onCreateView(inflater, container, bundle);
        messengerToolbarFragment.onViewCreated(view, bundle);
    }

    private void expectErrorMessage(Class<? extends Throwable> type, String message) {
        thrown.expect(type);
        thrown.expectMessage(containsString(message));
    }
}
