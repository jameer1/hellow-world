package com.rjtech.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.notification.model.WorkDairyNotificationsEntity;

//import com.rjtech.notification.model.WorkDairyNotificationsEntityCopy;

@Repository
public interface WorkDairyNotificationRepositoryCopy extends JpaRepository<WorkDairyNotificationsEntity, Long> {

    @Query("SELECT T from WorkDairyNotificationsEntity T where T.notificationMsg=:notificationMsg AND T.notificationStatus=:notificationStatus "
            + "AND T.workDairyEntity.id=:workDairyId")
    WorkDairyNotificationsEntity findWorkDairyNotifications(@Param("notificationStatus") String notificationStatus,
            @Param("notificationMsg") String notificationMsg, @Param("workDairyId") long id);
    
    @Modifying
    @Query("UPDATE WorkDairyNotificationsEntity T SET T.notificationStatus='Addtional Time For Approved' where T.workDairyEntity.projId=:projId AND T.id=:notificationId")
    void updateWorkDairyNotificationsByProjId(@Param("projId") Long projId,
            @Param("notificationId") Long notificationId);

    @Query("SELECT T FROM WorkDairyNotificationsEntity T  where T.workDairyEntity.id=:workDairyId AND T.notificationMsg=:additionalRequest"
            + " AND T.notificationStatus=:notifyStatus")
    List<WorkDairyNotificationsEntity> findByWorkDairyId(@Param("additionalRequest") String additionalRequest,
            @Param("notifyStatus") String notifyStatus, @Param("workDairyId") Long workDairyId);
}
