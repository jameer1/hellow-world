package com.rjtech.centrallib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.MeasurmentMstrEntity;

public interface MeasurementRepository extends CentralLibRepository<MeasurmentMstrEntity, Long> {

    @Query("SELECT MM FROM MeasurmentMstrEntity MM  WHERE (MM.clientId.clientId IS NULL OR MM.clientId.clientId=:clientId ) AND  MM.status=:status ORDER BY MM.code")
    List<MeasurmentMstrEntity> findByClientId(@Param("clientId") Long clientId, @Param("status") Integer status);

    @Query("SELECT MM FROM MeasurmentMstrEntity MM  WHERE (MM.clientId.clientId IS NULL OR MM.clientId.clientId=:clientId )  AND (:mesureCode IS NULL OR MM.code like :mesureCode ) AND  (:mesureName IS NULL OR MM.name like :mesureName )  AND MM.status=:status ORDER BY MM.code")
    List<MeasurmentMstrEntity> findMesures(@Param("clientId") Long clientId, @Param("mesureCode") String mesureCode,
            @Param("mesureName") String mesureName, @Param("status") Integer status);

    @Query("SELECT MM FROM MeasurmentMstrEntity MM  WHERE (MM.clientId.clientId IS NULL OR MM.clientId.clientId=:clientId ) AND  (:procureClassName is NULL or MM.procureClassName=:procureClassName) AND  MM.status=:status ORDER BY MM.code")
    List<MeasurmentMstrEntity> findByProcureType(@Param("clientId") Long clientId,
            @Param("procureClassName") String procureClassName, @Param("status") Integer status);

    @Query("SELECT MM FROM MeasurmentMstrEntity MM  WHERE MM.clientId.clientId IS NULL OR MM.clientId.clientId=:clientId ORDER BY MM.code")
    List<MeasurmentMstrEntity> findAllMesurements(@Param("clientId") Long clientId);

}