package com.rjtech.register.repository.emp;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.emp.model.EmpPayDeductionEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface EmpPayDeductionRepository extends RegisterBaseRepository<EmpPayDeductionEntity, Long> {

    @Query("SELECT ERPR FROM EmpPayDeductionEntity ERPR WHERE ERPR.empRegisterDtlEntity.id=:empRegId AND ERPR.status=:status")
    public List<EmpPayDeductionEntity> findEmpRegularPaybleRateDetails(@Param("empRegId") Long empRegId,
            @Param("status") Integer status);

    @Query("SELECT T FROM EmpPayDeductionEntity T WHERE T.id=:id AND T.status=:status")
    public List<EmpPayDeductionEntity> findEmpPayDeductionDetailsById(@Param("id") Long id,
            @Param("status") Integer status);

}
