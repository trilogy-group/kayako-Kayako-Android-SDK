package com.kayako.sdk.android.k5.common.adapter.list;

import com.kayako.sdk.base.parser.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ListItemTest {

    private static final int LIST_TYPE = 2;
    private static final String SUBTITLE = "subtitleTest";
    private static final String TITLE = "titleTest";

    private Resource resource;
    private ListItem listItem;

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Before
    public void setUp() {
        listItem = new ListItem(TITLE, SUBTITLE, resource);
    }


    @Test
    public void equals() {
        //Act
        ListItem listItem1 = new ListItem(TITLE, SUBTITLE, resource);

        //Assert
        assertEquals(true, listItem.equals(listItem1));
    }

    @Test
    public void setTitle() {
        //Act
        listItem.setTitle("titleTest");

        //Assert
        assertThat(listItem.getTitle(), is(equalTo(TITLE)));
    }

    @Test
    public void setSubtitle() {
        //Act
        listItem.setSubtitle("subtitleTest");

        //Assert
        assertThat(listItem.getSubtitle(), is(equalTo(SUBTITLE)));
    }

    @Test
    public void setResource() {
        //Act
        listItem.setResource(resource);

        //Assert
        assertThat(listItem.getResource(), is(equalTo(resource)));
    }

    @Test
    public void constructorAllArgs() {
        //Act
        listItem = new ListItem(LIST_TYPE, TITLE, SUBTITLE, resource);

        //Assert
        collector.checkThat(listItem.getItemType(), is(equalTo(LIST_TYPE)));
        collector.checkThat(listItem.getTitle(), is(equalTo(TITLE)));
        collector.checkThat(listItem.getSubtitle(), is(equalTo(SUBTITLE)));
        collector.checkThat(listItem.getResource(), is(equalTo(resource)));
    }

    @Test
    public void getContents() {
        //Act
        String listItemTitle = listItem.getContents().get("title");
        String listItemSubTitle = listItem.getContents().get("subtitle");

        //Assert
        collector.checkThat(listItemTitle, is(equalTo(String.valueOf(TITLE))));
        collector.checkThat(listItemSubTitle, is(equalTo(String.valueOf(SUBTITLE))));
    }

}
