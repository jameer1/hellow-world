package com.rjtech.document.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.document.model.CentralTemplatesEntity;

@Repository
public interface CentralTemplatesRepository extends JpaRepository<CentralTemplatesEntity, Long> {
	@Modifying
    @Query("UPDATE CentralTemplatesEntity CTEMPLS SET CTEMPLS.templateJson=:templateJson where CTEMPLS.templateId=:templateId")
    void updateTemplateJsonById( @Param("templateId") Long templateId , @Param("templateJson") String templateJson );
	
	@Query("SELECT CTEMPLS FROM CentralTemplatesEntity CTEMPLS WHERE CTEMPLS.templateCategoryId.categoryId=:templateCategoryId AND CTEMPLS.crmId.clientId=:crmId")
	List<CentralTemplatesEntity> findTemplatesByCategory( @Param("templateCategoryId") Long templateCategoryId , @Param("crmId") Long crmId );
	
	@Modifying
    @Query("UPDATE CentralTemplatesEntity CTEMPLS SET CTEMPLS.status=:status where CTEMPLS.templateId=:templateId")
    void updateTemplateStatusById( @Param("templateId") Long templateId , @Param("status") String status );
	
	@Query("SELECT CTEMPLS FROM CentralTemplatesEntity CTEMPLS WHERE CTEMPLS.templateId=:templateId")
	CentralTemplatesEntity findTemplatesByTemplateId( @Param("templateId") Long templateId );
	
	@Modifying
    @Query("UPDATE CentralTemplatesEntity CTEMPLS SET CTEMPLS.internalApprovalStatus=:approvalStatus,CTEMPLS.isInternalApproved=:isApproved,CTEMPLS.versionStatus=:versionStatus,CTEMPLS.isExternalApprovedRequired=:isExternalApprovalRequired,CTEMPLS.status=:status,CTEMPLS.internalApprovedBy.userId=:approvedBy where CTEMPLS.templateId=:templateId")
	void updateTemplateInternalApprovalStatusById( @Param("templateId") Long templateId , @Param("approvalStatus") String approvalStatus , @Param("isApproved") Character isApproved , @Param("versionStatus") Integer version_status , @Param("isExternalApprovalRequired") Character isExternalApprovalRequired , @Param("status") String status , @Param("approvedBy") Long approvedBy );
	
	@Modifying
    @Query("UPDATE CentralTemplatesEntity CTEMPLS SET CTEMPLS.externalApprovalStatus=:approvalStatus,CTEMPLS.isExternalApproved=:isApproved,CTEMPLS.status=:status,CTEMPLS.versionStatus=:versionStatus,CTEMPLS.externalApprovedBy.userId=:approvedBy where CTEMPLS.templateId=:templateId")
	void updateTemplateExternalApprovalStatusById( @Param("templateId") Long templateId , @Param("approvalStatus") String approvalStatus , @Param("status") String status , @Param("versionStatus") Integer version_status , @Param("isApproved") Character isApproved , @Param("approvedBy") Long approvedBy );
	
	@Query("SELECT MAX(CTEMPLS.templateId) FROM CentralTemplatesEntity CTEMPLS")
	Long findLastInsertedTemplateId();
	
	@Query("SELECT CTEMPLS FROM CentralTemplatesEntity CTEMPLS WHERE CTEMPLS.categoryMstrId.categoryMstrId=:categoryMstrId AND CTEMPLS.status=:status AND CTEMPLS.status IS NOT NULL ")
	List<CentralTemplatesEntity> findTemplatesByStatus( @Param("categoryMstrId") Integer categoryMstrId, @Param("status") String status );
	
	@Modifying
	@Query("UPDATE CentralTemplatesEntity CTEMPLS SET CTEMPLS.formsCount=:formsCnt where CTEMPLS.templateId=:templateId")
	void updateCentralTemplatesFormsCnt( @Param("templateId") Long templateId , @Param("formsCnt") Integer formsCnt );
}
