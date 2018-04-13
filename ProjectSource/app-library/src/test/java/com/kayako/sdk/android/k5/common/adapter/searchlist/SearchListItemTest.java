package com.kayako.sdk.android.k5.common.adapter.searchlist;

import com.kayako.sdk.base.parser.Resource;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SearchListItemTest {

    private static final String SUBTITLE = "subtitle";
    private static final String TITLE = "title";

    private Resource resource;

    private SearchListItem searchListItem;

    @Before
    public void setUp() {
        searchListItem = new SearchListItem(TITLE, SUBTITLE, resource);
    }

    @Test
    public void setTitleTest() {
        // Act
        searchListItem.setTitle(TITLE);

        //Assert
        assertThat(searchListItem.getTitle(), is(equalTo(TITLE)));
    }

    @Test
    public void setSubtitleTest() {
        // Act
        searchListItem.setSubtitle(SUBTITLE);

        ///Assert
        assertThat(searchListItem.getSubtitle(), is(equalTo(SUBTITLE)));
    }

    @Test
    public void setResourceTest() {
        // Act
        searchListItem.setResource(resource);

        //Assert
        assertThat(searchListItem.getResource(), is(equalTo(resource)));
    }
}