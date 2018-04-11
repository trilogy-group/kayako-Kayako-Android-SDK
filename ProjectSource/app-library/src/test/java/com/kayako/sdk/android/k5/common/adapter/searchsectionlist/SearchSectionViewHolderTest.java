package com.kayako.sdk.android.k5.common.adapter.searchsectionlist;

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
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchSectionViewHolderTest {

    private SearchSectionViewHolder headerViewHolder;

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Mock
    private View view;

    @Mock
    private TextView textView;

    @Before
    public void setUp() {
        when(view.findViewById(eq(R.id.ko__search_bar))).thenReturn(textView);
        headerViewHolder = new SearchSectionViewHolder(view);
    }

    @Test
    public void constructorMView() {
        //Assert
        verify(view, times(1)).findViewById(eq(R.id.ko__search_bar));
        collector.checkThat(headerViewHolder.mView, is(instanceOf(View.class)));
        collector.checkThat(headerViewHolder.mView, is(equalTo(view)));
    }

    @Test
    public void constructorMTitle() {
        //Act
        TextView textViewMTitle = (TextView) view.findViewById(R.id.ko__search_bar);

        //Assert
        verify(view, times(2)).findViewById(eq(R.id.ko__search_bar));
        collector.checkThat(headerViewHolder.mSearchEditText, is(instanceOf(TextView.class)));
        collector.checkThat(headerViewHolder.mSearchEditText, is(equalTo(textViewMTitle)));
    }
}
