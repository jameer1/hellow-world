package com.rjtech.register.fixedassets.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.LongTermLeaseActualRetrunsDtlTO;

public class LongTermLeaseActualRetrunsResp extends AppResp {
    private static final long serialVersionUID = 1L;    
    
    private List<LongTermLeaseActualRetrunsDtlTO> longTermLeaseActualRetrunsDtlTOs = new ArrayList<LongTermLeaseActualRetrunsDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<LongTermLeaseActualRetrunsDtlTO> getLongTermLeaseActualRetrunsDtlTOs() {
        return longTermLeaseActualRetrunsDtlTOs;
    }

    public void setLongTermLeaseActualRetrunsDtlTOs(
            List<LongTermLeaseActualRetrunsDtlTO> longTermLeaseActualRetrunsDtlTOs) {
        this.longTermLeaseActualRetrunsDtlTOs = longTermLeaseActualRetrunsDtlTOs;
    }

}
