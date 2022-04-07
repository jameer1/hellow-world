package com.rjtech.centrallib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.EmpClassMstrEntity;

public interface EmpClassRepository extends CentralLibRepository<EmpClassMstrEntity, Long> {

    @Query("SELECT ECM FROM EmpClassMstrEntity ECM  WHERE (ECM.clientId.clientId IS NULL OR ECM.clientId.clientId=:clientId ) AND  ECM.status=:status ORDER BY ECM.code")
    List<EmpClassMstrEntity> findByClientId(@Param("clientId") Long clientId, @Param("status") Integer status);

    @Query("SELECT ECM FROM EmpClassMstrEntity ECM  WHERE (ECM.clientId.clientId IS NULL OR ECM.clientId.clientId=:clientId )  AND (:empCode IS NULL OR ECM.code like :empCode ) AND  (:empName IS NULL OR ECM.name like :empName )  AND ECM.status=:status ORDER BY ECM.code")
    List<EmpClassMstrEntity> findEmpClass(@Param("clientId") Long clientId, @Param("empCode") String empCode,
            @Param("empName") String empName, @Param("status") Integer status);

    @Query("SELECT ECM FROM EmpClassMstrEntity ECM  WHERE ECM.clientId.clientId IS NULL OR ECM.clientId.clientId=:clientId ")
    List<EmpClassMstrEntity> findAllEmpClass(@Param("clientId") Long clientId);

    @Query("SELECT T FROM EmpClassMstrEntity T WHERE T.clientId.clientId=:crmId AND T.code=:code AND T.status=:status")
    public EmpClassMstrEntity findBy(@Param("crmId") Long crmId, @Param("code") String code, @Param("status") Integer status);
}