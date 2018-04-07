package com.kayako.sdk.android.k5.common.adapter.list;

import com.kayako.sdk.base.parser.Resource;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class HeaderListItemTest {

    private HeaderListItem headerListItem;
    private static final String TITLE = "titleForTest";

    @Mock
    private Resource resource;

    @Before
    public void setUp() {
        this.headerListItem = new HeaderListItem(TITLE, resource);
    }

    @Test
    public void setTitleTest() {
        headerListItem.setTitle(TITLE);
        assertThat(headerListItem.getTitle(), is(equalTo(TITLE)));
    }

    @Test
    public void getTitleTest() {
        assertThat(headerListItem.getTitle(), is(equalTo(TITLE)));
    }

    @Test
    public void constructorTest() {
        assertThat(headerListItem.getTitle(), is(equalTo(TITLE)));
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
        assertThat(itemTitle, is(equalTo(String.valueOf(TITLE))));
    }
}