package com.example.whiteshopingapp;

public class userdatamodalclass {
    private String House;
    private String Landmark;
    private String Phone;
    private String Pin;
    private String Road;
    private String name;
    private String phone2;

    public userdatamodalclass() {
    }

    public userdatamodalclass(String house, String landmark, String phone, String pin, String road, String name, String phone2) {
        House = house;
        Landmark = landmark;
        Phone = phone;
        Pin = pin;
        Road = road;
        this.name = name;
        this.phone2 = phone2;
    }

    public String getHouse() {
        return House;
    }

    public void setHouse(String house) {
        House = house;
    }

    public String getLandmark() {
        return Landmark;
    }

    public void setLandmark(String landmark) {
        Landmark = landmark;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPin() {
        return Pin;
    }

    public void setPin(String pin) {
        Pin = pin;
    }

    public String getRoad() {
        return Road;
    }

    public void setRoad(String road) {
        Road = road;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }
}
