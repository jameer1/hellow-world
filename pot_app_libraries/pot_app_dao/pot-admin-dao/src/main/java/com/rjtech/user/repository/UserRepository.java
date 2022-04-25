package com.rjtech.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.admin.repository.AdminRepository;
import com.rjtech.role.model.RoleMstrEntity;
import com.rjtech.user.model.UserEntity;

@Repository
public interface UserRepository extends AdminRepository<UserEntity, Long> {

    @Query("SELECT USR FROM UserEntity USR join USR.clientRegMstrEntity CRM where (( :clientId is null AND (USR.userType=1 or USR.client = true)) OR "
            + " (CRM.clientId=:clientId AND CRM.status=1 AND USR.userType=2)) "
            + " AND (:userName is NULL or USR.userName like :userName) AND (:empCode is NULL or USR.empCode like :empCode) AND USR.status=:status")
    public List<UserEntity> findUsers(@Param("clientId") Long clientId, @Param("userName") String userName,
            @Param("empCode") String empCode, @Param("status") Integer status);

    @Query("SELECT USR FROM UserEntity USR  left join USR.clientRegMstrEntity CRM where (:clientId is null AND (CRM.clientId IS NULL AND  USR.userType=1))"
            + " AND (:userName is NULL or USR.userName like :userName) AND (:empCode is NULL or USR.empCode like :empCode) AND USR.status=:status")
    public List<UserEntity> findVendorUsers(@Param("clientId") Long clientId, @Param("userName") String userName,
            @Param("empCode") String empCode, @Param("status") Integer status);

    @Query("SELECT USR FROM UserEntity USR where USR.userType=:userType AND USR.status=:status and USR.client = true ORDER BY USR.userId DESC")
    public List<UserEntity> findClientParentUser(@Param("userType") Integer userType, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE UserEntity USR SET USR.status=:status WHERE USR.userId in :deleteUserIds")
    public void deactivateUsers(@Param("deleteUserIds") List<Long> userIds, @Param("status") Integer status);

    @Query("SELECT USR FROM UserEntity USR  JOIN USR.userRoleMstrs URL where (:clientId IS NULL  or  (USR.clientRegMstrEntity.clientId=:clientId AND USR.clientRegMstrEntity.status=:status) ) AND ( URL.userRoleId=:roleId)  AND USR.status=:status")
    public List<UserEntity> findUsersByRoleId(@Param("clientId") Long clientId, @Param("roleId") Long roleId,
            @Param("status") Integer status);

    @Query("SELECT  DISTINCT URM.roleMstr.roleId  FROM UserRoleMapEntity URM JOIN  URM.userEntity USR "
            + " WHERE USR.userId=:userId AND (:clientId IS NULL  or USR.clientRegMstrEntity.clientId=:clientId) ")
    List<Long> findRolesByUser(@Param("userId") Long userId, @Param("clientId") Long clientId);

    @Query("SELECT DISTINCT URM.roleMstr from UserRoleMapEntity URM " + "where URM.userEntity.userId = :userId")
    RoleMstrEntity getUserRole(@Param("userId") Long userId);

    @Query("SELECT USR FROM UserEntity USR join USR.clientRegMstrEntity CRM where (( :clientId is null AND USR.userType=1) OR "
            + " (CRM.clientId=:clientId AND CRM.status=1 AND USR.userType=2)) "
            + " AND (:userName is NULL or USR.userName like :userName) AND (:empCode is NULL or USR.empCode like :empCode) ")
    public List<UserEntity> findAllUsers(@Param("clientId") Long clientId, @Param("userName") String userName,
            @Param("empCode") String empCode);

    @Modifying
    @Query("UPDATE UserEntity USR SET USR.status=:status WHERE USR.userId in :activeUserIds")
    public void activateUsers(@Param("activeUserIds") List<Long> userIds, @Param("status") Integer status);

}
