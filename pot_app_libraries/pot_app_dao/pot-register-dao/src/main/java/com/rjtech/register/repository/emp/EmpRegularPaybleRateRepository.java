package com.rjtech.register.repository.emp;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.emp.model.EmpRegularPayRateEntity;
import com.rjtech.register.repository.RegisterBaseRepository;


public interface EmpRegularPaybleRateRepository extends RegisterBaseRepository<EmpRegularPayRateEntity, Long> {

    @Query("SELECT ERPR FROM EmpRegularPayRateEntity ERPR WHERE ERPR.empRegisterDtlEntity.id=:empId AND ERPR.status=:status")
    public List<EmpRegularPayRateEntity> findEmpRegularPaybleRates(@Param("empId") Long empId,
            @Param("status") Integer status);
    
    

}
