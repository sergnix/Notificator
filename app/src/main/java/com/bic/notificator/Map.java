package com.bic.notificator;

import java.util.Arrays;
import java.util.List;

class Map {

    private String raw;
    private String lon;
    private String latt;

    Map(String raw) {
        this.raw = raw;
        List list = Arrays.asList(raw.split(","));
        latt = String.valueOf(list.get(0)) + "." + list.get(1);
        lon = String.valueOf(list.get(2)) + "." + list.get(3);

    }

    Double getLon() {
        return Double.valueOf(lon);
    }

    Double getLatt() {
        return Double.valueOf(latt);
    }
}
