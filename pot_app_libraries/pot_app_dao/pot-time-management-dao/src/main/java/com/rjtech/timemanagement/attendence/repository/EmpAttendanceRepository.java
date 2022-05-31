package com.rjtech.timemanagement.attendence.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
//import com.rjtech.projectlib.model.ProjCrewMstrEntityCopy;
import com.rjtech.timemanagement.attendance.model.EmpAttendanceEntity;
//import com.rjtech.timemanagement.register.emp.model.EmpProjRigisterEntityCopy;
//import com.rjtech.timemanagement.register.emp.model.EmpRegisterDtlEntityCopy;

@Repository
public interface EmpAttendanceRepository extends JpaRepository<EmpAttendanceEntity, Long> {

    @Query("SELECT DISTINCT T FROM EmpAttendanceEntity T " + " LEFT JOIN FETCH T.empAttendanceDtlEntities TD "
            + " WHERE T.empAttendanceMstrEntity.id=:attendanceId  AND T.empAttendanceMstrEntity.projId.projectId = :projId AND T.status=:status")
    List<EmpAttendanceEntity> findAttendanceRecords(@Param("attendanceId") Long attendanceId,
            @Param("projId") Long projId, @Param("status") Integer status);

    @Query("SELECT DISTINCT T FROM EmpAttendanceEntity T "
            + " WHERE T.empAttendanceMstrEntity.projId.projectId = :projId AND "
            + " lower(TO_CHAR(T.empAttendanceMstrEntity.attendenceMonth,'MON-YYYY')) = lower(:attendenceMonth) AND "
            + " T.empAttendanceMstrEntity.crewId.id != :currenCrewId AND T.status=:status")
    List<EmpAttendanceEntity> findOtherCrewAttendanceRecords(@Param("projId") Long projId,
            @Param("currenCrewId") Long currenCrewId, @Param("status") Integer status,
            @Param("attendenceMonth") String attendenceMonth);

    @Query("SELECT DISTINCT PEAT.empId FROM EmpAttendanceEntity PEAT "
            + " WHERE PEAT.empAttendanceMstrEntity.projId.projectId =:projId AND PEAT.empAttendanceMstrEntity.crewId.id=:crewId "
            + " AND TO_CHAR(PEAT.empAttendanceMstrEntity.attendenceMonth,'MON-YYYY') = TO_CHAR(:startDate,'MON-YYYY')")
    List<EmpRegisterDtlEntity> findAttendanceByProjIdCrewId(@Param("projId") long projId,
            @Param("crewId") long crewId, @Param("startDate") Date startDate);

    @Query("SELECT DISTINCT PER FROM EmpAttendanceEntity PEAT JOIN EmpProjRigisterEntity PER ON PER.empRegisterDtlEntity = PEAT.empId.id "
            + " WHERE PEAT.empAttendanceMstrEntity.id = :attId AND PEAT.empAttendanceMstrEntity.projId.projectId = :projId "
            + " AND PER.projMstrEntity.projectId = :projId AND PER.mobilaizationDate IS NOT NULL")
    List<EmpProjRigisterEntity> getEmpProjMobilizationDates(@Param("projId") long projId,
            @Param("attId") long attId);

   /* @Query("SELECT DISTINCT emp.id, emp.code, emp.firstName, emp.lastName, emp.gender, emp.empClassMstrEntity.name, "
            + "emp.companyMstrEntity.name FROM EmpAttendanceEntity pea JOIN pea.empId emp JOIN emp.empProjRigisterEntities per "
            + "WHERE per.projMstrEntity.projectId = :projId AND per.assignStatus = 'Y' and per.status =1 and per.isLatest ='Y' "
            + "AND emp.code = :userName AND emp.projMstrEntity.projectId = :projId")
    List<Object[]> findEmpDetailsForTimeSheet(@Param("projId") Long projId, @Param("userName") String userName); */
    
