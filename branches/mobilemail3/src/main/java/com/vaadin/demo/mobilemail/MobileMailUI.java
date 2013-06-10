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

import java.net.Inet4Address;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.demo.mobilemail.ui.SmartphoneMainView;
import com.vaadin.demo.mobilemail.ui.TabletMainView;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServletRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.WebBrowser;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import fi.jasoft.qrcode.QRCode;

@SuppressWarnings("serial")
@Theme("mobilemail")
@Title("Vaadin Mail Demo")
@Widgetset("com.vaadin.demo.mobilemail.gwt.MobileMailWidgetSet")
@Push(PushMode.AUTOMATIC)
@PreserveOnRefresh
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
        return viewPortWidth < 480;
    }

    public boolean isLargeScreenDevice() {
        float viewPortWidth = getBrowser().getScreenWidth();
        return viewPortWidth > 1024;
    }

    @Override
    protected void init(VaadinRequest request) {
        if (isSmallScreenDevice()) {
            setContent(new SmartphoneMainView());
        } else {
            if (isLargeScreenDevice()) {
                showNonMobileNotification(request);
            }
            setContent(new TabletMainView());
        }
        setImmediate(true);
    }

    private void showNonMobileNotification(VaadinRequest request) {
        VaadinServletRequest vsr = (VaadinServletRequest) request;

        try {
            URL appUrl = ((MobileMailServlet) vsr.getService().getServlet())
                    .getApplicationUrl(vsr);
            String myIp = Inet4Address.getLocalHost().getHostAddress();
            String qrCodeUrl = appUrl.toString().replaceAll("localhost", myIp);

            QRCode qrCode = new QRCode();
            qrCode.setHeight(150.0f, Unit.PIXELS);
            qrCode.setWidth(150.0f, Unit.PIXELS);
            qrCode.setValue(qrCodeUrl);

            Label info = new Label(
                    "You appear to be running this demo on a non-portable device. "
                            + "MobileMail is intended for touch devices primarily. "
                            + "Please read the QR code on your touch device to access the demo.");
            info.setWidth("100%");

            HorizontalLayout qrCodeLayout = new HorizontalLayout(qrCode, info);
            qrCodeLayout.setSizeFull();
            qrCodeLayout.setSpacing(true);
            qrCodeLayout.setMargin(true);
            qrCodeLayout.setExpandRatio(info, 1.0f);

            Window window = new Window(null, qrCodeLayout);
            window.setWidth(500.0f, Unit.PIXELS);
            window.setHeight(200.0f, Unit.PIXELS);
            window.addStyleName("qr-code");
            window.setModal(true);
            window.setResizable(false);
            window.setDraggable(false);
            addWindow(window);
            window.center();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
