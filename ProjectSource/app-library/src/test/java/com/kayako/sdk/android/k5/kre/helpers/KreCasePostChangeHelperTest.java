package com.kayako.sdk.android.k5.kre.helpers;

import com.kayako.sdk.android.k5.kre.data.ChangePost;

import org.junit.Assert;
import org.junit.Test;

public class KreCasePostChangeHelperTest {

    private String changePostEventPayload = "{\n" +
            "\t\"resource_url\": \"https://kayako-mobile-testing.kayako.com/api/v1/cases/posts/2191\",\n" +
            "\t\"resource_type\": \"post\",\n" +
            "\t\"resource_id\": 2191,\n" +
            "\t\"customer_resource_url\": \"https://kayako-mobile-testing.kayako.com/api/v1/conversations/258/message/2191\",\n" +
            "\t\"changed_properties\": [],\n" +
            "\t\"agent_resource_url\": \"https://kayako-mobile-testing.kayako.com/api/v1/cases/posts/2191\"\n" +
            "}";

    private String changePostEventPayload2 = "{\n" +
            "\t\"resource_url\": \"https://kayako-mobile-testing.kayako.com/api/v1/cases/posts/2198\",\n" +
            "\t\"resource_type\": \"post\",\n" +
            "\t\"resource_id\": 2198,\n" +
            "\t\"customer_resource_url\": \"https://kayako-mobile-testing.kayako.com/api/v1/conversations/258/message/2198\",\n" +
            "\t\"changed_properties\": [],\n" +
            "\t\"agent_resource_url\": \"https://kayako-mobile-testing.kayako.com/api/v1/cases/posts/2198\"\n" +
            "}";


    private String newPostEventPayload = "{\n" +
            "\t\"resource_url\": \"https://kayako-mobile-testing.kayako.com/api/v1/cases/posts/2199\",\n" +
            "\t\"resource_type\": \"post\",\n" +
            "\t\"resource_id\": 2199,\n" +
            "\t\"customer_resource_url\": \"https://kayako-mobile-testing.kayako.com/api/v1/conversations/258/message/2199\",\n" +
            "\t\"changed_properties\": [],\n" +
            "\t\"agent_resource_url\": \"https://kayako-mobile-testing.kayako.com/api/v1/cases/posts/2199\"\n" +
            "}";

    @Test
    public void extractChangePostPayload() throws Exception {
        ChangePost changePost = PushDataHelper.convertFromJsonString(ChangePost.class, changePostEventPayload);
        Assert.assertEquals("post", changePost.resource_type);
        Assert.assertEquals(2191L, changePost.resource_id.longValue());
        Assert.assertEquals("https://kayako-mobile-testing.kayako.com/api/v1/cases/posts/2191", changePost.resource_url);
        Assert.assertEquals("https://kayako-mobile-testing.kayako.com/api/v1/conversations/258/message/2191", changePost.customer_resource_url);
        Assert.assertEquals("https://kayako-mobile-testing.kayako.com/api/v1/cases/posts/2191", changePost.agent_resource_url);
    }

    @Test
    public void test2() throws Exception {
        ChangePost changePost = PushDataHelper.convertFromJsonString(ChangePost.class, changePostEventPayload2);
        Assert.assertEquals("post", changePost.resource_type);
        Assert.assertEquals(2198L, changePost.resource_id.longValue());

        ChangePost newPost = PushDataHelper.convertFromJsonString(ChangePost.class, newPostEventPayload);
        Assert.assertEquals("post", newPost.resource_type);
        Assert.assertEquals(2199L, newPost.resource_id.longValue());
    }

}