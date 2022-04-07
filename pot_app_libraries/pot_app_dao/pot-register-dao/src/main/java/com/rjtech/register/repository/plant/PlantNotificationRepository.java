package com.rjtech.register.repository.plant;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.register.plant.model.PlantNotificationsEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

@Repository("registerPlantNotificationsRepository")
public interface PlantNotificationRepository extends RegisterBaseRepository<PlantNotificationsEntity, Long> {

    @Query("SELECT T FROM PlantNotificationsEntity T WHERE (:id is null or T.id=:id)  AND (:projId IS NULL OR T.projMstrEntity.projectId=:projId) AND  T.status=:status")
    List<PlantNotificationsEntity> findPlantNotification(@Param("id") Long id, @Param("projId") Long projId,
            @Param("status") Integer status);

}
