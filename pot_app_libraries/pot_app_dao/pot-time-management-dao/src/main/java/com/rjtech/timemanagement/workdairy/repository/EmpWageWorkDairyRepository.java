package com.rjtech.timemanagement.workdairy.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.workdairy.model.WorkDairyEmpWageEntity;

@Repository
public interface EmpWageWorkDairyRepository extends JpaRepository<WorkDairyEmpWageEntity, Long> {

    @Query("SELECT DISTINCT wdmw FROM WorkDairyEmpWageEntity wdmw WHERE wdmw.workDairyEmpDtlEntity.workDairyEntity.projId.projectId IN :projIds AND "
            + "((wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=0 AND lower(wdmw.apprStatus)='approved' AND "
            + "lower(wdmw.workDairyEmpDtlEntity.workDairyEntity.apprStatus)='approved') OR "
            + "(wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=1 AND lower(wdmw.apprStatus)='client approved' AND "
            + "lower(wdmw.workDairyEmpDtlEntity.workDairyEntity.apprStatus)='client approved')) AND "
            + "wdmw.workDairyEmpDtlEntity.empRegId.companyMstrEntity.id IN :companyIds AND "
            + "wdmw.workDairyEmpDtlEntity.workDairyEntity.workDairyDate <= :toDate")
    public List<WorkDairyEmpWageEntity> getWorkDairyManPowerActualHrs(@Param("projIds") List<Long> projIds,
            @Param("companyIds") List<Long> companyIds, @Param("toDate") Date toDate);

    @Query("SELECT DISTINCT wdmw FROM WorkDairyEmpWageEntity wdmw WHERE wdmw.workDairyEmpDtlEntity.workDairyEntity.projId.projectId IN :projIds AND "
            + "((wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=0 AND lower(wdmw.apprStatus)='approved' AND "
            + "lower(wdmw.workDairyEmpDtlEntity.workDairyEntity.apprStatus)='approved') OR "
            + "(wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=1 AND lower(wdmw.apprStatus)='client approved' AND "
            + "lower(wdmw.workDairyEmpDtlEntity.workDairyEntity.apprStatus)='client approved')) AND "
            + "wdmw.workDairyEmpDtlEntity.empRegId.companyMstrEntity.id IN :companyIds AND "
            + "wdmw.workDairyEmpDtlEntity.empRegId.empClassMstrEntity.id IN :empClassIds AND "
            + "wdmw.workDairyEmpDtlEntity.workDairyEntity.workDairyDate <= :toDate")
    public List<WorkDairyEmpWageEntity> getWorkDairyManPowerActualHrs(@Param("projIds") List<Long> projIds,
            @Param("companyIds") List<Long> companyIds, @Param("toDate") Date toDate,
            @Param("empClassIds") List<Long> empClassIds);

    @Query("SELECT DISTINCT wdmw FROM WorkDairyEmpWageEntity wdmw WHERE wdmw.workDairyEmpDtlEntity.workDairyEntity.projId.projectId IN :projIds AND "
            + "((wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=0 AND lower(wdmw.apprStatus)='approved' AND "
            + "lower(wdmw.workDairyEmpDtlEntity.workDairyEntity.apprStatus)='approved') OR "
            + "(wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=1 AND lower(wdmw.apprStatus)='client approved' AND "
            + "lower(wdmw.workDairyEmpDtlEntity.workDairyEntity.apprStatus)='client approved')) AND "
            + "wdmw.workDairyEmpDtlEntity.empRegId.companyMstrEntity.id IN :companyIds AND "
            + "wdmw.workDairyEmpDtlEntity.workDairyEntity.workDairyDate BETWEEN :fromDate AND :toDate")
    public List<WorkDairyEmpWageEntity> getWorkDairyManPowerDateWiseActualHrs(@Param("projIds") List<Long> projIds,
            @Param("companyIds") List<Long> companyIds, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT DISTINCT wdmw FROM WorkDairyEmpWageEntity wdmw WHERE wdmw.workDairyEmpDtlEntity.workDairyEntity.projId.projectId IN :projIds AND "
            + "((wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=0 AND lower(wdmw.apprStatus)='approved' AND "
            + "lower(wdmw.workDairyEmpDtlEntity.workDairyEntity.apprStatus)='approved') OR "
            + "(wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=1 AND lower(wdmw.apprStatus)='client approved' AND "
            + "lower(wdmw.workDairyEmpDtlEntity.workDairyEntity.apprStatus)='client approved')) AND "
            + "wdmw.workDairyEmpDtlEntity.empRegId.companyMstrEntity.id IN :companyIds AND "
            + "wdmw.workDairyEmpDtlEntity.empRegId.empClassMstrEntity.id IN :empClassIds AND "
            + "wdmw.workDairyEmpDtlEntity.workDairyEntity.workDairyDate BETWEEN :fromDate AND :toDate")
    public List<WorkDairyEmpWageEntity> getWorkDairyManPowerDateWiseActualHrs(@Param("projIds") List<Long> projIds,
            @Param("companyIds") List<Long> companyIds, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate,
            @Param("empClassIds") List<Long> empClassIds);

