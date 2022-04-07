package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.ProjStatusMileStonesEntity;

public interface ProjStatusMileStonesRepository extends ProjSettingsBaseRepository<ProjStatusMileStonesEntity, Long> {

    @Query("SELECT T FROM ProjStatusMileStonesEntity T WHERE T.projMstrEntity.projectId=:projId AND T.status=:status")
    public List<ProjStatusMileStonesEntity> findProjStatusMileStones(@Param("projId") Long projId,
            @Param("status") Integer status);

    @Modifying
    @Query("DELETE From ProjStatusMileStonesEntity M where M.id=:id and M.status=:status")
    public void deleteProjStatusMileStones(@Param("id") Long id, @Param("status") Integer status);

}