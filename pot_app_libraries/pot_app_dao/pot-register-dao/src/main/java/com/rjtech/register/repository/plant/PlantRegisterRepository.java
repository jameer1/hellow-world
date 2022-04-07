package com.rjtech.register.repository.plant;

import java.util.List;
import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.register.plant.model.PlantRegisterDtlEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface PlantRegisterRepository extends RegisterBaseRepository<PlantRegisterDtlEntity, Long> {

    @Query("SELECT T FROM PlantRegisterDtlEntity T WHERE T.projMstrEntity.projectId=:projId and T.status=:status")
    public List<PlantRegisterDtlEntity> findProjectPlants(@Param("projId") Long projId,
            @Param("status") Integer status);
    
    @Query("SELECT  T FROM PlantRegisterDtlEntity  T  join fetch T.plantRegProjEntities  pre"
            + " WHERE pre.projId.projectId in ( :projIds ) and T.status=:status and T.id =pre.plantRegisterDtlEntity.id  and  pre.isLatest ='Y' ")
    public List<PlantRegisterDtlEntity> findLatestPlantsByProject(@Param("projIds") List<Long> projIds,
            @Param("status") Integer status);
    
    @Query("SELECT  T FROM PlantRegisterDtlEntity  T  join fetch T.plantRegProjEntities  pre"
            + " WHERE T.projMstrEntity.projectId in :projIds and T.status=:status and T.id =pre.plantRegisterDtlEntity.id  and  pre.isLatest ='Y' and "
            + "pre.postDeMobStatus =:plantCurrentStatus and T.cmpId.id in :companyIds and T.id in :plantIds and pre.commissionDate between :purchaseCommissionefromDate AND :purchaseCommissionetoDate")
    public List<PlantRegisterDtlEntity> findMasterPurchaseList(@Param("projIds") List<Long> projIds,
            @Param("status") Integer status, @Param("plantCurrentStatus") String plantCurrentStatus, @Param("companyIds") List<Long> companyIds, @Param("plantIds") List<Long> plantIds, @Param("purchaseCommissionefromDate") Date purchaseCommissionefromDate, @Param("purchaseCommissionetoDate") Date purchaseCommissionetoDate);
    
    @Query("SELECT  T FROM PlantRegisterDtlEntity  T  join fetch T.plantRegProjEntities  pre"
            + " WHERE T.projMstrEntity.projectId in :projIds and T.status=:status and T.id =pre.plantRegisterDtlEntity.id  and  pre.isLatest ='Y' and "
            + "pre.postDeMobStatus =:plantCurrentStatus and T.cmpId.id in :companyIds and T.id in :plantIds and pre.commissionDate between :purchaseCommissionefromDate AND :purchaseCommissionetoDate and pre.mobDate between :actualMobilisationfromDate AND :actualMobilisationtoDate")
    public List<PlantRegisterDtlEntity> findMasterPurchaseActualMobList(@Param("projIds") List<Long> projIds,
            @Param("status") Integer status, @Param("plantCurrentStatus") String plantCurrentStatus, @Param("companyIds") List<Long> companyIds, @Param("plantIds") List<Long> plantIds, @Param("purchaseCommissionefromDate") Date purchaseCommissionefromDate, @Param("purchaseCommissionetoDate") Date purchaseCommissionetoDate, @Param("actualMobilisationfromDate") Date actualMobilisationfromDate, @Param("actualMobilisationtoDate") Date actualMobilisationtoDate);
    
    @Query("SELECT  T FROM PlantRegisterDtlEntity  T  join fetch T.plantRegProjEntities  pre"
            + " WHERE T.projMstrEntity.projectId in :projIds and T.status=:status and T.id =pre.plantRegisterDtlEntity.id  and  pre.isLatest ='Y' and "
            + "pre.postDeMobStatus =:plantCurrentStatus and T.cmpId.id in :companyIds and T.id in :plantIds and pre.mobDate between :actualMobilisationfromDate AND :actualMobilisationtoDate")
    public List<PlantRegisterDtlEntity> findMasterActualMobList(@Param("projIds") List<Long> projIds,
            @Param("status") Integer status, @Param("plantCurrentStatus") String plantCurrentStatus, @Param("companyIds") List<Long> companyIds, @Param("plantIds") List<Long> plantIds, @Param("actualMobilisationfromDate") Date actualMobilisationfromDate, @Param("actualMobilisationtoDate") Date actualMobilisationtoDate);
    
    @Query("SELECT  T FROM PlantRegisterDtlEntity  T  join fetch T.plantRegProjEntities  pre"
            + " WHERE T.projMstrEntity.projectId in :projIds and T.status=:status and T.id =pre.plantRegisterDtlEntity.id  and  pre.isLatest ='Y' and "
            + "pre.postDeMobStatus =:plantCurrentStatus and T.id in :plantIds and pre.commissionDate between :purchaseCommissionefromDate AND :purchaseCommissionetoDate")
    public List<PlantRegisterDtlEntity> findMasterPurchaseList1(@Param("projIds") List<Long> projIds,
            @Param("status") Integer status, @Param("plantCurrentStatus") String plantCurrentStatus, @Param("plantIds") List<Long> plantIds, @Param("purchaseCommissionefromDate") Date purchaseCommissionefromDate, @Param("purchaseCommissionetoDate") Date purchaseCommissionetoDate);
    
    @Query("SELECT  T FROM PlantRegisterDtlEntity  T  join fetch T.plantRegProjEntities  pre"
            + " WHERE T.projMstrEntity.projectId in :projIds and T.status=:status and T.id =pre.plantRegisterDtlEntity.id  and  pre.isLatest ='Y' and "
            + "pre.postDeMobStatus =:plantCurrentStatus and T.id in :plantIds and pre.commissionDate between :purchaseCommissionefromDate AND :purchaseCommissionetoDate and pre.mobDate between :actualMobilisationfromDate AND :actualMobilisationtoDate")
    public List<PlantRegisterDtlEntity> findMasterPurchaseActualMobList1(@Param("projIds") List<Long> projIds,
            @Param("status") Integer status, @Param("plantCurrentStatus") String plantCurrentStatus, @Param("plantIds") List<Long> plantIds, @Param("purchaseCommissionefromDate") Date purchaseCommissionefromDate, @Param("purchaseCommissionetoDate") Date purchaseCommissionetoDate, @Param("actualMobilisationfromDate") Date actualMobilisationfromDate, @Param("actualMobilisationtoDate") Date actualMobilisationtoDate);
    
    @Query("SELECT  T FROM PlantRegisterDtlEntity  T  join fetch T.plantRegProjEntities  pre"
            + " WHERE T.projMstrEntity.projectId in :projIds and T.status=:status and T.id =pre.plantRegisterDtlEntity.id  and  pre.isLatest ='Y' and "
            + "pre.postDeMobStatus =:plantCurrentStatus and T.id in :plantIds and pre.mobDate between :actualMobilisationfromDate AND :actualMobilisationtoDate")
    public List<PlantRegisterDtlEntity> findMasterActualMobList1(@Param("projIds") List<Long> projIds,
            @Param("status") Integer status, @Param("plantCurrentStatus") String plantCurrentStatus, @Param("plantIds") List<Long> plantIds, @Param("actualMobilisationfromDate") Date actualMobilisationfromDate, @Param("actualMobilisationtoDate") Date actualMobilisationtoDate);
    
    @Query("SELECT  T FROM PlantRegisterDtlEntity  T  join fetch T.plantRegProjEntities  pre"
            + " WHERE T.projMstrEntity.projectId in :projIds and T.status=:status and T.id =pre.plantRegisterDtlEntity.id  and  pre.isLatest ='Y' and T.cmpId.id in :companyIds and T.id in :plantIds and T.procurecatgId.id in :procurecatgIds and pre.postDeMobStatus =:plantCurrentStatus and "
            + "pre.commissionDate between :purchaseCommissionefromDate AND :purchaseCommissionetoDate and pre.mobDate between :actualMobilisationfromDate AND :actualMobilisationtoDate and "
            + "pre.deMobDate between :deMobilisationfromDate AND :deMobilisationtoDate ")
    public List<PlantRegisterDtlEntity> findMasterPlantList(@Param("projIds") List<Long> projIds,
            @Param("status") Integer status, @Param("companyIds") List<Long> companyIds, @Param("plantIds") List<Long> plantIds, @Param("procurecatgIds") List<Long> procurecatgIds, @Param("plantCurrentStatus") String plantCurrentStatus, @Param("purchaseCommissionefromDate") Date purchaseCommissionefromDate, @Param("purchaseCommissionetoDate") Date purchaseCommissionetoDate, @Param("actualMobilisationfromDate") Date actualMobilisationfromDate, @Param("actualMobilisationtoDate") Date actualMobilisationtoDate, 
            @Param("deMobilisationfromDate") Date deMobilisationfromDate, @Param("deMobilisationtoDate") Date deMobilisationtoDate);
    
    @Query("SELECT  T FROM PlantRegisterDtlEntity  T  join fetch T.plantRegProjEntities  pre"
            + " WHERE pre.projId.projectId in ( :projIds ) and T.status=:status and T.id =pre.plantRegisterDtlEntity.id  and  pre.isLatest ='Y' and "
            + "pre.commissionDate between :purchaseCommissionefromDate AND :purchaseCommissionetoDate and pre.mobDate between :actualMobilisationfromDate AND :actualMobilisationtoDate and "
            + "pre.deMobDate between :deMobilisationfromDate AND :deMobilisationtoDate and pre.postDeMobStatus =:plantCurrentStatus and T.id in :plantIds and T.procurecatgId.id in :procurecatgIds ")
    public List<PlantRegisterDtlEntity> findMasterPlantList1(@Param("projIds") List<Long> projIds,
            @Param("status") Integer status, @Param("purchaseCommissionefromDate") Date purchaseCommissionefromDate, @Param("purchaseCommissionetoDate") Date purchaseCommissionetoDate, @Param("actualMobilisationfromDate") Date actualMobilisationfromDate, @Param("actualMobilisationtoDate") Date actualMobilisationtoDate, 
            @Param("deMobilisationfromDate") Date deMobilisationfromDate, @Param("deMobilisationtoDate") Date deMobilisationtoDate,@Param("plantCurrentStatus") String plantCurrentStatus,@Param("plantIds") List<Long> plantIds, @Param("procurecatgIds") List<Long> procurecatgIds);
   
    @Query("SELECT T FROM PlantRegisterDtlEntity T WHERE   T.clientId.clientId=:clientId  and T.projMstrEntity.projectId not in :projIds and T.projMstrEntity is not null  and T.status=:status")
    public List<PlantRegisterDtlEntity> findPlantsNotInUserProjects(@Param("clientId") Long clientId,
            @Param("projIds") List<Long> projIds, @Param("status") Integer status);

    @Query("SELECT T FROM PlantRegisterDtlEntity T WHERE   T.clientId.clientId=:clientId  and T.projMstrEntity.projectId in :projIds and T.status=:status")
    public List<PlantRegisterDtlEntity> findPlantsInUserProjects(@Param("clientId") Long clientId,
            @Param("projIds") List<Long> projIds, @Param("status") Integer status);

    @Query("SELECT T FROM PlantRegisterDtlEntity T WHERE T.clientId.clientId=:clientId and T.status=:status and T.projMstrEntity is null")
    public List<PlantRegisterDtlEntity> findNewPlants(@Param("clientId") Long clientId,
            @Param("status") Integer status);

    @Query("SELECT T FROM PlantRegisterDtlEntity T WHERE T.status=:status OR T.projMstrEntity.projectId=:projId")
    public List<PlantRegisterDtlEntity> findPlantRegister(@Param("status") Integer status,
            @Param("projId") Long projId);

    @Modifying
    @Query("UPDATE PlantRegisterDtlEntity T  SET T.status=:status  where T.id in :plantRegIds ")
    void deactivatePlantRegisters(@Param("plantRegIds") List<Long> plantRegIds, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE PlantRegisterDtlEntity T  SET T.projMstrEntity.projectId=:projId  where T.id=:plantId AND T.status=:status")
    void updatePlantProjectStatus(@Param("projId") Long projId, @Param("plantId") Long plantId,
            @Param("status") Integer status);

    /*
     * @Query("SELECT distinct prd FROM PlantRegisterDtlEntity prd left join fetch prd.plantProjectDtlEntityList ppd "
     * + " where prd.id=:plantId and prd.status =:plantstatus" +
     * " and ppd.projId =:projId and ppd.isLatest ='Y' and ppd.status=:projstatus )"
     * ) PlantRegisterDtlEntity getProcurePlantByProject(@Param("plantId") Long
     * plantId,@Param("plantstatus") Integer plantstatus,
     * 
     * @Param("projId") Long projId,@Param("projstatus") Integer projstatus);
     */
    @Query("SELECT DISTINCT T FROM PlantRegisterDtlEntity T where T.id=:plantId and T.status =:plantstatus")
    PlantRegisterDtlEntity findPlantByPlantId(@Param("plantId") Long plantId,
            @Param("plantstatus") Integer plantstatus);

    @Query("SELECT prd FROM PlantRegisterDtlEntity prd WHERE prd.projMstrEntity.projectId IN :projIds and prd.status=:status")
    public List<PlantRegisterDtlEntity> findMultiProjPlantDetails(@Param("projIds") List<Long> projIds,
            @Param("status") Integer status);

    public List<PlantRegisterDtlEntity> findByAssertIdAndClientId(@Param("assertId") String assertId,
            @Param("clientId") ClientRegEntity clientRegEntity);
}
