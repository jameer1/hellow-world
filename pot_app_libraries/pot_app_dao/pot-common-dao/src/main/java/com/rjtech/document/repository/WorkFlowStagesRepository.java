package com.rjtech.document.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.document.model.WorkFlowStagesEntity;

@Repository
public interface WorkFlowStagesRepository extends JpaRepository<WorkFlowStagesEntity, Long> {
	/*@Query("SELECT WFSSTAGES FROM WorkFlowStagesEntity WFSSTAGES WHERE WFSSTAGES.sampleTemplateId.templateId=:templateId AND WFSSTAGES.templateCategoryId.categoryId=:templateCategoryId AND WFSSTAGES.categoryMstrId.categoryMstrId=:templMstrCategoryId")
	WorkFlowStagesEntity getStagesBySampleTemplId( @Param("templateId") Long templateId , @Param("templateCategoryId") Long templateCategoryId , @Param("templMstrCategoryId") Integer templateMstrCategoryId );
	
	@Query("SELECT WFCSTAGES FROM WorkFlowStagesEntity WFCSTAGES WHERE WFCSTAGES.centralTemplateId.templateId=:templateId AND WFCSTAGES.templateCategoryId.categoryId=:templateCategoryId AND WFCSTAGES.categoryMstrId.categoryMstrId=:templMstrCategoryId")
	WorkFlowStagesEntity getStagesByCentralTemplId( @Param("templateId") Long templateId , @Param("templateCategoryId") Long templateCategoryId , @Param("templMstrCategoryId") Integer templateMstrCategoryId );
	
	@Query("SELECT WFPSTAGES FROM WorkFlowStagesEntity WFPSTAGES WHERE WFPSTAGES.projectTemplateId.templateId=:templateId AND WFPSTAGES.templateCategoryId.categoryId=:templateCategoryId AND WFPSTAGES.categoryMstrId.categoryMstrId=:templMstrCategoryId")
	WorkFlowStagesEntity getStagesByProjectTemplId( @Param("templateId") Long templateId , @Param("templateCategoryId") Long templateCategoryId , @Param("templMstrCategoryId") Integer templateMstrCategoryId );*/
	
	@Modifying
    @Query("UPDATE WorkFlowStagesEntity WFSTAGES SET WFSTAGES.stage1ApproverId.userId=:approverUserId, WFSTAGES.stage1Status=:approvalStatus,WFSTAGES.stage1Name=:stage1Name, WFSTAGES.status=:status where WFSTAGES.workflowId=:workflowId")
	void updateWorkflowStage1SubmitApproval( @Param("workflowId") Long workflowId, @Param("approvalStatus") String approvalStatus, @Param("approverUserId") Long approverUserId, @Param("stage1Name") String stage1Name, @Param("status") String status );	
	
	@Modifying
    @Query("UPDATE WorkFlowStagesEntity WFSTAGES SET WFSTAGES.stage2ApproverId.userId=:approverUserId, WFSTAGES.stage2Status=:approvalStatus, WFSTAGES.status=:status,WFSTAGES.stage2Name=:stage2Name where WFSTAGES.workflowId=:workflowId")
	void updateWorkflowStage2SubmitApproval( @Param("workflowId") Long workflowId, @Param("approvalStatus") String approvalStatus, @Param("approverUserId") Long approverUserId, @Param("stage2Name") String stage2Name, @Param("status") String status );
	
	@Modifying
    @Query("UPDATE WorkFlowStagesEntity WFSTAGES SET WFSTAGES.stage3ApproverId.userId=:approverUserId, WFSTAGES.stage3Status=:approvalStatus, WFSTAGES.status=:status,WFSTAGES.stage3Name=:stage3Name where WFSTAGES.workflowId=:workflowId")
	void updateWorkflowStage3SubmitApproval( @Param("workflowId") Long workflowId, @Param("approvalStatus") String approvalStatus, @Param("approverUserId") Long approverUserId, @Param("stage3Name") String stage3Name, @Param("status") String status );
	
	@Modifying
    @Query("UPDATE WorkFlowStagesEntity WFSTAGES SET WFSTAGES.stage4ApproverId.userId=:approverUserId, WFSTAGES.stage4Status=:approvalStatus, WFSTAGES.status=:status,WFSTAGES.stage4Name=:stage4Name where WFSTAGES.workflowId=:workflowId")
	void updateWorkflowStage4SubmitApproval( @Param("workflowId") Long workflowId, @Param("approvalStatus") String approvalStatus, @Param("approverUserId") Long approverUserId, @Param("stage4Name") String stage4Name, @Param("status") String status );
	
	@Modifying
    @Query("UPDATE WorkFlowStagesEntity WFSTAGES SET WFSTAGES.stage5ApproverId.userId=:approverUserId, WFSTAGES.stage5Status=:approvalStatus, WFSTAGES.status=:status,WFSTAGES.stage5Name=:stage5Name where WFSTAGES.workflowId=:workflowId")
	void updateWorkflowStage5SubmitApproval( @Param("workflowId") Long workflowId, @Param("approvalStatus") String approvalStatus, @Param("approverUserId") Long approverUserId, @Param("stage5Name") String stage5Name, @Param("status") String status );
	
	@Modifying
    @Query("UPDATE WorkFlowStagesEntity WFSTAGES SET WFSTAGES.stage1ApproverComments=:approverComments, WFSTAGES.stage1Status=:approvalStatus, WFSTAGES.status=:status where WFSTAGES.workflowId=:workflowId")
	void updateWorkflowStage1Approval( @Param("workflowId") Long workflowId, @Param("approvalStatus") String approvalStatus, @Param("approverComments") String approverComments, @Param("status") String status );
	
	@Modifying
	@Query("UPDATE WorkFlowStagesEntity WFSTAGES SET WFSTAGES.stage2ApproverComments=:approverComments, WFSTAGES.stage2Status=:approvalStatus, WFSTAGES.status=:status where WFSTAGES.workflowId=:workflowId")
	void updateWorkflowStage2Approval( @Param("workflowId") Long workflowId, @Param("approvalStatus") String approvalStatus, @Param("approverComments") String approverComments, @Param("status") String status );
	
	@Modifying
	@Query("UPDATE WorkFlowStagesEntity WFSTAGES SET WFSTAGES.stage3ApproverComments=:approverComments, WFSTAGES.stage3Status=:approvalStatus, WFSTAGES.status=:status where WFSTAGES.workflowId=:workflowId")
	void updateWorkflowStage3Approval( @Param("workflowId") Long workflowId, @Param("approvalStatus") String approvalStatus, @Param("approverComments") String approverComments, @Param("status") String status );
	
	@Modifying
	@Query("UPDATE WorkFlowStagesEntity WFSTAGES SET WFSTAGES.stage4ApproverComments=:approverComments, WFSTAGES.stage4Status=:approvalStatus, WFSTAGES.status=:status where WFSTAGES.workflowId=:workflowId")
	void updateWorkflowStage4Approval( @Param("workflowId") Long workflowId, @Param("approvalStatus") String approvalStatus, @Param("approverComments") String approverComments, @Param("status") String status );
	
	@Modifying
	@Query("UPDATE WorkFlowStagesEntity WFSTAGES SET WFSTAGES.stage5ApproverComments=:approverComments, WFSTAGES.stage5Status=:approvalStatus, WFSTAGES.status=:status where WFSTAGES.workflowId=:workflowId")
	void updateWorkflowStage5Approval( @Param("workflowId") Long workflowId, @Param("approvalStatus") String approvalStatus, @Param("approverComments") String approverComments, @Param("status") String status );
}
