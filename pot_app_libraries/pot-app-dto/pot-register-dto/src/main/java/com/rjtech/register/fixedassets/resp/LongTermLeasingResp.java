package com.rjtech.register.fixedassets.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.FixedAssetDtlTO;
import com.rjtech.register.fixedassets.dto.LongTermLeasingDtlTO;

public class LongTermLeasingResp extends AppResp {
    private static final long serialVersionUID = 1L;

    private List<LongTermLeasingDtlTO> longTermLeasingDtlTOs = new ArrayList<LongTermLeasingDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<LongTermLeasingDtlTO> getLongTermLeasingDtlTOs() {
        return longTermLeasingDtlTOs;
    }

    public void setLongTermLeasingDtlTOs(List<LongTermLeasingDtlTO> longTermLeasingDtlTOs) {
        this.longTermLeasingDtlTOs = longTermLeasingDtlTOs;
    }

}
