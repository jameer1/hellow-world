package com.rjtech.register.repository.emp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.emp.model.EmpChargeOutRateEntity;

//import com.rjtech.timemanagement.register.emp.model.EmpChargeOutRateEntityCopy;

public interface EmpChargeOutRateRepositoryCopy extends JpaRepository<EmpChargeOutRateEntity, Long> {

    // TODO, replace with chargeout rate effective from code
    @Query("SELECT T FROM EmpChargeOutRateEntity T WHERE T.empRegisterDtlEntity.id=:empRegId AND T.latest = 1")
    public List<EmpChargeOutRateEntity> findEmpChargeOutRates(@Param("empRegId") Long empRegId);

}
