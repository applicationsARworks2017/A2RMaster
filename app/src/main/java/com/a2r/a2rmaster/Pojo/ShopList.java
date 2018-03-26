package com.a2r.a2rmaster.Pojo;

/**
 * Created by Amaresh on 2/2/18.
 */

public class ShopList {
    String id,title,address,logo,gst,mobile_no,added_by,created,modified,is_approved,no_of_table,
            bill_prefix,frequency;

    public ShopList(String id, String title, String address, String logo, String gst,
                    String mobile_no, String added_by, String created, String modified,
                    String is_approved, String no_of_table, String bill_prefix, String frequency) {
        this.id=id;
        this.title=title;
        this.address=address;
        this.logo=logo;
        this.gst=gst;
        this.mobile_no=mobile_no;
        this.added_by=added_by;
        this.created=created;
        this.modified=modified;
        this.is_approved=is_approved;
        this.no_of_table=no_of_table;
        this.bill_prefix=bill_prefix;
        this.frequency=frequency;
    }

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

}
