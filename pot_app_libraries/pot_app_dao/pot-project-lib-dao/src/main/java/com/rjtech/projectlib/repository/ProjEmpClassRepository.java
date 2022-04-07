package com.rjtech.projectlib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projectlib.model.ProjEmpClassMstrEntity;

public interface ProjEmpClassRepository extends ProjLibBaseRepository<ProjEmpClassMstrEntity, Long> {

    @Query("SELECT PEC FROM ProjEmpClassMstrEntity PEC  WHERE PEC.projectId.projectId =:projId  AND  PEC.status=:status ORDER BY PEC.empClassMstrEntity.code")
    List<ProjEmpClassMstrEntity> findProjEmpClasses(@Param("projId") Long projId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE ProjEmpClassMstrEntity PEC  SET PEC.status=:status  where PEC.id in :projEmpClassIds")
    void deactivateProjEmpClass(@Param("projEmpClassIds") List<Long> projEmpClassIds, @Param("status") Integer status);

    @Query("SELECT PEC FROM ProjEmpClassMstrEntity PEC  WHERE  PEC.projectId.projectId =:projId  ORDER BY PEC.empClassMstrEntity.id")
    List<ProjEmpClassMstrEntity> findAllProjEmpClasses(@Param("projId") Long projId);

    @Query("SELECT PEC FROM ProjEmpClassMstrEntity PEC  WHERE PEC.projectId.projectId in (:projList)  AND  PEC.status=:status ")
    List<ProjEmpClassMstrEntity> getUserProjEmpClasses(@Param("projList") List<Long> projList,
            @Param("status") Integer status);

}