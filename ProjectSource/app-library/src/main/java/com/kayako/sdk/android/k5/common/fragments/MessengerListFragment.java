package com.kayako.sdk.android.k5.common.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.AttachmentMessageContinuedOtherListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.AttachmentMessageContinuedSelfListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.AttachmentMessageOtherListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.AttachmentMessageSelfListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.DateSeparatorListItem;
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
public class MessengerListFragment extends BaseListFragment implements MessengerAdapter.OnAvatarClickListener, MessengerAdapter.OnItemClickListener, MessengerAdapter.OnAttachmentClickListener {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String avatarUrl_other = "https://metalwihen4.kayako.com/avatar/get/0833f484-2dd2-5699-aef5-827ea49b77cc?1477595033";
        String avatarUrl_self = "https://metalwihen4.kayako.com/avatar/get/305307ec-e897-558f-9e5a-26d13e08352d?1477462700";

        List<BaseListItem> items = new ArrayList<>();
        items.add(new SimpleMessageOtherListItem("Hey there. You look lost. Can I help?", avatarUrl_other, 1477751012000L, null));
        items.add(new SimpleMessageSelfListItem("Yeah, I'm trying to buy a coffee machine. Don't know where to start...", avatarUrl_self, 1477751012000L, null));
        items.add(new SimpleMessageOtherListItem("We sell Solar Panels, not coffee machines", avatarUrl_other, 0, null));
        items.add(new SimpleMessageContinuedOtherListItem("Though I personally love Brewfictus coffee machines.", 0, null));
        items.add(new SimpleMessageContinuedOtherListItem("How about searching there?", 1477751013000L, null));
        items.add(new SimpleMessageSelfListItem("Eeep!", avatarUrl_self, 0, null));
        items.add(new SimpleMessageContinuedSelfListItem("Thanks", 1477751013000L, null));

        // Test Attachments by Other
        items.add(new SimpleMessageOtherListItem("Wait. Could you show me how your solar panels look?", avatarUrl_other, 0, null));
        items.add(new AttachmentMessageOtherListItem(avatarUrl_other, "http://cdn2.bigcommerce.com/n-d57o0b/tvhc2xod/product_images/uploaded_images/solar-panels.jpg?t=1416860323", "Solarpanels.png", 0, null));
        items.add(new SimpleMessageSelfListItem("Good, right?", avatarUrl_self, 1477763213000L, null));
        items.add(new AttachmentMessageContinuedOtherListItem("https://www.solarworld-usa.com/~/media/www/images/products/modules/off-grid.jpg?la=en", "Solarpanels.png", 0, null));
        items.add(new AttachmentMessageContinuedOtherListItem("https://www.solarworld-usa.com/~/media/www/images/products/modules/off-grid.jpg?la=en", "Solarpanels.png", 0, null));

        // Test Attachments by Self
        items.add(new SimpleMessageSelfListItem("Let me just send back everything you sent me.", avatarUrl_self, 0, null));
        items.add(new AttachmentMessageSelfListItem(avatarUrl_self, "http://cdn2.bigcommerce.com/n-d57o0b/tvhc2xod/product_images/uploaded_images/solar-panels.jpg?t=1416860323", "Solarpanels.png", 0, null));
        items.add(new SimpleMessageContinuedSelfListItem("Does that help? Here's another one", 0, null));

        items.add(new AttachmentMessageContinuedSelfListItem("https://www.solarworld-usa.com/~/media/www/images/products/modules/off-grid.jpg?la=en", "Solarpanels.png", 0, null));
        items.add(new AttachmentMessageContinuedSelfListItem("https://www.solarworld-usa.com/~/media/www/images/products/modules/off-grid.jpg?la=en", "Solarpanels.png", 0, null));

        // Test image without label
        items.add(new AttachmentMessageSelfListItem(avatarUrl_self, "http://cdn2.bigcommerce.com/n-d57o0b/tvhc2xod/product_images/uploaded_images/solar-panels.jpg?t=1416860323", null, 0, null));

        // Test time
        items.add(new DateSeparatorListItem(System.currentTimeMillis())); // now
        items.add(new SimpleMessageSelfListItem("Eeep!", avatarUrl_self, 0, null));
        items.add(new SimpleMessageSelfListItem("Eeep!", avatarUrl_self, 1477763213000L, null));
        items.add(new DateSeparatorListItem(1456842212000L)); // Tue, 01 Mar 2016 14:23:32 GMT
        items.add(new SimpleMessageSelfListItem("Eeep!", avatarUrl_self, 1477763213000L, null));
        items.add(new DateSeparatorListItem(1477751012000L)); // Sat, 29 Oct 2016 14:23:32 GMT
        items.add(new SimpleMessageSelfListItem("Eeep!", avatarUrl_self, 0, null));

        items.add(new SimpleMessageOtherListItem("!", avatarUrl_other, 1479763213000L, null));
        items.add(new SimpleMessageSelfListItem("?", avatarUrl_self, 1479766213000L, null));

        MessengerAdapter messengerAdapter = new MessengerAdapter(items);
        messengerAdapter.setOnItemClickListener(this);
        messengerAdapter.setOnAvatarClickListener(this);
        messengerAdapter.setOnAttachmentClickListener(this);
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

    @Override
    public void onClickAttachment(int messageType, Map<String, Object> messageData) {
        ViewUtils.showToastMessage(getContext(), "Attachment " + messageType, Toast.LENGTH_SHORT);
    }
}
