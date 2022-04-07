package com.rjtech.register.repository.emp;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.register.emp.model.EmpEnrollmentDtlEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;

//import com.rjtech.timemanagement.register.emp.model.EmpEnrollmentDtlEntityCopy;
//import com.rjtech.timemanagement.register.emp.model.EmpRegisterDtlEntityCopy;

@Repository
public interface EmpEnrollmentRepositoryCopy extends JpaRepository<EmpEnrollmentDtlEntity, Long> {

    @Query("select DISTINCT PER.empRegisterDtlEntity.id, PER.empRegisterDtlEntity.code,"
            + "PER.empRegisterDtlEntity.firstName, PER.empRegisterDtlEntity.lastName, "
            + "PER.empRegisterDtlEntity.gender, PER.empRegisterDtlEntity.empClassMstrEntity.name, "
            + " PER.empRegisterDtlEntity.companyMstrEntity.name "
            + "FROM EmpProjRigisterEntity PER where PER.projMstrEntity.projectId = :projId "
            + " AND PER.empRegisterDtlEntity not in (:empRegisterDtlEntities) "
            + " AND ( (PER.mobilaizationDate BETWEEN :startDate AND :endDate "
            + " OR PER.deMobilaizationDate BETWEEN :startDate AND :endDate) "
            + " OR PER.mobilaizationDate <= :startDate ) "
            + "AND (PER.deMobilaizationDate IS NULL OR PER.deMobilaizationDate >= :startDate) "
            + "ORDER BY PER.empRegisterDtlEntity.code")
    public List<Object[]> findNonAttendanceEmployees(@Param("projId") long projId, @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("empRegisterDtlEntities") List<EmpRegisterDtlEntity> empRegisterDtlEntities);

    @Query("select DISTINCT PER.empRegisterDtlEntity.id, PER.empRegisterDtlEntity.code, "
            + "PER.empRegisterDtlEntity.firstName, PER.empRegisterDtlEntity.lastName, "
            + "PER.empRegisterDtlEntity.gender, PER.empRegisterDtlEntity.empClassMstrEntity.name, "
            + " PER.empRegisterDtlEntity.companyMstrEntity.name "
            + "FROM EmpProjRigisterEntity PER where PER.projMstrEntity.projectId = :projId "
            + "AND ( (PER.mobilaizationDate BETWEEN :startDate AND :endDate "
            + " OR PER.deMobilaizationDate BETWEEN :startDate AND :endDate) "
            + " OR PER.mobilaizationDate <= :startDate ) AND (PER.deMobilaizationDate IS NULL OR PER.deMobilaizationDate >= :startDate) "
            + "ORDER BY PER.empRegisterDtlEntity.code")
    public List<Object[]> findNonAttendanceEmployees(@Param("projId") long projId, @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);
    
    @Query("select DISTINCT PER.empRegisterDtlEntity.id, PER.empRegisterDtlEntity.code,"
            + "PER.empRegisterDtlEntity.firstName, PER.empRegisterDtlEntity.lastName, "
            + "PER.empRegisterDtlEntity.gender, PER.empRegisterDtlEntity.empClassMstrEntity.name, "
            + " PER.empRegisterDtlEntity.companyMstrEntity.name "
            + "FROM EmpProjRigisterEntity PER where PER.projMstrEntity.projectId = :projId "
            + " AND PER.empRegisterDtlEntity in (:empRegisterDtlEntities) "
           // + " AND ( (PER.mobilaizationDate BETWEEN :startDate AND :endDate "
           // + " OR PER.deMobilaizationDate BETWEEN :startDate AND :endDate) "
           // + " OR PER.mobilaizationDate <= :startDate ) "
           // + "AND (PER.deMobilaizationDate IS NULL OR TO_CHAR(PER.deMobilaizationDate ,'DD-MM-YYYY') >= TO_CHAR(:currtDate,'DD-MM-YYYY')) "
            + "AND (PER.deMobilaizationDate IS NULL OR PER.deMobilaizationDate >= :currtDate) "
            + "ORDER BY PER.empRegisterDtlEntity.code")
    public List<Object[]> findAttendanceEmployeesFor(@Param("projId") long projId,
            @Param("empRegisterDtlEntities") List<EmpRegisterDtlEntity> empRegisterDtlEntities, 
            @Param("currtDate") Date currtDate);

}
