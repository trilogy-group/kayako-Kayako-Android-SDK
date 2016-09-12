package com.kayako.sdk.android.k5.articlepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.fragments.BaseStateFragment;
import com.kayako.sdk.android.k5.common.view.CropCircleTransformation;
import com.kayako.sdk.helpcenter.articles.Article;
import com.kayako.sdk.helpcenter.category.Category;
import com.kayako.sdk.helpcenter.section.Section;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ArticleFragment extends BaseStateFragment {

    public static final String ARG_ARTICLE = "article";
    private View mRoot;

    private TextView articleTitle;
    private TextView articleDirectoryPath;
    private WebView articleContent;
    private ImageView authorAvatar;
    private TextView authorName;

    public static ArticleFragment newInstance(Article article) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_ARTICLE, article);
        ArticleFragment articleFragment = new ArticleFragment();
        articleFragment.setArguments(bundle);
        return articleFragment;
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

        articleTitle = (TextView) mRoot.findViewById(R.id.ko__article_title);
        articleTitle.setText(article.getTitle());

        Section section = article.getSection();
        Category category = section.getCategory();
        articleDirectoryPath = (TextView) mRoot.findViewById(R.id.ko__article_directory);
        articleDirectoryPath.setText(category.getTitle() + " > " + section.getTitle());

        articleContent = (WebView) mRoot.findViewById(R.id.ko__article_webview);
        articleContent.loadData(article.getContents(), "text/html; charset=utf-8", "UTF-8");
        articleContent.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        authorName = (TextView) mRoot.findViewById(R.id.ko__article_author_name);
        authorName.setText(article.getAuthor().getFullName());

        // Show Image
        authorAvatar = (ImageView) mRoot.findViewById(R.id.ko__article_author_avatar); // TODO: Glide
        Glide.with(getContext())
                .load(article.getAuthor().getAvatarUrl())
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .placeholder(R.color.ko__dark_gray_image_background)
                .into(authorAvatar);

    }
}
