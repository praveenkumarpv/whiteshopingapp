package com.example.whiteshopingapp;

public class ordermodalclass {
    private String imurl;
    private String Order_id;
    private String Status;
    private String User_id;
    private String date;
    private String time;
    private String TotalAmount;

    public ordermodalclass() {
    }

    public ordermodalclass(String imurl, String order_id, String status, String user_id, String date, String time,String TotalAmount) {
        this.imurl = imurl;
        this.Order_id = order_id;
        this.Status = status;
        this.User_id = user_id;
        this.date = date;
        this.time = time;
        this.TotalAmount = TotalAmount;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getImurl() {
        return imurl;
    }

    public void setImurl(String imurl) {
        this.imurl = imurl;
    }

    public String getOrder_id() {
        return Order_id;
    }

    public void setOrder_id(String order_id) {
        Order_id = order_id;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
