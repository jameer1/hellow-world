package com.rjtech.centrallib.repository;

import java.util.Date;
import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.rjtech.common.model.UserProjectsEntity;



@Repository
public interface UserProjectsRepositoryEmpCopy extends JpaRepository<UserProjectsEntity, Long> {

    @Query("SELECT DISTINCT upr.userId.userId FROM UserProjectsEntity upr WHERE upr.userId.userId IS NOT NULL")
    List<Long> getUsersOfProjects();
}
