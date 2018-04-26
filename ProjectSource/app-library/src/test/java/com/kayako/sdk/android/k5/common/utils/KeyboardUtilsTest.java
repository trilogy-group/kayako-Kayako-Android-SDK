package com.kayako.sdk.android.k5.common.utils;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class KeyboardUtilsTest {
    @Mock
    private AppCompatActivity appCompatActivity;

    @Mock
    private View view;

    @Mock
    private InputMethodManager inputMethodManager;

    @Test
    public void givenActivityWhenHideKeyboardThenHideKeyboard() {
        //Arrange
        when(appCompatActivity.getCurrentFocus()).thenReturn(view);
        when(appCompatActivity.getSystemService(Context.INPUT_METHOD_SERVICE)).thenReturn(inputMethodManager);

        //Act
        KeyboardUtils.hideKeyboard(appCompatActivity);

        //Assert
        verify(inputMethodManager, times(1)).hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Test
    public void givenActivityWhenShowKeyboardThenShowKeyboard() {
        //Arrange
        when(appCompatActivity.getCurrentFocus()).thenReturn(view);
        when(appCompatActivity.getSystemService(Context.INPUT_METHOD_SERVICE)).thenReturn(inputMethodManager);

        //Act
        KeyboardUtils.showKeyboard(appCompatActivity);

        //Assert
        verify(inputMethodManager, times(1)).showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }
}
