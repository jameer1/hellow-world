package com.rjtech.register.emp.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.RegisterOnLoadTO;
import com.rjtech.centrallib.dto.RegisterProjectLibOnLoadTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpRegisterDtlTO;
import com.rjtech.register.emp.dto.ProjEmpRegisterTO;

public class EmpServiceHistoryResp extends AppResp {

    public EmpServiceHistoryResp() {

    }

    private static final long serialVersionUID = 1L;

    private EmpRegisterDtlTO empRegisterDtlTO = new EmpRegisterDtlTO();

    private List<ProjEmpRegisterTO> projEmpRegisterTOs = new ArrayList<ProjEmpRegisterTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    private RegisterProjectLibOnLoadTO projectLibTO = new RegisterProjectLibOnLoadTO();

    private RegisterOnLoadTO registerOnLoadTO = new RegisterOnLoadTO();

    List<String> empServiceType;

    public List<ProjEmpRegisterTO> getProjEmpRegisterTOs() {
        return projEmpRegisterTOs;
    }

    public void setProjEmpRegisterTOs(List<ProjEmpRegisterTO> projEmpRegisterTOs) {
        this.projEmpRegisterTOs = projEmpRegisterTOs;
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

    public List<String> getEmpServiceType() {
        return empServiceType;
    }

    public void setEmpServiceType(List<String> empServiceType) {
        this.empServiceType = empServiceType;
    }

}
