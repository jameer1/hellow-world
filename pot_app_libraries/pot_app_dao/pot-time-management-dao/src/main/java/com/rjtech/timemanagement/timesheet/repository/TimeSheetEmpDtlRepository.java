package com.rjtech.timemanagement.timesheet.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
//import com.rjtech.timemanagement.register.emp.model.EmpRegisterDtlEntityCopy;
import com.rjtech.timemanagement.timesheet.model.TimeSheetEmpWorkDtlEntity;

@Repository
public interface TimeSheetEmpDtlRepository extends JpaRepository<TimeSheetEmpWorkDtlEntity, Long> {

    @Query("SELECT T FROM TimeSheetEmpWorkDtlEntity T WHERE T.isLatest=true AND T.timeSheetEmpDtlEntity.id=:empId AND T.status=1")
    List<TimeSheetEmpWorkDtlEntity> findLatestEmpDetails(@Param("empId") Long empId);

    @Query("SELECT DISTINCT tsh.empRegisterDtlEntity.id, SUM(tsd.day1), SUM(tsd.day2), SUM(tsd.day3), SUM(tsd.day4), "
            + "SUM(tsd.day5), SUM(tsd.day6), SUM(tsd.day7) FROM TimeSheetEmpWorkDtlEntity tsd JOIN tsd.timeSheetEmpDtlEntity tsh JOIN "
            + "tsh.timeSheetEntity tsm WHERE tsm.weekStartDate=:weekStartDate AND tsm.id != :timeSheetId AND "
            + "tsm.projMstrEntity.projectId = :projId AND tsh.status = 1 AND tsd.isLatest = 1 GROUP BY tsh.empRegisterDtlEntity.id")
    List<Object[]> timeSheetBookedHrsForOtherCrew(@Param("projId") Long projId, @Param("timeSheetId") Long timeSheetId,
            @Param("weekStartDate") Date weekStartDate);

    @Query("SELECT DISTINCT tsh.empRegisterDtlEntity.id, SUM(tsd.day1), SUM(tsd.day2), SUM(tsd.day3), SUM(tsd.day4), SUM(tsd.day5), "
            + "SUM(tsd.day6), SUM(tsd.day7) FROM TimeSheetEmpWorkDtlEntity tsd JOIN tsd.timeSheetEmpDtlEntity tsh JOIN "
            + "tsh.timeSheetEntity tsm WHERE tsm.weekStartDate=:weekStartDate AND tsm.projMstrEntity.projectId = :projId AND "
            + "tsm.projCrewMstrEntity.id = :crewId AND tsh.status = 1 AND tsd.isLatest = 1 GROUP BY tsh.empRegisterDtlEntity.id")
    List<Object[]> timeSheetBookedHrsForSameCrew(@Param("projId") Long projId, @Param("crewId") Long crewId,
            @Param("weekStartDate") Date weekStartDate);

    @Query("SELECT T FROM TimeSheetEmpWorkDtlEntity T "
            + "WHERE T.timeSheetEmpDtlEntity.empRegisterDtlEntity IN :empRegisterDtlEntity "
            + "AND :workDairyDate BETWEEN T.timeSheetEmpDtlEntity.timeSheetEntity.weekStartDate "
            + "AND T.timeSheetEmpDtlEntity.timeSheetEntity.weekEndDate")
    public List<TimeSheetEmpWorkDtlEntity> getEmpTimesheetHrsForTheDay(
            @Param("empRegisterDtlEntity") List<EmpRegisterDtlEntity> empRegisterDtlEntity,
            @Param("workDairyDate") Date workDairyDate);

    @Query("SELECT tsd.projCostItemEntity.id, SUM(tsd.day1), SUM(tsd.day2), SUM(tsd.day3), SUM(tsd.day4), SUM(tsd.day5), "
            + "SUM(tsd.day6), SUM(tsd.day7) FROM TimeSheetEmpWorkDtlEntity tsd "
            + "WHERE tsd.timeSheetEmpDtlEntity.timeSheetEntity.projMstrEntity.projectId = :projId GROUP BY tsd.projCostItemEntity.id")
    List<Object[]> getCostCodeActualQuantity(@Param("projId") Long projId);

