package com.kayako.sdk.android.k5.kre.base;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.kayako.sdk.android.k5.common.utils.NetworkUtils;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.core.KayakoLogHelper;
import com.kayako.sdk.android.k5.kre.base.credentials.KreFingerprintCredentials;
import com.kayako.sdk.android.k5.kre.base.credentials.KreSessionCredentials;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.phoenixframework.channels.IErrorCallback;
import org.phoenixframework.channels.ISocketCloseCallback;
import org.phoenixframework.channels.ISocketOpenCallback;
import org.phoenixframework.channels.Socket;
import org.powermock.core.classloader.annotations.PrepareForTest;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@PrepareForTest({
        NetworkUtils.class,
        Kayako.class,
        TextUtils.class,
        Uri.class,
        Socket.class,
        SocketConnection.class,
        KayakoLogHelper.class,
        KreConnection.class
})
public abstract class SocketConnectionTest {
    private static final String INSTANCE_URL = "https://support.kayako.com";
    private static final String SESSION_ID = "session_id";
    private static final String REAL_TIME_URL = "realTimeUrl";
    private static final String USER_AGENT = "userAgent";
    private static final String FINGERPRINT_ID = "fingerprintId";
    private static final String HOST = "host";
    private static final String URL_STRING = "url_string";
    private static final Throwable THROWABLE = new Throwable();
    private static final String NO_NETWORK_CONNECTION = "No Network Connection";
    static final String ERROR_MESSAGE = "errorMessage";

    @Mock
    private SocketConnection.OnOpenSocketConnectionListener onOpenSocketConnectionListener;
    @Mock
    private SocketConnection.OnCloseConnectionListener onCloseConnectionListener;
    @Mock
    private Uri uri;
    @Mock
    private Uri.Builder builder;
    @Mock
    private Context context;
    @Mock
    Socket socket;
    @Captor
    ArgumentCaptor<ISocketOpenCallback> socketOpenCaptor;
    @Captor
    ArgumentCaptor<String> errorMessageCaptor;
    @Captor
    private ArgumentCaptor<ISocketCloseCallback> socketCloseCaptor;
    @Captor
    ArgumentCaptor<IErrorCallback> errorCaptor;
    @Captor
    private ArgumentCaptor<Socket.OnSocketThrowExceptionListener> socketThrowCaptor;
    KreSessionCredentials kreCredentials;
    private final SocketConnection socketConnection = new SocketConnection();

    public void setUp() throws Exception {
        mockStatic(NetworkUtils.class);
        mockStatic(Kayako.class);
        mockStatic(TextUtils.class);
        mockStatic(Uri.class);
        mockStatic(KayakoLogHelper.class);

        whenNew(Socket.class).withArguments(URL_STRING).thenReturn(socket);
        when(Kayako.getApplicationContext()).thenReturn(context);
        when(NetworkUtils.isConnectedToNetwork(context)).thenReturn(true);
        when(Uri.parse(or(eq(INSTANCE_URL), contains(REAL_TIME_URL)))).thenReturn(uri);
        when(uri.getHost()).thenReturn(HOST);
        when(uri.toString()).thenReturn(URL_STRING);
        when(uri.buildUpon()).thenReturn(builder);
        when(builder.build()).thenReturn(uri);
        when(builder.appendQueryParameter(anyString(), anyString())).thenReturn(builder);
        when(socket.onOpen(any(ISocketOpenCallback.class))).thenReturn(socket);
        when(socket.onClose(any(ISocketCloseCallback.class))).thenReturn(socket);
        when(socket.onError(any(IErrorCallback.class))).thenReturn(socket);

        kreCredentials = new KreSessionCredentials(REAL_TIME_URL, SESSION_ID, INSTANCE_URL, USER_AGENT);
    }

    @Test
    public void callsOnErrorWhenNotConnectedToNetwork() {
        //Arrange
        when(NetworkUtils.isConnectedToNetwork(context)).thenReturn(false);

        //Act
        socketConnection.connect(kreCredentials, onOpenSocketConnectionListener);

        //Assert
        verify(onOpenSocketConnectionListener).onError(errorMessageCaptor.capture());
        assertThat(errorMessageCaptor.getValue(), containsString(NO_NETWORK_CONNECTION));
    }

