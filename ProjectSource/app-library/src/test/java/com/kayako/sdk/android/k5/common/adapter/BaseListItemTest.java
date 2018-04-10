package com.kayako.sdk.android.k5.common.adapter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@RunWith(MockitoJUnitRunner.class)
public class BaseListItemTest {

    private static final int LIST_ITEM_TYPE = 4;
    private static final String LIST_ITEM_TYPE_FIELD = "listItemType";


    @Mock
    private BaseListItem baseListItem;

    @Test
    public void getData() throws Exception {
        //Arrange
        // class is abstract and field is private , going to use reflection
        Field f1 = baseListItem.getClass().getSuperclass().getDeclaredField(LIST_ITEM_TYPE_FIELD);
        f1.setAccessible(true);
        f1.set(baseListItem, LIST_ITEM_TYPE);

        // Act
        int expected = (Integer) f1.get(baseListItem);

        // Assert
        assertThat(LIST_ITEM_TYPE, is(equalTo(expected)));
    }
}
