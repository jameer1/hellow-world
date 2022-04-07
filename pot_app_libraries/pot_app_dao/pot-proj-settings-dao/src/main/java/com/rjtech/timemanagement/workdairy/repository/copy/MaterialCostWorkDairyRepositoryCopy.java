package com.rjtech.timemanagement.workdairy.repository.copy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.workdairy.model.WorkDairyMaterialCostEntity;

//import com.rjtech.workdairy.WorkDairyMaterialCostEntityCopy;

@Repository
public interface MaterialCostWorkDairyRepositoryCopy extends JpaRepository<WorkDairyMaterialCostEntity, Long> {

    @Query("SELECT wdmt.costId.id,to_char(wdmt.workDairyId.workDairyDate, 'DD-MM-YYYY'),SUM(wdmt.quantity) FROM "
            + "WorkDairyMaterialCostEntity wdmt WHERE wdmt.workDairyId.projId.projectId=:projId "
            + "AND ((LOWER(wdmt.workDairyMaterialStatusEntity.apprStatus) = 'approved' AND wdmt.workDairyId.clientApproval=0) OR "
            + "(LOWER(wdmt.workDairyMaterialStatusEntity.apprStatus) = 'client approved' AND wdmt.workDairyId.clientApproval=1)) "
            + "GROUP BY wdmt.costId.id,wdmt.workDairyId.workDairyDate")
    public List<Object[]> getMaterialCostDetails(@Param("projId") Long projId);
    
    @Query("SELECT wdmt.costId.id, wdmt.workDairyId.workDairyDate, SUM(wdmt.quantity) FROM WorkDairyMaterialCostEntity wdmt "
    		+ "WHERE wdmt.workDairyId.projId.projectId=:projId "
    		+ "AND wdmt.costId.id in (:resIds) "
            + "AND ((LOWER(wdmt.workDairyMaterialStatusEntity.apprStatus) = 'approved' AND wdmt.workDairyId.clientApproval=0) OR "
            + "(LOWER(wdmt.workDairyMaterialStatusEntity.apprStatus) = 'client approved' AND wdmt.workDairyId.clientApproval=1)) "
            + "GROUP BY wdmt.costId.id,wdmt.workDairyId.workDairyDate")
    public List<Object[]> getMaterialCostDetails(@Param("projId") Long projId, @Param("resIds") List<Long> resIds);

}
