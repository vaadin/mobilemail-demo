package com.vaadin.demo.mobilemail.data;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FolderTest {

    @Test
    public void testGetName() {
        Folder f = new Folder("Folder");
        assertEquals("Folder", f.getName());
    }

    @Test
    public void testSetName() {
        Folder f = new Folder("Folder");
        assertEquals("Folder", f.getName());

        f.setName("NewName");
        assertEquals("NewName", f.getName());
    }
}
