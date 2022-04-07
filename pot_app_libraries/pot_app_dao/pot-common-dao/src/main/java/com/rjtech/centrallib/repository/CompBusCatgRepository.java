package com.rjtech.centrallib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.BusinessCatgMstrEntity;

public interface CompBusCatgRepository extends CentralLibRepository<BusinessCatgMstrEntity, Long> {

    @Query("SELECT BCM FROM BusinessCatgMstrEntity BCM  WHERE (BCM.clientId IS NULL OR BCM.clientId=:clientId ) AND  BCM.status=:status")
    List<BusinessCatgMstrEntity> findByClientId(@Param("clientId") Long id, @Param("status") Integer status);

}