    private void verifyConnect() {
        verify(socket).onOpen(socketOpenCaptor.capture());
        verify(socket).onError(errorCaptor.capture());
        verify(socket).setOnSocketThrowExceptionListener(socketThrowCaptor.capture());
    }

    private void verifyConnectWasSuccessful() {
        verifyConnect();
        socketOpenCaptor.getValue().onOpen();
        verify(onOpenSocketConnectionListener).onOpen();
        assertTrue(socketConnection.isConnected());
    }

    private void verifyConnectWasNotSuccessfulWhenOnErrorCalled() {
        verifyConnect();
        errorCaptor.getValue().onError(ERROR_MESSAGE);
        verify(onOpenSocketConnectionListener).onError(eq(ERROR_MESSAGE));
        assertFalse(socketConnection.isConnected());
    }

    private void verifyConnectWasNotSuccessfulWhenOnThrowCalled() {
        verifyConnect();
        socketThrowCaptor.getValue().onThrowException(ERROR_MESSAGE, THROWABLE);
        assertFalse(socketConnection.isConnected());
    }

    private void verifyDisconnect() {
        verify(socket).onClose(socketCloseCaptor.capture());
        verify(socket, times(2)).onError(errorCaptor.capture());
    }

    private void verifyDisconnectWasSuccessfulWhenOnCloseCalled() {
        verifyDisconnect();
        socketCloseCaptor.getValue().onClose();
        verify(onCloseConnectionListener).onClose();
        assertFalse(socketConnection.isConnected());
    }

    private void verifyDisconnectWasSuccessfulWhenOnErrorCalled() {
        verifyDisconnect();
        errorCaptor.getValue().onError(ERROR_MESSAGE);
        verify(onCloseConnectionListener).onError(eq(ERROR_MESSAGE));
        assertFalse(socketConnection.isConnected());
    }

    @Test
    public void connectUsingFingerprintCredentials() {
        //Arrange
        KreFingerprintCredentials kreFingerprintCredentials =
                new KreFingerprintCredentials(REAL_TIME_URL, INSTANCE_URL, FINGERPRINT_ID);

        //Act
        socketConnection.connect(kreFingerprintCredentials, onOpenSocketConnectionListener);

        //Assert
        verifyConnectWasSuccessful();
    }

    @Test
    public void connectUsingSessionCredentials() {
        //Arrange
        KreSessionCredentials kreSessionCredentials =
                new KreSessionCredentials(REAL_TIME_URL, SESSION_ID, INSTANCE_URL, USER_AGENT);

        //Act
        socketConnection.connect(kreSessionCredentials, onOpenSocketConnectionListener);

        //Assert
        verifyConnectWasSuccessful();
    }

    @Test
    public void connectWasNotSuccessfulWhenOnErrorCalled() {
        //Act
        socketConnection.connect(kreCredentials, onOpenSocketConnectionListener);

        //Assert
        verifyConnectWasNotSuccessfulWhenOnErrorCalled();
    }

    @Test
    public void connectWasNotSuccessfulWhenOnThrowCalled() {
        //Arrange
        KreSessionCredentials kreSessionCredentials =
                new KreSessionCredentials(REAL_TIME_URL, SESSION_ID, INSTANCE_URL, USER_AGENT);

        //Act
        socketConnection.connect(kreSessionCredentials, onOpenSocketConnectionListener);

        //Assert
        verifyConnectWasNotSuccessfulWhenOnThrowCalled();
    }

    @Test
    public void disconnectWhenOnCloseCalled() {
        //Act
        socketConnection.connect(kreCredentials, onOpenSocketConnectionListener);
        socketConnection.disconnect(onCloseConnectionListener);

        //Assert
        verifyDisconnectWasSuccessfulWhenOnCloseCalled();
    }

    @Test
    public void disconnectWhenOnThrowCalled() {
        //Act
        socketConnection.connect(kreCredentials, onOpenSocketConnectionListener);
        socketConnection.disconnect(onCloseConnectionListener);

        //Assert
        verifyDisconnectWasSuccessfulWhenOnErrorCalled();
    }
}
