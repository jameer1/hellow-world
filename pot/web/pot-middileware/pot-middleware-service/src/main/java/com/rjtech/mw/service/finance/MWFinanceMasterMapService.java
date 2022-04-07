package com.rjtech.mw.service.finance;

import com.rjtech.centrallib.req.UserReq;
import com.rjtech.finance.resp.FinanceMapResp;

public interface MWFinanceMasterMapService {
    public FinanceMapResp taxCodeMap(UserReq userReq);

}
