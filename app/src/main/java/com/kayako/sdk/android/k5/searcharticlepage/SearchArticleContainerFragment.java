package com.kayako.sdk.android.k5.searcharticlepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.utils.ViewUtils;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SearchArticleContainerFragment extends Fragment implements SearchArticleContainerContract.View {

    private View mRoot;
    private Toolbar mToolbar;
    private EditText mSearchEditText;
    private SearchArticleFragment mSearchArticleResult;
    private SearchArticleContainerContract.Presenter mPresenter;

    public static Fragment newInstance() {
        return new SearchArticleContainerFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        mPresenter = SearchArticleContainerFactory.getPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.ko__fragment_search, null);
        mSearchArticleResult = (SearchArticleFragment) getChildFragmentManager().findFragmentById(R.id.container_search_results);
        setUpToolbar();
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setUpToolbar() {
        mToolbar = (Toolbar) mRoot.findViewById(R.id.ko__search_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(null);

        mSearchEditText = (EditText) mToolbar.findViewById(R.id.search_edittext);
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = mSearchEditText.getText().toString();
                mPresenter.onTextEntered(query);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_GO || event.equals(KeyEvent.KEYCODE_ENTER)) {
                    String query = mSearchEditText.getText().toString();
                    mPresenter.onEnterPressed(query);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return false;
    }

    @Override
    public void showLessCharactersTypedErrorMessage() {
        ViewUtils.showToastMessage(mRoot.getContext(), getString(R.string.ko__error_type_at_least_three_characters_to_search), Toast.LENGTH_LONG);
    }

    @Override
    public void showSearchResults(String query) {
        mSearchArticleResult.showSearchResults(query);
    }

    @Override
    public void clearSearchResults() {
        mSearchArticleResult.clearSearchResults();
    }
}
