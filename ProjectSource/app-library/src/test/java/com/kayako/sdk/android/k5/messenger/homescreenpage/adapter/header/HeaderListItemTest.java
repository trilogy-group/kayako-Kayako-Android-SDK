package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.header;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class HeaderListItemTest {
    private static final String TITLE = "Test title";
    private static final String SUBTITLE = "Test subtitle";

    private HeaderListItem headerListItem;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        headerListItem = new HeaderListItem(TITLE, SUBTITLE);
    }

    @Test
    public void getTitle() {
        assertEquals(headerListItem.getTitle(), TITLE);
    }

    @Test
    public void getSubTitle() {
        assertEquals(headerListItem.getSubtitle(), SUBTITLE);
    }

    @Test
    public void test_Constructor_ThrowException() {
        thrown.expect(IllegalArgumentException.class);
        new HeaderListItem(null, null);
    }

    @Test
    public void getContents() {
        Map<String,String> map = headerListItem.getContents();
        assertEquals(map.get("title"),TITLE);
        assertEquals(map.get("subtitle"),SUBTITLE);
    }
}
