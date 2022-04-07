package com.rjtech.register.repository.emp;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.emp.model.EmpMedicalHistoryEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface EmpMedicalHistoryRepository extends RegisterBaseRepository<EmpMedicalHistoryEntity, Long> {

    @Query("SELECT EMH FROM EmpMedicalHistoryEntity EMH WHERE EMH.empRegisterDtlEntity.id=:empRegId AND EMH.status=:status")
    public List<EmpMedicalHistoryEntity> findEmpMedicalHistory(@Param("empRegId") Long empId,
            @Param("status") Integer status);

}
