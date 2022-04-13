package com.rjtech.procurement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

}
