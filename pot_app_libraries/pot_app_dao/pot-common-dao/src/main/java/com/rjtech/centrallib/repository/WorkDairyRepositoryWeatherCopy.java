package com.rjtech.centrallib.repository;

import java.util.Date;
import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.rjtech.centrallib.model.WorkDairyEntityWeatherCopy;
import org.springframework.data.jpa.repository.JpaRepository;



@Repository
public interface WorkDairyRepositoryWeatherCopy extends JpaRepository<WorkDairyEntityWeatherCopy, Long> {
	 @Query("SELECT DISTINCT T.weatherId.id FROM WorkDairyEntityWeatherCopy T  WHERE T.weatherId.id IS NOT NULL")
    List<Long> getAllWeatherIds();
}