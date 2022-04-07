package com.rjtech.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.timesheet.model.TimeSheetEntity;



@Repository
public interface TimeSheetRepositoryCopy extends JpaRepository<TimeSheetEntity, Long> {

}
