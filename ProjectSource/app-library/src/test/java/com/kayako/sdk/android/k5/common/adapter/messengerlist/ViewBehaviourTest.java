package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import org.junit.Test;
import static org.junit.Assert.*;

public class ViewBehaviourTest {

    @Test
    public void test_constructor() {
        Boolean showTime = true;
        Boolean showAvatar = true;
        Boolean showChannel = true;
        Boolean showAsSelf = false;
        Boolean showDeliveryIndicator = false;
        ViewBehaviour viewBehaviour = new ViewBehaviour(
                showTime, showAvatar, showChannel, showAsSelf, showDeliveryIndicator, ViewBehaviour.MessageType.ATTACHMENT);

        assertNotNull(viewBehaviour);
        assertTrue(viewBehaviour.showTime);
        assertFalse(viewBehaviour.showAsSelf);
        assertNotEquals(ViewBehaviour.MessageType.SIMPLE, viewBehaviour.messageType);
        assertEquals(ViewBehaviour.MessageType.ATTACHMENT, viewBehaviour.messageType);
    }
}
