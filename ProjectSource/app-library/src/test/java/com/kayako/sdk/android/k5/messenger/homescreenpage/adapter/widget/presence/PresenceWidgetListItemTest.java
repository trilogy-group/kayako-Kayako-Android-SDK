package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.presence;

import com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.presence.PresenceWidgetListItem;

import static org.junit.Assert.assertThat;

import static org.hamcrest.core.Is.is;

import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.util.Map;

public class PresenceWidgetListItemTest {
    private static final String TITLE = "Test title";
    private static final String PRESENCE_CAPTION = "Presence caption";
    private static final String AVATAR_URL_1 = "http://kayako.com/avatar1.png";
    private static final String AVATAR_URL_2 = "http://kayako.com/avatar2.png";
    private static final String AVATAR_URL_3 = "http://kayako.com/avatar3.png";

    private PresenceWidgetListItem presenceWidgetListItem;

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Before
    public void setUp() {
        presenceWidgetListItem = new PresenceWidgetListItem(TITLE, PRESENCE_CAPTION, AVATAR_URL_1,
                AVATAR_URL_2, AVATAR_URL_3);
    }

    @Test
    public void getPresenceCaption() {
        assertThat(presenceWidgetListItem.getPresenceCaption(), is(PRESENCE_CAPTION));
    }

    @Test
    public void getAvatarUrl1() {
        assertThat(presenceWidgetListItem.getAvatarUrl1(), is(AVATAR_URL_1));
    }

    @Test
    public void getAvatarUrl2() {
        assertThat(presenceWidgetListItem.getAvatarUrl2(), is(AVATAR_URL_2));
    }

    @Test
    public void getAvatarUrl3() {
        assertThat(presenceWidgetListItem.getAvatarUrl3(), is(AVATAR_URL_3));
    }

    @Test
    public void getContents() {
        Map<String,String> contents = presenceWidgetListItem.getContents();
        collector.checkThat(contents.get("title"), equalTo(TITLE));
        collector.checkThat(contents.get("presenceCaption"), equalTo(PRESENCE_CAPTION));
        collector.checkThat(contents.get("avatarUrl1"), equalTo(AVATAR_URL_1));
        collector.checkThat(contents.get("avatarUrl2"), equalTo(AVATAR_URL_2));
        collector.checkThat(contents.get("avatarUrl3"), equalTo(AVATAR_URL_3));
    }
}
