package com.rjtech.register.repository.emp;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.emp.model.EmpRegularPayRateDetailEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface EmpRegularPaybleRateDetailRepository
        extends RegisterBaseRepository<EmpRegularPayRateDetailEntity, Long> {

    @Query("SELECT T FROM EmpRegularPayRateDetailEntity T WHERE T.empRegularPayRateEntity.id=:empPaybleRateId AND T.status=:status")
    public List<EmpRegularPayRateDetailEntity> findEmpRegularPaybleRateDetails(
            @Param("empPaybleRateId") Long empPaybleRateId, @Param("status") Integer status);

}
