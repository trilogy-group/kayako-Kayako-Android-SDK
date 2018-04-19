package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.DeliveryIndicator;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.BaseDeliveryIndicatorViewHolder;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.messenger.message.Message;
import com.kayako.sdk.messenger.message.MessageStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Kayako.class})
public class DeliveryIndicatorHelperTest {

    @Mock
    private BaseDeliveryIndicatorViewHolder holder;

    @Mock
    private View view;

    @Mock
    private ImageView imageView;

    @Mock
    private Context mockContext;

    @Mock
    private TextView textView;

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        final String deliveryIndicatorView = "deliveryIndicatorView";
        final String dotSeparator = "dotSeparator";
        final String deliveryIndicatorIcon = "deliveryIndicatorIcon";
        final String deliveryIndicatorTime = "deliveryIndicatorTime";
        final String deliveryIndicatorText = "deliveryIndicatorText";
        mockStatic(Kayako.class);
        when(Kayako.getApplicationContext()).thenReturn(mockContext);
        Whitebox.setInternalState(holder, deliveryIndicatorView, view);
        Whitebox.setInternalState(holder, dotSeparator, view);
        Whitebox.setInternalState(holder, deliveryIndicatorIcon, imageView);
        Whitebox.setInternalState(holder, deliveryIndicatorTime, textView);
        Whitebox.setInternalState(holder, deliveryIndicatorText, textView);
    }

    @Test
    public void whenNullClientDeliveryStatusThenGetDeliveryIndicatorReturnNull(){
        //Arrange
        ClientDeliveryStatus deliveryStatus = null;

        //Act & Assert
        errorCollector.checkThat(DeliveryIndicatorHelper.getDeliveryIndicator(deliveryStatus), nullValue());
    }

    @Test
    public void whenValidClientDeliveryStatusThenReturnCreatedObject(){
        //Act & Assert
        errorCollector.checkThat(DeliveryIndicatorHelper.getDeliveryIndicator(
                ClientDeliveryStatus.SENDING), is(instanceOf(DeliveryIndicator.class)));
        errorCollector.checkThat(DeliveryIndicatorHelper.getDeliveryIndicator(
                ClientDeliveryStatus.SENDING), notNullValue());
        errorCollector.checkThat(DeliveryIndicatorHelper.getDeliveryIndicator(
                ClientDeliveryStatus.FAILED_TO_SEND), notNullValue());

    }

    @Test
    public void whenNullMessageThenGetDeliveryIndicatorReturnNull() {
        //Arrange
        Message message = null;

        //Act & Assert
        errorCollector.checkThat(DeliveryIndicatorHelper.getDeliveryIndicator(
                message), nullValue());
    }

    @Test
    public void whenDifferentValidMessageStatusThenReturnValidObject() {
        //Arrange
        final long id = 1L;
        final long messageStatusUpdatedAt = 1_000L;
        final long createdAt = 2_000L;
        final long updatedAt = 3_000L;
        final long deliveryTime = 1_000L;
        final Message deliveredMessage = new Message(id,null,null,null,null,null,null,
                null,null,MessageStatus.DELIVERED, messageStatusUpdatedAt, createdAt, updatedAt);
        final Message rejectedMessage = new Message(id,null,null,null,null,null,null,
                null,null,MessageStatus.REJECTED, messageStatusUpdatedAt, createdAt, updatedAt);
        final Message seenMessage = new Message(id,null,null,null,null,null,null,
                null,null,MessageStatus.SEEN, messageStatusUpdatedAt, createdAt, updatedAt);
        final Message sentMessage = new Message(id,null,null,null,null,null,null,
                null,null,MessageStatus.SENT, messageStatusUpdatedAt, createdAt, updatedAt);

        //Act & Assert
        errorCollector.checkThat(DeliveryIndicatorHelper.getDeliveryIndicator(
                deliveredMessage).getDeliveryTime(), is(deliveryTime));
        errorCollector.checkThat(DeliveryIndicatorHelper.getDeliveryIndicator(
                rejectedMessage).getDeliveryTime(), nullValue());
        errorCollector.checkThat(DeliveryIndicatorHelper.getDeliveryIndicator(
                seenMessage).getDeliveryTime(), is(deliveryTime));
        errorCollector.checkThat(DeliveryIndicatorHelper.getDeliveryIndicator(
                sentMessage).getDeliveryTime(), is(deliveryTime));
    }

    @Test
    public void setDeliveryIndicatorViewWhenDeliveryIndicatorNull() {
        //Arrange
        final DeliveryIndicator deliveryIndicator = null;

        //Act
        DeliveryIndicatorHelper.setDeliveryIndicatorView(deliveryIndicator, 0L, holder);

        //Assert
        verify(view, times(2)).setVisibility(View.GONE);
        verify(imageView, times(1)).setVisibility(View.GONE);
        verify(textView, times(2)).setVisibility(View.GONE);
    }

    @Test
    public void setDeliveryIndicatorViewWhenTimeMessageCreatedGreaterThanZero() {
        //Arrange
        final DeliveryIndicator deliveryIndicator = null;
        final long timeMessageCreated = 1_000L;

        //Act
        DeliveryIndicatorHelper.setDeliveryIndicatorView(deliveryIndicator, timeMessageCreated, holder);

        //Assert
        verify(view, times(1)).setVisibility(View.GONE);
        verify(view, times(1)).setVisibility(View.VISIBLE);
        verify(imageView, times(1)).setVisibility(View.GONE);
        verify(textView, times(1)).setVisibility(View.GONE);
        verify(textView, times(1)).setVisibility(View.VISIBLE);

    }

    @Test
    public void setDeliveryIndicatorWhenDeliveryIndicatorNotNull() {
        //Arrange
        final DeliveryIndicator deliveryIndicator = new DeliveryIndicator(1, 1, 1_000L);
        final long timeMessageCreated = 0L;

        //Act
        DeliveryIndicatorHelper.setDeliveryIndicatorView(deliveryIndicator, timeMessageCreated, holder);

        //Assert
        verify(view, times(2)).setVisibility(View.VISIBLE);
        verify(imageView, times(1)).setVisibility(View.VISIBLE);
        verify(textView, times(2)).setVisibility(View.VISIBLE);
    }

    @Test
    public void setDeliveryIndicatorWhenDeliveryTimeZero() {
        //Arrange
        final DeliveryIndicator deliveryIndicator = new DeliveryIndicator(0, 0, 0L);
        final long timeMessageCreated = 0L;

        //Act
        DeliveryIndicatorHelper.setDeliveryIndicatorView(deliveryIndicator, timeMessageCreated, holder);

        //Assert
        verify(view, times(1)).setVisibility(View.GONE);
        verify(view, times(1)).setVisibility(View.VISIBLE);
        verify(imageView, times(1)).setVisibility(View.GONE);
        verify(textView, times(2)).setVisibility(View.GONE);
    }
}
