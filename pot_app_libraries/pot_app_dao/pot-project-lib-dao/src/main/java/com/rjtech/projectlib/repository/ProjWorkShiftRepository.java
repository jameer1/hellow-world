package com.rjtech.projectlib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projectlib.model.ProjWorkShiftMstrEntity;

public interface ProjWorkShiftRepository extends ProjLibBaseRepository<ProjWorkShiftMstrEntity, Long> {

    @Query("SELECT SHF FROM ProjWorkShiftMstrEntity SHF  WHERE SHF.projectId.projectId =:projectId  AND  SHF.status=:status ORDER BY SHF.code")
    List<ProjWorkShiftMstrEntity> findProjWorkShifts(@Param("projectId") Long projectId,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE ProjWorkShiftMstrEntity SHF  SET SHF.status=:status  where SHF.id in :projWorkShiftIds")
    void deactivateProjWorkShifts(@Param("projWorkShiftIds") List<Long> projWorkShiftIds,
            @Param("status") Integer status);

    @Query("SELECT SHF FROM ProjWorkShiftMstrEntity SHF  WHERE  SHF.projectId.projectId =:projectId   ORDER BY SHF.code")
    List<ProjWorkShiftMstrEntity> findAllProjWorkShifts(@Param("projectId") Long projectId);

    @Query("SELECT SHF FROM ProjWorkShiftMstrEntity SHF  WHERE SHF.projectId.projectId in (:projIds) AND  SHF.status=:status ORDER BY SHF.code")
    List<ProjWorkShiftMstrEntity> findProjWorkShifts(@Param("projIds") List<Long> projIds,
            @Param("status") Integer status);

}