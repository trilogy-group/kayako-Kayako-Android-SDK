package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.content.res.Resources;
import android.test.mock.MockContext;
import android.test.mock.MockResources;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputEmailListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputEmailViewHolder;
import com.kayako.sdk.android.k5.core.Kayako;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        InputFieldCommonStateHelper.class,
        Html.class,
        Kayako.class,
        LayoutInflater.class
})
public class InputFieldEmailHelperTest {

    private static final int LABEL_SUBMIT = R.string.ko__label_submit;
    private static final int HINT_DEFAULT_MESSENGER = R.string.ko__messenger_input_email_message_hint_default;
    private static final int HINT_DEFAULT = R.string.ko__messenger_input_email_message_hint_default;
    private static final String EMAIL = "myemail@abcd.com";
    private static final String DUMMY_STRING = "abcd";

    private InputFieldEmailHelper inputFieldEmailHelper;
    private InputEmailViewHolder viewHolder;

    @Mock
    private MyMockContext mockContext;

    @Mock
    private MyMockResource mockResource;

    @Mock
    private LayoutInflater mockInflater;

    @Mock
    private View mockView;

    @Mock
    private EditText emailEditText;

    @Mock
    private LinearLayout emailFieldLayout;

    @Mock
    public LinearLayout inputLayout;

    @Mock
    public LinearLayout submittedLayout;

    @Mock
    private TextView messageHint;

    @Mock
    private TextView messageInstruction;

    @Mock
    private Button submitButton;

    @Mock
    private Editable mockEditable;

    @Mock
    private Spanned mockSpanned;

    @Before
    public void setUp() throws Exception {
        mockStatic(LayoutInflater.class, Html.class, Kayako.class);
        when(mockView.getContext()).thenReturn(mockContext);
        when(Kayako.getApplicationContext()).thenReturn(mockContext);
        when(mockView.findViewById(R.id.ko__email_input_field_edittext)).thenReturn(emailEditText);
        when(mockView.findViewById(R.id.message_instruction)).thenReturn(messageInstruction);
        when(mockView.findViewById(R.id.submitted_layout)).thenReturn(submittedLayout);
        when(mockView.findViewById(R.id.input_layout)).thenReturn(inputLayout);
        when(mockView.findViewById(R.id.ko__email_input_field_message_hint)).thenReturn(messageHint);
        when(mockView.findViewById(R.id.ko__email_input_field_submit_button)).thenReturn(submitButton);
        when(mockView.findViewById(R.id.email_field_layout)).thenReturn(emailFieldLayout);
        when(LayoutInflater.from(mockContext)).thenReturn(mockInflater);
        when(mockInflater.inflate(eq(R.layout.ko__include_messenger_input_field_email),
                eq(emailFieldLayout), eq(false))).thenReturn(mockView);
        when(mockContext.getResources()).thenReturn(mockResource);

        viewHolder = new InputEmailViewHolder(mockView);

        Constructor<InputFieldEmailHelper> c = InputFieldEmailHelper.class.getDeclaredConstructor();
        c.setAccessible(true);
        inputFieldEmailHelper = c.newInstance();
    }

    @Test
    public void setFocusedFieldState() throws Exception {
        //Arrange
        when(Html.fromHtml(mockContext.getString(HINT_DEFAULT_MESSENGER))).thenReturn(mockSpanned);

        //Act
        final Method method = inputFieldEmailHelper.getClass().getDeclaredMethod(
                "setFocusedFieldState", InputEmailViewHolder.class);
        method.setAccessible(true);
        method.invoke(inputFieldEmailHelper, viewHolder);

        //Assert
        verify(mockContext, times(2)).getResources();
        verify(mockResource, times(1)).getString(LABEL_SUBMIT);
        verify(mockContext, times(2)).getString(HINT_DEFAULT_MESSENGER);
        verify(messageHint, times(1)).setText(Html.fromHtml(mockContext
                .getString(HINT_DEFAULT_MESSENGER)));
        verify(submitButton, times(1)).setText(mockContext.getResources()
                .getString(LABEL_SUBMIT));

        final Spanned expectedSpanned = Html.fromHtml(mockContext.getString(HINT_DEFAULT));
        assertThat(mockSpanned, is(expectedSpanned));
    }

