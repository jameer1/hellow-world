package com.rjtech.centrallib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.CostMstrEntity;

public interface CostCodeRepository extends CentralLibRepository<CostMstrEntity, Long> {

    @Query("SELECT CM FROM CostMstrEntity CM  WHERE (CM.clientId IS NULL OR CM.clientId.clientId =:clientId ) AND  CM.status=:status ORDER BY CM.code")
    List<CostMstrEntity> findByClientId(@Param("clientId") Long id, @Param("status") Integer status);

    @Query("SELECT CM FROM CostMstrEntity CM  WHERE (CM.clientId IS NULL OR CM.clientId.clientId =:clientId )  AND (:costCodeCode IS NULL OR CM.code like :costCodeCode ) AND  (:costCodeName IS NULL OR CM.name like :costCodeName )  AND CM.status=:status ORDER BY CM.code")
    List<CostMstrEntity> findCostCodes(@Param("clientId") Long clientId, @Param("costCodeCode") String costCodeCode,
            @Param("costCodeName") String costCodeName, @Param("status") Integer status);

    @Query("SELECT CM FROM CostMstrEntity CM  WHERE CM.clientId IS NULL OR CM.clientId.clientId=:clientId")
    List<CostMstrEntity> findAllCostCodes(@Param("clientId") Long id);
}