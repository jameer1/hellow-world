package com.rjtech.register.repository.emp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.emp.model.EmpContactDtlEntity;

//import com.rjtech.timemanagement.register.emp.model.EmpContactDtlEntityCopy;

public interface EmpContactDtlRepositoryCopy extends JpaRepository<EmpContactDtlEntity, Long> {

    @Query("SELECT T FROM EmpContactDtlEntity T WHERE T.empRegisterDtlEntity.id=:empRegId")
    public EmpContactDtlEntity findEmpContacts(@Param("empRegId") Long empRegId);

}
