package com.bic.notificator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SMSData {

    public String body;
    ArrayList listsms;
    List<String> data;
    private String datesms;
    private String phone;
    private String Lac;
    private String Cid;
    private String Mcc;
    private String Mns;
    private String Address;
    private String Coord;
    private String Tpar;

    SMSData(String body, String phone, String datesms) {
        this.body = body;
        this.data = Arrays.asList(body.split("\\$"));
        this.phone = phone;
        this.datesms = datesms;
    }

    public String getTpar() {
        return data.get(0);
    }

    public void setTpar(String tpar) {
        Tpar = tpar;
    }

    public String getBody() {
        return body;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCid() {
        return data.get(6);
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public String getLac() {
        return data.get(5);
    }

    public void setLac(String lac) {
        Lac = lac;
    }

    public String getMcc() {
        return data.get(3);
    }

    public void setMcc(String mcc) {
        Mcc = mcc;
    }

    public String getMns() {
        return data.get(4);
    }

    public void setMns(String mns) {
        Mns = mns;
    }

    public String getAddress() {
        return data.get(8);
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCoord() {
        return data.get(10);
    }
}
