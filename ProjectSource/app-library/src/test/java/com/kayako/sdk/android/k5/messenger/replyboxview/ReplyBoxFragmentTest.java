package com.kayako.sdk.android.k5.messenger.replyboxview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.kayako.sdk.android.k5.R;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.api.support.membermodification.MemberMatcher.methods;
import static org.powermock.api.support.membermodification.MemberModifier.replace;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@PrepareForTest({
        KayakoCreditsHelper.class,
        Intent.class,
        Uri.class,
        Fragment.class
})
@RunWith(PowerMockRunner.class)
public class ReplyBoxFragmentTest {

    private final ReplyBoxFragment replyBoxFragment = new ReplyBoxFragment();
    private Uri uri;

    @Mock
    private Bundle bundle;

    @Mock
    private LayoutInflater layoutInflater;

    @Mock
    private ViewGroup viewGroup;

    @Mock
    private View view;

    @Mock
    private ReplyBoxContract.Presenter presenter;

    @Mock
    private EditText editText;

    @Mock
    private TextView textView;

    @Mock
    private KeyEvent keyEvent;

    @Mock
    private Intent intent;

    @Mock
    private AppCompatActivity appCompatActivity;

    @Mock
    private Editable editable;

    @Mock
    private ImageButton imageButton;

    @Mock
    private ReplyBoxContract.ReplyBoxListener listener;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Captor
    private ArgumentCaptor<Integer> integerArgumentCaptor;

    @Captor
    private ArgumentCaptor<Boolean> booleanArgumentCaptor;

    @Captor
    private ArgumentCaptor<ReplyBoxContract.ReplyBoxListener> replyBoxListenerArgumentCaptor;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Test
    public void onCreate() {
        //Act
        replyBoxFragment.onCreate(bundle);
        final ReplyBoxContract.Presenter mPresenter =
                Whitebox.getInternalState(replyBoxFragment, "mPresenter");

        //Assert
        assertNotNull(mPresenter);
    }

    @Test
    public void onCreateView() {
        //Arrange
        when(layoutInflater.inflate(R.layout.ko__fragment_reply_box, viewGroup, false)).thenReturn(view);


        //Act
        replyBoxFragment.onCreateView(layoutInflater, viewGroup, bundle);
        final View mRoot = Whitebox.getInternalState(replyBoxFragment, "mRoot");

        //Assert
        assertEquals(view, mRoot);
    }

