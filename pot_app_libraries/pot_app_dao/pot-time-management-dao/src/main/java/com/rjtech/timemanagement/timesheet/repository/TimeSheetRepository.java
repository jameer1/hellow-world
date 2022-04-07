package com.rjtech.timemanagement.timesheet.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.timesheet.model.TimeSheetEntity;

@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheetEntity, Long> {

    @Query("SELECT T FROM TimeSheetEntity T  WHERE T.projMstrEntity.projectId=:projId AND  T.projCrewMstrEntity.id=:crewId  AND"
            + " T.status=:status  AND to_char(T.weekStartDate, 'DD-Mon-YYYY') =:weekCommenceDay ")
    List<TimeSheetEntity> findCrewTimeSheets(@Param("projId") Long projId, @Param("crewId") Long crewId,
            @Param("status") Integer status, @Param("weekCommenceDay") String weekCommenceDay);

    @Query("SELECT T FROM TimeSheetEntity T  WHERE T.projMstrEntity.projectId=:projId AND  T.projCrewMstrEntity.id=:crewId AND "
            + "T.additional=:additional AND T.status=:status  AND to_char(T.weekStartDate,'DD-Mon-YYYY') = :weekCommenceDay ")
    List<TimeSheetEntity> findCrewTimeSheet(@Param("projId") Long projId, @Param("crewId") Long crewId,
            @Param("additional") Integer additional, @Param("status") Integer status,
            @Param("weekCommenceDay") String weekCommenceDay);

    @Query("SELECT T FROM TimeSheetEntity T  WHERE T.projMstrEntity.projectId=:projId AND  T.empRegisterDtlEntity.id=:empId AND "
            + "T.status=:status  AND to_char(T.weekStartDate,'DD-Mon-YYYY')=:weekCommenceDay ")
    List<TimeSheetEntity> findIndividualTimeSheets(@Param("projId") Long projId, @Param("empId") Long empId,
            @Param("status") Integer status, @Param("weekCommenceDay") String weekCommenceDay);
//--------------------------------------------------------------------------------------------------------------------------------------------------
    /*
    @Query("SELECT T FROM TimeSheetEntity T  WHERE T.projMstrEntity.projectId=:projId AND  ((:empId is null or T.empRegisterDtlEntity is null) "
            + "or T.empRegisterDtlEntity.id=:empId) AND T.additional=:additional AND T.status=:status AND "
            + "to_char(T.weekStartDate,'DD-Mon-YYYY')=:weekCommenceDay ")
    List<TimeSheetEntity> findIndividualTimeSheet(@Param("projId") Long projId, @Param("empId") Long empId,
            @Param("additional") Integer additional, @Param("status") Integer status,
        	@Param("weekCommenceDay") String weekCommenceDay);		
    */
    @Query("SELECT T FROM TimeSheetEntity T  WHERE T.projMstrEntity.projectId=:projId AND T.empRegisterDtlEntity.id=:empId AND "
            + "T.additional=:additional AND T.status=:status  AND to_char(T.weekStartDate,'DD-Mon-YYYY') = :weekCommenceDay ")
    List<TimeSheetEntity> findIndividualTimeSheet(@Param("projId") Long projId, @Param("empId") Long empId,
    		@Param("additional") Integer additional, @Param("status") Integer status,
    		@Param("weekCommenceDay") String weekCommenceDay);
  //--------------------------------------------------------------------------------------------------------------------------------------------------
    @Query("SELECT T FROM TimeSheetEntity T  WHERE T.projMstrEntity.projectId=:projId AND  T.projCrewMstrEntity.id=:crewId AND "
            + "T.additional=:additional AND T.status=:status  AND to_char(T.weekStartDate,'DD-Mon-YYYY')=:weekCommenceDay AND "
            + "T.apprStatus='Submitted'")
    List<TimeSheetEntity> findCrewTimeSheetForApproval(@Param("projId") Long projId, @Param("crewId") Long crewId,
            @Param("additional") Integer additional, @Param("status") Integer status,
            @Param("weekCommenceDay") String weekCommenceDay);

    @Query("SELECT T FROM TimeSheetEntity T  WHERE T.projMstrEntity.projectId=:projId AND  T.empRegisterDtlEntity.id=:empId AND "
            + "T.additional=:additional AND T.status=:status  AND to_char(T.weekStartDate,'DD-Mon-YYYY')=:weekCommenceDay AND "
            + "T.apprStatus IS NOT NULL")
    List<TimeSheetEntity> findIndividualTimeSheetForApproval(@Param("projId") Long projId, @Param("empId") Long empId,
            @Param("additional") Integer additional, @Param("status") Integer status,
            @Param("weekCommenceDay") String weekCommenceDay);

    @Query("SELECT T FROM TimeSheetEntity T  WHERE T.id=:timeSheetId  AND T.status=:status")
    TimeSheetEntity findTimeSheetById(@Param("timeSheetId") Long timeSheetId, @Param("status") Integer status);

    @Query("SELECT DISTINCT t.empRegisterDtlEntity.id FROM TimeSheetEntity t WHERE t.projMstrEntity.projectId= :projId AND "
            + "t.status=1 AND t.empRegisterDtlEntity.id IS NOT NULL AND t.additional=:additional "
            + "AND t.weekStartDate=:weekStartDate")
    List<Long> getIndividualsFromTimeSheet(@Param("projId") Long projId, @Param("weekStartDate") Date weekStartDate,
            @Param("additional") Integer additional);

    @Query("SELECT DISTINCT t.reqUserMstrEntity.userId,t.reqUserMstrEntity.empCode,t.reqUserMstrEntity.displayName FROM TimeSheetEntity t WHERE "
            + "t.projMstrEntity.projectId in :projIds")
    List<Object[]> timeSheetReqUserDetails(@Param("projIds") List<Long> projIds);

    @Query("SELECT t FROM TimeSheetEntity t WHERE t.projMstrEntity.projectId in :projIds AND t.weekStartDate BETWEEN :weekStartDate AND "
            + ":weekEndDate AND t.apprStatus IN :apprStatus")
    List<TimeSheetEntity> timeSheetApprovalStatusReport(@Param("projIds") List<Long> projIds,
            @Param("weekStartDate") Date weekStartDate, @Param("weekEndDate") Date weekEndDate,
            @Param("apprStatus") List<String> apprStatus);

    @Query("SELECT t FROM TimeSheetEntity t WHERE t.projMstrEntity.projectId in :projIds AND t.weekStartDate BETWEEN :weekStartDate AND "
            + ":weekEndDate AND t.reqUserMstrEntity.userId in :userIds AND t.apprStatus IN :apprStatus")
    List<TimeSheetEntity> timeSheetApprovalStatusReport(@Param("projIds") List<Long> projIds,
            @Param("userIds") List<Long> userIds, @Param("weekStartDate") Date weekStartDate,
            @Param("weekEndDate") Date weekEndDate, @Param("apprStatus") List<String> apprStatus);
    
    @Query("SELECT T FROM TimeSheetEntity T  WHERE "
    		+ "T.weekStartDate Between :fromDate AND :toDate "
            //+ "AND T.weekEndDate Between :fromDate AND :toDate "
            + "AND T.createdBy.userId = :createdBy AND T.projMstrEntity.clientId.clientId=:crmId "
            + "ORDER BY T.weekStartDate")
    List<TimeSheetEntity> findAll(@Param("createdBy") Long createdBy, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("crmId") Long crmId);
    
    @Query("SELECT T FROM TimeSheetEntity T  WHERE "
    		+ "T.weekStartDate Between :fromDate AND :toDate AND T.projMstrEntity.clientId.clientId=:crmId "
            //+ "AND T.weekEndDate Between :fromDate AND :toDate "
    		+ "ORDER BY T.weekStartDate")
    List<TimeSheetEntity> findAll(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("crmId") Long crmId);

    @Query("SELECT T FROM TimeSheetEntity T  WHERE "
    		+ "T.weekStartDate Between :fromDate AND :toDate "
            //+ "AND T.weekEndDate Between :fromDate AND :toDate "
            + "AND T.createdBy.userId = :createdBy "
    		+ "AND T.projMstrEntity.projectId IN :projIds AND T.projMstrEntity.clientId.clientId=:crmId "
    		+ "ORDER BY T.weekStartDate")
    List<TimeSheetEntity> findAll(@Param("createdBy") Long createdBy, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("projIds") List<Long> projIds, @Param("crmId") Long crmId);
    
    @Query("SELECT T FROM TimeSheetEntity T  WHERE "
    		+ "T.weekStartDate Between :fromDate AND :toDate "
            //+ "AND T.weekEndDate Between :fromDate AND :toDate "
            + "AND T.projMstrEntity.projectId IN :projIds AND T.projMstrEntity.clientId.clientId=:crmId "
            + "ORDER BY T.weekStartDate")
    List<TimeSheetEntity> findAll(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("projIds") List<Long> projIds, @Param("crmId") Long crmId);
    
    @Query("SELECT T FROM TimeSheetEntity T  WHERE "
    		+ "T.weekStartDate Between :fromDate AND :toDate "
            + "AND T.weekEndDate Between :fromDate AND :toDate "
            + "AND T.createdBy.userId = :createdBy "
            + "AND T.apprStatus != 'Under Preparation' AND T.projMstrEntity.clientId.clientId=:crmId "
            + "ORDER BY T.weekStartDate")
    List<TimeSheetEntity> findAllSubmitted(@Param("createdBy") Long createdBy, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("crmId") Long crmId);
    
    @Query("SELECT T FROM TimeSheetEntity T  WHERE "
    		+ "T.weekStartDate Between :fromDate AND :toDate "
            + "AND T.weekEndDate Between :fromDate AND :toDate "
            + "AND T.apprStatus != 'Under Preparation' AND T.projMstrEntity.clientId.clientId=:crmId "
    		+ "ORDER BY T.weekStartDate")
    List<TimeSheetEntity> findAllSubmitted(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("crmId") Long crmId);

    @Query("SELECT T FROM TimeSheetEntity T  WHERE "
    		+ "T.weekStartDate Between :fromDate AND :toDate "
            + "AND T.weekEndDate Between :fromDate AND :toDate "
            + "AND T.createdBy.userId = :createdBy "
    		+ "AND T.projMstrEntity.projectId IN :projIds "
    		+ "AND T.apprStatus != 'Under Preparation' AND T.projMstrEntity.clientId.clientId=:crmId "
    		+ "ORDER BY T.weekStartDate")
    List<TimeSheetEntity> findAllSubmitted(@Param("createdBy") Long createdBy, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("projIds") List<Long> projIds, @Param("crmId") Long crmId);
    
    @Query("SELECT T FROM TimeSheetEntity T  WHERE "
    		+ "T.weekStartDate Between :fromDate AND :toDate "
            + "AND T.weekEndDate Between :fromDate AND :toDate "
            + "AND T.projMstrEntity.projectId IN :projIds "
            + "AND T.apprStatus != 'Under Preparation' AND T.projMstrEntity.clientId.clientId=:crmId "
            + "ORDER BY T.weekStartDate")
    List<TimeSheetEntity> findAllSubmitted(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("projIds") List<Long> projIds, @Param("crmId") Long crmId);
}