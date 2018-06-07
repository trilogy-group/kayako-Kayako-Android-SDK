package com.kayako.sdk.android.k5.helpcenter.sectionbycategorypage;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentHostCallback;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.spinnerlist.SpinnerItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        SectionByCategoryContainerFactory.class,
        SectionByCategoryContainerFragment.class,
        FragmentHostCallback.class
})
public class SectionByCategoryContainerFragmentTest {
    private static final String M_HOST = "mHost";
    private static final String TITLE = "title";
    private static final int START = 1;
    private static final long FIRST = 1L;

    @Mock
    private Bundle bundle;

    @Mock
    private LayoutInflater layoutInflater;

    @Mock
    private ViewGroup viewGroup;

    @Mock
    private View view;

    @Mock
    private SectionByCategoryContainerContract.Presenter presenter;

    @Mock
    private FragmentHostCallback fragmentHostCallback;

    @Mock
    private Handler handler;

    @Mock
    private AppCompatActivity appCompatActivity;

    @Mock
    private Context context;

    @Mock
    private ActionBar actionBar;

    @Mock
    private Menu menu;

    @Mock
    private MenuInflater menuInflater;

    @Mock
    private MenuItem menuItem;

    @Mock
    private AdapterView adapterView;

    @Mock
    private SpinnerItem spinnerItem;

    @Mock
    private Toolbar toolbar;

    @Mock
    private Spinner spinner;

    @Mock
    private TextView textView;

    @Test
    public void givenClassWhenNewInstanceThenReturnNewInstance(){
        //Act
        Fragment returnedValue = SectionByCategoryContainerFragment.newInstance();

        //Assert
        assertNotNull(returnedValue);
    }

    @Test
    public void givenBundleWhenOnCreateThenSetPresenter(){
        //Arrange
        SectionByCategoryContainerFragment sectionByCategoryContainerFragment =
                new SectionByCategoryContainerFragment();
        mockStatic(SectionByCategoryContainerFactory.class);
        when(SectionByCategoryContainerFactory.getPresenter(sectionByCategoryContainerFragment))
                .thenReturn(presenter);

        //Act
        sectionByCategoryContainerFragment.onCreate(bundle);

        //Assert
        assertNotNull(sectionByCategoryContainerFragment.mPresenter);
    }

    @Test
    public void givenLayoutViewBundleWhenOnCreateViewThenReturnView(){
        //Arrange
        SectionByCategoryContainerFragment sectionByCategoryContainerFragmentSpy =
               spy(new SectionByCategoryContainerFragment());
        when(layoutInflater.inflate(R.layout.ko__fragment_help_center, viewGroup, Boolean.FALSE)).thenReturn(view);
        when(sectionByCategoryContainerFragmentSpy.getActivity()).thenReturn(appCompatActivity);
        when(appCompatActivity.getSupportActionBar()).thenReturn(actionBar);

        //Act
        View returnedValue = sectionByCategoryContainerFragmentSpy.onCreateView(layoutInflater, viewGroup, bundle);

        //Assert
        assertEquals(view, returnedValue);
    }

    @Test
    public void givenViewBundleWhenOnViewCreatedThenCommit() throws Exception{
        //Arrange
        SectionByCategoryContainerFragment sectionByCategoryContainerFragment =
                new SectionByCategoryContainerFragment();
        fragmentHostCallback = new FragmentHostCallback(context, handler, START) {
            @Nullable
            @Override
            public Object onGetHost() {
                return null;
            }
        };
        Field f = sectionByCategoryContainerFragment.getClass().getSuperclass().getSuperclass()
                .getDeclaredField(M_HOST);
        f.setAccessible(true);
        f.set(sectionByCategoryContainerFragment, fragmentHostCallback);

        //Act
        sectionByCategoryContainerFragment.onViewCreated(view, bundle);

        //Assert
        assertNull(sectionByCategoryContainerFragment.mPresenter);
    }

    @Test
    public void givenBundleWhenOnActivityCreateThenInitPage(){
        //Arrange
        SectionByCategoryContainerFragment sectionByCategoryContainerFragment =
                new SectionByCategoryContainerFragment();
        sectionByCategoryContainerFragment.mPresenter = presenter;

        //Act
        sectionByCategoryContainerFragment.onActivityCreated(bundle);

        //Assert
        assertNotNull(sectionByCategoryContainerFragment.mPresenter);
    }

