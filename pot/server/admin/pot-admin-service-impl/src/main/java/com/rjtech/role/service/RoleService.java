package com.rjtech.role.service;

import com.rjtech.role.dto.RoleResponse;
import com.rjtech.role.req.GetPermissionReq;
import com.rjtech.role.req.RoleDelReq;
import com.rjtech.role.req.RoleGetReq;
import com.rjtech.role.req.RoleSaveReq;
import com.rjtech.role.req.SavePermissionReq;
import com.rjtech.role.resp.ModuleResp;
import com.rjtech.role.resp.RoleResp;
import com.rjtech.user.req.UserRoleReq;

public interface RoleService {

    public ModuleResp getRolePermissions(UserRoleReq permissionReq);

    public ModuleResp getRolePermissionsForClients(UserRoleReq permissionReq);

    public void saveRoles(RoleSaveReq roleReq);

    public String deleteRoles(RoleDelReq roleDelReq);

    public RoleResp getRoles(RoleGetReq roleGetReq);

    public void saveRolePermissions(SavePermissionReq savePermissionReq);

    public RoleResponse getRolePermissions(GetPermissionReq savePermissionReq);

    public RoleResponse getUserRole();
}
