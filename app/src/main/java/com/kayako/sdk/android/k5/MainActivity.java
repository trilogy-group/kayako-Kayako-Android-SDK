package com.kayako.sdk.android.k5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.kayako.sdk.helpcenter.HelpCenter;
import com.kayako.sdk.helpcenter.base.KayakoError;
import com.kayako.sdk.helpcenter.category.Category;
import com.kayako.sdk.helpcenter.category.CategoryManager;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final HelpCenter mHelpCenter = new HelpCenter("http://support.kayako.com", new Locale("en", "us"));
        Toast.makeText(MainActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
        mHelpCenter.getCategories(new CategoryManager.Callback() {
            @Override
            public void onSuccess(final List<Category> list) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Loaded Categories = " + list.size(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(final KayakoError kayakoError) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "FAILED! = " + kayakoError.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
