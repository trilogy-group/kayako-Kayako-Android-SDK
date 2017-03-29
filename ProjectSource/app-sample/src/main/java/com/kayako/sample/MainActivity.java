package com.kayako.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kayako.sample.store.Store;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.core.MessengerStylePref;
import com.kayako.sdk.android.k5.messenger.style.BackgroundFactory;
import com.kayako.sdk.android.k5.messenger.style.ForegroundFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Locale selectedLocale = Locale.US;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = (EditText) findViewById(R.id.edittext_url);
        editText.setText(Store.getInstance().getHelpCenterUrl());

        Spinner spinnerLocale = (Spinner) findViewById(R.id.spinner_locale);
        spinnerLocale.setAdapter(
                new ArrayAdapter<Locale>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        Locale.getAvailableLocales()
                )
        );
        spinnerLocale.setSelection(getPositionOfLocale(Locale.getAvailableLocales(), selectedLocale));
        spinnerLocale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLocale = Locale.getAvailableLocales()[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button button = (Button) findViewById(R.id.button_helpcenter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = editText.getText().toString();
                if (validateUrl(url)) {
                    Store.getInstance().setHelpCenterUrl(url);
                    Kayako.getInstance().openHelpCenter(MainActivity.this, url, selectedLocale); // Command to open Help Center
                }
            }
        });


        final Spinner spinnerMessengerBackground = (Spinner) findViewById(R.id.spinner_messenger_background);
        spinnerMessengerBackground.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                getBackgroundOptionsAsList()
        ));

        final Spinner spinnerMessengerForeground = (Spinner) findViewById(R.id.spinner_messenger_foreground);
        spinnerMessengerForeground.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                getForegroundOptionsAsList()
        ));

        final EditText editTextMessengerPrimaryColor = (EditText) findViewById(R.id.edittext_primary_color);

        Button buttonMessenger = (Button) findViewById(R.id.button_messenger);
        buttonMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editText.getText().toString();

                if (!validateUrl(url)) {
                    return;
                }

                // TODO: TESTING - should not be hardcoded!
                Store.getInstance().setHelpCenterUrl("https://kayako-mobile-testing.kayako.com");
                MessengerPref.getInstance().setFingerprintId("d0bc691c-62c5-468c-a4a5-3b096684dc96");

                // TODO: Add support for ColorRes id as selection primary color. call getString(R.color.--) to convert to hex string
                String primaryColor = editTextMessengerPrimaryColor.getText().toString();
                if (!validateColor(primaryColor)) {
                    return;
                }
                MessengerStylePref.getInstance().setPrimaryColor(primaryColor);

                // TODO: Separate Messenger from HelpCenter Prefs - both are independent of each other!
                // TODO: TESTING - Don't use MessengerStylePref - instead, use a builder pattern
                MessengerStylePref.getInstance().setBackground(
                        BackgroundFactory.getBackground(
                                Arrays.asList(BackgroundFactory.BackgroundOption.values()).get(spinnerMessengerBackground.getSelectedItemPosition())
                        )
                );

                MessengerStylePref.getInstance().setForeground(
                        ForegroundFactory.getForeground(
                                Arrays.asList(ForegroundFactory.ForegroundOption.values()).get(spinnerMessengerForeground.getSelectedItemPosition())
                        )
                );

                Kayako.getInstance().openMessenger(MainActivity.this, url, Locale.US); // Command to open Messenger
            }
        });

        Button buttonClear = (Button) findViewById(R.id.button_clear_all);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kayako.clearCache(); // Useful for testing situations when a new user joins
                Toast.makeText(getApplicationContext(), R.string.cleared_all, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean validateColor(String hexColor) {
        try {
            Color.parseColor(hexColor);
            return true;
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Invalid Hex Color! Valid example: #F1703F", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean validateUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(MainActivity.this, "URL can not be blank", Toast.LENGTH_LONG).show();
            return false;
        } else if (!URLUtil.isValidUrl(url)) {
            Toast.makeText(MainActivity.this, "Please enter a valid URL", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private int getPositionOfLocale(Locale[] locales, Locale locale) {
        int position = 0;
        for (Locale item : locales) {
            if (item.equals(locale)) {
                return position;
            }
            position++;
        }

        return position;
    }

    private List<String> getBackgroundOptionsAsList() {
        List<String> enums = new ArrayList<String>();
        for (BackgroundFactory.BackgroundOption option : BackgroundFactory.BackgroundOption.values()) {
            enums.add(option.name());
        }
        return enums;
    }

    private List<String> getForegroundOptionsAsList() {
        List<String> enums = new ArrayList<String>();
        for (ForegroundFactory.ForegroundOption option : ForegroundFactory.ForegroundOption.values()) {
            enums.add(option.name());
        }
        return enums;
    }

}
