package com.kayako.sdk.android.k5.messenger.data.conversationstarter;

import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.messenger.Messenger;
import com.kayako.sdk.messenger.conversationstarter.ConversationStarter;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import java.lang.ref.WeakReference;
import java.util.List;

@PrepareForTest(MessengerPref.class)
@RunWith(PowerMockRunner.class)
public class ConversationStarterRepositoryManyListenersTest {

    private ConversationStarterRepositoryManyListeners repositoryManyListeners;
    private ConversationStarterRepository conversationStarterRepository;

    @Mock
    private IConversationStarterRepository.OnLoadConversationStarterListener listener;

    @Mock
    private MessengerPref messengerPref;

    @Mock
    private Messenger mMessenger;

    @Mock
    private ConversationStarter mConversationStarter;

    @Mock
    private KayakoException kayakoException;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        mockStatic(MessengerPref.class);
        when(MessengerPref.getInstance()).thenReturn(messengerPref);
        when(messengerPref.getFingerprintId()).thenReturn("123ABC");
        repositoryManyListeners = new ConversationStarterRepositoryManyListeners();
        conversationStarterRepository = Whitebox.getInternalState(
                repositoryManyListeners, "conversationStarterRepository");
    }

    @Test
    public void getConversationStarter() {
        //Arrange
        Whitebox.setInternalState(conversationStarterRepository, "mMessenger", mMessenger);

        //Act
        repositoryManyListeners.getConversationStarter(listener);

        //Assert
        final List<WeakReference<IConversationStarterRepository.OnLoadConversationStarterListener>>
                listeners = Whitebox.getInternalState(repositoryManyListeners, "listeners");
        errorCollector.checkThat(listeners.size(), is(1));
    }

    @Test
    public void clear() {
        //Arrange
        Whitebox.setInternalState(conversationStarterRepository, "mMessenger", mMessenger);
        Whitebox.setInternalState(conversationStarterRepository, "mConversationStarter", mConversationStarter);
        repositoryManyListeners.getConversationStarter(listener);

        //Act
        repositoryManyListeners.clear();

        //Assert
        final ConversationStarter conversationStarterLocal =
                Whitebox.getInternalState(conversationStarterRepository, "mConversationStarter");
        final List<WeakReference<IConversationStarterRepository.OnLoadConversationStarterListener>>
                listeners = Whitebox.getInternalState(repositoryManyListeners, "listeners");
        errorCollector.checkThat(conversationStarterLocal, nullValue());
        errorCollector.checkThat(listeners.size(), is(0));
    }

    @Test
    public void onLoadConversationMetrics() {
        //Arrange
        Whitebox.setInternalState(conversationStarterRepository, "mMessenger", mMessenger);
        repositoryManyListeners.getConversationStarter(listener);

        //Act
        repositoryManyListeners.onLoadConversationMetrics(mConversationStarter);

        //Assert
        final List<WeakReference<IConversationStarterRepository.OnLoadConversationStarterListener>>
                listeners = Whitebox.getInternalState(repositoryManyListeners, "listeners");
        errorCollector.checkThat(listeners.size(), is(1));
    }

    @Test
    public void onFailure() {
        //Arrange
        Whitebox.setInternalState(conversationStarterRepository, "mMessenger", mMessenger);
        repositoryManyListeners.getConversationStarter(listener);

        //Act
        repositoryManyListeners.onFailure(kayakoException);

        //Assert
        final List<WeakReference<IConversationStarterRepository.OnLoadConversationStarterListener>>
                listeners = Whitebox.getInternalState(repositoryManyListeners, "listeners");
        errorCollector.checkThat(listeners.size(), is(1));
    }
}
