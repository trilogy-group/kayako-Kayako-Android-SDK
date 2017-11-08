package com.kayako.sample;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kayako.sample.store.Store;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.core.KayakoLogHelper;
import com.kayako.sdk.android.k5.core.MessengerBuilder;
import com.kayako.sdk.android.k5.messenger.style.BackgroundFactory;
import com.kayako.sdk.android.k5.messenger.style.ForegroundFactory;
import com.kayako.sdk.utils.LogUtils;

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

        showDebugLogs();

        final EditText editText = (EditText) findViewById(R.id.edittext_helpcenter_url);
        editText.setText(Store.getInstance().getHelpCenterUrl());

        final Spinner spinnerLocale = (Spinner) findViewById(R.id.spinner_locale);
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

        final Button button = (Button) findViewById(R.id.button_helpcenter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = editText.getText().toString();
                if (validateUrl(url)) {

                    url = url.trim(); // trim whitespaces
                    if (Store.getInstance().getHelpCenterUrl() != null
                            && !Store.getInstance().getHelpCenterUrl().equals(url)) {
                        Kayako.clearCache();
                    }

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
        spinnerMessengerForeground.setSelection(1); // select first option by default

        final EditText editTextMessengerUrl = (EditText) findViewById(R.id.edittext_messenger_url);
        if (Store.getInstance().getMessengerUrl() != null) {
            editTextMessengerUrl.setText(Store.getInstance().getMessengerUrl());
        }

        Button buttonMessenger = (Button) findViewById(R.id.button_messenger);
        buttonMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editTextBrandName = (EditText) findViewById(R.id.edittext_brand_name);
                String brandName = editTextBrandName.getText().toString();
                if (!validateBrandName(brandName)) {
                    return;
                }

                final EditText editTextTitle = (EditText) findViewById(R.id.edittext_title);
                String title = editTextTitle.getText().toString();
                if (!validateTitle(brandName)) {
                    return;
                }


                final EditText editTextDescription = (EditText) findViewById(R.id.edittext_description);
                String description = editTextDescription.getText().toString();
                if (!validateDescription(brandName)) {
                    return;
                }


                String url = editTextMessengerUrl.getText().toString();
                if (!validateUrl(url)) {
                    return;
                }

                url = url.trim(); // trim whitespaces
                if (Store.getInstance().getMessengerUrl() != null
                        && !Store.getInstance().getMessengerUrl().equals(url)) {
                    Kayako.clearCache();
                }

                final EditText editTextMessengerPrimaryColor = (EditText) findViewById(R.id.edittext_primary_color);
                String primaryColor = editTextMessengerPrimaryColor.getText().toString();
                if (!validateColor(primaryColor)) {
                    return;
                }

                MessengerBuilder builder = Kayako.getInstance()
                        .getMessenger()
                        .setUrl(url)
                        .setBrandName(brandName)
                        .setTitle(title)
                        .setDescription(description)
                        .setBackground(BackgroundFactory.getBackground(
                                Arrays.asList(BackgroundFactory.BackgroundOption.values()).get(spinnerMessengerBackground.getSelectedItemPosition())
                        ))
                        .setForeground(ForegroundFactory.getForeground(
                                Arrays.asList(ForegroundFactory.ForegroundOption.values()).get(spinnerMessengerForeground.getSelectedItemPosition())
                        ));

                builder.open(MainActivity.this);

                Store.getInstance().setMessengerUrl(url);
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

        hideKeyboard();
    }

    private void hideKeyboard() {
        try {
            // Check if no view has focus:
            AppCompatActivity activity = (AppCompatActivity) MainActivity.this;
            View view = activity.getCurrentFocus();
            if (view != null) {
                InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateBrandName(String brandName) {
        if (TextUtils.isEmpty(brandName)) {
            Toast.makeText(MainActivity.this, "Brand Name can not be blank", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(MainActivity.this, "Title can not be blank", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    private boolean validateDescription(String description) {
        if (TextUtils.isEmpty(description)) {
            Toast.makeText(MainActivity.this, "Description can not be blank", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    private boolean validateColor(String hexColor) {
        try {
            Color.parseColor(hexColor);
            return true;
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Invalid Hex Color! Valid example: #F1703F", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean validateUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(MainActivity.this, "URL can not be blank", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!URLUtil.isValidUrl(url)) {
            Toast.makeText(MainActivity.this, "Please enter a valid URL", Toast.LENGTH_SHORT).show();
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

    private void showDebugLogs() {
        // For debugging:
        LogUtils.setShowLogs(true);
        KayakoLogHelper.addLogListener(new KayakoLogHelper.PrintLogListener() {
            @Override
            public void printDebugLogs(String tag, String message) {
                Log.d(tag, message);
            }

            @Override
            public void printVerboseLogs(String tag, String message) {
                Log.v(tag, message);
            }

            @Override
            public void printErrorLogs(String tag, String message) {
                Log.e(tag, message);
            }

            @Override
            public void printStackTrace(String tag, Throwable e) {
                Log.e(tag, "printStackTrace:");
                e.printStackTrace();
            }

            @Override
            public void logPotentialCrash(String tag, Throwable e) {
                Log.e(tag, "logPotentialCrash:");
                e.printStackTrace();
            }
        });
    }
}
