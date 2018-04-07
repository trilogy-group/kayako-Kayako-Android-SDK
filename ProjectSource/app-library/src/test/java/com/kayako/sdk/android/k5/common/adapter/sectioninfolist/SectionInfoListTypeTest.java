package com.kayako.sdk.android.k5.common.adapter.sectioninfolist;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SectionInfoListTypeTest {
    private static final int SECTION_INFO_ITEM = 4;

    @Test
    public void testSectionInfoListTypPropertyValueIsCorrect() {
        assertThat(SectionInfoListType.SECTION_INFO_ITEM, is(equalTo(SECTION_INFO_ITEM)));
    }

}