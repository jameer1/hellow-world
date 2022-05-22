package com.rjtech.centrallib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.ProcureCatgDtlEntity;

public interface ProcureCatgRepository extends CentralLibRepository<ProcureCatgDtlEntity, Long> {

    @Query("SELECT PCM FROM ProcureCatgDtlEntity PCM  WHERE (PCM.clientId.clientId IS NULL OR PCM.clientId.clientId=:clientId ) AND  PCM.status=:status ORDER BY PCM.code")
    List<ProcureCatgDtlEntity> findByClientId(@Param("clientId") Long clientId, @Param("status") Integer status);

    @Query("SELECT PCM FROM ProcureCatgDtlEntity PCM  WHERE (PCM.clientId.clientId IS NULL OR PCM.clientId.clientId=:clientId )  AND (:subProcureName IS NULL OR UPPER(PCM.name) like UPPER(:subProcureName))  "
            + "AND (:procureId IS NULL OR UPPER(PCM.procureType) like UPPER(:procureId) ) AND PCM.status=:status ORDER BY PCM.code")
    List<ProcureCatgDtlEntity> findProcureCatgsByFilter(@Param("clientId") Long clientId,
            @Param("subProcureName") String subProcureName, @Param("procureId") String procureId,
            @Param("status") Integer status);

    @Query("SELECT PCM FROM ProcureCatgDtlEntity PCM  WHERE PCM.procureType =:procureId "
            + " AND  (PCM.clientId IS NULL OR PCM.clientId.clientId=:clientId )  AND PCM.status=:status ORDER BY PCM.code")
    List<ProcureCatgDtlEntity> getProcureByType(@Param("procureId") String procureId, @Param("clientId") Long clientId,
            @Param("status") Integer status);

    @Query("SELECT PCM FROM ProcureCatgDtlEntity PCM  WHERE PCM.clientId.clientId IS NULL OR PCM.clientId.clientId=:clientId")
    List<ProcureCatgDtlEntity> findAllProcureCatgs(@Param("clientId") Long clientId);
    
    @Query("SELECT PCM FROM ProcureCatgDtlEntity PCM WHERE UPPER(PCM.procureType) like UPPER(:procureType) ORDER BY PCM.code")
    List<ProcureCatgDtlEntity> findProcurementsByType(@Param("procureType") String procureType);
}
