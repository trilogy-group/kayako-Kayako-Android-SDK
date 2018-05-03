package com.kayako.sdk.android.k5.helpcenter.articlepage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.fragments.BaseContainerFragment;
import com.kayako.sdk.helpcenter.articles.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        ArticleContainerFragment.class,
        ArticleContainerFactory.class,
        Bundle.class,
        Fragment.class,
        ArticleFragment.class,
        BaseContainerFragment.class
})
public class ArticleContainerFragmentTest {
    private static final String ARG_ARTICLE = "article";
    private ArticleContainerFragment articleContainerFragment;

    @Mock
    private Article article;

    @Mock
    private ArticleContainerContract.Presenter presenter;

    @Mock
    private Bundle bundle;

    @Mock
    private View view;

    @Mock
    private LayoutInflater layoutInflater;

    @Mock
    private ViewGroup viewGroup;

    @Mock
    private Toolbar toolbar;

    @Mock
    private AppCompatActivity fragmentActivity;

    @Mock
    private ActionBar actionBar;

    @Mock
    private ArticleFragment articleFragment;

    @Mock
    private FragmentManager fragmentManager;

    @Mock
    private FragmentTransaction fragmentTransaction;

    @Mock
    private Menu menu;

    @Mock
    private MenuInflater menuInflater;

    @Mock
    private MenuItem menuItem;

    @Test
    public void givenArticleWhenNewInstanceThenReturnFragment()
            throws Exception {
        //Arrange
        mockStatic(ArticleContainerFragment.class);
        whenNew(ArticleContainerFragment.class).withAnyArguments()
                .thenReturn(articleContainerFragment);
        whenNew(Bundle.class).withAnyArguments().thenReturn(bundle);

        //Act
        Fragment returnedValue = ArticleContainerFragment
                .newInstance(article);

        //Assert
        assertEquals(articleContainerFragment, returnedValue);
    }

    @Test
    public void givenBundleWhenOnCreateThenGetPresenter() {
        //Arrange
        mockStatic(ArticleContainerFactory.class);
        when(ArticleContainerFactory.getPresenter(articleContainerFragment))
                .thenReturn(presenter);

        //Act
        articleContainerFragment = new ArticleContainerFragment();
        articleContainerFragment.onCreate(bundle);

        //Assert
        verifyStatic();
        ArticleContainerFactory.getPresenter(articleContainerFragment);
    }

    @Test
    public void givenLayoutViewGroupBundleWhenOnCreateViewThenReturnView() {
        //Arrange
        when(layoutInflater
                .inflate(R.layout.ko__fragment_default, viewGroup, Boolean.FALSE))
                .thenReturn(view);
        ArticleContainerFragment articleContainerFragmentSpy =
                spy(new ArticleContainerFragment());
        when(articleContainerFragmentSpy.getActivity()).thenReturn(fragmentActivity);
        articleContainerFragmentSpy.setArguments(bundle);
        when(bundle.getSerializable(ARG_ARTICLE)).thenReturn(article);
        when(view.findViewById(R.id.ko__toolbar)).thenReturn(toolbar);
        when(fragmentActivity.getSupportActionBar()).thenReturn(actionBar);

        //Act
        View returnedValue = articleContainerFragmentSpy
                .onCreateView(layoutInflater, viewGroup, bundle);

        //Assert
        assertEquals(view, returnedValue);
    }

    @Test
    public void givenViewBundleWhenOnViewCreatedThenCommitArticleFragmentTransaction() {
        //Arrange
        ArticleContainerFragment articleContainerFragmentSpy =
                spy(new ArticleContainerFragment());
        articleContainerFragmentSpy.setArguments(bundle);
        when(bundle.getSerializable(ARG_ARTICLE)).thenReturn(article);
        mockStatic(ArticleFragment.class);
        when(ArticleFragment.newInstance(article)).thenReturn(articleFragment);
        when(articleContainerFragmentSpy.getChildFragmentManager())
                .thenReturn(fragmentManager);
        when(fragmentManager.beginTransaction()).thenReturn(fragmentTransaction);
        when(fragmentTransaction.replace(R.id.ko__container, articleFragment))
                .thenReturn(fragmentTransaction);

        //Act
        articleContainerFragmentSpy.onViewCreated(view, bundle);

        //Assert
        verify(fragmentTransaction).commit();
    }

    @Test
    public void givenMenuMenuInflaterWhenOnCreateOptionsMenuThenRefreshOptionsMenu() {
        //Arrange
        ArticleContainerFragment articleContainerFragmentSpy =
                spy(new ArticleContainerFragment());
        articleContainerFragmentSpy.onPrepareOptionsMenu(menu);
        when(menu.findItem(R.id.ko__action_search)).thenReturn(menuItem);

        //Act
        articleContainerFragmentSpy.onCreateOptionsMenu(menu, menuInflater);

        //Assert
        verify(articleContainerFragmentSpy).onCreateOptionsMenu(menu, menuInflater);
    }

    @Test
    public void givenActivityWhenOpenSearchActivityThenOpenSearchPage() {
        //Arrange
        ArticleContainerFragment articleContainerFragmentSpy =
                spy(new ArticleContainerFragment());
        suppress(method(BaseContainerFragment.class, "openSearchPage"));

        //Act
        articleContainerFragmentSpy.openSearchActivity();

        //Assert
        verify(articleContainerFragmentSpy).openSearchActivity();
    }
}
