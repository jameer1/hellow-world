package com.rjtech.procurement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.procurement.model.PrecontractSowDtlEntity;

public interface PrecontractSowRepository extends ProcurementBaseRepository<PrecontractSowDtlEntity, Long> {

    @Query("SELECT PCSW FROM \r\n"
    		+ "com.rjtech.procurement.model.PrecontractSowDtlEntity PCSW  WHERE PCSW.preContractEntity.id=:contractId AND  PCSW.status=:status ORDER BY PCSW.id")
    List<PrecontractSowDtlEntity> findPrecontractSowTypes(@Param("contractId") Long contractId,
            @Param("status") Integer status);

    @Query("SELECT PCSW FROM \r\n"
    		+ "com.rjtech.procurement.model.PrecontractSowDtlEntity PCSW JOIN FETCH "
            + "PCSW.precontractSowCmpEntites PSC WHERE PCSW.preContractEntity.id=:contractId  AND "
            + "PSC.preContractsCmpEntity.id=:cmpId  AND PSC.quantity > 0 AND PCSW.status=:status ORDER BY PCSW.id")
    List<PrecontractSowDtlEntity> findPreContractSowDtlByCmpId(@Param("contractId") Long contractId,
            @Param("cmpId") Long cmpId, @Param("status") Integer status);

    @Query("SELECT PCSW.sowId.projCostItemEntity.id,SUM(PCSW.quantity * PCSW.estimateCost) FROM PrecontractSowDtlEntity PCSW WHERE "
            + "PCSW.preContractEntity.id = :precontractId AND PCSW.latest = 1 GROUP BY PCSW.sowId.projCostItemEntity.id")
    List<Object[]> getSowCostSummary(@Param("precontractId") Long precontractId);

}
