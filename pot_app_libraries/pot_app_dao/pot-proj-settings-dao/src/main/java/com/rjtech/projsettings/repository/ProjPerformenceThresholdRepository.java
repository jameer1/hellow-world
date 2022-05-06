package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.ProjPerformenceThresholdEntity;

public interface ProjPerformenceThresholdRepository
        extends ProjSettingsBaseRepository<ProjPerformenceThresholdEntity, Long> {

    @Query("SELECT PPFT FROM ProjPerformenceThresholdEntity PPFT WHERE  (( :projId IS NULL AND PPFT.projMstrEntity IS NULL) OR PPFT.projMstrEntity.projectId=:projId ) AND PPFT.status=:status")
    public List<ProjPerformenceThresholdEntity> findProjPerformenceThresholds(@Param("projId") Long projId,
            @Param("status") Integer status);

    @Query("SELECT PPFT FROM \r\n"
    		+ "com.rjtech.projsettings.model.ProjPerformenceThresholdEntity PPFT WHERE PPFT.isDefault='Y' AND PPFT.projMstrEntity IS NULL")
    public List<ProjPerformenceThresholdEntity> findDefaultProjPerformenceThresholds();

    @Modifying
    @Query("DELETE FROM ProjPerformenceThresholdEntity PPFT  where PPFT.projMstrEntity.projectId=:projId AND PPFT.status=:status ")
    void deactivateProjPerformenceThreshold(@Param("projId") Long projId, @Param("status") Integer status);

    @Query("SELECT PPFT FROM ProjPerformenceThresholdEntity PPFT WHERE PPFT.projMstrEntity.projectId IN :projIds AND "
            + "PPFT.status=:status")
    public List<ProjPerformenceThresholdEntity> findProjPerformenceThresholds(@Param("projIds") List<Long> projIds,
            @Param("status") Integer status);
}
