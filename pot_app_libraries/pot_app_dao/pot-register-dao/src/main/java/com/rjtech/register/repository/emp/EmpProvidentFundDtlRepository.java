package com.rjtech.register.repository.emp;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.emp.model.EmpProvidentFundDetailEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface EmpProvidentFundDtlRepository
        extends RegisterBaseRepository<EmpProvidentFundDetailEntity, Long> {

    @Query("SELECT T FROM EmpProvidentFundDetailEntity T WHERE T.empProvidentFundEntity.id=:empPaybleRateId AND T.status=:status")
    public List<EmpProvidentFundDetailEntity> findEmpProvidentFundDtl(
            @Param("empPaybleRateId") Long empPaybleRateId, @Param("status") Integer status);

}
