package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.ProjEstimateEntity;

public interface ProjEstimateRepository extends ProjSettingsBaseRepository<ProjEstimateEntity, Long> {

    @Query("SELECT PJEC FROM ProjEstimateEntity PJEC WHERE  PJEC.projMstrEntity.projectId=:projId")
    public List<ProjEstimateEntity> findProjEstimate(@Param("projId") Long projId);

    @Query("SELECT PJEC FROM ProjEstimateEntity PJEC WHERE  PJEC.projMstrEntity.projectId=:projId AND PJEC.resourceType='planthrs'")
    public ProjEstimateEntity findPlantEstimate(@Param("projId") Long projId);

    @Query("SELECT PJEC FROM ProjEstimateEntity PJEC WHERE  PJEC.projMstrEntity.projectId=:projId AND PJEC.resourceType='materials'")
    public ProjEstimateEntity findMaterialEstimate(@Param("projId") Long projId);

    @Query("SELECT PJEC FROM ProjEstimateEntity PJEC WHERE  PJEC.projMstrEntity.projectId=:projId AND PJEC.resourceType='manhrs'")
    public ProjEstimateEntity findManpowerEstimate(@Param("projId") Long projId);

    @Query("SELECT PJEC FROM ProjEstimateEntity PJEC WHERE  PJEC.projMstrEntity.projectId=:projId AND PJEC.resourceType='cost'")
    public ProjEstimateEntity findEstimateCostEstimate(@Param("projId") Long projId);

    @Query("SELECT PJEC FROM com.rjtech.projsettings.model.ProjEstimateEntity PJEC WHERE PJEC.projMstrEntity is null")
    public List<ProjEstimateEntity> findDefaultProjEstimate();

}
