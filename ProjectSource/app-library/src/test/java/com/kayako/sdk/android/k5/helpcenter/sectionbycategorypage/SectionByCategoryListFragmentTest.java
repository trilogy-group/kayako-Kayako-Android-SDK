package com.kayako.sdk.android.k5.helpcenter.sectionbycategorypage;

import android.app.Fragment;
import android.app.FragmentHostCallback;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.kayako.sdk.android.k5.activities.KayakoSearchArticleActivity;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.list.ListItem;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollAdapter;
import com.kayako.sdk.android.k5.common.fragments.ActivityNavigationResourceCallback;
import com.kayako.sdk.android.k5.common.fragments.BaseListFragment;
import com.kayako.sdk.android.k5.common.task.BackgroundTask;
import com.kayako.sdk.android.k5.messenger.style.type.Background;
import com.kayako.sdk.helpcenter.section.Section;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        SectionByCategoryFactory.class,
        KayakoSearchArticleActivity.class,
        BaseListFragment.class,
        SectionByCategoryListFragment.class
})
public class SectionByCategoryListFragmentTest {
    @Mock
    Context context;

    @Mock
    Bundle bundle;

    @Mock
    SectionByCategoryContract.Presenter presenter;

    @Mock
    View view;

    @Mock
    Section section;

    @Mock
    ActivityNavigationResourceCallback activityNavigationResourceCallback;

    @Mock
    Intent intent;

    @Mock
    BackgroundTask backgroundTask;

    @Mock
    BaseListItem baseListItem;

    @Mock
    ListItem listItem;

    @Test
    public void givenClassWhenNewInstanceThenReturnNewInstance(){
        //Act
        SectionByCategoryListFragment returnedValue = SectionByCategoryListFragment.newInstance();

        //Assert
        assertNotNull(returnedValue);
    }

    @Test
    public void givenContextWhenOnAttachThenSetActivity(){
        //Arrange
        SectionByCategoryListFragment sectionByCategoryListFragment = new SectionByCategoryListFragment();

        //Act
        sectionByCategoryListFragment.onAttach(context);

        //Assert
        assertNull(sectionByCategoryListFragment.mActivityNavigation);
    }

    @Test
    public void givenBundleWhenOnCreateThenSetPresenter(){
        //Arrange
        SectionByCategoryListFragment sectionByCategoryListFragment = new SectionByCategoryListFragment();
        mockStatic(SectionByCategoryFactory.class);
        when(SectionByCategoryFactory.getPresenter(sectionByCategoryListFragment)).thenReturn(presenter);

        //Act
        sectionByCategoryListFragment.onCreate(bundle);

        //Assert
        assertEquals(presenter, sectionByCategoryListFragment.mPresenter);
    }

    @Test(expected = java.lang.NullPointerException.class)
    public void givenViewBundleWhenOnViewCreatedThenSetupView(){
        //Arrange
        SectionByCategoryListFragment sectionByCategoryListFragment = new SectionByCategoryListFragment();
        mockStatic(SectionByCategoryFactory.class);
        when(SectionByCategoryFactory.getPresenter(sectionByCategoryListFragment)).thenReturn(presenter);
        sectionByCategoryListFragment.onCreate(bundle);

        //Act
        sectionByCategoryListFragment.onViewCreated(view, bundle);

        //Assert
        assertNotNull(sectionByCategoryListFragment.mPresenter);
    }

    @Test
    public void givenBundleWhenOnActivityCreatedThenInitPage(){
        //Arrange
        SectionByCategoryListFragment sectionByCategoryListFragment = new SectionByCategoryListFragment();
        mockStatic(SectionByCategoryFactory.class);
        when(SectionByCategoryFactory.getPresenter(sectionByCategoryListFragment)).thenReturn(presenter);
        sectionByCategoryListFragment.onCreate(bundle);

        //Act
        sectionByCategoryListFragment.onActivityCreated(bundle);

        //Assert
        verify(presenter).initPage();
    }

    @Test
    public void givenClassWhenOpenArticleListingPageThenExecuteTask() throws Exception {
        //Arrange
        SectionByCategoryListFragment sectionByCategoryListFragment = new SectionByCategoryListFragment();
        sectionByCategoryListFragment.mActivityNavigation = activityNavigationResourceCallback;

        //Act
        sectionByCategoryListFragment.openArticleListingPage(section);

        //Assert
        assertNotNull(sectionByCategoryListFragment.mActivityNavigation);
    }

