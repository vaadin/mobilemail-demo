package com.vaadin.demo.mobilemail.data;

import java.util.ArrayList;
import java.util.List;

/**
 * A mailbox contains details about the account as well as links to the root
 * folders of the mail box.
 */
public class MailBox extends AbstractPojo {

    private static final long serialVersionUID = 1L;

    private String emailAddress;

    private String mailServerUrl;

    private String mailServerUsername;

    private String mailServerPassword;

    private List<Folder> folders = new ArrayList<Folder>();

    /**
     * Default constructor
     */
    public MailBox() {
        this("");
    }

    /**
     * Constructor
     * 
     * @param name
     *            The name of the mail box
     */
    public MailBox(String name) {
        this.name = name;
    }

    /**
     * @return the emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress
     *            the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return the mailServerUrl
     */
    public String getMailServerUrl() {
        return mailServerUrl;
    }

    /**
     * @param mailServerUrl
     *            the mailServerUrl to set
     */
    public void setMailServerUrl(String mailServerUrl) {
        this.mailServerUrl = mailServerUrl;
    }

    /**
     * @return the mailServerUsername
     */
    public String getMailServerUsername() {
        return mailServerUsername;
    }

    /**
     * @param mailServerUsername
     *            the mailServerUsername to set
     */
    public void setMailServerUsername(String mailServerUsername) {
        this.mailServerUsername = mailServerUsername;
    }

    /**
     * @return the mailServerPassword
     */
    public String getMailServerPassword() {
        return mailServerPassword;
    }

    /**
     * @param mailServerPassword
     *            the mailServerPassword to set
     */
    public void setMailServerPassword(String mailServerPassword) {
        this.mailServerPassword = mailServerPassword;
    }

    /**
     * @return the folders
     */
    public List<Folder> getFolders() {
        return folders;
    }

    /**
     * @param folders
     *            the folders to set
     */
    public void setFolders(List<Folder> folders) {
        this.folders = folders;
    }
}
