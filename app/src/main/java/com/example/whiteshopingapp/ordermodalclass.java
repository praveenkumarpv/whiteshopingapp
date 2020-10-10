package com.example.whiteshopingapp;

public class ordermodalclass {
    private String imurl;
    private String name;
    private String time;
    private String orderid;
    private String status;

    public ordermodalclass() {
    }

    public ordermodalclass(String imurl, String name, String time, String orderid, String status) {
        this.imurl = imurl;
        this.name = name;
        this.time = time;
        this.orderid = orderid;
        this.status = status;
    }

    public String getImurl() {
        return imurl;
    }

    public void setImurl(String imurl) {
        this.imurl = imurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