    @Query("SELECT DISTINCT emp.id, emp.code, emp.firstName, emp.lastName, emp.gender, emp.empClassMstrEntity.name, "
            + "emp.companyMstrEntity.name FROM EmpAttendanceEntity pea JOIN pea.empId emp on pea.empId.id=emp.id "
            + "JOIN emp.empClassMstrEntity cme on emp.empClassMstrEntity.id = cme.id "
            + "JOIN emp.companyMstrEntity cse on emp.companyMstrEntity.id = cse.id "
	        + "WHERE "
	        + " emp.projMstrEntity.projectId = :projId")
    List<Object[]> findEmpDetailsForTimeSheet(@Param("projId") Long projId);

    @Query("SELECT DISTINCT emp.id,to_char(ptd.attendanceDate, 'Dy DD-Mon') FROM EmpAttendanceEntity pea JOIN pea.empId emp "
            + "JOIN pea.empAttendanceDtlEntities ptd WHERE ptd.attendanceDate between :weekStartDate AND :weekEndDate "
            + "AND pea.empAttendanceMstrEntity.projId.projectId = :projId AND ptd.projAttdCode = 'P'")
    List<Object[]> getAttendanceDetailsForTimeSheet(@Param("projId") Long projId,
            @Param("weekStartDate") Date weekStartDate, @Param("weekEndDate") Date weekEndDate);
//------------------------------
    @Query("SELECT DISTINCT emp.id,to_char(ptd.attendanceDate, 'Dy DD-Mon') FROM EmpAttendanceEntity pea JOIN pea.empId emp "
            + "JOIN pea.empAttendanceDtlEntities ptd WHERE ptd.attendanceDate between :weekStartDate AND :weekEndDate "
            + "AND pea.empAttendanceMstrEntity.projId.projectId = :projId AND ptd.projAttdCode = 'P' AND emp.id = :empId ")
    List<Object[]> getAttendanceDetailsForIndTimeSheet(@Param("projId") Long projId, @Param("empId") Long empId, 
            @Param("weekStartDate") Date weekStartDate, @Param("weekEndDate") Date weekEndDate);
//------------------------------

    @Query("SELECT emp.id, emp.code, emp.firstName, emp.lastName, emp.gender, emp.empClassMstrEntity.name, "
            + "emp.companyMstrEntity.name FROM EmpAttendanceEntity pea JOIN pea.empId emp JOIN emp.empProjRigisterEntities per ON "
            + "per.projMstrEntity.projectId = :projId AND per.assignStatus= 'Y' AND "
            + "((per.mobilaizationDate BETWEEN :weekStartDate AND :weekEndDate OR per.deMobilaizationDate BETWEEN :weekStartDate AND :weekEndDate) "
            + "OR per.mobilaizationDate <= :weekStartDate) AND (per.deMobilaizationDate IS NULL OR per.deMobilaizationDate >= :weekStartDate) "
            + "AND per.status = 1 JOIN emp.empEnrollmentDtlEntities eed ON ((eed.effectiveFrom BETWEEN :weekStartDate AND :weekEndDate "
            + "OR eed.effectiveTo BETWEEN :weekStartDate AND :weekEndDate) OR eed.effectiveFrom <= :weekStartDate) "
            + "AND (eed.effectiveTo IS NULL OR eed.effectiveTo >= :weekStartDate) JOIN pea.empAttendanceDtlEntities ptd "
            + "WHERE pea.crewId.id = :crewId AND ptd.projAttdCode='P' AND ptd.attendanceDate between :weekStartDate AND :weekEndDate AND "
            + "emp.id NOT IN (SELECT td.empRegisterDtlEntity.id FROM TimeSheetEmpDtlEntity td JOIN td.timeSheetEntity tsm ON tsm.projCrewMstrEntity.id = :crewId AND "
            + "tsm.projMstrEntity.projectId = :projId where td.status = 1 AND tsm.additional =:additional AND tsm.weekStartDate = :weekStartDate)")
    List<Object[]> findEmpRegDetailsForTimeSheet(@Param("projId") Long projId, @Param("crewId") Long crewId,
            @Param("additional") Integer additional, @Param("weekStartDate") Date weekStartDate,
            @Param("weekEndDate") Date weekEndDate);
    //------------------------
    @Query("SELECT emp.id, emp.code, emp.firstName, emp.lastName, emp.gender, emp.empClassMstrEntity.name, "
            + "emp.companyMstrEntity.name FROM EmpAttendanceEntity pea JOIN pea.empId emp JOIN emp.empProjRigisterEntities per ON "
            + "per.projMstrEntity.projectId = :projId AND per.assignStatus= 'Y' AND "
            + "((per.mobilaizationDate BETWEEN :weekStartDate AND :weekEndDate OR per.deMobilaizationDate BETWEEN :weekStartDate AND :weekEndDate) "
            + "OR per.mobilaizationDate <= :weekStartDate) AND (per.deMobilaizationDate IS NULL OR per.deMobilaizationDate >= :weekStartDate) "
            + "AND per.status = 1 JOIN emp.empEnrollmentDtlEntities eed ON ((eed.effectiveFrom BETWEEN :weekStartDate AND :weekEndDate "
            + "OR eed.effectiveTo BETWEEN :weekStartDate AND :weekEndDate) OR eed.effectiveFrom <= :weekStartDate) "
            + "AND (eed.effectiveTo IS NULL OR eed.effectiveTo >= :weekStartDate) JOIN pea.empAttendanceDtlEntities ptd "
            + "WHERE emp.id = :empId AND ptd.projAttdCode='P' AND ptd.attendanceDate between :weekStartDate AND :weekEndDate AND "
            + "emp.id NOT IN (SELECT td.empRegisterDtlEntity.id FROM TimeSheetEmpDtlEntity td JOIN td.timeSheetEntity tsm ON "
            + "tsm.projMstrEntity.projectId = :projId where td.status = 1 AND tsm.additional =:additional AND tsm.weekStartDate = :weekStartDate)")
    List<Object[]> findEmpRegDetailsForIndTimeSheet(@Param("projId") Long projId, @Param("empId") Long empId,
            @Param("additional") Integer additional, @Param("weekStartDate") Date weekStartDate,
            @Param("weekEndDate") Date weekEndDate);
    //------------------------

