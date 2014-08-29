package com.vaadin.demo.mobilemail.data;

/**
 * The status of a message.<br/>
 * A message can be {{@link #NEW} when first recieved in the inbox.
 * {@link #UNSENT} if saved in a drag box, {@link #SENT} when successfully sent,
 * {@link #READ} after a message was read and unread {@link #UNREAD} if not yet
 * read.
 * 
 */
public enum MessageStatus {
    /**
     * Message has just arrived in the inbox and waiting to be read
     */
    NEW,

    /**
     * Message has been written but not yet sent
     */
    UNSENT,

    /**
     * Message has successfully been sent
     */
    SENT,

    /**
     * Message has been read by the user
     */
    READ,

    /**
     * Message is still unread
     */
    UNREAD,

    /**
     * Message status has not yet been set
     */
    UNDEFINED,
}
