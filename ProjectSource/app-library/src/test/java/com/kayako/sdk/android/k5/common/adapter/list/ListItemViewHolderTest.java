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
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

@RunWith(MockitoJUnitRunner.class)
public class ListItemViewHolderTest {

    private ListItemViewHolder listItemViewHolder;

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Mock
    private View view;

    @Mock
    private TextView textView;

    @Before
    public void setUp() {
        when(view.findViewById(eq(R.id.ko__list_item_title))).thenReturn(textView);
        when(view.findViewById(eq(R.id.ko__list_item_subtitle))).thenReturn(textView);
        listItemViewHolder = new ListItemViewHolder(view);
    }

    @Test
    public void constructorMView() {
        //Act
        verify(view, times(1)).findViewById(eq(R.id.ko__list_item_title));
        verify(view, times(1)).findViewById(eq(R.id.ko__list_item_subtitle));

        //Assert
        collector.checkThat(listItemViewHolder.mView, is(instanceOf(View.class)));
        collector.checkThat(listItemViewHolder.mView, is(equalTo(view)));
    }

    @Test
    public void constructorMTitle() {
        //Act
        TextView textViewMTitle = (TextView) view.findViewById(R.id.ko__list_item_title);
        verify(view, times(2)).findViewById(eq(R.id.ko__list_item_title));
        verify(view, times(1)).findViewById(eq(R.id.ko__list_item_subtitle));

        //Assert
        collector.checkThat(listItemViewHolder.mTitle, is(instanceOf(TextView.class)));
        collector.checkThat(listItemViewHolder.mTitle, is(equalTo(textViewMTitle)));
    }

    @Test
    public void constructorMSubTitle() {
        //Act
        TextView textViewMSubTitle = (TextView) view.findViewById(R.id.ko__list_item_subtitle);
        verify(view, times(1)).findViewById(eq(R.id.ko__list_item_title));
        verify(view, times(2)).findViewById(eq(R.id.ko__list_item_subtitle));

        //Assert
        collector.checkThat(listItemViewHolder.mSubTitle, is(instanceOf(TextView.class)));
        collector.checkThat(listItemViewHolder.mSubTitle, is(equalTo(textViewMSubTitle)));
    }
}
