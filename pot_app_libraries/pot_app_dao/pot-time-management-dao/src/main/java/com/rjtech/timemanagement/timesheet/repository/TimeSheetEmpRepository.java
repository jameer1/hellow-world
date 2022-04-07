package com.rjtech.timemanagement.timesheet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.timesheet.model.TimeSheetEmpDtlEntity;

@Repository
public interface TimeSheetEmpRepository extends JpaRepository<TimeSheetEmpDtlEntity, Long> {

    @Query("SELECT DISTINCT T FROM TimeSheetEmpDtlEntity T LEFT JOIN FETCH T.timeSheetEmpWorkDtlEntities TD WHERE T.timeSheetEntity.id=:timeSheetId  AND T.status=:status")
    List<TimeSheetEmpDtlEntity> findTimeSheetDetails(@Param("timeSheetId") Long timeSheetId,
            @Param("status") Integer status);

    @Query("SELECT DISTINCT T FROM TimeSheetEmpDtlEntity T  WHERE T.timeSheetEntity.id=:timeSheetId AND T.status=:status")
    List<TimeSheetEmpDtlEntity> findTimeSheetTasksAndExpenses(@Param("timeSheetId") Long timeSheetId,
            @Param("status") Integer status);

}
