package com.kayako.sdk.android.k5.common.adapter.conversationlist;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConversationListTypeTest {

    private static final int CONVERSATION_LIST_ITEM = 2;

    @Test
    public void conversationListItemValueIsCorrect() {
        //Assert
        assertThat(CONVERSATION_LIST_ITEM, is(2));
    }
}
