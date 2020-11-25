package com.example.whiteshopingapp;

import java.util.List;

public class Modalclass {
    private String productname;
    private String imurl;
    private String mrp;
    private String offprice;
    private String quantity;
    private String stock;
    private String offpers;
    private String deliverycharge;
    private String imagename;
    private List<String> catogary;
    private List<String> tags;

    private  Modalclass(){}

    public Modalclass(String productname,String imurl, String mrp, String offprice, String quantity, String stock,
                      String offpers, String deliverycharge,String imagename,List<String> catogary,List<String> tags) {
        this.productname = productname;
        this.imurl = imurl;
        this.mrp = mrp;
        this.offprice = offprice;
        this.quantity = quantity;
        this.stock = stock;
        this.offpers = offpers;
        this.deliverycharge = deliverycharge;
        this.imagename =imagename;
        this.catogary = catogary;
        this.tags = tags;
    }

    public String getImurl() {
        return imurl;
    }

    public void setImurl(String imurl) {
        this.imurl = imurl;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getOffprice() {
        return offprice;
    }

    public void setOffprice(String offprice) {
        this.offprice = offprice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getOffpers() {
        return offpers;
    }

    public void setOffpers(String offpers) {
        this.offpers = offpers;
    }

    public String getDeliverycharge() {
        return deliverycharge;
    }

    public void setDeliverycharge(String deliverycharge) {
        this.deliverycharge = deliverycharge;
    }
    public String getImagename() {
        return imagename;
    }
    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public List<String> getCatogary() {
        return catogary;
    }

    public void setCatogary(List<String> catogary) {
        this.catogary = catogary;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
