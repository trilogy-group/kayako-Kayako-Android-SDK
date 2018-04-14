package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.TypingListItem;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.instanceOf;

public class TypingViewHelperTest {

    private static final String AVATAR = "avatar";
    private static final String FULL_NAME = "full_name";
    private static final long LAST_ACTIVE_AT = 1_000L;
    private UserViewModel userViewModel;
    private TypingViewHelper typingViewHelper;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        userViewModel = new UserViewModel(AVATAR, FULL_NAME, LAST_ACTIVE_AT);
        typingViewHelper = new TypingViewHelper();
    }

    @Test
    public void validObjectCreation() {
        errorCollector.checkThat(typingViewHelper, notNullValue());
    }

    @Test
    public void whenIsTypingFalseThenSetTypingStatusReturnFalse() {
        errorCollector.checkThat(typingViewHelper.setTypingStatus(userViewModel, false), is(false));
    }

    @Test
    public void whenIsTypingTrueThenSetTypingStatusReturnTrue() {
        errorCollector.checkThat(typingViewHelper.setTypingStatus(userViewModel, true), is(true));
    }

    @Test
    public void whenAgentTypingFalseThenGetTypingViewReturnEmptyList() {
        errorCollector.checkThat(typingViewHelper.getTypingViews().isEmpty(), is(true));
    }

    @Test
    public void whenAgentTypingStatusTrueThenGetTypingViewReturnListWithTypingListItem() {
        typingViewHelper.setTypingStatus(userViewModel, true);
        errorCollector.checkThat(typingViewHelper.getTypingViews().isEmpty(), is(false));
        errorCollector.checkThat(typingViewHelper.getTypingViews().get(0), is(instanceOf(TypingListItem.class)));
    }
}
