package com.rjtech.procurement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.procurement.model.PreContractsMaterialDtlEntity;

public interface PrecontractMaterialRepository extends ProcurementBaseRepository<PreContractsMaterialDtlEntity, Long> {

    @Query("SELECT PCM FROM com.rjtech.procurement.model.PreContractsMaterialDtlEntity PCM  WHERE PCM.preContractEntity.id=:contractId AND  PCM.status=:status ORDER BY PCM.id")
    List<PreContractsMaterialDtlEntity> findPreContractMaterials(@Param("contractId") Long contractId,
            @Param("status") Integer status);

    @Query("SELECT PCM FROM com.rjtech.procurement.model.PreContractsMaterialDtlEntity PCM JOIN FETCH "
            + "PCM.preContractsMaterialCmpEntities MCM WHERE PCM.preContractEntity.id=:contractId  AND "
            + "MCM.preContractsCmpEntity.id=:materialCmpId  AND MCM.quantity > 0 AND PCM.status=:status ORDER BY PCM.id")
    List<PreContractsMaterialDtlEntity> findPreContractMaterialDtlByCmpId(@Param("contractId") Long contractId,
            @Param("materialCmpId") Long materialCmpId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE com.rjtech.procurement.model.PreContractsMaterialDtlEntity PRC SET PRC.status=:status  WHERE PRC.id in :materialIds")
    void deactivateMaterialDetails(@Param("materialIds") List<Long> materialIds, @Param("status") Integer status);

    @Query("SELECT PCM.projcostStatement.projCostItemEntity.id,SUM(PCM.quantity * PCM.estimateCost)  FROM  PreContractsMaterialDtlEntity PCM WHERE "
            + "PCM.preContractEntity.id = :precontractId AND PCM.latest = 1 GROUP BY PCM.projcostStatement.projCostItemEntity.id")
    List<Object[]> getMaterialCostSummary(@Param("precontractId") Long precontractId);
    
    

}
