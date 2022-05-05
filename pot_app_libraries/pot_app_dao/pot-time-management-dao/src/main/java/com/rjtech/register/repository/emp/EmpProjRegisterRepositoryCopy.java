package com.rjtech.register.repository.emp;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.register.emp.model.EmpChargeOutRateEntity;
import com.rjtech.register.emp.model.EmpChargeOutRateEntityCopy;
import com.rjtech.register.emp.model.EmpProjRigisterEntity;

//import com.rjtech.timemanagement.register.emp.model.EmpChargeOutRateEntityCopy;
//import com.rjtech.timemanagement.register.emp.model.EmpProjRigisterEntityCopy;

@Repository
public interface EmpProjRegisterRepositoryCopy extends JpaRepository<EmpProjRigisterEntity, Long> {

    @Query("SELECT ecr FROM EmpProjRigisterEntity ped "
            + " join ped.empchargeOutRateEntities ecr WHERE ped.projMstrEntity.projectId = :projId")
    List<EmpChargeOutRateEntity> findChargeOutRates(@Param("projId") Long projId);
    
    //changed the return type from EmpChargeOutRateEntityCopy to EmpChargeOutRateEntity
    @Query("SELECT ecr FROM com.rjtech.register.emp.model.EmpProjRigisterEntity ped "
            + " join ped.empchargeOutRateEntities ecr WHERE ped.projMstrEntity = :projMstrEntity ")
    List<EmpChargeOutRateEntity> findChargeOutRates(@Param("projMstrEntity") ProjMstrEntity projMstrEntity);

    @Query("SELECT DISTINCT PED, PEC.projEmpCategory FROM EmpProjRigisterEntity PED JOIN PED.empchargeOutRateEntities ECR "
            + " LEFT JOIN ProjEmpClassMstrEntity PEC ON PEC.empClassMstrEntity = PED.empRegisterDtlEntity.empClassMstrEntity AND PEC.projectId = PED.projMstrEntity "
            + " WHERE ECR.mobCostItemEntity.id in (:costIds) AND ECR.mobRate > 0 AND "
            + " PED.projMstrEntity.id in (:projIds) AND PED.empRegisterDtlEntity.companyMstrEntity.id in (:cmpIds) AND "
            + " lower(PEC.projEmpCategory) in (:empCats) AND PED.mobilaizationDate between :fromDate and :toDate "
            + " order by PED.mobilaizationDate ")
    List<Object[]> findMobRates(@Param("projIds") List<Long> projIds, @Param("cmpIds") List<Long> cmpIds,
            @Param("costIds") List<Long> costIds, @Param("empCats") List<String> empCats,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT DISTINCT PED, PEC.projEmpCategory FROM EmpProjRigisterEntity PED JOIN PED.empchargeOutRateEntities ECR "
            + " LEFT JOIN ProjEmpClassMstrEntity PEC ON PEC.empClassMstrEntity = PED.empRegisterDtlEntity.empClassMstrEntity AND PEC.projectId = PED.projMstrEntity "
            + " WHERE ECR.deMobCostItemEntity.id in (:costIds) AND ECR.mobRate > 0 AND "
            + " PED.projMstrEntity.id in (:projIds) AND PED.empRegisterDtlEntity.companyMstrEntity.id in (:cmpIds) AND "
            + " lower(PEC.projEmpCategory) in (:empCats) AND PED.deMobilaizationDate between :fromDate and :toDate "
            + " order by PED.deMobilaizationDate ")
    List<Object[]> findDemobRates(@Param("projIds") List<Long> projIds, @Param("cmpIds") List<Long> cmpIds,
            @Param("costIds") List<Long> costIds, @Param("empCats") List<String> empCats,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

}
