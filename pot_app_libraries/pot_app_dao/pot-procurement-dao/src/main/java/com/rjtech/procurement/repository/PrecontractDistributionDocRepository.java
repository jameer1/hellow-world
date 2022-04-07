package com.rjtech.procurement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.procurement.model.PreContractDistributionDocEntity;

public interface PrecontractDistributionDocRepository
        extends ProcurementBaseRepository<PreContractDistributionDocEntity, Long> {

    @Query("SELECT T FROM PreContractDistributionDocEntity T  WHERE T.preContractId.id=:preContractId AND  T.status=:status")
    List<PreContractDistributionDocEntity> findPreContractDistributionDocs(@Param("preContractId") Long preContractId,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE PreContractDocEntity T SET T.status=:status  WHERE T.id in :docIds")
    void deactivateServiceDetails(@Param("docIds") List<Long> docIds, @Param("status") Integer status);

}
