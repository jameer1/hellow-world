package com.rjtech.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.common.model.UserMstrEntity;

@Repository
public interface LoginRepositoryCopy extends JpaRepository<UserMstrEntity, Long> {

    @Query("SELECT USR FROM com.rjtech.common.model.UserMstrEntity USR where USR.clientRegEntity.code=:clientCode AND USR.clientRegEntity.status=:status "
            + " AND (USR.empCode=:userName or USR.userName =:userName or USR.email =:userName) AND USR.status=:status and USR.userType=:userType")
    public UserMstrEntity findExternalUsers(@Param("clientCode") String clientCode, @Param("userName") String userName,
            @Param("status") Integer status, @Param("userType") Integer userType);

    @Query("SELECT USR FROM com.rjtech.common.model.UserMstrEntity USR where  (USR.empCode=:userName or USR.userName =:userName or USR.email =:userName) "
            + "AND USR.status=:status and USR.userType=:userType")
    public UserMstrEntity findInternalUsers(@Param("userName") String userName, @Param("status") Integer status,
            @Param("userType") Integer userType);

    @Query("SELECT USR FROM com.rjtech.common.model.UserMstrEntity USR where  USR.userId=:userId AND USR.status=:status and USR.userType=:userType")
    public UserMstrEntity findUserById(@Param("userId") Long userId, @Param("status") Integer status,
            @Param("userType") Integer userType);

}
