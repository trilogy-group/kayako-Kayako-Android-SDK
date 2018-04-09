package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.TypingListItem;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.instanceOf;


public class TypingViewHelperTest {

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    private final UserViewModel userViewModel = new UserViewModel("avatar", "full_name", 1000L);
    private final TypingViewHelper typingViewHelper = new TypingViewHelper();

    @Test
    public void test_constructor(){
        errorCollector.checkThat(typingViewHelper, notNullValue());
    }

    @Test
    public void whenIsTypingFalseThensetTypingStatusReturnFalse() {
        errorCollector.checkThat(typingViewHelper.setTypingStatus(userViewModel, false), is(false));
    }

    @Test
    public void whenIsTypingTrueThensetTypingStatusReturnTrue() {
        errorCollector.checkThat(typingViewHelper.setTypingStatus(userViewModel, true), is(true));
    }

    @Test
    public void whenAgentTypingFalseThenGetTypingViewReturnEmptyList() {
        errorCollector.checkThat(typingViewHelper.getTypingViews(), notNullValue());
        errorCollector.checkThat(typingViewHelper.getTypingViews().size() == 0, is(true));
    }

    @Test
    public void whenAgentTypingStatusTrueThenGetTypingViewReturnListWithTypingListItem() {
        typingViewHelper.setTypingStatus(userViewModel, true);
        errorCollector.checkThat(typingViewHelper.getTypingViews().size() > 0, is(true));
        errorCollector.checkThat(typingViewHelper.getTypingViews().get(0), is(instanceOf(TypingListItem.class)));
    }
}
