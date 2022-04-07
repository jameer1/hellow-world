package com.rjtech.register.repository.emp;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.emp.model.EmpProjRigisterEntity;
import com.rjtech.register.repository.RegisterBaseRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface EmpProjRegisterRepository extends RegisterBaseRepository<EmpProjRigisterEntity, Long> {

    @Query("SELECT PER FROM EmpProjRigisterEntity PER  WHERE PER.id not in :empIds  AND  "
            + " PER.projMstrEntity.projectId=:projId  AND PER.status=:status ")
    public List<EmpProjRigisterEntity> findNonAttendenceEmpRegisters(@Param("empIds") List<Long> empIds,
            @Param("projId") Long projId, @Param("status") Integer status);

    @Query("SELECT PER FROM EmpProjRigisterEntity PER  WHERE PER.id  in :empIds  AND  PER.projMstrEntity.projectId=:projId"
            + " AND PER.status=:status  ")
    public List<EmpProjRigisterEntity> findAttendenceEmpRegisters(@Param("empIds") List<Long> empIds,
            @Param("projId") Long projId, @Param("status") Integer status);

    @Query("SELECT PER FROM EmpProjRigisterEntity PER  WHERE PER.empRegisterDtlEntity.id=:empId")
    public List<EmpProjRigisterEntity> findProjEmpRigisters(@Param("empId") Long empId);

    @Query("SELECT PER FROM EmpProjRigisterEntity PER  WHERE PER.status=:status AND PER.projMstrEntity.projectId=:projId ")
    public List<EmpProjRigisterEntity> findProjEmpRigisters(@Param("status") Integer status,
            @Param("projId") Long projId);

    @Query("SELECT max(ped.id) FROM EmpProjRigisterEntity ped  WHERE ped.projMstrEntity.projectId=:projId  and ped.empRegisterDtlEntity.id =:empId ")
    public Long findLatestEmployeDeployment(@Param("projId") Long projId, @Param("empId") Long empId);

    @Query("SELECT ped FROM EmpProjRigisterEntity ped join fetch ped.empEnrollmentDtlEntities enr"
            + "   WHERE ped.empRegisterDtlEntity.id=:empId  and enr.isLatest = 'Y' ")
    public EmpProjRigisterEntity findLatestEmployeDeployment(@Param("empId") Long empId);

    @Query("SELECT ped FROM EmpProjRigisterEntity ped  WHERE ped.projMstrEntity.projectId=:projId  and ped.empRegisterDtlEntity.id =:empId and ped.isLatest = 'Y'")
    public EmpProjRigisterEntity getProjEmpEntityByProjandEmp(@Param("projId") Long projId, @Param("empId") Long empId);

    @Query("SELECT PER FROM EmpProjRigisterEntity PER  WHERE PER.empRegisterDtlEntity.id =:parentId AND  PER.isLatest='Y' AND "
            + "(PER.mobilaizationDate <= :toDate OR PER.deMobilaizationDate BETWEEN :fromDate AND :toDate)")
    public List<EmpProjRigisterEntity> getProjEmpRegForMobilizationStatistics(@Param("parentId") Long empRegId,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    @Modifying
    @Query("UPDATE EmpProjRigisterEntity PER SET PER.isLatest='N' WHERE PER.id=:id")
    void updateExistingEmpProjStatus( @Param("id") Long id );
    
    @Query("SELECT EPR FROM EmpProjRigisterEntity EPR WHERE EPR.deploymentId=:deploymentId and EPR.empRegisterDtlEntity.id=:registerId")
    public EmpProjRigisterEntity findEmpClassNames(@Param("deploymentId") Long deploymentId, @Param("registerId") Long registerId);
}