    @Test
    public void setUnfocusedFieldState() throws Exception {
        //Arrange
        when(Html.fromHtml(mockContext.getString(HINT_DEFAULT))).thenReturn(mockSpanned);

        //Act
        final Method method = inputFieldEmailHelper.getClass().getDeclaredMethod(
                "setUnfocusedFieldState", InputEmailViewHolder.class);
        method.setAccessible(true);
        method.invoke(inputFieldEmailHelper, viewHolder);

        //Assert
        verify(mockContext, times(2)).getResources();
        verify(mockResource, times(1)).getString(LABEL_SUBMIT);
        verify(mockContext, times(2)).getString(HINT_DEFAULT);
        verify(messageHint, times(1)).setText(Html.fromHtml(mockContext
                .getString(HINT_DEFAULT)));
        verify(submitButton, times(1)).setText(mockContext.getResources()
                .getString(LABEL_SUBMIT));

        final Spanned expectedSpanned = Html.fromHtml(mockContext.getString(HINT_DEFAULT));
        assertThat(mockSpanned, is(expectedSpanned));
    }

    @Test
    public void configureInputEmailField() {
        //Arrange
        InputEmailListItem inputEmailListItem = mock(InputEmailListItem.class);
        when(mockContext.getResources()).thenReturn(mockResource);
        when(Kayako.getApplicationContext().getResources().getString(
                R.string.ko__messenger_input_email_message_instruction)).thenReturn(DUMMY_STRING);

        //Act
        inputFieldEmailHelper.configureInputEmailField(viewHolder, inputEmailListItem);

        //Assert
        verify(emailEditText, times(1)).setHint(R.string.ko__messenger_input_email_edittext_hint);
        verify(inputEmailListItem, times(1)).hasSubmittedValue();
    }

    @Test
    public void setErrorFieldState() throws Exception {
        //Arrange
        when(Html.fromHtml(mockContext.getString(HINT_DEFAULT))).thenReturn(mockSpanned);

        //Act
        final Method method = inputFieldEmailHelper.getClass().getDeclaredMethod(
                "setErrorFieldState", InputEmailViewHolder.class);
        method.setAccessible(true);
        method.invoke(inputFieldEmailHelper, viewHolder);

        //Assert
        verify(mockContext, times(2)).getResources();
        verify(mockResource, times(1)).getString(LABEL_SUBMIT);
        verify(mockContext, times(1)).getString(HINT_DEFAULT);
        verify(messageHint, times(1)).setText(Html.fromHtml(mockContext
                .getString(HINT_DEFAULT)));
        verify(submitButton, times(1)).setText(mockContext.getResources()
                .getString(LABEL_SUBMIT));

        final Spanned expectedSpanned = Html.fromHtml(mockContext.getString(HINT_DEFAULT));
        assertThat(mockSpanned, is(expectedSpanned));
    }

    @Test
    public void setEmailFieldStateElseCase() throws Exception {
        //Arrange
        final Pattern pattern = mock(Pattern.class);
        final Matcher matcher = mock(Matcher.class);
        when(pattern.matcher(EMAIL)).thenReturn(matcher);
        when(matcher.matches()).thenReturn(true);
        setFinalStatic(Patterns.class.getField("EMAIL_ADDRESS"), pattern);
        when(mockEditable.toString()).thenReturn(EMAIL);
        when(emailEditText.getText()).thenReturn(mockEditable);
        when(mockView.findViewById(R.id.ko__email_input_field_edittext)).thenReturn(emailEditText);
        viewHolder = new InputEmailViewHolder(mockView);

        //Act
        final Method method = inputFieldEmailHelper.getClass().getDeclaredMethod(
                "setEmailFieldState", InputEmailViewHolder.class, boolean.class);
        method.setAccessible(true);
        method.invoke(inputFieldEmailHelper, viewHolder, false);

        //Assert
        verify(emailEditText, times(1)).getText();
        verify(mockView, times(2)).findViewById(R.id.ko__email_input_field_edittext);
        verify(mockContext, times(2)).getResources();
        verify(mockResource, times(1)).getString(LABEL_SUBMIT);
        verify(mockContext, times(1)).getString(HINT_DEFAULT);
        verify(messageHint, times(1)).setText(Html.fromHtml(mockContext
                .getString(HINT_DEFAULT)));
        verify(submitButton, times(1)).setText(mockContext.getResources()
                .getString(LABEL_SUBMIT));
    }

