package com.rjtech.register.fixedassets.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.SalesRecordsDtlTO;

public class SalesRecordsResp extends AppResp {
    private static final long serialVersionUID = 1L;

    private List<SalesRecordsDtlTO> salesRecordsDtlTOs = new ArrayList<SalesRecordsDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<SalesRecordsDtlTO> getSalesRecordsDtlTOs() {
        return salesRecordsDtlTOs;
    }

    public void setSalesRecordsDtlTOs(List<SalesRecordsDtlTO> salesRecordsDtlTOs) {
        this.salesRecordsDtlTOs = salesRecordsDtlTOs;
    }

}
