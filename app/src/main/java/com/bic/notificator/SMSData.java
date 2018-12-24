package com.bic.notificator;

import java.util.ArrayList;

public class SMSData {

    private String title;
    private String phone;
//    private double lont;
//    private double lat;

    public SMSData(String title, String phone) {
        this.title = title;
        this.phone = phone;
//        this.lont = lont;
//        this.lat = latt;
    }

    public String getTitle() {
        return title;
    }

    public String getPhone() {
        return phone;
    }

//    public double getLont() {
//        return lont;
//    }
//
//    public double getLat() {
//        return lat;
//    }
//
//    public void setLont(double lont) {
//        this.lont = lont;
//    }
//
//    public void setLatt(double lat) {
//        this.lat = lat;
//    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
