package com.kayako.sdk.android.k5.common.adapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BaseDataListItemTest {

    @Mock
    Map<String, Object> data;

    @Mock
    BaseDataListItem baseDataListItem;


    @Test
    public void getData() {
        baseDataListItem.setData(data);
        assertEquals(baseDataListItem.getData(), data);
    }

    @Test
    public void setData() {
        baseDataListItem.setData(data);
        assertEquals(baseDataListItem.getData(), data);
    }
}