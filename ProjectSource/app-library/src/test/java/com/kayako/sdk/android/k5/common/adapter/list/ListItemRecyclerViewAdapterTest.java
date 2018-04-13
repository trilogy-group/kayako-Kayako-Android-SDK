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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static junit.framework.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LayoutInflater.class})
public class ListItemRecyclerViewAdapterTest {

    private MockContext mockContext;

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Mock
    private ListItemRecyclerViewAdapter.OnListItemClickListener onListItemClickListener;

    @Mock
    private ListItemRecyclerViewAdapter.OnHeaderItemClickListener onHeaderItemClickListener;

    @Mock
    private View mockView;

    @Mock
    private List<BaseListItem> baseListItems;

    @Mock
    private LayoutInflater mockInflater;

    @Mock
    private ViewGroup mockParent;

    @InjectMocks
    private ListItemRecyclerViewAdapter listItemRecyclerViewAdapter;

    @Before
    public void setUp() {
        mockStatic(LayoutInflater.class);
        mockContext = new MockContext();
    }

    @Test
    public void setListClickListenerTest() throws Exception {
        //Arrange
        listItemRecyclerViewAdapter.setListClickListener(onListItemClickListener);

        //Act
        final Field field = listItemRecyclerViewAdapter.getClass().getDeclaredField("mListItemClickListener");

        //Assert
        assertEquals(field.get(listItemRecyclerViewAdapter), onListItemClickListener);
    }

    @Test
    public void setHeaderClickListenerTest() throws Exception {
        //Arrange
        listItemRecyclerViewAdapter.setHeaderClickListener(onHeaderItemClickListener);

        //Act
        final Field field = listItemRecyclerViewAdapter.getClass().getDeclaredField("mHeaderItemClickListener");

        //Assert
        assertEquals(field.get(listItemRecyclerViewAdapter), onHeaderItemClickListener);
    }

    @Test
    public void onCreateViewHolderHeaderItemTest() {
        //Arrange
        when(mockParent.getContext()).thenReturn(mockContext);
        when(LayoutInflater.from(mockContext)).thenReturn(mockInflater);
        when(mockInflater.inflate(anyInt(), eq(mockParent), eq(false))).thenReturn(mockView);

        //Act
        RecyclerView.ViewHolder viewHolder = listItemRecyclerViewAdapter.onCreateViewHolder(mockParent, ListType.HEADER_ITEM);

        //Assert
        collector.checkThat(viewHolder, is(instanceOf(HeaderViewHolder.class)));
        collector.checkThat(viewHolder.itemView, is(equalTo(mockView)));
    }

    @Test
    public void onCreateViewHolderListItemTest() {
        //Arrange
        when(mockParent.getContext()).thenReturn(mockContext);
        when(LayoutInflater.from(mockContext)).thenReturn(mockInflater);
        when(mockInflater.inflate(anyInt(), eq(mockParent), eq(false))).thenReturn(mockView);

        //Act
        RecyclerView.ViewHolder viewHolder = listItemRecyclerViewAdapter.onCreateViewHolder(mockParent, ListType.LIST_ITEM);

        //Assert
        collector.checkThat(viewHolder, is(instanceOf(ListItemViewHolder.class)));
        collector.checkThat(viewHolder.itemView, is(equalTo(mockView)));

    }

    @Test
    public void onCreateViewHolderLoadingItemTest() {
        //Arrange
        when(mockParent.getContext()).thenReturn(mockContext);
        when(LayoutInflater.from(mockContext)).thenReturn(mockInflater);
        when(mockInflater.inflate(anyInt(), eq(mockParent), eq(false))).thenReturn(mockView);

        //Act
        RecyclerView.ViewHolder viewHolder = listItemRecyclerViewAdapter.onCreateViewHolder(mockParent, ListType.LOADING_ITEM);

        //Assert
        collector.checkThat(viewHolder, is(instanceOf(LoadingViewHolder.class)));
        collector.checkThat(viewHolder.itemView, is(equalTo(mockView)));
    }

    @Test
    public void getItemViewTypeTest() {
        //Arrange
        final int position = 0;
        when(baseListItems.get(0)).thenReturn(mock(BaseListItem.class));
        listItemRecyclerViewAdapter.setData(baseListItems);

        //Act
        final int res = listItemRecyclerViewAdapter.getItemViewType(position);

        //Assert
        collector.checkThat(res, is(equalTo(listItemRecyclerViewAdapter.getData()
                .get(position).getItemType())));
    }
}
