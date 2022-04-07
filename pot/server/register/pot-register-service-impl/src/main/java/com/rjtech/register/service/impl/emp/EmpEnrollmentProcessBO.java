package com.rjtech.register.service.impl.emp;

import com.rjtech.register.emp.model.EmpEnrollmentDtlEntity;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;

public class EmpEnrollmentProcessBO {

    private EmpEnrollmentDtlEntity empEnrollmentDtlEntity = new EmpEnrollmentDtlEntity();
    private EmpProjRigisterEntity empProjRigisterEntity = new EmpProjRigisterEntity();

    public EmpEnrollmentDtlEntity getEmpEnrollmentDtlEntity() {
        return empEnrollmentDtlEntity;
    }

    public EmpProjRigisterEntity getProjEmpRigisterEntity() {
        return empProjRigisterEntity;
    }

}
