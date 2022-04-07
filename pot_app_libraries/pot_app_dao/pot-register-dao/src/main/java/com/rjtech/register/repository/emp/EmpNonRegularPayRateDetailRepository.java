package com.rjtech.register.repository.emp;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.emp.model.EmpNonRegularPayDetailEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface EmpNonRegularPayRateDetailRepository
        extends RegisterBaseRepository<EmpNonRegularPayDetailEntity, Long> {

    @Query("SELECT T FROM EmpNonRegularPayDetailEntity T WHERE T.empNonRegularPayRateEntity.id=:empPaybleRateId AND T.status=:status")
    public List<EmpNonRegularPayDetailEntity> findEmpNonRegularPayRateDetails(
            @Param("empPaybleRateId") Long empPaybleRateId, @Param("status") Integer status);

}
