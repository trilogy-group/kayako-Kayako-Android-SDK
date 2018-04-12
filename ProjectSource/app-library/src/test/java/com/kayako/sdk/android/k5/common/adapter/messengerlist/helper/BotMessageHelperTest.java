package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import com.kayako.sdk.android.k5.R;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BotMessageHelperTest {

    @Test
    public void getBotDrawableForSystemMessage() {
        assertEquals(R.drawable.ko__bot_avatar,
                BotMessageHelper.getBotDrawableForSystemMessage());
    }

    @Test
    public void getDefaultDrawableForConversation() {
        assertEquals(R.drawable.ko__ic_default_agent,
                BotMessageHelper.getDefaultDrawableForConversation());
    }
}
