package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.UserDecoration;
import com.kayako.sdk.helpcenter.user.UserMinimal;
import com.kayako.sdk.messenger.message.Message;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDecorationHelperTest {

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Mock
    Message message;

    @Mock
    UserMinimal userMinimal;

    @Test
    public void whenNullMessageThenReturnNull() {
       UserDecoration userDecoration = UserDecorationHelper.getUserDecoration(null, 1L);
       errorCollector.checkThat(userDecoration, nullValue());
    }

    @Test
    public void whenCreatorNullThenNull() {
        UserDecoration userDecoration = UserDecorationHelper.getUserDecoration(message, 1L);
        errorCollector.checkThat(userDecoration, nullValue());
    }

    @Test
    public void whenValidParamsThenObjectCreated() {
        when(message.getCreator()).thenReturn(userMinimal);
        when(message.getCreator().getFullName()).thenReturn("full_name");
        UserDecoration userDecoration = UserDecorationHelper.getUserDecoration(message, 1L);
        errorCollector.checkThat(userDecoration, notNullValue());
        errorCollector.checkThat(userDecoration.getName(), is(equalTo("full_name")));
    }

}
