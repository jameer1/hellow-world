package com.rjtech.notification.repository.copy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rjtech.notification.model.ReqApprNotificationEntity;
//import com.rjtech.notification.model.ReqApprNotificationEntityCopy;

@Repository
public interface ReqApprNotificationRepositoryCopy extends JpaRepository<ReqApprNotificationEntity, Long> {

}