    @Test(expected = java.lang.IllegalStateException.class)
    public void givenClassWhenOpenSearchPageThenStartActivity(){
        //Arrange
        SectionByCategoryListFragment sectionByCategoryListFragment = new SectionByCategoryListFragment();
        mockStatic(KayakoSearchArticleActivity.class);
        when(KayakoSearchArticleActivity.getIntent(null)).thenReturn(intent);

        //Act
        sectionByCategoryListFragment.openSearchPage();

        //Assert
        assertNull(sectionByCategoryListFragment.mActivityNavigation);
    }

    @Test
    public void givenClassWhenOnDetachThenActivityNull(){
        //Arrange
        SectionByCategoryListFragment sectionByCategoryListFragment = new SectionByCategoryListFragment();

        //Act
        sectionByCategoryListFragment.onDetach();

        //Assert
        assertNull(sectionByCategoryListFragment.mActivityNavigation);
    }

    @Test
    public void givenClassWhenOnDestroyViewThenBackgroundTaskNull(){
        //Arrange
        SectionByCategoryListFragment sectionByCategoryListFragment = new SectionByCategoryListFragment();
        sectionByCategoryListFragment.mBackgroundTask = backgroundTask;

        //Act
        sectionByCategoryListFragment.onDestroyView();

        //Assert
        assertNull(sectionByCategoryListFragment.mBackgroundTask);
    }

    @Test
    public void givenListWhenSetUpListThenInitList(){
        //Arrange
        SectionByCategoryListFragment sectionByCategoryListFragment = new SectionByCategoryListFragment();
        List<BaseListItem> listLocal = new ArrayList<>();
        listLocal.add(baseListItem);
        suppress(method(BaseListFragment.class, "initList", EndlessRecyclerViewScrollAdapter.class));

        //Act
        sectionByCategoryListFragment.setUpList(listLocal);

        //Assert
        assertNotNull(sectionByCategoryListFragment.listItemRecyclerViewAdapter);
    }

    @Test(expected = java.lang.NullPointerException.class)
    public void givenClassWhenShowOnlyListViewThenShowViews(){
        //Arrange
        SectionByCategoryListFragment sectionByCategoryListFragment = new SectionByCategoryListFragment();

        //Act
        sectionByCategoryListFragment.showOnlyListView();

        //Assert
        assertNull(sectionByCategoryListFragment.mActivityNavigation);
    }

    @Test(expected = java.lang.NullPointerException.class)
    public void givenClassWhenShowOnlyEmptyViewThenShowViews(){
        //Arrange
        SectionByCategoryListFragment sectionByCategoryListFragment = new SectionByCategoryListFragment();

        //Act
        sectionByCategoryListFragment.showOnlyEmptyView();

        //Assert
        assertNull(sectionByCategoryListFragment.mActivityNavigation);
    }

    @Test(expected = java.lang.NullPointerException.class)
    public void givenClassWhenShowOnlyErrorViewThenShowViews(){
        //Arrange
        SectionByCategoryListFragment sectionByCategoryListFragment = new SectionByCategoryListFragment();

        //Act
        sectionByCategoryListFragment.showOnlyErrorView();

        //Assert
        assertNull(sectionByCategoryListFragment.mActivityNavigation);
    }

    @Test(expected = java.lang.NullPointerException.class)
    public void givenClassWhenShowOnlyLoadingViewThenShowViews(){
        //Arrange
        SectionByCategoryListFragment sectionByCategoryListFragment = new SectionByCategoryListFragment();

        //Act
        sectionByCategoryListFragment.showOnlyLoadingView();

        //Assert
        assertNull(sectionByCategoryListFragment.mActivityNavigation);
    }

    @Test
    public void givenLisItemWhenOnClickListItemThenPresenterOnClick(){
        //Arrange
        SectionByCategoryListFragment sectionByCategoryListFragment = new SectionByCategoryListFragment();
        sectionByCategoryListFragment.mPresenter = presenter;

        //Act
        sectionByCategoryListFragment.onClickListItem(listItem);

        //Assert
        assertNotNull(sectionByCategoryListFragment.mPresenter);
    }

    @Test
    public void givenClassWhenOnClickSearchThenOnClickSearch(){
        //Arrange
        SectionByCategoryListFragment sectionByCategoryListFragment = new SectionByCategoryListFragment();
        sectionByCategoryListFragment.mPresenter = presenter;

        //Act
        sectionByCategoryListFragment.onClickSearch();

        //Assert
        assertNotNull(sectionByCategoryListFragment.mPresenter);
    }
}
