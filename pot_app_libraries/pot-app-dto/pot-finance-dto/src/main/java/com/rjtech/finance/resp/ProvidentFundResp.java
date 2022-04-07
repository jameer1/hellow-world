package com.rjtech.finance.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.ProvidentFundTO;

public class ProvidentFundResp extends AppResp {

    private static final long serialVersionUID = -1253574946584076861L;
    private List<ProvidentFundTO> providentFundTOs = null;

    public ProvidentFundResp() {
        providentFundTOs = new ArrayList<ProvidentFundTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProvidentFundTO> getProvidentFundTOs() {
        return providentFundTOs;
    }

    public void setProvidentFundTOs(List<ProvidentFundTO> providentFundTOs) {
        this.providentFundTOs = providentFundTOs;
    }

}
