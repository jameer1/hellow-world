package com.rjtech.register.repository.emp;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.emp.model.EmpPayDeductionDtlEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface EmpPayDeductionDtlRepository
        extends RegisterBaseRepository<EmpPayDeductionDtlEntity, Long> {

    @Query("SELECT T FROM EmpPayDeductionDtlEntity T WHERE T.empPayDeductionEntity.id=:empPaybleRateId AND T.status=:status")
    public List<EmpPayDeductionDtlEntity> findEmpPayDeductionDtl(
            @Param("empPaybleRateId") Long empPaybleRateId, @Param("status") Integer status);

}
