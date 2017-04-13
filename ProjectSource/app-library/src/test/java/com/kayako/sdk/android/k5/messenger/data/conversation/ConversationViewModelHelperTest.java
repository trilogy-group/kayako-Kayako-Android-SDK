package com.kayako.sdk.android.k5.messenger.data.conversation;

import com.kayako.sdk.ParserFactory;
import com.kayako.sdk.messenger.conversation.Conversation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConversationViewModelHelperTest {

    private ConversationViewModelHelper conversations;

    private Conversation conversation = ParserFactory.getConversationParser().parse(
            "{\n" +
                    "      \"id\": 233,\n" +
                    "      \"uuid\": \"32daac16-c3a3-54a6-8a88-b3702d774bc9\",\n" +
                    "      \"legacyId\": null,\n" +
                    "      \"subject\": \"4\",\n" +
                    "      \"channel\": \"HELPCENTER\",\n" +
                    "      \"requester\": {\n" +
                    "        \"id\": 22,\n" +
                    "        \"fullName\": \"neil.mathew+customer\",\n" +
                    "        \"lastActiveAt\": \"2017-04-11T16:04:15+00:00\",\n" +
                    "        \"lastSeenAt\": \"2017-04-11T16:04:15+00:00\",\n" +
                    "        \"avatar\": \"https://kayako-mobile-testing.kayako.com/avatar/get/939743ae-8251-5084-809b-dec5d3ce841d?1491926655\",\n" +
                    "        \"resourceType\": \"userMinimal\",\n" +
                    "        \"resourceUrl\": \"https://kayako-mobile-testing.kayako.com/api/v1/users/22\"\n" +
                    "      },\n" +
                    "      \"creator\": {\n" +
                    "        \"id\": 22,\n" +
                    "        \"fullName\": \"neil.mathew+customer\",\n" +
                    "        \"lastActiveAt\": \"2017-04-11T16:04:15+00:00\",\n" +
                    "        \"lastSeenAt\": \"2017-04-11T16:04:15+00:00\",\n" +
                    "        \"avatar\": \"https://kayako-mobile-testing.kayako.com/avatar/get/939743ae-8251-5084-809b-dec5d3ce841d?1491926655\",\n" +
                    "        \"resourceType\": \"userMinimal\",\n" +
                    "        \"resourceUrl\": \"https://kayako-mobile-testing.kayako.com/api/v1/users/22\"\n" +
                    "      },\n" +
                    "      \"lastReplier\": {\n" +
                    "        \"id\": 22,\n" +
                    "        \"fullName\": \"neil.mathew+customer\",\n" +
                    "        \"lastActiveAt\": \"2017-04-11T16:04:15+00:00\",\n" +
                    "        \"lastSeenAt\": \"2017-04-11T16:04:15+00:00\",\n" +
                    "        \"avatar\": \"https://kayako-mobile-testing.kayako.com/avatar/get/939743ae-8251-5084-809b-dec5d3ce841d?1491926655\",\n" +
                    "        \"resourceType\": \"userMinimal\",\n" +
                    "        \"resourceUrl\": \"https://kayako-mobile-testing.kayako.com/api/v1/users/22\"\n" +
                    "      },\n" +
                    "      \"lastAgentReplier\": null,\n" +
                    "      \"assignedTeam\": null,\n" +
                    "      \"assignedAgent\": null,\n" +
                    "      \"status\": {\n" +
                    "        \"id\": 1,\n" +
                    "        \"label\": \"New\",\n" +
                    "        \"type\": \"NEW\",\n" +
                    "        \"sortOrder\": 1,\n" +
                    "        \"isSlaActive\": true,\n" +
                    "        \"isDeleted\": false,\n" +
                    "        \"createdAt\": \"2017-01-09T13:25:38+00:00\",\n" +
                    "        \"updatedAt\": \"2017-01-09T13:25:38+00:00\",\n" +
                    "        \"resourceType\": \"caseStatus\",\n" +
                    "        \"resourceUrl\": \"https://kayako-mobile-testing.kayako.com/api/v1/cases/statuses/1\"\n" +
                    "      },\n" +
                    "      \"isCompleted\": false,\n" +
                    "      \"priority\": null,\n" +
                    "      \"type\": null,\n" +
                    "      \"readMarker\": {\n" +
                    "        \"id\": 321,\n" +
                    "        \"lastReadPostId\": 1737,\n" +
                    "        \"lastReadAt\": \"2017-04-11T04:18:42+00:00\",\n" +
                    "        \"unreadCount\": 0,\n" +
                    "        \"resourceType\": \"readMarker\"\n" +
                    "      },\n" +
                    "      \"customFields\": [],\n" +
                    "      \"realtimeChannel\": \"presence-61485139915436ab6fc57ca6b1e0bc87f58649bc427077133b6e71a278c3e8a2@v1_cases_233\",\n" +
                    "      \"lastMessagePreview\": \"2\",\n" +
                    "      \"lastMessageStatus\": \"DELIVERED\",\n" +
                    "      \"lastRepliedAt\": \"2017-04-10T08:40:35+00:00\",\n" +
                    "      \"createdAt\": \"2017-04-07T11:45:13+00:00\",\n" +
                    "      \"updatedAt\": \"2017-04-10T08:40:35+00:00\",\n" +
                    "      \"resourceType\": \"conversation\",\n" +
                    "      \"resourceUrl\": \"https://kayako-mobile-testing.kayako.com/api/v1/conversations/233\"\n" +
                    "    }"
    );

    @Before
    public void setUp() {
        conversations = new ConversationViewModelHelper();
    }

    @Test
    public void test1() {
        conversations.addOrUpdateElement(conversation, null);
        Assert.assertEquals(false, conversations.getConversationList().get(0).getLastAgentReplierTyping().isTyping());

        conversations.addOrUpdateElement(conversation, new ClientTypingActivity(true, new UserViewModel("", "Someone", 0L)));
        Assert.assertEquals(true, conversations.getConversationList().get(0).getLastAgentReplierTyping().isTyping());
    }

    @Test
    public void test2() {
        conversations.addOrUpdateElement(conversation, null);
        Assert.assertEquals(false, conversations.getConversationList().get(0).getLastAgentReplierTyping().isTyping());

        conversations.updateRealtimeProperty(conversation.getId(), new ClientTypingActivity(true, new UserViewModel("", "Someone", 0L)));
        Assert.assertEquals(true, conversations.getConversationList().get(0).getLastAgentReplierTyping().isTyping());
    }


    @Test
    public void test3() {
        conversations.addOrUpdateElement(conversation, null);
        Assert.assertEquals(false, conversations.getConversationList().get(0).getLastAgentReplierTyping().isTyping());

        conversations.addOrUpdateElement(conversation, new ClientTypingActivity(true, new UserViewModel("", "Someone", 0L)));
        Assert.assertEquals(true, conversations.getConversationList().get(0).getLastAgentReplierTyping().isTyping());

        // Ensure null values do not override existing values
        conversations.addOrUpdateElement(conversation, null);
        Assert.assertEquals(true, conversations.getConversationList().get(0).getLastAgentReplierTyping().isTyping());

    }

    @Test
    public void test4() throws Exception {
        conversations.addOrUpdateElement(conversation, null);
        Assert.assertEquals(false, conversations.getConversationList().get(0).getLastAgentReplierTyping().isTyping());

        conversations.addOrUpdateElement(conversation, new ClientTypingActivity(true, new UserViewModel("", "Someone", 0L)));
        Assert.assertEquals(true, conversations.getConversationList().get(0).getLastAgentReplierTyping().isTyping());

        // Ensure that updating conversation does not change realtime property
        conversations.updateConversationProperty(conversation.getId(), conversation);
        Assert.assertEquals(true, conversations.getConversationList().get(0).getLastAgentReplierTyping().isTyping());
    }
}