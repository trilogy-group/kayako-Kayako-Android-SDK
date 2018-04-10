package com.kayako.sdk.android.k5.common.adapter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BaseIdentityListItemTest {

    private static final Long ID = 0L;

    @Mock
    private BaseIdentityListItem baseIdentityListItem;

    @Test
    public void getIdTest() {
        baseIdentityListItem.setId(ID);
        assertEquals(ID, baseIdentityListItem.getId());
    }
}  
