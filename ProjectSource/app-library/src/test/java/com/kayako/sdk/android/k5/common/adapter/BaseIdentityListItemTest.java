package com.kayako.sdk.android.k5.common.adapter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BaseIdentityListItemTest {

    private static final long ID = 0L;

    @Mock
    private BaseIdentityListItem baseIdentityListItem;

    @Test
    public void getIdTest() {
        // Arrange & Act
        baseIdentityListItem.setId(ID);

        // Assert
        assertThat(ID, is(baseIdentityListItem.getId()));
    }
}  
