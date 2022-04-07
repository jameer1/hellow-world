package com.rjtech.register.emp.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.RegisterOnLoadTO;
import com.rjtech.centrallib.dto.RegisterProjectLibOnLoadTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpEnrollmentDtlTO;
import com.rjtech.register.emp.dto.EmpRegisterDropDownTO;
import com.rjtech.register.emp.dto.EmpRegisterDtlTO;

public class EmpEnrollmentResp extends AppResp {

    private static final long serialVersionUID = 1L;

    private EmpRegisterDtlTO empRegisterDtlTO = new EmpRegisterDtlTO();

    private List<EmpEnrollmentDtlTO> empEnrollmentDtlTOs = new ArrayList<EmpEnrollmentDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    private RegisterProjectLibOnLoadTO projectLibTO = new RegisterProjectLibOnLoadTO();

    private RegisterOnLoadTO registerOnLoadTO = new RegisterOnLoadTO();

    private EmpRegisterDropDownTO empRegisterDropDownTO = new EmpRegisterDropDownTO();

    public List<EmpEnrollmentDtlTO> getEmpEnrollmentDtlTOs() {
        return empEnrollmentDtlTOs;
    }

    public void setEmpEnrollmentDtlTOs(List<EmpEnrollmentDtlTO> empEnrollmentDtlTOs) {
        this.empEnrollmentDtlTOs = empEnrollmentDtlTOs;
    }

    public RegisterProjectLibOnLoadTO getProjectLibTO() {
        return projectLibTO;
    }

    public void setProjectLibTO(RegisterProjectLibOnLoadTO projectLibTO) {
        this.projectLibTO = projectLibTO;
    }

    public RegisterOnLoadTO getRegisterOnLoadTO() {
        return registerOnLoadTO;
    }

    public void setRegisterOnLoadTO(RegisterOnLoadTO registerOnLoadTO) {
        this.registerOnLoadTO = registerOnLoadTO;
    }

    public EmpRegisterDtlTO getEmpRegisterDtlTO() {
        return empRegisterDtlTO;
    }

    public void setEmpRegisterDtlTO(EmpRegisterDtlTO empRegisterDtlTO) {
        this.empRegisterDtlTO = empRegisterDtlTO;
    }

    public EmpRegisterDropDownTO getEmpRegisterDropDownTO() {
        return empRegisterDropDownTO;
    }

    public void setEmpRegisterDropDownTO(EmpRegisterDropDownTO empRegisterDropDownTO) {
        this.empRegisterDropDownTO = empRegisterDropDownTO;
    }

}
