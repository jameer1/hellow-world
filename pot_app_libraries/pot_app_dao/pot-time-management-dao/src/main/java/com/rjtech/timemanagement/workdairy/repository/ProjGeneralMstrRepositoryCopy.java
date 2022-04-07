package com.rjtech.timemanagement.workdairy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.eps.model.ProjMstrEntity;
//import com.rjtech.timemanagement.proj.settings.model.ProjGeneralMstrEntityCopy;
import com.rjtech.projsettings.model.ProjGeneralMstrEntity;

@Repository
public interface ProjGeneralMstrRepositoryCopy extends JpaRepository<ProjGeneralMstrEntity, Long> {

    @Query("SELECT DISTINCT T.maxHrs ,T.contractNumber FROM ProjGeneralMstrEntity T "
            + "WHERE T.projMstrEntity = :projMstrEntity " + "AND T.projMstrEntity.clientId = :clientRegEntity "
            + "AND T.status = 1 " + "AND T.isLatest = 'Y'")
    List<Object[]> getProjSettingsForWorkdairy(@Param("projMstrEntity") ProjMstrEntity projMstrEntity,
            @Param("clientRegEntity") ClientRegEntity clientRegEntity);

    @Query("SELECT DISTINCT general.maxHrs FROM ProjGeneralMstrEntity general WHERE general.projMstrEntity.projectId =:projId  AND "
            + "general.isLatest='Y' AND general.status=1")
    public Integer getMaxHrsOfProject(@Param("projId") Long projId);

    @Query("SELECT DISTINCT general.projMstrEntity.projectId, general.defualtHrs FROM ProjGeneralMstrEntity general WHERE general.projMstrEntity.projectId IN :projIds "
            + "AND general.isLatest='Y' AND general.status=1")
    public List<Object[]> getStandardHrsOfProjects(@Param("projIds") List<Long> projIds);

    @Query("SELECT DISTINCT general.projMstrEntity.projectId, general.currency FROM ProjGeneralMstrEntity general WHERE general.projMstrEntity.projectId IN :projIds "
            + "AND general.isLatest='Y' AND general.status=1")
    public List<Object[]> getCurrencyForProjects(@Param("projIds") List<Long> projIds);
    
    @Query("SELECT PGV FROM ProjGeneralMstrEntity PGV WHERE PGV.projMstrEntity.projectId=:projId AND PGV.status=:status AND PGV.isLatest='Y' ")
    public ProjGeneralMstrEntity findProjGenerals(@Param("projId") Long projId, @Param("status") Integer status);
}
