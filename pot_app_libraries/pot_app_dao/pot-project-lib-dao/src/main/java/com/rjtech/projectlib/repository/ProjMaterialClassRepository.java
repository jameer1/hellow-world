package com.rjtech.projectlib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.projectlib.model.ProjMaterialMstrEntity;

public interface ProjMaterialClassRepository extends ProjLibBaseRepository<ProjMaterialMstrEntity, Long> {

    @Query("SELECT PMCM FROM ProjMaterialMstrEntity PMCM  WHERE PMCM.projectId.projectId =:projId  AND  PMCM.status=:status ")
    List<ProjMaterialMstrEntity> findByProjectId(@Param("projId") Long projId, @Param("status") Integer status);

    @Query("SELECT PMCM FROM ProjMaterialMstrEntity PMCM  WHERE  PMCM.projectId.projectId =:projId  "
            + "AND PMCM.intrlApproved = true AND  PMCM.status=:status ")
    List<ProjMaterialMstrEntity> getInternalProjMaterialMstrEntity(@Param("projId") Long projId,
            @Param("status") Integer status);
    
    @Query("SELECT PMCM.groupId FROM ProjMaterialMstrEntity PMCM  WHERE  PMCM.projectId.projectId =:projId "
            + "AND PMCM.intrlApproved <> 1" 
            + "AND  PMCM.status=:status ")
    List<MaterialClassMstrEntity> getInternalProjMaterialMstrEntiti(@Param("projId") Long projId,
            @Param("status") Integer status);


    @Query("SELECT PMCM FROM ProjMaterialMstrEntity PMCM  WHERE PMCM.projectId.projectId =:projId  "
            + "AND  (PMCM.intrlApproved = true OR PMCM.extrlApproved = true) AND PMCM.status=:status ")
    List<ProjMaterialMstrEntity> getExternalProjMaterialMstrEntity(@Param("projId") Long projId,
            @Param("status") Integer status);

}