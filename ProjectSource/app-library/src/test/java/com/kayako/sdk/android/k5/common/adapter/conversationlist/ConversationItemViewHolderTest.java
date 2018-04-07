package com.kayako.sdk.android.k5.common.adapter.conversationlist;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConversationItemViewHolderTest {

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Mock
    View view;
    @Mock
    TextView textView;
    @Mock
    ImageView imageView;
    @Mock
    RelativeLayout relativeLayout;

    ConversationItemViewHolder conversationItemViewHolder;

    @Before
    public void setUp() {
        when(view.findViewById(R.id.ko__avatar)).thenReturn(imageView);
        when(view.findViewById(R.id.ko__name)).thenReturn(textView);
        when(view.findViewById(R.id.ko__time)).thenReturn(textView);
        when(view.findViewById(R.id.ko__subject)).thenReturn(textView);
        when(view.findViewById(R.id.layout)).thenReturn(relativeLayout);
        when(view.findViewById(R.id.ko__unread_counter)).thenReturn(textView);
        when(view.findViewById(R.id.ko__typing_progress_loader)).thenReturn(imageView);
        when(view.findViewById(R.id.ko__subject_line)).thenReturn(view);

        conversationItemViewHolder = new ConversationItemViewHolder(view);
    }

    @Test
    public void constructorItemViewTest() {
        collector.checkThat(conversationItemViewHolder.itemView, notNullValue());
        collector.checkThat(conversationItemViewHolder.itemView, is(instanceOf(View.class)));
        collector.checkThat(conversationItemViewHolder.itemView, is(equalTo(view)));
    }

    @Test
    public void constructorAvatarViewTest() {
        ImageView avatar = (ImageView) view.findViewById(R.id.ko__avatar);

        verify(view, times(2)).findViewById(R.id.ko__avatar);
        collector.checkThat(conversationItemViewHolder.avatar, notNullValue());
        collector.checkThat(conversationItemViewHolder.avatar, is(instanceOf(ImageView.class)));
        collector.checkThat(conversationItemViewHolder.avatar, is(equalTo(avatar)));
    }

    @Test
    public void constructorNameViewTest() {
        TextView nameTextView = (TextView) view.findViewById(R.id.ko__name);

        verify(view, times(2)).findViewById(R.id.ko__name);
        collector.checkThat(conversationItemViewHolder.name, notNullValue());
        collector.checkThat(conversationItemViewHolder.name, is(instanceOf(TextView.class)));
        collector.checkThat(conversationItemViewHolder.name, is(equalTo(nameTextView)));
    }

    @Test
    public void constructorTimeViewTest() {
        TextView timeTextView = (TextView) view.findViewById(R.id.ko__time);

        verify(view, times(2)).findViewById(R.id.ko__time);
        collector.checkThat(conversationItemViewHolder.time, notNullValue());
        collector.checkThat(conversationItemViewHolder.time, is(instanceOf(TextView.class)));
        collector.checkThat(conversationItemViewHolder.time, is(equalTo(timeTextView)));
    }

    @Test
    public void constructorSubjectViewTest() {
        TextView subjectView = (TextView) view.findViewById(R.id.ko__subject);

        verify(view, times(2)).findViewById(R.id.ko__subject);
        collector.checkThat(conversationItemViewHolder.subject, notNullValue());
        collector.checkThat(conversationItemViewHolder.subject, is(instanceOf(TextView.class)));
        collector.checkThat(conversationItemViewHolder.subject, is(equalTo(subjectView)));
    }

    @Test
    public void constructorLayoutViewTest() {
        RelativeLayout rLayout = (RelativeLayout) view.findViewById(R.id.layout);

        verify(view, times(2)).findViewById(R.id.layout);
        collector.checkThat(conversationItemViewHolder.layout, notNullValue());
        collector.checkThat(conversationItemViewHolder.layout, is(instanceOf(RelativeLayout.class)));
        collector.checkThat(conversationItemViewHolder.layout, is(equalTo(rLayout)));
    }

    @Test
    public void constructorUnreadCounterTest() {
        TextView unreadCounter = (TextView) view.findViewById(R.id.ko__unread_counter);

        verify(view, times(2)).findViewById(R.id.ko__unread_counter);
        collector.checkThat(conversationItemViewHolder.unreadCounter, notNullValue());
        collector.checkThat(conversationItemViewHolder.unreadCounter, is(instanceOf(TextView.class)));
        collector.checkThat(conversationItemViewHolder.unreadCounter, is(equalTo(unreadCounter)));
    }

    @Test
    public void constructorTypingProgressTest() {
        ImageView typingProgress = (ImageView) view.findViewById(R.id.ko__typing_progress_loader);

        verify(view, times(2)).findViewById(R.id.ko__typing_progress_loader);
        collector.checkThat(conversationItemViewHolder.typingLoader, notNullValue());
        collector.checkThat(conversationItemViewHolder.typingLoader, is(instanceOf(ImageView.class)));
        collector.checkThat(conversationItemViewHolder.typingLoader, is(equalTo(typingProgress)));
    }

    @Test
    public void constructorSubjectLineTest() {
        View subjectLine = view.findViewById(R.id.ko__subject_line);

        verify(view, times(2)).findViewById(R.id.ko__subject_line);
        collector.checkThat(conversationItemViewHolder.subjectLine, notNullValue());
        collector.checkThat(conversationItemViewHolder.subjectLine, is(instanceOf(View.class)));
        collector.checkThat(conversationItemViewHolder.subjectLine, is(equalTo(subjectLine)));
    }


}