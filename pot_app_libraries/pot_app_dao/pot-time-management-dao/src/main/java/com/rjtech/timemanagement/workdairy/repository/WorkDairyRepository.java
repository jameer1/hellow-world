package com.rjtech.timemanagement.workdairy.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.workdairy.model.WorkDairyEntity;

@Repository
public interface WorkDairyRepository extends JpaRepository<WorkDairyEntity, Long> {

    @Query("SELECT T FROM WorkDairyEntity T  WHERE T.projId.projectId=:projId AND trunc(T.workDairyDate) = trunc(:workDairyDate)"
            + " AND T.crewId.id=:crewId AND T.status=:status")
    List<WorkDairyEntity> findWorkDairy(@Param("projId") Long projId, @Param("workDairyDate") Date workDairyDate,
            @Param("crewId") Long crewId, @Param("status") Integer status);

    /*@Query("SELECT T FROM WorkDairyEntity T  WHERE T.projId=:projId AND  (:apprStatus IS NOT NULL AND T.apprStatus IS NOT NULL)  AND DATE_FORMAT(T.workDairyDate,'%d-%b-%Y')=:workDairyDate" + " AND T.crewId=:crewId AND T.status=:status")
    List<WorkDairyEntity> findWorkDairyForInternalApproval(@Param("projId") Long projId, @Param("apprStatus") String apprStatus, @Param("workDairyDate") String workDairyDate, @Param("crewId") Long crewId, @Param("status") Integer status);*/

    @Query("SELECT T FROM WorkDairyEntity T  WHERE T.projId.projectId=:projId AND T.apprStatus IS NOT NULL "
            + "AND T.workDairyDate Between :workDairyDate AND :workDairyDate "
            + " AND T.crewId.id=:crewId AND T.status=:status")
    List<WorkDairyEntity> findWorkDairyForInternalApproval(@Param("projId") Long projId,
            @Param("workDairyDate") Date workDairyDate, @Param("crewId") Long crewId, @Param("status") Integer status);

    /*@Query("SELECT T FROM WorkDairyEntity T  WHERE T.projId=:projId AND (:apprStatus IS NOT NULL AND T.apprStatus IS NOT NULL) AND T.clientApproval=true AND DATE_FORMAT(T.workDairyDate,'%d-%b-%Y')=:workDairyDate" + " AND T.crewId=:crewId AND T.status=:status")
    List<WorkDairyEntity> findWorkDairyForClientApproval(@Param("projId") Long projId, @Param("apprStatus") String apprStatus, @Param("workDairyDate") String workDairyDate, @Param("crewId") Long crewId, @Param("status") Integer status);*/

    @Query("SELECT T FROM WorkDairyEntity T  WHERE T.projId.projectId=:projId AND T.apprStatus = 'SubmittedForClientApproval' AND T.clientApproval=true AND T.workDairyDate = :workDairyDate"
            + " AND T.crewId.id = :crewId AND T.status = :status")
    List<WorkDairyEntity> findWorkDairyForClientApproval(@Param("projId") Long projId,
            @Param("workDairyDate") Date workDairyDate, @Param("crewId") Long crewId, @Param("status") Integer status);

    @Query("SELECT T FROM WorkDairyEntity T  WHERE T.id=:workDairyId  AND T.status=:status")
    List<WorkDairyEntity> findWorkDairyById(@Param("workDairyId") Long workDairyId, @Param("status") Integer status);

    @Query("SELECT T FROM WorkDairyEntity T  WHERE T.workDairyDate Between :fromDate AND :toDate AND T.projId.projectId IN :projIds "
            + "AND (T.clientApproval = 0 OR lower(T.apprStatus) != 'approved')")
    List<WorkDairyEntity> getWorkDairyApprovalReport(@Param("projIds") List<Long> projIds,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT T FROM WorkDairyEntity T  WHERE lower(T.apprStatus) = :apprStatus  AND "
            + "T.clientApproval=0  AND T.workDairyDate Between :fromDate AND :toDate AND T.projId.projectId IN :projIds")
    List<WorkDairyEntity> getWorkDairyApprovalReport(@Param("projIds") List<Long> projIds,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("apprStatus") String apprStatus);

