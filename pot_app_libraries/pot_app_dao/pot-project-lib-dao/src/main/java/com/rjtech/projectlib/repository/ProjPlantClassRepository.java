package com.rjtech.projectlib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projectlib.model.ProjPlantClassMstrEntity;

public interface ProjPlantClassRepository extends ProjLibBaseRepository<ProjPlantClassMstrEntity, Long> {

    @Query("SELECT PEC FROM ProjPlantClassMstrEntity PEC  WHERE PEC.projId.projectId =:projId  AND  PEC.status=:status ORDER BY PEC.plantMstrEntity.code")
    List<ProjPlantClassMstrEntity> findProjPlantClasses(@Param("projId") Long projId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE ProjPlantClassMstrEntity PEC  SET PEC.status=:status  where PEC.id in :projPlantClassIds")
    void deactivateProjCrewLists(@Param("projPlantClassIds") List<Long> projPlantClassIds,
            @Param("status") Integer status);

    @Query("SELECT PEC FROM ProjPlantClassMstrEntity PEC  WHERE PEC.projId.projectId =:projId ORDER BY  PEC.plantMstrEntity.id")
    List<ProjPlantClassMstrEntity> findAllProjPlantClasses(@Param("projId") Long projId);

}