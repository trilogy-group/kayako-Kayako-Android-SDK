package com.kayako.sdk.android.k5.common.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollAdapter;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollListener;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerAdapter;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.SimpleMessageContinuedOtherListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.SimpleMessageContinuedSelfListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.SimpleMessageOtherListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.SimpleMessageSelfListItem;
import com.kayako.sdk.android.k5.common.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class MessengerListFragment extends BaseListFragment implements MessengerAdapter.OnAvatarClickListenr, MessengerAdapter.OnItemClickListener {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String avatarUrl_other = "https://metalwihen4.kayako.com/avatar/get/0833f484-2dd2-5699-aef5-827ea49b77cc?1477595033";
        String avatarUrl_self = "https://metalwihen4.kayako.com/avatar/get/305307ec-e897-558f-9e5a-26d13e08352d?1477462700";

        List<BaseListItem> items = new ArrayList<>();
        items.add(new SimpleMessageOtherListItem("Hey there. You look lost. Can I help?", avatarUrl_other, null));
        items.add(new SimpleMessageSelfListItem("Yeah, I'm trying to buy a coffee machine. Don't know where to start...", avatarUrl_self, null));
        items.add(new SimpleMessageOtherListItem("We sell Solar Panels, not coffee machines", avatarUrl_other, null));
        items.add(new SimpleMessageContinuedOtherListItem("Though I personally love Brewfictus coffee machines.", null));
        items.add(new SimpleMessageContinuedOtherListItem("How about searching there?", null));
        items.add(new SimpleMessageSelfListItem("Eeep!", avatarUrl_self, null));
        items.add(new SimpleMessageContinuedSelfListItem("Thanks", null));

        MessengerAdapter messengerAdapter = new MessengerAdapter(items, this, this);
        initList(messengerAdapter, null);
    }

    @Override
    public void OnClickAvatar(int messageType, Map<String, Object> messageData) {
        ViewUtils.showToastMessage(getContext(), "Avatar: " + messageType, Toast.LENGTH_SHORT);
    }

    @Override
    public void onClickItem(int messageType, Map<String, Object> messageData) {
        ViewUtils.showToastMessage(getContext(), "Item " + messageType, Toast.LENGTH_SHORT);
    }
}
