package com.vaadin.demo.mobilemail.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.terminal.Page;
import com.vaadin.terminal.WrappedRequest;
import com.vaadin.ui.UI;

@Theme("mobilemail")
public class MobileMailWindow extends UI {

    private static final long serialVersionUID = 1L;

    public MobileMailWindow() {
        setImmediate(true);
    }

    @Override
    protected void init(WrappedRequest request) {
        Page.getCurrent().setTitle("MobileMail");
    }

}
