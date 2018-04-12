package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.UserDecoration;
import com.kayako.sdk.helpcenter.user.UserMinimal;
import com.kayako.sdk.messenger.message.Message;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDecorationHelperTest {

    private static final long USER_ID = 1L;

    @Mock
    private Message message;

    @Mock
    private UserMinimal userMinimal;

    @Test
    public void whenNullMessageThenReturnNull() {
       final UserDecoration userDecoration = UserDecorationHelper.getUserDecoration(null, USER_ID);
       assertNull(userDecoration);
    }

    @Test
    public void whenMessageCreatorNullThenNull() {
        final UserDecoration userDecoration = UserDecorationHelper.getUserDecoration(message, USER_ID);
        assertNull(userDecoration);
    }

    @Test
    public void whenValidParamsThenObjectCreated() {
        //Arrange
        final String fullName = "full_Name";
        when(message.getCreator()).thenReturn(userMinimal);
        when(message.getCreator().getFullName()).thenReturn(fullName);

        //Act
        final UserDecoration userDecoration = UserDecorationHelper.getUserDecoration(message, USER_ID);

        //Assert
        assertEquals(fullName, userDecoration.getName());
    }
}
