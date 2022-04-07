package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projsettings.model.ProjGeneralMstrEntity;

public interface ProjGeneralRepository extends ProjSettingsBaseRepository<ProjGeneralMstrEntity, Long> {

    @Query("SELECT PGV FROM ProjGeneralMstrEntity PGV WHERE PGV.projMstrEntity.projectId=:projId AND PGV.status=:status AND PGV.isLatest='Y' ")
    public List<ProjGeneralMstrEntity> findProjGenerals(@Param("projId") Long projId, @Param("status") Integer status);

    @Query("SELECT PGV FROM ProjGeneralMstrEntity PGV WHERE PGV.projMstrEntity.projectId IN :projIds AND "
            + "PGV.status=:status AND PGV.isLatest='Y' ")
    public List<ProjGeneralMstrEntity> findProjGenerals(@Param("projIds") List<Long> projIds,
            @Param("status") Integer status);

    @Query("SELECT PGV.id,PGV.currency FROM ProjGeneralMstrEntity PGV WHERE PGV.projMstrEntity.projectId=:projId AND PGV.status=:status AND PGV.isLatest='Y' ")
    public List<Object[]> findProjGeneralsCurrencys(@Param("projId") Long projId, @Param("status") Integer status);

    @Query("SELECT PGV FROM ProjGeneralMstrEntity PGV WHERE PGV.isDefault='Y' AND PGV.projMstrEntity IS NULL")
    public ProjGeneralMstrEntity findProjGeneralsDefault();

    @Query("SELECT PGV FROM ProjGeneralMstrEntity PGV  WHERE PGV.projMstrEntity.projectId!=:projId AND PGV.status=:status AND PGV.isLatest='Y' ")
    public List<ProjGeneralMstrEntity> findAllProjGenerals(@Param("projId") Long projId,
            @Param("status") Integer status);

    @Query("SELECT general.globalCalEntity FROM ProjGeneralMstrEntity general WHERE general.globalCalEntity.id=:id AND general.isLatest='Y' AND general.status=:status ")
    public List<Object[]> isAssignedToProject(@Param("id") Long id, @Param("status") Integer status);

    @Query("SELECT PGV.projResourceCurveEntity.id FROM ProjGeneralMstrEntity PGV WHERE PGV.projMstrEntity.projectId=:projId AND "
            + "PGV.status = 1 AND PGV.isLatest = 'Y' ")
    public Long getProjDefaultCurve(@Param("projId") Long projId);
    
    @Query("SELECT PGV.projResourceCurveEntity.id FROM ProjGeneralMstrEntity PGV WHERE PGV.projMstrEntity.projectId=:projId AND "
            + "PGV.status = 1 AND PGV.isLatest = 'Y' ")
    public List<Long> getProjDefaultCurveId(@Param("projId") Long projId);
    
    //select * from proj_general_values pgv where pgv.PGV_EPM_ID = 262 and pgv.pgv_updated_on = (select max(pgv_updated_on) from proj_general_values where PGV_EPM_ID = 262)
    //@Query("SELECT PGV.isoAlpha3 FROM ProjGeneralMstrEntity PGV where PGV.projMstrEntity=:projMstrEntity ")
    @Query("SELECT PGV.isoAlpha3 FROM ProjGeneralMstrEntity PGV where PGV.projMstrEntity=:projMstrEntity and PGV.updatedOn=(select max(ipgv.updatedOn) from ProjGeneralMstrEntity ipgv where ipgv.projMstrEntity=:projMstrEntity)")
    public String findCountry(@Param("projMstrEntity") ProjMstrEntity projMstrEntity);
    
    //Dinesh added queries from repository copy
    
    @Query("SELECT DISTINCT general.maxHrs FROM ProjGeneralMstrEntity general WHERE general.projMstrEntity.projectId =:projId  AND "
            + "general.isLatest='Y' AND general.status=1")
    public Integer getMaxHrsOfProject(@Param("projId") Long projId);
    
    @Query("SELECT PGV FROM ProjGeneralMstrEntity PGV WHERE PGV.projMstrEntity.projectId=:projId AND PGV.status=:status")
    List<ProjGeneralMstrEntity> getGeneralValues(@Param("projId") Long projId,@Param("status") Integer status);

}
