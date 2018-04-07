package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;
public class DeliveryIndicatorTest {

    private int deliveryStatusIconResId;
    private int deliveryStatusTextResId;
    private Long deliveryTime;
    DeliveryIndicator deliveryIndicator;

    @Before
    public void setup() {
        deliveryStatusIconResId = 1;
        deliveryStatusTextResId = 2;
        deliveryTime = 1000L;
        deliveryIndicator = new DeliveryIndicator(deliveryStatusIconResId, deliveryStatusTextResId, deliveryTime);
    }

    @Test
    public void test_constructor() {
        assertNotNull(deliveryIndicator);
        assertEquals(deliveryStatusIconResId, deliveryIndicator.getDeliveryStatusIconResId().intValue());
        assertEquals(deliveryStatusTextResId, deliveryIndicator.getDeliveryStatusTextResId().intValue());
        assertEquals(deliveryTime, deliveryIndicator.getDeliveryTime());
    }

    @Test
    public void test_getDeliveryStatusIconResId1(){
        assertEquals(deliveryStatusIconResId, deliveryIndicator.getDeliveryStatusIconResId().intValue());
    }

    @Test
    public void test_getDeliveryStatusIconResId2(){
        DeliveryIndicator deliveryIndicator1 = new DeliveryIndicator(0, deliveryStatusTextResId, deliveryTime);
        assertNull(deliveryIndicator1.getDeliveryStatusIconResId());
    }

    @Test
    public void test_test_getDeliveryStatusTextResId1() {
        assertEquals(deliveryStatusTextResId, deliveryIndicator.getDeliveryStatusTextResId().intValue());
    }

    @Test
    public void test_getDeliveryStatusTextResId2() {
        DeliveryIndicator deliveryIndicator1 = new DeliveryIndicator(deliveryStatusIconResId, 0, deliveryTime);
        assertNull(deliveryIndicator1.getDeliveryStatusTextResId());
    }

    @Test
    public void test_getDeliveryTime() {
        assertEquals(deliveryTime, deliveryIndicator.getDeliveryTime());
    }

    @Test
    public void test_getContents() {
        Map map = deliveryIndicator.getContents();
        assertEquals(3, map.size());
        assertEquals(String.valueOf(deliveryStatusIconResId), map.get("DeliveryIndicator.deliveryStatusIconResId"));
        assertEquals(String.valueOf(deliveryStatusTextResId), map.get("DeliveryIndicator.deliveryStatusTextResId"));
        assertEquals(String.valueOf(deliveryTime), map.get("DeliveryIndicator.deliveryTime"));
    }
}