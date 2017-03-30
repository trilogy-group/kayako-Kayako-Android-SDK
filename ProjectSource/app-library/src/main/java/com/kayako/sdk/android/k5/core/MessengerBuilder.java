package com.kayako.sdk.android.k5.core;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.Patterns;
import android.webkit.URLUtil;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.activities.KayakoMessengerActivity;
import com.kayako.sdk.android.k5.messenger.style.BackgroundFactory;
import com.kayako.sdk.android.k5.messenger.style.ForegroundFactory;
import com.kayako.sdk.android.k5.messenger.style.type.Background;
import com.kayako.sdk.android.k5.messenger.style.type.Foreground;
import com.kayako.sdk.utils.FingerprintUtils;

public class MessengerBuilder {

    // Mandatory
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
        String brandName;
        try {
            brandName = Kayako.getApplicationContext().getResources().getString(stringResId);
            return setBrandName(brandName);
        } catch (Resources.NotFoundException e) {
            throw new IllegalArgumentException("Invalid String resource for Brand Name");
        }
    }


    public MessengerBuilder setBrandName(String brandName) {
        if (brandName == null || brandName.length() < 1) {
            throw new IllegalArgumentException("Invalid Brand Name");
        }
        this.brandName = brandName;
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

    public void open(Context context) {
        // Mandatory Fields
        saveOrValidateUrl();
        saveOrValidateBrandName();

        // Fields that can not be null but need not be specified by user
        saveOrUseCachedOrGenerateNewFingerprintId();
        saveOrUseDefaultBackground();
        saveOrUseDefaultForeground();
        saveOrUseDefaultPrimaryColor();

        // Optional Fields
        saveIfAvailableUserEmail();

        Intent intent = KayakoMessengerActivity.getIntent(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
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

    private void saveOrValidateUrl() {
        if (url == null) {
            throw new IllegalArgumentException("URL is mandatory. Please call setUrl()");
        } else {
            MessengerPref.getInstance().setUrl(url);
        }
    }
}
