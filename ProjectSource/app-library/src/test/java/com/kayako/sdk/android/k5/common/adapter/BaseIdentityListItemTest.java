package com.kayako.sdk.android.k5.common.adapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BaseIdentityListItemTest {

    private static final long ID = 0L;

    @Mock
    BaseIdentityListItem baseIdentityListItem;

    @Before
    public void setUp() throws Exception {
        baseIdentityListItem.setId(ID);
    }

    @Test
    public void getIdTest() {
        assertEquals(baseIdentityListItem.getId().longValue(), ID);
    }
}
