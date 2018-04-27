package com.kayako.sdk.android.k5.common.viewhelpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@PrepareForTest({LayoutInflater.class})
@RunWith(PowerMockRunner.class)
public class CustomStateViewHelperTest {

    private CustomStateViewHelper customStateViewHelper;

    @Mock
    private Context context;

    @Mock
    private View view;

    @Mock
    private LinearLayout linearLayout;

    @Mock
    private LayoutInflater inflater;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        customStateViewHelper = new CustomStateViewHelper(linearLayout);
        mockStatic(LayoutInflater.class);
        when(LayoutInflater.from(context)).thenReturn(inflater);
        when(inflater.inflate(1, linearLayout, false)).thenReturn(view);
    }

    @Test
    public void checkAllView() {
        //Act
        customStateViewHelper.setEmptyView(1, context);
        customStateViewHelper.showEmptyView();
        customStateViewHelper.setLoadingView(1, context);
        customStateViewHelper.showLoadingView();
        customStateViewHelper.setErrorView(1, context);
        customStateViewHelper.showErrorView();

        //Assert
        errorCollector.checkThat(customStateViewHelper.hasEmptyView(), is(true));
        errorCollector.checkThat(customStateViewHelper.hasLoadingView(), is(true));
        errorCollector.checkThat(customStateViewHelper.hasErrorView(), is(true));
    }

    @Test
    public void clearContainer() {
        //Act
        customStateViewHelper.setEmptyView(1, context);
        customStateViewHelper.setLoadingView(1, context);
        customStateViewHelper.setErrorView(1, context);
        customStateViewHelper.hideEmptyView();
        customStateViewHelper.hideLoadingView();
        customStateViewHelper.hideErrorView();
        customStateViewHelper.hideAll();

        //Assert
        Mockito.verify(linearLayout, Mockito.times(4)).removeAllViews();
    }

    @Test
    public void whenEmptyViewNotSetThenShowEmptyViewThroughException() {
        //Arrange
        final String exceptionMessage = "Please setEmptyView() before calling this method";

        //Assert
        thrown.expect(AssertionError.class);
        thrown.expectMessage(exceptionMessage);

        //Act
        customStateViewHelper.showEmptyView();
    }

    @Test
    public void whenLoadingViewNotSetThenShowLoadingViewThroughException() {
        //Arrange
        final String exceptionMessage = "Please setLoadingView() before calling this method";

        //Assertion
        thrown.expect(AssertionError.class);
        thrown.expectMessage(exceptionMessage);

        //Act
        customStateViewHelper.showLoadingView();
    }

    @Test
    public void whenErrorViewNotSetThenShowErrorViewThroughException() {
        //Arrange
        final String exceptionMessage = "Please setErrorView() before calling this method";

        //Assert
        thrown.expect(AssertionError.class);
        thrown.expectMessage(exceptionMessage);

        //Act
        customStateViewHelper.showErrorView();
    }
}
