package com.rjtech.projschedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projschedule.model.ProjScheduleMaterialEntity;

public interface ProjScheduleMaterialRepository extends ProjScheduleBaseRepository<ProjScheduleMaterialEntity, Long> {

    @Query("SELECT T FROM ProjScheduleMaterialEntity T  WHERE T.projScheduleBaseLineEntity.id =:baseLineId  AND    T.status=:status ")
    public List<ProjScheduleMaterialEntity> findProjScheduleMaterialDetails(@Param("baseLineId") Long baseLineId,
            @Param("status") Integer status);

}
