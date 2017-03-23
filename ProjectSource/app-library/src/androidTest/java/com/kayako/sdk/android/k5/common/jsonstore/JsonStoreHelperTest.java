package com.kayako.sdk.android.k5.common.jsonstore;

import android.support.test.InstrumentationRegistry;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.messenger.style.type.Background;
import com.kayako.sdk.android.k5.messenger.style.type.Gradient;

import org.junit.Assert;
import org.junit.Test;

public class JsonStoreHelperTest {

    @Test
    public void test1() throws Exception {
        Kayako.initialize(InstrumentationRegistry.getContext());

        Gradient gradient = new Gradient(R.drawable.ko__gradient_bg_1, true);
        String json = JsonStoreHelper.getJsonStringRepresentation(gradient, Background.class);
        System.out.println(json);
        Background background = JsonStoreHelper.getOriginalClassRepresentation(json, Background.class);

        Assert.assertEquals(gradient.getBackgroundDrawable().getConstantState(), background.getBackgroundDrawable().getConstantState());
        Assert.assertEquals(gradient.getType(), background.getType());
        Assert.assertEquals(gradient.isDarkBackground(), background.isDarkBackground());
    }
}