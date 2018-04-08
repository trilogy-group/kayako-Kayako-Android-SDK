package com.kayako.sdk.android.k5.common.adapter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
        assertEquals(data, baseDataListItem.getData());
    }
}
