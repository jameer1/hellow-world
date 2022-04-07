package com.rjtech.register.emp.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.ProjEmpRegisterTO;

public class ProjEmpRegisterResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -3847339329373587407L;

    private List<ProjEmpRegisterTO> projEmpRegisterTOs = new ArrayList<ProjEmpRegisterTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProjEmpRegisterTO> getProjEmpRegisterTOs() {
        return projEmpRegisterTOs;
    }

    public void setProjEmpRegisterTOs(List<ProjEmpRegisterTO> projEmpRegisterTOs) {
        this.projEmpRegisterTOs = projEmpRegisterTOs;
    }

}
