package com.rjtech.mw.service.common;

import com.rjtech.centrallib.req.UserReq;
import com.rjtech.common.resp.CommonMapResp;

public interface MWCommonMapService {

    public CommonMapResp countryCodeMap(UserReq userReq);

}
