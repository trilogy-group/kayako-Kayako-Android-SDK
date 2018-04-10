package com.kayako.sdk.android.k5.common.adapter.spinnerlist;

import com.kayako.sdk.base.parser.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class SpinnerItemTest {

    @Mock
    private Resource resource;

    private static final SpinnerItem spinnerItem = new SpinnerItem();

    private static final String LABEL = "labelForTest";

    @Test
    public void getResourceTest() {
        spinnerItem.setResource(resource);
        assertThat(spinnerItem.getResource(), is(resource));
    }

    @Test
    public void setResourceTest() {
        spinnerItem.setResource(resource);
        assertThat(spinnerItem.getResource(), is(resource));
    }

    @Test
    public void getLabel() {
        spinnerItem.setLabel(LABEL);
        assertThat(spinnerItem.getLabel(), is(LABEL));
    }

    @Test
    public void setLabel() {
        spinnerItem.setLabel(LABEL);
        assertThat(spinnerItem.getLabel(), is(LABEL));
    }

    @Test
    public void equalsTest() {
        SpinnerItem spinnerItem1 = new SpinnerItem();
        spinnerItem.setResource(resource);
        spinnerItem1.setResource(resource);
        assertTrue(spinnerItem.equals(spinnerItem1));
    }
}

