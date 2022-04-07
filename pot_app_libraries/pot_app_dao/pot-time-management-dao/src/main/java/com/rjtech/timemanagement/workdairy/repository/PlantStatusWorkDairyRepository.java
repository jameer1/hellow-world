package com.rjtech.timemanagement.workdairy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.workdairy.model.WorkDairyPlantStatusEntity;

@Repository
public interface PlantStatusWorkDairyRepository extends JpaRepository<WorkDairyPlantStatusEntity, Long> {

    @Query("SELECT WDPS.workDairyPlantDtlEntity.workDairyEntity.projId.projectId,"
            + " SUM(WDPS.usedTotal), SUM(WDPS.idleTotal) FROM WorkDairyPlantStatusEntity WDPS "
            + " where WDPS.workDairyPlantDtlEntity.plantRegId.id = :plantId and "
            + " ( ( WDPS.workDairyPlantDtlEntity.workDairyEntity.clientApproval = 0 and lower(WDPS.apprStatus) = 'approved' "
            + "     and lower(WDPS.workDairyPlantDtlEntity.workDairyEntity.apprStatus) = 'approved') or "
            + "  ( WDPS.workDairyPlantDtlEntity.workDairyEntity.clientApproval = 1 and lower(WDPS.apprStatus) = 'client approved' "
            + "     and lower(WDPS.workDairyPlantDtlEntity.workDairyEntity.apprStatus) = 'client approved') ) "
            + " group by WDPS.workDairyPlantDtlEntity.workDairyEntity.projId.projectId ")
    List<Object[]> findWorkDairyByPlantId(@Param("plantId") long plantId);

}
