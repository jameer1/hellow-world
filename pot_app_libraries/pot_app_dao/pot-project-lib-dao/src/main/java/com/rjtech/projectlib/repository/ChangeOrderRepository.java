package com.rjtech.projectlib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rjtech.projectlib.model.ChangeOrderMstrEntity;

@Repository
public interface ChangeOrderRepository extends JpaRepository<ChangeOrderMstrEntity, Long> {
	
	@Query("SELECT CO FROM ChangeOrderMstrEntity CO WHERE CO.projectId.projectId in (:projectIds) and CO.status=1")
	List<ChangeOrderMstrEntity> findCoDetailsByProjIds( @Param("projectIds") List<Long> projectIds );
	
	@Query("SELECT CO FROM ChangeOrderMstrEntity CO WHERE CO.status=1")
	List<ChangeOrderMstrEntity> findAllProjsCoDetails();
	
	@Query("SELECT CO FROM ChangeOrderMstrEntity CO WHERE CO.status=1 and ( ?1 is null or CO.id in (?1)) ")
	List<ChangeOrderMstrEntity> findProjsCoDetailsByIds(List<Long> ids);
	
	@Modifying
    @Query("UPDATE ChangeOrderMstrEntity CO SET CO.approvalStatus=:approvalStatus,CO.isExternalApprovalRequired=:isExternalApprovalRequired,CO.internalApproverUserId.userId=:approverUserId,CO.originatorUserId.userId=:requestorUserId where CO.id=:coId")
	void updateCoInternalApproverDetailsById( @Param("coId") Long coId, @Param("approvalStatus") String approvalStatus, @Param("isExternalApprovalRequired") Integer isExternalApprovalRequired, @Param("approverUserId") Long approverUserId, @Param("requestorUserId") Long requestorUserId );
	
	@Modifying
    @Query("UPDATE ChangeOrderMstrEntity CO SET CO.approvalStatus=:approvalStatus where CO.id=:coId")
	void updateCoApprovalDetailsById( @Param("coId") Long coId, @Param("approvalStatus") String approvalStatus );
	
	@Modifying
    @Query("UPDATE ChangeOrderMstrEntity CO SET CO.approvalStatus=:approvalStatus,CO.externalApproverUserId.userId=:approverUserId where CO.id=:coId")
	void updateCoExternalApproverDetailsById( @Param("coId") Long coId, @Param("approvalStatus") String approvalStatus, @Param("approverUserId") Long approverUserId );
}
