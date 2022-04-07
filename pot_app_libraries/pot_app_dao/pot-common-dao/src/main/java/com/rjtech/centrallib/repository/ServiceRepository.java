package com.rjtech.centrallib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.ServiceMstrEntity;

public interface ServiceRepository extends CentralLibRepository<ServiceMstrEntity, Long> {

    @Query("SELECT SCM FROM ServiceMstrEntity SCM  WHERE (SCM.clientId.clientId IS NULL OR SCM.clientId.clientId=:clientId ) AND  SCM.status=:status ORDER BY SCM.code")
    List<ServiceMstrEntity> findByClientId(@Param("clientId") Long clientId, @Param("status") Integer status);

    @Query("SELECT SCM FROM ServiceMstrEntity SCM  WHERE (SCM.clientId.clientId IS NULL OR SCM.clientId.clientId=:clientId )  AND (:serviceCode IS NULL OR SCM.code like :serviceCode ) AND  (:serviceName IS NULL OR SCM.name like :serviceName )  AND SCM.status=:status ORDER BY SCM.code")
    List<ServiceMstrEntity> findServices(@Param("clientId") Long clientId, @Param("serviceCode") String serviceCode,
            @Param("serviceName") String serviceName, @Param("status") Integer status);

    @Query("SELECT SCM FROM ServiceMstrEntity SCM  WHERE SCM.clientId.clientId IS NULL OR SCM.clientId.clientId=:clientId")
    List<ServiceMstrEntity> findAllServices(@Param("clientId") Long clientId);
    
    @Query("SELECT SCM.procSubcatId FROM ServiceMstrEntity SCM  WHERE SCM.procSubcatId IS NOT NULL")
    List<Integer> getAllServices();
}