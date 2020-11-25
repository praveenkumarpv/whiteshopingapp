package com.example.whiteshopingapp;

public class cataddmodal {
   private String catName;
   private String imageurl;
   private Integer No;

    public cataddmodal() {
    }

    public cataddmodal(String catName, String imageurl, Integer no) {
        this.catName = catName;
        this.imageurl = imageurl;
        No = no;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Integer getNo() {
        return No;
    }

    public void setNo(Integer no) {
        No = no;
    }
}
