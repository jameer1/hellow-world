package com.rjtech.projectlib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projectlib.model.ProjCrewMstrEntity;

public interface ProjCrewRepository extends ProjLibBaseRepository<ProjCrewMstrEntity, Long> {

    @Query("SELECT CRW FROM ProjCrewMstrEntity CRW  WHERE  CRW.projId.projectId =:projId  AND  CRW.status=:status ORDER BY CRW.code")
    List<ProjCrewMstrEntity> findProjCrewLists(@Param("projId") Long projId, @Param("status") Integer status);

    @Query("SELECT CRW FROM ProjCrewMstrEntity CRW  WHERE  CRW.projId.projectId in :projIds  AND  CRW.status=:status ORDER BY CRW.code")
    List<ProjCrewMstrEntity> findMultipleProjsCrewList(@Param("projIds") List<Long> projIds,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE ProjCrewMstrEntity CRW  SET CRW.status=:status  where CRW.id in :projCrewIds")
    void deactivateProjCrewLists(@Param("projCrewIds") List<Long> projCrewIds, @Param("status") Integer status);

    @Query("SELECT CRW FROM ProjCrewMstrEntity CRW  WHERE  CRW.projId.projectId =:projId   ORDER BY CRW.code")
    List<ProjCrewMstrEntity> findAllProjCrewLists(@Param("projId") Long projId);

    @Query("SELECT CRW FROM ProjCrewMstrEntity CRW  WHERE  CRW.projId.projectId in :projIds  AND  CRW.status=:status ORDER BY CRW.code")
    List<ProjCrewMstrEntity> findMultipleProjsCrewMap(@Param("projIds") List<Long> projIds,
            @Param("status") Integer status);

}