    @Test
    public void givenMenuInflaterWhenOnCreateOptionsMenuThenRefresh(){
        //Arrange
        SectionByCategoryContainerFragment sectionByCategoryContainerFragment =
                new SectionByCategoryContainerFragment();
        when(menu.findItem(R.id.ko__action_search)).thenReturn(menuItem);

        //Act
        sectionByCategoryContainerFragment.onCreateOptionsMenu(menu, menuInflater);

        //Assert
        assertNull(sectionByCategoryContainerFragment.mPresenter);
    }

    @Test
    public void givenClassWhenOnDestroyThenOnDestroyView(){
        //Arrange
        SectionByCategoryContainerFragment sectionByCategoryContainerFragment =
                new SectionByCategoryContainerFragment();

        //Act
        sectionByCategoryContainerFragment.onDestroyView();

        //Assert
        assertNull(sectionByCategoryContainerFragment.mPresenter);
    }

    @Test
    public void givenAdapterViewIntLongWhenOnItemSelectedThenItemSelected(){
        //Arrange
        SectionByCategoryContainerFragment sectionByCategoryContainerFragment =
                new SectionByCategoryContainerFragment();
        sectionByCategoryContainerFragment.mPresenter = presenter;
        when(adapterView.getSelectedItem()).thenReturn(spinnerItem);

        //Act
        sectionByCategoryContainerFragment.onItemSelected(adapterView, view, START, FIRST);

        //Assert
        verify(presenter).onSpinnerItemSelected(spinnerItem);
    }

    @Test
    public void givenListIntWhenSetToolbarSpinnerThenSetListener(){
        //Arrange
        SectionByCategoryContainerFragment sectionByCategoryContainerFragmentSpy =
                spy(new SectionByCategoryContainerFragment());
        when(layoutInflater.inflate(R.layout.ko__fragment_help_center, viewGroup, Boolean.FALSE))
                .thenReturn(view);
        when(sectionByCategoryContainerFragmentSpy.getActivity()).thenReturn(appCompatActivity);
        when(appCompatActivity.getSupportActionBar()).thenReturn(actionBar);
        when(view.findViewById(R.id.ko__toolbar)).thenReturn(toolbar);
        when(toolbar.findViewById(R.id.ko__toolbar_spinner)).thenReturn(spinner);
        sectionByCategoryContainerFragmentSpy.onCreateView(layoutInflater, viewGroup, bundle);
        List<SpinnerItem> listLocal = new ArrayList<>();
        listLocal.add(spinnerItem);

        //Act
        sectionByCategoryContainerFragmentSpy.setToolbarSpinner(listLocal, START);

        //Assert
        verify(spinner).setOnItemSelectedListener(sectionByCategoryContainerFragmentSpy);
    }

    @Test
    public void givenClassWhenShowToolbarSpinnerThenShowToolbar(){
        //Arrange
        SectionByCategoryContainerFragment sectionByCategoryContainerFragmentSpy =
                spy(new SectionByCategoryContainerFragment());
        when(layoutInflater.inflate(R.layout.ko__fragment_help_center, viewGroup, Boolean.FALSE))
                .thenReturn(view);
        when(sectionByCategoryContainerFragmentSpy.getActivity()).thenReturn(appCompatActivity);
        when(appCompatActivity.getSupportActionBar()).thenReturn(actionBar);
        when(view.findViewById(R.id.ko__toolbar)).thenReturn(toolbar);
        when(toolbar.findViewById(R.id.ko__toolbar_spinner)).thenReturn(spinner);
        when(toolbar.findViewById(R.id.ko__toolbar_spinner)).thenReturn(view);
        sectionByCategoryContainerFragmentSpy.onCreateView(layoutInflater, viewGroup, bundle);

        //Act
        sectionByCategoryContainerFragmentSpy.showToolbarSpinner();

        //Assert
        verify(toolbar).findViewById(R.id.ko__toolbar_spinner);
    }

    @Test
    public void givenClassWhenHideToolbarSpinnerThenHideToolbar(){
        //Arrange
        SectionByCategoryContainerFragment sectionByCategoryContainerFragmentSpy =
                spy(new SectionByCategoryContainerFragment());
        when(layoutInflater.inflate(R.layout.ko__fragment_help_center, viewGroup, Boolean.FALSE))
                .thenReturn(view);
        when(sectionByCategoryContainerFragmentSpy.getActivity()).thenReturn(appCompatActivity);
        when(appCompatActivity.getSupportActionBar()).thenReturn(actionBar);
        when(view.findViewById(R.id.ko__toolbar)).thenReturn(toolbar);
        when(toolbar.findViewById(R.id.ko__toolbar_spinner)).thenReturn(spinner);
        when(toolbar.findViewById(R.id.ko__toolbar_spinner)).thenReturn(view);
        sectionByCategoryContainerFragmentSpy.onCreateView(layoutInflater, viewGroup, bundle);

        //Act
        sectionByCategoryContainerFragmentSpy.hideToolbarSpinner();

        //Assert
        verify(toolbar).findViewById(R.id.ko__toolbar_spinner);
    }

