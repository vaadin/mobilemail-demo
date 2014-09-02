package com.vaadin.demo.mobilemail.ui;

import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.demo.mobilemail.data.MobileMailContainer;

/**
 * NavigationManager to display mailboxlist-mailboxes(-mailboxes*n)-messages
 * hierarchy.
 */
public class MailboxHierarchyManager extends NavigationManager {

    private static final long serialVersionUID = 1L;
    private MailboxHierarchyView mailboxHierarchyView;

    public MailboxHierarchyManager(MobileMailContainer ds) {
        setWidth("300px");
        addStyleName("mailboxes");
        mailboxHierarchyView = new MailboxHierarchyView(ds, this);
        navigateTo(mailboxHierarchyView);
    }
}
