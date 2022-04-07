package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;


public class WeatherDelReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<Long> weatherIds = new ArrayList<Long>(5);

    public List<Long> getWeatherIds() {
        return weatherIds;
    }

    public void setWeatherIds(List<Long> weatherIds) {
        this.weatherIds = weatherIds;
    }

}
