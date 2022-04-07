package com.rjtech.finance.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.finance.dto.TaxOnSuperFundTaxTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;

public class SuperFundTaxResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -4980974583453409011L;
    private List<TaxOnSuperFundTaxTO> taxOnSuperFundTOs = null;

    public SuperFundTaxResp() {
        taxOnSuperFundTOs = new ArrayList<TaxOnSuperFundTaxTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<TaxOnSuperFundTaxTO> getTaxOnSuperFundTOs() {
        return taxOnSuperFundTOs;
    }

    public void setTaxOnSuperFundTOs(List<TaxOnSuperFundTaxTO> taxOnSuperFundTOs) {
        this.taxOnSuperFundTOs = taxOnSuperFundTOs;
    }

}
