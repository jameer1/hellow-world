package com.rjtech.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.role.model.UserRoleMapEntityCopy;

public interface UserRoleMapRepositoryCpy extends JpaRepository<UserRoleMapEntityCopy, Long> {

    @Query("select URM from UserRoleMapEntityCopy URM JOIN URM.roleMstr RL JOIN RL.permissions RP "
            + " WHERE RP.permission = :permission"
            + " AND  URM.userEntity.client = 0 and URM.userEntity.clientRegEntity.clientId = :clientId")
    List<UserRoleMapEntityCopy> findUserByPermission(@Param("permission") String permission,
            @Param("clientId") Long clientId);

    @Query("select URM from UserRoleMapEntityCopy URM JOIN URM.roleMstr RL JOIN RL.permissions RP "
            + " WHERE RP.permission = :permission AND "
            + " URM.userEntity.userId in (:users) and URM.userEntity.client = 0 and URM.userEntity.clientRegEntity.clientId = :clientId")
    List<UserRoleMapEntityCopy> findProjUserByPermission(@Param("users") List<Long> users,
            @Param("permission") String permission, @Param("clientId") Long clientId);
    
    @Query("select URM from UserRoleMapEntityCopy URM WHERE URM.userEntity.userId=:userId")
    UserRoleMapEntityCopy findUserRolesByUserId( @Param("userId") Long userId );
}
