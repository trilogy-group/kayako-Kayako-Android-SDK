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
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(MockitoJUnitRunner.class)
public class ListItemViewHolderTest {

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Mock
    View view;
    @Mock
    TextView textView;

    ListItemViewHolder listItemViewHolder;

    @Before
    public void setUp() {
        when(view.findViewById(anyInt())).thenReturn(textView);
        listItemViewHolder = new ListItemViewHolder(view);
    }

    @Test
    public void constructorMViewTest() {
        verify(view, times(2)).findViewById(anyInt());

        collector.checkThat(listItemViewHolder.mView, notNullValue());
        collector.checkThat(listItemViewHolder.mView, is(instanceOf(View.class)));
        collector.checkThat(listItemViewHolder.mView, is(equalTo(view)));
    }

    @Test
    public void constructorMTitleTest() {
        TextView textViewMTitle = (TextView) view.findViewById(R.id.ko__list_header_title);
        verify(view, times(3)).findViewById(anyInt());

        collector.checkThat(listItemViewHolder.mTitle, notNullValue());
        collector.checkThat(listItemViewHolder.mTitle, is(instanceOf(TextView.class)));
        collector.checkThat(listItemViewHolder.mTitle, is(equalTo(textViewMTitle)));


    }

    @Test
    public void constructorTest() {
        TextView textViewMSubTitle = (TextView) view.findViewById(R.id.ko__list_item_subtitle);
        verify(view, times(3)).findViewById(anyInt());

        collector.checkThat(listItemViewHolder.mSubTitle, notNullValue());
        collector.checkThat(listItemViewHolder.mSubTitle, is(instanceOf(TextView.class)));
        collector.checkThat(listItemViewHolder.mSubTitle, is(equalTo(textViewMSubTitle)));
    }

}