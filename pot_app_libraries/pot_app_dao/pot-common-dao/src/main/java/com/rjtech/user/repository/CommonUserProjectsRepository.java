package com.rjtech.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.common.model.UserProjectsEntity;
import com.rjtech.projectlib.repository.EPSProjBaseRepository;

@Repository
public interface CommonUserProjectsRepository extends EPSProjBaseRepository<UserProjectsEntity, Long> {

    @Query("SELECT upr FROM UserProjectsEntity upr WHERE  upr.userId.userId=:userId and  upr.status=:status")
    List<UserProjectsEntity> findUserProjects(@Param("userId") Long userId, @Param("status") Integer status);

    @Query("SELECT upr FROM UserProjectsEntity upr WHERE  upr.userId.userId=:userId")
    List<UserProjectsEntity> findAllUserProjects(@Param("userId") Long userId);

    @Query("SELECT upr.projectMstrEntity.projectId FROM UserProjectsEntity upr WHERE  upr.userId.userId=:userId and  upr.status=:status")
    List<Long> findUserProjIds(@Param("userId") Long userId, @Param("status") Integer status);

    @Query("SELECT upr FROM UserProjectsEntity upr WHERE upr.status=:status and upr.userId.userId=:userId and "
            + " upr.projectMstrEntity.clientId.clientId=:clientId")
    List<UserProjectsEntity> findUserProjectsByClient(@Param("userId") Long userId, @Param("clientId") Long clientId,
            @Param("status") Integer status);

    @Query("SELECT DISTINCT upr.userId.userId FROM UserProjectsEntity upr WHERE upr.projectMstrEntity.projectId=:projectId and upr.status=:status")
    List<Long> findUsersByProjId(@Param("projectId") Long projectId, @Param("status") Integer status);
    
    @Query("SELECT COUNT(upr) FROM UserProjectsEntity upr WHERE  upr.projectMstrEntity.projectId =:projId")
    int getUsersCountForProject(@Param("projId") Long projId);

}
