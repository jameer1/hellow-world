package com.rjtech.projectlib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.MaterialClassMstrEntity;
import com.rjtech.projectlib.model.ProjMaterialMstrEntity;
//import com.rjtech.projlib.model.ProjMaterialMstrEntityCopy;

public interface ProjMaterialClassRepository extends JpaRepository<ProjMaterialMstrEntity, Long> {

    @Query("SELECT PMCM.groupId FROM ProjMaterialMstrEntity PMCM  WHERE  PMCM.projectId.projectId =:projId "
            + "AND PMCM.intrlApproved <> 1" 
            + "AND  PMCM.status=:status ")
    List<MaterialClassMstrEntity> getInternalProjMaterialMstrEntity(@Param("projId") Long projId,
            @Param("status") Integer status);
    
    @Query("SELECT PMCM.groupId FROM ProjMaterialMstrEntity PMCM  WHERE  PMCM.projectId.projectId =:projId "
            + "AND PMCM.intrlApproved <> 1" 
            + "AND  PMCM.status=:status ")
    List<MaterialClassMstrEntity> getInternalProjMaterialMstrEntiti(@Param("projId") Long projId,
            @Param("status") Integer status);


    @Query("SELECT PMCM.groupId FROM ProjMaterialMstrEntity PMCM " 
            + "WHERE PMCM.projectId.projectId =:projId "
            + "AND PMCM.extrlApproved <> 1"
            + "AND PMCM.status=:status")
    List<MaterialClassMstrEntity> getExternalProjMaterialMstrEntity(@Param("projId") Long projId,
            @Param("status") Integer status);

}