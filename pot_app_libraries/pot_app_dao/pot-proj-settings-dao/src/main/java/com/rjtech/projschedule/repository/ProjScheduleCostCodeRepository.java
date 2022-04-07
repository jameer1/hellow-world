package com.rjtech.projschedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projschedule.model.ProjScheduleCostCodeEntity;

public interface ProjScheduleCostCodeRepository extends ProjScheduleBaseRepository<ProjScheduleCostCodeEntity, Long> {

    @Query("SELECT T FROM ProjScheduleCostCodeEntity T  WHERE T.projScheduleBaseLineEntity.id =:baseLineId  AND   T.status=:status ")
    public List<ProjScheduleCostCodeEntity> findProjScheduleCostCodeDetails(@Param("baseLineId") Long baseLineId,
            @Param("status") Integer status);

}
