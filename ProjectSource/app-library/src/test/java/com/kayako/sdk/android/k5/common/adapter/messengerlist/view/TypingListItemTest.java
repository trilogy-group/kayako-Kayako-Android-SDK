package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;


public class TypingListItemTest {

    private static final String AVATARURL = "/avatarUrl";
    private TypingListItem typingListItem;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp(){
        typingListItem = new TypingListItem(AVATARURL);
    }

    @Test
    public void test_constructor(){
        errorCollector.checkThat(typingListItem, notNullValue());
        errorCollector.checkThat(typingListItem.getItemType(), is(equalTo(MessengerListType.TYPING_FOOTER)));
    }

    @Test
    public void test_getAvatarUrl(){
        errorCollector.checkThat(typingListItem.getAvatarUrl(), is(equalTo(AVATARURL)));
    }

    @Test
    public void test_setAvatarUrl(){
        typingListItem.setAvatarUrl("/avatarUrl2");
        errorCollector.checkThat(typingListItem.getAvatarUrl(), is(equalTo("/avatarUrl2")));
    }

    @Test
    public void test_getContents(){
        Map map = typingListItem.getContents();
        errorCollector.checkThat(map.size() > 0, is(true));
        errorCollector.checkThat(map.get("avatarUrl").toString(), is(equalTo(AVATARURL)));
    }
}
