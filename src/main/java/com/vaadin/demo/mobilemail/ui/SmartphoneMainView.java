package com.vaadin.demo.mobilemail.ui;

import com.vaadin.demo.mobilemail.data.Message;
import com.vaadin.demo.mobilemail.data.MobileMailContainer;

/**
 * Main view for smartphones.
 * 
 * <p>
 * Extends {@link MailboxHierarchyManager} (shared with TabletMainView), but
 * shows also full messages in the panel when messages is selected.
 * 
 */
@SuppressWarnings("serial")
public class SmartphoneMainView extends MailboxHierarchyManager implements
        MainView {

    private final MessageView messageView;

    public SmartphoneMainView(MobileMailContainer ds) {
        super(ds);
        messageView = new MessageView(ds, true);
        setWidth("100%"); // to support wider horizontal view
        addStyleName("phone");
    }

    @Override
    public void setMessage(Message message, MessageHierarchyView messageList) {
        messageView.setMessage(message, messageList);
        // Navigation panel does not override previous component. As the
        // messagelist before message view changes we'll need to manually update
        // reference to previous component
        if (getCurrentComponent() == this) {
            messageView.setPreviousComponent(getPreviousComponent());
        } else {
            messageView.setPreviousComponent(getCurrentComponent());
        }

        // show messageView in current navigationPanel (this)
        navigateTo(messageView);
    }

    @Override
    public void updateNewMessages() {
        messageView.updateNewMessages();
    }

    @Override
    public Message getMessage() {
        return messageView.getMessage();
    }

}
