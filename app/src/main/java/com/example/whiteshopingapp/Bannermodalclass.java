package com.example.whiteshopingapp;

public class Bannermodalclass {
    private String imageurl;
    private String imagename;
    public  Bannermodalclass(){}

    public Bannermodalclass(String imageurl, String imagename) {
        this.imageurl = imageurl;
        this.imagename = imagename;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }
}
