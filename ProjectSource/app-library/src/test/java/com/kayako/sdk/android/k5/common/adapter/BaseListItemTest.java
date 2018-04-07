package com.kayako.sdk.android.k5.common.adapter;


import junit.framework.Assert;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@RunWith(MockitoJUnitRunner.class)
public class BaseListItemTest {

    private static final int LIST_ITEM_TYPE = 4;


    @Mock
    BaseListItem baseListItem;

    @Test
    public void getData() throws Exception {
        // class is abstract and field is private , going to use reflection
        Field f1 = baseListItem.getClass().getSuperclass().getDeclaredField("listItemType");
        f1.setAccessible(true);
        f1.set(baseListItem, LIST_ITEM_TYPE);
        int expected = (Integer) f1.get(baseListItem);
        MatcherAssert.assertThat(LIST_ITEM_TYPE, is(equalTo(expected)));
    }


}