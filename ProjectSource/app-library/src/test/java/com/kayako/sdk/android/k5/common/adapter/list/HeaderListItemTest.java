package com.kayako.sdk.android.k5.common.adapter.list;

import com.kayako.sdk.base.parser.Resource;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class HeaderListItemTest {

    private HeaderListItem headerListItem;
    private String title;

    @Mock
    private Resource resource;

    @Before
    public void setUp() {
        this.title = "titleForTest";
        this.headerListItem = new HeaderListItem(title, resource);
    }

    @Test
    public void setTitleTest() {
        headerListItem.setTitle(this.title);
        assertThat(headerListItem.getTitle(), is(equalTo(this.title)));
    }

    @Test
    public void getTitleTest() {
        assertThat(headerListItem.getTitle(), is(equalTo(this.title)));
    }

    @Test
    public void constructorTest() {
        assertThat(headerListItem.getTitle(), is(equalTo(this.title)));
    }

    @Test
    public void getResourceTest() {
        assertThat(headerListItem.getResource(), is(equalTo(resource)));
    }

    @Test
    public void setResourceTest() {
        headerListItem.setResource(resource);
        assertThat(headerListItem.getResource(), is(equalTo(resource)));
    }

    @Test
    public void getContentsTest() {
        String itemTitle = headerListItem.getContents().get("title");
        assertThat(itemTitle, is(equalTo(String.valueOf(this.title))));
    }
}