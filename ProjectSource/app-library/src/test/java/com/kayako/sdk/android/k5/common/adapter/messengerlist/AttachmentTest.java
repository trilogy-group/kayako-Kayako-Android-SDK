package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

public class AttachmentTest {

    private Attachment attachment;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        attachment = new Attachment(Attachment.TYPE.FILE);
    }

    @Test
    public void givenValidParamsThenObjectCreated() {
        errorCollector.checkThat(attachment, notNullValue());
        errorCollector.checkThat(Attachment.TYPE.URL, not(attachment.getType()));
        errorCollector.checkThat(Attachment.TYPE.FILE, is(equalTo(attachment.getType())));
    }

    @Test
    public void whenNullTypeThenIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(containsString("Invalid argument"));
        Attachment attachment = new Attachment(null);
    }

    @Test
    public void getContents() {
        errorCollector.checkThat(1, is(attachment.getContents().size()));
        errorCollector.checkThat(Attachment.TYPE.FILE.name(), is(equalTo(attachment.getContents().get("type"))));
    }
}
