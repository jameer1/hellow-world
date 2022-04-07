package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.WeatherTO;
import com.rjtech.common.dto.ClientTO;


public class WeatherReq extends ClientTO {
    private static final long serialVersionUID = 2950084862079755848L;

    private List<WeatherTO> weatherTOs = new ArrayList<WeatherTO>(5);

    public List<WeatherTO> getWeatherTOs() {
        return weatherTOs;
    }

    public void setWeatherTOs(List<WeatherTO> weatherTOs) {
        this.weatherTOs = weatherTOs;
    }

}
