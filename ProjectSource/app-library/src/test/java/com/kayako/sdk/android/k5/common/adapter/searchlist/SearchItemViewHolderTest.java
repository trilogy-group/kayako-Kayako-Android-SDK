package com.kayako.sdk.android.k5.common.adapter.searchlist;

import android.view.View;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchItemViewHolderTest {

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Mock
    private View view;

    @Mock
    private TextView textView;

    private SearchItemViewHolder searchItemViewHolder;

    @Before
    public void setUp() {
        when(view.findViewById(anyInt())).thenReturn(textView);
        searchItemViewHolder = new SearchItemViewHolder(view);
    }

    @Test
    public void constructorMView() {
        // Act
        verify(view, times(2)).findViewById(anyInt());
        // Assert
        collector.checkThat(searchItemViewHolder.mView, notNullValue());
        collector.checkThat(searchItemViewHolder.mView, is(instanceOf(View.class)));
        collector.checkThat(searchItemViewHolder.mView, is(equalTo(view)));
    }

    @Test
    public void constructorMTitle() {
        // Act
        TextView textViewMTitle = (TextView) view.findViewById(R.id.ko__list_item_title);
        verify(view, times(2)).findViewById(R.id.ko__list_item_title);
        // Assert
        collector.checkThat(searchItemViewHolder.mTitle, notNullValue());
        collector.checkThat(searchItemViewHolder.mTitle, is(instanceOf(TextView.class)));
        collector.checkThat(searchItemViewHolder.mTitle, is(equalTo(textViewMTitle)));
    }

    @Test
    public void constructorMSubTitle() {
        //Act
        TextView textViewMSubTitle = (TextView) view.findViewById(R.id.ko__list_item_subtitle);
        verify(view, times(2)).findViewById(R.id.ko__list_item_subtitle);
        // Assert
        collector.checkThat(searchItemViewHolder.mSubTitle, notNullValue());
        collector.checkThat(searchItemViewHolder.mSubTitle, is(instanceOf(TextView.class)));
        collector.checkThat(searchItemViewHolder.mSubTitle, is(equalTo(textViewMSubTitle)));
    }
}
