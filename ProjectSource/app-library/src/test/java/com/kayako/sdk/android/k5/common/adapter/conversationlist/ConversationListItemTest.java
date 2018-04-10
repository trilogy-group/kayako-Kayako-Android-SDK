package com.kayako.sdk.android.k5.common.adapter.conversationlist;

import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.ConversationViewModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ConversationListItemTest {

    @Mock
    private ConversationViewModel conversationViewModel;

    @Test
    public void getContentsWhenContentIsNotNull() {
        // Arrange
        Map<String, String> map = new HashMap<>();
        map.put("conversationId", "123");
        map.put("name", "kayako");
        map.put("avatarUrl", "avatar");
        map.put("timeInMilleseconds", String.valueOf(1234));
        map.put("subject", "testSubject");
        map.put("unreadCount", String.valueOf(1));

        when(conversationViewModel.getContents()).thenReturn(map);
        ConversationListItem conversationListItem = new ConversationListItem(conversationViewModel);

        //Act
        Map<String, String> expected = conversationListItem.getContents();

        //Assert
        verify(conversationViewModel, times(1)).getContents();
        assertEquals(conversationListItem.getContents(), expected);
    }

    @Test
    public void getContentsWhenContentIsNull() {
        //Arrange
        Map<String, String> map = new HashMap<>();
        ConversationListItem conversationListItem = new ConversationListItem(null);

        //Act
        Map<String, String> expected = conversationListItem.getContents();

        //Assert
        verifyZeroInteractions(conversationViewModel);
        assertEquals(expected, map);
    }
}

