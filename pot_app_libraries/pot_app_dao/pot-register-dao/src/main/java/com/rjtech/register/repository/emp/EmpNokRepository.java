package com.rjtech.register.repository.emp;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.emp.model.EmpNokEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface EmpNokRepository extends RegisterBaseRepository<EmpNokEntity, Long> {

    @Query("SELECT ENC FROM EmpNokEntity ENC WHERE ENC.empRegisterDtlEntity.id=:empId AND ENC.status=:status")
    public List<EmpNokEntity> findEmpNok(@Param("empId") Long empId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE EmpNokEntity ENE  SET ENE.status=:status  where ENE.id in :empNokIds ")
    void deactivateEmpNok(@Param("empNokIds") List<Long> empNokIds, @Param("status") Integer status);

}