    @Query("SELECT DISTINCT wdmw FROM WorkDairyEmpWageEntity wdmw WHERE wdmw.workDairyEmpDtlEntity.workDairyEntity.projId.projectId IN :projIds AND "
            + "((wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=0 AND lower(wdmw.apprStatus)='approved' AND "
            + "lower(wdmw.workDairyEmpDtlEntity.workDairyEntity.apprStatus)='approved') OR "
            + "(wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=1 AND lower(wdmw.apprStatus)='client approved' AND "
            + "lower(wdmw.workDairyEmpDtlEntity.workDairyEntity.apprStatus)='client approved')) AND "
            + "wdmw.workDairyEmpDtlEntity.workDairyEntity.workDairyDate = :workDate")
    public List<WorkDairyEmpWageEntity> getWorkDairyManPowerActualHrsByDate(@Param("projIds") List<Long> projIds,
            @Param("workDate") Date workDate);

    @Query("SELECT DISTINCT wdmw FROM WorkDairyEmpWageEntity wdmw JOIN wdmw.workDairyEmpCostEntities wmtc WHERE "
            + "wdmw.workDairyEmpDtlEntity.workDairyEntity.projId.projectId IN :projIds AND "
            + "((wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=0 AND lower(wdmw.apprStatus)='approved' AND "
            + "lower(wdmw.workDairyEmpDtlEntity.workDairyEntity.apprStatus)='approved') OR "
            + "(wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=1 AND lower(wdmw.apprStatus)='client approved' AND "
            + "lower(wdmw.workDairyEmpDtlEntity.workDairyEntity.apprStatus)='client approved')) AND "
            + "wdmw.workDairyEmpDtlEntity.empRegId.companyMstrEntity.id IN :companyIds AND "
            + "wmtc.costId.id IN :costIds AND wdmw.workDairyEmpDtlEntity.workDairyEntity.crewId.id IN :crewIds AND "
            + "wdmw.workDairyEmpDtlEntity.workDairyEntity.workDairyDate BETWEEN :fromDate AND :toDate")
    public List<WorkDairyEmpWageEntity> getWorkDairyManPowerDateWiseActualHrs(@Param("projIds") List<Long> projIds,
            @Param("companyIds") List<Long> companyIds, @Param("costIds") List<Long> costIds,
            @Param("crewIds") List<Long> crewIds, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT DISTINCT wdmw FROM WorkDairyEmpWageEntity wdmw JOIN wdmw.workDairyEmpCostEntities wmtc WHERE "
            + "wdmw.workDairyEmpDtlEntity.workDairyEntity.projId.projectId IN :projIds AND "
            + "((wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=0 AND lower(wdmw.apprStatus)='approved' AND "
            + "lower(wdmw.workDairyEmpDtlEntity.workDairyEntity.apprStatus)='approved') OR "
            + "(wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=1 AND lower(wdmw.apprStatus)='client approved' AND "
            + "lower(wdmw.workDairyEmpDtlEntity.workDairyEntity.apprStatus)='client approved')) AND "
            + "wdmw.workDairyEmpDtlEntity.empRegId.companyMstrEntity.id IN :companyIds AND "
            + "wmtc.costId.id IN :costIds AND wdmw.workDairyEmpDtlEntity.workDairyEntity.crewId.id IN :crewIds AND "
            + "wdmw.workDairyEmpDtlEntity.empRegId.empClassMstrEntity.id IN :empClassIds AND "
            + "wdmw.workDairyEmpDtlEntity.workDairyEntity.workDairyDate BETWEEN :fromDate AND :toDate")
    public List<WorkDairyEmpWageEntity> getWorkDairyManPowerDateWiseActualHrs(@Param("projIds") List<Long> projIds,
            @Param("companyIds") List<Long> companyIds, @Param("costIds") List<Long> costIds,
            @Param("crewIds") List<Long> crewIds, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate,
            @Param("empClassIds") List<Long> empClassIds);