    @Query("SELECT tsd FROM TimeSheetEmpWorkDtlEntity tsd JOIN tsd.timeSheetEmpDtlEntity tsh JOIN "
            + "tsh.timeSheetEntity tsm WHERE tsm.apprStatus = 'Approved' AND tsm.projMstrEntity.projectId IN :projIds AND "
            + "tsh.empRegisterDtlEntity.companyMstrEntity.id IN :companyIds AND tsd.isLatest = 1 AND  tsm.weekStartDate <= :toDate")
    List<TimeSheetEmpWorkDtlEntity> getManpowerActualHrsForReport(@Param("projIds") List<Long> projIds,
            @Param("companyIds") List<Long> companyIds, @Param("toDate") Date toDate);

    @Query("SELECT tsd FROM TimeSheetEmpWorkDtlEntity tsd JOIN tsd.timeSheetEmpDtlEntity tsh JOIN "
            + "tsh.timeSheetEntity tsm WHERE tsm.apprStatus = 'Approved' AND tsm.projMstrEntity.projectId IN :projIds AND "
            + "tsh.empRegisterDtlEntity.companyMstrEntity.id IN :companyIds AND tsd.isLatest = 1 AND "
            + "tsh.empRegisterDtlEntity.empClassMstrEntity.id IN :empClassIds AND tsm.weekStartDate <= :toDate")
    List<TimeSheetEmpWorkDtlEntity> getManpowerActualHrsForReport(@Param("projIds") List<Long> projIds,
            @Param("companyIds") List<Long> companyIds, @Param("toDate") Date toDate,
            @Param("empClassIds") List<Long> empClassIds);

    @Query("SELECT tsd FROM TimeSheetEmpWorkDtlEntity tsd JOIN tsd.timeSheetEmpDtlEntity tsh JOIN "
            + "tsh.timeSheetEntity tsm WHERE tsm.apprStatus = 'Approved' AND tsm.projMstrEntity.projectId IN :projIds AND "
            + "tsh.empRegisterDtlEntity.companyMstrEntity.id IN :companyIds AND tsd.isLatest = 1 AND  "
            + "((tsm.weekStartDate BETWEEN :fromDate AND :toDate OR tsm.weekEndDate BETWEEN :fromDate AND :toDate)"
            + "OR (:fromDate BETWEEN tsm.weekStartDate AND tsm.weekEndDate OR :toDate BETWEEN tsm.weekStartDate AND tsm.weekEndDate))")
    List<TimeSheetEmpWorkDtlEntity> getManpowerActualHrsDateWiseReport(@Param("projIds") List<Long> projIds,
            @Param("companyIds") List<Long> companyIds, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT tsd FROM TimeSheetEmpWorkDtlEntity tsd JOIN tsd.timeSheetEmpDtlEntity tsh JOIN "
            + "tsh.timeSheetEntity tsm WHERE tsm.apprStatus = 'Approved' AND tsm.projMstrEntity.projectId IN :projIds AND "
            + "tsh.empRegisterDtlEntity.companyMstrEntity.id IN :companyIds AND tsd.isLatest = 1 AND "
            + "tsh.empRegisterDtlEntity.empClassMstrEntity.id IN :empClassIds AND "
            + "((tsm.weekStartDate BETWEEN :fromDate AND :toDate OR tsm.weekEndDate BETWEEN :fromDate AND :toDate)"
            + "OR (:fromDate BETWEEN tsm.weekStartDate AND tsm.weekEndDate OR :toDate BETWEEN tsm.weekStartDate AND tsm.weekEndDate))")
    List<TimeSheetEmpWorkDtlEntity> getManpowerActualHrsDateWiseReport(@Param("projIds") List<Long> projIds,
            @Param("companyIds") List<Long> companyIds, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate,
            @Param("empClassIds") List<Long> empClassIds);

