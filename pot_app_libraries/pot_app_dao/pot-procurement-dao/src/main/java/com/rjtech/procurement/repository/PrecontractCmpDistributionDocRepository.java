package com.rjtech.procurement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.procurement.model.PreContractCmpDistributionDocEntity;

public interface PrecontractCmpDistributionDocRepository
        extends ProcurementBaseRepository<PreContractCmpDistributionDocEntity, Long> {

    @Query("SELECT T FROM PreContractCmpDistributionDocEntity T  WHERE T.preContractDistributionDocEntity.id=:documentId AND  T.status=:status")
    List<PreContractCmpDistributionDocEntity> findPreContractCmpDistributionDocs(@Param("documentId") Long documentId,
            @Param("status") Integer status);
    
    @Query("SELECT T FROM PreContractCmpDistributionDocEntity T  WHERE T.preContractCmpId.id=:docId")
    PreContractCmpDistributionDocEntity findcmpdoc(@Param("docId") Long docId);
}
