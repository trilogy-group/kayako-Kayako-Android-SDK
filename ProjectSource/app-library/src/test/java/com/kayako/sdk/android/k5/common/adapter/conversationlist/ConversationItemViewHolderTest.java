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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConversationItemViewHolderTest {

    private ConversationItemViewHolder conversationItemViewHolder;

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Mock
    private View view;

    @Mock
    private TextView textView;

    @Mock
    private ImageView imageView;

    @Mock
    private RelativeLayout relativeLayout;

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
    public void constructorItemView() {
        collector.checkThat(conversationItemViewHolder.itemView, is(instanceOf(View.class)));
        collector.checkThat(conversationItemViewHolder.itemView, is(equalTo(view)));
    }

    @Test
    public void constructorAvatarView() {
        ImageView avatar = (ImageView) view.findViewById(R.id.ko__avatar);

        verify(view, times(2)).findViewById(R.id.ko__avatar);
        collector.checkThat(conversationItemViewHolder.avatar, is(instanceOf(ImageView.class)));
        collector.checkThat(conversationItemViewHolder.avatar, is(equalTo(avatar)));
    }

    @Test
    public void constructorNameView() {
        TextView nameTextView = (TextView) view.findViewById(R.id.ko__name);

        verify(view, times(2)).findViewById(R.id.ko__name);
        collector.checkThat(conversationItemViewHolder.name, is(instanceOf(TextView.class)));
        collector.checkThat(conversationItemViewHolder.name, is(equalTo(nameTextView)));
    }

    @Test
    public void constructorTimeView() {
        TextView timeTextView = (TextView) view.findViewById(R.id.ko__time);

        verify(view, times(2)).findViewById(R.id.ko__time);
        collector.checkThat(conversationItemViewHolder.time, is(instanceOf(TextView.class)));
        collector.checkThat(conversationItemViewHolder.time, is(equalTo(timeTextView)));
    }

    @Test
    public void constructorSubjectView() {
        TextView subjectView = (TextView) view.findViewById(R.id.ko__subject);

        verify(view, times(2)).findViewById(R.id.ko__subject);
        collector.checkThat(conversationItemViewHolder.subject, is(instanceOf(TextView.class)));
        collector.checkThat(conversationItemViewHolder.subject, is(equalTo(subjectView)));
    }

    @Test
    public void constructorLayoutView() {
        RelativeLayout rLayout = (RelativeLayout) view.findViewById(R.id.layout);

        verify(view, times(2)).findViewById(R.id.layout);
        collector.checkThat(conversationItemViewHolder.layout, is(instanceOf(RelativeLayout.class)));
        collector.checkThat(conversationItemViewHolder.layout, is(equalTo(rLayout)));
    }

    @Test
    public void constructorUnreadCounter() {
        TextView unreadCounter = (TextView) view.findViewById(R.id.ko__unread_counter);

        verify(view, times(2)).findViewById(R.id.ko__unread_counter);
        collector.checkThat(conversationItemViewHolder.unreadCounter, is(instanceOf(TextView.class)));
        collector.checkThat(conversationItemViewHolder.unreadCounter, is(equalTo(unreadCounter)));
    }

    @Test
    public void constructorTypingProgress() {
        ImageView typingProgress = (ImageView) view.findViewById(R.id.ko__typing_progress_loader);

        verify(view, times(2)).findViewById(R.id.ko__typing_progress_loader);
        collector.checkThat(conversationItemViewHolder.typingLoader, is(instanceOf(ImageView.class)));
        collector.checkThat(conversationItemViewHolder.typingLoader, is(equalTo(typingProgress)));
    }

    @Test
    public void constructorSubjectLine() {
        View subjectLine = view.findViewById(R.id.ko__subject_line);

        verify(view, times(2)).findViewById(R.id.ko__subject_line);
        collector.checkThat(conversationItemViewHolder.subjectLine, is(instanceOf(View.class)));
        collector.checkThat(conversationItemViewHolder.subjectLine, is(equalTo(subjectLine)));
    }
}