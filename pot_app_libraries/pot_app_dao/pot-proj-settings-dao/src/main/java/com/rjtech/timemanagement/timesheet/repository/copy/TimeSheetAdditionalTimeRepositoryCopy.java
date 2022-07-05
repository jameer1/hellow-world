package com.rjtech.timemanagement.timesheet.repository.copy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.timesheet.model.TimeSheetAdditionalTimeEntity;

//import com.rjtech.timesheet.TimeSheetAdditionalTimeEntityCopy;

@Repository
public interface TimeSheetAdditionalTimeRepositoryCopy extends JpaRepository<TimeSheetAdditionalTimeEntity, Long> {

    @Query("SELECT T FROM com.rjtech.timemanagement.timesheet.model.TimeSheetAdditionalTimeEntity T WHERE (T.projCrewMstrEntity.projId.projectId=:projId) AND (T.notificationStatus=:notificationStatus) "
            + "AND (T.notificationMsg=:notificationMsg) AND (T.status=:status)")
    List<TimeSheetAdditionalTimeEntity> findTimeSheetNotificationsByStatus(@Param("projId") Long projId,
            @Param("notificationStatus") String notificationStatus, @Param("notificationMsg") String notificationMsg,
            @Param("status") Integer status);
}
