package com.vaadin.demo.mobilemail.data;

import java.io.Serializable;

/**
 * A field in a message
 */
public class MessageField implements Serializable {

    private static final long serialVersionUID = 1L;

    private String caption;

    private String value;

    /**
     * Constructor
     */
    public MessageField() {
        this("");
    }

    /**
     * Constructor
     * 
     * @param caption
     *            The caption of the field
     */
    public MessageField(String caption) {
        this(caption, null);
    }

    /**
     * Constructor
     * 
     * @param caption
     * @param value
     */
    public MessageField(String caption, String value) {
        this.caption = caption;
        this.value = value;
    }

    /**
     * Gets the caption of the field
     * 
     * @return A string describing the function of this field
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Sets the caption of the field.
     * 
     * @param caption
     *            A string describing the function of this field
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * Get the content of the field
     * 
     * @return The content of this field
     */
    public String getValue() {
        return value;
    }

    /**
     * Set the content of the field
     * 
     * @param value
     *            A string content
     */
    public void setValue(String value) {
        this.value = value;
    }
}
