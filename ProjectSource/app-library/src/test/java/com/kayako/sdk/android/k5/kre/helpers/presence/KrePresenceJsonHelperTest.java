package com.kayako.sdk.android.k5.kre.helpers.presence;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class KrePresenceJsonHelperTest {

    @Test
    public void testPresenceDiff() throws Exception {
        String jsonPayload1 = "{\n" +
                "     \"leaves\": {\n" +
                "          \"10\": {\n" +
                "               \"metas\": [{\n" +
                "                    \"last_active_at\": 1487796680803,\n" +
                "                    \"is_viewing\": true,\n" +
                "                    \"is_foreground\": true,\n" +
                "                    \"user\": {\n" +
                "                         \"id\": 10,\n" +
                "                         \"full_name\": \"Neil Mathew\",\n" +
                "                         \"avatar\": \"https://kayako-mobile-testing.kayako.com/avatar/get/9e3180e4-6967-5f62-93df-185beae50f93?1487796405\"\n" +
                "                    },\n" +
                "                    \"phx_ref_prev\": \"c4/FZg0AzFo=\",\n" +
                "                    \"phx_ref\": \"lUXUbBdLe7E=\"\n" +
                "               }]\n" +
                "          }\n" +
                "     },\n" +
                "     \"joins\": {\n" +
                "          \"10\": {\n" +
                "               \"metas\": [{\n" +
                "                    \"last_active_at\": 1487796992127,\n" +
                "                    \"is_viewing\": true,\n" +
                "                    \"is_updating\": true,\n" +
                "                    \"is_foreground\": true,\n" +
                "                    \"user\": {\n" +
                "                         \"id\": 10,\n" +
                "                         \"full_name\": \"Neil Mathew\",\n" +
                "                         \"avatar\": \"https://kayako-mobile-testing.kayako.com/avatar/get/9e3180e4-6967-5f62-93df-185beae50f93?1487796405\"\n" +
                "                    },\n" +
                "                    \"phx_ref_prev\": \"lUXUbBdLe7E=\",\n" +
                "                    \"phx_ref\": \"8WL0+5E+QFI=\"\n" +
                "               }]\n" +
                "          }\n" +
                "     }\n" +
                "}\n";

        Set<PresenceUser> presenceUserSet = KrePresenceJsonHelper.parsePresenceDiffJsonAndGetJoins(jsonPayload1);

        PresenceUser expected = new PresenceUser(
                new PresenceMetaUserData(10L, "Neil Mathew", "https://kayako-mobile-testing.kayako.com/avatar/get/9e3180e4-6967-5f62-93df-185beae50f93?1487796405"),
                new PresenceMetaActivityData(false, true, 1487796992127L, true, true));

        PresenceUser actual = presenceUserSet.iterator().next();

        Assert.assertEquals(1, presenceUserSet.size());

        // This check only ensures that the user id is the same (equals overriden)
        Assert.assertEquals(expected, actual);

        Assert.assertNotEquals(expected.getActivityData(), actual.getActivityData()); // NOT EQUAL because Typing was not mentioned before (null), but now it's mentioned (false)
        Assert.assertEquals(expected.getUserData().getAvatar(), actual.getUserData().getAvatar());
        Assert.assertEquals(expected.getUserData().getFullName(), actual.getUserData().getFullName());
    }

    /**
     * Added to test because of changes made to KRE. Now, Active User info is gathered from the meta tag, not the id.
     *
     * @param id
     * @return
     */
    private String getUserMeta(long id) {
        return "\"user\": {\n" +
                "\"id\": " + id + ",\n" +
                "\"full_name\": \"Neil Mathew\",\n" +
                "\"avatar\": \"https://kayako-mobile-testing.kayako.com/avatar/get/9e3180e4-6967-5f62-93df-185beae50f93?1487796405\"\n" +
                "}";
    }

    @Test
    public void testPresenceState() throws Exception {
        String json = "{\n" +
                "  \"12\": {\n" +
                "    \"metas\": [{\n" +
                "      \"phx_ref\": \"s1Rz69LvStU=\",\n" +
                getUserMeta(12) +
                "    }]\n" +
                "  },\n" +
                "  \"10\": {\n" +
                "    \"metas\": [{\n" +
                "      \"phx_ref\": \"oJCFiF1ORLo=\",\n" +
                getUserMeta(10) +
                "    }]\n" +
                "  },\n" +
                "  \"1\": {\n" +
                "    \"metas\": [{\n" +
                "      \"phx_ref\": \"x4PY6XrBegk=\",\n" +
                getUserMeta(1) +
                "    }]\n" +
                "  }\n" +
                "}";

        Set<PresenceUser> set;

        // Should note all 3 users
        set = KrePresenceJsonHelper.parsePresenceStateJson(json);
        Assert.assertEquals(3, set.size());

        // Should not increase the number of users for same set of users
        set = KrePresenceJsonHelper.parsePresenceStateJson(json);
        Assert.assertEquals(3, set.size());
    }
}