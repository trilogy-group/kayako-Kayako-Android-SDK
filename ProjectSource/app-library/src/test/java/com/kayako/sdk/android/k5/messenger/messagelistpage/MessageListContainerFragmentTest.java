package com.kayako.sdk.android.k5.messenger.messagelistpage;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.activities.KayakoAttachmentPreviewActivity;
import com.kayako.sdk.android.k5.activities.KayakoSelectConversationActivity;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.utils.file.FileAttachmentUtil;
import com.kayako.sdk.android.k5.core.HelpCenterPref;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.messenger.replyboxview.ReplyBoxContract;
import com.kayako.sdk.android.k5.messenger.toolbarview.MessengerToolbarContract;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
    MessageListContainerFragment.class,
    HelpCenterPref.class,
    Kayako.class,
    MessengerPref.class,
    MessageListContainerFactory.class,
    FragmentActivity.class,
    Intent.class,
    Bundle.class,
    FileAttachmentUtil.class,
    Fragment.class,
    Toast.class,
    Gravity.class
})
public class MessageListContainerFragmentTest {

  private MessageListContainerFragment messageListContainerFragment;

  private static final boolean SAVED_INSTANCE_STATE = false;
  private static final int RESULT_CODE_OK = 1;
  private static final String TOAST_MESSAGE = "HELLO";
  private static final String HINT_MESSAGE = "TEST MESSAGE";

  @Mock
  private Bundle bundle;

  @Mock
  private Context context;

  @Mock
  private MessageListContainerContract.Data contractData;

  @Mock
  private LayoutInflater inflater;

  @Mock
  private ViewGroup viewGroup;

  @Mock
  private View view;

  @Mock
  private FragmentActivity fragmentActivity;

  @Mock
  private Intent intent;

  @Mock
  private Resources resources;

  @Rule
  private ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() {
    messageListContainerFragment = PowerMockito.spy(new MessageListContainerFragment());
    PowerMockito.mockStatic(HelpCenterPref.class);
    PowerMockito.mockStatic(MessengerPref.class);
    PowerMockito.mockStatic(Kayako.class);
    PowerMockito.mockStatic(MessageListContainerFactory.class);
    PowerMockito.mockStatic(FileAttachmentUtil.class);
    PowerMockito.mockStatic(Toast.class);
    PowerMockito.mockStatic(Gravity.class);
    Mockito.when(messageListContainerFragment.getActivity()).thenReturn(fragmentActivity);
    Mockito.when(messageListContainerFragment.getContext()).thenReturn(context);
    Mockito.when(fragmentActivity.getIntent()).thenReturn(intent);
    Mockito.when(intent.getExtras()).thenReturn(bundle);
  }

  @Test
  public void callRetainInstanceOnCreate() {
    // Arrange
    PowerMockito.when(MessageListContainerFactory.getData())
        .thenReturn(contractData);

    // Act
    messageListContainerFragment.onCreate(bundle);

    // Assert
    Mockito.verify(messageListContainerFragment).onCreate(bundle);
    Mockito.verify(messageListContainerFragment).setRetainInstance(true);
    PowerMockito.verifyStatic(MessageListContainerFactory.class, Mockito.times(1));
    MessageListContainerFactory.getPresenter(messageListContainerFragment);
  }

  @Test
  public void callInflateOnCreateView() {
    // Arrange

    // Act
    messageListContainerFragment.onCreateView(inflater, viewGroup, null);

    // Assert
    Mockito.verify(inflater).inflate(R.layout.ko__fragment_message_list_container, viewGroup, SAVED_INSTANCE_STATE);
  }

  @Test
  public void fillFieldsOnViewCreated() {
    // Arrange

    // Act
    messageListContainerFragment.onViewCreated(view, bundle);

    // Assert
    Mockito.verify(messageListContainerFragment).onViewCreated(view, bundle);
    Mockito.verify(messageListContainerFragment, Mockito.times(3)).getChildFragmentManager();
  }

  @Test
  public void callOnActivityCreatedWithNonNullBundle() {
    // Arrange
    MessageListContainerFragment fragmentMock = PowerMockito.mock(MessageListContainerFragment.class);
    bundle.putLong(KayakoSelectConversationActivity.ARG_CONVERSATION_ID, 1L);
    Mockito.when(fragmentMock.getActivity()).thenReturn(fragmentActivity);

    // Act
    fragmentMock.onActivityCreated(bundle);

    // Assert
    Mockito.verify(fragmentMock).onActivityCreated(bundle);
  }

  @Test
  public void testOnActivityResultWithAddAttachment() {
    // Arrange
    int addAttachmentCode = Whitebox.getInternalState(MessageListContainerFragment.class, "REQUEST_CODE_ADD_ATTACHMENT");

    // Act
    messageListContainerFragment.onActivityResult(addAttachmentCode, RESULT_CODE_OK, intent);

    // Assert
    PowerMockito.verifyStatic(FileAttachmentUtil.class);
    FileAttachmentUtil.getFileOnActivityResult(RESULT_CODE_OK, intent);
  }

