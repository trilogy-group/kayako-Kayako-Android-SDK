package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.kayako.sdk.android.k5.common.adapter.ContentComparable;

import java.util.HashMap;
import java.util.Map;

public class DeliveryIndicator implements ContentComparable{

    @DrawableRes
    private int deliveryStatusIconResId;

    @StringRes
    private int deliveryStatusTextResId;

    private Long deliveryTime; // time when status was last updated - not to be confused with message last updated

    public DeliveryIndicator(@DrawableRes int deliveryStatusIconResId, @StringRes int deliveryStatusTextResId, Long deliveryTime) {
        this.deliveryStatusIconResId = deliveryStatusIconResId;
        this.deliveryStatusTextResId = deliveryStatusTextResId;
        this.deliveryTime = deliveryTime;
    }

    public Integer getDeliveryStatusIconResId() {
        return deliveryStatusIconResId == 0 ? null : deliveryStatusIconResId;
    }

    public Integer getDeliveryStatusTextResId() {
        return deliveryStatusTextResId == 0 ? null : deliveryStatusTextResId;
    }

    public Long getDeliveryTime() {
        return deliveryTime;
    }

    @Override
    public Map<String, String> getContents() {
        Map<String, String> map = new HashMap<>();
        map.put("DeliveryIndicator.deliveryStatusIconResId", String.valueOf(deliveryStatusIconResId));
        map.put("DeliveryIndicator.deliveryStatusTextResId", String.valueOf(deliveryStatusTextResId));
        map.put("DeliveryIndicator.deliveryTime", String.valueOf(deliveryTime));
        return map;
    }
}
