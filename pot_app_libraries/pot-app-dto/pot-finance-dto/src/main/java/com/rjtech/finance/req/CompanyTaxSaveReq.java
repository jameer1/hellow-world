package com.rjtech.finance.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.CompanyTaxTO;

public class CompanyTaxSaveReq extends ClientTO {

    private static final long serialVersionUID = -3502222734290499167L;

    private List<CompanyTaxTO> companyTaxTOs = new ArrayList<CompanyTaxTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<CompanyTaxTO> getCompanyTaxTOs() {
        return companyTaxTOs;
    }

    public void setCompanyTaxTOs(List<CompanyTaxTO> companyTaxTOs) {
        this.companyTaxTOs = companyTaxTOs;
    }

}