    @Test
    public void onViewCreated() throws Exception {
        //Arrange
        final String dummyPowerMessage = "dummyPowerMessage";
        suppress(methods(Fragment.class, "startActivity"));
        mockStatic(KayakoCreditsHelper.class);
        mockStatic(Uri.class);
        uri = mock(Uri.class);
        Whitebox.setInternalState(replyBoxFragment, "mRoot", view);
        Whitebox.setInternalState(replyBoxFragment, "mPresenter", presenter);
        when(view.findViewById(R.id.ko__reply_box_send_button)).thenReturn(view);
        when(view.findViewById(R.id.reply_box_edittext)).thenReturn(editText);
        when(keyEvent.getKeyCode()).thenReturn(KeyEvent.KEYCODE_ENTER);
        when(view.findViewById(R.id.ko__reply_box_attach_button)).thenReturn(view);
        when(view.findViewById(R.id.ko__reply_box_credits_by)).thenReturn(textView);
        when(KayakoCreditsHelper.getPoweredByMessage()).thenReturn(dummyPowerMessage);
        when(view.findViewById(R.id.ko__reply_box_credits_kayako)).thenReturn(textView);
        when(KayakoCreditsHelper.getLink()).thenReturn("link");
        when(Uri.parse("link")).thenReturn(uri);
        whenNew(Intent.class).withArguments(Intent.ACTION_VIEW, uri).thenReturn(intent);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object arguments[] = invocation.getArguments();
                View.OnClickListener listener = (View.OnClickListener)arguments[0];
                listener.onClick(view);
                return null;
            }
        }).when(view).setOnClickListener(any(View.OnClickListener.class));

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object arguments[] = invocation.getArguments();
                TextView.OnEditorActionListener actionListener = (TextView.OnEditorActionListener)arguments[0];
                actionListener.onEditorAction(textView, 1, keyEvent);
                return null;
            }
        }).when(editText).setOnEditorActionListener(any(TextView.OnEditorActionListener.class));

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object arguments[] = invocation.getArguments();
                View.OnClickListener onClickListener = (View.OnClickListener)arguments[0];
                onClickListener.onClick(view);
                return null;
            }
        }).when(view).setOnClickListener(any(View.OnClickListener.class));

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object arguments[] = invocation.getArguments();
                View.OnClickListener clickListener = (View.OnClickListener)arguments[0];
                clickListener.onClick(view);
                return null;
            }
        }).when(textView).setOnClickListener(any(View.OnClickListener.class));

        //Act
        replyBoxFragment.onViewCreated(view, bundle);
        verify(editText).setText(stringArgumentCaptor.capture());
        verify(textView).setText(stringArgumentCaptor.capture());

        //Assert
        verify(presenter, times(1)).onClickSend();
        verify(presenter, times(1)).onClickEnter();
        verify(presenter, times(1)).onClickAddAttachment();
        errorCollector.checkThat(stringArgumentCaptor.getAllValues().get(0), nullValue());
        errorCollector.checkThat(stringArgumentCaptor.getAllValues().get(1), is(dummyPowerMessage));
    }

    @Test
    public void onActivityCreated() {
        //Arrange
        Whitebox.setInternalState(replyBoxFragment, "mPresenter", presenter);

        //Act
        replyBoxFragment.onActivityCreated(bundle);

        //Assert
        final boolean flag = Whitebox.getInternalState(replyBoxFragment, "mCalled");
        verify(presenter, times(1)).initPage();
        errorCollector.checkThat(flag, is(true));
    }

    @Test
    public void getReplyBoxTextWhenPageNotLoaded() {
        //Act
        final String replyValue = replyBoxFragment.getReplyBoxText();

        //Assert
        errorCollector.checkThat(replyValue, nullValue());
    }

    @Test
    public void setAndGetReplyBoxTexts() throws NoSuchMethodException {
        //Arrange
        invokeSetUpMethod();
        final String editString = "Editable_Text";
        when(view.findViewById(R.id.reply_box_edittext)).thenReturn(editText);
        when(editText.getText()).thenReturn(editable);
        when(editable.toString()).thenReturn(editString);

        //Act
        replyBoxFragment.setReplyBoxText(editString);
        replyBoxFragment.setReplyBoxHintText(editString);
        final String replyString = replyBoxFragment.getReplyBoxText();
        verify(editText).setText(stringArgumentCaptor.capture());
        verify(editText).setHint(stringArgumentCaptor.capture());

        //Assert
        errorCollector.checkThat(replyString, is(editString));
        errorCollector.checkThat(replyString, is(stringArgumentCaptor.getAllValues().get(0)));
        errorCollector.checkThat(replyString, is(stringArgumentCaptor.getAllValues().get(1)));
    }

    @Test
    public void operationsOnReplyBox() throws NoSuchMethodException {
        //Arrange
        invokeSetUpMethod();
        when(view.findViewById(R.id.reply_box_layout)).thenReturn(view);
        when(view.findViewById(R.id.reply_box_edittext)).thenReturn(editText);

        //Act
        replyBoxFragment.hideReplyBox();
        replyBoxFragment.showReplyBox();
        replyBoxFragment.focusOnReplyBox();
        verify(view, times(2)).setVisibility(integerArgumentCaptor.capture());

        //Assert
        errorCollector.checkThat(integerArgumentCaptor.getAllValues().get(0), is(View.GONE));
        errorCollector.checkThat(integerArgumentCaptor.getAllValues().get(1), is(View.VISIBLE));
        verify(editText, times(1)).requestFocus();
    }

    @Test
    public void setAttachmentButtonVisible() throws NoSuchMethodException {
        //Arrange
        final boolean showAttachment = Boolean.TRUE;
        invokeSetUpMethod();
        when(view.findViewById(R.id.ko__reply_box_attach_button)).thenReturn(view);

        //Act
        replyBoxFragment.setAttachmentButtonVisibility(showAttachment);
        verify(view).setVisibility(integerArgumentCaptor.capture());

        //Assert
        errorCollector.checkThat(integerArgumentCaptor.getValue(), is(View.VISIBLE));
    }

    @Test
    public void setAttachmentButtonInVisible() throws NoSuchMethodException {
        //Arrange
        final boolean showAttachment = Boolean.FALSE;
        invokeSetUpMethod();
        when(view.findViewById(R.id.ko__reply_box_attach_button)).thenReturn(view);

        //Act
        replyBoxFragment.setAttachmentButtonVisibility(showAttachment);
        verify(view).setVisibility(integerArgumentCaptor.capture());

        //Assert
        errorCollector.checkThat(integerArgumentCaptor.getValue(), is(View.INVISIBLE));
    }

    @Test
    public void enableAndDisableSendButton() throws NoSuchMethodException {
        //Arrange
        invokeSetUpMethod();
        when(view.findViewById(R.id.ko__reply_box_send_button)).thenReturn(imageButton);

        //Act
        replyBoxFragment.enableSendButton();
        replyBoxFragment.disableSendButton();
        verify(imageButton, times(2)).setEnabled(booleanArgumentCaptor.capture());

        //Assert
        errorCollector.checkThat(booleanArgumentCaptor.getAllValues().get(0), is(true));
        errorCollector.checkThat(booleanArgumentCaptor.getAllValues().get(1), is(false));
    }

    @Test
    public void setReplyBoxListener() {
        //Act
        Whitebox.setInternalState(replyBoxFragment, "mPresenter", presenter);
        replyBoxFragment.setReplyBoxListener(listener);
        verify(presenter).setReplyBoxListener(replyBoxListenerArgumentCaptor.capture());

        //Assert
        errorCollector.checkThat(replyBoxListenerArgumentCaptor.getValue(), is(listener));
    }

    private void invokeSetUpMethod() throws NoSuchMethodException {
        Whitebox.setInternalState(replyBoxFragment, "mRoot", view);
        Method superGetActivity = Fragment.class.getMethod("getActivity");
        Method superIsAdded = Fragment.class.getMethod("isAdded");
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
    }
}
