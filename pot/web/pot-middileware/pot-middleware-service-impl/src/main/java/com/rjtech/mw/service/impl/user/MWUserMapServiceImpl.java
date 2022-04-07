package com.rjtech.mw.service.impl.user;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.admin.constans.AdminURLConstants;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.user.MWUserMapService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.user.req.ClientReq;
import com.rjtech.user.resp.ClientMapResp;
import com.rjtech.user.resp.UserMapResp;

@Service(value = "mwUserMapService")
@RJSService(modulecode = "mwUserMapService")
@Transactional
public class MWUserMapServiceImpl extends RestConfigServiceImpl implements MWUserMapService {

    public UserMapResp UserServiceMap(ClientReq clientReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.USER_PARH_URL + AdminURLConstants.GET_USERSERVICE_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(clientReq));
        return AppUtils.fromJson(strResponse.getBody(), UserMapResp.class);

    }

    public ClientMapResp ClientServiceMap(ClientReq clientReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.USER_PARH_URL + AdminURLConstants.GET_CLIENTSERVICE_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(clientReq));
        return AppUtils.fromJson(strResponse.getBody(), ClientMapResp.class);

    }

    public ClientMapResp emailServiceMap(ClientReq clientReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.USER_PARH_URL + AdminURLConstants.GET_EMAILSERVICE_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(clientReq));
        return AppUtils.fromJson(strResponse.getBody(), ClientMapResp.class);

    }
}
