package com.example.whiteshopingapp;

public class itemsmodelclass {
   private  String product;
   private  String price;
   private  String img;
   private  int count;
   private  String UnitPrice;
   private  String Quantity;

    public itemsmodelclass() {
    }

    public itemsmodelclass(String product, String price, String img, int count, String unitPrice, String quantity) {
        this.product = product;
        this.price = price;
        this.img = img;
        this.count = count;
        this.UnitPrice = unitPrice;
        this.Quantity = quantity;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}
