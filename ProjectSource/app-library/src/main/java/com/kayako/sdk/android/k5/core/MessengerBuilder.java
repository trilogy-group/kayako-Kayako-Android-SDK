package com.kayako.sdk.android.k5.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.webkit.URLUtil;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.activities.KayakoMessengerActivity;
import com.kayako.sdk.android.k5.messenger.style.BackgroundFactory;
import com.kayako.sdk.android.k5.messenger.style.ForegroundFactory;
import com.kayako.sdk.android.k5.messenger.style.type.Background;
import com.kayako.sdk.android.k5.messenger.style.type.Foreground;
import com.kayako.sdk.utils.FingerprintUtils;

import java.util.Locale;

public class MessengerBuilder {

    // Mandatory
    private String title;
    private String description;
    private String brandName;
    private String url;

    // Non-Null Fields
    private String fingerprintId;
    private Background background;
    private Foreground foreground;
    private String primaryHexColor;

    // Optional - Identity
    private String userEmail;

    public MessengerBuilder() {
    }

    public MessengerBuilder setBrandName(@StringRes int stringResId) {
        String brandName = getStringFromResourceId(stringResId);
        return setBrandName(brandName);
    }

    public MessengerBuilder setBrandName(String brandName) {
        assertValidString(brandName);
        this.brandName = brandName;
        return this;
    }

    public MessengerBuilder setTitle(@StringRes int stringResId) {
        String title = getStringFromResourceId(stringResId);
        return setTitle(title);
    }

    public MessengerBuilder setTitle(String title) {
        assertValidString(title);
        this.title = title;
        return this;
    }

    public MessengerBuilder setDescription(@StringRes int stringResId) {
        String description = getStringFromResourceId(stringResId);
        return setDescription(description);
    }

    public MessengerBuilder setDescription(String description) {
        assertValidString(description);
        this.description = description;
        return this;
    }

    public MessengerBuilder setUrl(String instanceUrl) {
        if (!URLUtil.isNetworkUrl(instanceUrl)) {
            throw new IllegalArgumentException("Invalid Network Url!");
        }
        this.url = instanceUrl;
        return this;
    }

    public MessengerBuilder setFingerprintId(String fingerprintId) {
        if (fingerprintId == null || fingerprintId.length() < 1) {
            throw new IllegalArgumentException("Invalid Fingerprint Id");
        }
        this.fingerprintId = fingerprintId;
        return this;
    }

    public MessengerBuilder setUserEmail(String userEmail) {
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            throw new IllegalArgumentException("Invalid Email Provided");
        }
        this.userEmail = userEmail;
        return this;
    }

    public MessengerBuilder setPrimaryColor(@ColorRes int colorResId) {
        String colorHex = "#" + Integer.toHexString(ContextCompat.getColor(Kayako.getApplicationContext(), colorResId));
        return setPrimaryColor(colorHex);
    }

    public MessengerBuilder setPrimaryColor(String hexColor) {
        try {
            Color.parseColor(hexColor); // If invalid color, will throw exception!
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Hex Color");
        }

        this.primaryHexColor = hexColor;
        return this;
    }

    public MessengerBuilder setBackground(Background background) {
        if (background == null || background.getBackgroundDrawable() == null) {
            throw new IllegalArgumentException("Invalid Background");
        }

        this.background = background;
        return this;
    }

    public MessengerBuilder setForeground(Foreground foreground) {
        if (foreground == null
                ||
                foreground.getType() != Foreground.ForegroundType.NONE
                        &&
                        (foreground.getDrawableForDarkBackground() == null || foreground.getDrawableForLightBackground() == null)) {
            throw new IllegalArgumentException("Invalid Foreground");
        }

        this.foreground = foreground;
        return this;
    }

    public void open(AppCompatActivity activity) {
        commonOpen();
        KayakoMessengerActivity.startActivity(activity);
    }

    public void open(Activity activity) {
        commonOpen();
        KayakoMessengerActivity.startActivity(activity);
    }

    private void commonOpen() {
        // Mandatory Fields
        saveOrValidateUrl();
        saveOrValidateBrandName();
        saveOrValidateTitle();
        saveOrValidateDescription();

        // Fields that can not be null but need not be specified by user
        saveOrUseCachedOrGenerateNewFingerprintId();
        saveOrUseDefaultBackground();
        saveOrUseDefaultForeground();
        saveOrUseDefaultPrimaryColor();

        // Optional Fields
        saveIfAvailableUserEmail();
    }

    private void saveIfAvailableUserEmail() {
        if (userEmail != null) {
            MessengerPref.getInstance().setEmailId(userEmail);
        }
    }

    // TODO: Find all UI compoenets using primary color and set the color programmatically!
    // TODO: Or make the steps so that the primaryMessengerColor is also Material Accent color - controlled via colors.xml? = Reason, material colors still affect a lot of the views
    private void saveOrUseDefaultPrimaryColor() {
        if (primaryHexColor == null) {
            MessengerStylePref.getInstance().setPrimaryColor(primaryHexColor);
        } else {
            setPrimaryColor(R.color.ko__messenger_primary_color);
            MessengerStylePref.getInstance().setPrimaryColor(primaryHexColor);
        }
    }

    private String getStringFromResourceId(@StringRes int stringResId) {
        try {
            return Kayako.getApplicationContext().getResources().getString(stringResId);
        } catch (Resources.NotFoundException e) {
            throw new IllegalArgumentException("Invalid String resource for Brand Name");
        }
    }

    private void assertValidString(String string) {
        if (string == null || string.length() < 1) {
            throw new IllegalArgumentException("Invalid String");
        }
    }

    private void saveOrUseDefaultForeground() {
        if (foreground == null) {
            MessengerStylePref.getInstance().setForeground(ForegroundFactory.getForeground(ForegroundFactory.ForegroundOption.CONFETTI));
        } else {
            MessengerStylePref.getInstance().setForeground(foreground);
        }
    }

    private void saveOrUseDefaultBackground() {
        if (background == null) {
            MessengerStylePref.getInstance().setBackground(BackgroundFactory.getBackground(BackgroundFactory.BackgroundOption.EGGPLANT));
        } else {
            MessengerStylePref.getInstance().setBackground(background);
        }
    }

    private void saveOrUseCachedOrGenerateNewFingerprintId() {
        // if fingerprint-id is not provided, one must be generated!
        if (fingerprintId == null && MessengerPref.getInstance().getFingerprintId() != null) {
            // Do nothing - use pre-saved fingerprintId
        } else if (fingerprintId == null) {
            MessengerPref.getInstance().setFingerprintId(FingerprintUtils.generateUUIDv4());
        } else {
            MessengerPref.getInstance().setFingerprintId(fingerprintId);
        }
    }

    private void saveOrValidateBrandName() {
        if (brandName == null) {
            throw new IllegalArgumentException("BrandName is mandatory. Please call setBrandName()");
        } else {
            MessengerPref.getInstance().setBrandName(brandName);
        }
    }

    private void saveOrValidateTitle() {
        if (title == null) {
            throw new IllegalArgumentException("Title is mandatory. Please call setTitle()");
        } else {
            MessengerPref.getInstance().setTitle(title);
        }
    }

    private void saveOrValidateDescription() {
        if (description == null) {
            throw new IllegalArgumentException("Description is mandatory. Please call setDescription()");
        } else {
            MessengerPref.getInstance().setDescription(description);
        }
    }

    private void saveOrValidateUrl() {
        if (url == null) {
            throw new IllegalArgumentException("URL is mandatory. Please call setUrl()");
        } else {
            MessengerPref.getInstance().setUrl(url);
        }
    }
}
