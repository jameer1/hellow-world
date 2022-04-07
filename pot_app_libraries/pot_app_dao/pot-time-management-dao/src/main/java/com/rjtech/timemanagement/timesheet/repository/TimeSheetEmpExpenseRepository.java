package com.rjtech.timemanagement.timesheet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.timesheet.model.TimeSheetEmpExpenseEntity;

@Repository
public interface TimeSheetEmpExpenseRepository extends JpaRepository<TimeSheetEmpExpenseEntity, Long> {

    @Query("SELECT T FROM TimeSheetEmpExpenseEntity T  WHERE T.timeSheetEmpDtlEntity.id=:timeSheetEmpId  AND (:apprStatus is NULL OR T.apprStatus=:apprStatus) AND T.status=:status")
    List<TimeSheetEmpExpenseEntity> findTimeSheetEmpExpenses(@Param("timeSheetEmpId") Long timeSheetEmpId,
            @Param("apprStatus") String apprStatus, @Param("status") Integer status);

}
