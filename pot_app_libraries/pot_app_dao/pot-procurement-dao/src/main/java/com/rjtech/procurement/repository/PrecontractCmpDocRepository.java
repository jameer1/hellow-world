package com.rjtech.procurement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.procurement.model.PreContractCmpDocEntity;

public interface PrecontractCmpDocRepository extends ProcurementBaseRepository<PreContractCmpDocEntity, Long> {

    @Query("SELECT PCCD FROM PreContractCmpDocEntity PCCD  WHERE PCCD.preContractsCmpEntity.id=:preContractCmpId AND  PCCD.status=:status")
    List<PreContractCmpDocEntity> findPreContractCmpDocs(@Param("preContractCmpId") Long preContractCmpId,
            @Param("status") Integer status);

    @Query("SELECT PCCD FROM PreContractCmpDocEntity PCCD  WHERE PCCD.preContractsCmpEntity.id=:preContractCmpId AND PCCD.fromVendor=:fromVendor AND PCCD.status=:status")
    List<PreContractCmpDocEntity> findPreContractCmpDocsByType(@Param("preContractCmpId") Long preContractCmpId,
            @Param("fromVendor") boolean fromVendor, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE PreContractCmpDocEntity PRC SET PRC.status=:status  WHERE PRC.id in :cmpDocIds")
    void deactivateServiceDetails(@Param("cmpDocIds") List<Long> cmpDocIds, @Param("status") Integer status);
}
