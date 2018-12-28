package com.bic.notificator;

import com.yandex.mapkit.geometry.Point;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Map {

    private static String raw;
    private List list;
    String lon;
    String latt;

    public Map(String raw) {
        this.raw = raw;
        list = Arrays.asList(raw.split(","));
        latt = String.valueOf(list.get(0)) + "." + list.get(1);
        lon = String.valueOf(list.get(2)) + "." + list.get(3);

    }

    public Map() {

    }

    public Double getLon() {
//        Pattern plon = Pattern.compile("^(\\-?\\d+(\\.\\d+)?),\\w*(\\-?\\d+(\\.\\d+)?)");
//        Matcher mlon = plon.matcher(raw);
        return Double.valueOf(lon);
    }

    public Double getLatt() {
//        Pattern platt = Pattern.compile("(\\-?\\d+(\\.\\d+)?),\\w*(\\-?\\d+(\\.\\d+)?)$");
//        Matcher mlatt = platt.matcher(raw);
        return Double.valueOf(latt);
    }




}
