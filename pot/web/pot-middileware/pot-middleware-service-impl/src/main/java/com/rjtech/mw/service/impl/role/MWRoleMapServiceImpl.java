package com.rjtech.mw.service.impl.role;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.admin.constans.AdminURLConstants;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.role.MWRoleMapService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.role.req.RoleGetReq;
import com.rjtech.role.resp.RoleMapResp;

@Service(value = "mwRoleMapService")
@RJSService(modulecode = "mwRoleMapService")
@Transactional
public class MWRoleMapServiceImpl extends RestConfigServiceImpl implements MWRoleMapService {

    public RoleMapResp RoleServiceMap(RoleGetReq roleGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.ROLE_PARH_URL + AdminURLConstants.GET_ROLESERVICE_MAP);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(roleGetReq));
        return AppUtils.fromJson(strResponse.getBody(), RoleMapResp.class);

    }
}
