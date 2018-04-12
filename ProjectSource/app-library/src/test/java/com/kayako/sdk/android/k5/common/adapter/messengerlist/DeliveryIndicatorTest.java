package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.is;
import java.util.Map;

public class DeliveryIndicatorTest {

    private int deliveryStatusIconResId;
    private int deliveryStatusTextResId;
    private long deliveryTime;
    private DeliveryIndicator deliveryIndicator;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setup() {
        deliveryStatusIconResId = 1;
        deliveryStatusTextResId = 2;
        deliveryTime = 1_000L;
        deliveryIndicator = new DeliveryIndicator(deliveryStatusIconResId, deliveryStatusTextResId, deliveryTime);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        errorCollector.checkThat(deliveryIndicator, notNullValue());
        errorCollector.checkThat(deliveryIndicator.getDeliveryStatusIconResId(), is(deliveryStatusIconResId));
        errorCollector.checkThat(deliveryIndicator.getDeliveryStatusTextResId(), is(deliveryStatusTextResId));
        errorCollector.checkThat(deliveryIndicator.getDeliveryTime(), is(deliveryTime));
    }

    @Test
    public void whenZeroDeliveryStatusIconResIdThenReturnNullValue() {
        DeliveryIndicator deliveryIndicatorLocal = new DeliveryIndicator(
                0, deliveryStatusTextResId, deliveryTime);
        errorCollector.checkThat(deliveryIndicatorLocal.getDeliveryStatusIconResId(), nullValue());
    }

    @Test
    public void whenZeroDeliveryStatusTextResIdThenReturnNullValue() {
        DeliveryIndicator deliveryIndicatorLocal = new DeliveryIndicator(
                deliveryStatusIconResId, 0, deliveryTime);
        errorCollector.checkThat(deliveryIndicatorLocal.getDeliveryStatusTextResId(), nullValue());
    }

    @Test
    public void getContents() {
        Map<String, String> map = deliveryIndicator.getContents();
        errorCollector.checkThat(map.size(), is(3));
        errorCollector.checkThat(map.get("DeliveryIndicator.deliveryStatusIconResId"),
                is(String.valueOf(deliveryStatusIconResId)));
        errorCollector.checkThat(map.get("DeliveryIndicator.deliveryStatusTextResId"),
                is(String.valueOf(deliveryStatusTextResId)));
        errorCollector.checkThat(map.get("DeliveryIndicator.deliveryTime"),
                is(String.valueOf(deliveryTime)));
    }
}
