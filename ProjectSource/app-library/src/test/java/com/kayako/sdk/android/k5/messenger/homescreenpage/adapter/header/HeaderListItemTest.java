package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.header;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by pedroveras on 16/04/18.
 */

public class HeaderListItemTest {
    private static final String TITLE = "Test title";
    private static final String SUBTITLE = "Test subtitle";

    private HeaderListItem headerListItem;

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
    public void testThrowException() {
        try {
            HeaderListItem headerListItem = new HeaderListItem(null, null);
        } catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
        }
    }

    @Test
    public void getContents() {
        Map<String,String> map = headerListItem.getContents();
        assertEquals(map.get("title"),TITLE);
        assertEquals(map.get("subtitle"),SUBTITLE);
    }
}
