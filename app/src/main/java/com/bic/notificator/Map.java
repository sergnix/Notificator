package com.bic.notificator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Map {

    private String raw;
    private String lon;
    private String latt;
    private String patternCoord = "(\\d{2},\\d{3,15}),(\\d{2},\\d{3,15})";

    Map(String raw) {
        this.raw = raw;
        List list = Arrays.asList(raw.split(","));
    }

    Double getLon() {
        Pattern pattern =  Pattern.compile(patternCoord);
        Matcher matcher = pattern.matcher(raw);
            if (matcher.find()) {
                lon = matcher.group(2).replace(",", ".");
                return Double.valueOf(lon);
            }
        return null;
    }

    Double getLatt() {
        Pattern pattern =  Pattern.compile(patternCoord);
        Matcher matcher = pattern.matcher(raw);
        if (matcher.find()) {
            latt = matcher.group(1).replace(",", ".");
            return Double.valueOf(latt);
        }
        return null;
    }
}
