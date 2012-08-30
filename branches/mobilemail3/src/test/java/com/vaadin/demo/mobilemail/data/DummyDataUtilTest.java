package com.vaadin.demo.mobilemail.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

public class DummyDataUtilTest {

    @Test
    public void testContainer() {

        MobileMailContainer container = (MobileMailContainer) DummyDataUtil
                .getContainer();

        // Container should contain 4 mailboxes
        assertEquals(4, container.rootItemIds().size());

        // Root items should be mailboxes
        for (Object p : container.rootItemIds()) {
            assertTrue("Root items should be mailboxes", p instanceof MailBox);
        }

        // Mailboxes should contain only folders and at least the default
        // folders
        for (Object p : container.rootItemIds()) {
            MailBox box = (MailBox) p;
            Collection<? extends AbstractPojo> folders = container
                    .getChildren(box);
            assertTrue("Each mailbox should contain at least 4 folders",
                    folders.size() >= 4);

            // The mailbox should only contain folders
            for (Object f : container.getChildren(box)) {
                assertTrue("Mailbox should only contain folders",
                        f instanceof Folder);
            }
        }
    }
}
