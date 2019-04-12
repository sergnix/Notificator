package com.bic.notificator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSData {

    public String body;
    ArrayList listsms;
    List<String> data;
    private String datesms;
    private String phone;
    public String Lac;
    public String Cid;
    public String Mcc;
    public String Mns;
    public String Address;
    public String Coord;
    public String Tpar;
    public boolean isCoord;

    SMSData(String body, String phone, String datesms) {
        this.body = body;
        this.data = Arrays.asList(body.split("\\$"));
        this.phone = phone;
        this.datesms = datesms;
        getCoord();
    }

    public String getTpar() {
        String patternTpar = "Т-(\\d{4})";
        Pattern pattern =  Pattern.compile(patternTpar);
        for (String item:data) {
            Matcher matcher = pattern.matcher(item);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return "Нет данных";
    }

    public String getCid() {
        String patternCid = "CID=(\\d{4})";
        Pattern pattern =  Pattern.compile(patternCid);
        for (String item:data) {
            Matcher matcher = pattern.matcher(item);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return "Нет данных";
    }

    public String getLac() {
        String patternLac = "LAC=(\\d{4})";
        Pattern pattern =  Pattern.compile(patternLac);
        for (String item:data) {
            Matcher matcher = pattern.matcher(item);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return "Нет данных";
    }

    public String getMcc() {
        String patternMcc = "MCC=(\\d{3})";
        Pattern pattern =  Pattern.compile(patternMcc);
        for (String item:data) {
            Matcher matcher = pattern.matcher(item);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return "Нет данных";
    }

    public String getMns() {
        String patternMns = "MNC=(\\d)";
        Pattern pattern =  Pattern.compile(patternMns);
        for (String item:data) {
            Matcher matcher = pattern.matcher(item);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return "Нет данных";
    }

    public String getAddress() {
        return data.get(8);
    }


    public String getCoord() {
        String patternCoord = "(\\d{2},\\d{3,15}),(\\d{2},\\d{3,15})";
        Pattern pattern =  Pattern.compile(patternCoord);
        for (String item:data) {
            Matcher matcher = pattern.matcher(item);
            if (matcher.find()) {
                isCoord = true;
                return matcher.group(0);
            }
        }
        isCoord = false;
        return null;
    }
}