    @Query("SELECT tsd FROM TimeSheetEmpWorkDtlEntity tsd JOIN tsd.timeSheetEmpDtlEntity tsh JOIN "
            + "tsh.timeSheetEntity tsm WHERE tsm.apprStatus = 'Approved' AND tsm.projMstrEntity.projectId IN :projIds "
            + "AND tsd.isLatest = 1 AND :workDate BETWEEN tsm.weekStartDate AND tsm.weekEndDate")
    List<TimeSheetEmpWorkDtlEntity> getManpowerActualHrsByDate(@Param("projIds") List<Long> projIds,
            @Param("workDate") Date workDate);

    @Query("SELECT tsd FROM TimeSheetEmpWorkDtlEntity tsd JOIN tsd.timeSheetEmpDtlEntity tsh JOIN "
            + "tsh.timeSheetEntity tsm WHERE tsm.apprStatus = 'Approved' AND tsm.projMstrEntity.projectId IN :projIds AND "
            + "tsh.empRegisterDtlEntity.companyMstrEntity.id IN :companyIds AND tsd.projCostItemEntity.id IN :costIds AND "
            + "tsm.projCrewMstrEntity.id IN :crewIds AND tsd.isLatest = 1 AND  "
            + "((tsm.weekStartDate BETWEEN :fromDate AND :toDate OR tsm.weekEndDate BETWEEN :fromDate AND :toDate) "
            + "OR (:fromDate BETWEEN tsm.weekStartDate AND tsm.weekEndDate OR :toDate BETWEEN tsm.weekStartDate AND tsm.weekEndDate))")
    List<TimeSheetEmpWorkDtlEntity> getManpowerActualHrsDateWiseReport(@Param("projIds") List<Long> projIds,
            @Param("companyIds") List<Long> companyIds, @Param("costIds") List<Long> costIds,
            @Param("crewIds") List<Long> crewIds, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT tsd FROM TimeSheetEmpWorkDtlEntity tsd JOIN tsd.timeSheetEmpDtlEntity tsh JOIN "
            + "tsh.timeSheetEntity tsm WHERE tsm.apprStatus = 'Approved' AND tsm.projMstrEntity.projectId IN :projIds AND "
            + "tsh.empRegisterDtlEntity.companyMstrEntity.id IN :companyIds AND tsd.projCostItemEntity.id IN :costIds AND "
            + "tsm.projCrewMstrEntity.id IN :crewIds AND tsd.isLatest = 1 AND "
            + "tsh.empRegisterDtlEntity.empClassMstrEntity.id IN :empClassIds AND "
            + "((tsm.weekStartDate BETWEEN :fromDate AND :toDate OR tsm.weekEndDate BETWEEN :fromDate AND :toDate) "
            + "OR (:fromDate BETWEEN tsm.weekStartDate AND tsm.weekEndDate OR :toDate BETWEEN tsm.weekStartDate AND tsm.weekEndDate))")
    List<TimeSheetEmpWorkDtlEntity> getManpowerActualHrsDateWiseReport(@Param("projIds") List<Long> projIds,
            @Param("companyIds") List<Long> companyIds, @Param("costIds") List<Long> costIds,
            @Param("crewIds") List<Long> crewIds, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate,
            @Param("empClassIds") List<Long> empClassIds);

    @Query("SELECT tsd FROM TimeSheetEmpWorkDtlEntity tsd JOIN tsd.timeSheetEmpDtlEntity tsh JOIN  tsh.timeSheetEntity tsm "
            + "WHERE tsm.apprStatus = 'Approved' AND tsm.projMstrEntity.projectId IN :projIds AND tsd.isLatest = 1 "
            + "AND ((tsm.weekStartDate BETWEEN :fromDate AND :toDate OR tsm.weekEndDate BETWEEN :fromDate AND :toDate) "
            + "OR (:fromDate BETWEEN tsm.weekStartDate AND tsm.weekEndDate OR :toDate BETWEEN tsm.weekStartDate AND tsm.weekEndDate))")
    List<TimeSheetEmpWorkDtlEntity> getManpowerActualHrsDateWise(@Param("projIds") List<Long> projIds,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

}
