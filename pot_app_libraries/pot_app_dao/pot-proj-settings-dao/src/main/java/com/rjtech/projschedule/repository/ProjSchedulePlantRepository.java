package com.rjtech.projschedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projschedule.model.ProjSchedulePlantEntity;

public interface ProjSchedulePlantRepository extends ProjScheduleBaseRepository<ProjSchedulePlantEntity, Long> {

    @Query("SELECT T FROM ProjSchedulePlantEntity T  WHERE T.projScheduleBaseLineEntity.id =:baseLineId  AND    T.status=:status ")
    public List<ProjSchedulePlantEntity> findProjSchedulePlantDetails(@Param("baseLineId") Long baseLineId,
            @Param("status") Integer status);

}
