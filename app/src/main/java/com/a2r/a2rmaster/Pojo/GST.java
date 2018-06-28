package com.a2r.a2rmaster.Pojo;

public class GST {
    String id,shop_id,tax_name,tax_value,effect_from,effect_to,created,modified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getTax_name() {
        return tax_name;
    }

    public void setTax_name(String tax_name) {
        this.tax_name = tax_name;
    }

    public String getTax_value() {
        return tax_value;
    }

    public void setTax_value(String tax_value) {
        this.tax_value = tax_value;
    }

    public String getEffect_from() {
        return effect_from;
    }

    public void setEffect_from(String effect_from) {
        this.effect_from = effect_from;
    }

    public String getEffect_to() {
        return effect_to;
    }

    public void setEffect_to(String effect_to) {
        this.effect_to = effect_to;
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
        return "ClassPojo [id = " + id + ", shop_id = " + shop_id + ", tax_name = " + tax_name +
                ", tax_value = " + tax_value + ", effect_from = " + effect_from + ", effect_to = " + effect_to +
                ", modified = " + modified + ", created = " + created+ "]";
    }
}

