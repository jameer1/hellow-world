package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.LeaveNormalTimeEntity;

public interface ProjLeaveRequestRepository extends ProjSettingsBaseRepository<LeaveNormalTimeEntity, Long> {

    @Query("SELECT p FROM LeaveNormalTimeEntity p WHERE p.projId.projectId=:projId AND p.status=:status  ORDER BY  p.id")
    public List<LeaveNormalTimeEntity> findProjLeaveApproval(@Param("projId") Long projId,
            @Param("status") Integer status);

    @Query("SELECT p FROM com.rjtech.projsettings.model.LeaveNormalTimeEntity p WHERE p.isDefault='Y' AND p.projId.projectId IS NULL ")
    public List<LeaveNormalTimeEntity> findDefalutProjLeaveApproval();
    
    @Query("SELECT p FROM LeaveNormalTimeEntity p WHERE p.projId.projectId=:projId AND p.status=:status  ORDER BY  p.id")
    public LeaveNormalTimeEntity findLeaveNormalTimeByProjId(@Param("projId") Long projId,
            @Param("status") Integer status);

}