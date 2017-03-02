package com.kayako.sdk.android.k5.kre.helpers;

import android.support.annotation.NonNull;

import com.kayako.sdk.android.k5.kre.base.KreSubscription;
import com.kayako.sdk.android.k5.kre.data.Change;

public class KreCaseChangeHelper {

    private static final String TAG = "KreCaseChangeHelper";

    private static final String EVENT_CHANGE = "CHANGE";

    private KreCaseChangeHelper() {
    }

    public static void addRawCaseChangeListener(@NonNull KreSubscription kreSubscription, @NonNull final RawCaseChangeListener listener) {
        kreSubscription.listenFor(EVENT_CHANGE, new KreSubscription.OnEventListener() {
            @Override
            public void onEvent(String event, String jsonBody) {
                // KayakoLogger.d(TAG, "onChangeCase:" + jsonBody);
                final Change pushData = PushDataHelper.convertFromJsonString(Change.class, jsonBody);
                listener.onCaseChange(pushData);
            }

            @Override
            public void onError(String message) {
                listener.onConnectionError();
            }
        });
    }
}
