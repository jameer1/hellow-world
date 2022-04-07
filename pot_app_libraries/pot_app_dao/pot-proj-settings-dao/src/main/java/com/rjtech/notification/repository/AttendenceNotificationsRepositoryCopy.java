package com.rjtech.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rjtech.notification.model.AttendenceNotificationsEntity;

//import com.rjtech.notification.model.AttendenceNotificationsEntityCopy;

public interface AttendenceNotificationsRepositoryCopy extends JpaRepository<AttendenceNotificationsEntity, Long> {

}
