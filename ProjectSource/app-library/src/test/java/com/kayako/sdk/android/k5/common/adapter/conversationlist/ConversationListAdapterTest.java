package com.kayako.sdk.android.k5.common.adapter.conversationlist;

import android.support.v7.widget.RecyclerView;
import android.test.mock.MockContext;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.list.ListType;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.LoadingViewHolder;


import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Field;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LayoutInflater.class})

public class ConversationListAdapterTest {

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Mock
    ConversationListAdapter.OnClickConversationListener onClickConversationListener;


    @Mock
    View mockView;


    @Mock
    LayoutInflater mockInflater;

    @Mock
    ViewGroup mockParent;

    private MockContext mockContext;

    @Mock
    List<BaseListItem> baseListItemList;

    @InjectMocks
    ConversationListAdapter conversationListAdapter;

    @Before
    public void setUp() {
        mockStatic(LayoutInflater.class);
        mockContext = new MockContext();
    }

    @Test
    public void setListClickListenerTest() throws Exception {
        conversationListAdapter = new
                ConversationListAdapter(baseListItemList, onClickConversationListener);
        // Class doesn't contain get method ,i  am going to use reflection for getting value
        final Field field = conversationListAdapter.getClass().getDeclaredField("mListener");
        field.setAccessible(true);
        assertEquals(field.get(conversationListAdapter), onClickConversationListener);
    }

    @Test
    public void onCreateViewHolderWhenViewTypeIsConversationListItem() {
        when(mockParent.getContext()).thenReturn(mockContext);
        when(LayoutInflater.from(mockContext)).thenReturn(mockInflater);
        when(mockInflater.inflate(anyInt(), eq(mockParent), eq(false))).thenReturn(mockView);

        RecyclerView.ViewHolder viewHolder = conversationListAdapter
                .onCreateViewHolder(mockParent, ConversationListType.CONVERSATION_LIST_ITEM);

        collector.checkThat(viewHolder, notNullValue());
        collector.checkThat(viewHolder, is(instanceOf(ConversationItemViewHolder.class)));
        collector.checkThat(viewHolder.itemView, is(equalTo(mockView)));

    }

    @Test
    public void onCreateViewHolderWhenViewTypeIsLoadingItem() {
        when(mockParent.getContext()).thenReturn(mockContext);
        when(LayoutInflater.from(mockContext)).thenReturn(mockInflater);
        when(mockInflater.inflate(anyInt(), eq(mockParent), eq(false))).thenReturn(mockView);

        RecyclerView.ViewHolder viewHolder = conversationListAdapter
                .onCreateViewHolder(mockParent, ListType.LOADING_ITEM);

        collector.checkThat(viewHolder, notNullValue());
        collector.checkThat(viewHolder, is(CoreMatchers.instanceOf(LoadingViewHolder.class)));
        collector.checkThat(viewHolder.itemView, is(equalTo(mockView)));

    }


    @Test
    public void getItemViewTypeTest() {
        int position = 0;
        when(baseListItemList.get(0)).thenReturn(mock(BaseListItem.class));
        conversationListAdapter.setData(baseListItemList);
        int res = conversationListAdapter.getItemViewType(position);
        assertThat(res, is(equalTo(conversationListAdapter.getData().get(position).getItemType())));
    }
}
