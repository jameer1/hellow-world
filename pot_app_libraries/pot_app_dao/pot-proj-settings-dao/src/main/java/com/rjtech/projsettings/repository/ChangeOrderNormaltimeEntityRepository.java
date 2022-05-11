package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.ChangeOrderNormalTimeEntity;



public interface ChangeOrderNormaltimeEntityRepository extends ProjSettingsBaseRepository<ChangeOrderNormalTimeEntity, Long> {
	
	
	
	  @Query("SELECT PJA FROM ChangeOrderNormalTimeEntity PJA WHERE (( :projId IS NULL AND PJA.projId IS NULL) OR PJA.projId.projectId=:projId) AND PJA.status=:status ORDER BY PJA.apprTypeId")
	  public List<ChangeOrderNormalTimeEntity> findProjChangeOrder(@Param("projId") Long projId, @Param("status") Integer status);
	
	 @Query("SELECT CON FROM ChangeOrderNormalTimeEntity CON WHERE CON.isDefault='Y' AND CON.projId IS NULL")
	  public List<ChangeOrderNormalTimeEntity> findDefaultCON();

}
