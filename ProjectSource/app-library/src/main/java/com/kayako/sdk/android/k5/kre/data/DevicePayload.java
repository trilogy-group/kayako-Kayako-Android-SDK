package com.kayako.sdk.android.k5.kre.data;

/**
 * Used to send the push notification device id with KRE User Subscription
 * <p>
 * Used to indicate which device is currently online.
 */
public class DevicePayload extends Payload {

    public Long device_id;

    public DevicePayload(long device_id) {
        this.device_id = device_id;
    }

}
