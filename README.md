# Overview

This SDK wraps the Kayako Developer APIs and is provided as an Android Library project that can be included in your application.

# Integration Guide

## Version Requriemennts

|Property|Version No.|
|---|---|
|targetSDKVersion|23|
|compileSDKVersion|23|
|buildToolsVersion|23|
|minSDKVersion|14|

# Getting Started

To get started, please follow the instructions below:

### Step 1: Android Manifest

Add the following lines to your Android Manifest:


```
        <!-- Paste this inside the Application tags -->
        <activity
            android:name="com.kayako.sdk.android.k5.activities.KayakoHelpCenterActivity"
            android:theme="@style/HelpCenterTheme" />

        <activity
            android:name="com.kayako.sdk.android.k5.activities.KayakoSearchArticleActivity"
            android:launchMode="singleTask"
            android:theme="@style/Ko__SearchArticlePageTheme"
            android:windowSoftInputMode="adjustPan|stateVisible" />

        <activity
            android:name="com.kayako.sdk.android.k5.activities.KayakoArticleActivity"
            android:theme="@style/HelpCenterTheme" />
```

### Step 2: Styles

Paste the following lines to your res/values/styles.xml file. 

```
    <style name="HelpCenterTheme" parent="Ko__AppTheme">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>
```

You can modify the three colours to style the help center activities to match with your own app theme. 
Note: For advanced customization, read the later sections.  

### Step 3: Gradle Build Dependencies

Add the following lines to your Gradle dependency:

```
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])

    // Add the following dependencies to get the Support SDK to work
    compile 'com.android.support:appcompat-v7:24.2.0'
    compile 'com.android.support:recyclerview-v7:24.2.0'
    compile 'com.android.support:support-v4:24.2.0'
    compile 'com.android.support:design:24.2.0'
    compile 'com.squareup.okhttp3:okhttp:3.4.1'
    compile 'com.squareup.okio:okio:1.9.0'
    compile 'com.google.code.gson:gson:2.4'
    compile 'com.github.bumptech.glide:glide:3.7.0'
    
    // The following jar files are required for the Kayako Help Center
    compile 'com.kayako.sdk.android:1.0.0' 
    compile 'com.kayako.sdk.java:1.0.0'
}
```

### Step 4: Application

In your Application class, add the following:

```
    KayakoHC.initialize(this);
```

### Step 5: Progaurd

If you’re using progaurd, please add the following the code to your progaurd-project.txt file

```
# OkHttp3
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

# Glide

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

# Kayako Android SDK

-keep class com.kayako.sdk.android.k5.** { *; }
-dontwarn com.kayako.sdk.android.k5.**
-keepattributes InnerClasses

# Android

-keep class android.support.v4.** { *; }
````

_For more information on progaurd, access the progaurd documentation: [http://developer.android.com/tools/help/proguard.html](http://developer.android.com/tools/help/proguard.html)_

### Step 6: Open Intent

Place the following lines of code where you want the Help Center to be opened:

```
    KayakoHC.getInstance().openHelpCenter(context, helpCenterUrl, defaultLocale);
