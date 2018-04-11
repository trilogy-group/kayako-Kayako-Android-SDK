package com.kayako.sdk.android.k5.common.adapter.list;

import com.kayako.sdk.base.parser.Resource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class HeaderListItemTest {

    private static final String TITLE = "titleForTest";
    private static final String CONTENT_TITLE = "title";

    private HeaderListItem headerListItem;

    @Mock
    private Resource resource;

    @Before
    public void setUp() {
        headerListItem = new HeaderListItem(TITLE, resource);
    }

    @Test
    public void setTitle() {
        //Act
        headerListItem.setTitle(TITLE);

        //Assert
        assertThat(headerListItem.getTitle(), is(equalTo(TITLE)));
    }

    @Test
    public void setResource() {
        //Act
        headerListItem.setResource(resource);

        //Assert
        assertThat(headerListItem.getResource(), is(equalTo(resource)));
    }

    @Test
    public void getContents() {
        //Act
        String itemTitle = headerListItem.getContents().get(CONTENT_TITLE);

        //Assert
        assertThat(itemTitle, is(equalTo(TITLE)));
    }
}