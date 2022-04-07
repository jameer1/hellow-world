package com.rjtech.register.repository.emp;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.emp.model.EmpBankAccountDtlEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface EmpBankAccountDtlRepository extends RegisterBaseRepository<EmpBankAccountDtlEntity, Long> {

    @Query("SELECT EBAD FROM EmpBankAccountDtlEntity EBAD WHERE EBAD.empRegisterDtlEntity.id=:empId AND EBAD.status=:status")
    public List<EmpBankAccountDtlEntity> findEmpBankAccounts(@Param("empId") Long empId,
            @Param("status") Integer status);

    @Modifying
    @Query("UPDATE EmpBankAccountDtlEntity EBAD SET  EBAD.status=:status  where  EBAD.id in :empBankAccountIds ")
    void deactivateEmpBankAccounts(@Param("empBankAccountIds") List<Long> empBankAccountIds,
            @Param("status") Integer status);
}
