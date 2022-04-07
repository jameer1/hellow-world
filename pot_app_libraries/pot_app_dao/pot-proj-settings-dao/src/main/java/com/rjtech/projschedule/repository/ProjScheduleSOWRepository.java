package com.rjtech.projschedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projschedule.model.ProjScheduleSOWEntity;

public interface ProjScheduleSOWRepository extends ProjScheduleBaseRepository<ProjScheduleSOWEntity, Long> {

    @Query("SELECT T FROM ProjScheduleSOWEntity T  WHERE T.projScheduleBaseLineEntity.id =:baseLineId  AND    T.status=:status ")
    public List<ProjScheduleSOWEntity> findProjScheduleSOWDetails(@Param("baseLineId") Long baseLineId,
            @Param("status") Integer status);

}
