package com.rjtech.projschedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projschedule.model.ProjScheduleManPowerEntity;

public interface ProjScheduleManPowerRepository extends ProjScheduleBaseRepository<ProjScheduleManPowerEntity, Long> {

    @Query("SELECT T FROM ProjScheduleManPowerEntity T  WHERE T.projScheduleBaseLineEntity.id =:baseLineId  AND  T.status=:status ")
    public List<ProjScheduleManPowerEntity> findProjScheduleManPowerDetails(@Param("baseLineId") Long baseLineId,
            @Param("status") Integer status);

}
