package com.vaadin.demo.mobilemail.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.vaadin.addon.touchkit.ui.NavigationBar;
import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickEvent;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.demo.mobilemail.MobileMailUI;
import com.vaadin.demo.mobilemail.data.AbstractPojo;
import com.vaadin.demo.mobilemail.data.DummyDataUtil;
import com.vaadin.demo.mobilemail.data.Folder;
import com.vaadin.demo.mobilemail.data.MailBox;
import com.vaadin.demo.mobilemail.data.Message;
import com.vaadin.demo.mobilemail.data.MessageStatus;
import com.vaadin.demo.mobilemail.data.MobileMailContainer;
import com.vaadin.demo.mobilemail.data.ParentFilter;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

/**
 * Displays accounts, mailboxes, message list hierarchically
 */
public class MailboxHierarchyView extends NavigationView {

	private static final long serialVersionUID = 1L;

	private final MobileMailContainer ds = DummyDataUtil.getContainer();

	private final Resource mailboxIcon = new ThemeResource(
			"../runo/icons/64/globe.png");

	static Resource reloadIcon = new ThemeResource(
			"graphics/reload-icon-2x.png");
	static Resource reloadIconWhite = new ThemeResource(
			"graphics/reload-icon-white-2x.png");

	private static Button reload;

	private static boolean horizontal = false;

	public MailboxHierarchyView(final MailboxHierarchyManager nav) {

		setCaption("Mailboxes");
		setWidth("100%");
		setHeight("100%");

		// Mailboxes do not have parents
		ds.setFilter(new ParentFilter(null));

		CssLayout root = new CssLayout();

		VerticalComponentGroup accounts = new VerticalComponentGroup();
		Label header = new Label("Accounts");
		header.setSizeUndefined();
		header.addStyleName("grey-title");
		root.addComponent(header);

		for (AbstractPojo itemId : ds.getItemIds()) {
			final MailBox mb = (MailBox) itemId;
			NavigationButton btn = new NavigationButton(mb.getName());
			if (mb.getName().length() > 20) {
				btn.setCaption(mb.getName().substring(0, 20) + "…");
			}
			btn.setIcon(mailboxIcon);
			btn.addClickListener(new NavigationButton.NavigationButtonClickListener() {

				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(NavigationButtonClickEvent event) {
					FolderHierarchyView v = new FolderHierarchyView(nav, mb,
							horizontal);
					nav.navigateTo(v);
				}
			});

			// Set new messages
			int newMessages = 0;
			for (Folder child : mb.getFolders()) {
				for (AbstractPojo p : child.getChildren()) {
					if (p instanceof Message) {
						Message msg = (Message) p;
						newMessages += msg.getStatus() == MessageStatus.NEW ? 1
								: 0;
					}
				}
			}
			if (newMessages > 0) {
				btn.setDescription(newMessages + "");
			}
			btn.addStyleName("pill");
			accounts.addComponent(btn);
		}

		root.addComponent(accounts);
		setContent(root);
		setToolbar(createToolbar());
	}

	static Component createToolbar() {
		return createToolbar(horizontal);
	}

	static Component createToolbar(boolean horizontal) {

		final NavigationBar toolbar = new NavigationBar();

		reload = new Button();
		reload.setIcon(horizontal ? reloadIcon : reloadIconWhite);
		reload.addStyleName("reload");
		reload.addStyleName("no-decoration");

		toolbar.setLeftComponent(reload);

		final SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy hh:mm");
		toolbar.setCaption("Updated "
				+ formatter.format(Calendar.getInstance().getTime()));

		reload.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				toolbar.setCaption("Updated "
						+ formatter.format(Calendar.getInstance().getTime()));
			}
		});

		UI touchKitApplication = (UI) MobileMailUI.getCurrent();
		if (touchKitApplication instanceof MobileMailUI) {
			MobileMailUI app = (MobileMailUI) touchKitApplication;
			if (app.isSmallScreenDevice()) {
				/*
				 * For small screen devices we add shortcut to new message below
				 * hierarcy views
				 */
				ClickListener showComposeview = new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						ComposeView cv = new ComposeView(true);
						cv.showRelativeTo(event.getButton());
					}
				};
				Button button = new Button(null, showComposeview);
				button.addStyleName("compose");
				button.setIcon(new ThemeResource("graphics/compose-icon-2x.png"));
				toolbar.setRightComponent(button);
				button.addStyleName("no-decoration");
			}
		}

		return toolbar;
	}

	public void setOrientation(boolean horizontal) {
		if (horizontal) {
			reload.setIcon(reloadIcon);
		} else {
			reload.setIcon(reloadIconWhite);
		}
		this.horizontal = horizontal;
	}
}
