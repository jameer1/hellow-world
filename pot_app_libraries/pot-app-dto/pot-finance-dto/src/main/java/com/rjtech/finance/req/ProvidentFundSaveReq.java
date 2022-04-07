package com.rjtech.finance.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.ProvidentFundTO;

public class ProvidentFundSaveReq extends ClientTO {

    private static final long serialVersionUID = 3468031742343165356L;
    private List<ProvidentFundTO> providentFundTOs = new ArrayList<ProvidentFundTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<ProvidentFundTO> getProvidentFundTOs() {
        return providentFundTOs;
    }

    public void setProvidentFundTOs(List<ProvidentFundTO> providentFundTOs) {
        this.providentFundTOs = providentFundTOs;
    }

}
