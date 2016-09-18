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

## External Dependencies

```
dependencies {
    compile ‘com.kayako.sdk.android.k5:1.0.0'
    compile 'com.android.support:appcompat-v7:23.2.1’ // backward compatibility support
    compile 'com.android.support:support-v13:23.2.1’ // backward compatibility support
    compile 'com.android.support:recyclerview-v7:23.2.1’ // listing of articles
    compile 'com.android.support:design:23.2.1’ // support for material design
    compile 'com.squareup.okhttp3:okhttp:3.3.1' // used for networking
    compile 'com.github.bumptech.glide:glide:3.7.0' // used for image loading and caching
    compile 'uk.co.chrisjenx:calligraphy:2.1.0' // used for fonts
}
```

## Build Systems

Currently there is support for only the Gradle Build System. We'll be adding samples for the Maven and Ant build soon. 

# Getting Started

## Android Manifest

Add the following lines to your Android Manifest. Make sure to replace the help center url with your own. 


```
<activity
            android:name=“com.kayako.sdk.android.k5.activity.HelpCenter"
            android:configChanges="keyboardHidden|orientation|screenSize"
            android:label="@string/title_helpcenter" />

      <activity
            android:name=“com.kayako.sdk.android.k5.activity.NavigateArticles"
            android:configChanges="keyboardHidden|orientation|screenSize"
            android:label="@string/title_search" />

      <activity
            android:name=“com.kayako.sdk.android.k5.activity.SearchActivity"
            android:configChanges="keyboardHidden|orientation|screenSize"
            android:label="@string/title_search" />

      <activity
            android:name=“com.kayako.sdk.android.k5.activity.Article"
            android:configChanges="keyboardHidden|orientation|screenSize"
            android:label="@string/title_search" />
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />
                <data android:scheme="http" />
                <data android:scheme="https" />
                <data android:host=“____PASTE_HELP_DESK_URL___" />
                <data android:pathPattern=“/.*/article/.*" />
            </intent-filter>
        </activity>
```

_Deep Linking has been added to allow a smooth in-app experience when clicking articles links mentioned in other articles._

## Application

Paste the following in your Application class. Replace the help center url with your own. 

```
KayakoCore.init(this, __PASTE_HELP_DESK_URL_HERE);
```

## Progaurd

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


# Customization

## String Customization 

**strings.xml**

|Id | Description |
|---|---|
|ko__title_knowledge_base|Title of Knowledge Base page|
|ko__title_navigate_articles|Title of Navigate Articles page|
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

**bool.xml**

|id|description|
|---|---|
|ko__showSearchBarSection|Show the giant search bar or hide it based on true/false value specified here|

**colors.xml**

|id|description|
|---|---|
|ko__search_section_bg|background color of search area on knowledge base page|
|ko__search_section_text|colour of search area text|
|ko__category_bg|background color of category|
|ko__cateogy_text|colour of category text|
|ko__section_bg|background color of section|
|ko__section_text|colour of section text|
|ko__section_desc|colour of section description|
|ko__article_list_bg|background color of article listing page|
|ko__article_list_text|colour of article list text|
|ko__article_list_desc|colour of article description text|
|ko__article_details_header_breadcrumb_text|colour of breadcrumb text in article details page|
|ko__article_details_header_bg|background color of header in article details page|
|ko__article_details_header_title|color of article title under header in article details page|
|ko__article_details_content_bg|background color of article detail page’s content area|

These can be used in addition to the android system colors:
- colorPrimary
- colorPrimaryDark
- colorAccent
- windowBackground

## Trouble Shooting

**Errors**

|Error Name|Description|
|---|---|
|HELP_DESK_URL_NOT_INITIALIZED|Help Desk Url has not been initialized in the app. Please mention the help desk url when initializing the sdk in the Application class.|


## Change Log

####v1.0.0
- First Iteration
