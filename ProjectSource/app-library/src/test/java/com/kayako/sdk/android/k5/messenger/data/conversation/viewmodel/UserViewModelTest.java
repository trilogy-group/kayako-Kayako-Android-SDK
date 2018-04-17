package com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;

public class UserViewModelTest {

    private static final String AVATAR = "avatar";
    private static final String FULL_NAME = "full_name";
    private static final long LAST_ACTIVE_AT = 12_345L;
    private UserViewModel userViewModel;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        userViewModel = new UserViewModel(AVATAR, FULL_NAME, LAST_ACTIVE_AT);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        //Assert
        errorCollector.checkThat(userViewModel.getAvatar(), is(AVATAR));
        errorCollector.checkThat(userViewModel.getFullName(), is(FULL_NAME));
        errorCollector.checkThat(userViewModel.getLastActiveAt(), is(LAST_ACTIVE_AT));
    }

    @Test
    public void getContents() {
        //Act
        Map<String, String> contentsMap = userViewModel.getContents();

        //Assert
        errorCollector.checkThat(contentsMap.size(), is(3));
        errorCollector.checkThat(contentsMap.get("avatar"), is(AVATAR));
        errorCollector.checkThat(contentsMap.get("lastActiveAt"), is(String.valueOf(LAST_ACTIVE_AT)));
    }
}
