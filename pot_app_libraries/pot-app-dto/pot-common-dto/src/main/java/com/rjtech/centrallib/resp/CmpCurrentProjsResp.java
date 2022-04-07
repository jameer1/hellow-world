package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.List;
import com.rjtech.centrallib.dto.CompanyProjectsTO;
import com.rjtech.common.resp.AppResp;


public class CmpCurrentProjsResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<CompanyProjectsTO> companyProjectsTO = null;

    public CmpCurrentProjsResp() {
        companyProjectsTO = new ArrayList<CompanyProjectsTO>(5);
    }

    public List<CompanyProjectsTO> getCompanyProjectsTO() {
        return companyProjectsTO;
    }

    public void setCompanyProjectsTO(List<CompanyProjectsTO> companyProjectsTO) {
        this.companyProjectsTO = companyProjectsTO;
    }

}