  @Test
  public void testOnActivityResultWithViewAttachment() {
    // Arrange
    int viewAttachmentCode = Whitebox.getInternalState(MessageListContainerFragment.class, "REQUEST_CODE_VIEW_ATTACHMENT_BEFORE_SENDING");
    int resultCode = Whitebox.getInternalState(KayakoAttachmentPreviewActivity.class, "RESULT_EXIT");

    // Act
    messageListContainerFragment.onActivityResult(viewAttachmentCode, resultCode, intent);

    // Assert
    assertNull(Whitebox.getInternalState(messageListContainerFragment, "mLastFileAttachmentAttached"));
  }

  @Test
  public void testOnDestroyView() {
    // Arrange
    MessageListContainerContract.Presenter presenterMock = PowerMockito.mock(MessageListContainerContract.Presenter.class);
    Whitebox.setInternalState(messageListContainerFragment, "mPresenter", presenterMock);

    // Act
    messageListContainerFragment.onDestroyView();

    // Assert
    Mockito.verify(messageListContainerFragment).onDestroyView();
  }

  @Test
  public void testHasPageLoaded() {
    // Arrange
    boolean isAdded = messageListContainerFragment.isAdded();
    Activity activity = messageListContainerFragment.getActivity();

    // Act
    boolean pageLoaded = messageListContainerFragment.hasPageLoaded();

    // Assert
    Mockito.verify(messageListContainerFragment, Mockito.times(2)).isAdded();
    if (isAdded) {
      Mockito.verify(messageListContainerFragment, Mockito.times(2)).getActivity();
    }
    assertThat(pageLoaded, is(isAdded && activity != null));
  }

  @Test
  public void testShowToastMessageWithResId() throws Exception {
    // Arrange
    Toast toastMock = Mockito.mock(Toast.class);
    PowerMockito.when(Toast.class, "makeText", context, R.string.ko__action_contact, Toast.LENGTH_SHORT)
        .thenReturn(toastMock);

    // Act
    messageListContainerFragment.showToastMessage(R.string.ko__action_contact);

    // Assert
    Mockito.verify(toastMock).show();
  }

  @Test
  public void testShowToastMessageWithString() throws Exception {
    // Arrange
    Toast toastMock = Mockito.mock(Toast.class);
    PowerMockito.when(Toast.class, "makeText", context, TOAST_MESSAGE, Toast.LENGTH_SHORT)
        .thenReturn(toastMock);

    // Act
    messageListContainerFragment.showToastMessage(TOAST_MESSAGE);

    // Assert
    Mockito.verify(toastMock).show();
  }

  @Test
  public void testSetupListInMessageListingView() {
    // Arrange
    List<BaseListItem> baseListItems = new ArrayList<>();
    PowerMockito.when(messageListContainerFragment.hasPageLoaded()).thenReturn(true);
    MessageListContract.ConfigureView messageListViewMock = Mockito.mock(MessageListContract.ConfigureView.class);
    MessageListContainerContract.Presenter presenterMock = Mockito.mock(MessageListContainerContract.Presenter.class);
    Whitebox.setInternalState(messageListContainerFragment, "mMessageListView", messageListViewMock);
    Whitebox.setInternalState(messageListContainerFragment, "mPresenter", presenterMock);

    // Act
    messageListContainerFragment.setupListInMessageListingView(baseListItems);

    // Assert
    Mockito.verify(messageListContainerFragment).setupListInMessageListingView(baseListItems);
    Mockito.verify(messageListViewMock).setupList(baseListItems);
  }

  @Test
  public void testShowEmptyViewInMessageListingView() {
    // Arrange
    PowerMockito.when(messageListContainerFragment.hasPageLoaded()).thenReturn(true);
    MessageListContract.ConfigureView messageListViewMock = Mockito.mock(MessageListContract.ConfigureView.class);
    Whitebox.setInternalState(messageListContainerFragment, "mMessageListView", messageListViewMock);

    // Act
    messageListContainerFragment.showEmptyViewInMessageListingView();

    // Assert
    Mockito.verify(messageListContainerFragment).showEmptyViewInMessageListingView();
    Mockito.verify(messageListViewMock).showEmptyView();
  }

  @Test
  public void testShowErrorViewInMessageListingView() {
    // Arrange
    PowerMockito.when(messageListContainerFragment.hasPageLoaded()).thenReturn(true);
    MessageListContract.ConfigureView messageListViewMock = Mockito.mock(MessageListContract.ConfigureView.class);
    Whitebox.setInternalState(messageListContainerFragment, "mMessageListView", messageListViewMock);

    // Act
    messageListContainerFragment.showErrorViewInMessageListingView();

    // Assert
    Mockito.verify(messageListContainerFragment).showErrorViewInMessageListingView();
    Mockito.verify(messageListViewMock).showErrorView();
  }

