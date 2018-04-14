package com.kayako.sdk.android.k5.messenger.data.conversationstarter;

import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.hamcrest.CoreMatchers.is;

@RunWith(MockitoJUnitRunner.class)
public class LastActiveAgentsDataTest {

    private static final String BRAND_NAME = "brand_name";
    private static final String NEW_BRAND_NAME = "new_brand_name";
    private static final long AVERAGE_REPLY_TIME = 1_000L;
    private static final long NEW_AVERAGE_REPLY_TIME = 1_200L;
    private static final String EXCEPTION_MESSAGE =
            "Brand Name and AverageReplyTime can not be null!";
    private final LastActiveAgentsData one =
            new LastActiveAgentsData(BRAND_NAME, AVERAGE_REPLY_TIME);
    private final LastActiveAgentsData same =
            new LastActiveAgentsData(BRAND_NAME, AVERAGE_REPLY_TIME);
    private final LastActiveAgentsData secondSame =
            new LastActiveAgentsData(BRAND_NAME, AVERAGE_REPLY_TIME);
    private final LastActiveAgentsData other =
            new LastActiveAgentsData(BRAND_NAME, NEW_AVERAGE_REPLY_TIME);
    private final LastActiveAgentsData secondOther =
            new LastActiveAgentsData(NEW_BRAND_NAME, AVERAGE_REPLY_TIME);

    @Mock
    private UserViewModel userViewModel;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Test
    public void whenValidParamsThenValidObject() {
        errorCollector.checkThat(one.getBrandName(), is(BRAND_NAME));
        errorCollector.checkThat(one.getAverageReplyTime(), is(AVERAGE_REPLY_TIME));
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        final LastActiveAgentsData lastActiveAgentsDataLocal =
                new LastActiveAgentsData(BRAND_NAME, AVERAGE_REPLY_TIME, userViewModel,
                        userViewModel, userViewModel);
        errorCollector.checkThat(lastActiveAgentsDataLocal.getBrandName(), is(BRAND_NAME));
        errorCollector.checkThat(lastActiveAgentsDataLocal.getAverageReplyTime(), is(AVERAGE_REPLY_TIME));
        errorCollector.checkThat(lastActiveAgentsDataLocal.getUser1(), is(userViewModel));
        errorCollector.checkThat(lastActiveAgentsDataLocal.getUser2(), is(userViewModel));
        errorCollector.checkThat(lastActiveAgentsDataLocal.getUser3(), is(userViewModel));
    }

    @Test
    public void whenNullBrandNameThenIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(EXCEPTION_MESSAGE);
        new LastActiveAgentsData(null, AVERAGE_REPLY_TIME);
    }

    @Test
    public void whenNullAverageReplyTimeThenIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(EXCEPTION_MESSAGE);
        new LastActiveAgentsData(BRAND_NAME, null, userViewModel,
                userViewModel, userViewModel);
    }

    @Test
    public void reflexivity() {
        errorCollector.checkThat(one, is(one));
    }

    @Test
    public void nullInequality() {
        errorCollector.checkThat(one.equals(null), is(false));
    }

    @Test
    public void symmetry() {
        errorCollector.checkThat(one, is(same));
        errorCollector.checkThat(same, is(one));
        errorCollector.checkThat(one.hashCode(), is(same.hashCode()));
        errorCollector.checkThat(one.equals(other), is(false));
        errorCollector.checkThat(other.equals(one), is(false));
        errorCollector.checkThat(one.equals(secondOther), is(false));
        errorCollector.checkThat(secondOther.equals(one), is(false));
    }

    @Test
    public void transitivity() {
        errorCollector.checkThat(one, is(same));
        errorCollector.checkThat(same, is(secondSame));
        errorCollector.checkThat(one, is(secondSame));
        errorCollector.checkThat(one.hashCode(), is(same.hashCode()));
        errorCollector.checkThat(one.hashCode(), is(secondSame.hashCode()));
    }
}
