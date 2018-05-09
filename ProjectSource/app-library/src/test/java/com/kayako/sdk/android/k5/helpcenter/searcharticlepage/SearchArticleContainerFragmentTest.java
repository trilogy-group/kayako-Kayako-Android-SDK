package com.kayako.sdk.android.k5.helpcenter.searcharticlepage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import com.kayako.sdk.android.k5.R;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@PrepareForTest(Fragment.class)
@RunWith(PowerMockRunner.class)
public class SearchArticleContainerFragmentTest {

    private static final CharSequence CHAR_SEQUENCE = "Hello";
    private static final int i = 0;
    private static final int i1 = 1;
    private static final int i2 = 2;
    private SearchArticleContainerFragment searchArticleContainerFragment;

    @Mock
    private View view;

    @Mock
    private Toolbar toolbar;

    @Mock
    private Bundle bundle;

    @Mock
    private LayoutInflater layoutInflater;

    @Mock
    private ViewGroup viewGroup;

    @Mock
    private AppCompatActivity compatActivity;

    @Mock
    private ActionBar actionBar;

    @Mock
    private EditText editText;

    @Mock
    private Editable editable;

    @Mock
    private SearchArticleContainerContract.Presenter presenter;

    @Mock
    private TextView textView;

    @Mock
    private KeyEvent keyEvent;

    @Mock
    private SearchArticleFragment searchArticleFragment;

    @Mock
    private MenuItem menuItem;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Before
    public void setUp() {
        searchArticleContainerFragment = (SearchArticleContainerFragment)SearchArticleContainerFragment.newInstance();
    }

    @Test
    public void onCreate() {
        //Act
        searchArticleContainerFragment.onCreate(bundle);
        final SearchArticleContainerContract.Presenter presenterLocal =
                Whitebox.getInternalState(searchArticleContainerFragment, "mPresenter");

        //Assert
        assertNotNull(presenterLocal);
    }

    @Test
    public void onCreateViewWhenAddTextChangedListener() throws NoSuchMethodException {
        //Arrange
        final int actionId = EditorInfo.IME_ACTION_SEARCH;
        onCreateViewSupport();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object arguments[] = invocationOnMock.getArguments();
                final TextWatcher textWatcher = (TextWatcher)arguments[0];
                textWatcher.beforeTextChanged(CHAR_SEQUENCE, i, i1, i2);
                textWatcher.onTextChanged(CHAR_SEQUENCE, i, i1, i2);
                textWatcher.afterTextChanged(editable);
                return null;
            }
        }).when(editText).addTextChangedListener(any(TextWatcher.class));

        //Act
        searchArticleContainerFragment.onCreateView(layoutInflater, viewGroup, bundle);

        //Assert
        verify(presenter).onTextEntered(stringArgumentCaptor.capture());
        assertEquals(CHAR_SEQUENCE.toString(), stringArgumentCaptor.getValue());
    }

    @Test
    public void onCreateViewWhenOnEditorActionListener() throws NoSuchMethodException {
        //Arrange
        final int actionId = EditorInfo.IME_ACTION_SEARCH;
        onCreateViewSupport();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object arguments[] = invocationOnMock.getArguments();
                final TextView.OnEditorActionListener onEditorActionListener =
                        (TextView.OnEditorActionListener)arguments[0];
                onEditorActionListener.onEditorAction(textView, actionId, keyEvent);
                return null;
            }
        }).when(editText).setOnEditorActionListener(any(TextView.OnEditorActionListener.class));

        //Act
        searchArticleContainerFragment.onCreateView(layoutInflater, viewGroup, bundle);

        //Assert
        verify(presenter).onEnterPressed(stringArgumentCaptor.capture());
        assertEquals(CHAR_SEQUENCE.toString(), stringArgumentCaptor.getValue());
    }

    @Test
    public void showSearchResults() {
        //Arrange
        final String query = "query_string";
        Whitebox.setInternalState(searchArticleContainerFragment, "mSearchArticleResult", searchArticleFragment);

        //Act
        searchArticleContainerFragment.showSearchResults(query);

        //Assert
        verify(searchArticleFragment).showSearchResults(stringArgumentCaptor.capture());
        assertEquals(query, stringArgumentCaptor.getValue());
    }

    @Test
    public void clearSearchResults() {
        //Arrange
        Whitebox.setInternalState(searchArticleContainerFragment, "mSearchArticleResult", searchArticleFragment);

        //Act
        searchArticleContainerFragment.clearSearchResults();

        //Assert
        final SearchArticleFragment searchArticleFragmentLocal =
                Whitebox.getInternalState(searchArticleContainerFragment, "mSearchArticleResult");
        assertEquals(searchArticleFragment, searchArticleFragmentLocal);
        verify(searchArticleFragment, times(1)).clearSearchResults();
    }

    @Test
    public void onOptionsItemSelected() throws NoSuchMethodException {
        //Arrange
        Method superGetActivity = Fragment.class.getMethod("getActivity");
        PowerMockito.replace(superGetActivity).with(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return compatActivity;
            }
        });
        when(menuItem.getItemId()).thenReturn(android.R.id.home);

        //Act
        final boolean flag = searchArticleContainerFragment.onOptionsItemSelected(menuItem);

        //Assert
        assertTrue(flag);
    }

    private void onCreateViewSupport() throws NoSuchMethodException {
        when(layoutInflater.inflate(R.layout.ko__fragment_search, viewGroup, false)).thenReturn(view);
        when(view.findViewById(R.id.ko__search_toolbar)).thenReturn(toolbar);
        Method superGetActivity = Fragment.class.getMethod("getActivity");
        PowerMockito.replace(superGetActivity).with(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return compatActivity;
            }
        });
        when(compatActivity.getSupportActionBar()).thenReturn(actionBar);
        when(toolbar.findViewById(R.id.ko__toolbar_search_edittext)).thenReturn(editText);
        when(editText.getText()).thenReturn(editable);
        when(editable.toString()).thenReturn(CHAR_SEQUENCE.toString());
        Whitebox.setInternalState(searchArticleContainerFragment, "mPresenter", presenter);
    }
}
