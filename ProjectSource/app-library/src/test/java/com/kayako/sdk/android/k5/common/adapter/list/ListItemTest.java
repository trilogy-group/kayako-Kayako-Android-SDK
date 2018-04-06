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

    private String title;
    private String subtitle;
    private int listType;
    private Resource resource;
    private ListItem listItem;

    @Before
    public void setUp() {
        this.title = "titleTest";
        this.subtitle = "subtitleTest";
        this.listType = 2;
        listItem = new ListItem(this.title, this.subtitle, this.resource);
    }


    @Test
    public void getTitleTest() {
        assertThat(listItem.getTitle(), is(equalTo(this.title)));
    }

    @Test
    public void setTitleTest() {
        listItem.setTitle("titleTest");
        assertThat(listItem.getTitle(), is(equalTo(this.title)));
    }

    @Test
    public void getSubtitleTest() {
        assertThat(listItem.getSubtitle(), is(equalTo(this.subtitle)));
    }

    @Test
    public void setSubtitleTest() {
        listItem.setSubtitle("subtitleTest");
        assertThat(listItem.getSubtitle(), is(equalTo(this.subtitle)));
    }

    @Test
    public void getResourceTest() {
        assertThat(listItem.getResource(), is(equalTo(this.resource)));
    }

    @Test
    public void setResourceTest() {
        listItem.setResource(resource);
        assertThat(listItem.getResource(), is(equalTo(resource)));
    }

    @Test
    public void constructorAllArgsTest() {
        listItem = new ListItem(this.listType, this.title, this.subtitle, this.resource);
        collector.checkThat(listItem.getItemType(), is(equalTo(this.listType)));
        collector.checkThat(listItem.getTitle(), is(equalTo(this.title)));
        collector.checkThat(listItem.getSubtitle(), is(equalTo(this.subtitle)));
        collector.checkThat(listItem.getResource(), is(equalTo(resource)));
    }

    @Test
    public void getContentsTest() {
        String listItemTitle = listItem.getContents().get("title");
        String listItemSubTitle = listItem.getContents().get("subtitle");
        collector.checkThat(listItemTitle, is(equalTo(String.valueOf(this.title))));
        collector.checkThat(listItemSubTitle, is(equalTo(String.valueOf(this.subtitle))));
    }

    @Test
    public void equalsTest() {
        ListItem listItem1 = new ListItem(this.title, this.subtitle, this.resource);
        assertEquals(true, this.listItem.equals(listItem1));
    }
}