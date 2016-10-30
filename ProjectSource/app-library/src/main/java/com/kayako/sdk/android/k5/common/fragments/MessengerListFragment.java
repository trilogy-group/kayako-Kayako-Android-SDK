package com.kayako.sdk.android.k5.common.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.AttachmentMessageContinuedOtherListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.AttachmentMessageContinuedSelfListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.AttachmentMessageOtherListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.AttachmentMessageSelfListItem;
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
        items.add(new SimpleMessageOtherListItem("Hey there. You look lost. Can I help?", avatarUrl_other, null));
        items.add(new SimpleMessageSelfListItem("Yeah, I'm trying to buy a coffee machine. Don't know where to start...", avatarUrl_self, null));
        items.add(new SimpleMessageOtherListItem("We sell Solar Panels, not coffee machines", avatarUrl_other, null));
        items.add(new SimpleMessageContinuedOtherListItem("Though I personally love Brewfictus coffee machines.", null));
        items.add(new SimpleMessageContinuedOtherListItem("How about searching there?", null));
        items.add(new SimpleMessageSelfListItem("Eeep!", avatarUrl_self, null));
        items.add(new SimpleMessageContinuedSelfListItem("Thanks", null));

        // Test Attachments by Other
        items.add(new SimpleMessageSelfListItem("Wait. Could you show me how your solar panels look?", avatarUrl_other, null));
        items.add(new AttachmentMessageOtherListItem("http://cdn2.bigcommerce.com/n-d57o0b/tvhc2xod/product_images/uploaded_images/solar-panels.jpg?t=1416860323", "Solarpanels.png", null));
        items.add(new SimpleMessageOtherListItem("Good, right?", avatarUrl_other, null));
        items.add(new AttachmentMessageContinuedOtherListItem("https://www.solarworld-usa.com/~/media/www/images/products/modules/off-grid.jpg?la=en", "Solarpanels.png", null));
        items.add(new AttachmentMessageContinuedOtherListItem("https://www.solarworld-usa.com/~/media/www/images/products/modules/off-grid.jpg?la=en", "Solarpanels.png", null));

        // Test Attachments by Self
        items.add(new SimpleMessageSelfListItem("Let me just send back everything you sent me.", avatarUrl_self, null));
        items.add(new AttachmentMessageSelfListItem("http://cdn2.bigcommerce.com/n-d57o0b/tvhc2xod/product_images/uploaded_images/solar-panels.jpg?t=1416860323", "Solarpanels.png", null));
        items.add(new SimpleMessageContinuedSelfListItem("Does that help? Here's another one", null));
        items.add(new AttachmentMessageContinuedSelfListItem("https://www.solarworld-usa.com/~/media/www/images/products/modules/off-grid.jpg?la=en", "Solarpanels.png", null));
        items.add(new AttachmentMessageContinuedSelfListItem("https://www.solarworld-usa.com/~/media/www/images/products/modules/off-grid.jpg?la=en", "Solarpanels.png", null));

        // Test image without label
        items.add(new AttachmentMessageSelfListItem("http://cdn2.bigcommerce.com/n-d57o0b/tvhc2xod/product_images/uploaded_images/solar-panels.jpg?t=1416860323", null, null));


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
