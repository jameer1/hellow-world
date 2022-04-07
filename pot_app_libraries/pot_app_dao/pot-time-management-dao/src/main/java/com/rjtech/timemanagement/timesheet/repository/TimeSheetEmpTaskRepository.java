package com.rjtech.timemanagement.timesheet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.timesheet.model.TimeSheetEmpTaskEntity;

@Repository
public interface TimeSheetEmpTaskRepository extends JpaRepository<TimeSheetEmpTaskEntity, Long> {

    @Query("SELECT T FROM TimeSheetEmpTaskEntity T  WHERE T.timeSheetEmpDtlEntity.id=:timeSheetEmpId AND (:apprStatus IS NULL AND  T.apprStatus IS NULL) OR (:apprStatus IS NOT NULL AND T.apprStatus=:apprStatus) AND T.status=:status")
    List<TimeSheetEmpTaskEntity> findTimeSheetEmpTasks(@Param("timeSheetEmpId") Long timeSheetEmpId,
            @Param("apprStatus") String apprStatus, @Param("status") Integer status);

}
