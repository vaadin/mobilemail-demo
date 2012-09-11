/* 
 * Copyright 2009 IT Mill Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.vaadin.demo.mobilemail;

import com.vaadin.addon.touchkit.ui.TouchKitUI;
import com.vaadin.annotations.Theme;
import com.vaadin.demo.mobilemail.ui.SmartphoneMainView;
import com.vaadin.demo.mobilemail.ui.TabletMainView;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.WebBrowser;
import com.vaadin.server.WrappedRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("mobilemail")
public class MobileMailUI extends TouchKitUI {

    /*
     * static CustomizedSystemMessages customizedSystemMessages = new
     * CustomizedSystemMessages();
     * 
     * static { // reload on session expired
     * customizedSystemMessages.setSessionExpiredCaption("");
     * customizedSystemMessages.setSessionExpiredMessage(""); }
     * 
     * public static SystemMessages getSystemMessages() { return
     * customizedSystemMessages; }
     */

    private static final String MSG = "<h1>Ooops...</h1> You accessed this demo "
            + "with a browser that is currently not supported by TouchKit. "
            + "TouchKit is "
            + "ment to be used with modern webkit based mobile browsers, "
            + "e.g. with iPhone. Curretly those "
            + "cover huge majority of actively used mobile browsers. "
            + "Support will be extended as other mobile browsers develop "
            + "and gain popularity. Testing ought to work with desktop "
            + "Safari or Chrome as well.";

    public MobileMailUI() {
    }

    public WebBrowser getBrowser() {
        VaadinSession context = UI.getCurrent().getSession();
        return context.getBrowser();
    }

    public boolean isSmallScreenDevice() {
        float viewPortWidth = getBrowser().getScreenWidth();
        return viewPortWidth < 600;
    }

    private ComponentContainer getFallbackContent() {
        VerticalLayout view = new VerticalLayout();
        view.addComponent(new Label(MSG, ContentMode.HTML));
        return view;
    }

    @Override
    protected void init(WrappedRequest request) {
        if (!(getBrowser().isTouchDevice() || getBrowser().isChrome() || getBrowser()
                .isSafari())) {
            setContent(getFallbackContent());
        } else {
            if (isSmallScreenDevice()) {
                setContent(new SmartphoneMainView());
            } else {
                setContent(new TabletMainView());
            }
        }
        Page.getCurrent().setTitle("MobileMail");
        setImmediate(true);
    }

}
