package com.kayako.sdk.android.k5.common.adapter.sectioninfolist;

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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SectionInfoViewHolderTest {

    private SectionInfoViewHolder sectionInfoViewHolder;

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Mock
    private View view;

    @Mock
    private TextView textView;

    @Before
    public void setUp() {
        when(view.findViewById(eq(R.id.ko__section_title))).thenReturn(textView);
        when(view.findViewById(eq(R.id.ko__section_description))).thenReturn(textView);
        sectionInfoViewHolder = new SectionInfoViewHolder(view);
    }

    @Test
    public void constructorMView() {
        //Act
        verify(view, times(1)).findViewById(eq(R.id.ko__section_title));
        verify(view, times(1)).findViewById(eq(R.id.ko__section_description));

        //Assert
        collector.checkThat(sectionInfoViewHolder.mView, is(instanceOf(View.class)));
        collector.checkThat(sectionInfoViewHolder.mView, is(equalTo(view)));
    }

    @Test
    public void constructorMTitle() {
        //Act
        TextView textViewMTitle = (TextView) view.findViewById(R.id.ko__section_title);
        verify(view, times(2)).findViewById(eq(R.id.ko__section_title));
        verify(view, times(1)).findViewById(eq(R.id.ko__section_description));

        //Assert
        collector.checkThat(sectionInfoViewHolder.title, is(instanceOf(TextView.class)));
        collector.checkThat(sectionInfoViewHolder.title, is(equalTo(textViewMTitle)));
    }

    @Test
    public void constructor() {
        //Act
        TextView textViewMSubTitle = (TextView) view.findViewById(R.id.ko__section_description);
        verify(view, times(2)).findViewById(eq(R.id.ko__section_description));
        verify(view, times(1)).findViewById(eq(R.id.ko__section_title));

        //Assert
        assertThat(sectionInfoViewHolder.description, is(instanceOf(TextView.class)));
        assertThat(sectionInfoViewHolder.description, is(equalTo(textViewMSubTitle)));
    }
}