    @Query("SELECT DISTINCT emp.id, emp.code, emp.firstName, emp.lastName, emp.gender, emp.empClassMstrEntity.name, "
            + "emp.companyMstrEntity.name FROM EmpAttendanceEntity pea JOIN pea.empId emp JOIN emp.empProjRigisterEntities per ON "
            + "per.projMstrEntity.projectId = :projId AND (per.assignStatus= 'Y' OR per.assignStatus IS NULL) AND "
            + "((per.mobilaizationDate BETWEEN :weekStartDate AND :weekEndDate OR per.deMobilaizationDate BETWEEN :weekStartDate AND :weekEndDate) "
            + "OR per.mobilaizationDate <= :weekStartDate) AND (per.deMobilaizationDate IS NULL OR per.deMobilaizationDate >= :weekStartDate) "
            + "AND per.status = 1 JOIN emp.empEnrollmentDtlEntities eed ON ((eed.effectiveFrom BETWEEN :weekStartDate AND :weekEndDate "
            + "OR eed.effectiveTo BETWEEN :weekStartDate AND :weekEndDate) OR eed.effectiveFrom <= :weekStartDate) "
            + "AND (eed.effectiveTo IS NULL OR eed.effectiveTo >= :weekStartDate) JOIN pea.empAttendanceDtlEntities ptd "
            + "WHERE pea.crewId.id = :fromCrewId AND ptd.projAttdCode='P' AND ptd.attendanceDate between :weekStartDate AND :weekEndDate AND "
            + "emp.id NOT IN (SELECT td.empRegisterDtlEntity.id FROM TimeSheetEmpDtlEntity td JOIN td.timeSheetEntity tsm ON tsm.projCrewMstrEntity.id = :crewId AND "
            + "tsm.projMstrEntity.projectId = :projId where td.status = 1 AND tsm.weekStartDate = :weekStartDate)")
    List<Object[]> findOtherCrewEmpRegDetailsForTimeSheet(@Param("projId") Long projId, @Param("crewId") Long crewId,
            @Param("fromCrewId") Long fromCrewId, @Param("weekStartDate") Date weekStartDate,
            @Param("weekEndDate") Date weekEndDate);

