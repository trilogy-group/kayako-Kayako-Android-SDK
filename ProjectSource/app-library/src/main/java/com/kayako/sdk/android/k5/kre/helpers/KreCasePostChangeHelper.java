package com.kayako.sdk.android.k5.kre.helpers;

import android.support.annotation.NonNull;

import com.kayako.sdk.android.k5.kre.base.KreSubscription;
import com.kayako.sdk.android.k5.kre.data.Change;
import com.kayako.sdk.android.k5.kre.data.ChangePost;

public class KreCasePostChangeHelper {

    private static final String TAG = "KreCasePostChangeHelper";

    private static final String EVENT_CHANGE_POST = "CHANGE_POST";
    private static final String EVENT_NEW_POST = "NEW_POST";

    private static final String RESOURCE_TYPE = "post";

    private KreCasePostChangeHelper() {
    }

    public static void addRawCasePostChangeListener(@NonNull KreSubscription kreSubscription, @NonNull final RawCasePostChangeListener listener) {
        kreSubscription.listenFor(EVENT_CHANGE_POST, new KreSubscription.OnEventListener() {
            @Override
            public void onEvent(String event, String jsonBody) {
                ChangePost changePost = PushDataHelper.convertFromJsonString(ChangePost.class, jsonBody);
                if (changePost != null && RESOURCE_TYPE.equals(changePost.resource_type)) {
                    listener.onChangePost(changePost.resource_id);
                }
            }

            @Override
            public void onError(String message) {
                listener.onConnectionError();
            }
        });

        kreSubscription.listenFor(EVENT_NEW_POST, new KreSubscription.OnEventListener() {
            @Override
            public void onEvent(String event, String jsonBody) {
                ChangePost changePost = PushDataHelper.convertFromJsonString(ChangePost.class, jsonBody);
                if (changePost != null && RESOURCE_TYPE.equals(changePost.resource_type)) {
                    listener.onNewPost(changePost.resource_id);
                }
            }

            @Override
            public void onError(String message) {
                listener.onConnectionError();
            }
        });
    }
}
