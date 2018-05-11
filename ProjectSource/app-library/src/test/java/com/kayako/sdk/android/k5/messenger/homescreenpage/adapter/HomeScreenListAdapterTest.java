package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.utils.ImageUtils;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.core.MessengerStylePref;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.ConversationStarterHelper;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.header.HeaderListItem;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.header.HeaderViewHolder;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.presence.PresenceWidgetListItem;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.presence.PresenceWidgetViewHolder;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.recentcases.RecentConversationsWidgetListItem;
import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.recentcases.RecentConversationsWidgetViewHolder;
import com.kayako.sdk.android.k5.messenger.style.MessengerTemplateHelper;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.suppress;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.support.membermodification.MemberMatcher.methods;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import java.util.ArrayList;
import java.util.List;

@PrepareForTest({
        RecyclerView.Adapter.class,
        LayoutInflater.class,
        RecyclerView.ViewHolder.class,
        MessengerStylePref.class,
        Kayako.class,
        ImageUtils.class
})
@RunWith(PowerMockRunner.class)
public class HomeScreenListAdapterTest {

    private HomeScreenListAdapter homeScreenListAdapter;

    @Mock
    private BaseListItem baseListItem;

    @Mock
    private ViewGroup viewGroup;

    @Mock
    private Context context;

    @Mock
    private LayoutInflater layoutInflater;

    @Mock
    private View view;

    @Mock
    private RecyclerView recyclerView;

    @Mock
    private HeaderViewHolder viewHolder;

    @Mock
    private TextView textView;

    @Mock
    private ImageView imageView;

    @Mock
    private MessengerStylePref messengerStylePref;

    @Mock
    private Resources resources;

    @Mock
    private PresenceWidgetViewHolder presenceWidgetViewHolder;

    @Mock
    private RecentConversationsWidgetListItem recentConversationsWidgetListItem;

