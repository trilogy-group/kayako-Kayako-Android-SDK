package com.kayako.sdk.android.k5.kre.helpers;

import com.kayako.sdk.android.k5.kre.data.PushData;

public interface RawChangeListener<T extends PushData> {

    void onChange(T t);

    void onConnectionError();
}
