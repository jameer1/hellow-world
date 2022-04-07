package com.rjtech.register.repository.material;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.notification.model.MaterialNotificationsEntity;

//import com.rjtech.notification.model.MaterialNotificationsEntityCopy;

@Repository("projSettingsMaterialNotificationsRepository")
public interface MaterialNotificationsRepositoryCopy extends JpaRepository<MaterialNotificationsEntity, Long> {

    @Modifying
    @Query("UPDATE  MaterialNotificationsEntity T SET T.type=:type,T.notificationStatus=:notifyStatus WHERE T.id=:id")
    void addtionalTimeMaterialApproved(@Param("id") Long id, @Param("type") String type,
            @Param("notifyStatus") String notifyStatus);
}
