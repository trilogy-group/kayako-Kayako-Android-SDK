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
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SectionInfoViewHolderTest {

    private SectionInfoViewHolder sectionInfoViewHolder;

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Mock
    View view;

    @Mock
    TextView textView;

    @Before
    public void setUp() {
        when(view.findViewById(anyInt())).thenReturn(textView);
        sectionInfoViewHolder = new SectionInfoViewHolder(view);
    }

    @Test
    public void constructorMViewTest() {
        verify(view, times(2)).findViewById(anyInt());

        collector.checkThat(sectionInfoViewHolder.mView, is(instanceOf(View.class)));
        collector.checkThat(sectionInfoViewHolder.mView, is(equalTo(view)));
    }

    @Test
    public void constructorMTitleTest() {
        TextView textViewMTitle = (TextView) view.findViewById(R.id.ko__section_title);
        verify(view, times(3)).findViewById(anyInt());

        collector.checkThat(sectionInfoViewHolder.title, is(instanceOf(TextView.class)));
        collector.checkThat(sectionInfoViewHolder.title, is(equalTo(textViewMTitle)));
    }

    @Test
    public void constructorTest() {
        TextView textViewMSubTitle = (TextView) view.findViewById(R.id.ko__section_description);
        verify(view, times(3)).findViewById(anyInt());

        assertThat(sectionInfoViewHolder.description, is(instanceOf(TextView.class)));
        assertThat(sectionInfoViewHolder.description, is(equalTo(textViewMSubTitle)));
    }
}
