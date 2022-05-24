package com.rjtech.procurement.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.procurement.model.PreContractsEmpDtlEntity;
import com.rjtech.procurement.model.PreContractsPlantDtlEntity;

public interface PrecontractPlantRepository extends ProcurementBaseRepository<PreContractsPlantDtlEntity, Long> {

    @Query("SELECT PCP FROM \r\n"
    		+ "com.rjtech.procurement.model.PreContractsPlantDtlEntity PCP  WHERE PCP.preContractEntity.id=:contractId AND  PCP.status=:status ORDER BY PCP.id")
    List<PreContractsPlantDtlEntity> findPreContractPlants(@Param("contractId") Long contractId,
            @Param("status") Integer status);

    @Query("SELECT PCP FROM \r\n"
    		+ "com.rjtech.procurement.model.PreContractsPlantDtlEntity PCP JOIN FETCH "
            + "PCP.preContractsPlantCmpEntities PCM WHERE PCP.preContractEntity.id=:contractId AND "
            + "PCM.preContractsCmpEntity.id=:plantCmpId AND PCM.quantity > 0 AND  PCP.status=:status ORDER BY PCP.id")
    List<PreContractsPlantDtlEntity> findPreContractPlantDtlByCmpId(@Param("contractId") Long contractId,
            @Param("plantCmpId") Long plantCmpId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE \r\n"
    		+ "com.rjtech.procurement.model.PreContractsPlantDtlEntity PRC SET PRC.status=:status  WHERE PRC.id in :plantDtlIds")
    void deactivatePlantDetails(@Param("plantDtlIds") List<Long> plantDtlIds, @Param("status") Integer status);

    @Query("SELECT PCP.projcostStatement.projCostItemEntity.id,SUM(PCP.quantity * PCP.estimateCost)  FROM  PreContractsPlantDtlEntity PCP WHERE "
            + "PCP.preContractEntity.id = :precontractId AND PCP.latest = 1 GROUP BY PCP.projcostStatement.projCostItemEntity.id")
    List<Object[]> getPlantCostSummary(@Param("precontractId") Long precontractId);
    
    @Query("SELECT PCE FROM com.rjtech.procurement.model.PreContractsPlantDtlEntity PCE WHERE PCE.startDate=:fromDate and PCE.finishDate=:toDate and PCE.procureSubCatgId.procureType='Plants'"
            + " and PCE.procureSubCatgId.name=:pocSubCatName and  PCE.preContractEntity.id=:precontractId AND "
            + " PCE.unitMeasure=:unitsOfMeasure ORDER BY PCE.id")
    List<PreContractsPlantDtlEntity> searchPlantsDtlsByCriteria(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("precontractId") Long precontractId,
 		 @Param("pocSubCatName") String pocSubCatName,// @Param("payableCat") String payableCat,
       	  @Param("unitsOfMeasure") String unitsOfMeasure);

}
