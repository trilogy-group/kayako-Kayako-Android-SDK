package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

public class AttachmentTest {

    @Rule
    public final ErrorCollector errorCollector  = new ErrorCollector();

    private Attachment attachment;

    @Before
    public void setUp(){
        attachment = new Attachment(Attachment.TYPE.FILE);
    }

    @Test
    public void givenValidParamsThenObjectCreated(){
        errorCollector.checkThat(attachment, notNullValue());
        errorCollector.checkThat(Attachment.TYPE.URL,not(attachment.getType()));
        errorCollector.checkThat(Attachment.TYPE.FILE, equalTo(attachment.getType()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNullTypeThenIllegalArgumentException(){
        Attachment attachment = new Attachment(null);
    }

    @Test
    public void getType(){
        errorCollector.checkThat(Attachment.TYPE.URL,not(attachment.getType()));
        errorCollector.checkThat(Attachment.TYPE.FILE, equalTo(attachment.getType()));
    }

    @Test
    public void getContents(){
        errorCollector.checkThat(1, equalTo(attachment.getContents().size()));
        errorCollector.checkThat(Attachment.TYPE.FILE.toString(), equalTo(attachment.getContents().get("type")));
    }
}
