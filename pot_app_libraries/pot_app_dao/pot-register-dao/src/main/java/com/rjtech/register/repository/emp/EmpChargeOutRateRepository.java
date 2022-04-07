package com.rjtech.register.repository.emp;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.emp.model.EmpChargeOutRateEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface EmpChargeOutRateRepository extends RegisterBaseRepository<EmpChargeOutRateEntity, Long> {

    @Query("SELECT T FROM EmpChargeOutRateEntity T WHERE T.empRegisterDtlEntity.id=:empRegId AND T.status=:status ORDER BY T.fromDate DESC")
    public List<EmpChargeOutRateEntity> findEmpChargeOutRates(@Param("empRegId") Long empRegId,
            @Param("status") Integer status);
}
