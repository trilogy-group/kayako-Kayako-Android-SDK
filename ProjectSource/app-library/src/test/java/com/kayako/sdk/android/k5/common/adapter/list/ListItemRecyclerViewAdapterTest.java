package com.kayako.sdk.android.k5.common.adapter.list;

import android.support.v7.widget.RecyclerView;
import android.test.mock.MockContext;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.LoadingViewHolder;

import java.lang.reflect.Field;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static junit.framework.Assert.*;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LayoutInflater.class})
public class ListItemRecyclerViewAdapterTest {

    @Mock
    ListItemRecyclerViewAdapter.OnListItemClickListener onListItemClickListener;

    @Mock
    ListItemRecyclerViewAdapter.OnHeaderItemClickListener onHeaderItemClickListener;

    @Mock
    View mockView;

    @Mock
    List<BaseListItem> baseListItems;
    @Mock
    LayoutInflater mockInflater;

    @Mock
    ViewGroup mockParent;

    private MockContext mockContext;

    @InjectMocks
    ListItemRecyclerViewAdapter listItemRecyclerViewAdapter;

    @Before
    public void setUp() {
        mockStatic(LayoutInflater.class);
        mockContext = new MockContext();
    }

    @Test
    public void setListClickListenerTest() throws Exception {
        listItemRecyclerViewAdapter.setListClickListener(onListItemClickListener);
        // Class doesn't contain get method ,i  am going to use reflection for getting value
        final Field field = listItemRecyclerViewAdapter.getClass().getDeclaredField("mListItemClickListener");
        assertEquals(field.get(listItemRecyclerViewAdapter), onListItemClickListener);
    }

    @Test
    public void setHeaderClickListenerTest() throws Exception {
        listItemRecyclerViewAdapter.setHeaderClickListener(onHeaderItemClickListener);
        // Class doesn't contain get method ,i  am going to use reflection for getting value
        final Field field = listItemRecyclerViewAdapter.getClass().getDeclaredField("mHeaderItemClickListener");
        assertEquals(field.get(listItemRecyclerViewAdapter), onHeaderItemClickListener);
    }

    @Test
    public void onCreateViewHolderHeaderItemTest() {
        when(mockParent.getContext()).thenReturn(mockContext);
        when(LayoutInflater.from(mockContext)).thenReturn(mockInflater);
        when(mockInflater.inflate(anyInt(), eq(mockParent), eq(false))).thenReturn(mockView);

        RecyclerView.ViewHolder viewHolder = listItemRecyclerViewAdapter.onCreateViewHolder(mockParent, ListType.HEADER_ITEM);

        assertNotNull(viewHolder);
        assertThat(viewHolder, is(CoreMatchers.instanceOf(HeaderViewHolder.class)));
        assertEquals(viewHolder.itemView, mockView);
    }

    @Test
    public void onCreateViewHolderListItemTest() {
        when(mockParent.getContext()).thenReturn(mockContext);
        when(LayoutInflater.from(mockContext)).thenReturn(mockInflater);
        when(mockInflater.inflate(anyInt(), eq(mockParent), eq(false))).thenReturn(mockView);

        RecyclerView.ViewHolder viewHolder = listItemRecyclerViewAdapter.onCreateViewHolder(mockParent, ListType.LIST_ITEM);

        assertNotNull(viewHolder);
        assertThat(viewHolder, is(CoreMatchers.instanceOf(ListItemViewHolder.class)));
        assertEquals(viewHolder.itemView, mockView);

    }

    @Test
    public void onCreateViewHolderLoadingItemTest() {
        when(mockParent.getContext()).thenReturn(mockContext);
        when(LayoutInflater.from(mockContext)).thenReturn(mockInflater);
        when(mockInflater.inflate(anyInt(), eq(mockParent), eq(false))).thenReturn(mockView);

        RecyclerView.ViewHolder viewHolder = listItemRecyclerViewAdapter.onCreateViewHolder(mockParent, ListType.LOADING_ITEM);

        assertNotNull(viewHolder);
        assertThat(viewHolder, is(CoreMatchers.instanceOf(LoadingViewHolder.class)));
        assertEquals(viewHolder.itemView, mockView);

    }

    @Test
    public void getItemViewTypeTest() {
        int position = 0;
        when(baseListItems.get(0)).thenReturn(mock(BaseListItem.class));
        listItemRecyclerViewAdapter.setData(baseListItems);
        int res = listItemRecyclerViewAdapter.getItemViewType(position);
        assertThat(res, is(equalTo(listItemRecyclerViewAdapter.getData().get(position).getItemType())));
    }
}