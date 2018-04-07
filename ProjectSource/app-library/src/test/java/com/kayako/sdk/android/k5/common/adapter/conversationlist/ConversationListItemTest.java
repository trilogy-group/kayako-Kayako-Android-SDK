package com.kayako.sdk.android.k5.common.adapter.conversationlist;

import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.ConversationViewModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;


import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ConversationListItemTest {

    @Mock
    ConversationViewModel conversationViewModel;

    @Test
    public void getContentsTest() {
        Map<String, String> map = new HashMap<>();
        map.put("conversationId", String.valueOf("123"));
        map.put("name", String.valueOf("kayako"));
        map.put("avatarUrl", String.valueOf("avatar"));
        map.put("timeInMilleseconds", String.valueOf(1234));
        map.put("subject", String.valueOf("testSubject"));
        map.put("unreadCount", String.valueOf(1));

        when(conversationViewModel.getContents()).thenReturn(map);
        ConversationListItem conversationListItem = new ConversationListItem(conversationViewModel);
        Map<String, String> expected = conversationViewModel.getContents();
        verify(conversationViewModel, times(1)).getContents();
        assertEquals(conversationListItem.getContents(), expected);
    }
}