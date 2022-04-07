package com.rjtech.mw.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.admin.constans.AdminURLConstants;
import com.rjtech.common.resp.AppResp;
import com.rjtech.mw.service.role.MWRoleService;
import com.rjtech.role.dto.RoleResponse;
import com.rjtech.role.req.GetPermissionReq;
import com.rjtech.role.req.RoleDelReq;
import com.rjtech.role.req.RoleGetReq;
import com.rjtech.role.req.RoleSaveReq;
import com.rjtech.role.req.SavePermissionReq;
import com.rjtech.role.resp.ModuleResp;
import com.rjtech.role.resp.RoleResp;
import com.rjtech.user.req.UserRoleReq;

@RestController
@RequestMapping(AdminURLConstants.ROLE_PARH_URL)
public class MWRoleController {

    @Autowired
    private MWRoleService mwroleService;

    @RequestMapping(value = AdminURLConstants.GET_MODULES_BY_ROLE, method = RequestMethod.POST)
    public ResponseEntity<ModuleResp> getModulesByRole(@RequestBody UserRoleReq permissionReq) {
        return new ResponseEntity<ModuleResp>(mwroleService.getRolePermissions(permissionReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.GET_MODULES_BY_ROLE_FOR_CLIENTS, method = RequestMethod.POST)
    public ResponseEntity<ModuleResp> getModulesByRoleForClients(@RequestBody UserRoleReq permissionReq) {
        return new ResponseEntity<ModuleResp>(mwroleService.getRolePermissionsForClients(permissionReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.GET_ROLES, method = RequestMethod.POST)
    public ResponseEntity<RoleResp> getRoles(@RequestBody RoleGetReq roleGetReq) {
        return new ResponseEntity<RoleResp>(mwroleService.getRoles(roleGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.SAVE_ROLE, method = RequestMethod.POST)
    public ResponseEntity<RoleResp> saveRoles(@RequestBody RoleSaveReq roleReq) {
        return new ResponseEntity<RoleResp>(mwroleService.saveRoles(roleReq), HttpStatus.OK);

    }

    @RequestMapping(value = AdminURLConstants.SAVE_ROLE_PERMISSION, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveRolePermissions(@RequestBody SavePermissionReq savePermissionReq) {
        return new ResponseEntity<AppResp>(mwroleService.saveRolePermissions(savePermissionReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.GET_ROLE_PERMISSION, method = RequestMethod.POST)
    public ResponseEntity<RoleResponse> getRolePermissions(@RequestBody GetPermissionReq getPermissionReq) {
        return new ResponseEntity<RoleResponse>(mwroleService.getRolePermissions(getPermissionReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.DELETE_ROLES, method = RequestMethod.POST)
    public ResponseEntity<RoleResp> deleteRoles(@RequestBody RoleDelReq roleDelReq) {
        RoleResp roleResp = mwroleService.deleteRoles(roleDelReq);
        if (roleResp.isError()) {
            return new ResponseEntity<RoleResp>(roleResp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<RoleResp>(roleResp, HttpStatus.OK);
    }
}