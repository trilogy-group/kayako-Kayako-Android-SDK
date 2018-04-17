package com.kayako.sdk.android.k5.kre.data;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;

public class DevicePayloadTest {

    @Test
    public void whenValidParamsThenObjectCreated() {
        //Arrange
        final long device_id = 1_000L;

        //Act
        final DevicePayload devicePayload = new DevicePayload(device_id);

        //Assert
        assertEquals(device_id, devicePayload.device_id.longValue());
    }
}
