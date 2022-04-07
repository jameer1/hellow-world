package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.register.fixedassets.dto.RevenueSalesDtlTO;

public class RevenueSalesSaveReq extends RevenueSalesGetReq {

    private static final long serialVersionUID = 1L;

    private List<RevenueSalesDtlTO> revenueSalesDtlTOs = new ArrayList<RevenueSalesDtlTO>();

    public List<RevenueSalesDtlTO> getRevenueSalesDtlTOs() {
        return revenueSalesDtlTOs;
    }

    public void setRevenueSalesDtlTOs(List<RevenueSalesDtlTO> revenueSalesDtlTOs) {
        this.revenueSalesDtlTOs = revenueSalesDtlTOs;
    }

}