    @Query("SELECT T FROM WorkDairyEntity T  WHERE lower(T.apprStatus) = :apprStatus  AND "
            + "T.clientApproval=1  AND T.workDairyDate Between :fromDate AND :toDate AND T.projId.projectId IN :projIds")
    List<WorkDairyEntity> getWorkDairyClientApprovalReport(@Param("projIds") List<Long> projIds,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("apprStatus") String apprStatus);

    @Query("SELECT T FROM WorkDairyEntity T  WHERE "
            + "T.workDairyDate Between :fromDate AND :toDate "
            + "AND T.createdBy.userId = :createdBy AND T.projId.clientId.clientId=:crmId "
            + "ORDER BY T.workDairyDate")
    List<WorkDairyEntity> findAll(@Param("createdBy") Long createdBy, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate,@Param("crmId") Long crmId);
    
    @Query("SELECT T FROM WorkDairyEntity T  WHERE "
    		+ "T.workDairyDate Between :fromDate AND :toDate AND T.projId.clientId.clientId=:crmId "
    		+ "ORDER BY T.workDairyDate")
    List<WorkDairyEntity> findAll(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate,@Param("crmId") Long crmId);

    @Query("SELECT T FROM WorkDairyEntity T  WHERE "
    		+ "T.workDairyDate Between :fromDate AND :toDate "
            + "AND T.createdBy.userId = :createdBy "
    		+ "AND T.projId.projectId IN :projIds AND T.projId.clientId.clientId=:crmId "
    		+ "ORDER BY T.workDairyDate")
    List<WorkDairyEntity> findAll(@Param("createdBy") Long createdBy, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("projIds") List<Long> projIds,@Param("crmId") Long crmId);
    
    @Query("SELECT T FROM WorkDairyEntity T  WHERE "
    		+ "T.workDairyDate Between :fromDate AND :toDate "
            + "AND T.projId.projectId IN :projIds AND T.projId.clientId.clientId=:crmId "
            + "ORDER BY T.workDairyDate")
    List<WorkDairyEntity> findAll(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("projIds") List<Long> projIds,@Param("crmId") Long crmId);

    @Query("SELECT T FROM WorkDairyEntity T  WHERE "
            + "T.workDairyDate Between :fromDate AND :toDate "
            + "AND T.createdBy.userId = :createdBy "
            + "AND T.apprStatus IS NOT NULL AND T.projId.clientId.clientId=:crmId "
            + "ORDER BY T.workDairyDate")
    List<WorkDairyEntity> findAllSubmitted(@Param("createdBy") Long createdBy, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate,@Param("crmId") Long crmId);
    
    @Query("SELECT T FROM WorkDairyEntity T  WHERE "
    		+ "T.workDairyDate Between :fromDate AND :toDate "
    		+ "AND T.apprStatus IS NOT NULL AND T.projId.clientId.clientId=:crmId "
    		+ "ORDER BY T.workDairyDate")
    List<WorkDairyEntity> findAllSubmitted(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate,@Param("crmId") Long crmId);

    @Query("SELECT T FROM WorkDairyEntity T  WHERE "
    		+ "T.workDairyDate Between :fromDate AND :toDate "
            + "AND T.createdBy.userId = :createdBy "
    		+ "AND T.projId.projectId IN :projIds "
    		+ "AND T.apprStatus IS NOT NULL AND T.projId.clientId.clientId=:crmId "
    		+ "ORDER BY T.workDairyDate")
    List<WorkDairyEntity> findAllSubmitted(@Param("createdBy") Long createdBy, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("projIds") List<Long> projIds,@Param("crmId") Long crmId);
    
    @Query("SELECT T FROM WorkDairyEntity T  WHERE "
    		+ "T.workDairyDate Between :fromDate AND :toDate "
            + "AND T.projId.projectId IN :projIds "
            + "AND T.apprStatus IS NOT NULL AND T.projId.clientId.clientId=:crmId "
            + "ORDER BY T.workDairyDate")
    List<WorkDairyEntity> findAllSubmitted(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("projIds") List<Long> projIds,@Param("crmId") Long crmId);
        
    @Query("SELECT T FROM WorkDairyEntity T  WHERE lower(T.apprStatus) = null  AND "
            + "T.clientApproval=0  AND T.workDairyDate Between :fromDate AND :toDate AND T.projId.projectId IN :projIds")
    List<WorkDairyEntity> getWorkDairyApprovalReports(@Param("projIds") List<Long> projIds,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
}