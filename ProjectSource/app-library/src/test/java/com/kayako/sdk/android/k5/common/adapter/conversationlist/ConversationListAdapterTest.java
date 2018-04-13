package com.kayako.sdk.android.k5.common.adapter.conversationlist;

import android.support.v7.widget.RecyclerView;
import android.test.mock.MockContext;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;
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
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LayoutInflater.class})

public class ConversationListAdapterTest {

    private MockContext mockContext;

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Mock
    private ConversationListAdapter.OnClickConversationListener onClickConversationListener;

    @Mock
    private View mockView;

    @Mock
    private LayoutInflater mockInflater;

    @Mock
    private ViewGroup mockParent;

    @Mock
    private List<BaseListItem> baseListItemList;

    @InjectMocks
    private ConversationListAdapter conversationListAdapter;

    @Before
    public void setUp() {
        mockStatic(LayoutInflater.class);
        mockContext = new MockContext();
    }

    @Test
    public void setListClickListenerTest() throws Exception {
        //Arrange
        conversationListAdapter = new
                ConversationListAdapter(baseListItemList, onClickConversationListener);

        //Act
        // Class doesn't contain get method ,i  am going to use reflection for getting value
        final Field field = conversationListAdapter.getClass().getDeclaredField("mListener");
        field.setAccessible(true);

        //Assert
        assertEquals(field.get(conversationListAdapter), onClickConversationListener);
    }

    @Test
    public void onCreateViewHolderWhenViewTypeIsConversationListItem() {
        //Arrange
        when(mockParent.getContext()).thenReturn(mockContext);
        when(LayoutInflater.from(mockContext)).thenReturn(mockInflater);
        when(mockInflater.inflate(eq(R.layout.ko__list_conversation), eq(mockParent), eq(false))).thenReturn(mockView);

        //Act
        RecyclerView.ViewHolder viewHolder = conversationListAdapter
                .onCreateViewHolder(mockParent, ConversationListType.CONVERSATION_LIST_ITEM);

        //Assert
        collector.checkThat(viewHolder, is(instanceOf(ConversationItemViewHolder.class)));
        collector.checkThat(viewHolder.itemView, is(equalTo(mockView)));

    }

    @Test
    public void onCreateViewHolderWhenViewTypeIsLoadingItem() {
        //Arrange
        when(mockParent.getContext()).thenReturn(mockContext);
        when(LayoutInflater.from(mockContext)).thenReturn(mockInflater);
        when(mockInflater.inflate(eq(R.layout.ko__list_item_loading), eq(mockParent), eq(false))).thenReturn(mockView);

        //Act
        RecyclerView.ViewHolder viewHolder = conversationListAdapter
                .onCreateViewHolder(mockParent, ListType.LOADING_ITEM);

        //Assert
        collector.checkThat(viewHolder, is(CoreMatchers.instanceOf(LoadingViewHolder.class)));
        collector.checkThat(viewHolder.itemView, is(equalTo(mockView)));

    }


    @Test
    public void getItemViewTypeTest() {
        //Arrange
        int position = 0;
        when(baseListItemList.get(0)).thenReturn(mock(BaseListItem.class));

        //Act
        conversationListAdapter.setData(baseListItemList);
        int res = conversationListAdapter.getItemViewType(position);

        //Assert
        assertThat(res, is(equalTo(conversationListAdapter.getData().get(position).getItemType())));
    }
}

