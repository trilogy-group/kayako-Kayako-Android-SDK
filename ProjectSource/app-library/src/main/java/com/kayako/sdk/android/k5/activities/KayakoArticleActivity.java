package com.kayako.sdk.android.k5.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.articlepage.ArticleContainerFragment;
import com.kayako.sdk.helpcenter.articles.Article;

public class KayakoArticleActivity extends AppCompatActivity {

    private static String ARG_ARTICLE = "article";

    public static Intent getIntent(Context context, Article article) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_ARTICLE, article);

        Intent intent = new Intent(context, KayakoArticleActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ko__activity_article);

        Article article = (Article) getIntent().getExtras().getSerializable(ARG_ARTICLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.ko__container_article, ArticleContainerFragment.newInstance(article)).commit();
    }
}
