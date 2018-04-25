package com.kayako.sdk.android.k5.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NetworkUtilsTest {
    @Mock
    private Context context;

    @Mock
    private ConnectivityManager cm;

    @Mock
    private NetworkInfo networkInfo;

    @Test
    public void givenContextWhenCheckingIfActiveNetworkThenReturnBoolean() {
        //Arrange
        when(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(cm);
        when(cm.getActiveNetworkInfo()).thenReturn(networkInfo);

        //Act
        boolean returnedValue = NetworkUtils.isConnectedToNetwork(context);

        //Assert
        assertFalse(returnedValue);
    }
}
