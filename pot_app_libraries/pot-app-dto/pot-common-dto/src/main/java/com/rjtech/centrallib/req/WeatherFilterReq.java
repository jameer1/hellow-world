package com.rjtech.centrallib.req;

import java.io.Serializable;

import com.rjtech.common.dto.ClientTO;

public class WeatherFilterReq extends ClientTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4168175394384111004L;
    private String weatherName;
    private String weatherCode;

    public String getWeatherName() {
        return weatherName;
    }

    public void setWeatherName(String weatherName) {
        this.weatherName = weatherName;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

}
