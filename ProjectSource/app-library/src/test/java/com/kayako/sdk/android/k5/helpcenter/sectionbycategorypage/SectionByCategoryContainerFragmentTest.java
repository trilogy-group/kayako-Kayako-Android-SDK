package com.kayako.sdk.android.k5.helpcenter.sectionbycategorypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({

})
public class SectionByCategoryContainerFragmentTest {
    @Mock
    private Bundle bundle;

    @Mock
    private LayoutInflater layoutInflater;

    @Mock
    private ViewGroup viewGroup;

    @Mock
    private View view;

    @Test
    public void givenBundleWhenOnCreateThenSetPresenter(){
        //Arrange
        SectionByCategoryContainerFragment sectionByCategoryContainerFragment =
                new SectionByCategoryContainerFragment();

        //Act
        sectionByCategoryContainerFragment.onCreate(bundle);

        //Assert
        assertNull(sectionByCategoryContainerFragment.mPresenter);
    }

    @Test
    public void givenLayoutViewBundleWhenOnCreateViewThenReturnView(){
        //Arrange
        SectionByCategoryContainerFragment sectionByCategoryContainerFragment =
                new SectionByCategoryContainerFragment();
        when(layoutInflater.inflate(R.layout.ko__fragment_help_center, viewGroup, Boolean.FALSE)).thenReturn(view);

        //Act
        View returnedValue = sectionByCategoryContainerFragment.onCreateView(layoutInflater, viewGroup, bundle);

        //Assert
        assertEquals(view, returnedValue);
    }

    @Test
    public void givenViewBundleWhenOnViewCreatedThenCommit(){
        //Arrange
        SectionByCategoryContainerFragment sectionByCategoryContainerFragment =
                new SectionByCategoryContainerFragment();

        //Act
        sectionByCategoryContainerFragment.onViewCreated(view, bundle);

        //Assert
        assertNull(sectionByCategoryContainerFragment.mPresenter);
    }
}
