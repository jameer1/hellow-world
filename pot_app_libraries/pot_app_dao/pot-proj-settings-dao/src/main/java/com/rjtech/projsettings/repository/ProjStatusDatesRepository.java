package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.ProjStatusDatesEntity;

public interface ProjStatusDatesRepository extends ProjSettingsBaseRepository<ProjStatusDatesEntity, Long> {

    @Query("SELECT T FROM ProjStatusDatesEntity T WHERE T.projMstrEntity.projectId=:projId AND T.status=:status")
    public List<ProjStatusDatesEntity> findProjStatusDates(@Param("projId") Long projId,
            @Param("status") Integer status);

}