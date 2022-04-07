package com.rjtech.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.notification.model.WorkDairyAdditionalTimeEntity;

@Repository
public interface WorkDairyAdditionalTimeRepository extends JpaRepository<WorkDairyAdditionalTimeEntity, Long> {

    @Query("SELECT T FROM WorkDairyAdditionalTimeEntity T  WHERE T.status=:status AND T.crewId.projId.projectId=:projId")
    List<WorkDairyAdditionalTimeEntity> findWorkDairyByStatus(@Param("projId") Long projId,
            @Param("status") String status);

}
