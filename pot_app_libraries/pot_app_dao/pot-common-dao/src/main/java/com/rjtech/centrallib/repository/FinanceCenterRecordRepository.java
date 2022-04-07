package com.rjtech.centrallib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.centrallib.model.FinanceCenterEntity;

@Repository
public interface FinanceCenterRecordRepository extends CentralLibRepository<FinanceCenterEntity, Long> {

    @Query("SELECT FCE FROM FinanceCenterEntity FCE  WHERE FCE.clientId.clientId IS NULL OR FCE.clientId.clientId=:clientId  AND FCE.status=:status AND (:countryCode IS NULL OR FCE.countryCode=:countryCode) ")
    List<FinanceCenterEntity> getFinanceCenterCodes(@Param("clientId") Long clientId,@Param("status") Integer status,@Param("countryCode") String countryCode);

    @Modifying
    @Query("UPDATE FinanceCenterEntity FCE  SET FCE.status=:status  where FCE.id in :financeCenterIds")
    void deactivateFinanceCenterRecords(@Param("financeCenterIds") List<Long> financeCenterIds, @Param("status") Integer status);
    
    @Query("SELECT FCE FROM FinanceCenterEntity FCE  WHERE FCE.clientId.clientId IS NULL OR FCE.clientId.clientId=:clientId  AND FCE.status=:status AND (:countryCode IS NULL OR FCE.countryCode=:countryCode)  AND (:provisionCode IS NULL OR FCE.provisionCode=:provisionCode) ")
    List<FinanceCenterEntity> getUnitOfRate(@Param("clientId") Long clientId,@Param("status") Integer status,@Param("countryCode") String countryCode,@Param("provisionCode") String provisionCode);
    
    @Query("SELECT FCE FROM FinanceCenterEntity FCE  WHERE FCE.clientId.clientId IS NULL OR FCE.clientId.clientId=:clientId  AND FCE.status=:status AND FCE.provisionName=:provisionName AND (:countryCode IS NULL OR FCE.countryCode=:countryCode) ")
    List<FinanceCenterEntity> getFinanceCenter(@Param("clientId") Long clientId,@Param("status") Integer status,@Param("countryCode") String countryCode,@Param("provisionName") String provisionName);
}

