package com.rjtech.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.notification.model.ProcurementNormalTimeEntity;

@Repository
public interface ProcurementNormalTimeRepository extends JpaRepository<ProcurementNormalTimeEntity, Long> {

	@Query("SELECT PNT FROM com.rjtech.notification.model.ProcurementNormalTimeEntity PNT WHERE PNT.projId.projectId=:projId AND PNT.status=1 AND PNT.procureType=:contractStageStatus")
    public ProcurementNormalTimeEntity findCutOffDaysForProject(@Param("projId") Long projId, @Param("contractStageStatus") String contractStageStatus);
	
	@Query("SELECT PNT FROM ProcurementNormalTimeEntity PNT WHERE PNT.projId.projectId=:projId AND PNT.status=1 AND PNT.procureType=:contractStageStatus")
    public ProcurementNormalTimeEntity findProcNormalTimeEntityId(@Param("projId") Long projId, @Param("contractStageStatus") String contractStageStatus);
}