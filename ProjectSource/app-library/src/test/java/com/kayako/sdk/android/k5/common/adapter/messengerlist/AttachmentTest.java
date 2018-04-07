package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AttachmentTest {

    Attachment attachment;

    @Before
    public void setUp(){
        attachment = new Attachment(Attachment.TYPE.FILE);
    }
    @Test
    public void test_constructor1(){
        assertNotNull(attachment);
        assertNotEquals(Attachment.TYPE.URL, attachment.getType());
        assertEquals(Attachment.TYPE.FILE, attachment.getType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_constructor2(){
        Attachment attachment = new Attachment(null);
    }

    @Test
    public void test_getType(){
        Attachment attachment = new Attachment(Attachment.TYPE.FILE);
        assertEquals( Attachment.TYPE.FILE, attachment.getType());
        assertNotEquals(Attachment.TYPE.URL, attachment.getType());
    }

    @Test
    public void test_getContents(){
        Attachment attachment = new Attachment(Attachment.TYPE.FILE);
        assertEquals(1, attachment.getContents().size());
        assertEquals(Attachment.TYPE.FILE.toString(), attachment.getContents().get("type"));
    }
}
