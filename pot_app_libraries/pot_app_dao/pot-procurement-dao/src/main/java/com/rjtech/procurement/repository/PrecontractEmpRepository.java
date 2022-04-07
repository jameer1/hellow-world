package com.rjtech.procurement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.procurement.model.PreContractsEmpDtlEntity;

public interface PrecontractEmpRepository extends ProcurementBaseRepository<PreContractsEmpDtlEntity, Long> {

    @Query("SELECT PCE FROM PreContractsEmpDtlEntity PCE  WHERE PCE.preContractEntity.id=:contractId AND  PCE.status=:status ORDER BY PCE.id")
    List<PreContractsEmpDtlEntity> findPreContractEmpTypes(@Param("contractId") Long contractId,
            @Param("status") Integer status);

    @Query("SELECT PCE FROM PreContractsEmpDtlEntity PCE JOIN FETCH "
            + "PCE.preContractsEmpCmpEntities ECM WHERE PCE.preContractEntity.id=:contractId AND "
            + "ECM.preContractsCmpEntity.id=:empCmpId AND ECM.quantity > 0 AND PCE.status=:status ORDER BY PCE.id")
    List<PreContractsEmpDtlEntity> findPreContractEmpDtlByCmpId(@Param("contractId") Long contractId,
            @Param("empCmpId") Long empCmpId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE PreContractsEmpDtlEntity PRC SET PRC.status=:status  WHERE PRC.id in :empDtlIds")
    void deactivateManPowerDetails(@Param("empDtlIds") List<Long> empDtlIds, @Param("status") Integer status);

    @Query("SELECT PRC.costStatement.projCostItemEntity.id,SUM(PRC.quantity * PRC.estimateCost)  FROM  PreContractsEmpDtlEntity PRC WHERE "
            + "PRC.preContractEntity.id = :precontractId AND PRC.latest = 1 GROUP BY PRC.costStatement.projCostItemEntity.id")
    List<Object[]> getManpowerCostSummary(@Param("precontractId") Long precontractId);
}
