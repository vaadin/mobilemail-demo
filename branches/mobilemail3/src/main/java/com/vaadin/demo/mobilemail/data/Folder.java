package com.vaadin.demo.mobilemail.data;

import java.util.ArrayList;
import java.util.List;

/**
 * A folder can contain other folders or messages. A folder cannot contain both
 * folders and subfolders.
 */
public class Folder extends AbstractPojo {

    private static final long serialVersionUID = 1L;

    private List<AbstractPojo> children = new ArrayList<AbstractPojo>();

    /**
     * Constructor
     * 
     * @param parent
     *            The parent folder
     */
    public Folder() {
        this("");
    }

    /**
     * Constructor
     * 
     * @param name
     *            The name of the folder
     */
    public Folder(String name) {
        this.name = name;
    }

    /**
     * @return the children
     */
    public List<AbstractPojo> getChildren() {
        return children;
    }

    /**
     * @param children
     *            the children to set
     */
    public void setChildren(List<AbstractPojo> children) {
        this.children = children;
    }
}
