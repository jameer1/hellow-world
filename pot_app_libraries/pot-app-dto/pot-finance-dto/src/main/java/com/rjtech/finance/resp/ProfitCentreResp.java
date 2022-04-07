package com.rjtech.finance.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.ProfitCentreTO;

public class ProfitCentreResp extends AppResp {

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
    };

}