  @Test
  public void testShowLoadingViewInMessageListingView() {
    // Arrange
    PowerMockito.when(messageListContainerFragment.hasPageLoaded()).thenReturn(true);
    MessageListContract.ConfigureView messageListViewMock = Mockito.mock(MessageListContract.ConfigureView.class);
    Whitebox.setInternalState(messageListContainerFragment, "mMessageListView", messageListViewMock);

    // Act
    messageListContainerFragment.showLoadingViewInMessageListingView();

    // Assert
    Mockito.verify(messageListContainerFragment).showLoadingViewInMessageListingView();
    Mockito.verify(messageListViewMock).showLoadingView();
  }

  @Test
  public void testHasUserInteractedWithList() {
    // Arrange
    PowerMockito.when(messageListContainerFragment.hasPageLoaded()).thenReturn(true);
    MessageListContract.ConfigureView messageListViewMock = Mockito.mock(MessageListContract.ConfigureView.class);
    Whitebox.setInternalState(messageListContainerFragment, "mMessageListView", messageListViewMock);

    // Act
    messageListContainerFragment.hasUserInteractedWithList();

    // Assert
    Mockito.verify(messageListContainerFragment).hasUserInteractedWithList();
    Mockito.verify(messageListViewMock).hasUserInteractedWithList();
  }

  @Test
  public void testHideReplyBox() {
    // Arrange
    PowerMockito.when(messageListContainerFragment.hasPageLoaded()).thenReturn(true);
    ReplyBoxContract.ConfigureView replyBoxMock = Mockito.mock(ReplyBoxContract.ConfigureView.class);
    Whitebox.setInternalState(messageListContainerFragment, "mReplyBoxView", replyBoxMock);

    // Act
    messageListContainerFragment.hideReplyBox();

    // Assert
    Mockito.verify(messageListContainerFragment).hideReplyBox();
    Mockito.verify(replyBoxMock).hideReplyBox();
  }

  @Test
  public void testShowReplyBox() {
    // Arrange
    PowerMockito.when(messageListContainerFragment.hasPageLoaded()).thenReturn(true);
    ReplyBoxContract.ConfigureView replyBoxMock = Mockito.mock(ReplyBoxContract.ConfigureView.class);
    Whitebox.setInternalState(messageListContainerFragment, "mReplyBoxView", replyBoxMock);

    // Act
    messageListContainerFragment.showReplyBox();

    // Assert
    Mockito.verify(messageListContainerFragment).showReplyBox();
    Mockito.verify(replyBoxMock).showReplyBox();
  }

  @Test
  public void testFocusOnReplyBox() {
    // Arrange
    PowerMockito.when(messageListContainerFragment.hasPageLoaded()).thenReturn(true);
    ReplyBoxContract.ConfigureView replyBoxMock = Mockito.mock(ReplyBoxContract.ConfigureView.class);
    Whitebox.setInternalState(messageListContainerFragment, "mReplyBoxView", replyBoxMock);

    // Act
    messageListContainerFragment.focusOnReplyBox();

    // Assert
    Mockito.verify(messageListContainerFragment).focusOnReplyBox();
    Mockito.verify(replyBoxMock).focusOnReplyBox();
  }

  @Test
  public void testSetReplyBoxHintMessage() {
    // Arrange
    PowerMockito.when(messageListContainerFragment.hasPageLoaded()).thenReturn(true);
    ReplyBoxContract.ConfigureView replyBoxMock = Mockito.mock(ReplyBoxContract.ConfigureView.class);
    Whitebox.setInternalState(messageListContainerFragment, "mReplyBoxView", replyBoxMock);

    // Act
    messageListContainerFragment.setReplyBoxHintMessage(HINT_MESSAGE);

    // Assert
    Mockito.verify(messageListContainerFragment).setReplyBoxHintMessage(HINT_MESSAGE);
    Mockito.verify(replyBoxMock).setReplyBoxHintText(HINT_MESSAGE);
  }

  @Test
  public void testCollapseToolbar() {
    // Arrange
    PowerMockito.when(messageListContainerFragment.hasPageLoaded()).thenReturn(true);
    MessengerToolbarContract.ConfigureView toolbarViewMock = Mockito.mock(MessengerToolbarContract.ConfigureView.class);
    Whitebox.setInternalState(messageListContainerFragment, "mToolbarView", toolbarViewMock);

    // Act
    messageListContainerFragment.collapseToolbar();

    // Assert
    Mockito.verify(messageListContainerFragment).collapseToolbar();
    Mockito.verify(toolbarViewMock).collapseToolbarView();
  }

}