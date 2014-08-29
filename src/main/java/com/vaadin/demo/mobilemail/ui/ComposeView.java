package com.vaadin.demo.mobilemail.ui;

import com.vaadin.addon.touchkit.ui.EmailField;
import com.vaadin.addon.touchkit.ui.NavigationBar;
import com.vaadin.addon.touchkit.ui.Popover;
import com.vaadin.demo.mobilemail.MobileMailUI;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ComposeView extends Popover implements ClickListener {

    VerticalLayout content = new VerticalLayout();
    NavigationBar navigationBar = new NavigationBar();
    EmailField to = new EmailField("To:");
    EmailField cc = new EmailField("Cc/Bcc:");
    TextField subject = new TextField("Title:");
    TextArea body = new TextArea();
    private final boolean smartphone;

    public ComposeView(boolean smartphone) {
        addStyleName("new-message");

        navigationBar.setWidth("100%");
        navigationBar.setCaption("New Message");
        navigationBar.setLeftComponent(new Button("Cancel", this));
        navigationBar.setRightComponent(new Button("Send", this));

        content.setSizeFull();
        content.addComponent(navigationBar);

        FormLayout fields = new FormLayout();
        fields.setMargin(new MarginInfo(false, true, false, true));
        fields.setSpacing(false);

        to.setWidth("100%");
        fields.addComponent(to);

        cc.setWidth("100%");
        fields.addComponent(cc);

        subject.setWidth("100%");
        fields.addComponent(subject);

        content.addComponent(fields);

        body.setSizeFull();
        content.addComponent(body);

        content.setExpandRatio(body, 1);
        setContent(content);

        setModal(true);
        setClosable(false);
        if (smartphone) {
            setSizeFull();
        } else {
            setHeight("100%");
            center();
        }
        this.smartphone = smartphone;
    }

    @Override
    public void attach() {
        super.attach();
        if (!smartphone) {
            if ((((MobileMailUI) MobileMailUI.getCurrent()))
                    .getBrowser().getScreenWidth() > 800) {
                setWidth("80%");
            } else {
                setWidth("100%");
            }
        }
    }

    @Override
    public void buttonClick(ClickEvent event) {
        getParent().getUI().removeWindow(this);
    }

}