    @Query("SELECT emp FROM EmpAttendanceEntity emp " + "JOIN emp.empAttendanceDtlEntities empAttndDtl "
            + "WHERE emp.crewId = :crewId " + "AND emp.empId.projMstrEntity = :projId "
            + "AND emp.empId.clientId = :clientRegEntity "
            + "AND trunc(empAttndDtl.attendanceDate) = trunc(:attendanceDate) AND empAttndDtl.projAttdCode='P'")
    public List<EmpAttendanceEntity> getEmpByAttendenceDate(@Param("projId") ProjMstrEntity projId,
            @Param("crewId") ProjCrewMstrEntity crewId, @Param("clientRegEntity") ClientRegEntity clientRegEntity,
            @Param("attendanceDate") Date attendanceDate);

    @Query("SELECT PER from EmpProjRigisterEntity PER where PER.empRegisterDtlEntity.id in (:empIds) ")
    public List<EmpProjRigisterEntity> findByEmpId(@Param("empIds") List<Long> empIds);

    @Query("SELECT DISTINCT T FROM EmpAttendanceEntity T LEFT JOIN FETCH T.empAttendanceDtlEntities TD "
            + " WHERE T.empAttendanceMstrEntity.status = 1 AND T.status = 1 AND "
            + " LOWER(to_char(T.empAttendanceMstrEntity.attendenceMonth,'MON-YYYY')) = LOWER(:attendenceMonth) AND "
            + " T.empAttendanceMstrEntity.crewId.id IN (:crewIds) AND T.empAttendanceMstrEntity.projId.projectId IN (:projIds) ")
    List<EmpAttendanceEntity> findAttendanceRecords(@Param("projIds") List<Long> projIds,
            @Param("crewIds") List<Long> crewIds, @Param("attendenceMonth") String attendenceMonth);

    @Query("SELECT DISTINCT T FROM EmpAttendanceEntity T LEFT JOIN FETCH T.empAttendanceDtlEntities TD "
            + " WHERE T.empAttendanceMstrEntity.status = 1 AND T.status = 1 AND LOWER(to_char(TD.attendanceDate, 'DD-MON-YYYY')) = LOWER(:attendanceDate) AND "
            + " T.empAttendanceMstrEntity.crewId.id IN (:crewIds) AND T.empAttendanceMstrEntity.projId.projectId IN (:projIds) ")
    List<EmpAttendanceEntity> findAttendanceRecordsByDate(@Param("projIds") List<Long> projIds,
            @Param("crewIds") List<Long> crewIds, @Param("attendanceDate") String attendanceDate);

    @Query("SELECT DISTINCT T FROM EmpAttendanceEntity T LEFT JOIN FETCH T.empAttendanceDtlEntities TD "
            + " WHERE T.empAttendanceMstrEntity.status = 1 AND T.status = 1 AND "
            + " T.empAttendanceMstrEntity.attendenceMonth between :fromMonth and :toMonth AND "
            + " T.empAttendanceMstrEntity.crewId.id IN (:crewIds) AND T.empAttendanceMstrEntity.projId.projectId IN (:projIds) ")
    List<EmpAttendanceEntity> findAttendanceRecordsBtwnDates(@Param("projIds") List<Long> projIds,
            @Param("crewIds") List<Long> crewIds, @Param("fromMonth") Date fromMonth, @Param("toMonth") Date toMonth);

    @Query("SELECT DISTINCT T FROM EmpAttendanceEntity T LEFT JOIN FETCH T.empAttendanceDtlEntities TD "
            + " WHERE T.empAttendanceMstrEntity.status = 1 AND T.status = 1 AND "
            + " LOWER(to_char(TD.attendanceDate, 'DD-MON-YYYY')) = LOWER(:attendenceDate) "
            + " AND T.empAttendanceMstrEntity.projId.projectId IN (:projIds) ")
    List<EmpAttendanceEntity> findDailyResourceStatus(@Param("projIds") List<Long> projIds,
            @Param("attendenceDate") String attendenceDate);

