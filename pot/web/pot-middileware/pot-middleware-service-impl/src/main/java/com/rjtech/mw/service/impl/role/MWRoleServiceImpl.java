package com.rjtech.mw.service.impl.role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.admin.constans.AdminURLConstants;
import com.rjtech.common.resp.AppResp;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.role.MWRoleService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.role.dto.RoleResponse;
import com.rjtech.role.req.GetPermissionReq;
import com.rjtech.role.req.RoleDelReq;
import com.rjtech.role.req.RoleGetReq;
import com.rjtech.role.req.RoleSaveReq;
import com.rjtech.role.req.SavePermissionReq;
import com.rjtech.role.resp.ModuleResp;
import com.rjtech.role.resp.RoleResp;
import com.rjtech.user.req.UserRoleReq;

@Service(value = "mwroleService")
@RJSService(modulecode = "mwroleService")
@Transactional
public class MWRoleServiceImpl extends RestConfigServiceImpl implements MWRoleService {

    public ModuleResp getRolePermissions(UserRoleReq permissionReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.ROLE_PARH_URL + AdminURLConstants.GET_MODULES_BY_ROLE);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(permissionReq));
        return AppUtils.fromJson(strResponse.getBody(), ModuleResp.class);
    }

    public ModuleResp getRolePermissionsForClients(UserRoleReq permissionReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(
                AdminURLConstants.ROLE_PARH_URL + AdminURLConstants.GET_MODULES_BY_ROLE_FOR_CLIENTS);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(permissionReq));
        return AppUtils.fromJson(strResponse.getBody(), ModuleResp.class);
    }

    public RoleResp saveRoles(RoleSaveReq roleReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.ROLE_PARH_URL + AdminURLConstants.SAVE_ROLE);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(roleReq));
        return AppUtils.fromJson(strResponse.getBody(), RoleResp.class);
    }

    public RoleResp deleteRoles(RoleDelReq roleDelReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.ROLE_PARH_URL + AdminURLConstants.DELETE_ROLES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(roleDelReq));
        return AppUtils.fromJson(strResponse.getBody(), RoleResp.class);
    }

    public RoleResp getRoles(RoleGetReq roleGetReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.ROLE_PARH_URL + AdminURLConstants.GET_ROLES);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(roleGetReq));
        return AppUtils.fromJson(strResponse.getBody(), RoleResp.class);
    }

    public AppResp saveRolePermissions(SavePermissionReq savePermissionReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.ROLE_PARH_URL + AdminURLConstants.SAVE_ROLE_PERMISSION);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(savePermissionReq));
        return AppUtils.fromJson(strResponse.getBody(), AppResp.class);
    }

    public RoleResponse getRolePermissions(GetPermissionReq getPermissionReq) {
        ResponseEntity<String> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.ROLE_PARH_URL + AdminURLConstants.GET_ROLE_PERMISSION);
        strResponse = constructPOSTRestTemplate(url, AppUtils.toJson(getPermissionReq));
        return AppUtils.fromJson(strResponse.getBody(), RoleResponse.class);
    }

    public RoleResponse getRoleByUser() {
        ResponseEntity<RoleResponse> strResponse = null;
        String url = getAdminExchangeUrl(AdminURLConstants.ROLE_PARH_URL + AdminURLConstants.GET_ROLE_BY_USER);
        strResponse = constructGETRestTemplate(url, RoleResponse.class);
        return strResponse.getBody();
    }
}
