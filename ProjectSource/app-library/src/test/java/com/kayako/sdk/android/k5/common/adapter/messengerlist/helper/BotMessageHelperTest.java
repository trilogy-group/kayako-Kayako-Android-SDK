package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import com.kayako.sdk.android.k5.R;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;

public class BotMessageHelperTest {

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Test
    public void getBotDrawableForSystemMessage() {
        errorCollector.checkThat(BotMessageHelper.getBotDrawableForSystemMessage(), is(R.drawable.ko__bot_avatar));
    }

    @Test
    public void getDefaultDrawableForConversation() {
        errorCollector.checkThat(BotMessageHelper.getDefaultDrawableForConversation(), is(R.drawable.ko__ic_default_agent));
    }
}
