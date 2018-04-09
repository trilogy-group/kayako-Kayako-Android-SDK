package com.kayako.sdk.android.k5.common.adapter.searchlist;

import com.kayako.sdk.base.parser.Resource;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SearchListItemTest {

    private static final String TITLE = "title";
    private static final String SUBTITLE = "subtitle";

    private Resource resource;

    private SearchListItem searchListItem;

    @Before
    public void setUp() {
        searchListItem = new SearchListItem(TITLE, SUBTITLE, resource);
    }

    @Test
    public void getTitleTest() {
        assertThat(searchListItem.getTitle(), is(equalTo(TITLE)));
    }

    @Test
    public void setTitleTest() {
        searchListItem.setTitle(TITLE);
        assertThat(searchListItem.getTitle(), is(equalTo(TITLE)));
    }

    @Test
    public void getSubtitleTest() {
        assertThat(searchListItem.getSubtitle(), is(equalTo(SUBTITLE)));
    }

    @Test
    public void setSubtitleTest() {
        searchListItem.setSubtitle(SUBTITLE);
        assertThat(searchListItem.getSubtitle(), is(equalTo(SUBTITLE)));
    }

    @Test
    public void getResourceTest() {
        assertThat(searchListItem.getResource(), is(equalTo(resource)));
    }

    @Test
    public void setResourceTest() {
        searchListItem.setResource(resource);
        assertThat(searchListItem.getResource(), is(equalTo(resource)));
    }
}