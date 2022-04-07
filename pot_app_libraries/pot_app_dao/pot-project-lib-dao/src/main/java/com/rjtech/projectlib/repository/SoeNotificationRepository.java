package com.rjtech.projectlib.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.projectlib.model.SoeNotificationsEntity;

@Repository
public interface SoeNotificationRepository extends NotificationsRepository<SoeNotificationsEntity, Long> {


}
