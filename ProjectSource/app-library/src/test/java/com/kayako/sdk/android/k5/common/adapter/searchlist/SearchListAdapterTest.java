package com.kayako.sdk.android.k5.common.adapter.searchlist;

import android.support.v7.widget.RecyclerView;
import android.test.mock.MockContext;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.list.ListType;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.LoadingViewHolder;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LayoutInflater.class})
public class SearchListAdapterTest {

    private MockContext mockContext;

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Mock
    private View mockView;

    @Mock
    private LayoutInflater mockInflater;

    @Mock
    private ViewGroup mockParent;

    @Mock
    private List<BaseListItem> baseListItemList;

    @InjectMocks
    private SearchListAdapter searchListAdapter;

    @Before
    public void setUp() {
        mockStatic(LayoutInflater.class);
        mockContext = new MockContext();
    }

    @Test
    public void getItemViewTypeWhenItemTypeIsNotEqualZero() {
        //Arrange
        int position = 1;
        when(baseListItemList.get(position)).thenReturn(mock(BaseListItem.class));
        searchListAdapter.setData(baseListItemList);

        //Act
        int res = searchListAdapter.getItemViewType(position);

        //Assert
        assertThat(res, is(equalTo(searchListAdapter.getData().get(position).getItemType())));
    }

    @Test
    public void onCreateViewHolderWhenViewTypeIsSearchItem() {
        //Arrange
        when(mockParent.getContext()).thenReturn(mockContext);
        when(LayoutInflater.from(mockContext)).thenReturn(mockInflater);
        when(mockInflater.inflate(eq(R.layout.ko__list_item_search_result), eq(mockParent), eq(false))).thenReturn(mockView);

        //Act
        RecyclerView.ViewHolder viewHolder = searchListAdapter.onCreateViewHolder(mockParent, SearchListType.SEARCH_ITEM);

        //Assert
        collector.checkThat(viewHolder, is(instanceOf(SearchItemViewHolder.class)));
        collector.checkThat(viewHolder.itemView, is(equalTo(mockView)));
    }

    @Test
    public void onCreateViewHolderWhenViewTypeIsNotSearchItem() {
        //Arrange
        when(mockParent.getContext()).thenReturn(mockContext);
        when(LayoutInflater.from(mockContext)).thenReturn(mockInflater);
        when(mockInflater.inflate(eq(R.layout.ko__list_item_loading), eq(mockParent), eq(false))).thenReturn(mockView);

        //Act
        RecyclerView.ViewHolder viewHolder = searchListAdapter.onCreateViewHolder(mockParent, ListType.LOADING_ITEM);

        //Assert
        collector.checkThat(viewHolder, is(instanceOf(LoadingViewHolder.class)));
        collector.checkThat(viewHolder.itemView, is(equalTo(mockView)));
    }
}
