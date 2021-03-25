package com.kayako.sdk.android.k5.kre.data;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;

public class DevicePayloadTest {

    @Test
    public void whenValidParamsThenObjectCreated() {
        //Arrange
        final long deviceId = 1_000L;

        //Act
        final DevicePayload devicePayload = new DevicePayload(deviceId);

        //Assert
        assertEquals(deviceId, devicePayload.device_id.longValue());
    }
}
