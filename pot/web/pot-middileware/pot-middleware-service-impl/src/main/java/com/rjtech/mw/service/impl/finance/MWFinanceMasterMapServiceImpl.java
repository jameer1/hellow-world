package com.rjtech.mw.service.impl.finance;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.req.UserReq;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.finance.constants.FinanceURLConstants;
import com.rjtech.finance.resp.FinanceMapResp;
import com.rjtech.mw.service.finance.MWFinanceMasterMapService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mwFinanceMasterMapService")
@RJSService(modulecode = "mwFinanceMasterMapService")
@Transactional
public class MWFinanceMasterMapServiceImpl extends RestConfigServiceImpl implements MWFinanceMasterMapService {

    public FinanceMapResp taxCodeMap(UserReq userReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getFinancePOSTRestTemplate(AppUtils.toJson(userReq),
                FinanceURLConstants.PARH_URL + FinanceURLConstants.GET_TAXCODES_MAP);
        return AppUtils.fromJson(strResponse.getBody(), FinanceMapResp.class);
    }

}
