package com.kayako.sdk.android.k5.kre.helpers.presence;


import com.kayako.sdk.android.k5.kre.helpers.RawUserOnCasePresenceListener;
import com.kayako.sdk.android.k5.kre.helpers.RawUserSubscribedPresenceListener;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class KrePresenceHelperTest {

    // TODO: Test what happens when callOnPresenceState is called twice or thrice - should ensure nothing crashes
    // TODO: Test calling diff events for typing, updating, etc - start typing, then stop typing...
    // TODO: Test when the only online user is yourself - and if method is called with zero users
    // TODO: Test both subscribe/unsubscribe along side isViewingCase/isNoLongerViewingCase

    class TestKrePresenceHelper extends KrePresenceHelper {

        public TestKrePresenceHelper() {
            super(null, false, 0);
            mShowLogs = false;
        }

        @Override
        protected void startTracking() {
            // do nothing
        }

        @Override
        public void callOnPresenceDiffEvent(String jsonBody) {
            super.callOnPresenceDiffEvent(jsonBody);
        }

        @Override
        public synchronized void callOnPresenceStateEvent(String jsonBody) {
            super.callOnPresenceStateEvent(jsonBody);
        }
    }

    /**
     * When a user unsubscribes, he/she should be treated as NoLongerViewingCase the case too.
     */
    @Test
    public void testIfUserUnsubscribesThenUserShouldNoLongerBeViewingCase() {

        TestKrePresenceHelper krePresenceHelper = new TestKrePresenceHelper();
        Assert.assertEquals(0, krePresenceHelper.mOnlineUsers.size());

        RawUserOnCasePresenceListener mockedRawUserOnlinePresenceListener = Mockito.mock(RawUserOnCasePresenceListener.class);
        krePresenceHelper.setRawUserOnCasePresenceListener(mockedRawUserOnlinePresenceListener);

        RawUserSubscribedPresenceListener mockedRawUserSubscribedPresenceListener = Mockito.mock(RawUserSubscribedPresenceListener.class);
        krePresenceHelper.setRawUserSubscribedPresenceListener(mockedRawUserSubscribedPresenceListener);

        String firstPresenceDiffJson = "{\n" +
                "            \"leaves\":{\n" +
                "            \"12\":{\n" +
                "                \"metas\":[{\n" +
                "                    \"last_active_at\":1488362564423, \"is_viewing\":true, \"is_foreground\":true, \"user\":\n" +
                "                    {\n" +
                "                        \"id\":12, \"full_name\":\"Neil Mathew #3\", \"avatar\":\n" +
                "                        \"https://kayako-mobile-testing.kayako.com/avatar/get/6174fbe8-3589-5e9f-bdd7-dd4e2b267e8c?1488362296\"\n" +
                "                    },\"phx_ref_prev\":\"pX8C0i77Y+4=\", \"phx_ref\":\"jUrSzEAyeNQ=\"\n" +
                "                }]}\n" +
                "        },\"joins\":{\n" +
                "            \"12\":{\n" +
                "                \"metas\":[{\n" +
                "                    \"last_active_at\":1488362564423, \"is_viewing\":false, \"is_foreground\":\n" +
                "                    true, \"user\":{\n" +
                "                        \"id\":12, \"full_name\":\"Neil Mathew #3\", \"avatar\":\n" +
                "                        \"https://kayako-mobile-testing.kayako.com/avatar/get/6174fbe8-3589-5e9f-bdd7-dd4e2b267e8c?1488362296\"\n" +
                "                    },\"phx_ref_prev\":\"jUrSzEAyeNQ=\", \"phx_ref\":\"IszY0I7WXTE=\"\n" +
                "                }]}\n" +
                "        }\n" +
                "        }";

        String secondPresenceDiffJson = "{\n" +
                "            \"leaves\":{\n" +
                "            \"12\":{\n" +
                "                \"metas\":[{\n" +
                "                    \"last_active_at\":1488362564423, \"is_viewing\":false, \"is_foreground\":\n" +
                "                    true, \"user\":{\n" +
                "                        \"id\":12, \"full_name\":\"Neil Mathew #3\", \"avatar\":\n" +
                "                        \"https://kayako-mobile-testing.kayako.com/avatar/get/6174fbe8-3589-5e9f-bdd7-dd4e2b267e8c?1488362296\"\n" +
                "                    },\"phx_ref_prev\":\"jUrSzEAyeNQ=\", \"phx_ref\":\"IszY0I7WXTE=\"\n" +
                "                }]}\n" +
                "        },\"joins\":{\n" +
                "        }\n" +
                "        }";

        krePresenceHelper.callOnPresenceDiffEvent(firstPresenceDiffJson); // adds user 12 to the list of users
        List<Long> testList = new ArrayList<>();
        testList.add(12L);
        Mockito.verify(mockedRawUserOnlinePresenceListener, Mockito.times(1)).onUsersAlreadyViewingCase(Mockito.eq(testList), Mockito.anyLong());
        Mockito.verify(mockedRawUserSubscribedPresenceListener, Mockito.times(1)).onUsersAlreadySubscribed(Mockito.eq(testList), Mockito.anyLong());
        Assert.assertEquals(1, krePresenceHelper.mOnlineUsers.size());

        krePresenceHelper.callOnPresenceDiffEvent(secondPresenceDiffJson); // removes user 12 from list of online users
        Mockito.verify(mockedRawUserOnlinePresenceListener, Mockito.times(1)).onUserNoLongerViewingCase(Mockito.eq(12L));
        Mockito.verify(mockedRawUserSubscribedPresenceListener, Mockito.times(1)).onUserNoLongerSubscribed(Mockito.eq(12L));
        Assert.assertEquals(0, krePresenceHelper.mOnlineUsers.size());
    }
}