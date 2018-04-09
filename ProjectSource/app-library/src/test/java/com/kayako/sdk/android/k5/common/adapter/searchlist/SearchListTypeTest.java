package com.kayako.sdk.android.k5.common.adapter.searchlist;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SearchListTypeTest {

    private static final int SEARCH_ITEM = 2;

    @Test
    public void searchItemValueIsCorrect() {
        assertThat(SearchListType.SEARCH_ITEM, is(equalTo(SEARCH_ITEM)));
    }
}