package com.kayako.sdk.android.k5.kre.helpers;

import android.support.annotation.NonNull;

import com.kayako.sdk.android.k5.core.KayakoLogHelper;
import com.kayako.sdk.android.k5.kre.base.KreSubscription;
import com.kayako.sdk.android.k5.kre.data.PushData;

public class KreChangeHelper {

    private static final String TAG = "KreChangeHelper";

    private static final String EVENT_CHANGE = "CHANGE";

    private KreChangeHelper() {
    }

    public static <T extends PushData> void addRawChangeListener(@NonNull KreSubscription kreSubscription, @NonNull final RawChangeListener<T> listener, final Class<T> pushDataClassType) {
        kreSubscription.listenFor(EVENT_CHANGE, new KreSubscription.OnEventListener() {
            @Override
            public void onEvent(String event, String jsonBody) {
                KayakoLogHelper.e("KRE: Before Parse, Json = ", jsonBody); // TODO: COMMENT LATER
                final T t = PushDataHelper.convertFromJsonString(pushDataClassType, jsonBody);
                KayakoLogHelper.e("KRE: After Parse, Object = ", t.toString());
                listener.onChange(t);
            }

            @Override
            public void onError(String message) {
                listener.onConnectionError();
            }
        });
    }
}
