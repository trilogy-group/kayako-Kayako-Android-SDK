package com.kayako.sdk.android.k5.common.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
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
import com.kayako.sdk.android.k5.common.adapter.messengerlist.DataItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.DateSeparatorListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerAdapter;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.SimpleMessageContinuedOtherListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.SimpleMessageContinuedSelfListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.SimpleMessageOtherListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.SimpleMessageSelfListItem;
import com.kayako.sdk.android.k5.common.utils.ViewUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Test fragment - should be deleted or kept in a test folder
 *
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class MessengerListFragment extends BaseListFragment {

    private MessengerAdapter mMessengerAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        testSample();
    }

    protected void initMessengerList(List<BaseListItem> items) {
        // Reverse item order
        Collections.reverse(items);

        // Create Adapter
        mMessengerAdapter = new MessengerAdapter(items);

        // Create LayoutManager and reverse the layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(mRoot.getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        layoutManager.scrollToPosition(0);

        // Set up RecyclerView
        init(mMessengerAdapter, layoutManager);

        // Scroll to the very bottom
        scrollToEndOfList();
    }

    protected void setOnItemClickListener(MessengerAdapter.OnItemClickListener listener) {
        mMessengerAdapter.setOnItemClickListener(listener);
    }

    protected void setOnAvatarClickListener(MessengerAdapter.OnAvatarClickListener listener) {
        mMessengerAdapter.setOnAvatarClickListener(listener);
    }

    protected void setOnAttachmentClickListener(MessengerAdapter.OnAttachmentClickListener listener) {
        mMessengerAdapter.setOnAttachmentClickListener(listener);
    }

    @Override
    protected void addItemsToEndOfList(List<BaseListItem> items) {
        // Reverse item order
        Collections.reverse(items);

        // Add to the beginning of the reversed layout (end)
        super.addItemsToBeginningOfList(items);
    }

    @Override
    protected void addItemsToBeginningOfList(List<BaseListItem> items) {
        // Reverse item order
        Collections.reverse(items);

        // Add to the end of the reversed layout (beginning)
        super.addItemsToEndOfList(items);
    }

    @Override
    protected void scrollToEndOfList() {
        super.scrollToBeginningOfList();
    }

    @Override
    protected void scrollToBeginningOfList() {
        super.scrollToBeginningOfList();
    }

    @Override
    protected int findFirstVisibleItemPosition() {
        return super.findLastVisibleItemPosition();
    }

    @Override
    protected int findLastVisibleItemPosition() {
        return super.findFirstVisibleItemPosition();
    }

    /**
     * scroll to bottom ONLY if the user is currently viewing the last few posts)
     */
    protected void scrollToNewMessagesIfNearby() {
        int lastPosition = getSizeOfData() - 1;
        int lastVisiblePosition = findFirstVisibleItemPosition();

        if (lastPosition - lastVisiblePosition < 3) { // difference is less than 3 items
            scrollToEndOfList();
        }
    }


    protected void convertDataItemToListItems(List<DataItem> dataItems) {
        // TODO: Assert that the list is ordered by date - NEWEST, NEW, OLD, OLDEST

        for (int i = 0; i < dataItems.size() - 1; i++) {
            DataItem dataItem = dataItems.get(i);
            DataItem nextDataItem = dataItems.get(i + 1);

            // TODO: Check the date of current dataItem and the nextDataItem. if the next DataItem is on the NEW DAY, add DATE_SEPARATOR between two

            // TODO: Check if read status of current dataItem is true and the nextDataItem is false. If true, add UNREAD_MESSAGES_SEPARATOR between two

            // TODO: Check if UserType - SELF/OTHER

            // TODO: Check if the time of the previous dataItem and the current dataItem is less than XX seconds (or 1 min).
            // TODO:       >> If yes, check if next dataItem is less than XX seconds.
            // TODO:                if yes, ContinuedGroupType of current is CONT
            // TODO:                if no, ContinuedGroupType of current is LAST
            // TODO:       >> If no,
            // TODO:                1> ContinuedGroupType of current == FIRST (with Avatar)

            // TODO: Check if avatar is being shown.
            // TODO:       >> if yes, check if channel is available
            // TODO:                >> if yes, show channel too
            // TODO:                >> if no, continue
            // TODO:       >> if no, continue
        }
    }

    ////////////////////////////////////////////// TEST IMPLEMENTATION //////////////////////////////////////////////

    private AtomicInteger testTaskCounter = new AtomicInteger(0);
    private AtomicInteger testMaxLoadMoreAttempts = new AtomicInteger(3);

    private void testSample() {
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
         *
         * X Reverse list - ScrollToBottom, ScrollToTop, AddItemsToBottom, AddItemsToTop, LoadMoreItems - Stop load more at the top
         */

        /**
         * NEW LIST ITEM TYPES
         * - SENDING MESSAGES - Ongoing?
         */

        // TODO: Figure out how to relate ids with position? - Map position with postId! :D
        // - Use a Map in the convertor class. When converting Posts -> ListTypes (map post id (Long) with position)??
        // -- But positions can change as items get deleted, making the map unreliable later
        // -- But there may be multiple positions for the same post (If it has attachments, for example)
        // - Ok, generate a function that finds all positions from a id. Should we add an ID variable,
        // -- or continue using Map - flexible but we need an abstract method that the user needs to override to extract an id from

        // TODO: TEST add new item at position
        // TODO: TEST remove new item at position
        // TODO: TEST replace new item at position

        /**
         * Things to track
         *
         * - Adding items to bottom of list does NOT affect the Load More Item
         * -- How to separate the Typing view (at bottom) from other at end items
         *
         * - Show/Hide Header info
         * --
         */

        final String test_avatarUrl_other = "https://metalwihen4.kayako.com/avatar/get/0833f484-2dd2-5699-aef5-827ea49b77cc?1477595033";
        final String test_avatarUrl_self = "https://metalwihen4.kayako.com/avatar/get/305307ec-e897-558f-9e5a-26d13e08352d?1477462700";
        final ChannelDecoration test_channelColor = new ChannelDecoration(R.color.colorAccent);
        final ChannelDecoration test_channelFacebook = new ChannelDecoration(R.drawable.ko__img_facebook);
        final ChannelDecoration test_channelTwitter = new ChannelDecoration(R.drawable.ko__img_twitter);
        final ChannelDecoration test_channelMail = new ChannelDecoration(R.drawable.ko__img_mail);
        final ChannelDecoration test_channelMessenger = new ChannelDecoration(R.drawable.ko__img_messenger);
        final ChannelDecoration test_channelNote = new ChannelDecoration(R.drawable.ko__img_note);
        final ChannelDecoration test_channelHelpCenter = new ChannelDecoration(R.drawable.ko__img_helpcenter);
        final ChannelDecoration test_channelDefault = null;

        List<BaseListItem> items = new ArrayList<>();
        items.add(new SimpleMessageOtherListItem("Hey there. You look lost. Can I help?", test_avatarUrl_other, test_channelDefault, 1477751012000L, null));
        items.add(new SimpleMessageSelfListItem("Yeah, I'm trying to buy a coffee machine. Don't know where to start...", test_avatarUrl_self, test_channelDefault, 1477751012000L, null));
        items.add(new SimpleMessageOtherListItem("We sell Solar Panels, not coffee machines", test_avatarUrl_other, test_channelDefault, 0, null));
        items.add(new SimpleMessageContinuedOtherListItem("Though I personally love Brewfictus coffee machines.", 0, null));
        items.add(new SimpleMessageContinuedOtherListItem("How about searching there?", 1477751013000L, null));
        items.add(new SimpleMessageSelfListItem("Eeep!", test_avatarUrl_self, test_channelDefault, 0, null));
        items.add(new SimpleMessageContinuedSelfListItem("Thanks", 1477751013000L, null));

        // Test Attachments by Other
        items.add(new SimpleMessageOtherListItem("Wait. Could you show me how your solar panels look?", test_avatarUrl_other, test_channelDefault, 0, null));
        items.add(new AttachmentMessageOtherListItem("http://cdn2.bigcommerce.com/n-d57o0b/tvhc2xod/product_images/uploaded_images/solar-panels.jpg?t=1416860323", "Solarpanels.png", test_avatarUrl_other, test_channelDefault, 0, null));
        items.add(new SimpleMessageSelfListItem("Good, right?", test_avatarUrl_self, test_channelDefault, 1477763213000L, null));
        items.add(new AttachmentMessageContinuedOtherListItem("https://www.solarworld-usa.com/~/media/www/images/products/modules/off-grid.jpg?la=en", "Solarpanels.png", 0, null));
        items.add(new AttachmentMessageContinuedOtherListItem("https://www.solarworld-usa.com/~/media/www/images/products/modules/off-grid.jpg?la=en", "Solarpanels.png", 0, null));

        // Test Attachments by Self
        items.add(new SimpleMessageSelfListItem("Let me just send back everything you sent me.", test_avatarUrl_self, test_channelDefault, 0, null));
        items.add(new AttachmentMessageSelfListItem(test_avatarUrl_self, test_channelDefault, "http://cdn2.bigcommerce.com/n-d57o0b/tvhc2xod/product_images/uploaded_images/solar-panels.jpg?t=1416860323", "Solarpanels.png", 0, null));
        items.add(new SimpleMessageContinuedSelfListItem("Does that help? Here's another one", 0, null));

        items.add(new AttachmentMessageContinuedSelfListItem("https://www.solarworld-usa.com/~/media/www/images/products/modules/off-grid.jpg?la=en", "Solarpanels.png", 0, null));
        items.add(new AttachmentMessageContinuedSelfListItem("https://www.solarworld-usa.com/~/media/www/images/products/modules/off-grid.jpg?la=en", "Solarpanels.png", 0, null));

        // Test image without label
        items.add(new AttachmentMessageSelfListItem(test_avatarUrl_self, test_channelDefault, "http://cdn2.bigcommerce.com/n-d57o0b/tvhc2xod/product_images/uploaded_images/solar-panels.jpg?t=1416860323", null, 0, null));

        // Test time
        items.add(new DateSeparatorListItem(System.currentTimeMillis())); // now
        items.add(new SimpleMessageSelfListItem("Eeep!", test_avatarUrl_self, test_channelDefault, 0, null));
        items.add(new SimpleMessageSelfListItem("Eeep!", test_avatarUrl_self, test_channelDefault, 1477763213000L, null));
        items.add(new DateSeparatorListItem(1456842212000L)); // Tue, 01 Mar 2016 14:23:32 GMT
        items.add(new SimpleMessageSelfListItem("Eeep!", test_avatarUrl_self, test_channelDefault, 1477763213000L, null));
        items.add(new DateSeparatorListItem(1477751012000L)); // Sat, 29 Oct 2016 14:23:32 GMT
        items.add(new SimpleMessageSelfListItem("Eeep!", test_avatarUrl_self, test_channelDefault, 0, null));

        // Test min-wdith of Chat Bubble
        items.add(new SimpleMessageOtherListItem("!", test_avatarUrl_other, test_channelDefault, 1479763213000L, null));
        items.add(new SimpleMessageSelfListItem("?", test_avatarUrl_self, test_channelDefault, 1479766213000L, null));

        // Test all channels
        items.add(new SimpleMessageOtherListItem("Facebook!", test_avatarUrl_other, test_channelFacebook, 1479763213000L, null));
        items.add(new SimpleMessageOtherListItem("Twitter!", test_avatarUrl_other, test_channelTwitter, 1479763213000L, null));
        items.add(new SimpleMessageOtherListItem("Mail!", test_avatarUrl_other, test_channelMail, 1479763213000L, null));
        items.add(new SimpleMessageOtherListItem("HelpCenter!", test_avatarUrl_other, test_channelHelpCenter, 1479763213000L, null));
        items.add(new SimpleMessageOtherListItem("Note!", test_avatarUrl_other, test_channelNote, 1479763213000L, null));
        items.add(new SimpleMessageOtherListItem("Messenger!", test_avatarUrl_other, test_channelMessenger, 1479763213000L, null));

        initMessengerList(items);

        setOnItemClickListener(new MessengerAdapter.OnItemClickListener() {
            @Override
            public void onClickItem(int messageType, Map<String, Object> messageData) {
                ViewUtils.showToastMessage(getContext(), "Item " + messageType, Toast.LENGTH_SHORT);
            }
        });

        setOnAvatarClickListener(new MessengerAdapter.OnAvatarClickListener() {
            public void OnClickAvatar(int messageType, Map<String, Object> messageData) {
                ViewUtils.showToastMessage(getContext(), "Avatar: " + messageType, Toast.LENGTH_SHORT);
            }
        });

        setOnAttachmentClickListener(new MessengerAdapter.OnAttachmentClickListener() {
            @Override
            public void onClickAttachment(int messageType, Map<String, Object> messageData) {
                ViewUtils.showToastMessage(getContext(), "Attachment " + messageType, Toast.LENGTH_SHORT);
            }
        });


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

                testTaskCounter.set(0);
                runTestTask(new TestCallback() {
                    @Override
                    public void performAfterWait() {

                        if (testMaxLoadMoreAttempts.getAndDecrement() <= 0) {
                            setHasMoreItems(false);
                            removeLoadMoreListener();
                            hideLoadMoreProgress();
                            return;
                        }

                        // ADD NEW ITEMS AT END
                        List<BaseListItem> items = new ArrayList();
                        String suffix = String.format("[%s]", testMaxLoadMoreAttempts);
                        items.add(new SimpleMessageSelfListItem("More " + suffix, test_avatarUrl_self, test_channelFacebook, 0, null));
                        for (int i = 2; i <= 20; i++) {
                            items.add(new SimpleMessageContinuedSelfListItem("More " + suffix + " " + i, 0, null));
                        }
                        addItemsToBeginningOfList(items); // TODO: Remove hideLoadMoreProgress call in addItemsToEndOfList?

                        hideLoadMoreProgress();
                    }
                });
            }
        });

        runTestTask(new TestCallback() {
            @Override
            public void performAfterWait() {
                List<BaseListItem> items2 = new ArrayList();
                items2.add(new SimpleMessageOtherListItem("Add one more at the end!", test_avatarUrl_self, test_channelFacebook, 0, null));
                addItemsToEndOfList(items2);

                scrollToNewMessagesIfNearby();
            }
        });

        runTestTask(new TestCallback() {
            @Override
            public void performAfterWait() {
                List<BaseListItem> items2 = new ArrayList();
                items2.add(new SimpleMessageSelfListItem("Blah Blah!", test_avatarUrl_self, test_channelFacebook, 0, null));
                addItemsToEndOfList(items2);

                scrollToNewMessagesIfNearby();
            }
        });

        runTestTask(new TestCallback() {
            @Override
            public void performAfterWait() {
                List<BaseListItem> items2 = new ArrayList();
                items2.add(new SimpleMessageOtherListItem("That's a good one!", test_avatarUrl_self, test_channelFacebook, 0, null));
                addItemsToEndOfList(items2);

                scrollToNewMessagesIfNearby();
            }
        });

    }

    private void runTestTask(final TestCallback testCallback) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(testTaskCounter.addAndGet(2000));
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

    private interface TestCallback {
        void performAfterWait();
    }

}
