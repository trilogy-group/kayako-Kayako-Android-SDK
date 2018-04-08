package com.kayako.sdk.android.k5.common.adapter.sectioninfolist;

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
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LayoutInflater.class})
public class SectionInfoAdapterTest {

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Mock
    ListItemRecyclerViewAdapter.OnListItemClickListener onListItemClickListener;

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
    SectionInfoAdapter sectionInfoAdapter;

    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";

    @Before
    public void setUp() {
        mockStatic(LayoutInflater.class);
        mockContext = new MockContext();
    }

    @Test
    public void constructorTest() {
        sectionInfoAdapter = new SectionInfoAdapter(baseListItemList, onListItemClickListener,
                TITLE, DESCRIPTION);
        verify(baseListItemList, times(2)).add(0, null);
    }

    @Test
    public void onCreateViewHolderWhenViewTypeIsSectionInfoItem() {
        when(mockParent.getContext()).thenReturn(mockContext);
        when(LayoutInflater.from(mockContext)).thenReturn(mockInflater);
        when(mockInflater.inflate(anyInt(), eq(mockParent), eq(false))).thenReturn(mockView);

        RecyclerView.ViewHolder viewHolder = sectionInfoAdapter.onCreateViewHolder(mockParent,
                SectionInfoListType.SECTION_INFO_ITEM);

        collector.checkThat(viewHolder, notNullValue());
        collector.checkThat(viewHolder, is(CoreMatchers.instanceOf(SectionInfoViewHolder.class)));
        collector.checkThat(viewHolder.itemView, is(equalTo(mockView)));

    }

    @Test
    public void onCreateViewHolderWhenViewTypeIsNotSectionInfoItem() {
        when(mockParent.getContext()).thenReturn(mockContext);
        when(LayoutInflater.from(mockContext)).thenReturn(mockInflater);
        when(mockInflater.inflate(anyInt(), eq(mockParent), eq(false))).thenReturn(mockView);

        RecyclerView.ViewHolder viewHolder = sectionInfoAdapter.onCreateViewHolder(mockParent,
                ListType.HEADER_ITEM);

        collector.checkThat(viewHolder, notNullValue());
        collector.checkThat(viewHolder, is(CoreMatchers.instanceOf(HeaderViewHolder.class)));
        collector.checkThat(viewHolder.itemView, is(equalTo(mockView)));
    }

    @Test
    public void testGetItemViewTypeWhenItemTypeIsNotEqualToZero() {
        int position = 1;
        when(baseListItemList.get(position)).thenReturn(mock(BaseListItem.class));
        sectionInfoAdapter.setData(baseListItemList);
        int res = sectionInfoAdapter.getItemViewType(position);
        assertThat(res, is(equalTo(sectionInfoAdapter.getData().get(position).getItemType())));
    }

    @Test
    public void testGetItemViewTypeWhenItemTypeIsEqualToZero() {
        int position = 0;
        when(baseListItemList.get(position)).thenReturn(mock(BaseListItem.class));
        sectionInfoAdapter.setData(baseListItemList);
        int expected = sectionInfoAdapter.getItemViewType(position);
        assertThat(expected, is(equalTo(SectionInfoListType.SECTION_INFO_ITEM)));
    }
}