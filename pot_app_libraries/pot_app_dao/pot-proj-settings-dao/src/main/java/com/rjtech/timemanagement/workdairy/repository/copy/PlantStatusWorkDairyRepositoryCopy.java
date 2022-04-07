package com.rjtech.timemanagement.workdairy.repository.copy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.workdairy.model.WorkDairyPlantStatusEntity;

//import com.rjtech.workdairy.WorkDairyPlantStatusEntityCopy;

@Repository
public interface PlantStatusWorkDairyRepositoryCopy extends JpaRepository<WorkDairyPlantStatusEntity, Long> {

    @Query("SELECT wdps.workDairyPlantDtlEntity.plantRegId.plantClassMstrId.id, wdps.workDairyPlantDtlEntity.workDairyEntity.workDairyDate, "
            + "SUM(wdps.usedTotal + wdps.idleTotal) FROM WorkDairyPlantStatusEntity wdps WHERE "
            + "wdps.workDairyPlantDtlEntity.workDairyEntity.projId.projectId = :projId "
            + "AND ((LOWER(wdps.apprStatus)='approved' AND wdps.workDairyPlantDtlEntity.workDairyEntity.clientApproval=0) OR "
            + "(LOWER(wdps.apprStatus)='client approved' AND wdps.workDairyPlantDtlEntity.workDairyEntity.clientApproval=1)) "
            + "GROUP BY  wdps.workDairyPlantDtlEntity.plantRegId.plantClassMstrId.id, wdps.workDairyPlantDtlEntity.workDairyEntity.workDairyDate "
            + "ORDER BY wdps.workDairyPlantDtlEntity.workDairyEntity.workDairyDate")
    public List<Object[]> getMaterialActualQtyForSchedules(@Param("projId") Long projId);
    
    @Query("SELECT wdps.workDairyPlantDtlEntity.plantRegId.plantClassMstrId.id, wdps.workDairyPlantDtlEntity.workDairyEntity.workDairyDate, "
            + "SUM(wdps.usedTotal + wdps.idleTotal) FROM WorkDairyPlantStatusEntity wdps WHERE "
            + "wdps.workDairyPlantDtlEntity.workDairyEntity.projId.projectId = :projId "
            + "AND wdps.workDairyPlantDtlEntity.plantRegId.plantClassMstrId.id in (:resIds) "
            + "AND ((LOWER(wdps.apprStatus)='approved' AND wdps.workDairyPlantDtlEntity.workDairyEntity.clientApproval=0) OR "
            + "(LOWER(wdps.apprStatus)='client approved' AND wdps.workDairyPlantDtlEntity.workDairyEntity.clientApproval=1)) "
            + "GROUP BY  wdps.workDairyPlantDtlEntity.plantRegId.plantClassMstrId.id, wdps.workDairyPlantDtlEntity.workDairyEntity.workDairyDate "
            + "ORDER BY wdps.workDairyPlantDtlEntity.workDairyEntity.workDairyDate")
    public List<Object[]> getMaterialActualQtyForSchedules(@Param("projId") Long projId, @Param("resIds") List<Long> resIds);

}
