package com.vaadin.demo.mobilemail.ui;

import com.vaadin.addon.touchkit.ui.NavigationManager;

/**
 * NavigationManager to display mailboxlist-mailboxes(-mailboxes*n)-messages
 * hierarchy.
 */
public class MailboxHierarchyManager extends NavigationManager {

    private static final long serialVersionUID = 1L;
    private MailboxHierarchyView mailboxHierarchyView;
    
    public MailboxHierarchyManager() {
        setWidth("300px");
        addStyleName("mailboxes");
        mailboxHierarchyView = new MailboxHierarchyView(this);
        navigateTo(mailboxHierarchyView);
    }
    
    public void setOrientation(boolean horizontal) {
        mailboxHierarchyView.setOrientation(horizontal);
    }
}
