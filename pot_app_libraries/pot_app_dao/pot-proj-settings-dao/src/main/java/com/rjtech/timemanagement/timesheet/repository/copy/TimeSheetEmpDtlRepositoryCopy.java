
package com.rjtech.timemanagement.timesheet.repository.copy;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.timesheet.model.TimeSheetEmpWorkDtlEntity;

//import com.rjtech.timesheet.TimeSheetEmpWorkDtlEntityCopy;

@Repository
public interface TimeSheetEmpDtlRepositoryCopy extends JpaRepository<TimeSheetEmpWorkDtlEntity, Long> {

    @Query("SELECT tsd.projCostItemEntity.id, MIN(tsd.timeSheetEmpDtlEntity.timeSheetEntity.weekStartDate), "
            + "MAX(tsd.timeSheetEmpDtlEntity.timeSheetEntity.weekEndDate), SUM(tsd.day1), SUM(tsd.day2), SUM(tsd.day3), SUM(tsd.day4), "
            + "SUM(tsd.day5), SUM(tsd.day6), SUM(tsd.day7) FROM TimeSheetEmpWorkDtlEntity tsd "
            + "WHERE tsd.timeSheetEmpDtlEntity.timeSheetEntity.projMstrEntity.projectId = :projId AND "
            + "lower(tsd.timeSheetEmpDtlEntity.timeSheetEntity.apprStatus)='approved' GROUP BY tsd.projCostItemEntity.id")
    List<Object[]> getCostCodeActualQuantity(@Param("projId") Long projId);

    @Query("SELECT tsd.timeSheetEmpDtlEntity.empRegisterDtlEntity.empClassMstrEntity.id, "
            + "tsd.timeSheetEmpDtlEntity.timeSheetEntity.weekStartDate, tsd.timeSheetEmpDtlEntity.timeSheetEntity.weekEndDate, "
            + "tsd.day1, tsd.day2, tsd.day3, tsd.day4, tsd.day5,tsd.day6,tsd.day7 FROM TimeSheetEmpWorkDtlEntity tsd WHERE "
            + "lower(tsd.apprStatus)='approved' AND tsd.timeSheetEmpDtlEntity.empRegisterDtlEntity.projMstrEntity.projectId =:projId")
    List<Object[]> getTimesheetEmpActualHrs(@Param("projId") Long projId);

    @Query("SELECT TSWD.timeSheetEmpDtlEntity.empRegisterDtlEntity.id, "
            + " TSWD.projCostItemEntity.id, TSWD.empWageMstrEntity.wageFactor,"
            + " TSWD.timeSheetEmpDtlEntity.timeSheetEntity.weekStartDate, "
            + " COALESCE(TSWD.day1, 0), COALESCE(TSWD.day2, 0), COALESCE(TSWD.day3, 0), COALESCE(TSWD.day4, 0), "
            + " COALESCE(TSWD.day5, 0), COALESCE(TSWD.day6, 0), COALESCE(TSWD.day7, 0) "
            + " FROM TimeSheetEmpWorkDtlEntity TSWD WHERE "
            + " TSWD.timeSheetEmpDtlEntity.empRegisterDtlEntity.id in (:empIds) and lower(TSWD.apprStatus)='approved' ")
    List<Object[]> getTimesheetEmpActualHrs(@Param("empIds") Set<Long> empIds);

