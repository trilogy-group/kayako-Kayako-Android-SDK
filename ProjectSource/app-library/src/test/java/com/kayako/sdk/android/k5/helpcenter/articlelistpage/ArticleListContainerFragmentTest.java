package com.kayako.sdk.android.k5.helpcenter.articlelistpage;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentHostCallback;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.helpcenter.section.Section;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        ArticleListContainerFactory.class,
        ArticleListContainerFragment.class,
        Bundle.class,
        FragmentHostCallback.class,
        ArticleListFragment.class,
        Fragment.class
})
public class ArticleListContainerFragmentTest {
    private static final String ARG_RESOURCE = "resource";
    private static final String M_HOST = "mHost";
    private static final String START_ACTIVITY = "startActivity";
    private static final String M_ARGUMENTS = "mArguments";
    private static final String TO_STRING = "toString";
    private static final int START = 1;

    @Mock
    private Bundle bundle;

    @Mock
    private ArticleListContainerContract.Presenter presenter;

    @Mock
    private Section section;

    @Mock
    private LayoutInflater layoutInflater;

    @Mock
    private ViewGroup viewGroup;

    @Mock
    private View view;

    @Mock
    private AppCompatActivity appCompatActivity;

    @Mock
    private Context context;

    @Mock
    private ActionBar actionBar;

    @Mock
    private Handler handler;

    @Mock
    private FragmentHostCallback fragmentHostCallback;

    @Mock
    private Toolbar toolbar;

    @Mock
    private ArticleListFragment articleListFragment;

    @Mock
    private Menu menu;

    @Mock
    private MenuInflater menuInflater;

    @Mock
    private MenuItem menuItem;

    @Test
    public void givenClassWhenNewInstanceThenReturnNewInstance() throws Exception {
        //Arrange
        suppress(method(Bundle.class, TO_STRING));
        whenNew(Bundle.class).withNoArguments().thenReturn(bundle);
        doNothing().when(bundle).putSerializable(ARG_RESOURCE, section);

        //Act
        ArticleListContainerFragment returnedValue = ArticleListContainerFragment
                .newInstance(section);

        //Assert
        assertNotNull(returnedValue);
    }

    @Test
    public void givenBundleWhenOnCreateThenSetPresenter(){
        //Arrange
        ArticleListContainerFragment articleListContainerFragment =
                new ArticleListContainerFragment();
        mockStatic(ArticleListContainerFactory.class);
        when(ArticleListContainerFactory.getPresenter(articleListContainerFragment))
                .thenReturn(presenter);

        //Act
        articleListContainerFragment.onCreate(bundle);

        //Assert
        verifyStatic();
        ArticleListContainerFactory.getPresenter(articleListContainerFragment);
    }

    @Test
    public void givenLayoutViewBundleWhenOnCreateViewThenReturnView(){
        //Arrange
        ArticleListContainerFragment articleListContainerFragmentSpy =
                spy(new ArticleListContainerFragment());
        when(layoutInflater.inflate(R.layout.ko__fragment_default, viewGroup, Boolean.FALSE))
                .thenReturn(view);
        when(articleListContainerFragmentSpy.getActivity()).thenReturn(appCompatActivity);
        when(appCompatActivity.getSupportActionBar()).thenReturn(actionBar);
        when(view.findViewById(R.id.ko__toolbar)).thenReturn(toolbar);

        //Act
        View returnedValue = articleListContainerFragmentSpy
                .onCreateView(layoutInflater, viewGroup, bundle);

        //Assert
        assertEquals(view, returnedValue);
    }

    @Test
    public void givenViewBundleWhenOnViewCreatedThenCommit() throws Exception{
        //Arrange
        ArticleListContainerFragment articleListContainerFragment =
                new ArticleListContainerFragment();
        fragmentHostCallback = new FragmentHostCallback(context, handler, START) {
            @Nullable
            @Override
            public Object onGetHost() {
                return null;
            }
        };
        Field f = articleListContainerFragment.getClass().getSuperclass().getSuperclass()
                .getDeclaredField(M_HOST);
        f.setAccessible(true);
        f.set(articleListContainerFragment, fragmentHostCallback);
        when(view.findViewById(R.id.ko__toolbar)).thenReturn(toolbar);
        f = articleListContainerFragment.getClass().getSuperclass().getSuperclass()
                .getDeclaredField(M_ARGUMENTS);
        f.setAccessible(true);
        f.set(articleListContainerFragment, bundle);
        mockStatic(ArticleListFragment.class);
        when(ArticleListFragment.newInstance(null)).thenReturn(articleListFragment);

        //Act
        articleListContainerFragment.onViewCreated(view, bundle);

        //Assert
        assertNotNull(f.get(articleListContainerFragment));
    }

    @Test
    public void givenMenuInflaterWhenOnCreateOptionsMenuThenRefresh(){
        //Arrange
        ArticleListContainerFragment articleListContainerFragment =
               spy(new ArticleListContainerFragment());
        when(menu.findItem(R.id.ko__action_search)).thenReturn(menuItem);

        //Act
        articleListContainerFragment.onCreateOptionsMenu(menu, menuInflater);

        //Assert
        verify(articleListContainerFragment).onCreateOptionsMenu(menu, menuInflater);
    }

    @Test
    public void givenMenuInflaterWhenOpenSearchPageThenRefresh(){
        //Arrange
        ArticleListContainerFragment articleListContainerFragment =
                spy(new ArticleListContainerFragment());
        suppress(method(Fragment.class, START_ACTIVITY, Intent.class));

        //Act
        articleListContainerFragment.openSearchPage();

        //Assert
        verify(articleListContainerFragment).openSearchPage();
    }
}
