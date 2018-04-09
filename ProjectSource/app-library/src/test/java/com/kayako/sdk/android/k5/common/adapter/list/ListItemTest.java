package com.kayako.sdk.android.k5.common.adapter.list;

import com.kayako.sdk.base.parser.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ListItemTest {

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    private static final String TITLE = "titleTest";
    private static final String SUBTITLE = "subtitleTest";
    private static final int LIST_TYPE = 2;

    private Resource resource;
    private ListItem listItem;

    @Before
    public void setUp() {
        listItem = new ListItem(TITLE, SUBTITLE, resource);
    }

    @Test
    public void setTitleTest() {
        listItem.setTitle("titleTest");
        assertThat(listItem.getTitle(), is(equalTo(TITLE)));
    }

    @Test
    public void setSubtitleTest() {
        listItem.setSubtitle("subtitleTest");
        assertThat(listItem.getSubtitle(), is(equalTo(SUBTITLE)));
    }

    @Test
    public void getResourceTest() {
        assertThat(listItem.getResource(), is(equalTo(resource)));
    }

    @Test
    public void setResourceTest() {
        listItem.setResource(resource);
        assertThat(listItem.getResource(), is(equalTo(resource)));
    }

    @Test
    public void constructorAllArgsTest() {
        listItem = new ListItem(LIST_TYPE, TITLE, SUBTITLE, resource);
        collector.checkThat(listItem.getItemType(), is(equalTo(LIST_TYPE)));
        collector.checkThat(listItem.getTitle(), is(equalTo(TITLE)));
        collector.checkThat(listItem.getSubtitle(), is(equalTo(SUBTITLE)));
        collector.checkThat(listItem.getResource(), is(equalTo(resource)));
    }

    @Test
    public void getContentsTest() {
        String listItemTitle = listItem.getContents().get("title");
        String listItemSubTitle = listItem.getContents().get("subtitle");

        collector.checkThat(listItemTitle, is(equalTo(String.valueOf(TITLE))));
        collector.checkThat(listItemSubTitle, is(equalTo(String.valueOf(SUBTITLE))));
    }

    @Test
    public void equalsTest() {
        ListItem listItem1 = new ListItem(TITLE, SUBTITLE, resource);
        assertEquals(true, listItem.equals(listItem1));
    }
}