```

For example:
```
    KayakoHC.getInstance().openHelpCenter(getContext(), "https://support.kayako.com", new Locale("en","us");
```

# Customization

## String Customization 

**strings.xml**

```
    <string name="ko__search_bar_how_can_we_help_you">How can we help you?</string>
    <string name="ko__label_retry">Retry</string>
    <string name="ko__label_search_hint">Search here...</string>
    <string name="ko__action_ok">Ok</string>
    <string name="ko__error_type_at_least_three_characters_to_search">Please type at least 3 characters to search!</string>
    <string name="ko__label_empty_view_title">Whoops!</string>
    <string name="ko__label_empty_view_description">Looks like there\'s nothing to show</string>
    <string name="ko__label_error_view_title">Uh Oh.</string>
    <string name="ko__label_error_view_description">Something went wrong on our side. Please try again later.</string>
    <string name="ko__label_error_view_network_title">Uh Oh.</string>
    <string name="ko__label_error_view_network_description">Unable to connect to server. Please check your network connection. </string>
    <string name="ko__msg_unable_to_load_more_items">Something went wrong. Unable to load more items.</string>
    <string name="ko__helpcenter_title">Help</string>
    <string name="ko__action_contact">Contact</string>
    <string name="ko__action_search">Search</string>
```    

|Id | Description |
|---|---|
|ko__search_bar_how_can_we_help_you|Title of Knowledge Base page|
|ko__label_retry|Title of Navigate Articles page|
|ko__title_search_articles|Title of Search Articles page|
|ko__title_article|Title of Article Detail Page|
|ko_label_welcome_message|Welcome message displayed in the Search Section in KnowledgeBase Page|
|ko_info_search| Hint when long pressing search|
|ko_info_was_this_article_helpful| Text asking for whether article was helpful|
|ko_error_title_no_internet|Error Message informing no internet connection|
|ko_error_desc_no_internet|Error Message Description informing user what steps to take next|
|ko_error_title_empty|Error Message title of having no items in the list|
|ko_error_desc_empty|Error Message description informing of actions steps|
|ko_error_title_error|Error Message title informing when server error faced|
|ko_error_desc_error|Error Message description when server error is faced|
|ko_button_retry|Button label to retry request|
|ko_button_login_facebook|Button label to login to facebook|
|ko_button_login_twitter|Button label to login to twitter|
|ko_button_login_kayako|Button label to login to kayako|
|ko_button_sign_up|Button label to sign up|
|ko_message_sign_in | Button label to sign in using Kayako Credentials|



You can add localizations of your language of choice. Just use the above strings and organize it in a separate folder.
For example, values-ru for russian.
```
.
├── res
|   ├── values-ru
|   |   ├── strings.xml
|   └── values-en
|       ├── strings.xml
```

## Design


### Simple Customization

For simple customization, you only need to add the material colors (*res/values/colors.xml*) for the help center to match your application's theme. 

```
<color name="colorPrimary"></color>
<color name="colorPrimaryDark">#7C7C7C</color>
<color name="colorAccent">#3AA6C5</color>
```

### Advanced Customization

For a more advanced customization, you can override the following colors in your HelpCenterTheme (Step 2 of Getting Started). 

![Page 1: Colors](images/colors-page1.png "Colors used in Help Center Page")
![Page 2: Colors](images/colors-page2.png "Colors used in Article Listing Page")
![Page 3: Colors](images/colors-page3.png "Colors used in Search Articles Page")
![Page 4: Colors](images/colors-page4.png "Colors used in Article Page")
![Page 5: Colors](images/colors-page5.png "Colors used in Error Page")


# APIs

Use the methods available available in the HelpCenter class.

|Class Name | Description |
|---|---|
|com.kayako.sdk.android.k5.KayakoHelpCenter|com.kayako.sdk.android.k5.KayakoHelpCenter|

## Help Center APIs

Contains all the methods to interact with the help center from opening the knowledge base, searching articles and opening a specific article.

#### Sample Example of opening the Help Center

Contains all the methods to interact with the help center from opening the knowledge base, searching articles and opening a specific article.

|Return Type | Method Signature | Description |
|---|---|---|
|void|searchHelpCenter(Context context, String query)|Opens the search articles page with the query string prefiled and
|void|openArticle(Context context, long articleId)|Opens the helpcenter page
|void|openHelpCenter(Context context)|Opens the helpcenter activity


## Trouble Shooting

**Errors**

|Error Name|Description|
|---|---|
|HELP_DESK_URL_NOT_INITIALIZED|Help Desk Url has not been initialized in the app. Please mention the help desk url when initializing the sdk in the Application class.|


## Change Log

####v1.0.0
- First Iteration
