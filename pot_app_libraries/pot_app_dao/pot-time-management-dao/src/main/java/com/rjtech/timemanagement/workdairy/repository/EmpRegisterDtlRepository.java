package com.rjtech.timemanagement.workdairy.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
//import com.rjtech.timemanagement.register.emp.model.EmpRegisterDtlEntityCopy;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEntity;

@Repository
public interface EmpRegisterDtlRepository extends JpaRepository<EmpRegisterDtlEntity, Long> {

    @Query("SELECT per.empRegisterDtlEntity.id, per.empRegisterDtlEntity.code, "
            + "per.empRegisterDtlEntity.firstName, per.empRegisterDtlEntity.lastName, "
            + "per.empRegisterDtlEntity.gender, " + "eed.empClassMstrEntity.name, "
            + "per.empRegisterDtlEntity.companyMstrEntity.name, " + "pec.projEmpCategory "
            + "FROM EmpProjRigisterEntity per "
            + "JOIN EmpEnrollmentDtlEntity eed ON eed.empProjRigisterEntity = per "
            + "AND ((eed.effectiveFrom BETWEEN :workDairyDate AND :workDairyDate "
            + "OR eed.effectiveTo BETWEEN :workDairyDate AND :workDairyDate) "
            + "OR eed.effectiveFrom <= :workDairyDate) "
            + "AND (eed.effectiveTo IS NULL OR eed.effectiveTo >= :workDairyDate) " + "AND eed.isLatest = 'Y' "
            + "JOIN EmpAttendanceEntity pat " + "ON  pat.empId = per.empRegisterDtlEntity "
            + "JOIN EmpAttendanceDtlEntity ptd " + "ON ptd.empAttendanceEntity = pat "
            + "JOIN ProjEmpClassMstrEntity pec " + "ON pec.empClassMstrEntity = eed.empClassMstrEntity "
            + "WHERE per.projMstrEntity = :projMstrEntity " + "AND per.assignStatus = 'Y' "
            + "AND ((per.mobilaizationDate BETWEEN :workDairyDate AND :workDairyDate "
            + "OR per.deMobilaizationDate BETWEEN :workDairyDate AND :workDairyDate) "
            + "OR per.mobilaizationDate <= :workDairyDate) " + "AND (per.deMobilaizationDate IS NULL "
            + "OR per.deMobilaizationDate >= :workDairyDate) " + "AND per.status = 1 "
            + "AND ptd.projLeaveId.code = 'P' " + "AND pat.empAttendanceMstrEntity.projId = :projMstrEntity "
            + "AND coalesce(ptd.projLeaveId.clientRegEntity, :clientRegEntity) = :clientRegEntity "
            + "AND ptd.attendanceDate = :workDairyDate")
    List<Object[]> findWorkDairyEmpReg(@Param("projMstrEntity") ProjMstrEntity projMstrEntity,
            @Param("clientRegEntity") ClientRegEntity clientRegEntity, @Param("workDairyDate") Date workDairyDate);

    @Query("SELECT PER.empRegisterDtlEntity.id, PER.empRegisterDtlEntity.code, "
            + "PER.empRegisterDtlEntity.firstName, PER.empRegisterDtlEntity.lastName, "
            + "PER.empRegisterDtlEntity.gender, " + "EED.empClassMstrEntity.name, "
            + "PER.empRegisterDtlEntity.companyMstrEntity.name, " + "PEC.projEmpCategory "
            + "FROM EmpProjRigisterEntity PER "
            + "JOIN EmpEnrollmentDtlEntity EED ON EED.empProjRigisterEntity = PER "
            + "AND ((EED.effectiveFrom BETWEEN :workDairyDate AND :workDairyDate "
            + "OR EED.effectiveTo BETWEEN :workDairyDate AND :workDairyDate) "
            + "OR EED.effectiveFrom <= :workDairyDate) "
            + "AND (EED.effectiveTo IS NULL OR EED.effectiveTo >= :workDairyDate) " + "AND EED.isLatest = 'Y' "
            + "JOIN EmpAttendanceEntity PAT " + "ON  PAT.empId = PER.empRegisterDtlEntity "
            + "JOIN EmpAttendanceDtlEntity PTD " + "ON PTD.empAttendanceEntity = PAT "
            + "JOIN ProjEmpClassMstrEntity PEC " + "ON PEC.empClassMstrEntity = EED.empClassMstrEntity "
            + "WHERE PER.projMstrEntity = :projMstrEntity " + "AND PER.assignStatus = 'Y' "
            + "AND ((PER.mobilaizationDate BETWEEN :workDairyDate AND :workDairyDate "
            + "OR PER.deMobilaizationDate BETWEEN :workDairyDate AND :workDairyDate) "
            + "OR PER.mobilaizationDate <= :workDairyDate) " + "AND (PER.deMobilaizationDate IS NULL "
            + "OR PER.deMobilaizationDate >= :workDairyDate) " + "AND PER.status = 1 "
            + "AND PTD.projLeaveId.code = 'P' " + "AND PAT.empAttendanceMstrEntity.projId = :projMstrEntity "
            + "AND coalesce(PTD.projLeaveId.clientRegEntity, :clientRegEntity) = :clientRegEntity "
            + "AND PTD.attendanceDate = :workDairyDate " + "AND PER.empRegisterDtlEntity.id "
            + "NOT IN (SELECT WD.empRegId FROM WorkDairyEmpDtlEntity WD " + "WHERE WD.status = 1 "
            + "AND WD.workDairyEntity = :workDairy)")
    List<Object[]> findWorkDairyEmpRegDetails(@Param("projMstrEntity") ProjMstrEntity projMstrEntity,
            @Param("clientRegEntity") ClientRegEntity clientRegEntity, @Param("workDairyDate") Date workDairyDate,
            @Param("workDairy") WorkDairyEntity workDairy);

}
