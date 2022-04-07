package com.rjtech.role.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.role.model.RoleMstrEntity;
import com.rjtech.role.repository.RoleRepository;
import com.rjtech.role.req.RoleGetReq;
import com.rjtech.role.resp.RoleMapResp;
import com.rjtech.role.service.RoleMapSevice;

@Service(value = "roleMapSevice")
@RJSService(modulecode = "roleMapSevice")
@Transactional
public class RoleMapSeviceImpl implements RoleMapSevice {

    @Autowired
    private RoleRepository roleRepository;

    public RoleMapResp RoleServiceMap(RoleGetReq roleGetReq) {
        RoleMapResp roleMapResp = new RoleMapResp();
        List<RoleMstrEntity> roleMstrEntities = roleRepository.findAllRoles(AppUserUtils.getClientId());
        if (CommonUtil.isListHasData(roleMstrEntities)) {
            for (RoleMstrEntity roleMstrEntity : roleMstrEntities) {
                if (CommonUtil.isNotBlankStr(roleMstrEntity.getRoleName())) {
                    roleMapResp.getUniqueRoleMap().put(roleMstrEntity.getRoleName().toUpperCase().trim(),
                            roleMstrEntity.getStatus());
                }
            }
        }
        return roleMapResp;
    }

}
