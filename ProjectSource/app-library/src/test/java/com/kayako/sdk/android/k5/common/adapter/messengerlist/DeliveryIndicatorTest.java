package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import java.util.Map;

public class DeliveryIndicatorTest {

    private int deliveryStatusIconResId;
    private int deliveryStatusTextResId;
    private Long deliveryTime;
    private DeliveryIndicator deliveryIndicator;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setup() {
        deliveryStatusIconResId = 1;
        deliveryStatusTextResId = 2;
        deliveryTime = 1000L;
        deliveryIndicator = new DeliveryIndicator(deliveryStatusIconResId, deliveryStatusTextResId, deliveryTime);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        errorCollector.checkThat(deliveryIndicator, notNullValue());
        errorCollector.checkThat(deliveryIndicator.getDeliveryStatusIconResId().intValue(), is(equalTo(deliveryStatusIconResId)));
        errorCollector.checkThat(deliveryIndicator.getDeliveryStatusTextResId().intValue(), is(equalTo(deliveryStatusTextResId)));
        errorCollector.checkThat(deliveryIndicator.getDeliveryTime(), is(equalTo(deliveryTime)));
    }

    @Test
    public void getDeliveryStatusIconResId(){
        errorCollector.checkThat(deliveryIndicator.getDeliveryStatusIconResId().intValue(), is(equalTo(deliveryStatusIconResId)));
    }

    @Test
    public void whenZeroDeliveryStatusIconResIdThenReturnNullValue(){
        DeliveryIndicator deliveryIndicator1 = new DeliveryIndicator(0, deliveryStatusTextResId, deliveryTime);
        errorCollector.checkThat(deliveryIndicator1.getDeliveryStatusIconResId(), nullValue());
    }

    @Test
    public void getDeliveryStatusTextResId() {
        errorCollector.checkThat(deliveryIndicator.getDeliveryStatusTextResId().intValue(), is(equalTo(deliveryStatusTextResId)));
    }

    @Test
    public void whenZeroDeliveryStatusTextResIdThenReturnNullValue() {
        DeliveryIndicator deliveryIndicator1 = new DeliveryIndicator(deliveryStatusIconResId, 0, deliveryTime);
        errorCollector.checkThat(deliveryIndicator1.getDeliveryStatusTextResId(), nullValue());
    }

    @Test
    public void getDeliveryTime() {
        errorCollector.checkThat(deliveryIndicator.getDeliveryTime(), is(equalTo(deliveryTime)));
    }

    @Test
    public void getContents() {
        Map map = deliveryIndicator.getContents();
        errorCollector.checkThat(map.size(), is(3));
        errorCollector.checkThat(map.get("DeliveryIndicator.deliveryStatusIconResId").toString(), is(equalTo(String.valueOf(deliveryStatusIconResId))));
        errorCollector.checkThat(map.get("DeliveryIndicator.deliveryStatusTextResId").toString(), is(equalTo(String.valueOf(deliveryStatusTextResId))));
        errorCollector.checkThat(map.get("DeliveryIndicator.deliveryTime").toString(), is(equalTo(String.valueOf(deliveryTime))));
    }
}