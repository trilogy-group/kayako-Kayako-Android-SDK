package com.kayako.sdk.android.k5.common.adapter.list;

import android.view.View;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

@RunWith(MockitoJUnitRunner.class)
public class HeaderViewHolderTest {

    private HeaderViewHolder headerViewHolder;

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Mock
    private View view;

    @Mock
    private TextView textView;

    @Before
    public void setUp() {
        when(view.findViewById(eq(R.id.ko__list_header_title))).thenReturn(textView);
        headerViewHolder = new HeaderViewHolder(view);
    }

    @Test
    public void constructorMView() {
        //Act
        verify(view, times(1)).findViewById(eq(R.id.ko__list_header_title));

        //Assert
        collector.checkThat(headerViewHolder.mView, is(instanceOf(View.class)));
        collector.checkThat(headerViewHolder.mView, is(equalTo(view)));
    }

    @Test
    public void constructorMTitle() {
        //Act
        TextView textViewMTitle = (TextView) view.findViewById(R.id.ko__list_header_title);
        verify(view, times(2)).findViewById(eq(R.id.ko__list_header_title));

        //Assert
        collector.checkThat(headerViewHolder.mTitle, is(instanceOf(TextView.class)));
        collector.checkThat(headerViewHolder.mTitle, is(equalTo(textViewMTitle)));
    }
}
