package com.a2r.a2rmaster.Pojo;

/**
 * Created by mobileapplication on 2/5/18.
 */

public class ItemList {
    String id,name,is_active,created,modified,product_type_id,product_category_id,price,added_by,shop_id,image;

    public String getProduct_type_id() {
        return product_type_id;
    }

    public void setProduct_type_id(String product_type_id) {
        this.product_type_id = product_type_id;
    }

    public String getProduct_category_id() {
        return product_category_id;
    }

    public void setProduct_category_id(String product_category_id) {
        this.product_category_id = product_category_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String title) {
        this.name = title;
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
        return "ClassPojo [id = " + id + ", title = " + name + ", is_active = " + is_active +
                ", modified = " + modified + ", created = " + created+ ",product_type_id="+product_type_id+"" +
                ",product_category_id="+product_category_id+",price="+price+",added_by="+added_by+",shop_id="+shop_id+",image="+image+"]";
    }
}
