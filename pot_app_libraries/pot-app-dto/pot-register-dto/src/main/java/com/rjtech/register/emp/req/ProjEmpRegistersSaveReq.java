package com.rjtech.register.emp.req;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.register.emp.dto.ProjEmpRegisterTO;

public class ProjEmpRegistersSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 708978163908101193L;

    private ProjEmpRegisterTO projEmpRegisterTO = new ProjEmpRegisterTO();

    public ProjEmpRegisterTO getProjEmpRegisterTO() {
        return projEmpRegisterTO;
    }

    public void setProjEmpRegisterTO(ProjEmpRegisterTO projEmpRegisterTO) {
        this.projEmpRegisterTO = projEmpRegisterTO;
    }

}
