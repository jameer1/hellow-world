package com.rjtech.timemanagement.workdairy.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.workdairy.model.WorkDairyProgressDtlEntity;

@Repository
public interface ProgressWorkDairyRepository extends JpaRepository<WorkDairyProgressDtlEntity, Long> {

    @Query("SELECT T FROM WorkDairyProgressDtlEntity T  WHERE T.workDairyId.id=:workDairyId AND T.status=:status")
    List<WorkDairyProgressDtlEntity> findWorkDairyProgressDetails(@Param("workDairyId") Long workDairyId,
            @Param("status") Integer status);

    @Modifying
    @Query("DELETE FROM  WorkDairyProgressDtlEntity T  where T.workDairyId.id=:workDairyId  AND  T.costId.id in :deleteIds")
    void deleteProgressCostCodes(@Param("workDairyId") Long workDairyId, @Param("deleteIds") List<Long> deleteIds);

    @Query("SELECT T.workDairyId.projId.projectId , T.workDairyId.workDairyDate, "
            + "SUM((T.sowId.projSORItemEntity.manPowerHrs / T.sowId.projSORItemEntity.quantity) * T.value) FROM "
            + "WorkDairyProgressDtlEntity T WHERE lower(T.apprStatus) ='approved' AND T.workDairyId.projId.projectId IN :projIds "
            + " AND T.sowId.projSORItemEntity.quantity !=0 "
            + "GROUP BY T.workDairyId.projId.projectId , T.workDairyId.workDairyDate")
    List<Object[]> getManpowerEarnedValues(@Param("projIds") List<Long> projIds);

    @Query("SELECT T FROM WorkDairyProgressDtlEntity T WHERE "
            + " T.workDairyId.projId.projectId in (:projIds) AND T.costId.id in (:costCodeIds) AND T.sowId.id in (:sowIds) AND "
            + " T.workDairyId.workDairyDate between :fromDate and :toDate AND "
            + " (( T.workDairyId.clientApproval=0 AND lower(T.apprStatus)='approved' AND lower(T.workDairyId.apprStatus)='approved') or "
            + " ( T.workDairyId.clientApproval=1 AND lower(T.apprStatus)='client approved' AND lower(T.workDairyId.apprStatus)='client approved'))")
    List<WorkDairyProgressDtlEntity> findProgressDateWise(@Param("projIds") List<Long> projIds,
            @Param("costCodeIds") List<Long> costCodeIds, @Param("sowIds") List<Long> sowIds,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    
    @Query("SELECT T FROM WorkDairyProgressDtlEntity T WHERE "
            + " T.workDairyId.projId.projectId in (:projIds) AND T.costId.id in (:costCodeIds) AND T.sowId.id in (:sowIds) AND "
            + " (( T.workDairyId.clientApproval=0 AND lower(T.apprStatus)='approved' AND lower(T.workDairyId.apprStatus)='approved') or "
            + " ( T.workDairyId.clientApproval=1 AND lower(T.apprStatus)='client approved' AND lower(T.workDairyId.apprStatus)='client approved'))")
    List<WorkDairyProgressDtlEntity> findProgressDateWisee(@Param("projIds") List<Long> projIds,
            @Param("costCodeIds") List<Long> costCodeIds, @Param("sowIds") List<Long> sowIds);

    @Query("SELECT T FROM WorkDairyProgressDtlEntity T WHERE "
            + " T.workDairyId.projId.projectId in (:projIds) AND T.sowId.id in (:sowIds) AND "
            + " T.workDairyId.workDairyDate between :fromDate and :toDate AND "
            + " (( T.workDairyId.clientApproval=0 AND lower(T.apprStatus)='approved' AND lower(T.workDairyId.apprStatus)='approved') or "
            + " ( T.workDairyId.clientApproval=1 AND lower(T.apprStatus)='client approved' AND lower(T.workDairyId.apprStatus)='client approved'))")
    List<WorkDairyProgressDtlEntity> findProgressActuals(@Param("projIds") List<Long> projIds,
            @Param("sowIds") List<Long> sowIds, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT T FROM WorkDairyProgressDtlEntity T WHERE "
            + " T.workDairyId.projId.projectId in (:projIds) AND lower(T.workDairyId.contractType) = lower(:contractType) AND "
            + " T.workDairyId.workDairyDate <= :toDate AND "
            + " (( T.workDairyId.clientApproval=0 AND lower(T.apprStatus)='approved' AND lower(T.workDairyId.apprStatus)='approved') or "
            + " ( T.workDairyId.clientApproval=1 AND lower(T.apprStatus)='client approved' AND lower(T.workDairyId.apprStatus)='client approved'))")
    List<WorkDairyProgressDtlEntity> findProgressActualsByContract(@Param("projIds") List<Long> projIds,
            @Param("contractType") String contractType, @Param("toDate") Date toDate);

    @Query("SELECT T.workDairyId.projId.projectId, T.workDairyId.projId.projName, T.workDairyId.projId.parentProjectMstrEntity.projectId,"
            + "T.workDairyId.projId.parentProjectMstrEntity.projName, "
            + "SUM((T.sowId.projSORItemEntity.manPowerHrs / T.sowId.projSORItemEntity.quantity) * T.value),"
            + "SUM((COALESCE(T.sowId.projSORItemEntity.labourRate,0) + COALESCE(T.sowId.projSORItemEntity.materialRate,0) + "
            + "COALESCE(T.sowId.projSORItemEntity.plantRate,0)+ COALESCE(T.sowId.projSORItemEntity.othersRate,0)) * T.value), "
            + "SUM((COALESCE(T.sowId.projSORItemEntity.totalRateIfNoSubCategory,0)) * T.value) "
            + "FROM WorkDairyProgressDtlEntity T "
            + "WHERE lower(T.apprStatus) ='approved' AND T.workDairyId.projId.projectId IN :projIds "
            + "AND T.sowId.projSORItemEntity.quantity !=0 "
            + "GROUP BY T.workDairyId.projId.projectId, T.workDairyId.projId.projName, T.workDairyId.projId.parentProjectMstrEntity.projectId,"
            + "T.workDairyId.projId.parentProjectMstrEntity.projName")
    List<Object[]> getManpowerEarnedValuesByProj(@Param("projIds") List<Long> projIds);
    
    @Modifying
    @Query("UPDATE WorkDairyProgressDtlEntity T SET T.status=:status where T.id in :progressIds")
    void updateWorkProgressStatusByIds( @Param("progressIds") List<Long> progressIds , @Param("status") Integer status );
}
