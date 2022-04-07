package com.rjtech.common.req;

import java.io.Serializable;

public class TimezoneReq implements Serializable {

    private static final long serialVersionUID = 2196603686046684942L;

    private String lat;
    private String lng;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

}
