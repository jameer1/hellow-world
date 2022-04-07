package com.rjtech.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rjtech.notification.model.PlantNotificationsEntity;
import com.rjtech.notification.model.PlantNotificationsEntityCopy;

//import com.rjtech.notification.model.PlantNotificationsEntityCopy;

public interface PlantNotificationsRepositoryCopy extends JpaRepository<PlantNotificationsEntityCopy, Long> {

}
