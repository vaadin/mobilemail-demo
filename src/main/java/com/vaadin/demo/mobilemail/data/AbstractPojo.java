package com.vaadin.demo.mobilemail.data;

import java.io.Serializable;

public class AbstractPojo implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    protected AbstractPojo parent;

    protected String name = "";

    /**
     * @return the parent
     */
    public AbstractPojo getParent() {
        return parent;
    }

    /**
     * @param parent
     *            the parent to set
     */
    public void setParent(AbstractPojo parent) {
        this.parent = parent;
    }

    /**
     * @return the shortName
     */
    public String getName() {
        return name;
    }

    /**
     * @param shortName
     *            the shortName to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get an unique id for this pojo
     * 
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * Set a unique id for this pojo
     * 
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AbstractPojo) {
            AbstractPojo p = (AbstractPojo) obj;
            return p.getId() == getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return String.valueOf(getId()).hashCode();
    }
}
