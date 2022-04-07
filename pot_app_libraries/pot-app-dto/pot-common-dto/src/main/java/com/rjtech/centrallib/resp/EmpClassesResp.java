package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.EmpClassTO;
import com.rjtech.common.resp.AppResp;


public class EmpClassesResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<EmpClassTO> empClassTOs = null;

    public EmpClassesResp() {
        empClassTOs = new ArrayList<EmpClassTO>(5);
    }

    public List<EmpClassTO> getEmpClassTOs() {
        return empClassTOs;
    }

}
