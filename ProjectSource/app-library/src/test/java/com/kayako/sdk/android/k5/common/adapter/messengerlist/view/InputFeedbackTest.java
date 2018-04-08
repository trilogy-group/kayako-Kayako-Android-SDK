package com.kayako.sdk.android.k5.common.adapter.messengerlist.view;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class InputFeedbackTest {

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Test
    public void enumValue(){
        errorCollector.checkThat(InputFeedback.RATING.GOOD.name(), is(equalTo("GOOD")));
        errorCollector.checkThat(InputFeedback.RATING.BAD.name(), is(equalTo("BAD")));
    }

}
