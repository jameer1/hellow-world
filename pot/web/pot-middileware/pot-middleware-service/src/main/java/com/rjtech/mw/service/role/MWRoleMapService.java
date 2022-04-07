package com.rjtech.mw.service.role;

import com.rjtech.role.req.RoleGetReq;
import com.rjtech.role.resp.RoleMapResp;

public interface MWRoleMapService {

    public RoleMapResp RoleServiceMap(RoleGetReq roleGetReq);

}
