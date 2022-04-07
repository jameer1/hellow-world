package com.rjtech.centrallib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.StockMstrEntity;

public interface StockRepository extends CentralLibRepository<StockMstrEntity, Long> {

    @Query("SELECT SM FROM StockMstrEntity SM  WHERE (SM.clientId.clientId IS NULL OR SM.clientId.clientId=:clientId ) AND  SM.status=:status ORDER BY SM.code")
    List<StockMstrEntity> findByClientId(@Param("clientId") Long id, @Param("status") Integer status);

    @Query("SELECT SM FROM StockMstrEntity SM  WHERE (SM.clientId.clientId IS NULL OR SM.clientId.clientId=:clientId )  AND (:stockCode IS NULL OR SM.code like :stockCode ) AND  (:stockName IS NULL OR SM.name like :stockName )  AND SM.status=:status ORDER BY SM.code")
    List<StockMstrEntity> findStocks(@Param("clientId") Long clientId, @Param("stockCode") String stockCode,
            @Param("stockName") String stockName, @Param("status") Integer status);

    @Query("SELECT SM FROM StockMstrEntity SM  WHERE SM.clientId.clientId IS NULL OR SM.clientId.clientId=:clientId")
    List<StockMstrEntity> findAllStockYards(@Param("clientId") Long id);

}