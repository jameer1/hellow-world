package com.rjtech.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.admin.repository.AdminRepository;
import com.rjtech.common.model.UserProjectsEntity;

@Repository
public interface UserProjectsRepository extends AdminRepository<UserProjectsEntity, Long> {

    @Query("SELECT upr FROM UserProjectsEntity upr WHERE  upr.status=:status and upr.userId=:userId")
    List<UserProjectsEntity> findUserProjects(@Param("userId") Long userId, @Param("status") Integer status);
    
    @Query("SELECT upr FROM UserProjectsEntity upr WHERE  upr.projectMstrEntity.projectId=:projId")
    List<UserProjectsEntity> findProjectUsers(@Param("projId") Long projId);
    
}
