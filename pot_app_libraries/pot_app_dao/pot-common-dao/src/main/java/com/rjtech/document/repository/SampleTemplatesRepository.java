package com.rjtech.document.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.document.model.SampleTemplatesEntity;

@Repository
public interface SampleTemplatesRepository extends JpaRepository<SampleTemplatesEntity, Long> {
	
	@Query("SELECT TEMPLS FROM SampleTemplatesEntity TEMPLS WHERE TEMPLS.templateCategoryId.categoryId=:templateCategoryId AND TEMPLS.categoryMstrId.categoryMstrId=:categoryMstrId")
	List<SampleTemplatesEntity> findTemplatesByCategory( @Param("templateCategoryId") Long templateCategoryId , @Param("categoryMstrId") Integer categoryMstrId );
	
	@Modifying
    @Query("UPDATE SampleTemplatesEntity STEMPLS SET STEMPLS.templateJson=:templateJson  where STEMPLS.templateId=:templateId")
    void updateTemplateJsonById( @Param("templateId") Long templateId , @Param("templateJson") String templateJson );
	
	@Query("SELECT TEMPLS FROM SampleTemplatesEntity TEMPLS WHERE TEMPLS.templateId=:templateId")
	SampleTemplatesEntity findTemplatesByTemplateId( @Param("templateId") Long templateId );
	
	@Modifying
    @Query("UPDATE SampleTemplatesEntity STEMPLS SET STEMPLS.status=:status  where STEMPLS.templateId=:templateId")
    void updateTemplateStatusById( @Param("templateId") Long templateId , @Param("status") String status );
	
	@Query("SELECT MAX(STEMPLS.templateId) FROM SampleTemplatesEntity STEMPLS")
	Long findLastInsertedTemplateId();
	
	@Query("SELECT TEMPLS FROM SampleTemplatesEntity TEMPLS WHERE TEMPLS.categoryMstrId.categoryMstrId=:categoryMstrId AND TEMPLS.status=:status AND TEMPLS.status IS NOT NULL ")
	List<SampleTemplatesEntity> findTemplatesByStatus( @Param("categoryMstrId") Integer categoryMstrId, @Param("status") String status );
	
	@Modifying
    @Query("UPDATE SampleTemplatesEntity STEMPLS SET STEMPLS.internalApprovalStatus=:approvalStatus,STEMPLS.isInternalApproved=:isApproved,STEMPLS.isExternalApprovalRequired=:isExternalApprovalRequired,STEMPLS.status=:status,STEMPLS.internalApproverUserId.userId=:approvedBy where STEMPLS.templateId=:templateId")
	void updateTemplateInternalApprovalStatusById( @Param("templateId") Long templateId , @Param("approvalStatus") String approvalStatus , @Param("isApproved") Character isApproved , @Param("isExternalApprovalRequired") Character isExternalApprovalRequired , @Param("status") String status , @Param("approvedBy") Long approvedBy );
	
	@Modifying
    @Query("UPDATE SampleTemplatesEntity STEMPLS SET STEMPLS.internalApprovalStatus=:approvalStatus,STEMPLS.isInternalApproved=:isApproved,STEMPLS.isExternalApprovalRequired=:isExternalApprovalRequired,STEMPLS.status=:status,STEMPLS.externalApproverUserId.userId=:approvedBy where STEMPLS.templateId=:templateId")
	void updateTemplateInternalApprovalWithExternalApproverById( @Param("templateId") Long templateId , @Param("approvalStatus") String approvalStatus , @Param("isApproved") Character isApproved , @Param("isExternalApprovalRequired") Character isExternalApprovalRequired , @Param("status") String status , @Param("approvedBy") Long approvedBy );
	
	@Modifying
    @Query("UPDATE SampleTemplatesEntity STEMPLS SET STEMPLS.externalApprovalStatus=:approvalStatus,STEMPLS.isExternalApproved=:isApproved,STEMPLS.status=:status,STEMPLS.externalApproverUserId.userId=:approvedBy where STEMPLS.templateId=:templateId")
	void updateTemplateExternalApprovalStatusById( @Param("templateId") Long templateId , @Param("approvalStatus") String approvalStatus , @Param("status") String status , @Param("isApproved") Character isApproved , @Param("approvedBy") Long approvedBy );
	
	@Modifying
	@Query("UPDATE SampleTemplatesEntity STEMPLS SET STEMPLS.formsCount=:formsCnt where STEMPLS.templateId=:templateId")
	void updateSampleTemplatesFormsCnt( @Param("templateId") Long templateId , @Param("formsCnt") Integer formsCnt );
}
