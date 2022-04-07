package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.CompanyProjectsTO;


public class CompanyProjSaveReq extends CompanyGetReq {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<CompanyProjectsTO> companyProjectsTOs = new ArrayList<CompanyProjectsTO>(
            5);

    public List<CompanyProjectsTO> getCompanyProjectsTOs() {
        return companyProjectsTOs;
    }

    public void setCompanyProjectsTOs(List<CompanyProjectsTO> companyProjectsTOs) {
        this.companyProjectsTOs = companyProjectsTOs;
    }

}
