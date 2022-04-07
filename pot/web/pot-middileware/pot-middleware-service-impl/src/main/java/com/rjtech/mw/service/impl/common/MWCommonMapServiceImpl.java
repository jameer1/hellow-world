package com.rjtech.mw.service.impl.common;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.req.UserReq;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.resp.CommonMapResp;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.common.MWCommonMapService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mwCommonMapService")
@RJSService(modulecode = "mwCommonMapService")
@Transactional
public class MWCommonMapServiceImpl extends RestConfigServiceImpl implements MWCommonMapService {

    public CommonMapResp countryCodeMap(UserReq userReq) {
        ResponseEntity<String> strResponse = null;
        String url = getCentralLibExchangeUrl(CommonConstants.PARH_URL + CommonConstants.GET_COUNTRY_CODE_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(userReq));
        return AppUtils.fromJson(strResponse.getBody(), CommonMapResp.class);
    }

}
