package com.kayako.sdk.android.k5.articlepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.fragments.BaseStateFragment;
import com.kayako.sdk.android.k5.common.view.CropCircleTransformation;
import com.kayako.sdk.helpcenter.articles.Article;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ArticleFragment extends BaseStateFragment implements ArticlePageContract.View {

    public static final String ARG_ARTICLE = "article";
    private View mRoot;
    private ArticlePageContract.Presenter mPresenter;

    public static ArticleFragment newInstance(Article article) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_ARTICLE, article);
        ArticleFragment articleFragment = new ArticleFragment();
        articleFragment.setArguments(bundle);
        return articleFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = ArticleFactory.getPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.ko__fragment_article_content, null);
        super.initStateViews(mRoot);
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Article article = (Article) getArguments().getSerializable(ARG_ARTICLE);
        mPresenter.initPage(article);
    }

    @Override
    public void setAuthorName(String name) {
        ((TextView) mRoot.findViewById(R.id.ko__article_author_name)).setText(name);
    }

    @Override
    public void setAuthorAvatar(String avatarUrl) {
        ImageView authorAvatar = (ImageView) mRoot.findViewById(R.id.ko__article_author_avatar); // TODO: Glide
        Glide.with(getContext())
                .load(avatarUrl)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .placeholder(R.color.ko__dark_gray_image_background)
                .into(authorAvatar);
    }

    @Override
    public void setArticleTitle(String title) {
        ((TextView) mRoot.findViewById(R.id.ko__article_title)).setText(title);
    }

    @Override
    public void setArticleDirectoryPath(String path) {
        ((TextView) mRoot.findViewById(R.id.ko__article_directory)).setText(path);
    }

    @Override
    public void setArticleContent(String htmlContent) {
        // TODO: Add test to ensure that if there are large tables, horizontal scroll is enabled. Else, it's only vertical scroll.
        // The webview is formatted with the CSS file available in assets folder
        WebView articleContent = (WebView) mRoot.findViewById(R.id.ko__article_web_view);
        String styledHtmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"kayako-style.css\" />" + htmlContent;
        articleContent.loadDataWithBaseURL("file:///android_asset/", styledHtmlData, "text/html; charset=utf-8", "UTF-8", null);
        articleContent.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        articleContent.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO: -Use WebChromeClient instead? - onPageFinished will not be called until all of the assets (css/js/images) have finished loading for that page.
                super.onPageFinished(view, url);
                mPresenter.onContentLoaded();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                // TODO Show Error Page?
            }
        });
    }


    @Override
    public void hideContentScrollbarsWhileAllowingScroll() {
        WebView articleContent = (WebView) mRoot.findViewById(R.id.ko__article_web_view);
        articleContent.setVerticalScrollBarEnabled(false);
        articleContent.setHorizontalScrollBarEnabled(false);
    }

    @Override
    public void showArticleContent() {
        mRoot.findViewById(R.id.ko__article_web_view).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideArticleContent() {
        mRoot.findViewById(R.id.ko__article_web_view).setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        super.showLoadingView();
    }

    @Override
    public void hideLoading() {
        super.hideLoadingView();
    }
}
