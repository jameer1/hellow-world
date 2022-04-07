package com.rjtech.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.notification.model.WorkDairyAdditionalTimeEntity;

//import com.rjtech.timemanagement.proj.settings.model.WorkDairyAdditionalTimeEntityCopy;

@Repository
public interface WorkDairyAdditionalTimeRepositoryCopy extends JpaRepository<WorkDairyAdditionalTimeEntity, Long> {

    @Query("SELECT T FROM WorkDairyAdditionalTimeEntity T WHERE T.crewId.id=:crewId "
            + "AND T.type=:type")
    List<WorkDairyAdditionalTimeEntity> getWorkDairyDetails(@Param("crewId") Long crewId,
            @Param("type") String type);
    
    @Query("SELECT T FROM WorkDairyAdditionalTimeEntity T  WHERE (T.crewId.projId.projectId=:projId) AND (T.status=:status)")
    List<WorkDairyAdditionalTimeEntity> findWorkDairyById(@Param("projId") Long projId,
            @Param("status") String status);

}
