package com.rjtech.projschedule.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.projschedule.model.ProjScheduleAssignedBaseLineEntity;

@Repository
public interface ProjScheduleAssignedBaseLineRepository
        extends ProjScheduleBaseRepository<ProjScheduleAssignedBaseLineEntity, Long> {

    @Query("FROM ProjScheduleAssignedBaseLineEntity entity WHERE entity.projectEntity.projectId = :projId AND entity.baseLineEntity.id = :baseLineId")
    public ProjScheduleAssignedBaseLineEntity findByProjectEntityIdAndBaseLineEntityId(@Param("projId") Long projId,
            @Param("baseLineId") Long baseLineId);
}
