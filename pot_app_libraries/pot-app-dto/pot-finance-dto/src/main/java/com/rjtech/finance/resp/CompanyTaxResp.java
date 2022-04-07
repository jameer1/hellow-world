package com.rjtech.finance.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.finance.dto.CompanyTaxTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class CompanyTaxResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -1532449805945497592L;
    private List<CompanyTaxTO> companyTaxTOs = null;

    public CompanyTaxResp() {
        companyTaxTOs = new ArrayList<CompanyTaxTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<CompanyTaxTO> getCompanyTaxTOs() {
        return companyTaxTOs;
    }

    public void setCompanyTaxTOs(List<CompanyTaxTO> companyTaxTOs) {
        this.companyTaxTOs = companyTaxTOs;
    }

}
