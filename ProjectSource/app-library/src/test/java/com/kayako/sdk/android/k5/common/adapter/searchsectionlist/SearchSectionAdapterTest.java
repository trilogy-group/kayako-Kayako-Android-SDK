package com.kayako.sdk.android.k5.common.adapter.searchsectionlist;

import android.support.v7.widget.RecyclerView;
import android.test.mock.MockContext;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.list.HeaderViewHolder;
import com.kayako.sdk.android.k5.common.adapter.list.ListItemRecyclerViewAdapter;
import com.kayako.sdk.android.k5.common.adapter.list.ListType;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LayoutInflater.class})
public class SearchSectionAdapterTest {

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Mock
    SearchSectionAdapter.OnSearchClickListener onSearchClickListener;

    @Mock
    ListItemRecyclerViewAdapter.OnListItemClickListener itemClickListener;


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
    SearchSectionAdapter searchSectionAdapter;

    @Before
    public void setUp() {
        mockStatic(LayoutInflater.class);
        mockContext = new MockContext();
    }

    @Test
    public void constructorTest() {
        searchSectionAdapter = new SearchSectionAdapter(baseListItemList, itemClickListener, onSearchClickListener);
        verify(baseListItemList, times(2)).add(0, null);
    }

    @Test
    public void testOnCreateViewHolderWhenViewTypeIsSearchSectionItem() {
        when(mockParent.getContext()).thenReturn(mockContext);
        when(LayoutInflater.from(mockContext)).thenReturn(mockInflater);
        when(mockInflater.inflate(anyInt(), eq(mockParent), eq(false))).thenReturn(mockView);

        RecyclerView.ViewHolder viewHolder = searchSectionAdapter.onCreateViewHolder(mockParent, SearchSectionListType.SEARCH_SECTION_ITEM);

        collector.checkThat(viewHolder, notNullValue());
        collector.checkThat(viewHolder, is(instanceOf(SearchSectionViewHolder.class)));
        collector.checkThat(viewHolder.itemView, is(equalTo(mockView)));

    }

    @Test
    public void testOnCreateViewHolderWhenViewTypeIsNotSearchSectionItem() {
        when(mockParent.getContext()).thenReturn(mockContext);
        when(LayoutInflater.from(mockContext)).thenReturn(mockInflater);
        when(mockInflater.inflate(anyInt(), eq(mockParent), eq(false))).thenReturn(mockView);

        RecyclerView.ViewHolder viewHolder = searchSectionAdapter.onCreateViewHolder(mockParent, ListType.HEADER_ITEM);

        collector.checkThat(viewHolder, notNullValue());
        collector.checkThat(viewHolder, is(instanceOf(HeaderViewHolder.class)));
        collector.checkThat(viewHolder.itemView, is(equalTo(mockView)));
    }

    @Test
    public void testGetItemViewTypeWhenItemTypeIsNotEqualZero() {
        int position = 1;
        when(baseListItemList.get(position)).thenReturn(mock(BaseListItem.class));
        searchSectionAdapter.setData(baseListItemList);
        int res = searchSectionAdapter.getItemViewType(position);
        assertThat(res, is(equalTo(searchSectionAdapter.getData().get(position).getItemType())));
    }

    @Test
    public void testGetItemViewTypeWhenItemTypeIsEqualZero() {
        int position = 0;
        when(baseListItemList.get(position)).thenReturn(mock(BaseListItem.class));
        searchSectionAdapter.setData(baseListItemList);
        int expected = searchSectionAdapter.getItemViewType(position);
        assertThat(expected, is(equalTo(SearchSectionListType.SEARCH_SECTION_ITEM)));
    }

}