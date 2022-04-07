package com.rjtech.timemanagement.workdairy.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
//import com.rjtech.projectlib.model.ProjCrewMstrEntityCopy;
//import com.rjtech.timemanagement.register.emp.model.EmpRegisterDtlEntityCopy;
import com.rjtech.timemanagement.workdairy.model.WorkDairyEmpDtlEntity;

@Repository
public interface EmpWorkDairyRepository extends JpaRepository<WorkDairyEmpDtlEntity, Long> {

    @Query("SELECT T FROM WorkDairyEmpDtlEntity T JOIN FETCH T.workDairyEmpWageEntities WEE WHERE T.workDairyEntity.id=:workDairyId AND T.status=:status")
    List<WorkDairyEmpDtlEntity> findWorkDairyEmpDetails(@Param("workDairyId") Long workDairyId,
            @Param("status") Integer status);

    @Query("SELECT DISTINCT t.empRegId.id, to_char(t.workDairyEntity.workDairyDate, 'Dy DD-Mon'), SUM(wdc.usedTime +  wdc.idleTime) FROM "
            + "WorkDairyEmpDtlEntity t JOIN t.workDairyEmpWageEntities wdw JOIN wdw.workDairyEmpCostEntities wdc WHERE "
            + "t.workDairyEntity.workDairyDate between :weekStartDate AND :weekEndDate AND t.workDairyEntity.projId.projectId=:projId AND "
            + "wdc.isLatest = 1 GROUP BY t.empRegId.id , t.workDairyEntity.workDairyDate")
    List<Object[]> workDairyBookedHrsForTimeSheet(@Param("projId") Long workDairyId,
            @Param("weekStartDate") Date weekStartDate, @Param("weekEndDate") Date weekEndDate);
    
    @Query("SELECT WD.empRegId.id, (WDC.usedTime + WDC.idleTime) "
            + "FROM WorkDairyEmpDtlEntity WD "
            + "JOIN WorkDairyEmpWageEntity WDMW ON  WDMW.workDairyEmpDtlEntity = WD "
            + "JOIN WorkDairyEmpCostEntity WDC ON WDC.workDairyEmpWageEntity = WDMW "
            + "WHERE WD.workDairyEntity.workDairyDate = :workDairyDate "
            + "AND WD.workDairyEntity.crewId != :crewId "
            + "AND WD.workDairyEntity.projId = :projId "
            + "AND WD.status = 1 "
            + "AND WD.empRegId IN :empRegList")
    List<Object[]> getBookedHoursForOtherCrew(@Param("projId") ProjMstrEntity projId, 
            @Param("crewId") ProjCrewMstrEntity crewId, 
            @Param("workDairyDate") Date workDairyDate,
            @Param("empRegList") List<EmpRegisterDtlEntity> empRegList);
    
    @Modifying
    @Query("UPDATE WorkDairyEmpDtlEntity T SET T.status=:status where T.id in :manpowerIds")
    void updateWorkDairyManpowerByIds( @Param("manpowerIds") List<Long> manpowerIds , @Param("status") Integer status );
}
