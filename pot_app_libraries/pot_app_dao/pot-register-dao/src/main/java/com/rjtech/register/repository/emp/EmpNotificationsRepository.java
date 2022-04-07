package com.rjtech.register.repository.emp;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.emp.model.EmpNotificationsEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface EmpNotificationsRepository extends RegisterBaseRepository<EmpNotificationsEntity, Long> {

    @Query("SELECT T FROM EmpNotificationsEntity T WHERE (:id is null or T.id=:id) "
            + "AND (:projId IS NULL OR T.projMstrEntity.projectId=:projId)" + "AND  T.status=:status")
    List<EmpNotificationsEntity> findEmpNotifications(@Param("id") Long id, @Param("projId") Long projId,
            @Param("status") Integer status);

}
