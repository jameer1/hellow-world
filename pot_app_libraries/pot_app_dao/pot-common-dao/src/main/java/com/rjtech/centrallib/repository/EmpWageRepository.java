package com.rjtech.centrallib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.EmpWageMstrEntity;

public interface EmpWageRepository extends CentralLibRepository<EmpWageMstrEntity, Long> {

    @Query("SELECT EWM FROM EmpWageMstrEntity EWM  WHERE (EWM.clientId.clientId IS NULL OR EWM.clientId.clientId=:clientId ) AND  EWM.status=:status ORDER BY EWM.code")
    List<EmpWageMstrEntity> findByClientId(@Param("clientId") Long id, @Param("status") Integer status);

    @Query("SELECT EWM FROM EmpWageMstrEntity EWM  WHERE (EWM.clientId.clientId IS NULL OR EWM.clientId.clientId=:clientId )  AND (:empWageCode IS NULL OR EWM.code like :empWageCode  ) AND  (:empWageName IS NULL OR EWM.name like :empWageName ) AND EWM.status=:status ORDER BY EWM.code")
    List<EmpWageMstrEntity> findEmpWages(@Param("clientId") Long clientId, @Param("empWageCode") String empWageCode,
            @Param("empWageName") String empWageName, @Param("status") Integer status);

    @Query("SELECT EWM FROM EmpWageMstrEntity EWM  WHERE EWM.clientId.clientId IS NULL OR EWM.clientId.clientId=:clientId")
    List<EmpWageMstrEntity> findAllEmpWages(@Param("clientId") Long id);
}