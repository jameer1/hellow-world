package com.rjtech.role.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.rjtech.role.model.RolePermissionEntity;

public interface RolePermissionRepository extends CrudRepository<RolePermissionEntity, Long> {

    @Query("SELECT RP from RolePermissionEntity RP where RP.roleMstrEntity.roleId = :roleId and RP.permission = :permissionKey and RP.clientId.clientId = :clientId")
    RolePermissionEntity getPermission(@Param("roleId") long roleId, @Param("clientId") long clientId,
            @Param("permissionKey") String permissionKey);

    @Query("SELECT RP from RolePermissionEntity RP where RP.roleMstrEntity.roleId = :roleId and RP.clientId.clientId = :clientId")
    List<RolePermissionEntity> getRolePermissions(@Param("roleId") long roleId, @Param("clientId") Long clientId);

    /**
     * Use this method only for Raju created roles.
     * 
     * @param roleId
     * @return {@link List} of {@link RolePermissionEntity}
     */
    @Query("SELECT RP from RolePermissionEntity RP where RP.roleMstrEntity.roleId = :roleId")
    List<RolePermissionEntity> getRolePermissions(@Param("roleId") long roleId);

    @Query(" SELECT DISTINCT USR.userId , USR.userName,USR.email FROM RolePermissionEntity RLP "
            + " JOIN RLP.roleMstrEntity RLM JOIN  RLM.userRoleMapEntities URM JOIN URM.userEntity USR "
            + " JOIN USR.clientRegMstrEntity CRM WHERE RLP.permission=:permission AND RLP.clientId.clientId=:clientId "
            + " AND CRM.clientId=:clientId and USR.status=1")
    public List<Object[]> getUsersByModulePermission(@Param("permission") String permission,
            @Param("clientId") Long clientId);
}
