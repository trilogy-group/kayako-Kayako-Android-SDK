package com.kayako.sdk.android.k5.kre.base;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.phoenixframework.channels.Channel;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class KreConnectionTest extends SocketConnectionTest {
    private static final String CHANNEL_NAME = "channelName";
    private final KreConnection connection = new KreConnection();
    @Mock
    private Channel channel;
    @Mock
    private SocketConnection.OnOpenConnectionListener onOpenConnectionListener;
    @Captor
    private ArgumentCaptor<Channel> channelCaptor;
    @Captor
    private ArgumentCaptor<Boolean> reconnectCaptor;
    @Rule
    private final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() throws Exception {
        super.setUp();
        when(socket.chan(CHANNEL_NAME, null)).thenReturn(channel);
    }

    private void connectFirstTime(SocketConnection.OnOpenConnectionListener onOpenConnectionListener, boolean success) {
        connection.connect(kreCredentials, CHANNEL_NAME, onOpenConnectionListener);
        verify(socket).onOpen(socketOpenCaptor.capture());
        verify(socket).onError(errorCaptor.capture());

        if (success) {
            socketOpenCaptor.getValue().onOpen();
        } else {
            errorCaptor.getValue().onError(ERROR_MESSAGE);
        }
    }

    @Test
    public void connectWasSuccessfulWhenOnOpenCalled() {
        //Act
        connectFirstTime(onOpenConnectionListener, true);

        //Assert
        verify(onOpenConnectionListener).onOpen(channelCaptor.capture());
        errorCollector.checkThat(channelCaptor.getValue(), is(sameInstance(channel)));
        errorCollector.checkThat(connection.isConnected(), is(true));
    }

    @Test
    public void connectWasNotSuccessfulWhenOnErrorCalled() {
        //Act
        connectFirstTime(onOpenConnectionListener, false);

        //Assert
        verify(onOpenConnectionListener).onError(errorMessageCaptor.capture());
        errorCollector.checkThat(connection.isConnected(), is(false));
        errorCollector.checkThat(errorMessageCaptor.getValue(), is(equalTo(ERROR_MESSAGE)));
    }

    @Test
    public void connectOtherThanFirstTime() {
        //Arrange
        connectFirstTime(onOpenConnectionListener, true);

        //Act
        connection.connect(kreCredentials, CHANNEL_NAME, mock(SocketConnection.OnOpenConnectionListener.class));

        //Assert
        verify(onOpenConnectionListener).onOpen(channelCaptor.capture());
        errorCollector.checkThat(channelCaptor.getValue(), is(sameInstance(channel)));
        errorCollector.checkThat(connection.isConnected(), is(true));
    }

    @Test
    public void disconnectWhenOneListener() {
        //Arrange
        connectFirstTime(onOpenConnectionListener, true);

        //Act
        connection.disconnect(onOpenConnectionListener);

        //Assert
        assertFalse(connection.isConnected());
    }

    @Test
    public void stillConnectedWhenMoreThanOneListeners() {
        //Arrange
        connectFirstTime(onOpenConnectionListener, true);
        connection.connect(kreCredentials, CHANNEL_NAME, mock(SocketConnection.OnOpenConnectionListener.class));

        //Act
        connection.disconnect(onOpenConnectionListener);

        //Assert
        assertTrue(connection.isConnected());
    }

    @Test
    public void configureReconnectOnFailure() {
        //Arrange
        connectFirstTime(onOpenConnectionListener, true);

        //Act
        connection.configureReconnectOnFailure(true);

        //Assert
        verify(socket).reconectOnFailure(reconnectCaptor.capture());
        assertTrue(reconnectCaptor.getValue());
    }

}
