package com.rjtech.register.repository.emp;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.emp.model.EmpProvidentFundEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface EmpProvidentFundRepository extends RegisterBaseRepository<EmpProvidentFundEntity, Long> {

    @Query("SELECT T FROM EmpProvidentFundEntity T WHERE T.empRegisterDtlEntity.id=:empRegId AND T.status=:status")
    public List<EmpProvidentFundEntity> findEmpProvidentFunds(@Param("empRegId") Long empRegId,
            @Param("status") Integer status);

    @Query("SELECT T FROM EmpProvidentFundEntity T WHERE T.id=:id AND T.status=:status")
    public List<EmpProvidentFundEntity> findEmpProvidentFundDetailsById(@Param("id") Long id,
            @Param("status") Integer status);
}
