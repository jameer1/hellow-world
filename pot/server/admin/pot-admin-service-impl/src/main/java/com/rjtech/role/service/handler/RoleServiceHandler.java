package com.rjtech.role.service.handler;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.role.dto.RoleTO;
import com.rjtech.role.model.RoleMstrEntity;

public class RoleServiceHandler {

    public static RoleTO populateRolePOJOFromEntity(RoleMstrEntity roleMstrEntity) {
        RoleTO roleTO = new RoleTO();
        if (CommonUtil.isNonBlankLong(roleMstrEntity.getRoleId())) {
            roleTO.setRoleId(roleMstrEntity.getRoleId());
        }
        roleTO.setRoleName(roleMstrEntity.getRoleName());
        ClientRegEntity client = roleMstrEntity.getClientId();
        if (client != null) {
            roleTO.setClientId(client.getClientId());
        }

        if ((AppUserUtils.getAdminClientId() == null || !AppUserUtils.getAdminClientId().equals(1L))
                && client == null) {
            roleTO.setDefaultRole(true);
        }
        roleTO.setStatus(roleMstrEntity.getStatus());
        return roleTO;
    }

}