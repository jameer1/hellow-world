package com.rjtech.register.repository.emp;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rjtech.register.emp.model.EmpRegisterDtlEntity;

//import com.rjtech.timemanagement.register.emp.model.EmpRegisterDtlEntityCopy;

public interface EmpRegisterRepositoryCopy extends JpaRepository<EmpRegisterDtlEntity, Long> {

}
