package com.rjtech.role.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.repository.ClientRegRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.role.dto.RolePermResp;
import com.rjtech.role.dto.RoleResponse;
import com.rjtech.role.dto.RoleTO;
import com.rjtech.role.model.RoleMstrEntity;
import com.rjtech.role.model.RolePermissionEntity;
import com.rjtech.role.repository.RolePermissionRepository;
import com.rjtech.role.repository.RoleRepository;
import com.rjtech.role.req.GetPermissionReq;
import com.rjtech.role.req.PermissionReq;
import com.rjtech.role.req.RoleDelReq;
import com.rjtech.role.req.RoleGetReq;
import com.rjtech.role.req.RoleSaveReq;
import com.rjtech.role.req.SavePermissionReq;
import com.rjtech.role.resp.ModuleResp;
import com.rjtech.role.resp.RoleResp;
import com.rjtech.role.service.RoleService;
import com.rjtech.role.service.handler.RoleServiceHandler;
import com.rjtech.user.repository.UserRepository;
import com.rjtech.user.req.UserRoleReq;

@Service(value = "roleService")
@RJSService(modulecode = "roleService")
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ClientRegRepository clientRegRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private UserRepository userRepository;

    public ModuleResp getRolePermissions(UserRoleReq userRoleReq) {
        return null;
    }

    public ModuleResp getRolePermissionsForClients(UserRoleReq userRoleReq) {
        return null;
    }

    public void saveRoles(RoleSaveReq roleSaveReq) {
        List<RoleTO> roleTos = roleSaveReq.getRoleTOs();
        Long clientId = AppUserUtils.getClientId();
        if (clientId == null) {
            clientId = userRepository.findOne(AppUserUtils.getUserId()).getClientRegMstrEntity().getClientId();
        }
        RoleMstrEntity defaultRole = roleRepository.findDefaultRole();
        List<RolePermissionEntity> defaultRolePermissions = defaultRole.getPermissions();
        List<RoleMstrEntity> savedRoles = new ArrayList<RoleMstrEntity>();
        ClientRegEntity clientRegEntity = clientRegRepository.findOne(clientId);
        for (RoleTO roleTo : roleTos) {
            RoleMstrEntity roleMasterEntity = new RoleMstrEntity();
            if (CommonUtil.objectNotNull(roleTo.getRoleId())) {
                roleMasterEntity = roleRepository.findOne(roleTo.getRoleId());
                roleMasterEntity.setEdit(true);
            } else {
                Long adminClientId = AppUserUtils.getAdminClientId();
                // Don't set client ID for RoleMstrEntity when Raju is creating a role
                if (adminClientId == null || !adminClientId.equals(1L))
                    roleMasterEntity.setClientId(clientRegEntity);
                roleMasterEntity.setStatus(1);
            }
            roleMasterEntity.setRoleName(roleTo.getRoleName());
            savedRoles.add(roleRepository.save(roleMasterEntity));
        }

        for (RoleMstrEntity role : savedRoles) {
            if (!role.isEdit()) {
                List<RolePermissionEntity> permissions = new ArrayList<RolePermissionEntity>();
                for (RolePermissionEntity defaultRolePerm : defaultRolePermissions) {
                    RolePermissionEntity permission = new RolePermissionEntity();
                    permission.setClientId(clientRegEntity);
                    permission.setRoleMstrEntity(role);
                    permission.setPermission(defaultRolePerm.getPermission());
                    permissions.add(rolePermissionRepository.save(permission));
                }
                role.setPermissions(permissions);
                roleRepository.save(role);
            }
        }
    }

    public RoleResp getRoles(RoleGetReq roleGetReq) {
        RoleResp roleResp = new RoleResp();
        Long clientId = AppUserUtils.getClientId();
        if (clientId == null) {
            clientId = userRepository.findOne(AppUserUtils.getUserId()).getClientRegMstrEntity().getClientId();
        }
        List<RoleMstrEntity> roleMstrEntities = roleRepository.findRoles(clientId, roleGetReq.getStatus());
        for (RoleMstrEntity roleMstrEntity : roleMstrEntities) {
            roleResp.getRoleTOs().add(RoleServiceHandler.populateRolePOJOFromEntity(roleMstrEntity));
        }
        return roleResp;
    }

    public String deleteRoles(RoleDelReq roleDelReq) {
        StringBuilder rolesWhichCannotBeDeleted = new StringBuilder();
        for (Long roleId : roleDelReq.getRoleIds()) {
            RoleMstrEntity roleMasterEntity = roleRepository.findOne(roleId);
            if (roleMasterEntity.getUserRoleMapEntities() != null
                    && !roleMasterEntity.getUserRoleMapEntities().isEmpty()) {
                rolesWhichCannotBeDeleted.append("'" + roleMasterEntity.getRoleName() + "' ");
            } else {
                roleRepository.delete(roleMasterEntity);
            }
        }
        return rolesWhichCannotBeDeleted.toString();
    }

    public void saveRolePermissions(SavePermissionReq savePermissionReq) {
        Long roleId = savePermissionReq.getRoleId();
        RoleMstrEntity roleMasterEntity = roleRepository.findOne(roleId);
        Long clientId = AppUserUtils.getClientId();
        if (clientId == null) {
            clientId = userRepository.findOne(AppUserUtils.getUserId()).getClientRegMstrEntity().getClientId();
        }
        ClientRegEntity client = clientRegRepository.findOne(clientId);
        List<RolePermissionEntity> rolePermissionsList = rolePermissionRepository.getRolePermissions(roleId, clientId);
        List<PermissionReq> permissionReqList = savePermissionReq.getPermissions();
        if (rolePermissionsList == null)
            rolePermissionsList = new ArrayList<RolePermissionEntity>();
        System.out.println();
        for (PermissionReq permission : permissionReqList) {
            if (permission.getRolePermId() == null && !permission.isRemove()) {
                RolePermissionEntity rolePerm = rolePermissionRepository.getPermission(roleId, clientId,
                        permission.getPermission());
                if (rolePerm == null) {
                    rolePerm = new RolePermissionEntity();
                    rolePerm.setPermission(permission.getPermission());
                    rolePerm.setClientId(client);
                    rolePerm.setRoleMstrEntity(roleMasterEntity);
                    rolePermissionsList.add(rolePerm);
                }
            } else if (permission.getRolePermId() != null && permission.isRemove()) {
                rolePermissionRepository.delete(permission.getRolePermId());
                rolePermissionsList = rolePermissionRepository.getRolePermissions(roleId, clientId);
            } else if (permission.getRolePermId() == null && permission.isRemove()) {
                RolePermissionEntity rolePerm = rolePermissionRepository.getPermission(roleId, clientId,
                        permission.getPermission());
                if (rolePerm != null) {
                    rolePermissionRepository.delete(rolePerm);
                    rolePermissionsList = rolePermissionRepository.getRolePermissions(roleId, clientId);
                }
            }
        }
        if (rolePermissionsList.size() > 0)
            rolePermissionRepository.save(rolePermissionsList);
    }

    public RoleResponse getRolePermissions(GetPermissionReq getPermissionReq) {
        RoleResponse roleResponse = new RoleResponse();
        List<RolePermResp> rolePermissions = new ArrayList<RolePermResp>();
        List<String> permissions = new ArrayList<>();
        RoleMstrEntity roleMstrEntity = roleRepository.findOne(getPermissionReq.getRoleId());
        List<RolePermissionEntity> rolePermissionEntites = null;
        if (roleMstrEntity.getClientId() == null) {
            rolePermissionEntites = rolePermissionRepository.getRolePermissions(roleMstrEntity.getRoleId());
        } else {
            rolePermissionEntites = rolePermissionRepository.getRolePermissions(roleMstrEntity.getRoleId(),
                    roleMstrEntity.getClientId().getClientId());
        }

        for (RolePermissionEntity rolePerm : rolePermissionEntites) {
            RolePermResp rolePermTo = new RolePermResp();
            rolePermTo.setId(rolePerm.getId());
            rolePermTo.setPermission(rolePerm.getPermission());
            rolePermissions.add(rolePermTo);
            permissions.add(rolePerm.getPermission());
        }
        roleResponse.setRolePermissions(rolePermissions);
        roleResponse.setPermissions(permissions);
        //log.info("RoleResponse {}  {}", roleResponse.getRolePermissions().get(0).getPermission(),
        //       roleResponse.getRolePermissions().get(0).getId());
        return roleResponse;
    }

    private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    public RoleResponse getUserRole() {
        RoleResponse roleResponse = new RoleResponse();
        RoleMstrEntity roleMstrEntity = userRepository.getUserRole(AppUserUtils.getUserId());
        long roleId = roleMstrEntity.getRoleId();
        roleResponse.setRoleId(roleId);
        roleResponse.setRoleName(roleMstrEntity.getRoleName());
        List<RolePermissionEntity> rolePermissionEnity = rolePermissionRepository.getRolePermissions(roleId);
        List<String> permissions = new ArrayList<>();
        List<RolePermResp> permissionTos = new ArrayList<RolePermResp>();
        for (RolePermissionEntity rolePerm : rolePermissionEnity) {
            permissions.add(rolePerm.getPermission());
            RolePermResp rolePermTo = new RolePermResp();
            rolePermTo.setId(rolePerm.getId());
            rolePermTo.setPermission(rolePerm.getPermission());
            permissionTos.add(rolePermTo);
        }
        roleResponse.setPermissions(permissions);
        roleResponse.setRolePermissions(permissionTos);
        return roleResponse;
    }
}
