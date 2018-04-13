package com.kayako.sdk.android.k5.kre.data;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;

public class ChangedPropertiesTest {

    private static final long LAST_ASSIGNED_AT = 1_234L;
    private static int POST_COUNT = 5;
    private static final long LAST_REPLIER = 1_235L;
    private static final long LAST_UPDATED_BY = 1_236L;
    private static final long UPDATED_AT = 1_237L;
    private static final long LAST_AGENT_ACTIVITY_AT = 1_238L;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        final ChangedProperties changedProperties = new ChangedProperties(
                LAST_ASSIGNED_AT, POST_COUNT, LAST_REPLIER, LAST_UPDATED_BY, UPDATED_AT,
                LAST_AGENT_ACTIVITY_AT);
        errorCollector.checkThat(changedProperties.getLastAssignedAt(), is(LAST_ASSIGNED_AT));
        errorCollector.checkThat(changedProperties.getPostCount(), is(POST_COUNT));
        errorCollector.checkThat(changedProperties.getLastReplier(), is(LAST_REPLIER));
        errorCollector.checkThat(changedProperties.getLastUpdatedBy(), is(LAST_UPDATED_BY));
        errorCollector.checkThat(changedProperties.getUpdatedAt(), is(UPDATED_AT));
        errorCollector.checkThat(changedProperties.getLastAgentActivityAt(), is(LAST_AGENT_ACTIVITY_AT));
    }
}
