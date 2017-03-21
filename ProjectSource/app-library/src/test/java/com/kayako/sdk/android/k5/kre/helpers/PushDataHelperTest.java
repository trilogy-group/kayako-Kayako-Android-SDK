package com.kayako.sdk.android.k5.kre.helpers;

import com.kayako.sdk.android.k5.kre.data.Change;
import com.kayako.sdk.android.k5.kre.data.ChangedProperties;

import org.junit.Assert;
import org.junit.Test;

public class PushDataHelperTest {


    @Test
    public void convertFromJsonString() throws Exception {

        String caseChangeJsonPayload = "{\n" +
                "\t\"resource_url\": \"https://swarnavasengupta.kayako.com/api/v1/cases/210\",\n" +
                "\t\"resource_type\": \"case\",\n" +
                "\t\"resource_id\": 210,\n" +
                "\t\"customer_resource_url\": \"https://swarnavasengupta.kayako.com/api/v1/conversations/210\",\n" +
                "\t\"changed_properties\": {\n" +
                "\t\t\"updated_at\": 1489264028,\n" +
                "\t\t\"post_count\": 3,\n" +
                "\t\t\"last_agent_activity_at\": 1489264028,\n" +
                "\t\t\"form_id\": \"1\",\n" +
                "\t\t\"form\": \"1\"\n" +
                "\t},\n" +
                "\t\"agent_resource_url\": \"https://swarnavasengupta.kayako.com/api/v1/cases/210\"\n" +
                "}";

        Change change = PushDataHelper.convertFromJsonString(Change.class, caseChangeJsonPayload);
        Assert.assertEquals(210, change.getResourceId().longValue());
        Assert.assertEquals("https://swarnavasengupta.kayako.com/api/v1/cases/210", change.getResourceUrl());
        Assert.assertEquals("case", change.getResourceType());

        ChangedProperties changedProperties = change.getChangedProperties();
        Assert.assertEquals(1489264028L, changedProperties.getUpdatedAt().longValue());
        Assert.assertEquals(3, changedProperties.getPostCount().intValue());
        Assert.assertEquals(1489264028L, changedProperties.getLastAgentActivityAt().longValue());

    }

}