package com.kayako.sdk.android.k5.common.adapter.list;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;


public class ListTypeTest {

    private static final int LIST_ITEM = 2;
    private static final int HEADER_ITEM = 3;

    @Test
    public void listItemValueIsCorrect() {
        assertThat(ListType.LIST_ITEM, is(equalTo(LIST_ITEM)));
    }

    @Test
    public void headerItemValueIsCorrect() {
        assertThat(ListType.HEADER_ITEM, is(equalTo(HEADER_ITEM)));
    }
}