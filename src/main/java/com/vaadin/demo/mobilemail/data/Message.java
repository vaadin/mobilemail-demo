package com.vaadin.demo.mobilemail.data;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Message extends AbstractPojo {

    private static final long serialVersionUID = 1L;

    private Date timestamp = new Date();

    private List<MessageField> fields;

    /**
     * The status of the message, by default it is set as undefined
     */
    private MessageStatus status = MessageStatus.UNDEFINED;

    /**
     * Constructor
     * 
     * @param from
     *            Email address from who the email is sent
     * @param to
     *            The recipients email address
     * @param subject
     *            The subject of the email
     */
    public Message(String from, String to, String subject) {
        setFields(Arrays.asList(new MessageField("From", from),
                new MessageField("To", to),
                new MessageField("Subject", subject)));
    }

    /**
     * Constructor
     * 
     * @param from
     *            Email address from who the email is sent
     * @param to
     *            The recipients email address
     * @param subject
     *            The subject of the email
     * @param content
     *            The body part of the email
     */
    public Message(String from, String to, String subject, String content) {
        setFields(Arrays.asList(new MessageField("From", from),
                new MessageField("To", to),
                new MessageField("Subject", subject), new MessageField("Body",
                        content)));
    }

    /**
     * @return the fields
     */
    public List<MessageField> getFields() {
        return fields;
    }

    /**
     * @param fields
     *            the fields to set
     */
    public void setFields(List<MessageField> fields) {
        this.fields = fields;
    }

    /**
     * @return the status
     */
    public MessageStatus getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp
     *            the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Fetches a field by matching its caption
     * 
     * @param field
     *            The field caption to match
     */
    public MessageField getMessageField(String field) {
        if (field != null) {
            for (MessageField f : fields) {
                if (f.getCaption().toLowerCase().equals(field.toLowerCase())) {
                    return f;
                }
            }
        }
        return null;
    }
}
