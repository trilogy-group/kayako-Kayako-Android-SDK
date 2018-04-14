package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.MessengerListType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

public class TypingListItemTest {

    private static final String AVATAR_URL = "/avatarUrl";
    private TypingListItem typingListItem;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp(){
        typingListItem = new TypingListItem(AVATAR_URL);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated(){
        errorCollector.checkThat(typingListItem.getItemType(), is(equalTo(MessengerListType.TYPING_FOOTER)));
        errorCollector.checkThat(typingListItem.getAvatarUrl(), is(equalTo(AVATAR_URL)));
    }

    @Test
    public void setAvatarUrl(){
        final String newAvatarUrl = "/avatarUrlNew";
        typingListItem.setAvatarUrl(newAvatarUrl);
        errorCollector.checkThat(typingListItem.getAvatarUrl(), is(equalTo(newAvatarUrl)));
    }

    @Test
    public void getContents(){
        Map<String, String> map = typingListItem.getContents();
        errorCollector.checkThat(map.isEmpty(), is(false));
        errorCollector.checkThat(map.get("avatarUrl"), is(equalTo(AVATAR_URL)));
    }
}
