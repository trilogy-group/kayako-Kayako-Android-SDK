package com.kayako.sdk.android.k5.kre.helpers;

import com.kayako.sdk.android.k5.kre.data.Change;

public interface RawCaseChangeListener {

    void onCaseChange(Change change);

    void onConnectionError();
}
