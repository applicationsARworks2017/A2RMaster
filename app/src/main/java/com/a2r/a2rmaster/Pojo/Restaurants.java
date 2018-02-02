package com.a2r.a2rmaster.Pojo;

/**
 * Created by Amaresh on 2/2/18.
 */

public class Restaurants {
    String id,title,address,logo,gst,mobile_no,added_by,is_approved,created;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAdded_by() {
        return added_by;
    }

    public void setAdded_by(String added_by) {
        this.added_by = added_by;
    }

    public String getIs_approved() {
        return is_approved;
    }

    public void setIs_approved(String is_approved) {
        this.is_approved = is_approved;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", title = " + title + ", address = " + address + ", logo = " + logo +
                ", gst = " + gst + ", mobile_no = " + mobile_no + ", added_by = " + added_by +
                ", is_approved = " + is_approved + ", created = " + created+ "]";
    }
}
