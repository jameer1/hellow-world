package com.rjtech.register.fixedassets.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.RevenueSalesDtlTO;

public class RevenueSalesResp extends AppResp {
    private static final long serialVersionUID = 1L;

    private List<RevenueSalesDtlTO> revenueSalesDtlTOs = new ArrayList<RevenueSalesDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<RevenueSalesDtlTO> getRevenueSalesDtlTOs() {
        return revenueSalesDtlTOs;
    }

    public void setRevenueSalesDtlTOs(List<RevenueSalesDtlTO> revenueSalesDtlTOs) {
        this.revenueSalesDtlTOs = revenueSalesDtlTOs;
    }

}
