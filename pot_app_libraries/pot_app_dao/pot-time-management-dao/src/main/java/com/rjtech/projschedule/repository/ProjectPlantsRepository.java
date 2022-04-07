package com.rjtech.projschedule.repository;

//import com.rjtech.projschedule.model.ProjectPlantsDtlEntityCopy;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.PlantMstrEntity;
import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projsettings.model.ProjectPlantsDtlEntity;

public interface ProjectPlantsRepository extends JpaRepository<ProjectPlantsDtlEntity, Long> {

	
	@Query("SELECT PJP FROM ProjectPlantsDtlEntity PJP WHERE PJP.plantMstrEntity.id=:projId")
	List<ProjectPlantsDtlEntity> findOneId(@Param("projId") Long projId);
	
	 @Query("SELECT PJP FROM ProjectPlantsDtlEntity PJP WHERE  PJP.projMstrEntity.projectId=:projId AND PJP.status=:status")
	    public List<ProjectPlantsDtlEntity> findProjectPlants(@Param("projId") Long projId, @Param("status") Integer status);
		
		@Query("SELECT PJP FROM ProjectPlantsDtlEntity PJP WHERE PJP.projMstrEntity.projectId=:projId AND PJP.status NOT IN (2,3)")
	    public List<ProjectPlantsDtlEntity> findProjectPlants( @Param("projId") Long projId );

	    @Query("SELECT PJP.plantMstrEntity.id, sum(PJP.originalQty), sum(PJP.revisedQty), sum(PJP.estimateComplete) "
	            + "FROM ProjectPlantsDtlEntity PJP WHERE PJP.projMstrEntity.projectId=:projId AND PJP.status=:status GROUP BY "
	            + "PJP.plantMstrEntity.id")
	    public List<Object[]> findProjectPlantsStatus(@Param("projId") Long projId, @Param("status") Integer status);

	    @Query("SELECT PCM.id," + "    SUM(WDPS.usedTotal + WDPS.idleTotal) " + "from PlantMstrEntity PCM "
	            + "JOIN PlantRegisterDtlEntityCopy PLRD ON PLRD.plantClassMstrId = PCM "
	            + "JOIN WorkDairyEntity WDM ON WDM.projId = :projId "
	            + "JOIN WorkDairyPlantDtlEntity WDPD ON WDPD.workDairyEntity = WDM AND WDPD.plantRegId = PLRD "
	            + "JOIN WorkDairyPlantStatusEntity WDPS ON WDPS.workDairyPlantDtlEntity = WDPD AND "
	            + "((WDPS.apprStatus = 'Approved' and WDM.clientApproval = 0) "
	            + "        OR (WDPS.apprStatus = 'Client Approved' and WDM.clientApproval = 1)) " + "GROUP BY PCM.id")
	    public List<Object[]> getPlantActualHrs(@Param("projId") ProjMstrEntity projId);

	    @Query("SELECT PCM FROM PlantMstrEntity PCM  WHERE PCM.clientId = :clientId  "
	            + "AND PCM.status = :status AND PCM NOT IN "
	            + "(SELECT PJP.plantMstrEntity FROM ProjectPlantsDtlEntity PJP where PJP.projMstrEntity = :projId)"
	            + "ORDER BY PCM.code")
	    List<PlantMstrEntity> getPlantsForBudget(@Param("clientId") ClientRegEntity clientId,
	            @Param("projId") ProjMstrEntity projId, @Param("status") Integer status);
	    
	    @Query("SELECT PJP FROM ProjectPlantsDtlEntity PJP WHERE PJP.projMstrEntity.projectId=:projId AND PJP.plantMstrEntity.id=:resId AND PJP.status=1")
	    public ProjectPlantsDtlEntity findBy(@Param("projId") Long projId, @Param("resId") Long resId);
	        
	    @Modifying
	    @Query("UPDATE ProjectPlantsDtlEntity PJP SET PJP.itemStatus=:itemStatus,PJP.approverUserId.userId=:approverUserId,PJP.originatorUserId.userId=:originatorUserId WHERE PJP.id in :plantIds")
	    public void updateApproverDetailsByIds( @Param("plantIds") List<Long> plantIds, @Param("approverUserId") Long approverUserId, @Param("originatorUserId") Long originatorUserId, @Param("itemStatus") String itemStatus );
	    
	    @Modifying
	    @Query("UPDATE ProjectPlantsDtlEntity PJP SET PJP.itemStatus=:itemStatus,PJP.status=:status WHERE PJP.id in :plantIds")
	    public void updateApprovalStatusByIds( @Param("plantIds") List<Long> plantIds, @Param("itemStatus") String itemStatus, @Param("status") Integer status );
	    
	    @Modifying
	    @Query("UPDATE ProjectPlantsDtlEntity PJP SET PJP.itemStatus=:itemStatus,PJP.returnedByUserId.userId=:returnedByUserId,PJP.isItemReturned=:isItemReturned,PJP.returnedComments=:comments WHERE PJP.id=:plantId")
	    public void updateReturnItemDetailsById( @Param("plantId") Long plantId, @Param("returnedByUserId") Long returnedByUserId , @Param("isItemReturned") Integer isItemReturned, @Param("comments") String comments, @Param("itemStatus") String itemStatus );
	    
	    @Modifying
	    @Query("UPDATE ProjectPlantsDtlEntity PJP SET PJP.itemStatus=:itemStatus WHERE PJP.id=:plantId")
	    public void updatePlantItemStatusById( @Param("plantId") Long plantId, @Param("itemStatus") String itemStatus );
}
