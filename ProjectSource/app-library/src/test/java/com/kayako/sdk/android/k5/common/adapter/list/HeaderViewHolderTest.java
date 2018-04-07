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
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class HeaderViewHolderTest {

    @Rule
    public final ErrorCollector collector = new ErrorCollector();
    @Mock
    View view;

    @Mock
    TextView textView;

    HeaderViewHolder headerViewHolder;

    @Before
    public void setUp() {
        when(view.findViewById(anyInt())).thenReturn(textView);
        headerViewHolder = new HeaderViewHolder(view);
    }

    @Test
    public void constructorMViewTest() {
        verify(view, times(1)).findViewById(anyInt());

        collector.checkThat(headerViewHolder.mView, notNullValue());
        collector.checkThat(headerViewHolder.mView, is(instanceOf(View.class)));
        collector.checkThat(headerViewHolder.mView, is(equalTo(view)));
    }

    @Test
    public void constructorMTitleTest() {
        TextView textViewMTitle = (TextView) view.findViewById(R.id.ko__list_header_title);
        verify(view, times(2)).findViewById(anyInt());

        collector.checkThat(headerViewHolder.mTitle, notNullValue());
        collector.checkThat(headerViewHolder.mTitle, is(instanceOf(TextView.class)));
        collector.checkThat(headerViewHolder.mTitle, is(equalTo(textViewMTitle)));
    }
}