    @Test
    public void givenClassWhenShowToolbarTitleThenShowTitle(){
        //Arrange
        SectionByCategoryContainerFragment sectionByCategoryContainerFragmentSpy =
                spy(new SectionByCategoryContainerFragment());
        when(layoutInflater.inflate(R.layout.ko__fragment_help_center, viewGroup, Boolean.FALSE))
                .thenReturn(view);
        when(sectionByCategoryContainerFragmentSpy.getActivity()).thenReturn(appCompatActivity);
        when(appCompatActivity.getSupportActionBar()).thenReturn(actionBar);
        when(view.findViewById(R.id.ko__toolbar)).thenReturn(toolbar);
        when(toolbar.findViewById(R.id.ko__toolbar_spinner)).thenReturn(spinner);
        when(toolbar.findViewById(R.id.ko__toolbar_title)).thenReturn(view);
        sectionByCategoryContainerFragmentSpy.onCreateView(layoutInflater, viewGroup, bundle);

        //Act
        sectionByCategoryContainerFragmentSpy.showToolbarTitle();

        //Assert
        verify(toolbar).findViewById(R.id.ko__toolbar_title);
    }

    @Test
    public void givenClassWhenHideToolbarTitleThenHideTitle(){
        //Arrange
        SectionByCategoryContainerFragment sectionByCategoryContainerFragmentSpy =
                spy(new SectionByCategoryContainerFragment());
        when(layoutInflater.inflate(R.layout.ko__fragment_help_center, viewGroup, Boolean.FALSE))
                .thenReturn(view);
        when(sectionByCategoryContainerFragmentSpy.getActivity()).thenReturn(appCompatActivity);
        when(appCompatActivity.getSupportActionBar()).thenReturn(actionBar);
        when(view.findViewById(R.id.ko__toolbar)).thenReturn(toolbar);
        when(toolbar.findViewById(R.id.ko__toolbar_spinner)).thenReturn(spinner);
        when(toolbar.findViewById(R.id.ko__toolbar_title)).thenReturn(view);
        sectionByCategoryContainerFragmentSpy.onCreateView(layoutInflater, viewGroup, bundle);

        //Act
        sectionByCategoryContainerFragmentSpy.hideToolbarTitle();

        //Assert
        verify(toolbar).findViewById(R.id.ko__toolbar_title);
    }

    @Test
    public void givenStringWhenSetToolbarTitleThenSetTitle(){
        //Arrange
        SectionByCategoryContainerFragment sectionByCategoryContainerFragmentSpy =
                spy(new SectionByCategoryContainerFragment());
        when(layoutInflater.inflate(R.layout.ko__fragment_help_center, viewGroup, Boolean.FALSE))
                .thenReturn(view);
        when(sectionByCategoryContainerFragmentSpy.getActivity()).thenReturn(appCompatActivity);
        when(appCompatActivity.getSupportActionBar()).thenReturn(actionBar);
        when(view.findViewById(R.id.ko__toolbar)).thenReturn(toolbar);
        when(toolbar.findViewById(R.id.ko__toolbar_spinner)).thenReturn(spinner);
        when(toolbar.findViewById(R.id.ko__toolbar_title)).thenReturn(textView);
        sectionByCategoryContainerFragmentSpy.onCreateView(layoutInflater, viewGroup, bundle);

        //Act
        sectionByCategoryContainerFragmentSpy.setToolbarTitle(TITLE);

        //Assert
        verify(toolbar).findViewById(R.id.ko__toolbar_title);
    }

    @Test
    public void givenClassWhenReloadSectionsByCategoryThenCommit() throws Exception {
        //Arrange
        SectionByCategoryContainerFragment sectionByCategoryContainerFragment =
                new SectionByCategoryContainerFragment();
        fragmentHostCallback = new FragmentHostCallback(context, handler, START) {
            @Nullable
            @Override
            public Object onGetHost() {
                return null;
            }
        };
        Field f = sectionByCategoryContainerFragment.getClass().getSuperclass().getSuperclass()
                .getDeclaredField(M_HOST);
        f.setAccessible(true);
        f.set(sectionByCategoryContainerFragment, fragmentHostCallback);

        //Act
        sectionByCategoryContainerFragment.reloadSectionsByCategory();

        //Assert
        assertNull(sectionByCategoryContainerFragment.mPresenter);
    }
}
