package com.rjtech.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.workdairy.model.WorkDairyEntity;

//import com.rjtech.notification.model.WorkDairyEntity;

@Repository
public interface WorkDairyRepository extends JpaRepository<WorkDairyEntity, Long> {

}
