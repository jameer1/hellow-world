package com.rjtech.finance.service;

import com.rjtech.centrallib.req.UserReq;
import com.rjtech.finance.resp.FinanceMapResp;

public interface FinanceMasterMapService {

    public FinanceMapResp taxCodeMap(UserReq userReq);

}
