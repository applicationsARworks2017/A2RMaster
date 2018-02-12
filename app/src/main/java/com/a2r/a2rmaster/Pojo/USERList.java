package com.a2r.a2rmaster.Pojo;

/**
 * Created by mobileapplication on 2/12/18.
 */

public class USERList {
    String id,name,mobile,email,user_type_id,added_by,shop_id,created,modified,shop_title,address,
            shop_user_type_id,shop_user_type_title,shop_user_is_active;

    public USERList(String id, String name, String mobile, String email, String user_type_id,
                    String added_by, String shop_id, String created, String modified, String shop_title,
                    String address, String shop_user_type_id, String shop_user_type_title, String shop_user_is_active) {
        this.id=id;
        this.name=name;
        this.mobile=mobile;
        this.email=email;
        this.user_type_id=user_type_id;
        this.added_by=added_by;
        this.shop_id=shop_id;
        this.created=created;
        this.modified=modified;
        this.shop_title=shop_title;
        this.address=address;
        this.shop_user_type_id=shop_user_type_id;
        this.shop_user_type_title=shop_user_type_title;
        this.shop_user_is_active=shop_user_is_active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_type_id() {
        return user_type_id;
    }

    public void setUser_type_id(String user_type_id) {
        this.user_type_id = user_type_id;
    }

    public String getAdded_by() {
        return added_by;
    }

    public void setAdded_by(String added_by) {
        this.added_by = added_by;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
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

    public String getShop_title() {
        return shop_title;
    }

    public void setShop_title(String shop_title) {
        this.shop_title = shop_title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShop_user_type_id() {
        return shop_user_type_id;
    }

    public void setShop_user_type_id(String shop_user_type_id) {
        this.shop_user_type_id = shop_user_type_id;
    }

    public String getShop_user_type_title() {
        return shop_user_type_title;
    }

    public void setShop_user_type_title(String shop_user_type_title) {
        this.shop_user_type_title = shop_user_type_title;
    }

    public String getShop_user_is_active() {
        return shop_user_is_active;
    }

    public void setShop_user_is_active(String shop_user_is_active) {
        this.shop_user_is_active = shop_user_is_active;
    }


}
