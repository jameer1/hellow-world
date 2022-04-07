package com.rjtech.register.emp.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.centrallib.dto.RegisterOnLoadTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpRegisterDropDownTO;
import com.rjtech.register.emp.dto.EmpRegisterDtlTO;

public class EmpRegisterOnLoadResp extends AppResp {

    private static final long serialVersionUID = -3370208703327427455L;

    private List<EmpRegisterDtlTO> empRegisterDtlTOs = null;
    private EmpRegisterDropDownTO empRegisterDropDownTO;
    private RegisterOnLoadTO registerOnLoadTO;
    private Map<Long, LabelKeyTO> userProjMap = new HashMap<Long, LabelKeyTO>();
    private Map<String, LabelKeyTO> empProjClassMap = new HashMap<String, LabelKeyTO>();

    public EmpRegisterOnLoadResp() {
        empRegisterDtlTOs = new ArrayList<EmpRegisterDtlTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<EmpRegisterDtlTO> getEmpRegisterDtlTOs() {
        return empRegisterDtlTOs;
    }

    public void setEmpRegisterDtlTOs(List<EmpRegisterDtlTO> empRegisterDtlTOs) {
        this.empRegisterDtlTOs = empRegisterDtlTOs;
    }

    public EmpRegisterDropDownTO getEmpRegisterDropDownTO() {
        return empRegisterDropDownTO;
    }

    public void setEmpRegisterDropDownTO(EmpRegisterDropDownTO empRegisterDropDownTO) {
        this.empRegisterDropDownTO = empRegisterDropDownTO;
    }

    public RegisterOnLoadTO getRegisterOnLoadTO() {
        return registerOnLoadTO;
    }

    public void setRegisterOnLoadTO(RegisterOnLoadTO registerOnLoadTO) {
        this.registerOnLoadTO = registerOnLoadTO;
    }

    public Map<Long, LabelKeyTO> getUserProjMap() {
        return userProjMap;
    }

    public void setUserProjMap(Map<Long, LabelKeyTO> userProjMap) {
        this.userProjMap = userProjMap;
    }

    public Map<String, LabelKeyTO> getEmpProjClassMap() {
        return empProjClassMap;
    }

    public void setEmpProjClassMap(Map<String, LabelKeyTO> empProjClassMap) {
        this.empProjClassMap = empProjClassMap;
    }

}
