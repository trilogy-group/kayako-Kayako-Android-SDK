package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.UserDecoration;
import com.kayako.sdk.helpcenter.user.UserMinimal;
import com.kayako.sdk.messenger.message.Message;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDecorationHelperTest {

    private final long userId = 1L;

    @Mock
    private Message message;

    @Mock
    private UserMinimal userMinimal;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Test
    public void whenNullMessageThenReturnNull() {
       UserDecoration userDecoration = UserDecorationHelper.getUserDecoration(null, userId);
       errorCollector.checkThat(userDecoration, nullValue());
    }

    @Test
    public void whenMessageCreatorNullThenNull() {
        UserDecoration userDecoration = UserDecorationHelper.getUserDecoration(message, userId);
        errorCollector.checkThat(userDecoration, nullValue());
    }

    @Test
    public void whenValidParamsThenObjectCreated() {
        final String fullName = "full_Name";
        when(message.getCreator()).thenReturn(userMinimal);
        when(message.getCreator().getFullName()).thenReturn(fullName);
        UserDecoration userDecoration = UserDecorationHelper.getUserDecoration(message, userId);
        errorCollector.checkThat(userDecoration.getName(), is(equalTo(fullName)));
    }
}