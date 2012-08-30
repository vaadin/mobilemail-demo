package com.vaadin.demo.mobilemail.data;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests the mailbox functionality
 */
public class MailBoxTest {

    @Test
    public void testGetName() {

        // Empty mailbox
        MailBox box = new MailBox("Test");
        assertEquals("Test", box.getName());

        // Mailbox with folders
        box = new MailBox("Test2");
        assertEquals("Test2", box.getName());
    }

    @Test
    public void testSetName() {

        MailBox box = new MailBox("Test");
        assertEquals("Test", box.getName());

        box.setName("NewName");
        assertEquals("NewName", box.getName());
    }
}
