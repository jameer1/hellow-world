package com.rjtech.register.repository.plant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.notification.model.PlantNotificationsEntity;

//import com.rjtech.notification.model.PlantNotificationsEntityCopy;

@Repository("projSettingsPlantNotificationsRepository")
public interface PlantNotificationRepositoryCopy extends JpaRepository<PlantNotificationsEntity, Long> {

    @Modifying
    @Query("UPDATE PlantNotificationsEntity T SET T.type=:type, T.notificationStatus=:notifyStatus WHERE T.id  =:id")
    void addtionalTimePlantApproved(@Param("id") Long id, @Param("type") String type,
            @Param("notifyStatus") String notifyStatus);

}
