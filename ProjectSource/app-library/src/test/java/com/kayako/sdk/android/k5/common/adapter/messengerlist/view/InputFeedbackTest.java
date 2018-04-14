package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;

public class InputFeedbackTest {

    private static final String GOOD = "GOOD";
    private static final String BAD = "BAD";

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Test
    public void enumValue() {
        errorCollector.checkThat(InputFeedback.RATING.GOOD.name(), is(GOOD));
        errorCollector.checkThat(InputFeedback.RATING.BAD.name(), is(BAD));
    }
}
