package com.a2r.a2rmaster.Pojo;

/**
 * Created by mobileapplication on 2/5/18.
 */

public class Ordersummery {
    String id,order_no,name,mobile,payment_mode,total_amount,bill_no,bill_date,table_no,status,discount,discount_reason,orders,created;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
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

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getBill_date() {
        return bill_date;
    }

    public void setBill_date(String bill_date) {
        this.bill_date = bill_date;
    }

    public String getTable_no() {
        return table_no;
    }

    public void setTable_no(String table_no) {
        this.table_no = table_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscount_reason() {
        return discount_reason;
    }

    public void setDiscount_reason(String discount_reason) {
        this.discount_reason = discount_reason;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ",order_no = " + order_no + ", title = " + name  +
                ", mobile = " + mobile + ",created = " + created + ", payment_mode = " + payment_mode+ ",total_amount="+total_amount+"" +
                ",bill_no="+bill_no+",bill_date="+bill_date+",table_no="+table_no+",discount_reason="+discount_reason+"orders="+orders+",status="+status+",discount="+discount+"]";
    }
}

/*
*
* "id": 1459,
            "order_no": "0037",
            "name": "guest",
            "mobile": "",
            "payment_mode": "",
            "total_amount": 435,
            "shop_id": 49,
            "bill_no": "000926",
            "bill_date": "2018-09-29T22:52:18+05:30",
            "table_no": "Table8",
            "status": "Billed",
            "bill_by": 42,
            "discount": 0,
            "discount_reason": "",
            "added_by": 43,
            "created": "29-09-2018 10:21 PM",
            "modified": "2018-09-29T22:52:18+05:30",
            "orders": 2
* */