    @Test
    public void setEmailFieldStateWhenValideEmailIsFalse() throws Exception {
        //Arrange
        final Pattern pattern = PowerMockito.mock(Pattern.class);
        final Matcher matcher = PowerMockito.mock(Matcher.class);
        when(pattern.matcher(EMAIL)).thenReturn(matcher);
        when(matcher.matches()).thenReturn(false);
        setFinalStatic(Patterns.class.getField("EMAIL_ADDRESS"), pattern);
        when(mockEditable.toString()).thenReturn(EMAIL);
        when(emailEditText.getText()).thenReturn(mockEditable);
        when(mockView.findViewById(R.id.ko__email_input_field_edittext)).thenReturn(emailEditText);
        when(mockView.isFocused()).thenReturn(false);
        viewHolder = new InputEmailViewHolder(mockView);

        //Act
        final Method method = inputFieldEmailHelper.getClass().getDeclaredMethod(
                "setEmailFieldState", InputEmailViewHolder.class, boolean.class);
        method.setAccessible(true);
        method.invoke(inputFieldEmailHelper, viewHolder, false);

        //Assert
        verify(emailEditText, times(1)).getText();
        verify(mockView, times(2)).findViewById(R.id.ko__email_input_field_edittext);
        verify(mockContext, times(2)).getResources();
        verify(mockResource, times(1)).getString(LABEL_SUBMIT);
        verify(mockContext, times(1)).getString(HINT_DEFAULT_MESSENGER);
        verify(messageHint, times(1)).setText(Html.fromHtml(mockContext
                .getString(HINT_DEFAULT_MESSENGER)));
        verify(submitButton, times(1)).setText(mockContext.getResources()
                .getString(LABEL_SUBMIT));
    }

    @Test
    public void setEmailFieldStateWhenValideEmailIsTrue() throws Exception {
        //Arrange
        final Pattern pattern = PowerMockito.mock(Pattern.class);
        final Matcher matcher = PowerMockito.mock(Matcher.class);
        when(pattern.matcher(EMAIL)).thenReturn(matcher);
        when(matcher.matches()).thenReturn(true);
        setFinalStatic(Patterns.class.getField("EMAIL_ADDRESS"), pattern);
        when(mockEditable.toString()).thenReturn(EMAIL);
        when(emailEditText.getText()).thenReturn(mockEditable);
        when(mockView.findViewById(R.id.ko__email_input_field_edittext)).thenReturn(emailEditText);
        viewHolder = new InputEmailViewHolder(mockView);

        //Act
        final Method method = inputFieldEmailHelper.getClass().getDeclaredMethod(
                "setEmailFieldState", InputEmailViewHolder.class, boolean.class);
        method.setAccessible(true);
        method.invoke(inputFieldEmailHelper, viewHolder, true);

        //Assert
        verify(emailEditText, times(1)).getText();
        verify(mockView, times(2)).findViewById(R.id.ko__email_input_field_edittext);
        verify(mockContext, times(2)).getResources();
        verify(mockResource, times(1)).getString(LABEL_SUBMIT);
        verify(messageHint, times(1)).setText(Html.fromHtml(mockContext
                .getString(HINT_DEFAULT)));
        verify(submitButton, times(1)).setText(mockContext.getResources()
                .getString(LABEL_SUBMIT));
    }

    static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);
        // removing final modifier from field
        final Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }
}

class MyMockContext extends MockContext {

    @Override
    public Resources getResources() {
        return new MyMockResource();
    }
}

class MyMockResource extends MockResources {
    private static final String DUMMY_STRING = "Dumb-Dumber";

    @Override
    public String getString(int id) throws NotFoundException {
        return DUMMY_STRING;
    }
}
