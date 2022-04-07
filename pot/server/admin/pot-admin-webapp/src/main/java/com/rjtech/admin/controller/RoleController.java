package com.rjtech.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.admin.constans.AdminURLConstants;
import com.rjtech.common.resp.AppResp;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.role.dto.RoleResponse;
import com.rjtech.role.req.GetPermissionReq;
import com.rjtech.role.req.RoleDelReq;
import com.rjtech.role.req.RoleGetReq;
import com.rjtech.role.req.RoleSaveReq;
import com.rjtech.role.req.SavePermissionReq;
import com.rjtech.role.resp.ModuleResp;
import com.rjtech.role.resp.RoleResp;
import com.rjtech.role.service.RoleService;
import com.rjtech.user.req.UserRoleReq;

@RestController
@RequestMapping(AdminURLConstants.ROLE_PARH_URL)
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = AdminURLConstants.GET_MODULES_BY_ROLE, method = RequestMethod.POST)
    public ResponseEntity<ModuleResp> getModulesByRole(@RequestBody UserRoleReq permissionReq) {
        return new ResponseEntity<ModuleResp>(roleService.getRolePermissions(permissionReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.GET_MODULES_BY_ROLE_FOR_CLIENTS, method = RequestMethod.POST)
    public ResponseEntity<ModuleResp> getModulesByRoleForClients(@RequestBody UserRoleReq permissionReq) {
        return new ResponseEntity<ModuleResp>(roleService.getRolePermissionsForClients(permissionReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.GET_ROLES, method = RequestMethod.POST)
    public ResponseEntity<RoleResp> getRoles(@RequestBody RoleGetReq roleGetReq) {
        return new ResponseEntity<RoleResp>(roleService.getRoles(roleGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.SAVE_ROLE, method = RequestMethod.POST)
    public ResponseEntity<RoleResp> saveRoles(@RequestBody RoleSaveReq roleReq) {
        roleService.saveRoles(roleReq);

        RoleGetReq roleGetReq = new RoleGetReq();
        roleGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        RoleResp roleResp = roleService.getRoles(roleGetReq);

        roleResp.cloneAppResp(CommonUtil.getSaveAppResp());

        return new ResponseEntity<RoleResp>(roleResp, HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.DELETE_ROLES, method = RequestMethod.POST)
    public ResponseEntity<RoleResp> deleteRoles(@RequestBody RoleDelReq roleDelReq) {

        String rolesWhichCannotBeDeleted = roleService.deleteRoles(roleDelReq);
        RoleGetReq roleGetReq = new RoleGetReq();
        roleGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        RoleResp roleResp = roleService.getRoles(roleGetReq);
        if (rolesWhichCannotBeDeleted.length() > 0) {
            roleResp.setError(true);
            AppResp appResp = new AppResp();
            appResp.setMessage("Could not delete following role(s) " + rolesWhichCannotBeDeleted
                    + ", as these roles were assigned to users.");
            appResp.setStatus("Error");
            roleResp.cloneAppResp(appResp);
        } else {
            roleResp.cloneAppResp(CommonUtil.getDeleteAppResp());
        }
        return new ResponseEntity<RoleResp>(roleResp, HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.SAVE_ROLE_PERMISSION, method = RequestMethod.POST)
    public ResponseEntity<AppResp> saveRolePermissions(@RequestBody SavePermissionReq savePermissionReq) {
        roleService.saveRolePermissions(savePermissionReq);
        AppResp appResp = CommonUtil.getSaveAppResp();
        return new ResponseEntity<AppResp>(appResp, HttpStatus.OK);

    }

    @RequestMapping(value = AdminURLConstants.GET_ROLE_PERMISSION)
    public ResponseEntity<RoleResponse> getRolePermissions(@RequestBody GetPermissionReq getPermissionReq) {
        return new ResponseEntity<RoleResponse>(roleService.getRolePermissions(getPermissionReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.GET_ROLE_BY_USER)
    public ResponseEntity<RoleResponse> getRoleByUser() {
        return new ResponseEntity<RoleResponse>(roleService.getUserRole(), HttpStatus.OK);
    }
}