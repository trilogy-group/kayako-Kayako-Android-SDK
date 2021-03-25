package com.kayako.sdk.android.k5.common.viewhelpers;

import android.content.Context;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultStateViewHelperTest {

    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private DefaultStateViewHelper defaultStateViewHelper;

    @Mock
    private Context context;

    @Mock
    private ViewStub viewStub;

    @Mock
    private TextView textView;

    @Mock
    private View.OnClickListener listener;

    @Before
    public void setUp() {
        when(viewStub.findViewById(anyInt())).thenReturn(viewStub);
        defaultStateViewHelper = new DefaultStateViewHelper(viewStub);
    }

    @Test
    public void setupEmptyView() {
        //Arrange
        when(viewStub.findViewById(anyInt())).thenReturn(textView);

        //Act
        defaultStateViewHelper.setupEmptyView(TITLE, DESCRIPTION);
        defaultStateViewHelper.showEmptyView(context);
        defaultStateViewHelper.hideEmptyView();

        //Assert
        verify(viewStub, times(1)).setVisibility(View.VISIBLE);
        verify(viewStub, times(1)).setVisibility(View.GONE);
    }

    @Test
    public void setupErrorView() {
        //Arrange
        when(viewStub.findViewById(anyInt())).thenReturn(textView);

        //Act
        defaultStateViewHelper.setupErrorView(TITLE, DESCRIPTION, listener);
        defaultStateViewHelper.showErrorView(context);
        defaultStateViewHelper.hideErrorView();

        //Assert
        verify(viewStub, times(1)).setVisibility(View.VISIBLE);
        verify(viewStub, times(1)).setVisibility(View.GONE);
    }

    @Test
    public void setupLoadingView() {
        //Act
        defaultStateViewHelper.showLoadingView();
        defaultStateViewHelper.hideLoadingView();

        //Assert
        verify(viewStub, times(2)).setVisibility(View.VISIBLE);
        verify(viewStub, times(2)).setVisibility(View.GONE);
    }
}
