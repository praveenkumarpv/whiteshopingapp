package com.example.whiteshopingapp;

public class itemsmodelclass {
    private String product;
    private String img;
    private String price;
    private String product_id;

    public String getOrder_id() {
        return Order_id;
    }

    public void setOrder_id(String order_id) {
        Order_id = order_id;
    }

    private String Order_id;
    private String Status;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    private String Date;
    private String User_id;
    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }



    public String getTot_price() {
        return Tot_price;
    }

    public void setTot_price(String tot_price) {
        Tot_price = tot_price;
    }

    private String Tot_price;

    public Double getNetAmount() {
        return NetAmount;
    }

    public void setNetAmount(Double netAmount) {
        NetAmount = netAmount;
    }

    private Double NetAmount;




    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private int count;

    public itemsmodelclass (String img, String price, String product, String product_id, int count, String Tot_price,String Date) {
        this.product = product;
        this.img = img;
        this.price = price;
        this.product_id = product_id;
        this.count=count;
        this.Tot_price=Tot_price;
        this.Date=Date;
    }

    public itemsmodelclass () {
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
}
