package com.a2r.a2rmaster.Pojo;

/**
 * Created by mobileapplication on 2/5/18.
 */

public class ItemList {
    String id,title,is_active,created,modified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", title = " + title + ", is_active = " + is_active +
                ", modified = " + modified + ", created = " + created+ "]";
    }
}
