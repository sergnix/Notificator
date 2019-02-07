package com.bic.notificator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SMSData {

    ArrayList listsms;
    private String datesms;
    public String getBody() {
        return body;
    }
    public String body;
    private String phone;
    List<String> data;
    private String Lac;
    private String Cid;
    private String Mcc;
    private String Mns;
    private String Address;
    private String lont;
    private String lat;

    SMSData(String body, String phone, String datesms) {
        this.body = body;
        this.data = Arrays.asList(body.split("\\$"));
        this.phone = phone;
        this.datesms = datesms;
    }

    public String getPhone() {
        return phone;
    }

    public String getCid() { return data.get(6); }

    public String getLac() {
        return data.get(5);
    }

    public String getMcc() {
        return data.get(3);
    }

    public String getMns() {
        return data.get(4);
    }

    public String getAddress() {
        return data.get(8);
    }

    public String getLat() {
        return data.get(10);
    }

    public String getLont() { return data.get(10); }

    public void setLont(String lont) {
        this.lont = lont;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLac(String lac) {
        Lac = lac;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public void setMcc(String mcc) {
        Mcc = mcc;
    }

    public void setMns(String mns) {
        Mns = mns;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
