package com.rjtech.centrallib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.WeatherMstrEntity;

public interface WeatherRepository extends CentralLibRepository<WeatherMstrEntity, Long> {

    @Query("SELECT WM FROM WeatherMstrEntity WM  WHERE (WM.clientId.clientId IS NULL OR WM.clientId.clientId=:clientId ) AND  WM.status=:status ORDER BY WM.code")
    List<WeatherMstrEntity> findByClientId(@Param("clientId") Long clientId, @Param("status") Integer status);

    @Query("SELECT WM FROM WeatherMstrEntity WM  WHERE (WM.clientId.clientId IS NULL OR WM.clientId.clientId=:clientId )  AND (:weatherCode IS NULL OR WM.code like :weatherCode ) AND  (:weatherName IS NULL OR WM.name like :weatherName )  AND WM.status=:status ORDER BY WM.code")
    List<WeatherMstrEntity> findweathers(@Param("clientId") Long clientId, @Param("weatherCode") String weatherCode,
            @Param("weatherName") String weatherName, @Param("status") Integer status);

    @Query("SELECT WM FROM WeatherMstrEntity WM  WHERE WM.clientId.clientId IS NULL OR WM.clientId.clientId=:clientId ")
    List<WeatherMstrEntity> findAllWeathers(@Param("clientId") Long clientId);

}