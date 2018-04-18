package com.kayako.sdk.android.k5.messenger.data.conversationstarter;

import com.kayako.sdk.android.k5.EqualsAndHashTest;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AssignedAgentDataTest extends EqualsAndHashTest<AssignedAgentData> {

    private static final String EXCEPTION_MESSAGE = "User and properties can not be null!";
    private static final boolean IS_ACTIVE = Boolean.TRUE;
    private static final String FULL_NAME = "full_name";
    private static final String AVATAR = "avatar";
    private static final long LAST_ACTIVE_AT = 1_000L;

    @Mock
    private UserViewModel userViewModel;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        when(userViewModel.getFullName()).thenReturn(FULL_NAME);
        when(userViewModel.getAvatar()).thenReturn(AVATAR);
        when(userViewModel.getLastActiveAt()).thenReturn(LAST_ACTIVE_AT);
        one = new AssignedAgentData(userViewModel, IS_ACTIVE);
        same = new AssignedAgentData(userViewModel, IS_ACTIVE);
        secondSame = new AssignedAgentData(userViewModel, IS_ACTIVE);
        other = new AssignedAgentData(userViewModel, Boolean.FALSE);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        //Assert
        errorCollector.checkThat(one.getUser(), is(userViewModel));
        errorCollector.checkThat(one.isActive(), is(IS_ACTIVE));
    }

    @Test
    public void whenUserNUllThenIllegalArgumentException() {
        //Contract
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(EXCEPTION_MESSAGE);

        //Act
        new AssignedAgentData(null, IS_ACTIVE);
    }

    @Test
    public void whenUserFullNameNullThenIllegalArgumentException() {
        //Arrange
        when(userViewModel.getFullName()).thenReturn(null);

        //Assert
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(EXCEPTION_MESSAGE);

        //Act
        new AssignedAgentData(userViewModel, IS_ACTIVE);
    }
}