    @Mock
    private RecentConversationsWidgetViewHolder recentConversationsWidgetViewHolder;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        final List<BaseListItem> items = null;
        homeScreenListAdapter = new HomeScreenListAdapter(items);
        suppress(methods(RecyclerView.Adapter.class, "notifyDataSetChanged"));
        suppress(methods(ImageUtils.class, "setAvatarImage"));
        mockStatic(LayoutInflater.class);
        when(viewGroup.getContext()).thenReturn(context);
        when(LayoutInflater.from(context)).thenReturn(layoutInflater);
        when(view.getContext()).thenReturn(context);
        when(view.findViewById(R.id.ko__messenger_home_screen_widget_container)).thenReturn(viewGroup);
        when(view.findViewById(R.id.ko__recent_conversation_list)).thenReturn(recyclerView);
        mockStatic(MessengerStylePref.class);
        mockStatic(Kayako.class);
        when(MessengerStylePref.getInstance()).thenReturn(messengerStylePref);
        when(Kayako.getApplicationContext()).thenReturn(context);
        when(context.getResources()).thenReturn(resources);
    }

    @Test
    public void replaceData() {
        //Arrange
        final List<BaseListItem> data = new ArrayList<>();
        data.add(baseListItem);
        when(baseListItem.getItemType()).thenReturn(1);
        homeScreenListAdapter.replaceData(data);

        //Act
        final int count = homeScreenListAdapter.getItemCount();
        final int itemType = homeScreenListAdapter.getItemViewType(0);
        final List<BaseListItem> newData = Whitebox.getInternalState(homeScreenListAdapter, "mItems");

        //Assert
        errorCollector.checkThat(newData, is(data));
        errorCollector.checkThat(count, is(1));
        errorCollector.checkThat(itemType, is(1));
    }

    @Test
    public void onCreateViewHolderWhenViewTypeHEADER() {
        //Arrange
        final int viewType = HomeScreenListType.HEADER;
        when(layoutInflater.inflate(R.layout.ko__list_messenger_home_screen_header,
                viewGroup, false)).thenReturn(view);

        //Act
        final RecyclerView.ViewHolder viewHolder = homeScreenListAdapter.onCreateViewHolder(viewGroup, viewType);

        //Assert
        errorCollector.checkThat(viewHolder, notNullValue());
    }

    @Test
    public void onCreateViewHolderWhenViewTypeFOOTER() {
        //Arrange
        final int viewType = HomeScreenListType.FOOTER;
        when(layoutInflater.inflate(R.layout.ko__list_messenger_home_screen_footer,
                viewGroup, false)).thenReturn(view);

        //Act
        final RecyclerView.ViewHolder viewHolder = homeScreenListAdapter.onCreateViewHolder(viewGroup, viewType);
        //Assert
        errorCollector.checkThat(viewHolder, notNullValue());
    }

    @Test
    public void onCreateViewHolderWhenViewTypeWIDGETPRESENCE() {
        //Arrange
        final int viewType = HomeScreenListType.WIDGET_PRESENCE;
        when(layoutInflater.inflate(R.layout.ko__list_messenger_home_screen_widget,
                viewGroup, false)).thenReturn(view);

        //Act
        final RecyclerView.ViewHolder viewHolder = homeScreenListAdapter.onCreateViewHolder(viewGroup, viewType);

        //Assert
        errorCollector.checkThat(viewHolder, notNullValue());
    }

    @Test
    public void onCreateViewHolderWhenViewTypeWIDGETRECENTCONVERSATIONS() {
        //Arrange
        final int viewType = HomeScreenListType.WIDGET_RECENT_CONVERSATIONS;
        when(layoutInflater.inflate(R.layout.ko__list_messenger_home_screen_widget,
                viewGroup, false)).thenReturn(view);

        //Act
        final RecyclerView.ViewHolder viewHolder = homeScreenListAdapter.onCreateViewHolder(viewGroup, viewType);

        //Assert
        errorCollector.checkThat(viewHolder, notNullValue());
    }

    @Test
    public void onBindViewHolderWhenViewTypeHEADER() {
        //Arrange
        final int position = 0;
        Whitebox.setInternalState(viewHolder, "title", textView);
        Whitebox.setInternalState(viewHolder, "subtitle", textView);
        when(viewHolder.getItemViewType()).thenReturn(HomeScreenListType.HEADER);
        final List<BaseListItem> newData = new ArrayList<>();
        final HeaderListItem headerListItem = new HeaderListItem("title", "subtitle");
        newData.add(headerListItem);
        homeScreenListAdapter.replaceData(newData);

        //Act
        homeScreenListAdapter.onBindViewHolder(viewHolder, position);

        //Assert
        verifyStatic(MessengerTemplateHelper.class, times(2));
        MessengerTemplateHelper.applyTextColor(textView);
    }

    @Test
    public void onBindViewHolderWhenViewTypeWIDGETPRESENCE() {
        //Arrange
        final int position = 0;
        when(presenceWidgetViewHolder.getItemViewType()).thenReturn(HomeScreenListType.WIDGET_PRESENCE);
        Whitebox.setInternalState(presenceWidgetViewHolder, "title", textView);
        Whitebox.setInternalState(presenceWidgetViewHolder, "presenceCaptionTextView", textView);
        Whitebox.setInternalState(presenceWidgetViewHolder, "avatar1", imageView);
        Whitebox.setInternalState(presenceWidgetViewHolder, "avatar2", imageView);
        Whitebox.setInternalState(presenceWidgetViewHolder, "avatar3", imageView);
        Whitebox.setInternalState(presenceWidgetViewHolder, "actionButton", textView);
        final List<BaseListItem> newData = new ArrayList<>();
        final PresenceWidgetListItem presenceWidgetListItem = new PresenceWidgetListItem(
                "title", "caption", "url1", "url2", "url3");
        Whitebox.setInternalState(presenceWidgetListItem, "hasActionButton", true);
        newData.add(presenceWidgetListItem);
        homeScreenListAdapter.replaceData(newData);

        //Act
        homeScreenListAdapter.onBindViewHolder(presenceWidgetViewHolder, position);

        //Assert
        verifyStatic(MessengerTemplateHelper.class);
        ConversationStarterHelper.setAgentAvatar(imageView, "url1");
    }

    @Test
    public void onBindViewHolderWhenViewTypeWIDGETRECENTCONVERSATIONS() {
        //Arrange
        final int position = 0;
        when(recentConversationsWidgetViewHolder.getItemViewType()).thenReturn(HomeScreenListType.WIDGET_RECENT_CONVERSATIONS);
        Whitebox.setInternalState(recentConversationsWidgetViewHolder, "title", textView);
        Whitebox.setInternalState(recentConversationsWidgetViewHolder, "actionButton", textView);
        Whitebox.setInternalState(recentConversationsWidgetViewHolder, "recyclerView", recyclerView);
        final List<BaseListItem> newData = new ArrayList<>();
        newData.add(recentConversationsWidgetListItem);
        homeScreenListAdapter.replaceData(newData);

        //Act
        homeScreenListAdapter.onBindViewHolder(recentConversationsWidgetViewHolder, position);

        //Assert
        verify(recentConversationsWidgetListItem).getConversations();
    }
}
