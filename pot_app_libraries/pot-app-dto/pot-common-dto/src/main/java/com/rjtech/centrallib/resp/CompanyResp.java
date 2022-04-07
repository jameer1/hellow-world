package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.CompanyTO;
import com.rjtech.common.resp.AppResp;


public class CompanyResp extends AppResp {
    private static final long serialVersionUID = 2950084862079755L;

    private List<CompanyTO> companyTOs = null;

    public CompanyResp() {
        companyTOs = new ArrayList<CompanyTO>(5);
    }

    public List<CompanyTO> getCompanyTOs() {
        return companyTOs;
    }

    public void setCompanyTOs(List<CompanyTO> companyTOs) {
        this.companyTOs = companyTOs;
    }

}
