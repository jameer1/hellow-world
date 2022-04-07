package com.rjtech.timemanagement.workdairy.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.workdairy.model.WorkDairyPlantCostEntity;

@Repository
public interface PlantCostWorkDairyRepository extends JpaRepository<WorkDairyPlantCostEntity, Long> {

    @Modifying
    @Query("DELETE FROM  WorkDairyPlantCostEntity T  where T.workDairyId.id=:workDairyId  AND  T.costId.id in :deleteIds")
    void deletePlantCostCodes(@Param("workDairyId") Long workDairyId, @Param("deleteIds") List<Long> deleteIds);

    @Query("SELECT T FROM  WorkDairyPlantCostEntity T where T.workDairyId.id=:workDairyId AND T.status=:status AND T.isLatest=true")
    List<WorkDairyPlantCostEntity> findLatestWorkDairyCostDetails(@Param("workDairyId") Long workDairyId,
            @Param("status") Integer status);

    @Query("SELECT T FROM WorkDairyPlantCostEntity T "
            + " where T.isLatest = 1 AND lower(to_char(T.workDairyId.workDairyDate, 'DD-MON-YYYY')) = lower(:workDairyDate) AND T.workDairyId.projId.id in (:projIds) AND "
            + " T.workDairyId.crewId.id in (:crewIds) AND T.costId.id in (:costItemIds) AND "
            + "(( T.workDairyId.clientApproval=0 AND lower(T.workDairyPlantStatusEntity.apprStatus)='approved' AND lower(T.workDairyId.apprStatus)='approved') or "
            + "( T.workDairyId.clientApproval=1 AND lower(T.workDairyPlantStatusEntity.apprStatus)='client approved' AND lower(T.workDairyId.apprStatus)='client approved'))")
    List<WorkDairyPlantCostEntity> findPlantsByDate(@Param("projIds") List<Long> projIds,
            @Param("crewIds") List<Long> crewIds, @Param("costItemIds") List<Long> costItemIds,
            @Param("workDairyDate") String workDairyDate);

    @Query("SELECT T FROM WorkDairyPlantCostEntity T "
            + " WHERE T.isLatest = 1 AND T.workDairyId.workDairyDate between :fromDate and :toDate "
            + " AND T.workDairyId.projId.id in (:projIds) AND T.workDairyId.crewId.id in (:crewIds) AND "
            + " T.workDairyPlantStatusEntity.workDairyPlantDtlEntity.plantRegId.cmpId.id in (:cmpIds) AND "
            + "(( T.workDairyId.clientApproval=0 AND lower(T.workDairyPlantStatusEntity.apprStatus)='approved' AND lower(T.workDairyId.apprStatus)='approved') or "
            + "( T.workDairyId.clientApproval=1 AND lower(T.workDairyPlantStatusEntity.apprStatus)='client approved' AND lower(T.workDairyId.apprStatus)='client approved'))")
    List<WorkDairyPlantCostEntity> findPlantsBtwnDates(@Param("projIds") List<Long> projIds,
            @Param("crewIds") List<Long> crewIds, @Param("cmpIds") List<Long> cmpIds, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);

    @Query("SELECT T FROM WorkDairyPlantCostEntity T "
            + " where T.isLatest = 1 AND T.workDairyId.workDairyDate between :fromDate and :toDate"
            + " AND T.workDairyId.projId.id in (:projIds) AND T.workDairyId.crewId.id in (:crewIds) AND "
            + " T.workDairyPlantStatusEntity.workDairyPlantDtlEntity.plantRegId.cmpId.id in (:cmpIds) AND T.costId.id in (:costItemIds) AND "
            + "(( T.workDairyId.clientApproval=0 AND lower(T.workDairyPlantStatusEntity.apprStatus)='approved' AND lower(T.workDairyId.apprStatus)='approved') or "
            + "( T.workDairyId.clientApproval=1 AND lower(T.workDairyPlantStatusEntity.apprStatus)='client approved' AND lower(T.workDairyId.apprStatus)='client approved')) "
            + " order by T.workDairyId.workDairyDate")
    List<WorkDairyPlantCostEntity> findPlantsBtwnDates(@Param("projIds") List<Long> projIds,
            @Param("crewIds") List<Long> crewIds, @Param("cmpIds") List<Long> cmpIds,
            @Param("costItemIds") List<Long> costItemIds, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);

    @Query("SELECT T FROM WorkDairyPlantCostEntity T "
            + " where T.isLatest = 1 AND T.workDairyId.workDairyDate <= :toDate AND T.workDairyId.projId.id in (:projIds) AND "
            + " T.workDairyPlantStatusEntity.workDairyPlantDtlEntity.plantRegId.cmpId.id in (:cmpIds) AND "
            + "(( T.workDairyId.clientApproval=0 AND lower(T.workDairyPlantStatusEntity.apprStatus)='approved' AND lower(T.workDairyId.apprStatus)='approved') or "
            + "( T.workDairyId.clientApproval=1 AND lower(T.workDairyPlantStatusEntity.apprStatus)='client approved' AND lower(T.workDairyId.apprStatus)='client approved')) "
            + " order by T.workDairyId.workDairyDate")
    List<WorkDairyPlantCostEntity> findAllPlantsBefore(@Param("projIds") List<Long> projIds,
            @Param("cmpIds") List<Long> cmpIds, @Param("toDate") Date toDate);

    @Query("SELECT T FROM WorkDairyPlantCostEntity T "
            + " where T.isLatest = 1 AND T.workDairyId.workDairyDate between :fromDate and :toDate"
            + " AND T.workDairyId.projId.id in (:projIds) AND T.workDairyId.crewId.id in (:crewIds) "
            + " AND T.workDairyPlantStatusEntity.workDairyPlantDtlEntity.plantRegId.id in (:plantIds) "
            + " AND T.workDairyPlantStatusEntity.workDairyPlantDtlEntity.plantRegId.cmpId.id in (:cmpIds) AND T.costId.id in (:costItemIds) AND "
            + "(( T.workDairyId.clientApproval=0 AND lower(T.workDairyPlantStatusEntity.apprStatus)='approved' AND lower(T.workDairyId.apprStatus)='approved') or "
            + "( T.workDairyId.clientApproval=1 AND lower(T.workDairyPlantStatusEntity.apprStatus)='client approved' AND lower(T.workDairyId.apprStatus)='client approved')) "
            + " order by T.workDairyId.workDairyDate")
    List<WorkDairyPlantCostEntity> findPlantsBtwnDates(@Param("projIds") List<Long> projIds,
            @Param("crewIds") List<Long> crewIds, @Param("cmpIds") List<Long> cmpIds,
            @Param("costItemIds") List<Long> costItemIds, @Param("plantIds") List<Long> plantIds,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

}
