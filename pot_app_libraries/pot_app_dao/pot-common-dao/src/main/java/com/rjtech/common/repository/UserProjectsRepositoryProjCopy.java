package com.rjtech.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.rjtech.common.model.UserProjectsEntity;

@Repository
public interface UserProjectsRepositoryProjCopy extends JpaRepository<UserProjectsEntity, Long> {

    @Query("SELECT DISTINCT upr.projectMstrEntity.projectId FROM UserProjectsEntity upr WHERE  upr.projectMstrEntity.projectId IS NOT NULL")
    List<Long> getAllUserProjects();
}
