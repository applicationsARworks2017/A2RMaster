package com.a2r.a2rmaster.Pojo;

/**
 * Created by Amaresh on 1/28/18.
 */

public class User {

    String name,id,username,mobile,email,user_type_id,shop_id,created;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", name = " + name + ", username = " + username + ", " +
                "mobile = " + mobile + ", email = " + email + ", user_type_id = " + user_type_id +
                ", shop_id = " + shop_id + ", created = " + created+"]";
    }
}
