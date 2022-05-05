package com.rjtech.register.repository.plant;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.register.plant.model.PlantChargeOutRatesEntity;
import com.rjtech.register.plant.model.PlantChargeOutRatesEntityCopy;

//import com.rjtech.register.plant.model.PlantChargeOutRatesEntityCopy;

@Repository
public interface PlantChargeOutRateRepositoryCopy extends JpaRepository<PlantChargeOutRatesEntity, Long> {

    @Query("SELECT T FROM PlantChargeOutRatesEntity T "
            + " WHERE T.plantRegProjEntity.plantRegisterDtlEntity.id = :plantId"
            + " AND T.plantRegProjEntity.projId.projectId = :projId " + " AND T.latest = 1 ")
    public PlantChargeOutRatesEntity findPlantChargeOutRates(@Param("plantId") Long plantId,
            @Param("projId") Long projId);
    
    @Query("SELECT T FROM PlantChargeOutRatesEntityCopy T "
            + " WHERE T.plantRegProjEntity.plantRegisterDtlEntity.id = :plantId"
            + " AND T.plantRegProjEntity.projId.projectId = :projId " + " AND T.latest = 1 ")
    public PlantChargeOutRatesEntityCopy findPlantChargeOutRate(@Param("plantId") Long plantId,
            @Param("projId") Long projId);
    
    @Query("SELECT PCR FROM PlantRegProjEntity PPJD "
            + " join PPJD.plantChargeOutRatesEntities PCR WHERE PPJD.projId = :projMstrEntity ")
    public List<PlantChargeOutRatesEntityCopy> findPlantChargeOutRates(
            @Param("projMstrEntity") ProjMstrEntity projMstrEntity);
    
    // New One
    @Query("SELECT PCR FROM PlantRegProjEntity PPJD "
            + " join PPJD.plantChargeOutRatesEntities PCR WHERE PPJD.projId = :projMstrEntity ")
    public List<PlantChargeOutRatesEntityCopy> findPlantChargeOutRatesNew(
            @Param("projMstrEntity") ProjMstrEntity projMstrEntity);

    @Query("SELECT PPJD.projId, PCR.projMobCostItem, PPJD.plantRegisterDtlEntity, PPJD.mobDate, PCR.mobChargeOutRate, PCR.category, PGV.currency "
            + " FROM PlantRegProjEntity PPJD JOIN PPJD.plantChargeOutRatesEntities PCR "
            + " LEFT JOIN ProjGeneralMstrEntity PGV ON PGV.projMstrEntity = PPJD.projId AND PGV.status=1 AND PGV.isLatest='Y' "
            + " WHERE PPJD.projId.projectId in (:projIds) AND PPJD.plantRegisterDtlEntity.cmpId.id in (:cmpIds) AND "
            + " PCR.projMobCostItem.id in (:costIds) AND PCR.mobChargeOutRate > 0 AND "
            + " PPJD.mobDate BETWEEN :fromDate AND :toDate order by PPJD.mobDate ")
    List<Object[]> findMobRates(@Param("projIds") List<Long> projIds, @Param("cmpIds") List<Long> cmpIds,
            @Param("costIds") List<Long> costIds, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT PPJD.projId, PCR.projDemobCostItem, PPJD.plantRegisterDtlEntity, PPJD.deMobDate, PCR.deMobChargeOutRate, PCR.category, PGV.currency "
            + " FROM PlantRegProjEntity PPJD JOIN PPJD.plantChargeOutRatesEntities PCR "
            + " LEFT JOIN ProjGeneralMstrEntity PGV ON PGV.projMstrEntity = PPJD.projId AND PGV.status=1 AND PGV.isLatest='Y' "
            + " WHERE PPJD.projId.projectId in (:projIds) AND PPJD.plantRegisterDtlEntity.cmpId.id in (:cmpIds) AND "
            + " PCR.projDemobCostItem.id in (:costIds) AND PCR.deMobChargeOutRate > 0 AND "
            + " PPJD.deMobDate BETWEEN :fromDate AND :toDate order by PPJD.deMobDate ")
    List<Object[]> findDeMobRates(@Param("projIds") List<Long> projIds, @Param("cmpIds") List<Long> cmpIds,
            @Param("costIds") List<Long> costIds, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

}
