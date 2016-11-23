package com.kayako.sdk.android.k5.common.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollAdapter;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.AttachmentMessageContinuedOtherListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.AttachmentMessageContinuedSelfListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.AttachmentMessageOtherListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.AttachmentMessageSelfListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.ChannelDecoration;
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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Test fragment - should be deleted or kept in a test folder
 *
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class MessengerListFragment extends BaseListFragment implements MessengerAdapter.OnAvatarClickListener, MessengerAdapter.OnItemClickListener, MessengerAdapter.OnAttachmentClickListener {

    String avatarUrl_other = "https://metalwihen4.kayako.com/avatar/get/0833f484-2dd2-5699-aef5-827ea49b77cc?1477595033";
    String avatarUrl_self = "https://metalwihen4.kayako.com/avatar/get/305307ec-e897-558f-9e5a-26d13e08352d?1477462700";

    ChannelDecoration channelColor = new ChannelDecoration(R.color.colorAccent);
    ChannelDecoration channelFacebook = new ChannelDecoration(R.drawable.ko__img_facebook);
    ChannelDecoration channelTwitter = new ChannelDecoration(R.drawable.ko__img_twitter);
    ChannelDecoration channelMail = new ChannelDecoration(R.drawable.ko__img_mail);
    ChannelDecoration channelMessenger = new ChannelDecoration(R.drawable.ko__img_messenger);
    ChannelDecoration channelNote = new ChannelDecoration(R.drawable.ko__img_note);
    ChannelDecoration channelHelpCenter = new ChannelDecoration(R.drawable.ko__img_helpcenter);

    private AtomicInteger maxLoadMoreAttempts = new AtomicInteger(3);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /**
         * X Init List
         * X ADD NEW ITEMS TO END OF LIST (LOAD-MORE)
         *
         * X ADD NEW ITEM AT POS
         * X REMOVE ITEM FROM POS
         * X REPLACE ITEM AT POS
         *
         * X RE-SET WHOLE LIST
         * X ADD SCROLL LISTENER
         * X SMOOTH SCROLL TO POSITION
         */

        /**
         * NEW LIST ITEM TYPES
         * - SENDING MESSAGES - Ongoing?
         */

        // TODO: Figure out how to relate ids with position?
        // TODO: Reverse list - add NEW items to bottom, load OLD items at top
        // TODO: TEST add new item at position
        // TODO: TEST remove new item at position
        // TODO: TEST replace new item at position

        /**
         * Things to track
         *
         * - Adding items to bottom of list does NOT affect the Load More Item
         * - How to separate the Typing view (at bottom) from other at end items
         *
         */

        ChannelDecoration channelDefault = null;

        List<BaseListItem> items = new ArrayList<>();
        items.add(new SimpleMessageOtherListItem("Hey there. You look lost. Can I help?", avatarUrl_other, channelDefault, 1477751012000L, null));
        items.add(new SimpleMessageSelfListItem("Yeah, I'm trying to buy a coffee machine. Don't know where to start...", avatarUrl_self, channelDefault, 1477751012000L, null));
        items.add(new SimpleMessageOtherListItem("We sell Solar Panels, not coffee machines", avatarUrl_other, channelDefault, 0, null));
        items.add(new SimpleMessageContinuedOtherListItem("Though I personally love Brewfictus coffee machines.", 0, null));
        items.add(new SimpleMessageContinuedOtherListItem("How about searching there?", 1477751013000L, null));
        items.add(new SimpleMessageSelfListItem("Eeep!", avatarUrl_self, channelDefault, 0, null));
        items.add(new SimpleMessageContinuedSelfListItem("Thanks", 1477751013000L, null));

        // Test Attachments by Other
        items.add(new SimpleMessageOtherListItem("Wait. Could you show me how your solar panels look?", avatarUrl_other, channelDefault, 0, null));
        items.add(new AttachmentMessageOtherListItem("http://cdn2.bigcommerce.com/n-d57o0b/tvhc2xod/product_images/uploaded_images/solar-panels.jpg?t=1416860323", "Solarpanels.png", avatarUrl_other, channelDefault, 0, null));
        items.add(new SimpleMessageSelfListItem("Good, right?", avatarUrl_self, channelDefault, 1477763213000L, null));
        items.add(new AttachmentMessageContinuedOtherListItem("https://www.solarworld-usa.com/~/media/www/images/products/modules/off-grid.jpg?la=en", "Solarpanels.png", 0, null));
        items.add(new AttachmentMessageContinuedOtherListItem("https://www.solarworld-usa.com/~/media/www/images/products/modules/off-grid.jpg?la=en", "Solarpanels.png", 0, null));

        // Test Attachments by Self
        items.add(new SimpleMessageSelfListItem("Let me just send back everything you sent me.", avatarUrl_self, channelDefault, 0, null));
        items.add(new AttachmentMessageSelfListItem(avatarUrl_self, channelDefault, "http://cdn2.bigcommerce.com/n-d57o0b/tvhc2xod/product_images/uploaded_images/solar-panels.jpg?t=1416860323", "Solarpanels.png", 0, null));
        items.add(new SimpleMessageContinuedSelfListItem("Does that help? Here's another one", 0, null));

        items.add(new AttachmentMessageContinuedSelfListItem("https://www.solarworld-usa.com/~/media/www/images/products/modules/off-grid.jpg?la=en", "Solarpanels.png", 0, null));
        items.add(new AttachmentMessageContinuedSelfListItem("https://www.solarworld-usa.com/~/media/www/images/products/modules/off-grid.jpg?la=en", "Solarpanels.png", 0, null));

        // Test image without label
        items.add(new AttachmentMessageSelfListItem(avatarUrl_self, channelDefault, "http://cdn2.bigcommerce.com/n-d57o0b/tvhc2xod/product_images/uploaded_images/solar-panels.jpg?t=1416860323", null, 0, null));

        // Test time
        items.add(new DateSeparatorListItem(System.currentTimeMillis())); // now
        items.add(new SimpleMessageSelfListItem("Eeep!", avatarUrl_self, channelDefault, 0, null));
        items.add(new SimpleMessageSelfListItem("Eeep!", avatarUrl_self, channelDefault, 1477763213000L, null));
        items.add(new DateSeparatorListItem(1456842212000L)); // Tue, 01 Mar 2016 14:23:32 GMT
        items.add(new SimpleMessageSelfListItem("Eeep!", avatarUrl_self, channelDefault, 1477763213000L, null));
        items.add(new DateSeparatorListItem(1477751012000L)); // Sat, 29 Oct 2016 14:23:32 GMT
        items.add(new SimpleMessageSelfListItem("Eeep!", avatarUrl_self, channelDefault, 0, null));

        // Test min-wdith of Chat Bubble
        items.add(new SimpleMessageOtherListItem("!", avatarUrl_other, channelDefault, 1479763213000L, null));
        items.add(new SimpleMessageSelfListItem("?", avatarUrl_self, channelDefault, 1479766213000L, null));

        // Test all channels
        items.add(new SimpleMessageOtherListItem("Facebook!", avatarUrl_other, channelFacebook, 1479763213000L, null));
        items.add(new SimpleMessageOtherListItem("Twitter!", avatarUrl_other, channelTwitter, 1479763213000L, null));
        items.add(new SimpleMessageOtherListItem("Mail!", avatarUrl_other, channelMail, 1479763213000L, null));
        items.add(new SimpleMessageOtherListItem("HelpCenter!", avatarUrl_other, channelHelpCenter, 1479763213000L, null));
        items.add(new SimpleMessageOtherListItem("Note!", avatarUrl_other, channelNote, 1479763213000L, null));
        items.add(new SimpleMessageOtherListItem("Messenger!", avatarUrl_other, channelMessenger, 1479763213000L, null));

        MessengerAdapter messengerAdapter = new MessengerAdapter(items);
        messengerAdapter.setOnItemClickListener(this);
        messengerAdapter.setOnAvatarClickListener(this);
        messengerAdapter.setOnAttachmentClickListener(this);
        initList(messengerAdapter);

//        // SCROLL TO BOTTOM
//        scrollToEndOfList();

        // SCROLL LISTENERS
        final RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState != recyclerView.SCROLL_STATE_IDLE) {
                    Toast.makeText(getContext(), "GO GO GO", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        };

        // ADD SCROLL LISTENER - Any scroll should pop up a "GO GO" message
        setScrollListener(scrollListener);

        // REMOVE SCROLL LISTENER - After 2 seconds, "GO GO" should stop showing as you scroll up and down
        runTestTask(new TestCallback() {
            @Override
            public void performAfterWait() {
                removeScrollListener(scrollListener);
            }
        });

        // LOAD MORE ITEMS
        setLoadMoreListener(new EndlessRecyclerViewScrollAdapter.OnLoadMoreListener() {
            @Override
            public void loadMoreItems() {
                showLoadMoreProgress();

                testTaskcounter.set(0);
                runTestTask(new TestCallback() {
                    @Override
                    synchronized public void performAfterWait() {

                        if (maxLoadMoreAttempts.getAndDecrement() <= 0) {
                            setHasMoreItems(false);
                            removeLoadMoreListener();
                            hideLoadMoreProgress();
                            return;
                        }

                        // ADD NEW ITEMS AT END
                        List<BaseListItem> items = new ArrayList();
                        String suffix = String.format("[%s]", maxLoadMoreAttempts);
                        items.add(new SimpleMessageSelfListItem("More!" + suffix, avatarUrl_self, channelFacebook, 0, null));
                        items.add(new SimpleMessageContinuedSelfListItem("More 2!" + suffix, 0, null));
                        items.add(new SimpleMessageContinuedSelfListItem("More 3!" + suffix, 0, null));
                        items.add(new SimpleMessageContinuedSelfListItem("More 4!" + suffix, 0, null));
                        items.add(new SimpleMessageContinuedSelfListItem("More 5!" + suffix, 1479763213000L, null));
                        addItemsToEndOfList(items); // TODO: Remove hideLoadMoreProgress call in addItemsToEndOfList?

                        hideLoadMoreProgress();
                        scrollToEndOfList();
                    }
                });
            }
        });


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

    private AtomicInteger testTaskcounter = new AtomicInteger(0);

    private void runTestTask(final TestCallback testCallback) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(testTaskcounter.addAndGet(2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            synchronized protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                testCallback.performAfterWait();
            }
        }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    interface TestCallback {
        void performAfterWait();
    }

}
