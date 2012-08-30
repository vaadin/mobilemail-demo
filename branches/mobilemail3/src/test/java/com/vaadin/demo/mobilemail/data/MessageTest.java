package com.vaadin.demo.mobilemail.data;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class MessageTest {

    @Test
    public void testGetSetFields() {

        Message msg = new Message("from", "to", "subject");
        List<MessageField> fields = msg.getFields();
        assertEquals(3, fields.size());
        assertEquals("From", fields.get(0).getCaption());
        assertEquals("from", fields.get(0).getValue());
        assertEquals("To", fields.get(1).getCaption());
        assertEquals("to", fields.get(1).getValue());
        assertEquals("Subject", fields.get(2).getCaption());
        assertEquals("subject", fields.get(2).getValue());

        msg = new Message("from", "to", "subject", "content");
        fields = msg.getFields();
        assertEquals(4, fields.size());
        assertEquals("From", fields.get(0).getCaption());
        assertEquals("from", fields.get(0).getValue());
        assertEquals("To", fields.get(1).getCaption());
        assertEquals("to", fields.get(1).getValue());
        assertEquals("Subject", fields.get(2).getCaption());
        assertEquals("subject", fields.get(2).getValue());
        assertEquals("Body", fields.get(3).getCaption());
        assertEquals("content", fields.get(3).getValue());

        // Add a custom field
        MessageField cc = new MessageField("CC", "cc");
        fields = new ArrayList<MessageField>(msg.getFields());
        fields.add(cc);
        msg.setFields(Collections.unmodifiableList(fields));
        fields = msg.getFields();
        assertEquals(5, fields.size());
        assertEquals("From", fields.get(0).getCaption());
        assertEquals("from", fields.get(0).getValue());
        assertEquals("To", fields.get(1).getCaption());
        assertEquals("to", fields.get(1).getValue());
        assertEquals("Subject", fields.get(2).getCaption());
        assertEquals("subject", fields.get(2).getValue());
        assertEquals("Body", fields.get(3).getCaption());
        assertEquals("content", fields.get(3).getValue());
        assertEquals("CC", fields.get(4).getCaption());
        assertEquals("cc", fields.get(4).getValue());
    }

    @Test
    public void testGetSetStatus() {

        // By default the message should be undefined
        Message m = new Message("from", "to", "subject");
        assertEquals(MessageStatus.UNDEFINED, m.getStatus());

        // Setting status to read
        m.setStatus(MessageStatus.READ);

        // Status should now be read
        assertEquals(MessageStatus.READ, m.getStatus());
    }
}
