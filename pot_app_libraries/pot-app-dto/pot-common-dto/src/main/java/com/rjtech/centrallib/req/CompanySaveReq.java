package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.CompanyTO;
import com.rjtech.common.dto.ClientTO;


public class CompanySaveReq extends ClientTO {

    private static final long serialVersionUID = -3763356942184775462L;

    private List<CompanyTO> companyTOs = new ArrayList<CompanyTO>(5);

    public List<CompanyTO> getCompanyTOs() {
        return companyTOs;
    }

    public void setCompanyTOs(List<CompanyTO> companyTOs) {
        this.companyTOs = companyTOs;
    }

}