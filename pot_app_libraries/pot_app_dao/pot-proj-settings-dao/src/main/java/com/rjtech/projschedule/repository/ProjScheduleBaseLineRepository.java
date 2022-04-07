package com.rjtech.projschedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projschedule.model.ProjScheduleBaseLineEntity;

public interface ProjScheduleBaseLineRepository extends ProjScheduleBaseRepository<ProjScheduleBaseLineEntity, Long> {

    @Query("SELECT T FROM ProjScheduleBaseLineEntity T  WHERE T.projId.projectId = :projId AND T.status=:status AND T.scheduleItemType=:scheduleItemType")
    public List<ProjScheduleBaseLineEntity> findProjScheduleBaselines(@Param("projId") Long projId,
            @Param("status") Integer status, @Param("scheduleItemType") String scheduleItemType);
}
