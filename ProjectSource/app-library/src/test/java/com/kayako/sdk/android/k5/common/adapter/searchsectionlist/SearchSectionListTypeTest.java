package com.kayako.sdk.android.k5.common.adapter.searchsectionlist;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SearchSectionListTypeTest {

    private static final int SEARCH_SECTION_ITEM = 4;

    @Test
    public void listItemValueIsCorrect() {
        assertThat(SearchSectionListType.SEARCH_SECTION_ITEM, is(equalTo(SEARCH_SECTION_ITEM)));
    }

}
