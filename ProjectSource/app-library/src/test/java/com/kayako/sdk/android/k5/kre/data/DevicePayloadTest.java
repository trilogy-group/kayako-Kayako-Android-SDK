package com.kayako.sdk.android.k5.kre.data;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;

public class DevicePayloadTest {

    @Test
    public void whenValidParamsThenObjectCreated() {
        final long device_id = 1_000L;
        final DevicePayload devicePayload = new DevicePayload(device_id);
        assertEquals(device_id, devicePayload.device_id.longValue());
    }
}
