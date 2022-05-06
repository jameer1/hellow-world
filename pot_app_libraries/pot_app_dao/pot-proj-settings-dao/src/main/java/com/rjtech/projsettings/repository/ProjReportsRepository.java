package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.ProjectReportsEntity;

public interface ProjReportsRepository extends ProjSettingsBaseRepository<ProjectReportsEntity, Long> {

    @Query("SELECT PJR FROM ProjectReportsEntity PJR WHERE PJR.projMstrEntity.projectId = :projId AND PJR.status=:status ORDER BY PJR.id")
    public List<ProjectReportsEntity> findProjReports(@Param("projId") Long projId, @Param("status") Integer status);

    @Query("SELECT PJR FROM com.rjtech.projsettings.model.ProjectReportsEntity PJR WHERE PJR.projMstrEntity IS NULL ")
    public List<ProjectReportsEntity> findAllProjReports();

    @Query("SELECT PJR FROM ProjectReportsEntity PJR WHERE PJR.projMstrEntity.projectId IN :projIds")
    public List<ProjectReportsEntity> findProjReports(@Param("projIds") List<Long> projIds);

}
