package com.rjtech.procurement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.procurement.model.PreContractDocEntity;

public interface PrecontractDocRepository extends ProcurementBaseRepository<PreContractDocEntity, Long> {

    @Query("SELECT PCD FROM PreContractDocEntity PCD  WHERE PCD.preContractId.id = :preContractId AND  PCD.status=:status")
    List<PreContractDocEntity> findPreContractDocs(@Param("preContractId") Long preContractId,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE PreContractDocEntity PRC SET PRC.status=:status  WHERE PRC.id in :docIds")
    void deactivateServiceDetails(@Param("docIds") List<Long> docIds, @Param("status") Integer status);
    
    @Query("SELECT PCD FROM PreContractDocEntity PCD  WHERE PCD.projDocFileEntity.id = :docId")
    PreContractDocEntity findDoc(@Param("docId") Long docId);

}
