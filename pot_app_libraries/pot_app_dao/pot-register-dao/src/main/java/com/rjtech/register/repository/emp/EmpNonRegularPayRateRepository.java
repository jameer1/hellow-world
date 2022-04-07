package com.rjtech.register.repository.emp;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.emp.model.EmpNonRegularPayRateEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface EmpNonRegularPayRateRepository extends RegisterBaseRepository<EmpNonRegularPayRateEntity, Long> {

    @Query("SELECT ENPR FROM EmpNonRegularPayRateEntity ENPR WHERE ENPR.empRegisterDtlEntity.id=:empId AND ENPR.status=:status")
    public List<EmpNonRegularPayRateEntity> findEmpNonRegularPayRates(@Param("empId") Long empId,
            @Param("status") Integer status);
}
