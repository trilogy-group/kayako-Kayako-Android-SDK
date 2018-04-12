package com.kayako.sdk.android.k5.common.adapter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.when;

import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BaseDataListItemTest {

    @Mock
    private Map<String, Object> data;

    @Mock
    private BaseDataListItem baseDataListItem;

    @Test
    public void getData() {
        //Arrange
        when(baseDataListItem.getData()).thenReturn(data);

        //Act
        baseDataListItem.setData(data);

        //Assert
        assertEquals(data, baseDataListItem.getData());
    }
}