    @Query("SELECT DISTINCT T FROM EmpAttendanceEntity T WHERE T.empId.projMstrEntity.projectId IN :projIds AND "
            + "T.empId.companyMstrEntity.id IN :companyIds")
    List<EmpAttendanceEntity> getCurrentEmployeeList(@Param("projIds") List<Long> projIds,
            @Param("companyIds") List<Long> companyIds);

    @Query("SELECT DISTINCT T FROM EmpAttendanceEntity T WHERE T.empId.projMstrEntity.projectId IN :projIds AND "
            + "T.empId.companyMstrEntity.id IN :companyIds AND T.empId.empClassMstrEntity.id IN :empClassIds")
    List<EmpAttendanceEntity> getCurrentEmployeeList(@Param("projIds") List<Long> projIds,
            @Param("companyIds") List<Long> companyIds, @Param("empClassIds") List<Long> empClassIds);
    
    @Query("SELECT DISTINCT PEAT.empId FROM EmpAttendanceEntity PEAT "
            + " WHERE PEAT.empAttendanceMstrEntity.projId.projectId =:projId AND PEAT.empAttendanceMstrEntity.crewId.id=:crewId "
            + " AND TO_CHAR(PEAT.empAttendanceMstrEntity.attendenceMonth,'MON-YYYY') = TO_CHAR(:startDate,'MON-YYYY')")
    List<EmpRegisterDtlEntity> findAttendanceForByProjIdCrewId(@Param("projId") long projId,
            @Param("crewId") long crewId, @Param("startDate") Date startDate);
    
    @Query("SELECT PEAT.empAttendanceMstrEntity.attendenceMonth, PEAT.empId.id, PEAT.empId.procureCatgDtlEntity.id, PEAD.attendanceDate, PEAD.projAttdCode "
            + " FROM EmpAttendanceEntity PEAT join PEAT.empAttendanceDtlEntities PEAD "
            + " where PEAT.empId.id in (:empIds) AND PEAT.empId.procureCatgDtlEntity.id in (:procureIds) AND PEAD.projAttdCode is not null ")
    List<Object[]> findAttendance(@Param("empIds") Set<Long> empIds, @Param("procureIds") Set<Long> procureIds);

    @Query("SELECT PEAT.empAttendanceMstrEntity.attendenceMonth, PEAT.empId, PEAD.attendanceDate, PEAD.projAttdCode, PEC.projEmpCategory, ECR.projGeneralMstrEntity.currency "
            + " FROM EmpAttendanceEntity PEAT join PEAT.empAttendanceDtlEntities PEAD "
            + " JOIN EmpProjRigisterEntity PED on PED.empRegisterDtlEntity = PEAT.empId AND PED.projMstrEntity = PEAT.empAttendanceMstrEntity.projId "
            + " JOIN PED.empchargeOutRateEntities ECR "
            + " LEFT JOIN ProjEmpClassMstrEntity PEC ON PEC.empClassMstrEntity = PEAT.empId.empClassMstrEntity AND PEC.projectId = PEAT.empAttendanceMstrEntity.projId "
            + " WHERE PEAT.empAttendanceMstrEntity.projId = :projMstr "
            + " AND PEAT.empId.procureCatgDtlEntity.id in (:procureIds) AND PEAD.projAttdCode is not null "
            + " AND lower(PEC.projEmpCategory) in (:empCats) AND PEAT.empId.companyMstrEntity.id in (:cmpIds) "
            + " AND PEAD.attendanceDate between :fromDate and :toDate AND ECR.leaveCostItemEntity.id in (:costIds) ")
    List<Object[]> findAttendanceByProjs(@Param("projMstr") ProjMstrEntity projMstr,
            @Param("procureIds") Set<Long> procureIds, @Param("empCats") List<String> empCats,
            @Param("cmpIds") List<Long> cmpIds, @Param("costIds") List<Long> costIds, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);

}