    @Query("SELECT DISTINCT wdmw FROM WorkDairyEmpWageEntity wdmw WHERE  wdmw.workDairyEmpDtlEntity.workDairyEntity.projId.projectId IN :projIds AND "
            + "((wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=0 AND lower(wdmw.apprStatus)='approved' AND "
            + "lower(wdmw.workDairyEmpDtlEntity.workDairyEntity.apprStatus)='approved') OR "
            + "(wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=1 AND lower(wdmw.apprStatus)='client approved' AND "
            + "lower(wdmw.workDairyEmpDtlEntity.workDairyEntity.apprStatus)='client approved')) AND "
            + "wdmw.workDairyEmpDtlEntity.workDairyEntity.crewId.id IN :crewIds AND "
            + "wdmw.workDairyEmpDtlEntity.empRegId.empClassMstrEntity.id IN :empClassIds AND "
            + "wdmw.workDairyEmpDtlEntity.workDairyEntity.workDairyDate BETWEEN :fromDate AND :toDate")
    public List<WorkDairyEmpWageEntity> getWorkDairyManPowerDateWiseIdleHrs(@Param("projIds") List<Long> projIds,
            @Param("crewIds") List<Long> crewIds, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate,
            @Param("empClassIds") List<Long> empClassIds);

    @Query("SELECT DISTINCT wdmw FROM WorkDairyEmpWageEntity wdmw WHERE wdmw.workDairyEmpDtlEntity.workDairyEntity.projId.projectId IN :projIds AND "
            + "((wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=0 AND lower(wdmw.apprStatus)='approved' AND "
            + "lower(wdmw.workDairyEmpDtlEntity.workDairyEntity.apprStatus)='approved') OR "
            + "(wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=1 AND lower(wdmw.apprStatus)='client approved' AND "
            + "lower(wdmw.workDairyEmpDtlEntity.workDairyEntity.apprStatus)='client approved')) AND "
            + "wdmw.workDairyEmpDtlEntity.workDairyEntity.crewId.id IN :crewIds AND "
            + "wdmw.workDairyEmpDtlEntity.workDairyEntity.workDairyDate BETWEEN :fromDate AND :toDate")
    public List<WorkDairyEmpWageEntity> getWorkDairyManPowerDateWiseIdleHrs(@Param("projIds") List<Long> projIds,
            @Param("crewIds") List<Long> crewIds, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT DISTINCT wdmw FROM WorkDairyEmpWageEntity wdmw WHERE wdmw.workDairyEmpDtlEntity.workDairyEntity.projId.projectId IN :projIds AND "
            + "((wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=0 AND lower(wdmw.apprStatus)='approved' AND "
            + "lower(wdmw.workDairyEmpDtlEntity.workDairyEntity.apprStatus)='approved') OR "
            + "(wdmw.workDairyEmpDtlEntity.workDairyEntity.clientApproval=1 AND lower(wdmw.apprStatus)='client approved' AND "
            + "lower(wdmw.workDairyEmpDtlEntity.workDairyEntity.apprStatus)='client approved')) AND "
            + "wdmw.workDairyEmpDtlEntity.workDairyEntity.workDairyDate BETWEEN :fromDate AND :toDate")
    public List<WorkDairyEmpWageEntity> getWorkDairyManPowerDateWiseActualHrs(@Param("projIds") List<Long> projIds,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

}
