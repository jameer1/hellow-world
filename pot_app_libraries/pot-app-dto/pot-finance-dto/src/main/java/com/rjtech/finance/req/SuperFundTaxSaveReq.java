package com.rjtech.finance.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.TaxOnSuperFundTaxTO;

public class SuperFundTaxSaveReq extends ClientTO {

    private static final long serialVersionUID = 1854182786048770609L;

    private List<TaxOnSuperFundTaxTO> taxOnSuperFundTOs = new ArrayList<TaxOnSuperFundTaxTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<TaxOnSuperFundTaxTO> getTaxOnSuperFundTOs() {
        return taxOnSuperFundTOs;
    }

    public void setTaxOnSuperFundTOs(List<TaxOnSuperFundTaxTO> taxOnSuperFundTOs) {
        this.taxOnSuperFundTOs = taxOnSuperFundTOs;
    }

}
