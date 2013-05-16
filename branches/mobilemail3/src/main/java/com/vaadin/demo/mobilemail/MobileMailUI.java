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

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.demo.mobilemail.ui.SmartphoneMainView;
import com.vaadin.demo.mobilemail.ui.TabletMainView;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.WebBrowser;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("mobilemail")
@Title("MobileMail")
@Widgetset("com.vaadin.demo.mobilemail.gwt.MobileMailWidgetSet")
public class MobileMailUI extends UI {

    public MobileMailUI() {
    }

    @SuppressWarnings("deprecation")
    public WebBrowser getBrowser() {
        VaadinSession context = UI.getCurrent().getSession();
        return context.getBrowser();
    }

    public boolean isSmallScreenDevice() {
        float viewPortWidth = getBrowser().getScreenWidth();
        return viewPortWidth < 600;
    }

    @Override
    protected void init(VaadinRequest request) {
        if (isSmallScreenDevice()) {
            setContent(new SmartphoneMainView());
        } else {
            setContent(new TabletMainView());
        }
        setImmediate(true);
    }

}
