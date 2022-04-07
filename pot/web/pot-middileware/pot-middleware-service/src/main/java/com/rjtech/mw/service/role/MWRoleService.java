package com.rjtech.mw.service.role;

import com.rjtech.common.resp.AppResp;
import com.rjtech.role.dto.RoleResponse;
import com.rjtech.role.req.GetPermissionReq;
import com.rjtech.role.req.RoleDelReq;
import com.rjtech.role.req.RoleGetReq;
import com.rjtech.role.req.RoleSaveReq;
import com.rjtech.role.req.SavePermissionReq;
import com.rjtech.role.resp.ModuleResp;
import com.rjtech.role.resp.RoleResp;
import com.rjtech.user.req.UserRoleReq;

public interface MWRoleService {

    public ModuleResp getRolePermissions(UserRoleReq userRoleReq);

    public ModuleResp getRolePermissionsForClients(UserRoleReq permissionReq);

    public RoleResp saveRoles(RoleSaveReq roleReq);

    public RoleResp getRoles(RoleGetReq roleGetReq);

    public AppResp saveRolePermissions(SavePermissionReq savePermissionReq);

    public RoleResponse getRolePermissions(GetPermissionReq getPermissionReq);

    public RoleResponse getRoleByUser();

    public RoleResp deleteRoles(RoleDelReq roleDelReq);

}
