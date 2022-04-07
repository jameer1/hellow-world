package com.rjtech.role.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.admin.repository.AdminRepository;
import com.rjtech.role.model.RoleMstrEntity;

@Repository
public interface RoleRepository extends AdminRepository<RoleMstrEntity, Long> {

    @Query("SELECT RL FROM RoleMstrEntity RL WHERE RL.status=:status AND  (RL.clientId IS  NULL OR   RL.clientId.clientId=:clientId ) ORDER BY  RL.roleName")
    List<RoleMstrEntity> findRoles(@Param("clientId") Long clientId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE RoleMstrEntity RLP SET RLP.status=:status WHERE RLP.roleId in :roleIds")
    void deactivateRoles(@Param("roleIds") List<Long> roleIds, @Param("status") Integer status);

    @Query("SELECT RL FROM RoleMstrEntity RL WHERE RL.clientId IS  NULL OR   RL.clientId.clientId=:clientId  ORDER BY  RL.roleName")
    List<RoleMstrEntity> findAllRoles(@Param("clientId") Long clientId);

    @Query("SELECT RL FROM RoleMstrEntity RL WHERE RL.defaultRole = true")
    RoleMstrEntity findDefaultRole();
}