package sampleapp.myapplication;

import android.content.Context;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.messenger.style.ForegroundFactory;
import com.kayako.sdk.android.k5.messenger.style.type.SolidColor;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context context = MainActivity.this;
        final java.util.Locale locale = Locale.ENGLISH;
        final String helpCenterUrl = "http://support.kayako.com";

        findViewById(R.id.button_helpcenter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Help Center using the following line - Kayako
                Kayako.getInstance().openHelpCenter(context, helpCenterUrl, locale);
            }
        });

        findViewById(R.id.button_messenger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Messenger using the following lines - Kayako
                Kayako.getInstance().getMessenger()
                        .setUrl("https://kayako-mobile-testing.kayako.com")
                        .setBrandName("Brewfictus")
                        .setTitle("Howdy")
                        .setDescription("We make quality coffee at a price you can afford. How can we help?")
                        .setBackground(new SolidColor(R.color.colorPrimary, true))
                        .setForeground(ForegroundFactory.getForeground(ForegroundFactory.ForegroundOption.CONFETTI))
                        .open(MainActivity.this);
            }
        });

    }
}
