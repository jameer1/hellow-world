package com.rjtech.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.common.model.UserMstrEntity;

@Repository
public interface LoginRepository extends JpaRepository<UserMstrEntity, Long> {
    @Query("SELECT USR FROM UserMstrEntity USR where USR.clientRegEntity.code=:clientCode AND USR.clientRegEntity.status=:status "
            + " AND (USR.empCode=:userName or USR.userName =:userName or USR.email =:userName) AND USR.status=:status and USR.userType=:userType")
    public UserMstrEntity findExternalUsers(@Param("clientCode") String clientCode, @Param("userName") String userName,
            @Param("status") Integer status, @Param("userType") Integer userType);

    @Query("SELECT USR FROM UserMstrEntity USR where  (USR.empCode=:userName or USR.userName =:userName or USR.email =:userName) "
            + "AND USR.status=:status and USR.userType=:userType")
    public UserMstrEntity findInternalUsers(@Param("userName") String userName, @Param("status") Integer status,
            @Param("userType") Integer userType);

    @Query("SELECT USR FROM UserMstrEntity USR where  USR.userId=:userId AND USR.status=:status and USR.userType=:userType")
    public UserMstrEntity findUserById(@Param("userId") Long userId, @Param("status") Integer status,
            @Param("userType") Integer userType);
    
    @Query("SELECT USR FROM UserMstrEntity USR where USR.userId in (:users) AND USR.status=:status")
    public List<UserMstrEntity> findUserByIds(@Param("users") List<Long> users, @Param("status") Integer status);
   
}
