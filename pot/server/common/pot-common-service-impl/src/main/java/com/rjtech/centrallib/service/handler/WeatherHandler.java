package com.rjtech.centrallib.service.handler;

import java.util.ArrayList;
import java.util.List;
import com.rjtech.centrallib.dto.WeatherTO;
import com.rjtech.centrallib.model.WeatherMstrEntity;
import com.rjtech.centrallib.resp.WeatherResp;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;

public class WeatherHandler {

    public static WeatherResp convertEntityToPOJO(List<WeatherMstrEntity> entities) {
        WeatherResp weatherResp = new WeatherResp();
        WeatherTO classificationTO = null;
        for (WeatherMstrEntity entity : entities) {
            classificationTO = convertWeatherPOJOToEntity(entity);
            weatherResp.getWeatherTOs().add(classificationTO);
        }
        return weatherResp;
    }

    public static WeatherTO convertWeatherPOJOToEntity(WeatherMstrEntity entity) {
        WeatherTO classificationTO = new WeatherTO();
        classificationTO.setId(entity.getId());
        classificationTO.setCode(entity.getCode());
        classificationTO.setName(entity.getName());
        ClientRegEntity clientRegEntity = entity.getClientId();
        if (null != clientRegEntity) {
            classificationTO.setClientId(clientRegEntity.getClientId());
        }
        classificationTO.setStatus(entity.getStatus());
        return classificationTO;
    }

    public static List<WeatherMstrEntity> convertPOJOToEntity(List<WeatherTO> weatherTOs) {
        List<WeatherMstrEntity> entities = new ArrayList<WeatherMstrEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        WeatherMstrEntity entity = null;
        for (WeatherTO weatherTO : weatherTOs) {
            entity = new WeatherMstrEntity();
            if (CommonUtil.isNonBlankLong(weatherTO.getId())) {
                entity.setId(weatherTO.getId());
            }
            entity.setCode(weatherTO.getCode());
            entity.setName(weatherTO.getName());
            entity.setStatus(weatherTO.getStatus());
            entities.add(entity);
        }
        return entities;
    }

}
