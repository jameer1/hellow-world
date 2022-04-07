package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.WeatherTO;
import com.rjtech.common.resp.AppResp;


public class WeatherResp extends AppResp {
    private static final long serialVersionUID = 2950084862079755848L;

    private List<WeatherTO> weatherTOs = null;

    public WeatherResp() {
        weatherTOs = new ArrayList<WeatherTO>(5);
    }

    public List<WeatherTO> getWeatherTOs() {
        return weatherTOs;
    }

}
