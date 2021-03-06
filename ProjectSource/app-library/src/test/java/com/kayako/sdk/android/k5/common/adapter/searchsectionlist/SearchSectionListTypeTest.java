package com.kayako.sdk.android.k5.common.adapter.searchsectionlist;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class SearchSectionListTypeTest {

    private static final int SEARCH_SECTION_ITEM = 4;

    @Test
    public void listItemValueIsCorrect() {
        assertThat(SearchSectionListType.SEARCH_SECTION_ITEM, is(SEARCH_SECTION_ITEM));
    }

}