    @Query("SELECT TSWD.timeSheetEmpDtlEntity.empRegisterDtlEntity, TSWD.projCostItemEntity, TSWD.empWageMstrEntity.wageFactor, "
            + " TSWD.timeSheetEmpDtlEntity.timeSheetEntity.weekStartDate, COALESCE(TSWD.day1, 0), COALESCE(TSWD.day2, 0), "
            + " COALESCE(TSWD.day3, 0), COALESCE(TSWD.day4, 0), COALESCE(TSWD.day5, 0), COALESCE(TSWD.day6, 0), COALESCE(TSWD.day7, 0), "
            + " TSWD.timeSheetEmpDtlEntity.timeSheetEntity.projMstrEntity, PEC.projEmpCategory "
            + " FROM TimeSheetEmpWorkDtlEntity TSWD "
            + " LEFT JOIN ProjEmpClassMstrEntity PEC ON PEC.empClassMstrEntity = TSWD.timeSheetEmpDtlEntity.empRegisterDtlEntity.empClassMstrEntity "
            + " AND PEC.projectId = TSWD.timeSheetEmpDtlEntity.timeSheetEntity.projMstrEntity "
            + " WHERE TSWD.timeSheetEmpDtlEntity.timeSheetEntity.projMstrEntity.projectId in (:projIds) AND (lower(TSWD.apprStatus)='approved' "
            + " AND lower(PEC.projEmpCategory) in (:empCats) AND "
            + " TSWD.timeSheetEmpDtlEntity.empRegisterDtlEntity.companyMstrEntity.id in (:cmpIds) AND TSWD.projCostItemEntity.id in (:costIds) AND "
            + " (TSWD.timeSheetEmpDtlEntity.timeSheetEntity.weekStartDate BETWEEN :fromDate AND :toDate OR "
            + " TSWD.timeSheetEmpDtlEntity.timeSheetEntity.weekEndDate BETWEEN :fromDate AND :toDate OR "
            + " :fromDate BETWEEN TSWD.timeSheetEmpDtlEntity.timeSheetEntity.weekStartDate AND TSWD.timeSheetEmpDtlEntity.timeSheetEntity.weekEndDate OR "
            + " :toDate BETWEEN TSWD.timeSheetEmpDtlEntity.timeSheetEntity.weekStartDate AND TSWD.timeSheetEmpDtlEntity.timeSheetEntity.weekEndDate)) "
            + " order by TSWD.timeSheetEmpDtlEntity.timeSheetEntity.weekStartDate ")
    List<Object[]> getTimesheetEmpActualHrs(@Param("projIds") List<Long> projIds,
            @Param("empCats") List<String> empCats, @Param("cmpIds") List<Long> cmpIds,
            @Param("costIds") List<Long> costIds, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    @Query("SELECT tsd.timeSheetEmpDtlEntity.empRegisterDtlEntity.empClassMstrEntity.id, "
            + "tsd.timeSheetEmpDtlEntity.timeSheetEntity.weekStartDate, tsd.timeSheetEmpDtlEntity.timeSheetEntity.weekEndDate, "
            + "tsd.day1, tsd.day2, tsd.day3, tsd.day4, tsd.day5,tsd.day6,tsd.day7 FROM TimeSheetEmpWorkDtlEntity tsd WHERE "
            + "lower(tsd.apprStatus)='approved' AND tsd.timeSheetEmpDtlEntity.empRegisterDtlEntity.projMstrEntity.projectId=:projId "
            + "AND tsd.timeSheetEmpDtlEntity.empRegisterDtlEntity.empClassMstrEntity.id in (:resIds) "
            + "ORDER BY tsd.timeSheetEmpDtlEntity.timeSheetEntity.weekStartDate")
    List<Object[]> getTimesheetEmpActualHrs(@Param("projId") Long projId, @Param("resIds") List<Long> resIds);
    
    @Query("SELECT tsd FROM TimeSheetEmpWorkDtlEntity tsd JOIN tsd.timeSheetEmpDtlEntity tsh JOIN  tsh.timeSheetEntity tsm "
            + "WHERE tsm.apprStatus = 'Approved' AND tsm.projMstrEntity.projectId IN :projIds AND tsd.isLatest = 1 "
            + "AND ((tsm.weekStartDate BETWEEN :fromDate AND :toDate OR tsm.weekEndDate BETWEEN :fromDate AND :toDate) "
            + "OR (:fromDate BETWEEN tsm.weekStartDate AND tsm.weekEndDate OR :toDate BETWEEN tsm.weekStartDate AND tsm.weekEndDate))")
    List<TimeSheetEmpWorkDtlEntity> getManpowerActualHrsDateWise(@Param("projIds") List<Long> projIds,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

}
