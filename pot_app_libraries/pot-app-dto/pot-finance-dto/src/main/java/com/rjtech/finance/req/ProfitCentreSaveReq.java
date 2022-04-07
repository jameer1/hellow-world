package com.rjtech.finance.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.ProfitCentreTO;

public class ProfitCentreSaveReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<ProfitCentreTO> profitCentreTOs = new ArrayList<ProfitCentreTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProfitCentreTO> getProfitCentreTOs() {
        return profitCentreTOs;
    }

    public void setProfitCentreTOs(List<ProfitCentreTO> profitCentreTOs) {
        this.profitCentreTOs = profitCentreTOs;
    }

}
