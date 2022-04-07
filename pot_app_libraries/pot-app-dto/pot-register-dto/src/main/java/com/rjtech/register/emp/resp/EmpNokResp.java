package com.rjtech.register.emp.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpNokTO;

public class EmpNokResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -5312972811371367986L;

    private List<EmpNokTO> empNokTOs = new ArrayList<EmpNokTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<EmpNokTO> getEmpNokTOs() {
        return empNokTOs;
    }

    public void setEmpNokTOs(List<EmpNokTO> empNokTOs) {
        this.empNokTOs = empNokTOs;
    }

}
